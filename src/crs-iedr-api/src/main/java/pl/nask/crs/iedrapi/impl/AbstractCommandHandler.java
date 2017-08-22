package pl.nask.crs.iedrapi.impl;

import pl.nask.crs.api.vo.AuthenticatedUserVO;
import pl.nask.crs.app.commons.CommonAppService;
import pl.nask.crs.app.domains.DomainAppService;
import pl.nask.crs.app.nichandles.NicHandleAppService;
import pl.nask.crs.app.payment.PaymentAppService;
import pl.nask.crs.app.tickets.TicketAppService;
import pl.nask.crs.commons.dictionary.Dictionary;
import pl.nask.crs.domains.BillingStatus;
import pl.nask.crs.iedrapi.exceptions.CommandFailed;
import pl.nask.crs.iedrapi.exceptions.ParameterValueRangeErrorException;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.security.authentication.AuthenticationService;
import pl.nask.crs.ticket.AdminStatus;
import pl.nask.crs.ticket.Ticket;

/**
 * Easy to extend, delegates method calls to CommandHandlerHelper
 * 
 * @author Artur Gniadzik
 *
 */
public class AbstractCommandHandler {
	private CommandHandlerHelper helper;
    
    public CommandHandlerHelper getHelper() {
		return helper;
	}

	public void setHelper(CommandHandlerHelper helper) {
		this.helper = helper;
	}
	
	public long getAccountId(AuthenticatedUserVO user) throws AccessDeniedException, CommandFailed {
        return helper.getAccountId(user);
    }

    public String getAccountName(AuthenticatedUserVO user) throws AccessDeniedException, CommandFailed {
        return helper.getAccountName(user);
    }

	public void validatePage(long total, long offset) throws ParameterValueRangeErrorException {
		helper.validatePage(total, offset);
	}

	public NicHandleAppService getNicHandleAppService() {
		return helper.getNicHandleAppService();
	}

	public AuthenticationService getAuthService() {
		return helper.getAuthService();
	}

	public DomainAppService getDomainAppService() {
		return helper.getDomainAppService();
	}

	public PaymentAppService getPaymentAppService() {
		return helper.getPaymentAppService();
	}

	public TicketAppService getTicketAppService() {
		return helper.getTicketAppService();
	}
	
	public Ticket viewTicketForDomain(AuthenticatedUser user, String domainName) throws CommandFailed, AccessDeniedException {
		return helper.viewTicketForDomain(user, domainName);
	}

	public Dictionary<String, BillingStatus> getBillingStatusDictionary() {
		return helper.getBillingStatusDictionary();
	}

	public Dictionary<Integer, AdminStatus> getAdminStatusDictionary() {
		return helper.getAdminStatusDictionary();
	}

    public CommonAppService getCommonAppService() {
    	return helper.getCommonAppService();
    }
}
