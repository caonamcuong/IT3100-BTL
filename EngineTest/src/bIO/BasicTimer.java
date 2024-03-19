package bIO;

public class BasicTimer {
	private long init_counter;
	private long cur_counter;
	private boolean running;
	private Runnable action;
	
	BasicTimer(long init_counter, Runnable action) {
		this.init_counter = init_counter;
		this.action = action;
		this.running = false;
	}
	
	public void setup() {
		running = true;
		cur_counter = init_counter;
	}
	public void stop() {
		running = false;
	}
	public void run() {
		if (!running) {
			return;
		}
		cur_counter -= 1;
		if (cur_counter == 0) {
			action.run();
			running = false;
		}
	}
	
	public long getInitCounter() { return init_counter; }
	public void setInitCounter(long new_counter) { init_counter = new_counter; }
	public boolean getRunning() { return running; }
}