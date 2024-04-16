package player;

import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;

import bIO.BasicIO;
import bIO.BasicNumber;
import bIO.BasicObject;
import bIO.BasicSprite;
import bIO.BasicTimer;
import bIO.BasicWall;
import bIO.BoundingBox;
import bIO.Vec2f;
import enemy.EnemyHurtBox;

public class Player extends BasicObject {
	private static final TreeMap<String, BasicSprite> state_machine = new TreeMap<String, BasicSprite>() {{
		put("idle", new BasicSprite (
			"bin/player.png",
			Arrays.asList(0,96,96*2,96*3,96*4,96*5),
			Arrays.asList(39,39,39,39,39,39),
			Arrays.asList(96,96,96,96,96,96),
			Arrays.asList(39,39,39,39,39,39)
		));
		put ("jump", new BasicSprite (
			"bin/player.png",
			Arrays.asList(96*6),
			Arrays.asList(39),
			Arrays.asList(96),
			Arrays.asList(39)
		));
		put("attack", new BasicSprite (
			"bin/player.png",
			Arrays.asList(0,96,96*2,96*3,96*4,96*5,96*6,96*7,96*8),
			Arrays.asList(0,0,0,0,0,0,0,0,0),
			Arrays.asList(96,96,96,96,96,96,96,96,96),
			Arrays.asList(39,39,39,39,39,39,39,39,39)
		));
		put("hurt", new BasicSprite (
			"bin/player.png",
			Arrays.asList(96*7),
			Arrays.asList(39),
			Arrays.asList(96),
			Arrays.asList(39)
		));
	}};
	@Override
	public BasicSprite getSprite() { return state_machine.get(getState()); }
	
	private static final float mov_speed = 800f;
	private static final float jmp_speed = 400f;
	private static final float float_speed = 0.63f;
	private static final long attack_time = BasicIO.getStepPerSec();
	private static final long attack_delay = (long)((double)BasicIO.getStepPerSec() / 9. * 4);
	private static final long attack_relay = (long)((double)BasicIO.getStepPerSec() / 9. * 5);
	private static final long hurt_time = BasicIO.getStepPerSec();
	
	private static final int KEY_UP = KeyEvent.VK_SPACE;
	private static final int KEY_RIGHT = KeyEvent.VK_RIGHT;
	private static final int KEY_LEFT = KeyEvent.VK_LEFT;
	private static final int KEY_ATTACK = KeyEvent.VK_A;
	
	private int direction; //0 : right, 1 : left
	
	private Vec2f velocity;
	
	private boolean can_jump;
	private BasicTimer coyote_timer;
	
	private boolean holding_jump;
	private BasicTimer holding_jump_timer;
	
	private boolean can_attack;
	private BasicTimer can_attack_timer;
	
	private BasicTimer attack_countdown_timer;
	private BasicTimer attack_spawn_timer;
	
	private boolean hurt_knockback;
	private BasicTimer hurt_timer;
	
	public Player(BasicIO io) {
		super(io);
		setState("idle");
		setPosition(new Vec2f(0,0));
		setBBox(new BoundingBox(16, 32));
		setBBoxOrigin(new Vec2f(0.5f, 1.0f));
		setBBoxDrawFlag(io.getDebug());
		setSpriteOrigin(new Vec2f(0.5f, 1.0f));
		setScale(1f);
		setSpritePlayback(9);
		
		velocity = new Vec2f(0,0);
		can_jump = true;
		direction = 0;
		hurt_knockback = false;

		coyote_timer = new BasicTimer(io.getStepPerSec() / 5, new Runnable() {
			public void run() {
				can_jump = false;
			}
		});
		
		holding_jump = false;
		holding_jump_timer = new BasicTimer(io.getStepPerSec() / 5, new Runnable() {
			public void run() {
				holding_jump = false;
			}
		});
		
		can_attack = true;
		can_attack_timer = new BasicTimer(io.getStepPerSec(), new Runnable() {
			public void run() {
				can_attack = true;
			}
		});
		
		Player aPlayer = this;
		attack_countdown_timer = new BasicTimer(attack_time, new Runnable() {
			public void run() {
				aPlayer.setState("idle");
			}
		});
		attack_spawn_timer = new BasicTimer(attack_delay, new Runnable() {
			public void run() {
				aPlayer.getIO().addObject(
					new PlayerHitbox(getIO(), 
						aPlayer.getPosition().add(new Vec2f(direction==0?32f:-32f, -16f)),
					new Vec2f(48, 32),
					aPlayer.attack_relay
				));
			}
		});
		
		hurt_timer = new BasicTimer(hurt_time, new Runnable() {
			public void run() {
				aPlayer.setState("jump");
			}
		});
	}
	
	private void updateXDirection() {
		int movx = (getIO().isPressing(KEY_RIGHT)?1:0)
				- (getIO().isPressing(KEY_LEFT)?1:0);
		if (movx > 0) {
			direction = 0;
			setHFlip(false);
		}
		else if (movx < 0) {
			direction = 1;
			setHFlip(true);
		}
	}
	
	private void updateXMovement() {
		updateXDirection();
		int movx = (getIO().isPressing(KEY_RIGHT)?1:0)
				- (getIO().isPressing(KEY_LEFT)?1:0);
		velocity.setX(new BasicNumber(movx*mov_speed));
	}
	
