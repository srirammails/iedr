package pl.nask.crs.scheduler;

import it.sauronsoftware.cron4j.SchedulingPattern;

/**
 *
 * @author Artur Kielak
 */
public class JobConfigValidator {
	private JobRegistry jobRegistry;

	JobConfigValidator(JobRegistry jobRegistry) {
		this.jobRegistry = jobRegistry;
	}

	public boolean hasImplementation(JobConfig job) {
		return jobRegistry.getTask(job.getName()) != null;
	}

	public boolean hasValidSchedulePattern(JobConfig jobConfig) {
		return SchedulingPattern.validate(jobConfig.getSchedulePattern());
	}

	public void validate(JobConfig jobConfig) {
        jobConfig.clearErrorMessage();
		if(!hasImplementation(jobConfig)) {
			jobConfig.addErrorMessage("Implementation not found");
		}
		if(!hasValidSchedulePattern(jobConfig)) {
			jobConfig.addErrorMessage("Invalid schedule pattern");
		}
	}
}
