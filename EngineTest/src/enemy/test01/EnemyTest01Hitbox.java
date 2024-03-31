package enemy.test01;

import java.util.TreeMap;

import bIO.BasicIO;
import bIO.BasicObject;
import bIO.BasicSprite;
import bIO.BasicTimer;
import bIO.BoundingBox;
import bIO.Vec2f;

public class EnemyTest01Hitbox extends BasicObject {
	private static final TreeMap<String, BasicSprite> state_machine = new TreeMap<String, BasicSprite>() {{
		put("idle", null);
	}};
	@Override
	public BasicSprite getSprite() { return state_machine.get(getState()); }
	private BasicTimer des_timer;
	private boolean des_flag;
	
	public EnemyTest01Hitbox(BasicIO io) {
		super(io);
		setState("idle");
		setPosition(new Vec2f(0,0));
		setBBox(new BoundingBox(64, 64));
		setBBoxOrigin(new Vec2f(0.5f, 0.5f));
		setBBoxDrawFlag(true);
		
		des_timer = new BasicTimer(io.getStepPerSec() / 3, new Runnable() {
			public void run() {
				des_flag = true;
			}
		});
		des_timer.setup();
		des_flag = false;
	}
	
	@Override
	public void fixedUpdate() {
		des_timer.run();
		if (des_flag == true) {
			des_timer.stop();
			getIO().removeObject(this);
		}
	}
}
