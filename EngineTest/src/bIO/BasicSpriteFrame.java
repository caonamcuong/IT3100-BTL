package bIO;

public class BasicSpriteFrame {
	private int offset_x;
	private int offset_y;
	private int frame_width;
	private int frame_height;
	
	public BasicSpriteFrame(int ox, int oy, int w, int h) {
		offset_x = ox;
		offset_y = oy;
		frame_width = w;
		frame_height = h;
	}
	
	public int getOffsetX() { return offset_x; }
	public int getOffsetY() { return offset_y; }
	public int getFrameWidth() { return frame_width; }
	public int getFrameHeight() { return frame_height; }
}
