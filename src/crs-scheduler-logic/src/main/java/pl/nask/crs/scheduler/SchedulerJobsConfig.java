package pl.nask.crs.scheduler;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Artur Kielak
 */

public class SchedulerJobsConfig {
	private List<JobConfig> currentJobs = new ArrayList<JobConfig>();
	private List<JobConfig> jobsToRemove;
	private List<JobConfig> jobsToSchedule;
	private List<JobConfig> jobsToReschedule;

	public List<JobConfig> getJobsConfigToSchedule() {
		return jobsToSchedule;
	}

	public List<JobConfig> getJobsConfigToReschedule() {
		return jobsToReschedule;
	}

	public List<JobConfig> getJobsConfigToDeschedule() {
		return jobsToRemove;
	}

	public void updateConfiguration(List<JobConfig> newJobs) {
		if (currentJobs == null) {
			currentJobs = new ArrayList<JobConfig>();
		}

		jobsToRemove = new ArrayList<JobConfig>();
		jobsToSchedule = new ArrayList<JobConfig>();
		jobsToReschedule = new ArrayList<JobConfig>();

		// find differences between configurations (updates and new objects first)
		for (JobConfig c: newJobs) {
			if (currentJobs.contains(c)) {
				jobsToReschedule.add(c);
			} else {
				jobsToSchedule.add(c);
			}
		}

		// find deleted configurations

		for (JobConfig c: currentJobs) {
			if (!newJobs.contains(c)) {
				jobsToRemove.add(c);
			}
		}

		currentJobs = newJobs;
	}
}

