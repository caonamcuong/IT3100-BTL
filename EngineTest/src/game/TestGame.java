package game;

import bIO.BasicBackground;
import bIO.BasicIO;
import bIO.BasicNumber;
import bIO.BasicPlayer;
import bIO.BasicWall;
import bIO.Vec2f;
import controller.SlideController;
import enemy.test03.EnemyController;
import enemy.test03.EnemyTest03;
import map.map;
import player.Player;
import ui.Clock;

public class TestGame {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		// TEST
		
		// di chuyen bang mui ten trai-phai
		// bam space de nhay
		
		BasicIO bsio = new BasicIO();
		SlideController controller = new SlideController(bsio);
		Player player = new Player(bsio, controller);
		player.setPosition(new Vec2f(120,48));
		player.bboxUpdate();
		controller.addMainPlayer(player);
		for (int i = 0; i < 20; ++i) {
			if (i == 2) continue;
		  	for (int j = 6; j <= 9; ++j) {
			BasicNumber xx = new BasicNumber(i * 32f);
			BasicNumber yy = new BasicNumber(j * 32f);
			controller.addObject(new BasicWall(bsio) {{setPosition(new Vec2f(xx, yy)); bboxUpdate();}});
			}
		}
		controller.addObject(new BasicWall(bsio) {{setPosition(new Vec2f(new BasicNumber(4*32), new BasicNumber(5*32))); bboxUpdate();}});
		controller.addObject(new BasicWall(bsio) {{setPosition(new Vec2f(new BasicNumber(5*32), new BasicNumber(5*32))); bboxUpdate();}});
		controller.addObject(new BasicWall(bsio) {{setPosition(new Vec2f(new BasicNumber(7*32), new BasicNumber(3*32))); bboxUpdate();}});
		controller.addObject(new BasicWall(bsio) {{setPosition(new Vec2f(new BasicNumber(9*32), new BasicNumber(5*32))); bboxUpdate();}});
		controller.addObject(new BasicWall(bsio) {{setPosition(new Vec2f(new BasicNumber(11*32), new BasicNumber(5*32))); bboxUpdate();}});
		controller.addObject(new BasicWall(bsio) {{setPosition(new Vec2f(new BasicNumber(11*32), new BasicNumber(4*32))); bboxUpdate();}});
		controller.addObject(new BasicWall(bsio) {{setPosition(new Vec2f(new BasicNumber(3*32), new BasicNumber(5*32))); bboxUpdate();}});
		controller.addObject(new BasicWall(bsio) {{setPosition(new Vec2f(new BasicNumber(3*32), new BasicNumber(4*32))); bboxUpdate();}});
	   	controller.addObject(new BasicWall(bsio) {{setPosition(new Vec2f(new BasicNumber(1*32), new BasicNumber(5*32))); bboxUpdate();}});
		
		
		bsio.addObject(controller);
		bsio.addBackgroundObject(new map(bsio, controller, bsio.getStepPerSec() * 2));
		//bsio.addBackgroundObject(new EnemyController(bsio, controller, new Vec2f(32f*14, 32f*2)));
		
		//bsio.addBackgroundObject(new BasicBackground(bsio) {{setPosition(new Vec2f(0, 0));}});
		//bsio.addBackgroundObject(new BasicBackground(bsio) {{setPosition(new Vec2f(607, 0));}});
		bsio.addBackgroundObject(new Clock(bsio) {{setPosition(new Vec2f(40, 40));}});
		
		bsio.run();
	}
}
