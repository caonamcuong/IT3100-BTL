package frame;

import java.util.Random;
import java.util.TreeMap;

import bIO.BasicController;
import bIO.BasicIO;
import bIO.BasicObject;
import bIO.BasicSprite;
import bIO.BasicTimer;
import bIO.Vec2f;
import enemy.test03.EnemyTest03;
import map.ground;

public class FrameGenerator extends BasicObject {
	private static final TreeMap<String, BasicSprite> state_machine = new TreeMap<String, BasicSprite>() {{
		put("idle", null);
	}};
	@Override
	public BasicSprite getSprite() { return state_machine.get(getState()); }
	
	private BasicTimer timer;
	private Random rand;
	private static final int NUM_TYPE = 2;
	
	private BasicIO io;
	private BasicController control;
	boolean stopped;
	
	public FrameGenerator(BasicIO bsio, BasicController controller, long resetTime) {
		super(bsio);
		setState("idle");
		io = bsio;
		control = controller;
		
		FrameGenerator t = this;
		rand = new Random();
		timer = new BasicTimer(resetTime, new Runnable() {
			public void run() {
				System.out.println("here");
				t.chooseFrame();
			}
		});
		timer.setup();
		stopped = false;
	}
	
	@Override
	public void fixedUpdate() {
		if (stopped) return;
		timer.run();
		if (!timer.getRunning()) timer.setup();
	}
	
	void chooseFrame() {
		int typ = rand.nextInt(0, NUM_TYPE) + 1;
		System.out.println(typ);
		if (typ == 1) {
			float yPos = rand.nextFloat(-80, -40);
			genFrame1(new Vec2f(640, yPos));
		}
		else if (typ == 2) {
			float yPos = rand.nextFloat(-80, -40);
			genFrame2(new Vec2f(640, yPos));
		}
	}
	
	private void genFrame1(Vec2f position) {
		float x = position.getX().toFloat();
		float y = position.getY().toFloat();
		control.addObject(new ground(io, control, 0+x, 300+y));
		control.addObject(new ground(io, control, 250+x, 220+y));
		control.addObject(new EnemyTest03(io, 20+x, 280+y));
		control.addObject(new EnemyTest03(io, 180+x, 280+y));
		control.addObject(new EnemyTest03(io, 300+x, 210+y));
		control.addObject(new EnemyTest03(io, 360+x, 210+y));
		control.addObject(new EnemyTest03(io, 400+x, 210+y));
	}

	private void genFrame2(Vec2f position) {
		float x = position.getX().toFloat();
		float y = position.getY().toFloat();
		control.addObject(new ground(io, control, 0+x, 300+y));
		control.addObject(new ground(io, control, 200+x, 300+y));
		control.addObject(new EnemyTest03(io, 50+x, 290+y));
		control.addObject(new EnemyTest03(io, 100+x, 290+y));
		control.addObject(new EnemyTest03(io, 150+x, 290+y));
		control.addObject(new EnemyTest03(io, 200+x, 290+y));
		control.addObject(new EnemyTest03(io, 250+x, 290+y));
		control.addObject(new EnemyTest03(io, 300+x, 290+y));
	}
	
	public void stop() {
		stopped = true;
	}
}