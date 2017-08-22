package pl.nask.crs.scheduler.jobs;


import pl.nask.crs.app.domains.DomainAppService;

public class RenewalNotificationJob extends AbstractJob {

    private final DomainAppService domainAppService;

    public RenewalNotificationJob(DomainAppService domainAppService) {
        this.domainAppService = domainAppService;
    }

    @Override
    public void runJob() {
        domainAppService.runNotificationProcess(null);
    }

    @Override
    public String getJobName() {
        return this.getClass().getSimpleName();
    }
}
