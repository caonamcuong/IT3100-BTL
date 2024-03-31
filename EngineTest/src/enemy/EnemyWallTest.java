package enemy;

import java.util.TreeMap;

import bIO.BasicIO;
import bIO.BasicObject;
import bIO.BasicSprite;
import bIO.BoundingBox;
import bIO.Vec2f;

public class EnemyWallTest extends BasicObject {
	private static final TreeMap<String, BasicSprite> state_machine = new TreeMap<String, BasicSprite>() {{
		put("idle", null);
	}};
	@Override
	public BasicSprite getSprite() { return state_machine.get(getState()); }
	
	public EnemyWallTest(BasicIO io) {
		super(io);
		setState("idle");
		setPosition(new Vec2f(0,0));
		setBBox(new BoundingBox(64, 64));
		setBBoxOrigin(new Vec2f(0, 0));
		setBBoxDrawFlag(true);
	}
}
