package map;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.TreeMap;
import java.util.Random;

import bIO.BasicController;
import bIO.BasicIO;
import bIO.BasicNumber;
import bIO.BasicObject;
import bIO.BasicSprite;
import bIO.BasicTimer;
import bIO.BoundingBox;
import bIO.Vec2f;
import controller.SlideController;
import enemy.test02.EnemyTest02Bullet;

public class map extends BasicObject {
	private static final TreeMap<String, BasicSprite> state_machine = new TreeMap<String, BasicSprite>() {{
		put("idle", null);
	}};
	@Override
	public BasicSprite getSprite() { return state_machine.get(getState()); }
	private BasicTimer ground_timer;
	private boolean ground_flag;

	private Random random = new Random();	
	BasicController controller;
	
	public map(BasicIO io, BasicController controller, long timer_time) {
		super(io);
		setState("idle");
		setPosition(new Vec2f(100, 150));
		setBBox(new BoundingBox(0, 0));
		setBBoxOrigin(new Vec2f(0, 0));
		setBBoxDrawFlag(true);
		setSpriteOrigin(new Vec2f(0, 0));
		setScale(1f);
		
		this.controller = controller;
		
		ground_timer = new BasicTimer(timer_time, new Runnable() {
			public void run() {
				ground_flag = true;
			}
		});
		ground_timer.setup();
		 ground_flag=false;
	}
	
	// gen địa hình
	// chọn một y ngẫu nhiên
	// luôn đảm bảo gen được 1 y bé hơn y ngẫu nhiên	
	
	// build frame:
	// 1 frame: chứa thanh và quái dựng trước
	// 	: high_y, low_y
	//  : đảm bảo người chơi có thể chạm được mọi vị trí trong một frame
	
	// frame[n]  : high_y[n]  , low_y[n]
	// frame[n+1]: high_y[n+1], low_y[n+1]
	// high_y[n] + jump_forces * C >= low_y[n+1]
	// C ~ 0.5
	
	@Override
	public void fixedUpdate() {
		ground_timer.run();
		if (ground_flag) {
			
			ground_timer.stop();
			Vec2f pos = new Vec2f(640, 300+random.nextFloat(50)-100);
			
			controller.addObject(new ground(getIO(), controller) {{
				setPosition(pos);
			}});
			
			ground_flag = false;
			ground_timer.setup();
		}
	}
}