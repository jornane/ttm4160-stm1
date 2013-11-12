package exercises;

import runtime.EventWindow;
import runtime.IStateMachine;
import runtime.Scheduler;
import runtime.Timer;

public class PeriodicTimerMachine implements IStateMachine {
	
	private static final String START = "Start", STOP = "Stop", EXIT = "Exit", TIMER_1 = "t1";	
	public static final String[] EVENTS = {START, STOP, EXIT};
	private enum State {IDLE, ACTIVE, FINAL}
	private Timer t1 = new Timer("t1");
	protected State state = State.IDLE;

	public int fire(String event, Scheduler scheduler) {
		if(state==State.IDLE) {
			if(event.equals(START)) {
				t1.start(scheduler, 1000);
				state = State.ACTIVE;
				return EXECUTE_TRANSITION;
			}
		} else if(state==State.ACTIVE) {
			if(event.equals(STOP)) {
				t1.stop();
				state = State.IDLE;
				return EXECUTE_TRANSITION;
			} else if (event.equals(TIMER_1)) {
				System.out.println("tick");
				t1.start(scheduler, 1000);
				state = State.ACTIVE;
				return EXECUTE_TRANSITION;
			}
		}
		return DISCARD_EVENT;
	}
	
	public static void main(String[] args) {
		IStateMachine stm = new PeriodicTimerMachine();
		Scheduler s = new Scheduler(stm);
		
		EventWindow w = new EventWindow(EVENTS, s);
		w.show();

		s.run();
	}
}
