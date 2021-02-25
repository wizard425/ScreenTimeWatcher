import java.awt.EventQueue;

import javax.swing.*;

public class ThreadCurrentTime extends Thread {

	private Session s;
	private JLabel clock;
	
	public ThreadCurrentTime(Session s, JLabel clock) {
		this.s = s;
		this.clock = clock;
	}
	
	@Override
	public void run() {
		
		while(true) {
		
			long aktsec = System.currentTimeMillis() / 1000;
			EventQueue.invokeLater(new Runnable() {
				
				@Override
				public void run() {
					clock.setText(String.valueOf(aktsec - s.startsec));					
				}
			});
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
}
