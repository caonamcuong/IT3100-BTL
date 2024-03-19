package bIO;

import java.util.LinkedList;
import java.util.List;

import java.io.IOException;

public class BasicSprite {
	private String texture;
	private LinkedList<BasicSpriteFrame> frame_list;
	
	public BasicSprite(String tex, 
			List<Integer> ox, List<Integer> oy,
			List<Integer> ww, List<Integer> hh)
	{
		if (ox.size() != oy.size()) {
			throw new RuntimeException("Size of offset_x should equals that of offset_y");
		}
		if (ox.size() != ww.size()) {
			throw new RuntimeException("Size of frame_weight should equals that of offset_x");
		}
		if (ox.size() != hh.size()) {
			throw new RuntimeException("Size of frame_height should equals that of offset_x");
		}
		frame_list = new LinkedList<BasicSpriteFrame>();
		for (int i = 0; i < ox.size(); ++i) {
			int offsetx = ox.get(i);
			int offsety = oy.get(i);
			int fwidth = ww.get(i);
			int fheight = hh.get(i);
			frame_list.add(new BasicSpriteFrame(offsetx, offsety, fwidth, fheight));
		}
		texture = tex;
	}
	
	public String getTexture() {
		return texture;
	}
	public BasicSpriteFrame getFrame(int idx) {
		return frame_list.get(idx);
	}
	public int getFrameCount() {
		return frame_list.size();
	}
}
