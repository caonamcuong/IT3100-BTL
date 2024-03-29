package bIO;

import java.util.Arrays;
import java.util.TreeMap;

public class BasicBackground extends BasicObject {
	private static final TreeMap<String, BasicSprite> state_machine = new TreeMap<String, BasicSprite>() {{
		put("idle", new BasicSprite (
				"./src/bg.png",
				Arrays.asList(0),
				Arrays.asList(0),
				Arrays.asList(607),
				Arrays.asList(360)
			));
	}};
	@Override
	public BasicSprite getSprite() { return state_machine.get(getState()); }
	public BasicBackground(BasicIO io) {
		super(io);
		setState("idle");
		setBBox(new BoundingBox(607, 0));
		setBBoxDrawFlag(false);
		
	}
	
	@Override 
	public void postUpdate() {
		setPosition(getPosition().sub(new Vec2f(0.005f, 0)));
		if (getPosition().getX().lt(getBBox().getWidth().mul(0.96f).negate())) {
			setPosition(getPosition().add(new Vec2f(getBBox().getWidth().mul(2f), new BasicNumber(0))));
		}
	}
}
