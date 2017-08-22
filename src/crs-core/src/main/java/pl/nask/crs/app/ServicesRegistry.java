package pl.nask.crs.app;

import pl.nask.crs.accounts.services.AccountSearchService;
import pl.nask.crs.accounts.services.AccountService;
import pl.nask.crs.commons.email.service.EmailTemplateSender;
import pl.nask.crs.dnscheck.DnsCheckService;
import pl.nask.crs.domains.services.DomainSearchService;
import pl.nask.crs.domains.services.DomainService;
import pl.nask.crs.nichandle.service.NicHandleSearchService;
import pl.nask.crs.nichandle.service.NicHandleService;
import pl.nask.crs.payment.service.DepositService;
import pl.nask.crs.payment.service.InvoiceNumberService;
import pl.nask.crs.payment.service.PaymentService;
import pl.nask.crs.price.PriceService;
import pl.nask.crs.ticket.services.TicketSearchService;
import pl.nask.crs.ticket.services.TicketService;
import pl.nask.crs.vat.VatService;

public interface ServicesRegistry {
	public DomainSearchService getDomainSearchService();

	public AccountService getAccountService();
	
	public AccountSearchService getAccountSearchService();
	
	public NicHandleService getNicHandleService();
	
	public NicHandleSearchService getNicHandleSearchService();

	public PaymentService getPaymentService();

    public TicketSearchService getTicketSearchService();

    public TicketService getTicketService();

    public DnsCheckService getDnsCheckService();

    public DomainService getDomainService();

    public InvoiceNumberService getInvoicingService();

    public EmailTemplateSender getEmailTemplateSender();

    public VatService getVatService();

    public PriceService getPriceService();
    
    public DepositService getDepositService();
    
}
