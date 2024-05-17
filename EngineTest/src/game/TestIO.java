package game;

import java.util.List;

import bIO.BasicIO;
import bIO.BasicObject;
import bIO.Vec2f;

public class TestIO extends BasicIO {
	public TestIO() {
		super();
	}
	
	@Override
	protected void updateListPost(List<BasicObject> lst) {
		for (BasicObject o: lst) {
			Vec2f opos = o.getPosition();
			if (opos.getX().toFloat() < -32f) {
				removeObject(o);
			}
			else {
				o.setPosition(o.getPosition().sub(new Vec2f(0.001f, 0)));
				o.postUpdate();
				quadUpdateObject(o, opos);
			}
		}
	}
}
