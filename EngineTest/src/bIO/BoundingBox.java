package bIO;

public class BoundingBox {
	Vec2f position;
	Vec2f size;
	
	BoundingBox(float w, float h) {
		size = new Vec2f(w, h);
		position = new Vec2f(0,0);
	}
	BoundingBox(float w, float h, float x, float y) {
		size = new Vec2f(w, h);
		position = new Vec2f(x, y);
	}
	BoundingBox(Vec2f v) {
		size = v;
		position = new Vec2f(0,0);
	}
	BoundingBox(Vec2f v1, Vec2f v2) {
		size = v1;
		position = v2;
	}
	
	public void setWidth(float w) { size.setX(w); }
	public void setHeight(float h) { size.setY(h); }
	public void setSize(float w, float h) {
		setWidth(w); 
		setHeight(h);
	}
	public void setSize(Vec2f new_size) {
		setWidth(new_size.getX());
		setHeight(new_size.getY());
	}
	public float getWidth() { return size.getX(); }
	public float getHeight() { return size.getY(); }
	public Vec2f getSize() {
		return size;
	}
	
	public BoundingBox add(Vec2f v) {
		return new BoundingBox(size, position.add(v));
	}
	public BoundingBox add(float x, float y) {
		return new BoundingBox(size, position.add(x,y));
	}
	public BoundingBox sub(Vec2f v) {
		return new BoundingBox(size, position.sub(v));
	}
	public BoundingBox sub(float x, float y) {
		return new BoundingBox(size, position.sub(x,y));
	}
	
	public void setX(float x) { position.setX(x); }
	public void setY(float y) { position.setY(y); }
	public void setPosition(float x, float y) {
		setX(x); setY(y);
	}
	public void setPosition(Vec2f new_pos) {
		setX(new_pos.getX());
		setY(new_pos.getY());
	}
	public float getX() { return position.getX(); }
	public float getY() { return position.getY(); }
	public Vec2f getPosition() {
		return position;
	}
	public float getX2() { return getX() + getWidth(); }
	public float getY2() { return getY() + getHeight(); }
	public Vec2f getPosition2() {
		return new Vec2f(getX2(), getY2());
	}
	
	private boolean inRange(float x, float a, float b) {
		return a < x && x < b;
	}
	private boolean inRange2(float x, float a, float b) {
		return a <= x && x <= b;
	}
	public boolean collideWith(BoundingBox o) {
		float x11 = getX(), x12 = getX2();
		float x21 = o.getX(), x22 = o.getX2();
		
		float y11 = getY(), y12 = getY2();
		float y21 = o.getY(), y22 = o.getY2();
		
		boolean bx = inRange(x11, x21, x22) || inRange(x12, x21, x22) ||
				inRange(x21, x11, x12) || inRange(x22, x11, x12);
		boolean by = inRange(y11, y21, y22) || inRange(y12, y21, y22) ||
				inRange(y21, y11, y12) || inRange(y22, y11, y12);
		return bx && by;
	}
	public boolean containPoint(Vec2f p) {
		boolean bx = (getX() <= p.getX() && p.getX() < getX2());
		boolean by = (getY() <= p.getY() && p.getY() < getY2());
		return bx && by;
	}
	public boolean contain(BoundingBox o) {
		boolean bx = inRange2(o.getX(), getX(), getX2()) &&
				inRange2(o.getX2(), getX(), getX2());
		boolean by = inRange2(o.getY(), getY(), getY2()) &&
				inRange2(o.getY2(), getY(), getY2());
		return bx && by;
	}
	public boolean insideOf(BoundingBox o) {
		return o.contain(this);
	}
	
	@Override
	public String toString() {
		return "[BoundingBox] [Position: " + getPosition() + "] [Size: " + getSize() + "]";
	}
}
