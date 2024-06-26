package bIO;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.TreeSet;

import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Graphics;
import java.awt.GraphicsDevice;
import java.awt.GraphicsDevice.WindowTranslucency;
import java.awt.GraphicsEnvironment;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.Timer;
import javax.swing.JComponent;


public class BasicIO implements Engine {

	private Queue<InputEvent> input_queue;
	private long past_time, cur_time;
	private static final long time_step = 100000;
	private JFrame main_frame;
	private String ev_string;
	private TreeSet<Integer> pressing_keys;
	private JLabel ev_label;
	private Timer main_loop;
	private BasicSpriteManager sprite_manager;
	private BasicQuadTree quad_tree;
	private List<BasicObject> active_object;
	private List<BasicObject> background_object;
	private List<BasicObject> toadd_object;
	private List<BasicObject> toremove_object;
	private List<BasicObject> toadd_background;
	private List<BasicObject> toremove_background;
	private BasicSound sound_manager;
	
	// test section remove later
	private JComponent jc;
	
	public static boolean getDebug() {return false;}
	
	public BasicIO() {
		if (System.getProperty("sun.java2d.opengl") != null)
			System.setProperty("sun.java2d.opengl", "true");
		input_queue = new LinkedList<InputEvent>();
		pressing_keys = new TreeSet<Integer>();
		past_time = System.nanoTime();
		ev_label = new JLabel("Hello");
		main_frame = new JFrame();
		//main_frame.setUndecorated(true);
		//main_frame.setOpacity(0.55f);
		GraphicsEnvironment ge = 
	            GraphicsEnvironment.getLocalGraphicsEnvironment();
	        GraphicsDevice gd = ge.getDefaultScreenDevice();
	        boolean isPerPixelTranslucencySupported = 
	            gd.isWindowTranslucencySupported(WindowTranslucency.PERPIXEL_TRANSLUCENT);
	    assert(isPerPixelTranslucencySupported);
		
		
		sprite_manager = new BasicSpriteManager();
		sound_manager = new BasicSound();
		//quad_tree = new BasicQuadTree(new BoundingBox(
		//		new BasicNumber(640*2), new BasicNumber(360*2), 
		//		new BasicNumber(-320), new BasicNumber(-180)), 
		//	100);
		
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
		active_object = Collections.synchronizedList(new LinkedList<BasicObject>());
		background_object = Collections.synchronizedList(new LinkedList<BasicObject>());
		toadd_object = Collections.synchronizedList(new LinkedList<BasicObject>());
		toremove_object = Collections.synchronizedList(new LinkedList<BasicObject>());
		toadd_background = Collections.synchronizedList(new LinkedList<BasicObject>());
		toremove_background = Collections.synchronizedList(new LinkedList<BasicObject>());
		
		//Test section remove later
		jc = new JComponent() {
			public void paintComponent(Graphics g) {
				if (background_object.size()!=0) {
					drawList(background_object, g);
				}
				if (active_object.size()!=0) {
					drawList(active_object, g);
				}
			}
			private void drawList(List<BasicObject> lst, Graphics g) {
				for (BasicObject o : lst) {
					BasicSprite sprite = o.getSprite();
					if (sprite != null) {
						BufferedImage bI = null;
						try {
							bI = sprite_manager.getSpriteFrame(sprite, o.getSpriteIndex());
						}
						catch (IOException e) {
							throw new RuntimeException("cannot load sprite");
						}
						
						// translate (-0.5, -0.5)
						// rotate (deg)
						// scale (scl)
						// translate (0.5, 0.5)
						// draw
						AffineTransform atflip = AffineTransform.getScaleInstance(o.getHFlip()?-1:1, o.getVFlip()?-1:1);
						atflip.translate(o.getHFlip()?-bI.getWidth():0, o.getVFlip()?-bI.getHeight():0);
						AffineTransformOp opflip = new AffineTransformOp(atflip, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
						bI = opflip.filter(bI, null);
						
						AffineTransform at = new AffineTransform();
						at.setToScale(o.getScale(), o.getScale());
						
						AffineTransformOp op = new AffineTransformOp(at, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
						BufferedImage bIa = new BufferedImage((int)(bI.getWidth()*o.getScale()), 
								(int)(bI.getHeight()*o.getScale()), 
								BufferedImage.TYPE_4BYTE_ABGR);
						op.filter(bI, bIa);
						
						Vec2f sprite_size = new Vec2f(new BasicNumber(bIa.getWidth()), 
								new BasicNumber(bIa.getHeight()));
						Vec2f draw_position = o.getPosition().sub(sprite_size.mul(o.getSpriteOrigin()));
						int dx = (int)draw_position.getX().toDouble();
						int dy = (int)draw_position.getY().toDouble();
						g.drawImage(bIa, dx, dy, null);
					}
					if (o.getBBoxDrawFlag()) {
						BoundingBox bb = o.getBBox();
						int dx = (int)bb.getX().toDouble();
						int dy = (int)bb.getY().toDouble();
						int dw = (int)bb.getWidth().toDouble();
						int dh = (int)bb.getHeight().toDouble();
						g.drawRect(dx, dy, dw, dh);
					}
				}
			}
		};
		jc.setOpaque(false);
		main_frame.add(jc);
		//End of test section
		
		
		main_frame.setVisible(true);
		main_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void addObject(BasicObject o) {
		toadd_object.add(o);
	}
	public void removeObject(BasicObject o) {
		toremove_object.add(o);
	}
	public void addBackgroundObject(BasicObject o) {
		toadd_background.add(o);
	}
	public void removeBackgroundObject(BasicObject o) {
		toremove_background.add(o);
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
		
		updateList(active_object);
		updateList(background_object);
		
		for (BasicObject o: toadd_object) {
			active_object.add(o);
			//quad_tree.addObject(o);
		}
		for (BasicObject o: toremove_object) {
			active_object.remove(o);
			//quad_tree.removeObject(o);
		}
		for (BasicObject o: toadd_background) {
			background_object.add(o);
		}
		for (BasicObject o: toremove_background) {
			background_object.remove(o);
		}
		toadd_object.clear();
		toremove_object.clear();
		toadd_background.clear();
		toremove_background.clear();
	}
	protected void updateList(List<BasicObject> lst) {
		for (BasicObject o: lst) o.fixedUpdate();
	}
	public void postUpdate() {
		updateListPost(active_object);
	}
	protected void updateListPost(List<BasicObject> lst) {
		for (BasicObject o: lst) o.postUpdate();
	}
	
	public void update() {
		cur_time = System.nanoTime();
		long new_past_time = past_time;
		for (long elapsed_time = cur_time - past_time; 
				elapsed_time >= time_step; 
				elapsed_time -= time_step, new_past_time += time_step) {
			fixedUpdate();
			postUpdate();
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
		//ev_label.setText(ev_string);
		jc.repaint();
	}
	
	// duct tape
	public void reset() {
		//quad_tree = new BasicQuadTree(new BoundingBox(
		//		new BasicNumber(640*2), new BasicNumber(360*2), 
		//		new BasicNumber(-320), new BasicNumber(-180)), 
		//	100);
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
	public static long getStepPerSec() { return 1000000000L / time_step; }
	public List<BasicObject> quadQueryObject(BoundingBox bbox) {
		List<BasicObject> ret = Collections.synchronizedList(new LinkedList<BasicObject>());
		for (BasicObject o: active_object) {
			if (bbox.collideWith(o.getBBox())) ret.add(o);
		}
		
		return ret;
	}
	public void quadUpdateObject(BasicObject o, Vec2f old_pos) {
		//quad_tree.updateObject(o, old_pos);
	}
	public void playSound(String s) {
		try {
			sound_manager.playSound(s);
		}
		catch (Exception ignored) {
			ignored.printStackTrace(System.err);
		}
	}
}
