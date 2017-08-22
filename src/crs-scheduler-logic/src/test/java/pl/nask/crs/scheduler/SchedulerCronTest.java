package pl.nask.crs.scheduler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import pl.nask.crs.scheduler.dao.SchedulerDAOImpl;
import pl.nask.crs.scheduler.jobs.AbstractJob;

@ContextConfiguration(locations = {"/scheduler-config.xml", "/scheduler-config-test.xml"})
public class SchedulerCronTest extends AbstractTransactionalTestNGSpringContextTests {

    @Resource
    SchedulerDAOImpl dao;

    @Resource
    SchedulerCron schedulerCron;

    static Logger log = Logger.getLogger(SchedulerCronTest.class);

    private AbstractJob emptyTask = new EmptyTask();

    @Test
    public void reloadTest() {
        AssertJUnit.assertTrue(schedulerCron.start());
        AssertJUnit.assertTrue(schedulerCron.reload());
        AssertJUnit.assertTrue(schedulerCron.stop());
    }
//TODO: CRS-72
//    @Test
//    public void jobConfigValidatorTest() {
//        Map<String, AbstractJob> tasks = new HashMap<String, AbstractJob>();
//        tasks.put("SampleJobOne", emptyTask);
//        tasks.put("SampleJobTwo", emptyTask);
//        tasks.put("SampleJobThree", emptyTask);
//        tasks.put("Updater", emptyTask);
//        JobRegistry testRegistry = new JobRegistry(tasks);
//
//        JobConfigValidator jobConfigValidator = new JobConfigValidator(testRegistry);
//        List<JobConfig> jobConfig = dao.getJobConfigs();
//        for (JobConfig current : jobConfig) {
//            assertValid(jobConfigValidator, current);
//        }
//
//        JobConfig job = new JobConfig();
//        String unnormalJobName = "UpdaterFailed";
//        String unnormalJobPattern = "* * * * * failed";
//        job.setName(unnormalJobName);
//        job.setSchedulePattern(unnormalJobPattern);
//
//        AssertJUnit.assertFalse(jobConfigValidator.hasImplementation(job));
//        AssertJUnit.assertFalse(jobConfigValidator.hasValidSchedulePattern(job));
//    }
//
//    private void assertValid(JobConfigValidator jobConfigValidator, JobConfig current) {
//        AssertJUnit.assertTrue(current.getName() + " has an implementation", jobConfigValidator.hasImplementation(current));
//        AssertJUnit.assertTrue(current.getName() + " has valid schema pattern", jobConfigValidator.hasValidSchedulePattern(current));
//    }

    @Test
    public void getJobToScheduleTest() {
        dao.deleteJobConfigByName("Updater");
        List<JobConfig> jobsConfig = dao.getJobConfigs();
        SchedulerJobsConfig schedulerJobs = new SchedulerJobsConfig();

        schedulerJobs.updateConfiguration(jobsConfig);
        int size = schedulerJobs.getJobsConfigToSchedule().size();
        AssertJUnit.assertTrue(jobsConfig.size() == size);

        dao.createJobConfig("Updater", "1 * * * *");
        jobsConfig = dao.getJobConfigs();

        schedulerJobs.updateConfiguration(jobsConfig);
        size = schedulerJobs.getJobsConfigToSchedule().size();
        AssertJUnit.assertTrue(size == 1);
    }

    @Test
    public void getJobToDecheduleTest() {
        List<JobConfig> jobsConfig = dao.getJobConfigs();
        SchedulerJobsConfig schedulerJobs = new SchedulerJobsConfig();
        schedulerJobs.updateConfiguration(jobsConfig);
        schedulerJobs.getJobsConfigToSchedule();

        //DELETE TOW JOBS TO CALL DESCHEDULE ACTION
        dao.deleteJobConfigById(3);
        dao.deleteJobConfigById(4);

        //LOAD NEW CONFIGS
        jobsConfig = dao.getJobConfigs();

        schedulerJobs.updateConfiguration(jobsConfig);

        //CHECK IF DESCHEDULE EQUALS TWO
        int size = schedulerJobs.getJobsConfigToDeschedule().size();
        AssertJUnit.assertTrue(size == 2);
    }

// TODO: CRS-72
//    @Test
//    public void getJobToRecheduleTest() {
//        List<JobConfig> jobsConfig = dao.getJobConfigs();
//        SchedulerJobsConfig schedulerJobs = new SchedulerJobsConfig();
//        schedulerJobs.updateConfiguration(jobsConfig);
//        schedulerJobs.getJobsConfigToSchedule();
//
//        //UPDATE TOW JOBS TO CALL DESCHEDULE ACTION
//        JobConfig job1 = dao.getJobConfigById(1);
//        job1.setSchedulePattern("*/3 * * * *");
//        JobConfig job2 = dao.getJobConfigById(2);
//        job2.setSchedulePattern("*/6 * * * *");
//
//        dao.updateJobConfig(job1);
//        dao.updateJobConfig(job2);
//
//        //LOAD NEW CONFIGS
//        jobsConfig = dao.getJobConfigs();
//
//        //CHECK IF JOBS TO RESCHEDULE EQUALS TWO
//        schedulerJobs.updateConfiguration(jobsConfig);
//        int size = schedulerJobs.getJobsConfigToReschedule().size();
//        AssertJUnit.assertEquals(4, size);
//    }

    @Test(enabled = false) // breaks transaction model
    public void linkTestForInvokeTest() {
        schedulerCron.start();
        schedulerCron.invoke("EmptyJob");
        schedulerCron.stop();
    }
}
