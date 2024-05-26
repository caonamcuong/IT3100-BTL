package controller;

import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;

import java.util.Collections;

import bIO.BasicController;
import bIO.BasicIO;
import bIO.BasicNumber;
import bIO.BasicObject;
import bIO.BasicSprite;
import bIO.BasicTimer;
import bIO.Vec2f;
import frame.FrameGenerator;
import map.ground;
import player.Player;
import ui.Clock;

public class SlideController extends BasicController {
	private static final TreeMap<String, BasicSprite> state_machine = new TreeMap<String, BasicSprite>() {{
		put("idle", null);
		put("menu1", new BasicSprite (
				"menuall.png",
				Arrays.asList(0),
				Arrays.asList(0),
				Arrays.asList(640),
				Arrays.asList(360)
			));
		put("menu2", new BasicSprite (
				"menuall.png",
				Arrays.asList(0),
				Arrays.asList(360),
				Arrays.asList(640),
				Arrays.asList(360)
			));
		put("menu3", new BasicSprite (
				"menuall.png",
				Arrays.asList(0),
				Arrays.asList(360*2),
				Arrays.asList(640),
				Arrays.asList(360)
			));
		put("over", null);
		put("post-over", new BasicSprite (
				"gameover.png",
				Arrays.asList(0),
				Arrays.asList(40),
				Arrays.asList(640),
				Arrays.asList(320)
			));
	}};
	@Override
	public BasicSprite getSprite() { return state_machine.get(getState()); }
	
	private static final int KEY_UP = KeyEvent.VK_UP;
	private static final int KEY_DOWN = KeyEvent.VK_DOWN;
	private static final int KEY_SELECT = KeyEvent.VK_SPACE;
	
	static final BasicNumber slideSpeed = new BasicNumber(0.005f);
	private Player main_player;
	private Clock clock;
	private FrameGenerator map_gen; 
	private BasicTimer inputDelayTimer;
	private boolean inputAcceptFlag;
	
	public SlideController(BasicIO io) {
		super(io);
		setPosition(new Vec2f(0,0));
		setState("menu1");
		inputAcceptFlag = true;
		inputDelayTimer = new BasicTimer(io.getStepPerSec() / 2, new Runnable() {
			public void run() {
				inputAcceptFlag = true;
			}
		});
		main_player = null;
	}
	
	private void clearAllObjects() {
		for (BasicObject o: controlled_objects) {
			getIO().removeObject(o);
		}
		toadd_objects.clear();
		toremove_objects.clear();
		controlled_objects.clear();
		
		map_gen.stop();
		getIO().removeObject(map_gen);
		//getIO().removeObject(clock);
		
	}
	
	private void startGame() {
		
		main_player = new Player(getIO(), this);
		main_player.setPosition(new Vec2f(200,180));
		main_player.bboxUpdate();
		
		addObject(main_player);
		for (int i = 0; i < 5; ++i) {
			addObject(new ground(getIO(), this, 200*i, 200));
		}
		
		clock = new Clock(getIO());
		clock.setPosition(new Vec2f(40, 40));
		clock.setPosition(new Vec2f(40f, 40f));
		getIO().addBackgroundObject(clock);
		clock.init();
		map_gen = new FrameGenerator(getIO(), this, getIO().getStepPerSec() * 10);
		getIO().addBackgroundObject(map_gen);
	}
	private void endGame() {
		//getIO().reset();
		getIO().playSound("gameover.wav");
		clearAllObjects();
	}
	private void restartGame() {
		clock.destroy();
		startGame();
	}
	
	private void disableInput() {
		inputAcceptFlag = false;
		inputDelayTimer.setup();
	}
	
	@Override 
	public void fixedUpdate() {
		if (getState() == "menu1") {
			if (inputAcceptFlag && getIO().isPressing(KEY_DOWN)) {
				disableInput();
				setState("menu2");
			}
			else if (inputAcceptFlag && getIO().isPressing(KEY_UP)) {
				disableInput();
				setState("menu3");
			}
			else if (inputAcceptFlag && getIO().isPressing(KEY_SELECT)) {
				disableInput();
				startGame();
				setState("idle");
			}
			inputDelayTimer.run();
		}
		else if (getState() == "menu2") {
			if (inputAcceptFlag && getIO().isPressing(KEY_DOWN)) {
				disableInput();
				setState("menu3");
			}
			else if (inputAcceptFlag && getIO().isPressing(KEY_UP)) {
				disableInput();
				setState("menu1");
			}
			inputDelayTimer.run();
		}
		else if (getState() == "menu3") {
			if (inputAcceptFlag && getIO().isPressing(KEY_DOWN)) {
				disableInput();
				setState("menu1");
			}
			else if (inputAcceptFlag && getIO().isPressing(KEY_UP)) {
				disableInput();
				setState("menu2");
			}
			else if (inputAcceptFlag && getIO().isPressing(KEY_SELECT)) {
				disableInput();
				// endGame();
				//setState("idle");
			}
			inputDelayTimer.run();
		}
		else if (getState() == "over") {
			clock.stop();
			endGame();
			
			setState("post-over");
		}
		else if (getState() == "post-over") {
			if (inputAcceptFlag && getIO().isPressing(KEY_SELECT)) {
				disableInput();
				restartGame();
				setState("idle");
			}
			inputDelayTimer.run();
		}
		else if (getState() == "idle") {
			if (main_player.getState() == "gameover") {
				setState("over");
			}
		}
	}
	
	@Override
	public void postUpdate() {
		if (getState()=="idle") {
			for (BasicObject o: controlled_objects) {
				if (o.getPosition().getX().toFloat() <= -300f) {
					toremove_objects.add(o);
					continue;
				}
				if (o.getPosition().getY().toFloat() > 800f) {
					toremove_objects.add(o);
					continue;
				}
				
				Vec2f old_pos = o.getBBox().getPosition();
				o.setPosition(o.getPosition().sub(slideSpeed, new BasicNumber(0)));
				getIO().quadUpdateObject(o, old_pos);
			}
			
			for (BasicObject o: toadd_objects) {
				controlled_objects.add(o);
				getIO().addObject(o);
			}
			for (BasicObject o: toremove_objects) {
				controlled_objects.remove(o);
				getIO().removeObject(o); 
			}
			toremove_objects.clear();
			toadd_objects.clear();
		}
	}
}
