package pl.nask.crs.scheduler;

import it.sauronsoftware.cron4j.Scheduler;
import it.sauronsoftware.cron4j.SchedulerListener;
import it.sauronsoftware.cron4j.SchedulingPattern;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.scheduler.dao.SchedulerDAO;
import pl.nask.crs.scheduler.dao.SchedulerDAOImpl;
import pl.nask.crs.scheduler.exceptions.InvalidSchedulePatternException;
import pl.nask.crs.scheduler.exceptions.JobDuplicationNameException;
import pl.nask.crs.scheduler.jobs.AbstractJob;
import pl.nask.crs.security.authentication.AuthenticatedUser;

/**
 *
 * @author Artur Kielak
 */

public class SchedulerCronImpl implements SchedulerCron {
	static final Logger log = Logger.getLogger(SchedulerCronImpl.class);

	private Scheduler scheduler = new Scheduler();
	private SchedulerJobsConfig schedulerJobsConfig = new SchedulerJobsConfig();
	private JobConfigValidator jobConfigValidator;
	private SchedulerDAO dao;
	private JobRegistry jobRegistry;

	public SchedulerCronImpl(SchedulerDAOImpl dao, SchedulerListener listener) {
		this.dao = dao;
		scheduler.addSchedulerListener(listener);
	}

	public void setRegistry(JobRegistry jobRegistry) {
		this.jobRegistry = jobRegistry;
		this.jobConfigValidator = new JobConfigValidator(jobRegistry);
	}

	@Override
	public boolean reload() {
		if (scheduler.isStarted()) {
			updateJobsConfiguration();
			return true;
		}
		return false;
	}

	@Override
	public boolean start() {
		try {
			updateJobsConfiguration();
			scheduler.start();
            log.info("Scheduler started.");
			return true;
		} catch (IllegalStateException e) {
			log.debug("Start scheduler failure "+ e.getMessage());
			return false;
		}
	}

	@Override
	public boolean stop() {
		try {
			scheduler.stop();
            log.info("Scheduler stopped.");
            return true;
		} catch (IllegalStateException e) {
			log.debug("Stop scheduler failure " + e.getMessage());
			return false;
		}
	}

	@Override
	public void invoke(String taskName) {
		AbstractJob task = jobRegistry.getTask(taskName);
		SchedulerTask st = SchedulerTask.getInstance(taskName, task);
		scheduler.launch(st);
	}
	
	@Override
	public boolean isRunning() {			
		return scheduler.isStarted();
	}
	
	private void updateJobsConfiguration() {
		List<JobConfig> jobsConfig = filterValidJobsConfigurations(dao.getJobConfigs());
		schedulerJobsConfig.updateConfiguration(jobsConfig);
		doSchedule();
		doReschedule();
		doDeschedule();
	}

	private List<JobConfig> filterValidJobsConfigurations(List<JobConfig> jobs) {
		List<JobConfig> jobsConfig = new ArrayList<JobConfig>();
		for(JobConfig job : jobs) {
			jobConfigValidator.validate(job);
			if(!job.isValid()) {
				logJobConfigError(job);
			} else {
				jobsConfig.add(job);				
			}
		}
		return jobsConfig;
	}

    private void logJobConfigError(JobConfig job, String errorMessage) {
        job.setErrorMessage(errorMessage);
        logJobConfigError(job);
    }

    private void logJobConfigError(JobConfig job) {
        job.setActive(false);
        dao.updateJobConfig(job);
	}

	private void doSchedule() {
		List<JobConfig> jobsConfig = schedulerJobsConfig.getJobsConfigToSchedule();
		for(JobConfig currentConfig : jobsConfig) {
			AbstractJob newJob = jobRegistry.getTask(currentConfig.getName());
			SchedulerTask task = SchedulerTask.getInstance(currentConfig, newJob);
			try {
				String id = scheduler.schedule(currentConfig.getSchedulePattern(), task);
				currentConfig.setScheduleId(id);
				logJobConfigActive(currentConfig);
			} catch (Exception e) {
				log.error("Error while scheduling job for execution: " + e.getMessage(), e);
				logJobConfigError(currentConfig, e.getMessage());
			}	
		}
	}

	private void logJobConfigActive(JobConfig job) {
        job.setActive(true);
        job.clearErrorMessage();
		dao.updateJobConfig(job);
	}

	private void doDeschedule() {
		List<JobConfig> jobsConfig = schedulerJobsConfig.getJobsConfigToDeschedule();
		for(JobConfig current : jobsConfig) {
			try {
				scheduler.deschedule(current.getScheduleId());
				logJobConfigRemoved(current);
			} catch (Exception e) {
				log.error("Error while removing job from the scheduler: " + e.getMessage(), e);
				logJobConfigError(current, e.getMessage());
			}
		}
	}

	private void logJobConfigRemoved(JobConfig current) {
		log.info("Job configuration removed from the schedule: " + current);
	}


	private void doReschedule() {
		List<JobConfig> jobs = schedulerJobsConfig.getJobsConfigToReschedule();
		for(JobConfig current : jobs) {
			try {
				scheduler.reschedule(current.getScheduleId(), current.getSchedulePattern());
				logJobConfigActive(current);
			} catch (Exception e) {
				log.error("Reschedule scheduler failure " + e.getMessage(), e);
				logJobConfigError(current, e.getMessage());
			}
		}
	}

    @Transactional(readOnly = true)
    @Override
    public LimitedSearchResult<Job> findJobs(AuthenticatedUser user, JobSearchCriteria criteria, long offset, long limit, List<SortCriterion> sortBy) {
        return dao.findJobs(criteria, offset, limit, sortBy);
    }

    @Transactional(readOnly = true)
    @Override
    public LimitedSearchResult<Job> findJobsHistory(AuthenticatedUser user, JobSearchCriteria criteria, long offset, long limit, List<SortCriterion> sortBy) {
        return dao.findJobsHistory(criteria, offset, limit, sortBy);
    }

    public void jobExist(String name) throws JobDuplicationNameException {
        JobConfig task = dao.getJobConfigByName(name);
        if(task != null) {
            throw new JobDuplicationNameException();
        }
    }

    public boolean validSchedulePattern(String schedule) {
        return SchedulingPattern.validate(schedule);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Integer addJobConfig(AuthenticatedUser user, String name, String schedule) throws JobDuplicationNameException, InvalidSchedulePatternException {
        if(!validSchedulePattern(schedule)) {
            throw new InvalidSchedulePatternException();
        }
        jobExist(name);
        return dao.createJobConfig(name, schedule);
    }

    @Transactional(readOnly = true)
    @Override
    public List<JobConfig> getJobConfigs(AuthenticatedUser user) {
        return dao.getJobConfigs();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void removeJobConfig(AuthenticatedUser user, int id) {
        dao.deleteJobConfigById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public JobConfig getJobConfig(AuthenticatedUser user, int id) {
        return dao.getJobConfigById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void modifyJobConfig(AuthenticatedUser user, int id, String schedulePattern) throws InvalidSchedulePatternException {
        if(!validSchedulePattern(schedulePattern)) {
            throw new InvalidSchedulePatternException();
        }
        JobConfig jobConfig = dao.getJobConfigById(id);
        jobConfig.setSchedulePattern(schedulePattern);
        dao.updateJobConfig(jobConfig);
    }
}
