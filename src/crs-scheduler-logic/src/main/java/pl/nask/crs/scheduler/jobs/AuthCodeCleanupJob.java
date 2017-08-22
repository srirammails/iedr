package pl.nask.crs.scheduler.jobs;

import pl.nask.crs.app.domains.DomainAppService;
import pl.nask.crs.commons.OpInfo;

public class AuthCodeCleanupJob extends AbstractJob {

    private final DomainAppService service;

    public AuthCodeCleanupJob(DomainAppService service) {
        this.service = service;
    }

    @Override
    public void runJob() throws Exception {
        service.authCodeCleanup(new OpInfo("Cleanup"));
    }

    @Override
    public String getJobName() {
        return this.getClass().getSimpleName();
    }

}
