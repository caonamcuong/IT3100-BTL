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
import map.ground;
import map.map;
import player.Player;
import ui.Clock;

public class FrameTest {
	public static void main(String[] args) {
		//TestMap01(100, -120);
		//TestMap02(0,0);
		//TestMap03(0,0);
		//TestMap04(0,0);
		//TestMap05(0,0);
		//TestMap06(0,0);
		TestMap08(0,0);
	}
	
	private static void TestMap01(float x, float y) {
		BasicIO bsio = new BasicIO();
		BasicController controller = new BasicController(bsio);
		Player player = new Player(bsio, controller);
		player.setPosition(new Vec2f(120+x,48+y));
		player.bboxUpdate();
		controller.addObject(player);
		
		controller.addObject(new ground(bsio, controller, 0+x, 300+y));
		controller.addObject(new ground(bsio, controller, 250+x, 220+y));
		controller.addObject(new EnemyTest03(bsio, 20+x, 280+y));
		controller.addObject(new EnemyTest03(bsio, 180+x, 280+y));
		controller.addObject(new EnemyTest03(bsio, 300+x, 210+y));
		controller.addObject(new EnemyTest03(bsio, 360+x, 210+y));
		controller.addObject(new EnemyTest03(bsio, 400+x, 210+y));
		
		bsio.addObject(controller);
		bsio.addBackgroundObject(new Clock(bsio) {{setPosition(new Vec2f(40, 40));}});
		
		bsio.run();
	}
	
	private static void TestMap02(float x, float y) {
		BasicIO bsio = new BasicIO();
		BasicController controller = new BasicController(bsio);
		Player player = new Player(bsio, controller);
		player.setPosition(new Vec2f(120+x,48+y));
		player.bboxUpdate();
		controller.addObject(player);
		
		controller.addObject(new ground(bsio, controller, 0+x, 300+y));
		controller.addObject(new ground(bsio, controller, 200+x, 300+y));
		controller.addObject(new EnemyTest03(bsio, 50+x, 290+y));
		controller.addObject(new EnemyTest03(bsio, 100+x, 290+y));
		controller.addObject(new EnemyTest03(bsio, 150+x, 290+y));
		controller.addObject(new EnemyTest03(bsio, 200+x, 290+y));
		controller.addObject(new EnemyTest03(bsio, 250+x, 290+y));
		controller.addObject(new EnemyTest03(bsio, 300+x, 290+y));
		
		bsio.addObject(controller);
		bsio.addBackgroundObject(new Clock(bsio) {{setPosition(new Vec2f(40, 40));}});
		
		bsio.run();
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
		controller.addObject(new EnemyTest03(bsio, 250+x, 290+y));
		controller.addObject(new EnemyTest03(bsio, 300+x, 290+y));
		controller.addObject(new EnemyTest03(bsio, 350+x, 290+y));
		
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
		BasicIO bsio = new BasicIO();
		BasicController controller = new BasicController(bsio);
		Player player = new Player(bsio, controller);
		player.setPosition(new Vec2f(120+x,48+y));
		player.bboxUpdate();
		controller.addObject(player);
		
		controller.addObject(new ground(bsio, controller, 0+x, 300+y));
		controller.addObject(new ground(bsio, controller, 250+x, 240+y));
		controller.addObject(new ground(bsio, controller, 120+x, 140+y));
		controller.addObject(new EnemyTest06(bsio, controller, 290+x, 220+y));
		controller.addObject(new EnemyTest06(bsio, controller, 340+x, 220+y));
		controller.addObject(new EnemyTest06(bsio, controller, 390+x, 220+y));
		
		bsio.addObject(controller);
		bsio.addBackgroundObject(new Clock(bsio) {{setPosition(new Vec2f(40, 40));}});
		
		bsio.run();
	}
	
	private static void TestMap06(float x, float y) {
		BasicIO bsio = new BasicIO();
		BasicController controller = new BasicController(bsio);
		Player player = new Player(bsio, controller);
		player.setPosition(new Vec2f(120+x,48+y));
		player.bboxUpdate();
		controller.addObject(player);
		
		controller.addObject(new ground(bsio, controller, 0+x, 250+y));
		controller.addObject(new ground(bsio, controller, 250+x, 300+y));
		controller.addObject(new ground(bsio, controller, 220+x, 170+y));
		
		bsio.addObject(controller);
		bsio.addBackgroundObject(new Clock(bsio) {{setPosition(new Vec2f(40, 40));}});
		
		bsio.run();
	}
	
	private static void TestMap08(float x, float y) {
		BasicIO bsio = new BasicIO();
		BasicController controller = new BasicController(bsio);
		Player player = new Player(bsio, controller);
		player.setPosition(new Vec2f(120+x,48+y));
		player.bboxUpdate();
		controller.addObject(player);
		
		controller.addObject(new ground(bsio, controller, 0+x, 300+y));
		controller.addObject(new ground(bsio, controller, 250+x, 220+y));
		controller.addObject(new EnemyTest03(bsio, 20+x, 280+y));
		controller.addObject(new EnemyTest04(bsio, controller, 180+x, 280+y));
		controller.addObject(new EnemyTest03(bsio, 300+x, 210+y));
		controller.addObject(new EnemyTest04(bsio, controller, 360+x, 210+y));
		controller.addObject(new EnemyTest04(bsio, controller, 400+x, 210+y));
		
		bsio.addObject(controller);
		bsio.addBackgroundObject(new Clock(bsio) {{setPosition(new Vec2f(40, 40));}});
		
		bsio.run();
	}
}
