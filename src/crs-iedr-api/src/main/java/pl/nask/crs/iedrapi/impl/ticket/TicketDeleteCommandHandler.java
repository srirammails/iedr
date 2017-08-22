package pl.nask.crs.iedrapi.impl.ticket;

import ie.domainregistry.ieapi_1.ResponseType;
import ie.domainregistry.ieapi_ticket_1.SNameType;
import pl.nask.crs.app.commons.exceptions.CancelTicketException;
import pl.nask.crs.iedrapi.APICommandHandler;
import pl.nask.crs.iedrapi.ApiValidator;
import pl.nask.crs.iedrapi.AuthData;
import pl.nask.crs.iedrapi.ResponseTypeFactory;
import pl.nask.crs.iedrapi.ValidationCallback;
import pl.nask.crs.iedrapi.exceptions.*;
import pl.nask.crs.payment.exceptions.PaymentException;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.ticket.Ticket;
import pl.nask.crs.ticket.exceptions.TicketNotFoundException;

public class TicketDeleteCommandHandler extends AbstractTicketCommandHandler implements APICommandHandler<SNameType> {

    @Override
    public ResponseType handle(AuthData auth, SNameType command, ValidationCallback callback)
            throws AccessDeniedException, IedrApiException {
    	ApiValidator.assertNoError(callback);
        AuthenticatedUser user = auth.getUser();
        Ticket t = viewTicketForDomain(user, command.getName());

        checkTicket(user, t, command.getName());

        try {
            getCommonAppService().cancel(user, t.getId());
        } catch (TicketNotFoundException e) {
            throw new ObjectDoesNotExistException(ReasonCode.TICKET_NAME_DOES_NOT_EXIST, new Value("name", TICKET_NAMESPACE, command.getName()));
        } catch (CancelTicketException e) {
            throw new CommandFailed(e);
        } catch (PaymentException e) {
            throw new CommandFailed(e);
        }
        return ResponseTypeFactory.success();
    }

}
