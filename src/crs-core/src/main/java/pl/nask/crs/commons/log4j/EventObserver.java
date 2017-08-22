package pl.nask.crs.commons.log4j;

import org.apache.log4j.Priority;
import org.apache.log4j.spi.LoggingEvent;

public class EventObserver {

	private Priority minPriority;
	private int eventCount;

	public EventObserver(Priority minPriority) {
		this.minPriority = minPriority;
	}

	public synchronized void append(LoggingEvent e) {
		if (e.getLevel().isGreaterOrEqual(minPriority)) {
			storeEvent(e);
		}		
	}

	private void storeEvent(LoggingEvent e) {			
		eventCount++;
	}

	public synchronized int eventCount() {
		return eventCount;
	}

}
