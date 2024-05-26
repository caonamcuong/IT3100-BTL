package map;

import java.util.Arrays;
import java.util.TreeMap;

import bIO.BasicController;
import bIO.BasicIO;
import bIO.BasicNumber;
import bIO.BasicSprite;
import bIO.BasicWall;
import bIO.BoundingBox;
import bIO.Vec2f;
import controller.SlideController;

public class ground extends BasicWall {
	private static final TreeMap<String, BasicSprite> state_machine = new TreeMap<String, BasicSprite>() {{
		put("idle", new BasicSprite (
				"map2.png",
				Arrays.asList(0),
				Arrays.asList(0),
				Arrays.asList(200),
				Arrays.asList(20)
			));
	}};
	@Override
	public BasicSprite getSprite() { return state_machine.get(getState()); }
		
	private BasicWall[] wall;
	private boolean loaded;
	
	public ground(BasicIO io, BasicController controller) {
		super(io);
		setState("idle");
		setPosition(new Vec2f(0,0));
		setBBox(new BoundingBox(0, 0));
		setBBoxOrigin(new Vec2f(0, 0));
		setBBoxDrawFlag(true);
		
		wall = new BasicWall[5];
		loaded=true;
		for (int i = 0; i < 5 ; ++i) {
			wall[i] = new BasicWall(io, 40, 20);
			wall[i].setPosition(getPosition().add(new BasicNumber(40*i), new BasicNumber(0)));
			controller.addObject(wall[i]);
		}
	}
	public ground(BasicIO io, BasicController controller, float x, float y) {
		this(io, controller);
		setPosition(new Vec2f(x, y));
	}
	@Override
	public void setPosition(Vec2f pos) {
		super.setPosition(pos);
		if (loaded) {
			for (int i = 0; i < 5; ++i) {
				wall[i].setPosition(getPosition().add(new BasicNumber(40*i), new BasicNumber(0)));
			}
		}
	}

	@Override
	public void postUpdate() {
		Vec2f old_pos = getBBox().getPosition();
		bboxUpdate();
		getIO().quadUpdateObject(this, old_pos);
	}
}