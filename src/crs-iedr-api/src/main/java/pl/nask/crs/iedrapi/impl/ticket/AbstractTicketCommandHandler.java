package pl.nask.crs.iedrapi.impl.ticket;

import pl.nask.crs.iedrapi.impl.AbstractCommandHandler;
import pl.nask.crs.iedrapi.exceptions.*;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.ticket.Ticket;
import pl.nask.crs.xml.Constants;
import pl.nask.crs.nichandle.NicHandle;
import pl.nask.crs.nichandle.search.NicHandleSearchCriteria;
import pl.nask.crs.commons.search.LimitedSearchResult;

import java.util.Set;
import java.util.List;
import java.util.HashSet;

public class AbstractTicketCommandHandler extends AbstractCommandHandler {
	
    public static final String TICKET_NAMESPACE = Constants.IEAPI_TICKET_NAMESPACE;    

    public void checkTicketContacts(AuthenticatedUser user, long accountId, List<String> contacts, int min, int max, ReasonCode minReason, ReasonCode maxReason, int contactsCount) throws IedrApiException, AccessDeniedException {
        if (contacts == null || contacts.size() < min)
            throw new DataManagementPolicyViolationException(minReason);
        if (contactsCount > max)
            throw new DataManagementPolicyViolationException(maxReason);

        Set<String> contactsSet = new HashSet<String>();
        for (String c : contacts) {
            if (!contactsSet.add(c))
                throw new ParamValuePolicyErrorException(ReasonCode.DUPLICATE_CONTACT_ID, new Value("contact", TICKET_NAMESPACE, c));
            if (!c.endsWith("-IEDR"))
                throw new ParamValueSyntaxErrorException(ReasonCode.CONTACT_ID_SYNTAX_ERROR, new Value("contact", TICKET_NAMESPACE, c));

            NicHandleSearchCriteria criteria = new NicHandleSearchCriteria();
            criteria.setNicHandleId(c);
            criteria.setAccountNumber(accountId);
            LimitedSearchResult<NicHandle> searchResult =  getNicHandleAppService().search(user, criteria, 0, 10, null);
            if (searchResult.getTotalResults() == 0)
                throw new ParameterValueRangeErrorException(ReasonCode.CONTACT_ID_DOESNT_EXIST, new Value("contact", TICKET_NAMESPACE, c));
        }
    }

    public void checkTicket(AuthenticatedUser user, Ticket ticket, String domainName) throws AuthorizationErrorException, ObjectDoesNotExistException {
        if (ticket == null)
			throw new ObjectDoesNotExistException(ReasonCode.TICKET_NAME_DOES_NOT_EXIST, new Value("name", TICKET_NAMESPACE, domainName));

        if (!ticket.getOperation().getBillingContactsField().get(0).getNewValue().getNicHandle().equals(user.getUsername()))
            throw new AuthorizationErrorException(
                    ReasonCode.TICKET_IS_MANAGED_BY_ANOTHER_RESELLER,
                    new Value("name", TICKET_NAMESPACE, ticket.getOperation().getDomainNameField().getNewValue()));
    }

}
