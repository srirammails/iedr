package pl.nask.crs.app;

import java.lang.reflect.Field;

import pl.nask.crs.app.accounts.AccountAppService;
import pl.nask.crs.app.domains.DomainAppService;
import pl.nask.crs.app.invoicing.InvoicingAppService;
import pl.nask.crs.app.nichandles.NicHandleAppService;
import pl.nask.crs.app.payment.PaymentAppService;
import pl.nask.crs.app.reports.ReportsAppService;
import pl.nask.crs.app.tickets.TicketAppService;
import pl.nask.crs.app.users.UserAppService;
import pl.nask.crs.commons.utils.Validator;

public class AppServicesRegistryImpl implements AppServicesRegistry {
	
	private NicHandleAppService nicHandleAppService;
	private TicketAppService ticketAppService;
	private UserAppService userAppService;
	private ReportsAppService reportsAppService;
	private PaymentAppService paymentAppService;
	private DomainAppService domainAppService;
	private AccountAppService accountAppService;
	private InvoicingAppService invoiceAppService;

	public void validate() {
		try {
			Field[] fields = getClass().getDeclaredFields();
			for (Field f: fields) {
				Validator.assertNotNull(f.get(this), f.getName());
			}
		} catch (Exception e) {
			throw new IllegalStateException("Object not properly initialized, one of the services is missing", e);
		}
	}
	

	/* (non-Javadoc)
	 * @see pl.nask.crs.app.Services#getNicHandleAppService()
	 */
	public NicHandleAppService getNicHandleAppService() {
		return nicHandleAppService;
	}
	public void setNicHandleAppService(NicHandleAppService nicHandleAppService) {
		this.nicHandleAppService = nicHandleAppService;
	}
	/* (non-Javadoc)
	 * @see pl.nask.crs.app.Services#getTicketAppService()
	 */
	public TicketAppService getTicketAppService() {
		return ticketAppService;
	}
	public void setTicketAppService(TicketAppService ticketAppService) {
		this.ticketAppService = ticketAppService;
	}
	/* (non-Javadoc)
	 * @see pl.nask.crs.app.Services#getUserAppService()
	 */
	public UserAppService getUserAppService() {
		return userAppService;
	}
	public void setUserAppService(UserAppService userAppService) {
		this.userAppService = userAppService;
	}
	/* (non-Javadoc)
	 * @see pl.nask.crs.app.Services#getReportsAppService()
	 */
	public ReportsAppService getReportsAppService() {
		return reportsAppService;
	}
	public void setReportsAppService(ReportsAppService reportsAppService) {
		this.reportsAppService = reportsAppService;
	}
	/* (non-Javadoc)
	 * @see pl.nask.crs.app.Services#getPaymentAppService()
	 */
	public PaymentAppService getPaymentAppService() {
		return paymentAppService;
	}
	public void setPaymentAppService(PaymentAppService paymentAppService) {
		this.paymentAppService = paymentAppService;
	}
	/* (non-Javadoc)
	 * @see pl.nask.crs.app.Services#getDomainAppService()
	 */
	public DomainAppService getDomainAppService() {
		return domainAppService;
	}
	public void setDomainAppService(DomainAppService domainAppService) {
		this.domainAppService = domainAppService;
	}
	/* (non-Javadoc)
	 * @see pl.nask.crs.app.Services#getAccountAppService()
	 */
   @Override
	public AccountAppService getAccountAppService() {
		return accountAppService;
	}
	public void setAccountAppService(AccountAppService accountAppService) {
		this.accountAppService = accountAppService;
	}
   
	@Override
	public InvoicingAppService getInvoicingAppService() {
		return invoiceAppService;
	}
	
	public void setInvoicingAppService(InvoicingAppService invoiceAppService) {
		this.invoiceAppService = invoiceAppService;
	}
}
