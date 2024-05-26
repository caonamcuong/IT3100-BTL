package frame;

import java.util.Random;
import java.util.TreeMap;

import bIO.BasicController;
import bIO.BasicIO;
import bIO.BasicObject;
import bIO.BasicSprite;
import bIO.BasicTimer;
import bIO.Vec2f;
import enemy.test03.EnemyTest03;
import enemy.test03.EnemyTest04;
import enemy.test03.EnemyTest05;
import enemy.test03.EnemyTest06;
import enemy.test04.Archers;
import map.ground;

public class FrameGenerator extends BasicObject {
	private static final TreeMap<String, BasicSprite> state_machine = new TreeMap<String, BasicSprite>() {{
		put("idle", null);
	}};
	@Override
	public BasicSprite getSprite() { return state_machine.get(getState()); }
	
	private BasicTimer timer;
	private BasicTimer level_timer;
	private Random rand;
	private static final int types[] = {3,4,9,10, 1,2,5,6,7,8,11, 12,13,14,15, 16,17,18,19,20,21, 22,23,24,25};
	
	private BasicIO io;
	private BasicController control;
	boolean stopped;
	private int level = 0;
	private int start_Pos = 0;
	private int end_Pos = 4;
	
	private static final long levelTime = BasicIO.getStepPerSec() * 20;
	
	public FrameGenerator(BasicIO bsio, BasicController controller, long resetTime) {
		super(bsio);
		setState("idle");
		io = bsio;
		control = controller;
		
		FrameGenerator t = this;
		rand = new Random();
		timer = new BasicTimer(resetTime, new Runnable() {
			public void run() {
				t.chooseFrame();
			}
		});
		level_timer = new BasicTimer(levelTime, new Runnable() {
			public void run() {
				t.levelUp();
			}
		});
		
		timer.setup();
		stopped = false;
		start_Pos = 0;
		end_Pos = 4;
	}
	
	@Override
	public void fixedUpdate() {
		if (stopped) return;
		level_timer.run();
		timer.run();
		if (!timer.getRunning()) timer.setup();
		if (!level_timer.getRunning()) level_timer.setup();
	}
	
	private void levelUp() {
		if (level == 0) {
			level = 1;
			start_Pos = 0;
			end_Pos = 11;
		}
		else if (level == 1) {
			level = 2;
			start_Pos = 4;
			end_Pos = 15;
		}
		else if (level == 2) {
			level = 3;
			start_Pos = 11;
			end_Pos = 21;
		}
		else if (level == 3) {
			level = 4;
			start_Pos = 11;
			end_Pos = 25;
		}
		else if (level == 4) {
			level = 5;
			start_Pos = 21;
			end_Pos = 25;
		}
	}
	
	private void chooseFrame() {
		int typ = types[rand.nextInt(start_Pos, end_Pos)];
		float yPos = rand.nextFloat(-80, -40);
		//System.out.println(typ);
		
		if (typ == 1) genFrame1(new Vec2f(640, yPos));
		else if (typ == 2) genFrame2(new Vec2f(640, yPos));
		else if (typ == 3) genFrame3(new Vec2f(640, yPos));
		else if (typ == 4) genFrame4(new Vec2f(640, yPos));
		else if (typ == 5) genFrame5(new Vec2f(640, yPos));
		else if (typ == 6) genFrame6(new Vec2f(640, yPos));
		else if (typ == 7) genFrame7(new Vec2f(640, yPos));
		else if (typ == 8) genFrame8(new Vec2f(640, yPos));
		else if (typ == 9) genFrame9(new Vec2f(640, yPos));
		else if (typ == 10) genFrame10(new Vec2f(640, yPos));
		else if (typ == 11) genFrame11(new Vec2f(640, yPos));
		else if (typ == 12) genFrame12(new Vec2f(640, yPos));
		else if (typ == 13) genFrame13(new Vec2f(640, yPos));
		else if (typ == 14) genFrame14(new Vec2f(640, yPos));
		else if (typ == 15) genFrame15(new Vec2f(640, yPos));
		else if (typ == 16) genFrame16(new Vec2f(640, yPos));
		else if (typ == 17) genFrame17(new Vec2f(640, yPos));
		else if (typ == 18) genFrame18(new Vec2f(640, yPos));
		else if (typ == 19) genFrame19(new Vec2f(640, yPos));
		else if (typ == 20) genFrame20(new Vec2f(640, yPos));
		else if (typ == 21) genFrame21(new Vec2f(640, yPos));
		else if (typ == 22) genFrame22(new Vec2f(640, yPos));
		else if (typ == 23) genFrame23(new Vec2f(640, yPos));
		else if (typ == 24) genFrame24(new Vec2f(640, yPos));
		else if (typ == 25) genFrame25(new Vec2f(640, yPos));
	}
	
