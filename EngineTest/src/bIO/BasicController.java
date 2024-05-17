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
	
	public BasicController(BasicIO io) {
		super(io);
		controlled_objects = Collections.synchronizedList(new LinkedList<BasicObject>());
		toadd_objects = Collections.synchronizedList(new LinkedList<BasicObject>());
		toremove_objects = Collections.synchronizedList(new LinkedList<BasicObject>());
	}
	
	public void addObject(BasicObject o) {
		toadd_objects.add(o);
	}
}
