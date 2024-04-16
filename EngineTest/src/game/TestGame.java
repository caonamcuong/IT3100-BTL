package game;

import bIO.BasicBackground;
import bIO.BasicIO;
import bIO.BasicNumber;
import bIO.BasicPlayer;
import bIO.BasicWall;
import bIO.BoundingBox;
import bIO.Vec2f;
import enemy.EnemyHurtBox;
import player.Player;

public class TestGame {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		// TEST
		
		// di chuyen bang mui ten trai-phai
		// bam space de nhay
		
		BasicIO bsio = new BasicIO();
		bsio.addObject(new Player(bsio) {{setPosition(new Vec2f(120f,48));}});
		for (int i = 0; i < 20; ++i) {
			for (int j = 6; j <= 9; ++j) {
			BasicNumber xx = new BasicNumber(i * 32f);
			BasicNumber yy = new BasicNumber(j * 32f);
			bsio.addObject(new BasicWall(bsio) {{setPosition(new Vec2f(xx, yy)); bboxUpdate();}});
			}
		}
		bsio.addObject(new BasicWall(bsio) {{setPosition(new Vec2f(new BasicNumber(4*32), new BasicNumber(5*32))); bboxUpdate();}});
		bsio.addObject(new BasicWall(bsio) {{setPosition(new Vec2f(new BasicNumber(5*32), new BasicNumber(5*32))); bboxUpdate();}});
		bsio.addObject(new BasicWall(bsio) {{setPosition(new Vec2f(new BasicNumber(7*32), new BasicNumber(3*32))); bboxUpdate();}});
		bsio.addObject(new BasicWall(bsio) {{setPosition(new Vec2f(new BasicNumber(9*32), new BasicNumber(5*32))); bboxUpdate();}});
		bsio.addObject(new BasicWall(bsio) {{setPosition(new Vec2f(new BasicNumber(11*32), new BasicNumber(5*32))); bboxUpdate();}});
		bsio.addObject(new BasicWall(bsio) {{setPosition(new Vec2f(new BasicNumber(11*32), new BasicNumber(4*32))); bboxUpdate();}});
		bsio.addObject(new BasicWall(bsio) {{setPosition(new Vec2f(new BasicNumber(3*32), new BasicNumber(5*32))); bboxUpdate();}});
		bsio.addObject(new BasicWall(bsio) {{setPosition(new Vec2f(new BasicNumber(3*32), new BasicNumber(4*32))); bboxUpdate();}});
		bsio.addObject(new BasicWall(bsio) {{setPosition(new Vec2f(new BasicNumber(1*32), new BasicNumber(5*32))); bboxUpdate();}});
		
		bsio.addObject(new EnemyHurtBox(bsio) {{
			setPosition(new Vec2f(32f*14, 32f*4));
			setBBox(new BoundingBox(new Vec2f(32f, 32f)));
			setBBoxOrigin(new Vec2f(0, 0));
			setBBoxDrawFlag(getIO().getDebug());
			bboxUpdate();
		}});
		
		bsio.addBackgroundObject(new BasicBackground(bsio) {{setPosition(new Vec2f(0, 0));}});
		bsio.addBackgroundObject(new BasicBackground(bsio) {{setPosition(new Vec2f(607, 0));}});
		
		bsio.run();
	}
}
