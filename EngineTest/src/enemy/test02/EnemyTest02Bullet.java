package enemy.test02;

import java.util.TreeMap;

import bIO.BasicIO;
import bIO.BasicNumber;
import bIO.BasicObject;
import bIO.BasicSprite;
import bIO.BasicTimer;
import bIO.BoundingBox;
import bIO.Vec2f;
import enemy.EnemyHurtBox;

public class EnemyTest02Bullet extends EnemyHurtBox {
	private static final TreeMap<String, BasicSprite> state_machine = new TreeMap<String, BasicSprite>() {{
		put("idle", null);
	}};
	@Override
	public BasicSprite getSprite() { return state_machine.get(getState()); }
	
	private Vec2f velocity;
	
	public EnemyTest02Bullet(BasicIO io) {
		super(io);
		setState("idle");
		setPosition(new Vec2f(0,0));
		setBBox(new BoundingBox(16, 16));
		setBBoxOrigin(new Vec2f(0.5f, 0.5f));
		setBBoxDrawFlag(true);
		
		velocity = new Vec2f(0,0);
	}
	public void setVelocity(float a, float b) {
		velocity = new Vec2f(a,b);
	}
	
	@Override
	public void fixedUpdate() {
		Vec2f mov_step = velocity.mul(new BasicNumber(getIO().getUnitStep(1f)));
		Vec2f old_pos = getPosition();
		setPosition(getPosition().add(mov_step));
		getIO().quadUpdateObject(this, old_pos);
	}

	@Override
	public void postUpdate() {
		bboxUpdate();
		if (getPosition().getX().lt(new BasicNumber(-10))) {
			getIO().removeObject(this);
		}
		else if (getPosition().getX().gt(new BasicNumber(650))) {
			getIO().removeObject(this);
		}
	}
}