	private boolean updateYMovement() {
		boolean press_jump = false;
		int movy = 0;
		if (getIO().isPressing(KEY_UP)) {
			if (can_jump) {
				movy = 1;
				can_jump = false;
				holding_jump = true;
				holding_jump_timer.setup();
				press_jump = true;
			}
			else if (holding_jump) {
				velocity = velocity.add(new BasicNumber(0), new BasicNumber(-float_speed));
			}
		}
		else {
			if (holding_jump) {
				holding_jump = false;
				holding_jump_timer.stop();
			}
		}
		if (movy !=0) velocity.setY(new BasicNumber(-movy*jmp_speed));
		
		return press_jump;
	}
	private boolean updateAttack() {
		boolean press_attack = false;
		if (can_attack && getIO().isPressing(KEY_ATTACK)) {
			press_attack=true;
			attack_countdown_timer.setup();
			attack_spawn_timer.setup();
			can_attack_timer.setup();
			can_attack = false;
		}
		return press_attack;
	}
	private boolean updateEnemy() {
		List<BasicObject> o = getIO().quadQueryObject(new BoundingBox(
				new BasicNumber(256), new BasicNumber(128),
				getBBox().getX().sub(new BasicNumber(128)),
				getBBox().getY().sub(new BasicNumber(64))
			));
		o.removeIf(p -> !(p instanceof EnemyHurtBox));
		
		for (BasicObject en: o) {
			if (en.getBBox().collideWith(getBBox())) {
				return true;
			}
		}
		
		return false;
	}
	private void updateHurtYMovement() {
		hurt_knockback = true;
		velocity.setY(new BasicNumber(-jmp_speed));
	}
	private void updateHurtXMovement() {
		if (hurt_knockback) {
			int movx = direction==0?-1:1;
			velocity.setX(new BasicNumber(movx*mov_speed*0.5));
		}
	}
	
	@Override
	public void fixedUpdate() {
		Vec2f mov_step = new Vec2f(0,0);
		velocity.setX(new BasicNumber(0));
		velocity = velocity.add(new BasicNumber(0), new BasicNumber(0.2f));
		
		if (getState() == "idle") {
			updateXDirection();
			
			if (updateYMovement()) {
				setState("jump");
			}
			else if (updateAttack()) {
				setState("attack");
			}
			else if (updateEnemy()) {
				setState("hurt");
				updateHurtYMovement();
				hurt_timer.setup();
			}
		}
		else if (getState() == "jump") {
			updateXMovement();
			updateYMovement();
			
			if (updateEnemy()) {
				setState("hurt");
				updateHurtYMovement();
				hurt_timer.setup();
			}
		}
		else if (getState() == "attack") {
			attack_countdown_timer.run();
			attack_spawn_timer.run();
			
			if (updateEnemy()) {
				setState("hurt");
				updateHurtYMovement();
				hurt_timer.setup();
			}
		}
		else if (getState() == "hurt") {
			hurt_timer.run();
			updateHurtXMovement();
		}
		
		List<BasicObject> o = getIO().quadQueryObject(new BoundingBox(
				new BasicNumber(128), new BasicNumber(128),
				getBBox().getX().sub(new BasicNumber(64)),
				getBBox().getY().sub(new BasicNumber(64))
			));
		o.removeIf(p -> !(p instanceof BasicWall));
			
		mov_step = velocity.mul(new BasicNumber(getIO().getUnitStep(1f)));
			
		BasicNumber dx = mov_step.getX();
		for (int i = 0; i < 32; ++i) {
			boolean do_collide = false;
			BasicObject collision_o = null;
			for (BasicObject w: o) {
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
			for (BasicObject w: o) {
				if (getBBox().add(new BasicNumber(0), dy).collideWith(w.getBBox())) {
					do_collide=true;
					collision_o = w;
					break;
				}
			}
			if (do_collide) {
				velocity.setY(new BasicNumber(0));
				if (dy.toFloat() > 0f) { // collide with wall underneath
					can_jump = true;
					coyote_timer.setup();
					hurt_knockback = false;
					if (getState() == "jump") setState("idle");
				}
				dy = dy.div(2f);
				
				//hacky-ish
				if (i == 31) {
					if (dy.toFloat() > 0f) dy = collision_o.getBBox().getY().sub(getBBox().getY2());
					else dy = collision_o.getBBox().getY2().sub(getBBox().getY());
				}
			}
			else break;
		}
		if (Math.abs(dy.toFloat()) < 0.0001f) dy = new BasicNumber(0);
		mov_step.setY(dy);
		
		coyote_timer.run();
		holding_jump_timer.run();
		can_attack_timer.run();
		
		Vec2f old_pos = getPosition();
		setPosition(getPosition().add(mov_step));
		getIO().quadUpdateObject(this, old_pos);
	}
	@Override
	public void postUpdate() { 
		//if (velocity.getX().toFloat() != 0) {
		//	if (!getState().equals("run"))
		//		setState("run");
		//}
		//else {
		//	if (!getState().equals("idle"))
		//		setState("idle");
		//}
		//if (velocity.getX().gt(0)) {
		//	direction = 0;
		//	setHFlip(false);
		//}
		//else if (velocity.getX().lt(0)) {
		//	direction = 1;
		//	setHFlip(true);
		//}
		//Vec2f old_pos = getPosition();
		//setPosition(getPosition().sub(new Vec2f(0.005f, 0)));
		//getIO().quadUpdateObject(this, old_pos);
		bboxUpdate();
		
	}
	
}
