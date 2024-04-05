package enemy;

import bIO.BasicIO;
import bIO.Vec2f;
import enemy.test01.EnemyTest01;
import enemy.test02.EnemyTest02;
import enemy.test02.EnemyTest02V3;

public class EnemyGameTest {
	public static void main(String[] args) {
		BasicIO bsio = new BasicIO();
		EnemyPlayerTest pl = new EnemyPlayerTest(bsio) {{
			setPosition(new Vec2f(6 * 32f, 6 * 32f));
		}};
		bsio.addObject(pl);
		
		EnemyTest01 en01 = new EnemyTest01(bsio) {{
			setPosition(new Vec2f(8 * 32f, 4 * 32f));
		}};
		bsio.addObject(en01);
		EnemyTest02V3 en02 = new EnemyTest02V3(bsio) {{
			setPosition(new Vec2f(10 * 32f, 4 * 32f));
		}};
		bsio.addObject(en02);
		EnemyTest02 en03 = new EnemyTest02(bsio) {{
			setPosition(new Vec2f(12 * 32f, 4 * 32f));
		}};
		bsio.addObject(en03);
		
		for (int i = 0; i < 4; ++i) {
			float xx = (i+2) * 64f;
			bsio.addObject(new EnemyWallTest(bsio) {{
				setPosition(new Vec2f(xx, (8 * 32f)));
			}});
		}
		
		bsio.run();
	}
}
