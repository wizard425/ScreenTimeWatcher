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
					clock.setText(secToTime(aktsec - s.startsec));					
				}
			});
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Converts the given secs to String with HH:MM:SS
	 * 
	 * @param sec the sec to convert
	 * @return String with HH:MM:SS-Format
	 */
	public String secToTime(long sec) {
		String ret = "";
		long s, m, h;
		s = sec % 60;
		m = (sec / 60) % 60;

		h = (sec / 60) / 60;
		if (s < 10 && m < 10) {
			ret = h + ":0" + m + ":0" + s;
		}else if(s < 10) {
			ret = h + ":" + m + ":0" + s;
		}else if(m < 10) {
			ret = h + ":0" + m + ":" + s;
		}else {
			ret = h + ":" + m + ":" + s;
		}
		return ret;
	}
	
}
