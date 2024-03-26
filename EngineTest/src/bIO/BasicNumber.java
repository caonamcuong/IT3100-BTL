package bIO;

public class BasicNumber {
	private long value;
	private static long step = 512*512; // 2^n
	
	BasicNumber() {
		value = 0;
	}
	BasicNumber(double d) {
		value = (long)(d * step);
	}
	BasicNumber(float f) {
		value = (long)(f * step);
	}
	
	public double toDouble() {
		return (double)value / step;
	}
	public float toFloat() {
		return (float)value / step;
	}
	public BasicNumber add(BasicNumber n) {
		BasicNumber a = new BasicNumber();
		a.value = value + n.value;
		return a;
	}
	public BasicNumber add(float f) {
		return add(new BasicNumber(f));
	}
	public BasicNumber add(double d) {
		return add(new BasicNumber(d));
	}
	public BasicNumber mul(BasicNumber n) {
		BasicNumber a = new BasicNumber();
		a.value = value * n.value / step;
		return a;
	}
	public BasicNumber mul(float f) {
		return mul(new BasicNumber(f));
	}
	public BasicNumber mul(double d) {
		return mul(new BasicNumber(d));
	}
	public BasicNumber sub(BasicNumber n) {
		BasicNumber a = new BasicNumber();
		a.value = value - n.value;
		return a;
	}
	public BasicNumber sub(float f) {
		return sub(new BasicNumber(f));
	}
	public BasicNumber sub(double d) {
		return sub(new BasicNumber(d));
	}
	public BasicNumber div(BasicNumber n) {
		BasicNumber a = new BasicNumber();
		a.value = value * step / n.value;
		return a;
	}
	public BasicNumber div(float f) {
		return new BasicNumber(toDouble() / f);
	}
	public BasicNumber div(double d) {
		return new BasicNumber(toDouble() / d);
	}
	public BasicNumber negate() {
		BasicNumber a = new BasicNumber();
		a.value = -value;
		return a;
	}
	public boolean gt(BasicNumber n) {
		return value > n.value;
	}
	public boolean gt(float f) {
		return gt(new BasicNumber(f));
	}
	public boolean gt(double d) {
		return gt(new BasicNumber(d));
	}
	public boolean ge(BasicNumber n) {
		return value >= n.value;
	}
	public boolean ge(float f) {
		return ge(new BasicNumber(f));
	}
	public boolean ge(double d) {
		return ge(new BasicNumber(d));
	}
	public boolean lt(BasicNumber n) {
		return value < n.value;
	}
	public boolean lt(float f) {
		return lt(new BasicNumber(f));
	}
	public boolean lt(double d) {
		return lt(new BasicNumber(d));
	}
	public boolean le(BasicNumber n) {
		return value <= n.value;
	}
	public boolean le(float f) {
		return le(new BasicNumber(f));
	}
	public boolean le(double d) {
		return le(new BasicNumber(d));
	}
	
	@Override 
	public String toString() {
		return "" + toDouble();
	}
}
