package pl.nask.crs.api.triplepass;

import javax.jws.WebService;

import org.apache.log4j.Logger;

import pl.nask.crs.api.WsSessionAware;
import pl.nask.crs.api.validation.ValidationHelper;
import pl.nask.crs.api.vo.AuthenticatedUserVO;
import pl.nask.crs.app.triplepass.TriplePassAppService;
import pl.nask.crs.app.triplepass.TriplePassSupportService;
import pl.nask.crs.app.triplepass.exceptions.FinancialCheckException;
import pl.nask.crs.app.triplepass.exceptions.TicketIllegalStateException;
import pl.nask.crs.commons.OpInfo;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.ticket.exceptions.TicketNotFoundException;

@WebService(serviceName="CRSTriplePass", endpointInterface="pl.nask.crs.api.triplepass.CRSTriplePass")
public class TriplePassEndpoint extends WsSessionAware implements CRSTriplePass {
	private static final Logger LOG = Logger.getLogger(TriplePassEndpoint.class);
		
	private TriplePassAppService triplePassService;
	
	private TriplePassSupportService triplePassSupportService;
	
	public void setTriplePassService(TriplePassAppService service) {
		this.triplePassService = service;
	}
	
	public void setTriplePassSupportService(TriplePassSupportService triplePassSupportService) {
		this.triplePassSupportService = triplePassSupportService;
	}
	
	@Override
	public boolean triplePass(AuthenticatedUserVO user, String domainName) {
    	ValidationHelper.validate(user);
        AuthenticatedUser completeUser = validateSessionAndRetrieveFullUserInfo(user);
        return triplePassService.triplePass(completeUser, domainName);
	}
	
	@Override
	public boolean financialCheck(AuthenticatedUserVO user, long ticketId) {
        try {
            triplePassSupportService.performFinancialCheck(user, ticketId);
            return true;
        } catch (FinancialCheckException e) {
            String msg = "Exception during financial checking: " + e.getMessage();
            LOG.error(msg, e);
            throw new IllegalStateException(msg);
        } catch (TicketIllegalStateException e) {
            String msg = "Illegal ticket state: " + e.getMessage();
            LOG.error(msg, e);
            throw new IllegalArgumentException(msg);
        } catch (TicketNotFoundException e) {
            String msg = "There is no ticket with id=" + ticketId;
            LOG.error(msg, e);
            throw new IllegalArgumentException(msg);
        }
    }

	@Override
	public boolean promoteTicketToDomain(AuthenticatedUserVO user, long ticketId) {
		try {
			triplePassSupportService.promoteTicketToDomain(user, ticketId);	
			return true;
		} catch (TicketNotFoundException e) {
			String msg = "There is no ticket with id=" + ticketId;
			LOG.error(msg, e);
			throw new IllegalArgumentException(msg);
		} catch (TicketIllegalStateException e) {
			String msg = "Illegal ticket state: " + e.getMessage();
			LOG.error(msg, e);
			throw new IllegalArgumentException(msg);
		}
	}

    @Override
    public void giboAdminFailed(AuthenticatedUserVO user, String domainName, String remark) {
        triplePassSupportService.giboAdminFailed(user, domainName, new OpInfo(user, remark));
    }
}
