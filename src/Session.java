import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Session {

	long startsec = 0;
	long endsec = 0;
	Date startday;

	public Session() {
		startsec = System.currentTimeMillis() / 1000;
		startday = new Date();
	}

	public void setStart(long startsec) {
		this.startsec = startsec;
	}

	public void setEnd(long endsec) {
		this.endsec = endsec;
	}

	/**
	 * Returns the time since the Session started
	 * 
	 * @return long, sec since sessionstart
	 */
	public long getTimeSinceStart() {
		long ret = 0;
		long aktuell = System.currentTimeMillis();

		ret = aktuell - startsec;

		return ret;
	}

	public void endSession() {
		endsec = System.currentTimeMillis() / 1000;
		// writes the current Session in the csv-File
		try {

			System.out.println(new SimpleDateFormat("dd/MM/yyyy").format(startday));
			BufferedWriter f = new BufferedWriter(new FileWriter("/home/samuel/eclipse-workspace/TimeController/src/sessions.csv",true));
			f.append(new SimpleDateFormat("dd/MM/yyyy").format(startday) + ";" + (endsec - startsec));
			f.newLine();

			f.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Returns the Duration of the Session if its ended, if Session is still
	 * running, the Method returns -1
	 * 
	 * @return duration of the Session in seconds
	 */
	public long getDuration() {
		long ret = 0;

		if (endsec > 0) {
			ret = endsec - startsec;
		} else {
			ret = -1;
		}

		return ret;
	}

}