	private void genFrame1(Vec2f position) {
		float x = position.getX().toFloat();
		float y = position.getY().toFloat();
		control.addObject(new ground(io, control, 0+x, 300+y));
		control.addObject(new ground(io, control, 250+x, 220+y));
		control.addObject(new EnemyTest03(io, 180+x, 280+y));
		control.addObject(new EnemyTest03(io, 400+x, 210+y));
		control.addObject(new EnemyTest03(io, 120+x, 280+y));
		control.addObject(new EnemyTest03(io, 340+x, 210+y));
	}

	private void genFrame2(Vec2f position) {
		float x = position.getX().toFloat();
		float y = position.getY().toFloat();
		control.addObject(new ground(io, control, 0+x, 300+y));
		control.addObject(new ground(io, control, 250+x, 220+y));
		control.addObject(new EnemyTest03(io, 180+x, 280+y));
		control.addObject(new EnemyTest04(io, control, 400+x, 210+y));
		control.addObject(new EnemyTest04(io, control, 120+x, 280+y));
		control.addObject(new EnemyTest03(io, 340+x, 210+y));
	}
	
	private void genFrame3(Vec2f position) {
		float x = position.getX().toFloat();
		float y = position.getY().toFloat();
		control.addObject(new ground(io, control, 0+x, 300+y));
		control.addObject(new ground(io, control, 250+x, 220+y));
		control.addObject(new EnemyTest03(io, 180+x, 280+y));
		control.addObject(new EnemyTest03(io, 400+x, 210+y));
	}
	private void genFrame4(Vec2f position) {
		float x = position.getX().toFloat();
		float y = position.getY().toFloat();
		control.addObject(new ground(io, control, 0+x, 300+y));
		control.addObject(new ground(io, control, 250+x, 220+y));
		control.addObject(new EnemyTest04(io, control, 180+x, 280+y));
		control.addObject(new EnemyTest05(io, control, 400+x, 210+y));
	}
	
