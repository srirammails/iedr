package pl.nask.crs.iedrapi.impl.ticket;

import ie.domainregistry.ieapi_1.ResponseType;
import ie.domainregistry.ieapi_ticket_1.QueryType;
import ie.domainregistry.ieapi_ticket_1.ResDataType;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.iedrapi.APICommandHandler;
import pl.nask.crs.iedrapi.ApiValidator;
import pl.nask.crs.iedrapi.AuthData;
import pl.nask.crs.iedrapi.IedrApiConfig;
import pl.nask.crs.iedrapi.ResponseTypeFactory;
import pl.nask.crs.iedrapi.ValidationCallback;
import pl.nask.crs.iedrapi.exceptions.CommandUseError;
import pl.nask.crs.iedrapi.exceptions.IedrApiException;
import pl.nask.crs.iedrapi.impl.TypeConverter;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.ticket.CustomerStatusEnum;
import pl.nask.crs.ticket.Ticket;
import pl.nask.crs.ticket.operation.DomainOperation.DomainOperationType;
import pl.nask.crs.ticket.search.TicketSearchCriteria;

public class TicketQueryCommandHandler extends AbstractTicketCommandHandler implements APICommandHandler<QueryType> {

	@Override
	public ResponseType handle(AuthData auth, QueryType command, ValidationCallback callback)
			throws AccessDeniedException, IedrApiException {
		
		TicketSearchCriteria searchCriteria = new TicketSearchCriteria();
		searchCriteria.setAccountId(getAccountId(auth.getUser()));
		searchCriteria.setCustomerStatus(CustomerStatusEnum.NEW);
		switch (command.getType()) {
			case ALL:
				break;
			case TRANSFERS:
				searchCriteria.setTicketType(DomainOperationType.XFER);
				break;
			case MODIFICATIONS:
				searchCriteria.setTicketType(DomainOperationType.MOD);
				break;
			case REGISTRATIONS:
				searchCriteria.setTicketType(DomainOperationType.REG);
				break;
		default: 
			throw new CommandUseError();
		}
		
		ApiValidator.assertNoError(callback);
		
		long offset = TypeConverter.pageToOffset(command.getPage());
		LimitedSearchResult<Ticket> res = getTicketAppService().search(auth.getUser(), searchCriteria , offset , IedrApiConfig.getPageSize(), null);

		if (res.getTotalResults() == 0)
			return ResponseTypeFactory.successNoRes();
		validatePage(res.getTotalResults(), offset);
		
		ResDataType resDataType = new ResDataType();
		resDataType.setPage(command.getPage());
		resDataType.setTotalPages(TypeConverter.totalResultsToPages(res.getTotalResults()));
		for (Ticket t: res.getResults()) {
			resDataType.getDomain().add(t.getOperation().getDomainNameField().getNewValue());
		}
		
		ResponseType cres = ResponseTypeFactory.success(resDataType);
		return cres ;
	}

}
