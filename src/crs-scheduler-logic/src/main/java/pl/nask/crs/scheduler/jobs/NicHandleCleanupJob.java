package pl.nask.crs.scheduler.jobs;

import pl.nask.crs.app.nichandles.NicHandleAppService;


public class NicHandleCleanupJob extends AbstractJob {
	private final  NicHandleAppService service;
	
	public NicHandleCleanupJob(NicHandleAppService service) {
		super();
		this.service = service;
	}

    @Override
    public void runJob() {
		service.removeDeletedNichandles();
    }

    @Override
    public String getJobName() {
        return this.getClass().getSimpleName();
    }
}
