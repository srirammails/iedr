package pl.nask.crs.scheduler.jobs;

import pl.nask.crs.app.invoicing.InvoicingAppService;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import it.sauronsoftware.cron4j.TaskExecutionContext;

public class TransactionInvalidationJob extends AbstractJob {
	private final InvoicingAppService invoicingAppService;
	
	public TransactionInvalidationJob(InvoicingAppService invoicingAppService) {
		this.invoicingAppService = invoicingAppService;
	}

    @Override
    public void runJob() {
    	AuthenticatedUser user = null;
		invoicingAppService.runTransactionInvalidation(user);
    }

    @Override
    public String getJobName() {
        return this.getClass().getSimpleName();
    }
}
