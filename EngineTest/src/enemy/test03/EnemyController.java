package enemy.test03;

import java.util.TreeMap;

import bIO.BasicController;
import bIO.BasicIO;
import bIO.BasicObject;
import bIO.BasicSprite;
import bIO.BasicTimer;
import bIO.BoundingBox;
import bIO.Vec2f;
import controller.SlideController;
import enemy.test01.EnemyTest01;

public class EnemyController extends BasicObject {
	private static final TreeMap<String, BasicSprite> state_machine = new TreeMap<String, BasicSprite>() {{
		put("idle", null);
	}};
	@Override
	public BasicSprite getSprite() { return state_machine.get(getState()); }
	BasicTimer spawnTimer;
	Vec2f spawnLocation;
	
	public EnemyController(BasicIO io, BasicController controller, Vec2f spawnLoc) {
		super(io);
		setState("idle");
		setPosition(new Vec2f(0,0));
		spawnLocation = spawnLoc;
		
		spawnTimer = new BasicTimer(io.getStepPerSec() * 5, new Runnable() {
			public void run() {
				controller.addObject(new EnemyTest03(io) {{
					setPosition(spawnLoc);
					bboxUpdate();
				}});
			}
		});
		spawnTimer.setup();
	}
	
	@Override
	public void fixedUpdate() {
		spawnTimer.run();
		if (!spawnTimer.getRunning()) spawnTimer.setup();
	}
}
