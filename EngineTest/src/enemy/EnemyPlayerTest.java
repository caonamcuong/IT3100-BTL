package enemy;

import java.awt.event.KeyEvent;
import java.util.List;
import java.util.TreeMap;

import bIO.BasicIO;
import bIO.BasicNumber;
import bIO.BasicObject;
import bIO.BasicSprite;
import bIO.BasicTimer;
import bIO.BoundingBox;
import bIO.Vec2f;

import enemy.test01.EnemyTest01Hitbox;

public class EnemyPlayerTest extends BasicObject {
	private static final TreeMap<String, BasicSprite> state_machine = new TreeMap<String, BasicSprite>() {{
		put("idle", null);
		put("gethit", null);
	}};
	@Override
	public BasicSprite getSprite() { return state_machine.get(getState()); }

	private Vec2f velocity;
	private boolean canhit;
	private BasicTimer canhit_timer;
	
	public EnemyPlayerTest(BasicIO io) {
		super(io);
		setState("idle");
		setPosition(new Vec2f(0,0));
		setBBox(new BoundingBox(16, 16));
		setBBoxOrigin(new Vec2f(0.5f, 0.5f));
		setBBoxDrawFlag(true);
		velocity = new Vec2f(0,0);
		
		canhit = true;
		canhit_timer = new BasicTimer(getIO().getStepPerSec() * 3, new Runnable() {
			public void run() {
				canhit = true;
			}
		});
	}
	
	@Override
	public void fixedUpdate() {
		if (getState().equals("idle")) {
			int movx = (getIO().isPressing(KeyEvent.VK_RIGHT)?1:0)
					- (getIO().isPressing(KeyEvent.VK_LEFT)?1:0);
			int movy = (getIO().isPressing(KeyEvent.VK_DOWN)?1:0)
					- (getIO().isPressing(KeyEvent.VK_UP)?1:0);
		
			velocity.setX(new BasicNumber(Math.max(Math.min(movx*2000, 2000), -2000)));
			velocity.setY(new BasicNumber(Math.max(Math.min(movy*2000, 2000), -2000)));
			
			Vec2f mov_step = velocity.mul(new BasicNumber(getIO().getUnitStep(1f)));
			Vec2f old_pos = getPosition();
			setPosition(getPosition().add(mov_step));
			getIO().quadUpdateObject(this, old_pos);
			
			List<BasicObject> o = getIO().quadQueryObject(new BoundingBox(
					new BasicNumber(256), new BasicNumber(256),
					getBBox().getX().sub(new BasicNumber(128)),
					getBBox().getY().sub(new BasicNumber(128))
				));
			List<BasicObject> enemyhitbox = o.stream().filter(i -> i instanceof EnemyTest01Hitbox).toList();
			
			canhit_timer.run();
			for (BasicObject hb : enemyhitbox) {
				if (getBBox().collideWith(hb.getBBox()) && canhit) {
					canhit = false;
					canhit_timer.setup();
					System.out.println("hitted");
				}
			}
		}
	}
	
	@Override
	public void postUpdate() {
		bboxUpdate();
	}
}
