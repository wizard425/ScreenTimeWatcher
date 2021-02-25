
public class Session {

	long startsec = 0;
	long endsec = 0;
	
	
	public Session() {
		startsec = System.currentTimeMillis() / 1000;
	}
	
	public void setStart(long startsec) {
		this.startsec = startsec;
	}
	
	public void setEnd(long endsec) {
		this.endsec = endsec;
	}
	
	/**
	 * Returns the time since the Session started
	 * @return long, sec since sessionstart
	 */
	public long getTimeSinceStart() {
		long ret = 0;
		long aktuell = System.currentTimeMillis();
		
		ret = aktuell - startsec;
		
		return ret;
	}
	
	/**
	 * Returns the Duration of the Session if its ended, if Session is still running, the Method returns -1
	 * @return duration of the Session in seconds
	 */
	public long getDuration() {
		long ret = 0;
		
		if(endsec > 0) {
			ret = endsec - startsec;
		}else {
			ret = -1;
		}
		
		return ret;
	}
	

}
