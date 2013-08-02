package runtime;

import java.util.TimerTask;

public class Timer extends java.util.Timer {
	
	private String timerId;
	private Scheduler scheduler;
	private long delay;
	
	public Timer(String timerId) {
		super(timerId);
		this.timerId = timerId;
	}
	
	public void start(final Scheduler scheduler, long delay) {
		this.schedule(new TimerTask() {
			public void run() {
				scheduler.addToQueueFirst(timerId);
			}
		}, delay);
	}
	
	public void stop() {
		super.cancel();
	}
	
	public void restart() {
		super.cancel();
		this.start(scheduler, delay);
	}
	
	public String getId() {
		return timerId;
	}
}
