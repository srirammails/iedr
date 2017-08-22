package pl.nask.crs.scheduler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import pl.nask.crs.scheduler.jobs.AbstractJob;

public class JobRegistry {
	private final Map<String, AbstractJob> tasks;

	public JobRegistry(Map<String, AbstractJob> tasks) {
		this.tasks = tasks;
	}
	
	public AbstractJob getTask(String taskName) {
		return tasks.get(taskName);
	}
	
	public List<String> getTaskNames() {
		return new ArrayList<String>(tasks.keySet());
	}
}
