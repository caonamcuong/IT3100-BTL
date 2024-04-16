package player;

import java.util.TreeMap;

import bIO.BasicIO;
import bIO.BasicObject;
import bIO.BasicSprite;
import bIO.BasicTimer;
import bIO.BoundingBox;
import bIO.Vec2f;

public class PlayerHitbox extends BasicObject {
	private static final TreeMap<String, BasicSprite> state_machine = new TreeMap<String, BasicSprite>() {{
		put("idle", null);
	}};
	@Override
	public BasicSprite getSprite() { return state_machine.get(getState()); }
	
	private BasicTimer des_timer;
	
	public PlayerHitbox(BasicIO io, Vec2f position, Vec2f size, long destime) {
		super(io);
		setState("idle");
		setPosition(position);
		setBBox(new BoundingBox(size));
		setBBoxOrigin(new Vec2f(0.5f, 0.5f));
		
		setBBoxDrawFlag(io.getDebug());
		PlayerHitbox aHb = this;
		des_timer = new BasicTimer(destime, new Runnable() {
			public void run() {
				aHb.getIO().removeObject(aHb);
			}
		});
		des_timer.setup();
		
		bboxUpdate();
	}
	@Override
	public void fixedUpdate() {
		des_timer.run();
	}
}
