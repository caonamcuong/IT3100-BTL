package bIO;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;

import player.Player;

public class BasicController extends BasicObject {
	private static final TreeMap<String, BasicSprite> state_machine = new TreeMap<String, BasicSprite>() {{
		put("idle", null);
	}};
	@Override
	public BasicSprite getSprite() { return state_machine.get(getState()); }
	
	protected List<BasicObject> controlled_objects;
	protected List<BasicObject> toadd_objects;
	protected List<BasicObject> toremove_objects;
	
	@Override
	public void fixedUpdate() {}
	@Override
	public void postUpdate() {
		for (BasicObject o: controlled_objects) {
			if (o.getPosition().getX().toFloat() <= -300f) {
				toremove_objects.add(o);
				continue;
			}
			if (o.getPosition().getY().toFloat() > 800f) {
				toremove_objects.add(o);
				continue;
			}
		}
		
		for (BasicObject o: toadd_objects) {
			controlled_objects.add(o);
			getIO().addObject(o);
		}
		for (BasicObject o: toremove_objects) { // O(n^2)
			controlled_objects.remove(o);
			getIO().removeObject(o); 
		}
		toremove_objects.clear();
		toadd_objects.clear();
	}
	
	public BasicController(BasicIO io) {
		super(io);
		setState("idle");
		controlled_objects = Collections.synchronizedList(new LinkedList<BasicObject>());
		toadd_objects = Collections.synchronizedList(new LinkedList<BasicObject>());
		toremove_objects = Collections.synchronizedList(new LinkedList<BasicObject>());
	}
	
	public void addObject(BasicObject o) {
		toadd_objects.add(o);
	}
}
