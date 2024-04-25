package ui;

import java.util.Arrays;
import java.util.TreeMap;

import bIO.BasicIO;
import bIO.BasicObject;
import bIO.BasicSprite;
import bIO.BoundingBox;
import bIO.Vec2f;

public class CharacterDigit extends BasicObject {
	private static final TreeMap<String, BasicSprite> state_machine = new TreeMap<String, BasicSprite>() {{
		put("null", null);
		put("clock", new BasicSprite (
				"bin/font.png",
				Arrays.asList(0),
				Arrays.asList(0),
				Arrays.asList(40),
				Arrays.asList(40)
			));
		put("0", new BasicSprite (
				"bin/font.png",
				Arrays.asList(40),
				Arrays.asList(0),
				Arrays.asList(40),
				Arrays.asList(40)
			));
		put("1", new BasicSprite (
				"bin/font.png",
				Arrays.asList(40*2),
				Arrays.asList(0),
				Arrays.asList(40),
				Arrays.asList(40)
			));
		put("2", new BasicSprite (
				"bin/font.png",
				Arrays.asList(40*3),
				Arrays.asList(0),
				Arrays.asList(40),
				Arrays.asList(40)
			));
		put("3", new BasicSprite (
				"bin/font.png",
				Arrays.asList(40*4),
				Arrays.asList(0),
				Arrays.asList(40),
				Arrays.asList(40)
			));
		put("4", new BasicSprite (
				"bin/font.png",
				Arrays.asList(40*5),
				Arrays.asList(0),
				Arrays.asList(40),
				Arrays.asList(40)
			));
		put("5", new BasicSprite (
				"bin/font.png",
				Arrays.asList(40*6),
				Arrays.asList(0),
				Arrays.asList(40),
				Arrays.asList(40)
			));
		put("6", new BasicSprite (
				"bin/font.png",
				Arrays.asList(40*7),
				Arrays.asList(0),
				Arrays.asList(40),
				Arrays.asList(40)
			));
		put("7", new BasicSprite (
				"bin/font.png",
				Arrays.asList(40*8),
				Arrays.asList(0),
				Arrays.asList(40),
				Arrays.asList(40)
			));
		put("8", new BasicSprite (
				"bin/font.png",
				Arrays.asList(40*9),
				Arrays.asList(0),
				Arrays.asList(40),
				Arrays.asList(40)
			));
		put("9", new BasicSprite (
				"bin/font.png",
				Arrays.asList(40*10),
				Arrays.asList(0),
				Arrays.asList(40),
				Arrays.asList(40)
			));
		put(":", new BasicSprite (
				"bin/font.png",
				Arrays.asList(0),
				Arrays.asList(40),
				Arrays.asList(40),
				Arrays.asList(40)
			));
		put("0b", new BasicSprite (
				"bin/font.png",
				Arrays.asList(40),
				Arrays.asList(40),
				Arrays.asList(40),
				Arrays.asList(40)
			));
		put("1b", new BasicSprite (
				"bin/font.png",
				Arrays.asList(40*2),
				Arrays.asList(40),
				Arrays.asList(40),
				Arrays.asList(40)
			));
		put("2b", new BasicSprite (
				"bin/font.png",
				Arrays.asList(40*3),
				Arrays.asList(40),
				Arrays.asList(40),
				Arrays.asList(40)
			));
		put("3b", new BasicSprite (
				"bin/font.png",
				Arrays.asList(40*4),
				Arrays.asList(40),
				Arrays.asList(40),
				Arrays.asList(40)
			));
		put("4b", new BasicSprite (
				"bin/font.png",
				Arrays.asList(40*5),
				Arrays.asList(40),
				Arrays.asList(40),
				Arrays.asList(40)
			));
		put("5b", new BasicSprite (
				"bin/font.png",
				Arrays.asList(40*6),
				Arrays.asList(40),
				Arrays.asList(40),
				Arrays.asList(40)
			));
		put("6b", new BasicSprite (
				"bin/font.png",
				Arrays.asList(40*7),
				Arrays.asList(40),
				Arrays.asList(40),
				Arrays.asList(40)
			));
		put("7b", new BasicSprite (
				"bin/font.png",
				Arrays.asList(40*8),
				Arrays.asList(40),
				Arrays.asList(40),
				Arrays.asList(40)
			));
		put("8b", new BasicSprite (
				"bin/font.png",
				Arrays.asList(40*9),
				Arrays.asList(40),
				Arrays.asList(40),
				Arrays.asList(40)
			));
		put("9b", new BasicSprite (
				"bin/font.png",
				Arrays.asList(40*10),
				Arrays.asList(40),
				Arrays.asList(40),
				Arrays.asList(40)
			));
	}};
	@Override
	public BasicSprite getSprite() { return state_machine.get(getState()); }
	
	public CharacterDigit(BasicIO io) {
		super(io);
		setSpriteOrigin(new Vec2f(0.0f, 0.0f));
		setScale(1f);;
	}
	
	@Override
	public void fixedUpdate() {}
	@Override
	public void postUpdate() {}
}
