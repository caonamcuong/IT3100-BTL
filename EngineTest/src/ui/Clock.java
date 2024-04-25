package ui;

import java.util.TreeMap;

import bIO.BasicIO;
import bIO.BasicObject;
import bIO.BasicSprite;
import bIO.BasicTimer;
import bIO.Vec2f;

public class Clock extends BasicObject {
	private static final TreeMap<String, BasicSprite> state_machine = new TreeMap<String, BasicSprite>() {{
		put("null", null);
	}};
	@Override
	public BasicSprite getSprite() { return state_machine.get(getState()); }
	
	private static final long step = BasicIO.getStepPerSec();
	private static final long font_width = 40;
	private int elapsed = 0;
	private BasicTimer bTm;
	
	CharacterDigitBlack m1, m2, colon, s1, s2;
	
	public Clock(BasicIO io) {
		super(io);
		elapsed = 0;
		m1 = new CharacterDigitBlack(io);
		m2 = new CharacterDigitBlack(io);
		colon = new CharacterDigitBlack(io);
		s1 = new CharacterDigitBlack(io);
		s2 = new CharacterDigitBlack(io);
		
		io.addBackgroundObject(m1);
		io.addBackgroundObject(m2);
		io.addBackgroundObject(colon);
		io.addBackgroundObject(s1);
		io.addBackgroundObject(s2);
		
		Clock a = this;
		bTm = new BasicTimer(step, new Runnable() {
			public void run() {
				a.incTime();
				bTm.setup();
			}
		});
		bTm.setup();
		setState("null");
		
		m1.setState("0");
		m2.setState("0");
		s1.setState("0");
		s2.setState("0");
		colon.setState(":");
	}
	
	@Override
	public void fixedUpdate() {
		if (!bTm.getRunning()) bTm.setup();
		bTm.run();
	}
	@Override 
	public void postUpdate() {}
	
	@Override
	public void setPosition(Vec2f new_pos) {
		super.setPosition(new_pos);
		m1.setPosition(new_pos);
		m2.setPosition(new_pos.add(new Vec2f(font_width, 0)));
		colon.setPosition(new_pos.add(new Vec2f(font_width*2, 0)));
		s1.setPosition(new_pos.add(new Vec2f(font_width*3, 0)));
		s2.setPosition(new_pos.add(new Vec2f(font_width*4, 0)));
	}
	
	private void incTime() {
		elapsed += 1;
		int min = elapsed / 60;
		int sec = elapsed - min * 60;
		m1.setState(Integer.toString(min/10));
		m2.setState(Integer.toString(min%10));
		s1.setState(Integer.toString(sec/10));
		s2.setState(Integer.toString(sec%10));
	}
}
