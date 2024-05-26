package game;

import bIO.BasicController;
import bIO.BasicIO;
import bIO.BasicNumber;
import bIO.BasicWall;
import bIO.Vec2f;
import controller.SlideController;
import enemy.test03.EnemyTest03;
import enemy.test03.EnemyTest04;
import enemy.test03.EnemyTest05;
import enemy.test03.EnemyTest06;
import enemy.test04.Archers;
import map.ground;
import map.map;
import player.Player;
import ui.Clock;

public class FrameTest {
	public static void main(String[] args) {
		//TestMap01(0, 0);
		//TestMap02(0,0);
		//TestMap03(0,0);
		//TestMap04(0,0);
		TestMap05(0,0);
		//TestMap06(0,0);
		//TestMap08(0,0);
	}
	
	private static void TestMap01(float x, float y) {
		BasicIO io = new BasicIO();
		BasicController control = new BasicController(io);
		Player player = new Player(io, control);
		player.setPosition(new Vec2f(120+x,48+y));
		player.bboxUpdate();
		control.addObject(player);
		
		control.addObject(new ground(io, control, 0+x, 300+y));
		control.addObject(new ground(io, control, 250+x, 220+y));
		control.addObject(new EnemyTest03(io, 180+x, 280+y));
		control.addObject(new EnemyTest04(io, control, 400+x, 210+y));
		control.addObject(new EnemyTest04(io, control, 120+x, 280+y));
		control.addObject(new EnemyTest03(io, 340+x, 210+y));
		
		io.addObject(control);
		io.addBackgroundObject(new Clock(io) {{setPosition(new Vec2f(40, 40));}});
		
		io.run();
	}
	
	private static void TestMap02(float x, float y) {
		BasicIO io = new BasicIO();
		BasicController control = new BasicController(io);
		Player player = new Player(io, control);
		player.setPosition(new Vec2f(120+x,48+y));
		player.bboxUpdate();
		control.addObject(player);
		
		control.addObject(new ground(io, control, 0+x, 300+y));
		control.addObject(new ground(io, control, 200+x, 300+y));
		control.addObject(new EnemyTest06(io, control, 120+x, 290+y));
		control.addObject(new EnemyTest03(io, 160+x, 290+y));
		control.addObject(new Archers(io, control, 200+x, 290+y));
		control.addObject(new EnemyTest06(io, control, 240+x, 290+y));
		control.addObject(new EnemyTest06(io, control, 280+x, 290+y));
		control.addObject(new EnemyTest03(io, 340+x, 290+y));
		control.addObject(new Archers(io, control, 380+x, 290+y));
		
		io.addObject(control);
		io.addBackgroundObject(new Clock(io) {{setPosition(new Vec2f(40, 40));}});
		
		io.run();
	}
	
	private static void TestMap03(float x, float y) {
		BasicIO bsio = new BasicIO();
		BasicController controller = new BasicController(bsio);
		Player player = new Player(bsio, controller);
		player.setPosition(new Vec2f(120+x,48+y));
		player.bboxUpdate();
		controller.addObject(player);
		
		controller.addObject(new ground(bsio, controller, 0+x, 300+y));
		controller.addObject(new ground(bsio, controller, 200+x, 300+y));
		//controller.addObject(new EnemyTest03(bsio, 250+x, 290+y));
		controller.addObject(new Archers(bsio, controller, 300+x, 290+y));
		//controller.addObject(new EnemyTest03(bsio, 350+x, 290+y));
		
		bsio.addObject(controller);
		bsio.addBackgroundObject(new Clock(bsio) {{setPosition(new Vec2f(40, 40));}});
		
		bsio.run();
	}
	
	private static void TestMap04(float x, float y) {
		BasicIO bsio = new BasicIO();
		BasicController controller = new BasicController(bsio);
		Player player = new Player(bsio, controller);
		player.setPosition(new Vec2f(120+x,48+y));
		player.bboxUpdate();
		controller.addObject(player);
		
		controller.addObject(new ground(bsio, controller, 0+x, 300+y));
		controller.addObject(new ground(bsio, controller, 250+x, 220+y));
		controller.addObject(new EnemyTest03(bsio, 180+x, 280+y));
		controller.addObject(new EnemyTest03(bsio, 400+x, 210+y));
		
		bsio.addObject(controller);
		bsio.addBackgroundObject(new Clock(bsio) {{setPosition(new Vec2f(40, 40));}});
		
		bsio.run();
	}
	
	private static void TestMap05(float x, float y) {
		BasicIO io = new BasicIO();
		BasicController control = new BasicController(io);
		Player player = new Player(io, control);
		player.setPosition(new Vec2f(120+x,48+y));
		player.bboxUpdate();
		control.addObject(player);
		
		control.addObject(new ground(io, control, 0+x, 300+y));
		control.addObject(new ground(io, control, 250+x, 240+y));
		control.addObject(new ground(io, control, 120+x, 140+y));
		control.addObject(new EnemyTest04(io, 320+x, 220+y));
		control.addObject(new EnemyTest05(io, control, 380+x, 220+y));
		control.addObject(new EnemyTest03(io, 440+x, 220+y));
		control.addObject(new EnemyTest03(io, 80+x, 280+y));
		control.addObject(new EnemyTest03(io, 160+x, 280+y));
		control.addObject(new EnemyTest05(io, control, 190+x, 280+y));
		
		io.addObject(control);
		io.addBackgroundObject(new Clock(io) {{setPosition(new Vec2f(40, 40));}});
		
		io.run();
	}
	
	private static void TestMap06(float x, float y) {
		BasicIO io = new BasicIO();
		BasicController control = new BasicController(io);
		Player player = new Player(io, control);
		player.setPosition(new Vec2f(120+x,48+y));
		player.bboxUpdate();
		control.addObject(player);
		
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
		
		io.addObject(control);
		io.addBackgroundObject(new Clock(io) {{setPosition(new Vec2f(40, 40));}});
		
		io.run();
	}
	
	private static void TestMap08(float x, float y) {
		BasicIO io = new BasicIO();
		BasicController control = new BasicController(io);
		Player player = new Player(io, control);
		player.setPosition(new Vec2f(120+x,48+y));
		player.bboxUpdate();
		control.addObject(player);
		
		control.addObject(new ground(io, control, 0+x, 300+y));
		control.addObject(new ground(io, control, 250+x, 220+y));
		control.addObject(new EnemyTest05(io, control, 80+x, 280+y));
		control.addObject(new EnemyTest06(io, control, 140+x, 280+y));
		control.addObject(new EnemyTest06(io, control, 180+x, 280+y));
		control.addObject(new EnemyTest05(io, control, 300+x, 210+y));
		control.addObject(new EnemyTest06(io, control, 360+x, 210+y));
		control.addObject(new EnemyTest06(io, control, 400+x, 210+y));
		
		io.addObject(control);
		io.addBackgroundObject(new Clock(io) {{setPosition(new Vec2f(40, 40));}});
		
		io.run();
	}
}
