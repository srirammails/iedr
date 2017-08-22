package pl.nask.crs.scheduler;


import org.apache.log4j.Logger;

import pl.nask.crs.scheduler.jobs.AbstractJob;

public class EmptyTaskWithError extends AbstractJob {

	@Override
	public String getJobName() {
		return "emptyTaskWithError";
	}
	
	@Override
	public void runJob() throws Exception {
		Logger.getLogger(EmptyTaskWithError.class).error("Error message");
	}
}
