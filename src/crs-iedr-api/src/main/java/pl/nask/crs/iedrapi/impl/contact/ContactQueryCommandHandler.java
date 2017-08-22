package pl.nask.crs.iedrapi.impl.contact;



import ie.domainregistry.ieapi_1.ResponseType;
import ie.domainregistry.ieapi_contact_1.QueryType;
import ie.domainregistry.ieapi_contact_1.ResDataType;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.iedrapi.ApiValidator;
import pl.nask.crs.iedrapi.AuthData;
import pl.nask.crs.iedrapi.IedrApiConfig;
import pl.nask.crs.iedrapi.ResponseTypeFactory;
import pl.nask.crs.iedrapi.ValidationCallback;
import pl.nask.crs.iedrapi.exceptions.IedrApiException;
import pl.nask.crs.iedrapi.exceptions.ParameterValueRangeErrorException;
import pl.nask.crs.iedrapi.exceptions.ReasonCode;
import pl.nask.crs.iedrapi.impl.TypeConverter;
import pl.nask.crs.nichandle.NicHandle;
import pl.nask.crs.nichandle.search.NicHandleSearchCriteria;
import pl.nask.crs.security.authentication.AccessDeniedException;

public class ContactQueryCommandHandler extends AbstractContactCommandHandler <QueryType>{
  
    @Override
    public ResponseType handle(AuthData auth, QueryType command, ValidationCallback callback)
            throws AccessDeniedException, IedrApiException {
        ApiValidator.assertNoError(callback);
        int page = command.getPage();
        long offset = TypeConverter.pageToOffset(page);
        long accountNumber = getAccountId(auth.getUser());
        
        NicHandleSearchCriteria criteria = new NicHandleSearchCriteria();
        criteria.setAccountNumber(accountNumber);
        switch (command.getType()) {
            case ADMIN:
                criteria.setContact("A");
                break;
            case TECH:
                criteria.setContact("T");
                break;
            default: // ALL or unknown type! ignore it, search for all                
        }
                
        LimitedSearchResult<NicHandle> searchRes = getNicHandleAppService().search(auth.getUser(), criteria , offset, IedrApiConfig.getPageSize(), null);
        if (searchRes.getTotalResults() == 0)
            return ResponseTypeFactory.successNoRes();
        
        if (searchRes.getTotalResults() < offset)
            throw new ParameterValueRangeErrorException(ReasonCode.ATTRIBUTE_PAGE_IS_OUT_OF_RANGE);
        
        ResDataType res = new ResDataType();
        res.setPage(page);
        res.setTotalPages(TypeConverter.totalResultsToPages(searchRes.getTotalResults()));
        res.getId().addAll(TypeConverter.nhToId(searchRes.getResults()));
        return ResponseTypeFactory.success(res);
    }
}
