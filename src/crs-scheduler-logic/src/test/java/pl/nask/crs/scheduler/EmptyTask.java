package pl.nask.crs.scheduler;

import org.apache.log4j.Logger;

import pl.nask.crs.scheduler.jobs.AbstractJob;

public class EmptyTask extends AbstractJob {
	private Logger log = Logger.getLogger(EmptyTask.class);		
	
	@Override
	public void runJob() throws Exception {
		log.debug("run() called");
	}

	@Override
	public String getJobName() {
		return "emptyTask";
	}

}
