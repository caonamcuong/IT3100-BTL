package enemy.test01;

import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;

import bIO.BasicIO;
import bIO.BasicNumber;
import bIO.BasicObject;
import bIO.BasicSprite;
import bIO.BasicTimer;
import bIO.BoundingBox;
import bIO.Vec2f;
import enemy.EnemyPlayerTest;
import enemy.EnemyWallTest;

public class EnemyTest01 extends BasicObject {
	private static final TreeMap<String, BasicSprite> state_machine = new TreeMap<String, BasicSprite>() {{
		put("idle", new BasicSprite (
				"./src/goomba.png",
				Arrays.asList(3),
				Arrays.asList(155),
				Arrays.asList(19),
				Arrays.asList(19)
			));
		put("detect_player", new BasicSprite (
				"./src/goomba.png",
				Arrays.asList(3),
				Arrays.asList(155),
				Arrays.asList(19),
				Arrays.asList(19)
			));
		put("attack", new BasicSprite (
				"./src/goomba.png",
				Arrays.asList(3),
				Arrays.asList(155),
				Arrays.asList(19),
				Arrays.asList(19)
			));
	}};
	@Override
	public BasicSprite getSprite() { return state_machine.get(getState()); }
	private Vec2f velocity;
	private BasicTimer attack_timer;
	private boolean attack_flag;
	
	public EnemyTest01(BasicIO io) {
		super(io);
		setState("idle");
		setPosition(new Vec2f(0,0));
		setBBox(new BoundingBox(19, 19));
		setBBoxOrigin(new Vec2f(0.5f, 1.0f));
		setBBoxDrawFlag(true);
		setSpriteOrigin(new Vec2f(0.5f, 1.0f));
		setScale(1f);
		
		velocity = new Vec2f(0,0);
		attack_timer = new BasicTimer(io.getStepPerSec() * 2, new Runnable() {
			public void run() {
				attack_flag = true;
			}
		});
		attack_timer.stop();
		attack_flag = false;
	}
	
	@Override
	public void fixedUpdate() {
		velocity = velocity.add(new BasicNumber(0), new BasicNumber(0.2f));
		//Vec2f mov_step = velocity.mul(new BasicNumber(getIO().getUnitStep(1f)));
		
		List<BasicObject> o = getIO().quadQueryObject(new BoundingBox(
				new BasicNumber(128), new BasicNumber(64),
				getBBox().getX().sub(new BasicNumber(64)),
				getBBox().getY().sub(new BasicNumber(32))
			));
		
		List<BasicObject> wallList = o.stream().filter(i -> i instanceof EnemyWallTest).toList();
		List<BasicObject> playerList = o.stream().filter(i -> i instanceof EnemyPlayerTest).toList();
		
		if (getState().equals("idle")) {
			if (!playerList.isEmpty()) {
				BasicObject player = playerList.get(0);
				Vec2f dv = player.getPosition().sub(getPosition());
				float dx = Math.abs(dv.getX().toFloat());
				float dy = Math.abs(dv.getY().toFloat());
				
				if (dx <= 256f && dy <= 64f) {
					setState("detect_player");
					attack_timer.setup();
				}
			}
		}
		else if (getState().equals("detect_player")) {
			if (!playerList.isEmpty()) {
				BasicObject player = playerList.get(0);
				Vec2f dv = player.getPosition().sub(getPosition());
				float dx = Math.abs(dv.getX().toFloat());
				float dy = Math.abs(dv.getY().toFloat());
				
				if (dx > 256f || dy > 64f) {
					setState("idle");
					attack_timer.stop();
				}
				else if (dx > 32f) {
					if (dv.getX().toFloat() < 0) {
						velocity.setX(new BasicNumber(-1000));
						setHFlip(false);
						attack_timer.setup();
					}
					else if (dv.getX().toFloat() > 0) {
						velocity.setX(new BasicNumber(1000));
						setHFlip(true);
						attack_timer.setup();
					}
				}
				else {
					velocity.setX(new BasicNumber(0));
					attack_timer.run();
				}
			}
			else {
				velocity.setX(new BasicNumber(0));
				setState("idle");
				attack_timer.stop();
			}
		}
		
		if (attack_flag) {
			attack_timer.stop();
			Vec2f pos = getPosition();
			getIO().addObject(new EnemyTest01Hitbox(getIO()) {{
				setPosition(pos);
			}});
			attack_flag = false;
		}
		
		Vec2f mov_step = velocity.mul(new BasicNumber(getIO().getUnitStep(1f)));
		BasicNumber dx = mov_step.getX();
		for (int i = 0; i < 32; ++i) {
			boolean do_collide = false;
			BasicObject collision_o = null;
			for (BasicObject w: wallList) {
				if (getBBox().add(dx, new BasicNumber(0)).collideWith(w.getBBox())) {
					do_collide=true;
					collision_o = w;
					break;
				}
			}
			if (do_collide) {
				dx = dx.div(2f);
				if (i == 31) {
					if (dx.gt(new BasicNumber(0f))) dx = collision_o.getBBox().getX().sub(getBBox().getX2());
					else dx = collision_o.getBBox().getX2().sub(getBBox().getX());
				}
			}
			else break;
		}
		if (Math.abs(dx.toFloat()) < 0.0001f) dx = new BasicNumber(0);
		mov_step.setX(dx);
		BasicNumber dy = mov_step.getY();
		for (int i = 0; i < 32; ++i) {
			boolean do_collide = false;
			BasicObject collision_o = null;
			for (BasicObject w: wallList) {
				if (getBBox().add(new BasicNumber(0), dy).collideWith(w.getBBox())) {
					do_collide=true;
					collision_o = w;
					break;
				}
			}
			if (do_collide) {
				velocity.setY(new BasicNumber(0));
				dy = dy.div(2f);
				if (i == 31) {
					if (dy.toFloat() > 0f) dy = collision_o.getBBox().getY().sub(getBBox().getY2());
					else dy = collision_o.getBBox().getY2().sub(getBBox().getY());
				}
			}
			else break;
		}
		if (Math.abs(dy.toFloat()) < 0.0001f) dy = new BasicNumber(0);
		mov_step.setY(dy);
		
		Vec2f old_pos = getPosition();
		setPosition(getPosition().add(mov_step));
		getIO().quadUpdateObject(this, old_pos);
	}

}
