package pl.nask.crs.scheduler;

import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.scheduler.exceptions.InvalidSchedulePatternException;
import pl.nask.crs.scheduler.exceptions.JobDuplicationNameException;
import pl.nask.crs.security.authentication.AuthenticatedUser;

import java.util.List;

/**
 *
 * @author Artur Kielak
 */
public interface SchedulerCron {
	boolean reload();
	boolean start();
	boolean stop();
	void invoke(String taskName);
	boolean isRunning();

    LimitedSearchResult<Job> findJobs(AuthenticatedUser user, JobSearchCriteria criteria, long offset, long limit, List<SortCriterion> sortBy);
    LimitedSearchResult<Job> findJobsHistory(AuthenticatedUser user, JobSearchCriteria criteria, long offset, long limit, List<SortCriterion> sortBy);

    Integer addJobConfig(AuthenticatedUser user, String name, String schedule) throws JobDuplicationNameException, InvalidSchedulePatternException;
    List<JobConfig> getJobConfigs(AuthenticatedUser user);
    void removeJobConfig(AuthenticatedUser user, int id);
    JobConfig getJobConfig(AuthenticatedUser user, int id);
    void modifyJobConfig(AuthenticatedUser user, int id, String schedulePattern) throws InvalidSchedulePatternException;
}
