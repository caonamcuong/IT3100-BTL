package bIO;

import java.util.TreeMap;

public class BasicObject {
	private Vec2f position;
	private BoundingBox bbox;
	private Vec2f bbox_origin;
	private boolean bbox_draw_flag;
	private float scale;
	private boolean hflip, vflip;
	private Vec2f sprite_origin;
	private int sprite_index;
	private int sprite_playback; // frame per sec
	private String state;
	
	
	private long frame_time;
	private BasicIO io;
	
	//Overwrite in children
	private static final TreeMap<String, BasicSprite> state_machine = new TreeMap<String, BasicSprite>();
	public BasicSprite getSprite() { return state_machine.get(state); }
	
	BasicObject(BasicIO io) {
		this.io = io;
		this.frame_time = io.getCurTime();
		this.position = new Vec2f(new BasicNumber(0),new BasicNumber(0));
		this.bbox = new BoundingBox(new BasicNumber(0),new BasicNumber(0));
		this.sprite_origin = new Vec2f(new BasicNumber(0),new BasicNumber(0));
		this.sprite_playback = 10;
		this.sprite_index = 0;
		this.scale = 1.0f;
		this.hflip = false;
		this.vflip = false;
		this.bbox_draw_flag = false;
	}
	
	public void fixedUpdate() {}
	public void postUpdate() {
		bboxUpdate();
	}
	public void collideWith(BasicObject o) {}
	
	public void bboxUpdate() {
		Vec2f bboxSize = getBBox().getSize();
		Vec2f offset = bboxSize.mul(bbox_origin);
		bbox.setPosition(getPosition().sub(offset));
	}
	
	public void frameUpdate() {
		if (getSprite() == null) return;
		
		long elapsed_time = io.getCurTime() - frame_time;
		float count_milisec = elapsed_time / 1000000L;
		int advanced_frame = (int)(count_milisec / 1000f * sprite_playback);
		if (advanced_frame > 0) {
			sprite_index = (sprite_index + advanced_frame) % getSprite().getFrameCount();
			frame_time = io.getCurTime();
		}
	}
	
	public BasicIO getIO() { return io; }
	public Vec2f getSpriteOrigin() { return sprite_origin; }
	public boolean getBBoxDrawFlag() { return bbox_draw_flag; }
	public BoundingBox getBBox() { return bbox; }
	public Vec2f getBBoxOrigin() { return bbox_origin; }
	public Vec2f getPosition() { return position; }
	public float getScale() { return scale; }
	public long getFrameTime() { return frame_time; }
	public void setFrameTime(long tm) { frame_time = tm; }
	public int getSpriteIndex() { return sprite_index; }
	public int getSpritePlayback() { return sprite_playback; }
	public String getState() { return state; }
	public boolean getHFlip() { return hflip; }
	public boolean getVFlip() { return vflip; }
	
	public void setSpriteOrigin(Vec2f origin) { sprite_origin = origin; }
	public void setBBoxDrawFlag(boolean flag) { bbox_draw_flag = flag; }
	public void setBBox(BoundingBox bb) { bbox = bb; }
	public void setBBoxOrigin(Vec2f origin) { bbox_origin = origin; }
	public void setPosition(Vec2f pos) { position = pos; }
	public void setScale(float scl) { scale = scl; }
	public void setSpriteIndex(int idx) { sprite_index = idx; }
	public void setSpritePlayback(int framerate) { sprite_playback = framerate; }
	public void setState(String st) { 
		state=st; 
		sprite_index=0;
		frame_time=io.getCurTime();
	}
	public void setHFlip(boolean b) {hflip=b;}
	public void setVFlip(boolean b) {vflip=b;}
	
}