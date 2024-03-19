package bIO;

import java.awt.event.KeyEvent;

public class InputEvent {
	enum EventType {
		KEYBOARD,
		CONTROLLER,
		MOUSE
	}
	private EventType evt_type;
	private int evt_action1;
	private int evt_action2;
	private long evt_timecode;
	
	EventType getEvtType() { return evt_type; }
	int getEvtAction1() { return evt_action1; }
	int getEvtAction2() { return evt_action2; }
	long getEvtTimecode() { return evt_timecode; }
	boolean isEvtKeyboard() { return evt_type == EventType.KEYBOARD; }
	
	@Override
	public String toString() {
		StringBuilder sB = new StringBuilder();
		sB.append("[InputEvent]");
		switch (evt_type) {
			case KEYBOARD:
				sB.append(" [KeyBoard Event]");
				sB.append(" [KeyCode: " + evt_action1 + "]");
				sB.append(" [Released: " + evt_action2 + "]");
				sB.append(" [Time Code: " + evt_timecode + "]");
			break;
			default:
				sB.append(" [Not Supported Yet]");
			break;
		}
		
		return sB.toString();
	}
	
	public InputEvent(KeyEvent e, boolean released, long tc) {
		evt_type = EventType.KEYBOARD;
		evt_action1 = e.getKeyCode();
		evt_action2 = released?1:0;
		evt_timecode = tc;
	}
}
