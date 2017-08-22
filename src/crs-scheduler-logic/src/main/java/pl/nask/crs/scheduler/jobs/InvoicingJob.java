package pl.nask.crs.scheduler.jobs;

import pl.nask.crs.app.invoicing.InvoicingAppService;
import pl.nask.crs.security.authentication.AuthenticatedUser;

public class InvoicingJob extends AbstractJob {

	private final InvoicingAppService invoicingAppService;
	
	public InvoicingJob(InvoicingAppService invoicingAppService) {
        super();
        this.invoicingAppService = invoicingAppService;
    }

    @Override
    public void runJob() {
        AuthenticatedUser user = null;
        invoicingAppService.runInvoicing(user);
    }

    @Override
    public String getJobName() {
        return this.getClass().getSimpleName();
    }
}
