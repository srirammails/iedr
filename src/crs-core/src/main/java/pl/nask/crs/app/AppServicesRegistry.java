package pl.nask.crs.app;

import pl.nask.crs.app.accounts.AccountAppService;
import pl.nask.crs.app.domains.DomainAppService;
import pl.nask.crs.app.invoicing.InvoicingAppService;
import pl.nask.crs.app.nichandles.NicHandleAppService;
import pl.nask.crs.app.payment.PaymentAppService;
import pl.nask.crs.app.reports.ReportsAppService;
import pl.nask.crs.app.tickets.TicketAppService;
import pl.nask.crs.app.users.UserAppService;

public interface AppServicesRegistry {

	public NicHandleAppService getNicHandleAppService();

	public TicketAppService getTicketAppService();

	public UserAppService getUserAppService();

	public ReportsAppService getReportsAppService();

	public PaymentAppService getPaymentAppService();

	public DomainAppService getDomainAppService();

	public AccountAppService getAccountAppService();

	public InvoicingAppService getInvoicingAppService();
   
}