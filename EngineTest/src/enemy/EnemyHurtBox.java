package enemy;

import java.util.TreeMap;

import bIO.BasicIO;
import bIO.BasicObject;
import bIO.BasicSprite;
import bIO.Vec2f;

public class EnemyHurtBox extends BasicObject {
	private static final TreeMap<String, BasicSprite> state_machine = new TreeMap<String, BasicSprite>() {{
		put("idle", null);
	}};
	@Override
	public BasicSprite getSprite() { return state_machine.get(getState()); }
	
	public EnemyHurtBox(BasicIO io) {
		super(io);
		setState("idle");
		setPosition(new Vec2f(0,0));
	}
}
