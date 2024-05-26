package enemy.test02;

import java.util.Arrays;
import java.util.TreeMap;

import bIO.BasicIO;
import bIO.BasicObject;
import bIO.BasicSprite;
import bIO.BasicTimer;
import bIO.BoundingBox;
import bIO.Vec2f;

public class EnemyTest02V3 extends BasicObject {
	private static final TreeMap<String, BasicSprite> state_machine = new TreeMap<String, BasicSprite>() {{
		put("idle", new BasicSprite (
				"goomba.png",
				Arrays.asList(3),
				Arrays.asList(155),
				Arrays.asList(19),
				Arrays.asList(19)
			));
	}};
	@Override
	public BasicSprite getSprite() { return state_machine.get(getState()); }
	
	private BasicTimer attack_timer;
	private boolean attack_flag;
	
	public EnemyTest02V3(BasicIO io) {
		super(io);
		setState("idle");
		setPosition(new Vec2f(0,0));
		setBBox(new BoundingBox(19, 19));
		setBBoxOrigin(new Vec2f(0.5f, 1.0f));
		setBBoxDrawFlag(true);
		setSpriteOrigin(new Vec2f(0.5f, 1.0f));
		setScale(1f);
		
		attack_timer = new BasicTimer(io.getStepPerSec() * 5, new Runnable() {
			public void run() {
				attack_flag = true;
			}
		});
		attack_timer.setup();
		attack_flag=false;
	}
	
	@Override
	public void fixedUpdate() {
		attack_timer.run();
		if (attack_flag) {
			attack_timer.stop();
			Vec2f pos = getPosition();
			boolean flip = getHFlip();
			getIO().addObject(new EnemyTest02Bullet(getIO()) {{
				setPosition(pos);
				setVelocity(flip?1000:-1000, 0);
			}});
			attack_flag = false;
			attack_timer.setup();
		}
	}
		
	
}
