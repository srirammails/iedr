package pl.nask.crs.scheduler.jobs;

import pl.nask.crs.scheduler.SchedulerCron;

public class UpdaterJob extends AbstractJob {

	private SchedulerCron cron;

	public void setCron(SchedulerCron cron) {
		this.cron = cron;
	}	
	
    @Override
    public void runJob() {
		cron.reload();
    }

    @Override
    public String getJobName() {
        return this.getClass().getSimpleName();
    }
}
