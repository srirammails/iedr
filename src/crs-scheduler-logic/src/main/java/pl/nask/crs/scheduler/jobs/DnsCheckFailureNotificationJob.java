package pl.nask.crs.scheduler.jobs;


import pl.nask.crs.dnscheck.DnsNotificationService;

public class DnsCheckFailureNotificationJob extends AbstractJob {
    //FIXME app service required ?
    private DnsNotificationService service;

    public DnsCheckFailureNotificationJob(DnsNotificationService service) {
        this.service = service;
    }

    @Override
    public void runJob() {
        service.sendNotifications();
    }

    @Override
    public String getJobName() {
        return this.getClass().getSimpleName();
    }
}
