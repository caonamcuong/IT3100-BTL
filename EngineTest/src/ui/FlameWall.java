package ui;

import java.util.Arrays;
import java.util.TreeMap;

import bIO.BasicIO;
import bIO.BasicNumber;
import bIO.BasicObject;
import bIO.BasicSprite;
import bIO.BoundingBox;
import bIO.Vec2f;

public class FlameWall extends BasicObject {
	private static final TreeMap<String, BasicSprite> state_machine = new TreeMap<String, BasicSprite>() {{
		put("idle", new BasicSprite (
				"src/flamewall.png",
				Arrays.asList(0,160,320),
				Arrays.asList(30,30,30),
				Arrays.asList(140,140,140),
				Arrays.asList(360,360,360)
			));
		//put("idle", null);
	}};
	@Override
	public BasicSprite getSprite() { return state_machine.get(getState()); }
	public FlameWall(BasicIO io) {
		super(io);
		setState("idle");
		setPosition(new Vec2f(-100,0));
		setBBoxDrawFlag(false);
		setSpritePlayback(6);
	}
	
	@Override 
	public void fixedUpdate() {}
	@Override
	public void postUpdate() {}
}
