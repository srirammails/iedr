package pl.nask.crs.scheduler;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.scheduler.dao.SchedulerDAOImpl;

@ContextConfiguration(locations = {"/scheduler-config.xml", "/scheduler-config-test.xml"})
public class SchedulerTest extends AbstractTransactionalTestNGSpringContextTests {

    static Logger log = Logger.getLogger(SchedulerTest.class);
    @Resource
    SchedulerDAOImpl dao;

    @Test
    public void getJobConfigByName() {
        JobConfig config = dao.getJobConfigByName("Updater");
        AssertJUnit.assertNotNull("getJobConfigByName()(test)", config);

        log.debug("id=" + config.getId());
        log.debug("job=" + config.getName());
        log.debug("schedule=" + config.getSchedulePattern());
    }

    @Test
    public void getJobConfigById() {
        JobConfig config = dao.getJobConfigById(1);
        AssertJUnit.assertNotNull("getJobConfigById()(test)", config);

        log.debug("id=" + config.getId());
        log.debug("job=" + config.getName());
        log.debug("schedule=" + config.getSchedulePattern());
    }

    @Test
    public void getJobByName() {
        Job job = dao.getJobByName("job1");
        AssertJUnit.assertNotNull("getJobByName()(test)", job);
    }

    @Test
    public void getJobById() {
        Job job = dao.getJobById(1);

        AssertJUnit.assertNotNull("getJobById()(test)", job);
    }

    @Test
    public void getJobsByStatus() {
        List<Job> jobs = dao.getJobsByStatus(JobStatus.ACTIVE);
        for (Job l : jobs) {
            log.debug("getJobsByStatus = " + l.getStart());
        }
        AssertJUnit.assertNotNull("getJobById()(test)", jobs);
    }

    @Test
    public void getJobs() {
        LimitedSearchResult<Job> jobs = dao.findJobs(new JobSearchCriteria(), 0, 10, null);
        AssertJUnit.assertNotNull("getJobs()(test)", jobs);
        for (Job j : jobs.getResults()) {
            log.debug("getJobs = " + j);
        }
        AssertJUnit.assertTrue(jobs.getTotalResults() >= 4);
        AssertJUnit.assertTrue(jobs.getResults().size() >= 4);
    }

    @Test
    public void getJobsHist() {
        LimitedSearchResult<Job> jobs = dao.findJobsHistory(new JobSearchCriteria(), 0, 10, null);
        AssertJUnit.assertNotNull("getJobsHist()(test)", jobs);
        AssertJUnit.assertTrue(jobs.getTotalResults() >= 4);
    }


    @Test
    public void getJobConfigs() {
        List<JobConfig> jobConfigs = dao.getJobConfigs();
        for (JobConfig j : jobConfigs) {
            log.debug("id = " + j.getId());
            log.debug("name = " + j.getName());
            log.debug("schedule = " + j.getSchedulePattern());
        }
        AssertJUnit.assertNotNull("getJobConfigs()(test)", jobConfigs);
    }

    @Test
    public void logJobStartedTest() {
        String jobName = "invoicing";
        dao.logJobStarted(jobName);
        Job job = dao.getJobByName(jobName);
        AssertJUnit.assertNotNull(job);
    }

    @Test
    public void updateConfigStatusTest() throws Exception {
        JobConfig jobConfig = dao.getJobConfigById(3);
        AssertJUnit.assertNull(jobConfig.getScheduleId());
        jobConfig.setScheduleId("scheduleId");
        dao.updateJobConfig(jobConfig);
        jobConfig = dao.getJobConfigById(3);
        AssertJUnit.assertEquals("scheduleId", jobConfig.getScheduleId());
    }

}
