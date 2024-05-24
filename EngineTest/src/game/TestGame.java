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
import frame.FrameGenerator;
import map.map;
import player.Player;
import ui.Clock;
import ui.FlameWall;

public class TestGame {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		// TEST
		
		// di chuyen bang mui ten trai-phai
		// bam space de nhay
		
		BasicIO bsio = new BasicIO();
		SlideController controller = new SlideController(bsio);
		//Player player = new Player(bsio, controller);
		//player.setPosition(new Vec2f(120,48));
		//player.bboxUpdate();
		//controller.addMainPlayer(player);
		//for (int i = 0; i < 40; ++i) {
		//	if (i == 2) continue;
		//  	for (int j = 6; j <= 9; ++j) {
		  //	BasicNumber xx = new BasicNumber(i * 32f);
		//	BasicNumber yy = new BasicNumber(j * 32f);
		//	controller.addObject(new BasicWall(bsio) {{setPosition(new Vec2f(xx, yy)); bboxUpdate();}});
		//	}
		//}

		bsio.addBackgroundObject(new BasicBackground(bsio) {{setPosition(new Vec2f(0, 0));}});
		bsio.addBackgroundObject(new BasicBackground(bsio) {{setPosition(new Vec2f(640, 0));}});
		bsio.addObject(new FlameWall(bsio));
		
		bsio.addObject(controller);
		//bsio.addBackgroundObject(new FrameGenerator(bsio, controller, bsio.getStepPerSec() * 10));
		//bsio.addBackgroundObject(new map(bsio, controller, bsio.getStepPerSec() * 2));
		//bsio.addBackgroundObject(new EnemyController(bsio, controller, new Vec2f(32f*14, 32f*2)));
		//bsio.addBackgroundObject(new Clock(bsio) {{setPosition(new Vec2f(40, 40));}});
		
		bsio.run();
	}
}
