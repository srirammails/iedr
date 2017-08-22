package pl.nask.crs.scheduler.jobs;

import pl.nask.crs.app.domains.DomainAppService;
import pl.nask.crs.commons.OpInfo;
import pl.nask.crs.security.authentication.AuthenticatedUser;

public class PushQ extends AbstractJob {
	private final DomainAppService domainAppService;
	
	public PushQ(DomainAppService domainAppService) {
		this.domainAppService = domainAppService;
	}

    @Override
    public void runJob() {
        AuthenticatedUser user = null;
        domainAppService.pushQ(user, new OpInfo("PushQ"));
    }

    @Override
    public String getJobName() {
        return this.getClass().getSimpleName();
    }
}
