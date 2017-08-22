package pl.nask.crs.app;

import java.lang.reflect.Field;

import pl.nask.crs.accounts.services.AccountSearchService;
import pl.nask.crs.accounts.services.AccountService;
import pl.nask.crs.commons.email.service.EmailTemplateSender;
import pl.nask.crs.commons.utils.Validator;
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

public class ServicesRegistryImpl implements ServicesRegistry {
	private AccountService accountService;
	private DomainSearchService domainSearchService;
	private NicHandleSearchService nicHandleSearchService;
	private NicHandleService nicHandleService;
	private PaymentService paymentService;
	private AccountSearchService accountSearchService;
	private TicketSearchService ticketSearchService;
    private TicketService ticketService;
	private DnsCheckService dnsCheckService;
    private DomainService domainService;
    private InvoiceNumberService invoicingService;
    private EmailTemplateSender emailTemplateSender;
    private VatService vatService;
    private PriceService priceService;
    private DepositService depositService;

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
	
	@Override
	public AccountService getAccountService() {	
		return accountService;
	}	
	public void setAccountService(AccountService accountService) {
		this.accountService = accountService;
	}
	
	@Override
	public DomainSearchService getDomainSearchService() {
		return domainSearchService;
	}
	public void setDomainSearchService(DomainSearchService domainSearchService) {
		this.domainSearchService = domainSearchService;
	}

	@Override
	public NicHandleSearchService getNicHandleSearchService() {
		return nicHandleSearchService;
	}
	public void setNicHandleSearchService(NicHandleSearchService nicHandleSearchService) {
		this.nicHandleSearchService = nicHandleSearchService;
	}
	
	@Override
	public NicHandleService getNicHandleService() {	
		return nicHandleService;
	}
	public void setNicHandleService(NicHandleService nicHandleService) {
		this.nicHandleService = nicHandleService;
	}
	
	@Override
	public PaymentService getPaymentService() {
		return paymentService;
	}
	public void setPaymentService(PaymentService paymentService) {
		this.paymentService = paymentService;
	}
	
	@Override
	public AccountSearchService getAccountSearchService() { 
		return accountSearchService;
	}
	public void setAccountSearchService(AccountSearchService accountSearchService) {
		this.accountSearchService = accountSearchService;
	}

    @Override
    public TicketSearchService getTicketSearchService() {
        return ticketSearchService;
    }

    public void setTicketSearchService(TicketSearchService ticketSearchService) {
        this.ticketSearchService = ticketSearchService;
    }

    @Override
    public TicketService getTicketService() {
        return ticketService;
    }

    public void setTicketService(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @Override
    public DnsCheckService getDnsCheckService() {
        return dnsCheckService;
    }

    public void setDnsCheckService(DnsCheckService dnsCheckService) {
        this.dnsCheckService = dnsCheckService;
    }

    @Override
    public DomainService getDomainService() {
        return domainService;
    }

    public void setDomainService(DomainService domainService) {
        this.domainService = domainService;
    }

    @Override
    public InvoiceNumberService getInvoicingService() {
        return invoicingService;
    }

    public void setInvoicingService(InvoiceNumberService invoicingService) {
        this.invoicingService = invoicingService;
    }

    @Override
    public EmailTemplateSender getEmailTemplateSender() {
        return emailTemplateSender;
    }

    public void setEmailTemplateSender(EmailTemplateSender emailTemplateSender) {
        this.emailTemplateSender = emailTemplateSender;
    }

    @Override
    public VatService getVatService() {
        return vatService;
    }

    public void setVatService(VatService vatService) {
        this.vatService = vatService;
    }

    @Override
    public PriceService getPriceService() {
        return priceService;
    }

    public void setPriceService(PriceService priceService) {
        this.priceService = priceService;
    }

	public DepositService getDepositService() {
		return depositService;
	}

	public void setDepositService(DepositService depositService) {
		this.depositService = depositService;
	}
    
    
}
