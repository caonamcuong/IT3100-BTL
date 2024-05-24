package enemy.test03;

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
import enemy.EnemyPlayerTest;
import enemy.EnemyWallTest;
import player.Player;
import player.PlayerHitbox;

public class EnemyTest03 extends EnemyHurtBox {
	private static final TreeMap<String, BasicSprite> state_machine = new TreeMap<String, BasicSprite>() {{
		put("idle", new BasicSprite (
				"src/skull.png",
				Arrays.asList(0),
				Arrays.asList(24),
				Arrays.asList(20),
				Arrays.asList(24)
			));
		put("detecting", new BasicSprite (
				"src/skull.png",
				Arrays.asList(0),
				Arrays.asList(0),
				Arrays.asList(20),
				Arrays.asList(24)
			));
		put("attack", new BasicSprite (
				"src/skull.png",
				Arrays.asList(0),
				Arrays.asList(48),
				Arrays.asList(20),
				Arrays.asList(24)
			));
		put ("destroying", null);
		put ("destroyed", null);
	}};
	@Override
	public BasicSprite getSprite() { return state_machine.get(getState()); }
	
	private static final float detect_xrange = 150;
	private static final float detect_yrange = 32;
	private static final float grav_speed = 0.2f;
	private static final float mov_speed = 300f;
	private static final float jmp_speed = 1200f;
	private static final long detect_time = BasicIO.getStepPerSec();
	
	private Vec2f velocity;
	private int direction;
	private BasicTimer detect_timer;
	private boolean detect_flag;
	
	public EnemyTest03(BasicIO io) {
		super(io);
		setState("idle");
		setPosition(new Vec2f(0,0));
		setBBox(new BoundingBox(18, 20));
		setBBoxOrigin(new Vec2f(0.5f, 1.0f));
		setBBoxDrawFlag(getIO().getDebug());
		setSpriteOrigin(new Vec2f(0.5f, 1.0f));
		setScale(1f);
		
		
		velocity = new Vec2f(0,0);
		direction = 0;
		detect_timer = new BasicTimer(detect_time, new Runnable() {
			public void run() {
				detect_flag = true;
			}
		});
		detect_flag = false;
	}
	public EnemyTest03(BasicIO io, float x, float y) {
		this(io);
		setPosition(new Vec2f(x,y));
	}
	
	@Override
	public void fixedUpdate() {
		if (getState() == "destroying") {
			setState("destroyed");
			getIO().removeObject(this);
			return;
		}
		else if (getState() == "destroyed") {
			return;
		}
		
		if (getPosition().getX().toFloat() < -64f) {
			setState("destroying");
			return;
		}
		
		Vec2f mov_step = new Vec2f(0,0);
		velocity.setX(new BasicNumber(0));
		velocity = velocity.add(new BasicNumber(0), new BasicNumber(grav_speed));
		
		List<BasicObject> o = getIO().quadQueryObject(new BoundingBox(
				new BasicNumber(detect_xrange*2), new BasicNumber(detect_yrange*2),
				getBBox().getX().sub(new BasicNumber(detect_xrange)),
				getBBox().getY().sub(new BasicNumber(detect_yrange))
		));
		List<BasicObject> wallList = o.stream().filter(i -> i instanceof BasicWall).toList();
		List<BasicObject> playerList = o.stream().filter(i -> i instanceof Player).toList();
		List<BasicObject> playerHb = o.stream().filter(i -> i instanceof PlayerHitbox).toList();
		
		for (BasicObject pHb : playerHb) {
			if (pHb.getBBox().collideWith(getBBox())) {
				setState("destroying");
				return;
			}
		}
		
		if (getState() == "idle") {
			if (playerList.size() >= 1) {
				detect_timer.setup();
				setState("detecting");
			}
		}
		else if (getState() == "detecting") {
			if (playerList.size() == 0) {
				setState("idle");
			}
			else {
				BasicObject p = playerList.get(0);
				if (p.getPosition().sub(getPosition()).getX().lt(0)) direction = 1;
				else direction = 0;
				
				detect_timer.run();
				
				if (detect_flag) {
					getIO().playSound("src/smb_fireball.wav");
					detect_flag = false;
					velocity.setY(new BasicNumber(-jmp_speed));
					setState("attack");
				}
			}
		}
		else if (getState() == "attack") {
			velocity.setX(new BasicNumber(direction==0?mov_speed:-mov_speed));
		}
		
		mov_step = velocity.mul(new BasicNumber(getIO().getUnitStep(1f)));
		
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
				if (dy.toFloat() > 0f) { // collide with wall underneath
					if (getState() == "attack") setState("idle");
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
		
		Vec2f old_pos = getPosition();
		setPosition(getPosition().add(mov_step));
		getIO().quadUpdateObject(this, old_pos);
	}
	
	@Override
	public void postUpdate() {
		setHFlip(direction==0);
		bboxUpdate();
	}
}
