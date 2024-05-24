package bIO;

public class Vec2f {
	private BasicNumber x, y;
	public BasicNumber getX() { return x; }
	public BasicNumber getY() { return y; }
	public void setX(BasicNumber n) { x=n; }
	public void setY(BasicNumber n) { y=n; }
	
	public Vec2f (BasicNumber x, BasicNumber y) {
		this.x = x;
		this.y = y;
	}
	public Vec2f (float x, float y) {
		this.x = new BasicNumber(x);
		this.y = new BasicNumber(y);
	}
	
	public Vec2f mul(BasicNumber c) {
		return new Vec2f(getX().mul(c), getY().mul(c));
	}
	public Vec2f mul(BasicNumber c1, BasicNumber c2) {
		return new Vec2f(getX().mul(c1), getY().mul(c2));
	}
	public Vec2f mul(Vec2f o) {
		return mul(o.getX(), o.getY());
	}
	public Vec2f div(BasicNumber d) {
		return new Vec2f(getX().div(d), getY().div(d));
	}
	public Vec2f div(BasicNumber d1, BasicNumber d2) {
		return new Vec2f(getX().div(d1), getY().div(d2));
	}
	public Vec2f div(Vec2f o) {
		return div(o.getX(), o.getY());
	}
	public Vec2f add(BasicNumber x, BasicNumber y) {
		return new Vec2f(getX().add(x), getY().add(y));
	}
	public Vec2f sub(BasicNumber x, BasicNumber y) {
		return new Vec2f(getX().sub(x), getY().sub(y));
	}
	public BasicNumber dot(BasicNumber x, BasicNumber y) {
		return getX().mul(x).add(getY().mul(y));
	}
	public Vec2f add(Vec2f o) {
		return add(o.getX(), o.getY());
	}
	public Vec2f sub(Vec2f o) {
		return sub(o.getX(), o.getY());
	}
	public BasicNumber dot(Vec2f o) {
		return dot(o.getX(), o.getY());
	}
	public Vec2f normalize() {
		float x = getX().toFloat();
		float y = getY().toFloat();
		float length = (float)Math.sqrt(x*x+y*y);
		return new Vec2f(x / length, y / length);
	}
	//public float len() {
	//	return (float)Math.sqrt(getX() * getX() + getY() * getY());
	//}
	
	@Override
	public String toString() {
		return getX() + " " + getY();
	}
}
