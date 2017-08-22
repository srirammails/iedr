/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.nask.crs.web.task;

import java.util.List;

import org.apache.log4j.Logger;
import pl.nask.crs.scheduler.JobConfig;
import pl.nask.crs.scheduler.SchedulerCron;
import pl.nask.crs.web.security.AuthenticatedUserAwareAction;

/**
 * @author Artur Kielak
 */
public class TaskViewAction extends AuthenticatedUserAwareAction {

    private Logger log = Logger.getLogger(TaskViewAction.class);
    private SchedulerCron schedulerCron;
    private Integer id;

    public TaskViewAction(SchedulerCron schedulerCron) {
        this.schedulerCron = schedulerCron;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<JobConfig> getTaskList() throws Exception {
        List<JobConfig> tasks = null;
        try {
            tasks = schedulerCron.getJobConfigs(getUser());
        } catch (Exception e) {
            addActionError("Exception " + e.getMessage());
        }
        return tasks;
    }

    public String remove() {
        try {
            schedulerCron.removeJobConfig(getUser(), id);
            return SUCCESS;
        } catch (Exception e) {
            addActionError("Exception " + e.getMessage());
            return ERROR;
        }
    }
}
