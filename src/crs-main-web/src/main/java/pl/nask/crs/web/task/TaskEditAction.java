package pl.nask.crs.web.task;

import pl.nask.crs.scheduler.JobConfig;
import pl.nask.crs.scheduler.SchedulerCron;
import pl.nask.crs.scheduler.exceptions.InvalidSchedulePatternException;
import pl.nask.crs.web.security.AuthenticatedUserAwareAction;

/**
 * (C) Copyright 2013 NASK
 * Software Research & Development Department
 */
public class TaskEditAction extends AuthenticatedUserAwareAction {

    private SchedulerCron schedulerCron;
    private Integer id;
    private TaskWrapper wrapper;

    public TaskEditAction(SchedulerCron schedulerCron) {
        this.schedulerCron = schedulerCron;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public TaskWrapper getWrapper() {
        return wrapper;
    }

    public void setWrapper(TaskWrapper wrapper) {
        this.wrapper = wrapper;
    }

    @Override
    public String input() throws Exception {
        JobConfig jobConfig = schedulerCron.getJobConfig(getUser(), id);
        wrapper = new TaskWrapper(jobConfig);
        return INPUT;
    }

    public String save() {
        try {
            schedulerCron.modifyJobConfig(getUser(), wrapper.getId(), wrapper.getSchedule());
            return SUCCESS;
        } catch (InvalidSchedulePatternException e) {
            addActionError("Invalid schedule pattern");
            return ERROR;
        }
    }
}
