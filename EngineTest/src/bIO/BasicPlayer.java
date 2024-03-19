package bIO;

import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;

import java.awt.event.KeyEvent;

public class BasicPlayer extends BasicObject {
	private static final TreeMap<String, BasicSprite> state_machine = new TreeMap<String, BasicSprite>() {{
		put("idle", new BasicSprite (
			"./src/textureTest.png",
			Arrays.asList(0),
			Arrays.asList(8),
			Arrays.asList(16),
			Arrays.asList(16)
		));
		put("run", new BasicSprite (
			"./src/textureTest.png",
			Arrays.asList(20,38,56),
			Arrays.asList(8,8,8),
			Arrays.asList(16,16,16),
			Arrays.asList(16,16,16)
		));
	}};
	private static final String object_name = "TestPlayer";
	@Override
	public String getName() { return object_name; }
	@Override
	public BasicSprite getSprite() { return state_machine.get(getState()); }
	
	private Vec2f velocity;
	private Vec2f acceleration;
	private boolean can_jump;
	private boolean holding_jump;
	private BasicTimer holding_jump_timer;
	private BasicTimer coyote_timer;
	
	BasicPlayer(BasicIO io) {
		super(io);
		setState("idle");
		setPosition(new Vec2f(0,0));
		setBBox(new BoundingBox(8, 16));
		setBBoxOrigin(new Vec2f(0.5f, 1.0f));
		setBBoxDrawFlag(true);
		setSpriteOrigin(new Vec2f(0.5f, 1.0f));
		
		velocity = new Vec2f(0,0);
		acceleration = new Vec2f(0,0);
		can_jump = true;
		holding_jump = false;
		holding_jump_timer = new BasicTimer(1500, new Runnable() {
			public void run() {
				holding_jump = false;
			}
		});
		coyote_timer = new BasicTimer(2500, new Runnable() {
			public void run() {
				can_jump = false;
			}
		});
	}
	
	@Override
	public void fixedUpdate() {
		
		int movx = (getIO().isPressing(KeyEvent.VK_RIGHT)?1:0)
				- (getIO().isPressing(KeyEvent.VK_LEFT)?1:0);
		int movy = 0;
		if (getIO().isPressing(KeyEvent.VK_SPACE)) {
			if (can_jump) {
				movy = 1;
				can_jump = false;
				holding_jump = true;
				holding_jump_timer.setup();
			}
			else if (holding_jump) {
				velocity = velocity.add(0, -1f);
			}
		}
		else {
			if (holding_jump) {
				holding_jump = false;
				holding_jump_timer.stop();
			}
		}
		
		velocity.setX(Math.max(Math.min(movx*1000, 1000), -1000));
		if (movy !=0) velocity.setY(-movy*100);
		velocity = velocity.add(0, 0.2f);
		List<BasicObject> o = getIO().quadQueryObject(new BoundingBox(
			128, 128,
			getBBox().getX() - 64,
			getBBox().getY() - 64
		));
		o.removeIf(p -> !(p instanceof BasicWall));
		
		Vec2f mov_step = velocity.mul(getIO().getUnitStep(1f));
		float dy = mov_step.getY();
		for (int i = 0; i < 32; ++i) {
			boolean do_collide = false;
			BasicObject collision_o = null;
			for (BasicObject w: o) {
				if (getBBox().add(0, dy).collideWith(w.getBBox())) {
					do_collide=true;
					collision_o = w;
					break;
				}
			}
			if (do_collide) {
				if (dy > 0f) {
					velocity.setY(0);
					can_jump = true;
					coyote_timer.setup();
				}
				dy /= 2f;
				
				//hacky-ish
				if (i == 31) {
					if (dy > 0f) dy = collision_o.getBBox().getY() - getBBox().getY2();
					else dy = collision_o.getBBox().getY2() - getBBox().getY();
				}
			}
			else break;
			
			
		}
		coyote_timer.run();
		holding_jump_timer.run();
		
		mov_step.setY(dy);
		float dx = mov_step.getX();
		for (int i = 0; i < 32; ++i) {
			boolean do_collide = false;
			for (BasicObject w: o) {
				if (getBBox().add(dx, 0).collideWith(w.getBBox())) {
					do_collide=true;
					break;
				}
			}
			if (do_collide) dx /= 2f;
			else break;
		}
		mov_step.setX(dx);
		
		Vec2f old_pos = getPosition();
		setPosition(getPosition().add(mov_step));
		getIO().quadUpdateObject(this, old_pos);
		
		//if (getBBox().getY2() > 360) { // map height
		//	velocity.setY(-getBBox().getY2()+360);
		//	can_jump = true;
		//}
	}
	@Override
	public void postUpdate() { 
		if (velocity.getX() != 0) {
			if (!getState().equals("run"))
				setState("run");
		}
		else {
			if (!getState().equals("idle"))
				setState("idle");
		}
		bboxUpdate();
	}
	
}