	private void genFrame5(Vec2f position) {
		float x = position.getX().toFloat();
		float y = position.getY().toFloat();
		control.addObject(new ground(io, control, 0+x, 300+y));
		control.addObject(new ground(io, control, 250+x, 220+y));
		control.addObject(new EnemyTest05(io, control, 180+x, 280+y));
		control.addObject(new EnemyTest05(io, control, 400+x, 210+y));
		control.addObject(new EnemyTest04(io, control, 120+x, 280+y));
		control.addObject(new EnemyTest03(io, 340+x, 210+y));
	}
	private void genFrame6(Vec2f position) {
		float x = position.getX().toFloat();
		float y = position.getY().toFloat();
		control.addObject(new ground(io, control, 0+x, 300+y));
		control.addObject(new ground(io, control, 250+x, 220+y));
		control.addObject(new EnemyTest05(io, control, 180+x, 280+y));
		control.addObject(new EnemyTest04(io, control, 400+x, 210+y));
		control.addObject(new EnemyTest04(io, control, 120+x, 280+y));
		control.addObject(new EnemyTest05(io, control, 340+x, 210+y));
	}
	private void genFrame7(Vec2f position) {
		float x = position.getX().toFloat();
		float y = position.getY().toFloat();
		control.addObject(new ground(io, control, 0+x, 300+y));
		control.addObject(new ground(io, control, 200+x, 300+y));
		control.addObject(new EnemyTest03(io, 160+x, 290+y));
		control.addObject(new EnemyTest03(io, 240+x, 290+y));
		control.addObject(new EnemyTest03(io, 340+x, 290+y));
		control.addObject(new EnemyTest03(io, 380+x, 290+y));
	}
	private void genFrame8(Vec2f position) {
		float x = position.getX().toFloat();
		float y = position.getY().toFloat();
		control.addObject(new ground(io, control, 0+x, 300+y));
		control.addObject(new ground(io, control, 200+x, 300+y));
		control.addObject(new EnemyTest05(io, control, 160+x, 290+y));
		control.addObject(new EnemyTest04(io, control, 240+x, 290+y));
		control.addObject(new EnemyTest04(io, control, 340+x, 290+y));
		control.addObject(new EnemyTest05(io, control, 380+x, 290+y));
	}
	private void genFrame9(Vec2f position) {
		float x = position.getX().toFloat();
		float y = position.getY().toFloat();
		control.addObject(new ground(io, control, 0+x, 300+y));
		control.addObject(new ground(io, control, 200+x, 300+y));
		control.addObject(new EnemyTest03(io, 200+x, 290+y));
		control.addObject(new EnemyTest03(io, 340+x, 290+y));
	}
	private void genFrame10(Vec2f position) {
		float x = position.getX().toFloat();
		float y = position.getY().toFloat();
		control.addObject(new ground(io, control, 0+x, 300+y));
		control.addObject(new ground(io, control, 200+x, 300+y));
		control.addObject(new EnemyTest04(io, control, 200+x, 290+y));
		control.addObject(new EnemyTest05(io, control, 340+x, 290+y));
	}
	private void genFrame11(Vec2f position) {
		float x = position.getX().toFloat();
		float y = position.getY().toFloat();
		control.addObject(new ground(io, control, 0+x, 300+y));
		control.addObject(new ground(io, control, 200+x, 300+y));
		control.addObject(new EnemyTest03(io, 160+x, 290+y));
		control.addObject(new EnemyTest03(io, 240+x, 290+y));
		control.addObject(new EnemyTest05(io, control, 340+x, 290+y));
		control.addObject(new EnemyTest05(io, control, 380+x, 290+y));
	}
	private void genFrame12(Vec2f position) {
		float x = position.getX().toFloat();
		float y = position.getY().toFloat();
		control.addObject(new ground(io, control, 0+x, 300+y));
		control.addObject(new ground(io, control, 250+x, 240+y));
		control.addObject(new ground(io, control, 120+x, 140+y));
		control.addObject(new EnemyTest03(io, 320+x, 220+y));
		control.addObject(new EnemyTest03(io, 380+x, 220+y));
		control.addObject(new EnemyTest03(io, 440+x, 220+y));
		control.addObject(new EnemyTest03(io, 80+x, 280+y));
		control.addObject(new EnemyTest03(io, 160+x, 280+y));
		control.addObject(new EnemyTest03(io, 190+x, 280+y));
	}
	private void genFrame13(Vec2f position) {
		float x = position.getX().toFloat();
		float y = position.getY().toFloat();
		control.addObject(new ground(io, control, 0+x, 300+y));
		control.addObject(new ground(io, control, 250+x, 240+y));
		control.addObject(new ground(io, control, 120+x, 140+y));
		control.addObject(new EnemyTest03(io, 320+x, 220+y));
		control.addObject(new EnemyTest03(io, 380+x, 220+y));
		control.addObject(new EnemyTest04(io, control, 440+x, 220+y));
		control.addObject(new EnemyTest05(io, control, 80+x, 280+y));
		control.addObject(new EnemyTest03(io, 160+x, 280+y));
		control.addObject(new EnemyTest04(io, control, 190+x, 280+y));
	}
	private void genFrame14(Vec2f position) {
		float x = position.getX().toFloat();
		float y = position.getY().toFloat();
		control.addObject(new ground(io, control, 0+x, 300+y));
		control.addObject(new ground(io, control, 250+x, 240+y));
		control.addObject(new ground(io, control, 120+x, 140+y));
		control.addObject(new EnemyTest03(io, 320+x, 220+y));
		control.addObject(new EnemyTest05(io, control, 380+x, 220+y));
		control.addObject(new EnemyTest05(io, control, 440+x, 220+y));
		control.addObject(new EnemyTest03(io, 80+x, 280+y));
		control.addObject(new EnemyTest05(io, control, 160+x, 280+y));
		control.addObject(new EnemyTest05(io, control, 190+x, 280+y));
	}
	private void genFrame15(Vec2f position) {
		float x = position.getX().toFloat();
		float y = position.getY().toFloat();
		control.addObject(new ground(io, control, 0+x, 300+y));
		control.addObject(new ground(io, control, 250+x, 240+y));
		control.addObject(new ground(io, control, 120+x, 140+y));
		control.addObject(new EnemyTest04(io, 320+x, 220+y));
		control.addObject(new EnemyTest05(io, control, 380+x, 220+y));
		control.addObject(new EnemyTest03(io, 440+x, 220+y));
		control.addObject(new EnemyTest03(io, 80+x, 280+y));
		control.addObject(new EnemyTest03(io, 160+x, 280+y));
		control.addObject(new EnemyTest05(io, control, 190+x, 280+y));
	}
	private void genFrame16(Vec2f position) {
		float x = position.getX().toFloat();
		float y = position.getY().toFloat();
		
		control.addObject(new ground(io, control, 0+x, 250+y));
		control.addObject(new ground(io, control, 250+x, 300+y));
		control.addObject(new ground(io, control, 220+x, 170+y));
		control.addObject(new EnemyTest03(io, 120+x, 230+y));
		control.addObject(new EnemyTest03(io, 180+x, 230+y));
		control.addObject(new EnemyTest03(io, 260+x, 150+y));
		control.addObject(new EnemyTest03(io, 320+x, 150+y));
		control.addObject(new EnemyTest03(io, 400+x, 150+y));
		control.addObject(new EnemyTest03(io, 260+x, 280+y));
	}
	private void genFrame17(Vec2f position) {
		float x = position.getX().toFloat();
		float y = position.getY().toFloat();
		control.addObject(new ground(io, control, 0+x, 250+y));
		control.addObject(new ground(io, control, 250+x, 300+y));
		control.addObject(new ground(io, control, 220+x, 170+y));
		control.addObject(new EnemyTest03(io, 120+x, 230+y));
		control.addObject(new EnemyTest05(io, control, 180+x, 230+y));
		control.addObject(new EnemyTest03(io, 260+x, 150+y));
		control.addObject(new EnemyTest05(io, control, 320+x, 150+y));
		control.addObject(new EnemyTest03(io, 400+x, 150+y));
		control.addObject(new EnemyTest06(io, control, 260+x, 280+y));
	}
	private void genFrame18(Vec2f position) {
		float x = position.getX().toFloat();
		float y = position.getY().toFloat();
		control.addObject(new ground(io, control, 0+x, 250+y));
		control.addObject(new ground(io, control, 250+x, 300+y));
		control.addObject(new ground(io, control, 220+x, 170+y));
		control.addObject(new EnemyTest05(io, control, 120+x, 230+y));
		control.addObject(new EnemyTest03(io, 180+x, 230+y));
		control.addObject(new EnemyTest04(io, control, 260+x, 150+y));
		control.addObject(new EnemyTest03(io, 320+x, 150+y));
		control.addObject(new EnemyTest04(io, control, 400+x, 150+y));
		control.addObject(new EnemyTest05(io, control, 260+x, 280+y));
	}
	private void genFrame19(Vec2f position) {
		float x = position.getX().toFloat();
		float y = position.getY().toFloat();
		control.addObject(new ground(io, control, 0+x, 250+y));
		control.addObject(new ground(io, control, 250+x, 300+y));
		control.addObject(new ground(io, control, 220+x, 170+y));
		control.addObject(new Archers(io, control, 320+x, 150+y));
		control.addObject(new Archers(io, control, 440+x, 280+y));
	}
	private void genFrame20(Vec2f position) {
		float x = position.getX().toFloat();
		float y = position.getY().toFloat();
		control.addObject(new ground(io, control, 0+x, 250+y));
		control.addObject(new ground(io, control, 250+x, 300+y));
		control.addObject(new ground(io, control, 220+x, 170+y));
		control.addObject(new EnemyTest03(io, 120+x, 230+y));
		control.addObject(new EnemyTest06(io, control, 180+x, 230+y));
		control.addObject(new EnemyTest03(io, 260+x, 150+y));
		control.addObject(new EnemyTest03(io, 320+x, 150+y));
		control.addObject(new EnemyTest06(io, control, 400+x, 150+y));
		control.addObject(new EnemyTest06(io, control, 260+x, 280+y));
	}
	private void genFrame21(Vec2f position) {
		float x = position.getX().toFloat();
		float y = position.getY().toFloat();
		control.addObject(new ground(io, control, 0+x, 250+y));
		control.addObject(new ground(io, control, 250+x, 300+y));
		control.addObject(new ground(io, control, 220+x, 170+y));
		
		control.addObject(new EnemyTest06(io, control,120+x, 230+y));
		control.addObject(new EnemyTest06(io, control, 180+x, 230+y));
		control.addObject(new EnemyTest03(io, 260+x, 150+y));
		control.addObject(new EnemyTest06(io, control, 320+x, 150+y));
		control.addObject(new EnemyTest03(io, 400+x, 150+y));
		control.addObject(new EnemyTest03(io, 260+x, 280+y));
	}
	private void genFrame22(Vec2f position) {
		float x = position.getX().toFloat();
		float y = position.getY().toFloat();
		
		control.addObject(new ground(io, control, 0+x, 300+y));
		control.addObject(new ground(io, control, 250+x, 220+y));
		control.addObject(new EnemyTest05(io, control, 80+x, 280+y));
		control.addObject(new EnemyTest06(io, control, 140+x, 280+y));
		control.addObject(new EnemyTest06(io, control, 180+x, 280+y));
		control.addObject(new EnemyTest05(io, control, 300+x, 210+y));
		control.addObject(new EnemyTest06(io, control, 360+x, 210+y));
		control.addObject(new EnemyTest06(io, control, 400+x, 210+y));
	}
	private void genFrame23(Vec2f position) {
		float x = position.getX().toFloat();
		float y = position.getY().toFloat();
		control.addObject(new ground(io, control, 0+x, 300+y));
		control.addObject(new ground(io, control, 200+x, 300+y));
		control.addObject(new EnemyTest06(io, control, 120+x, 290+y));
		control.addObject(new EnemyTest03(io, 160+x, 290+y));
		control.addObject(new Archers(io, control, 200+x, 290+y));
		control.addObject(new EnemyTest06(io, control, 240+x, 290+y));
		control.addObject(new EnemyTest06(io, control, 280+x, 290+y));
		control.addObject(new EnemyTest03(io, 340+x, 290+y));
		control.addObject(new Archers(io, control, 380+x, 290+y));
	}
	private void genFrame24(Vec2f position) {
		float x = position.getX().toFloat();
		float y = position.getY().toFloat();
		control.addObject(new ground(io, control, 0+x, 300+y));
		control.addObject(new ground(io, control, 250+x, 240+y));
		control.addObject(new ground(io, control, 120+x, 140+y));
		
		control.addObject(new EnemyTest03(io, 200+x, 120+y));
		control.addObject(new EnemyTest03(io, 260+x, 120+y));
		control.addObject(new EnemyTest05(io, control, 320+x, 120+y));
		
		control.addObject(new EnemyTest06(io, control, 300+x, 220+y));
		control.addObject(new EnemyTest06(io, control, 360+x, 220+y));
		control.addObject(new EnemyTest06(io, control, 420+x, 220+y));
		
		control.addObject(new EnemyTest03(io, 40+x, 280+y));
		control.addObject(new EnemyTest03(io, 100+x, 280+y));
		control.addObject(new Archers(io, control, 160+x, 280+y));	
	}
	private void genFrame25(Vec2f position) {
		float x = position.getX().toFloat();
		float y = position.getY().toFloat();
		
		control.addObject(new ground(io, control, 0+x, 250+y));
		control.addObject(new ground(io, control, 250+x, 300+y));
		control.addObject(new ground(io, control, 220+x, 170+y));

		control.addObject(new EnemyTest03(io, 320+x, 150+y));
		control.addObject(new Archers(io, control, 380+x, 150+y));
		
		control.addObject(new EnemyTest03(io, 360+x, 280+y));
		control.addObject(new Archers(io, control, 400+x, 280+y));
		
		control.addObject(new EnemyTest04(io, control, 60+x, 230+y));
		control.addObject(new EnemyTest06(io, control, 120+x, 230+y));
		control.addObject(new EnemyTest06(io, control, 180+x, 230+y));
	}
	
	public void stop() {
		stopped = true;
	}
}
