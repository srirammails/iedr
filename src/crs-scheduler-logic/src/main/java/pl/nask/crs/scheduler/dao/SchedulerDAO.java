package pl.nask.crs.scheduler.dao;

import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.scheduler.Job;
import pl.nask.crs.scheduler.JobConfig;
import pl.nask.crs.scheduler.JobSearchCriteria;
import pl.nask.crs.scheduler.JobStatus;
import java.util.List;

public interface SchedulerDAO {
	JobConfig getJobConfigByName(String jobName);
	JobConfig getJobConfigById(Integer jobId);
	List<JobConfig> getJobConfigs();

	Job getJobById(Integer jobId);
	Job getJobByName(String jobName);
	LimitedSearchResult<Job> findJobs(JobSearchCriteria criteria, long offset, long limit, List<SortCriterion> sortBy);
    LimitedSearchResult<Job> findJobsHistory(JobSearchCriteria criteria, long offset, long limit, List<SortCriterion> sortBy);
    List<Job> getJobsByStatus(JobStatus status);

	int createJobConfig(String name, String schedulePattern);
	void deleteJobConfigById(int id);
	void deleteJobConfigByName(String name);
	void updateJobConfig(JobConfig jobConfig);
}
