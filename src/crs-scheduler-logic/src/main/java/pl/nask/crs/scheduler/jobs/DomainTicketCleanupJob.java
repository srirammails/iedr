package pl.nask.crs.scheduler.jobs;


import pl.nask.crs.app.commons.CommonAppService;
import pl.nask.crs.commons.OpInfo;
import pl.nask.crs.security.authentication.AuthenticatedUser;

public class DomainTicketCleanupJob extends AbstractJob {

    private CommonAppService commonAppService;

    public DomainTicketCleanupJob(CommonAppService commonAppService) {
        this.commonAppService = commonAppService;
    }

    @Override
    public void runJob() {
        AuthenticatedUser user = null;
        commonAppService.cleanupTickets(user, new OpInfo("Cleanup"));
    }

    @Override
    public String getJobName() {
        return this.getClass().getSimpleName();
    }
}
