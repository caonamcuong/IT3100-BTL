package bIO;

public class BoundingBox {
	Vec2f position;
	Vec2f size;
	
	BoundingBox(BasicNumber w, BasicNumber h) {
		size = new Vec2f(w, h);
		position = new Vec2f(new BasicNumber(0),new BasicNumber(0));
	}
	BoundingBox(BasicNumber w, BasicNumber h, BasicNumber x, BasicNumber y) {
		size = new Vec2f(w, h);
		position = new Vec2f(x, y);
	}
	BoundingBox(float w, float h) {
		this(new BasicNumber(w), new BasicNumber(h));
	}
	BoundingBox(float w, float h, float x, float y) {
		this(new BasicNumber(w), new BasicNumber(h),
				new BasicNumber(x), new BasicNumber(y));
	}
	BoundingBox(Vec2f v) {
		size = v;
		position = new Vec2f(new BasicNumber(0),new BasicNumber(0));
	}
	BoundingBox(Vec2f v1, Vec2f v2) {
		size = v1;
		position = v2;
	}
	
	public void setWidth(BasicNumber w) { size.setX(w); }
	public void setHeight(BasicNumber h) { size.setY(h); }
	public void setSize(BasicNumber w, BasicNumber h) {
		setWidth(w); 
		setHeight(h);
	}
	public void setSize(Vec2f new_size) {
		setWidth(new_size.getX());
		setHeight(new_size.getY());
	}
	public BasicNumber getWidth() { return size.getX(); }
	public BasicNumber getHeight() { return size.getY(); }
	public Vec2f getSize() {
		return size;
	}
	
	public BoundingBox add(Vec2f v) {
		return new BoundingBox(size, position.add(v));
	}
	public BoundingBox add(BasicNumber x, BasicNumber y) {
		return new BoundingBox(size, position.add(x,y));
	}
	public BoundingBox sub(Vec2f v) {
		return new BoundingBox(size, position.sub(v));
	}
	public BoundingBox sub(BasicNumber x, BasicNumber y) {
		return new BoundingBox(size, position.sub(x,y));
	}
	
	public void setX(BasicNumber x) { position.setX(x); }
	public void setY(BasicNumber y) { position.setY(y); }
	public void setPosition(BasicNumber x, BasicNumber y) {
		setX(x); setY(y);
	}
	public void setPosition(Vec2f new_pos) {
		setX(new_pos.getX());
		setY(new_pos.getY());
	}
	public BasicNumber getX() { return position.getX(); }
	public BasicNumber getY() { return position.getY(); }
	public Vec2f getPosition() {
		return position;
	}
	public BasicNumber getX2() { return getX().add(getWidth()); }
	public BasicNumber getY2() { return getY().add(getHeight()); }
	public Vec2f getPosition2() {
		return new Vec2f(getX2(), getY2());
	}
	
	private boolean inRange(BasicNumber x, BasicNumber a, BasicNumber b) {
		return a.lt(x) && x.lt(b);
	}
	private boolean inRange2(BasicNumber x, BasicNumber a, BasicNumber b) {
		return a.le(x) && x.le(b);
	}
	private boolean inRange3(BasicNumber x, BasicNumber a, BasicNumber b) {
		return a.le(x) && x.lt(b);
	}
	public boolean collideWith(BoundingBox o) {
		BasicNumber x11 = getX(), x12 = getX2();
		BasicNumber x21 = o.getX(), x22 = o.getX2();
		
		BasicNumber y11 = getY(), y12 = getY2();
		BasicNumber y21 = o.getY(), y22 = o.getY2();
		
		boolean bx = inRange(x11, x21, x22) || inRange(x12, x21, x22) ||
				inRange(x21, x11, x12) || inRange(x22, x11, x12);
		boolean by = inRange(y11, y21, y22) || inRange(y12, y21, y22) ||
				inRange(y21, y11, y12) || inRange(y22, y11, y12);
		if (x11 == x21 && x12 == x22) return by;
		if (y11 == y21 && y12 == y22) return bx;
		return bx && by;
	}
	public boolean containPoint(Vec2f p) {
		boolean bx = inRange3(p.getX(), getX(), getX2());
		boolean by = inRange3(p.getY(), getY(), getY2());
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
