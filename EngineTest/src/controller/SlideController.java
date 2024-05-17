package controller;

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
import bIO.Vec2f;
import player.Player;

public class SlideController extends BasicController {
	private static final TreeMap<String, BasicSprite> state_machine = new TreeMap<String, BasicSprite>() {{
		put("idle", null);
	}};
	@Override
	public BasicSprite getSprite() { return state_machine.get(getState()); }
	
	static final BasicNumber slideSpeed = new BasicNumber(0.005f);
	Player main_player;
	
	public SlideController(BasicIO io) {
		super(io);
		setState("idle");
		main_player = null;
	}
	
	public void addMainPlayer(Player o) {
		main_player = o;
		addObject((BasicObject)o);
	}
	
	@Override
	public void postUpdate() {
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
		for (BasicObject o: toremove_objects) { // O(n^2)
			controlled_objects.remove(o);
			getIO().removeObject(o); 
		}
		toremove_objects.clear();
		toadd_objects.clear();
	}
}
