package bIO;

import java.util.TreeMap;

public class BasicWall extends BasicObject {
	private static final TreeMap<String, BasicSprite> state_machine = new TreeMap<String, BasicSprite>() {{
		put("idle", null);
	}};
	private static final String object_name = "TestWall";
	@Override
	public String getName() { return object_name; }
	@Override
	public BasicSprite getSprite() { return state_machine.get(getState()); }
	
	BasicWall(BasicIO io) {
		super(io);
		setState("idle");
		setPosition(new Vec2f(0,0));
		setBBox(new BoundingBox(32, 32));
		setBBoxOrigin(new Vec2f(0, 0));
		setBBoxDrawFlag(true);
		
	}
	
}
