package runtime;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * The event window is used to generate events by pressing a button. 
 */
public class EventWindow {
	
	private JFrame frame;
	private String[] events;
	private Scheduler scheduler;
	
	/**
	 * 
	 * @param events - the list of the events that the state machine may accept
	 * @param scheduler - the scheduler to dispatch the events
	 */
	public EventWindow(String[] events, Scheduler scheduler) {
		this.events = events;
		this.scheduler = scheduler;
	}

	public void show() {
		SwingUtilities.invokeLater(new Runnable(){
			@Override
			public void run() {
				frame = new JFrame("Events");
				frame.setLayout(new GridLayout(events.length, 1));
				// Implementing state machine shutdown on #dispose() would be better,
				// but is too complex for this example
				frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		
				for(String buttonText: events) {
					final JButton button = new JButton();
					button.setText(buttonText);
					button.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							scheduler.addToQueueLast(button.getText());
						}
					});
					frame.getContentPane().add(button);
				}
		
				frame.setVisible(true);
				frame.pack();
			}});
	}
	
	public void close() {
		SwingUtilities.invokeLater(new Runnable(){
			@Override
			public void run() {
				frame.dispose();
			}});
	}

}
