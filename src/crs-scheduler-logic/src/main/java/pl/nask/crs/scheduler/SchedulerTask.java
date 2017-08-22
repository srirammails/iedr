package pl.nask.crs.scheduler;

import it.sauronsoftware.cron4j.Task;
import it.sauronsoftware.cron4j.TaskExecutionContext;

import org.apache.log4j.Logger;
import org.apache.log4j.Priority;

import pl.nask.crs.commons.log4j.EventObserver;
import pl.nask.crs.scheduler.jobs.AbstractJob;

public class SchedulerTask extends Task {
	private Logger log = Logger.getLogger(getClass());
	
	private final AbstractJob jobImplementation;

	private final String taskName;

	private final int jobConfigId;

    private SchedulerTask(int jobConfigId, String taskName, AbstractJob jobImplementation) {
        this.jobConfigId = jobConfigId;
        this.taskName = taskName;
        this.jobImplementation = jobImplementation;
    }

	public static SchedulerTask getInstance(String taskName, AbstractJob jobImplementation) {
		return new SchedulerTask(-1, taskName, jobImplementation);
	}

    public static SchedulerTask getInstance(JobConfig jobConfig, AbstractJob jobImplementation) {
        return new SchedulerTask(jobConfig.getId(), jobConfig.getName(), jobImplementation);
    }

	@Override
	public final void execute(TaskExecutionContext arg0) throws RuntimeException {
		EventObserver observer = new EventObserver(Priority.ERROR);
		try {
			jobImplementation.run(observer);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		if (observer.eventCount() > 0) {
			arg0.setStatusMessage("ERROR");			
		}
	}
	
	public int getJobId() {
		return jobConfigId;
	}

	public String getJobName() {
		return taskName;
	}
	
	@Override
	public boolean supportsStatusTracking() {	
		return true;
	}		
}
