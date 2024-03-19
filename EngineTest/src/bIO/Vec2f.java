package bIO;

public class Vec2f {
	private float x, y;
	public float getX() { return x; }
	public float getY() { return y; }
	public void setX(float f) { x=f; }
	public void setY(float f) { y=f; }
	
	Vec2f (float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public Vec2f mul(float c) {
		return new Vec2f(getX() * c, getY() * c);
	}
	public Vec2f mul(float c1, float c2) {
		return new Vec2f(getX() * c1, getY() * c2);
	}
	public Vec2f mul(Vec2f o) {
		return mul(o.getX(), o.getY());
	}
	public Vec2f div(float d) {
		return new Vec2f(getX() / d, getY() / d);
	}
	public Vec2f div(float d1, float d2) {
		return new Vec2f(getX() / d1, getY() / d2);
	}
	public Vec2f div(Vec2f o) {
		return div(o.getX(), o.getY());
	}
	public Vec2f add(float x, float y) {
		return new Vec2f(getX() + x, getY() + y);
	}
	public Vec2f sub(float x, float y) {
		return new Vec2f(getX() - x, getY() - y);
	}
	public float dot(float x, float y) {
		return getX() * x + getY() * y;
	}
	public Vec2f add(Vec2f o) {
		return add(o.getX(), o.getY());
	}
	public Vec2f sub(Vec2f o) {
		return sub(o.getX(), o.getY());
	}
	public float dot(Vec2f o) {
		return dot(o.getX(), o.getY());
	}
	public float len() {
		return (float)Math.sqrt(getX() * getX() + getY() * getY());
	}
	
	@Override
	public String toString() {
		return getX() + " " + getY();
	}
}
