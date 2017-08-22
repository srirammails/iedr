package pl.nask.crs.web.task;

import java.util.List;

import pl.nask.crs.scheduler.JobRegistry;
import pl.nask.crs.scheduler.SchedulerCron;
import pl.nask.crs.web.security.AuthenticatedUserAwareAction;

@Deprecated
public class TaskInstrumentationAction extends AuthenticatedUserAwareAction {
	
	private final SchedulerCron scheduler;
	
	// a task name to be runned
	private String taskName;
	private List<String> validTaskNames;

	public TaskInstrumentationAction(JobRegistry jobRegistry, SchedulerCron scheduler) {
		this.validTaskNames = jobRegistry.getTaskNames();
		this.scheduler = scheduler;
	}
	
	public String runTask() {
		if (!scheduler.isRunning()) {
			addActionError("The process cannot be manually run if the scheduler is not running");
			return ERROR;
		} else {
			scheduler.invoke(taskName);
			return INPUT;
		}
	}

	public String startScheduler() {
		scheduler.start();
		return INPUT;
	}

	public String stopScheduler() {
		scheduler.stop();
		return INPUT;
	}

	public String reloadScheduler() {
		scheduler.reload();
		return INPUT;
	}
	
	public boolean isSchedulerRunning() {
		return scheduler.isRunning();
	}
	
	public List<String> getValidTaskNames() {
		return validTaskNames;
	}
	
	public String getTaskName() {
		return taskName;
	}
	
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

}
