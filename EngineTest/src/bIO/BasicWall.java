package bIO;

import java.util.TreeMap;

public class BasicWall extends BasicObject {
	private static final TreeMap<String, BasicSprite> state_machine = new TreeMap<String, BasicSprite>() {{
		put("idle", null);
	}};
	@Override
	public BasicSprite getSprite() { return state_machine.get(getState()); }
	
	public BasicWall(BasicIO io) {
		super(io);
		setState("idle");
		setPosition(new Vec2f(0,0));
		setBBox(new BoundingBox(32, 32));
		setBBoxOrigin(new Vec2f(0, 0));
		setBBoxDrawFlag(getIO().getDebug());
	}
	
	@Override 
	public void postUpdate() {
		//Vec2f old_pos = getPosition();
		//setPosition(getPosition().sub(new Vec2f(0.005f, 0)));
		//if (getPosition().getX().lt(getBBox().getWidth().mul(2).negate())) {
		//	setPosition(getPosition().add(new Vec2f(640, 0)));
		//}
		//bboxUpdate();
		//getIO().quadUpdateObject(this, old_pos);
		
	}
	
}
