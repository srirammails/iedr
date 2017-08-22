package pl.nask.crs.scheduler;


import pl.nask.crs.scheduler.jobs.AbstractJob;

public class EmptyFailingTask extends AbstractJob {

	@Override
	public String getJobName() {
		return "emptyFailingTask";
	}
	
	@Override
	public void runJob() throws Exception {
		throw new Exception("Failing!!!!");
	}
}
