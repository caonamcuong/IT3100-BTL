package bIO;

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
	private BasicSpriteManager sprite_man;
	private BasicQuadTree quad_tree;
	private LinkedList<BasicObject> active_object;
	private LinkedList<BasicObject> background_object;
	
	// test section remove later
	private JComponent jc;
	
	public BasicIO() {
		if (System.getProperty("sun.java2d.opengl") != null)
			System.setProperty("sun.java2d.opengl", "true");
		input_queue = new LinkedList<InputEvent>();
		pressing_keys = new TreeSet<Integer>();
		past_time = System.nanoTime();
		ev_label = new JLabel("Hello");
		main_frame = new JFrame();
		sprite_man = new BasicSpriteManager();
		quad_tree = new BasicQuadTree(new BoundingBox(
				new BasicNumber(640*2), new BasicNumber(360*2), 
				new BasicNumber(-320), new BasicNumber(-180)), 
			128);
		
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
		background_object = new LinkedList<BasicObject>();
		
		//Test section remove later
		jc = new JComponent() {
			public void paintComponent(Graphics g) {
				if (background_object.size()!=0) {
					Iterator<BasicObject> itr = background_object.iterator();
					drawList(itr, g);
				}
				if (active_object.size()!=0) {
					Iterator<BasicObject> itr = active_object.iterator();
					drawList(itr, g);
				}
			}
			private void drawList(Iterator<BasicObject> itr, Graphics g) {
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
						BufferedImage bIa = new BufferedImage((int)(bI.getWidth()*o.getScale()), (int)(bI.getHeight()*o.getScale()), BufferedImage.TYPE_3BYTE_BGR);
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
		main_frame.add(jc);
		//End of test section
		
		
		main_frame.setVisible(true);
		main_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void addObject(BasicObject o) {
		active_object.add(o);
		quad_tree.addObject(o);
	}
	public void addBackgroundObject(BasicObject o) {
		background_object.add(o);
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
		
		updateActive();
		updateBackground();
	}
	private void updateActive() {
		Iterator<BasicObject> itr = active_object.iterator();
		while (itr.hasNext()) {
			BasicObject o = itr.next();
			o.fixedUpdate();
		}
		
		itr = active_object.iterator();
		while (itr.hasNext()){
			BasicObject o = itr.next();
			o.postUpdate();
		}
	}
	private void updateBackground() {
		Iterator<BasicObject> itr = background_object.iterator();
		while (itr.hasNext()) {
			BasicObject o = itr.next();
			o.fixedUpdate();
		}
		
		itr = background_object.iterator();
		while (itr.hasNext()){
			BasicObject o = itr.next();
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
