package bIO;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.TreeSet;

import java.awt.event.KeyEvent;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.Timer;
import javax.swing.JComponent;


public class BasicIO implements Engine {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4395625629568626917L;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		// TEST
		
		// di chuyen bang mui ten trai-phai
		// bam space de nhay
		
		BasicIO bsio = new BasicIO();
		bsio.addObject(new BasicPlayer(bsio) {{setPosition(new Vec2f(120,0));}});
		for (int i = 0; i < 20; ++i) {
			float xx = i * 32f;
			bsio.addObject(new BasicWall(bsio) {{setPosition(new Vec2f(xx, 6*32));}});
		}
		bsio.addObject(new BasicWall(bsio) {{setPosition(new Vec2f(4*32, 5*32));}});
		bsio.addObject(new BasicWall(bsio) {{setPosition(new Vec2f(5*32, 5*32));}});
		bsio.addObject(new BasicWall(bsio) {{setPosition(new Vec2f(8*32, 3*32));}});
		bsio.addObject(new BasicWall(bsio) {{setPosition(new Vec2f(9*32, 5*32));}});
		bsio.addObject(new BasicWall(bsio) {{setPosition(new Vec2f(11*32, 5*32));}});
		bsio.addObject(new BasicWall(bsio) {{setPosition(new Vec2f(11*32, 4*32));}});
		bsio.addObject(new BasicWall(bsio) {{setPosition(new Vec2f(3*32, 5*32));}});
		bsio.addObject(new BasicWall(bsio) {{setPosition(new Vec2f(3*32, 4*32));}});
		bsio.addObject(new BasicWall(bsio) {{setPosition(new Vec2f(1*32, 5*32));}});
		
		bsio.run();
	}
	
	private Queue<InputEvent> input_queue;
	private long past_time, cur_time;
	private static final long time_step = 100000;
	private JFrame main_frame;
	private String ev_string;
	private TreeSet<Integer> pressing_keys;
	private JLabel ev_label;
	private Timer main_loop;
	private BasicSpriteManager sprite_man;
	private BasicQuadTree quad_tree;
	private LinkedList<BasicObject> active_object;
	
	// test section remove later
	private JComponent jc;
	
	public BasicIO() {
		System.setProperty("sun.java2d.opengl", "true");
		input_queue = new LinkedList<InputEvent>();
		pressing_keys = new TreeSet<Integer>();
		past_time = System.nanoTime();
		ev_label = new JLabel("Hello");
		main_frame = new JFrame();
		sprite_man = new BasicSpriteManager();
		quad_tree = new BasicQuadTree(new BoundingBox(
				640*2, 360*2, -320, -180), 128);
		
		main_frame.setSize(640,360);
		main_frame.setFocusable(true);
		main_frame.requestFocus();
		main_frame.add(ev_label);
		
		KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
		manager.addKeyEventDispatcher(new KeyEventDispatcher() {
			public boolean dispatchKeyEvent(KeyEvent e) {
				if (e.getID() == KeyEvent.KEY_RELEASED) {
					input_queue.add(new InputEvent(e, true, System.nanoTime()));
					return true;
				}
				else if (e.getID() == KeyEvent.KEY_PRESSED){
					input_queue.add(new InputEvent(e, false, System.nanoTime()));
					return true;
				}
				return false;
			}
		});
		main_loop = new Timer(10, new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				update();
			}
		});
		active_object = new LinkedList<BasicObject>();
		
		//Test section remove later
		jc = new JComponent() {
			public void paintComponent(Graphics g) {
				if (active_object.size()==0) return;
				Iterator<BasicObject> itr = active_object.iterator();
				while (itr.hasNext()) {
					BasicObject o = itr.next();
					BasicSprite sprite = o.getSprite();
					if (sprite != null) {
						BufferedImage bI = null;
						try {
							bI = sprite_man.getSpriteFrame(sprite, o.getSpriteIndex());
						}
						catch (IOException e) {
							throw new RuntimeException("cannot load sprite");
						}
						Vec2f sprite_size = new Vec2f(bI.getWidth(), bI.getHeight());
						Vec2f draw_position = o.getPosition().sub(sprite_size.mul(o.getSpriteOrigin()));
						int dx = (int)draw_position.getX();
						int dy = (int)draw_position.getY();
						g.drawImage(bI, dx, dy, null);
					}
					if (o.getBBoxDrawFlag()) {
						BoundingBox bb = o.getBBox();
						int dx = (int)bb.getX();
						int dy = (int)bb.getY();
						int dw = (int)bb.getWidth();
						int dh = (int)bb.getHeight();
						g.drawRect(dx, dy, dw, dh);
					}
				}
			}
		};
		main_frame.add(jc);
		//End of test section
		
		
		main_frame.setVisible(true);
		main_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void addObject(BasicObject o) {
		active_object.add(o);
		quad_tree.addObject(o);
	}
		
	public void fixedUpdate() {
		StringBuilder sb = new StringBuilder();
		long time_anchor = past_time + time_step;
		
		while (input_queue.size() > 0) {
			if (input_queue.peek().getEvtTimecode() <= time_anchor) {
				// process
				InputEvent iev = input_queue.remove();
				if (iev.isEvtKeyboard()) {
					if (iev.getEvtAction2() == 0) 
						pressing_keys.add(iev.getEvtAction1());
					else
						pressing_keys.remove(iev.getEvtAction1());
				}
				sb.append(iev);
			}
			else {
				break;
			}
		}
		
		String ss = sb.toString();
		if (ss.length() > 0) {
			sb.append(pressing_keys.size());
			ev_string = sb.toString();
		}
		
		Iterator<BasicObject> itr = active_object.iterator();
		while (itr.hasNext()) {
			BasicObject o = itr.next();
			o.fixedUpdate();
			o.postUpdate();
		}
	}
	public void update() {
		cur_time = System.nanoTime();
		long new_past_time = past_time;
		for (long elapsed_time = cur_time - past_time; 
				elapsed_time >= time_step; 
				elapsed_time -= time_step, new_past_time += time_step) {
			fixedUpdate();
		}
		Iterator<BasicObject> itr = active_object.iterator();
		while (itr.hasNext()) {
			BasicObject o = itr.next();
			o.frameUpdate();
		}
		render();
		past_time = new_past_time;
	}
	public void render() {
		ev_label.setText(ev_string);
		jc.repaint();
	}
	
	public void run() {
		main_loop.start();
	}
	public boolean isPressing(int keycode) {
		return pressing_keys.contains(keycode);
	}
	public long getCurTime() { return cur_time; }
	public static long getTimeStep() { return time_step; }
	public static float getUnitStep(float u) { return u/time_step; }
	public List<BasicObject> quadQueryObject(BoundingBox bbox) {
		return quad_tree.queryObject(bbox);
	}
	public void quadUpdateObject(BasicObject o, Vec2f old_pos) {
		quad_tree.updateObject(o, old_pos);
	}
}
