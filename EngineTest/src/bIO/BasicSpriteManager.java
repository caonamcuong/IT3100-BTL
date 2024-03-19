package bIO;

import java.awt.image.BufferedImage;

import java.util.HashMap;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class BasicSpriteManager {
	HashMap<String, BufferedImage> texture_manager;
	
	public BasicSpriteManager() {
		texture_manager = new HashMap<String, BufferedImage>();
	}
	
	private void loadTexture(String tex_name)
		throws IOException
	{
		if (textureExists(tex_name)) return;
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File(tex_name));
		} catch (IOException e) {
			throw new IOException("Cannot load texture");
		}
		texture_manager.put(tex_name, img);
	}
	
	private boolean textureExists(String tex_name) {
		return texture_manager.containsKey(tex_name);
	}
	
	private BufferedImage getTexture(String tex_name)
		throws IOException
	{
		if (!textureExists(tex_name)) {
			throw new IOException("Texture not exists");
		}
		return texture_manager.get(tex_name);
	}
	
	public BufferedImage getSpriteFrame(BasicSprite sprite, int index)
		throws IOException
	{	
		// slow, no cache
		BufferedImage img = null;
		if (!textureExists(sprite.getTexture())) {
			try {
				loadTexture(sprite.getTexture());
			}
			catch(IOException e) {
				throw new IOException();
			}
		}
		
		try {
			img = getTexture(sprite.getTexture());
		}
		catch (IOException e) {
			throw new IOException("Cannot load texture");
		}
		
		BasicSpriteFrame frm = sprite.getFrame(index);
		if (frm.getOffsetX() < 0 || frm.getOffsetY() < 0 ||
			frm.getOffsetX() + frm.getFrameWidth() > img.getWidth() ||
			frm.getOffsetY() + frm.getFrameHeight() > img.getHeight()) {
			throw new IOException("Frame out of Texture bound");
		}
		
		return img.getSubimage(frm.getOffsetX(), frm.getOffsetY(), 
				frm.getFrameWidth(), frm.getFrameHeight());
		
	}
}