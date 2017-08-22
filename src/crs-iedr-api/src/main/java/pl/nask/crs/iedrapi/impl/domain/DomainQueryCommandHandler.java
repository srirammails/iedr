package pl.nask.crs.iedrapi.impl.domain;

import ie.domainregistry.ieapi_1.ResponseType;
import ie.domainregistry.ieapi_domain_1.QueryType;
import ie.domainregistry.ieapi_domain_1.ResDataType;
import ie.domainregistry.ieapi_domain_1.ResDomainType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.domains.Domain;
import pl.nask.crs.domains.NRPStatus;
import pl.nask.crs.domains.search.DomainSearchCriteria;
import pl.nask.crs.domains.search.TransferDirection;
import pl.nask.crs.domains.search.TransferedDomainSearchCriteria;
import pl.nask.crs.iedrapi.ApiValidator;
import pl.nask.crs.iedrapi.AuthData;
import pl.nask.crs.iedrapi.IedrApiConfig;
import pl.nask.crs.iedrapi.ResponseTypeFactory;
import pl.nask.crs.iedrapi.ValidationCallback;
import pl.nask.crs.iedrapi.exceptions.IedrApiException;
import pl.nask.crs.iedrapi.impl.TypeConverter;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.security.authentication.AuthenticatedUser;

/**
 * @author: Marcin Tkaczyk
 */
public class DomainQueryCommandHandler extends AbstractDomainCommandHandler<QueryType> {

    public ResponseType handle(AuthData auth, QueryType command, ValidationCallback callback) throws AccessDeniedException, IedrApiException {

        DomainValidationHelper.commandPlainCheck(command);
        ApiValidator.assertNoError(callback);

        int page = command.getPage();
        long offset = TypeConverter.pageToOffset(page);

        LimitedSearchResult<Domain> searchRes;
        if (command.getTransfer() != null) {
            TransferedDomainSearchCriteria transferCriteria = new TransferedDomainSearchCriteria();
            switch (command.getTransfer().getType()) {
                case INBOUND:
                    transferCriteria.setTransferDirection(TransferDirection.INBOUND);
                    break;
                case OUTBOUND:
                    transferCriteria.setTransferDirection(TransferDirection.OUTBOUND);
                    break;
                default:
                    throw new IllegalArgumentException("Invalid transfer type: " + command.getTransfer().getType());

            }
            transferCriteria.setTransferDateFrom(command.getTransfer().getFrom());
            transferCriteria.setTransferDateTo(command.getTransfer().getTo());
            searchRes = getDomainAppService().findTransferedDomains(auth.getUser(), transferCriteria, offset, IedrApiConfig.getPageSize(), null);
        } else {
            long accountNumber = getAccountId(auth.getUser());
            DomainSearchCriteria criteria = new DomainSearchCriteria();
            criteria.setAccountId(accountNumber);

            if (command.getDomainStatus() != null) {
                switch (command.getDomainStatus()) {
                    case ACTIVE:
                        criteria.setActive(true);
                        break;
                    case NRP:
                        criteria.setActive(false);
                        break;
                }
            }

            if (command.getContact() != null) {
                criteria.setNicHandle(command.getContact().getValue());
                if (command.getContact().getType() != null) {
                    switch (command.getContact().getType()) {
                        case ADMIN:
                            criteria.setContactType(Arrays.asList("A"));
                            break;
                        case TECH:
                            criteria.setContactType(Arrays.asList("T"));
                            break;
                        default:
                            throw new IllegalArgumentException("Invalid contact type: " + command.getContact().getType());
                    }
                }
            }

            criteria.setRenewTo(command.getRenewalDateRangeEnd());
            criteria.setRenewFrom(command.getRenewalDateRangeStart());
            excludeNRPStatusDeletedDomains(criteria);
            criteria.setAttachReservationInfo(command.isAttachReservationInfo());
            searchRes = getDomainAppService().search(auth.getUser(), criteria, offset, IedrApiConfig.getPageSize(), null);
        }

        long totalResults;
        totalResults = searchRes.getTotalResults();
        validatePage(totalResults, offset);
        if (totalResults == 0)
            return ResponseTypeFactory.successNoRes();
        ResDataType res = new ResDataType();
        res.setPage(page);
        res.setTotalPages(TypeConverter.totalResultsToPages(totalResults));
        boolean attachReservationInfo = TypeConverter.commandFieldToBoolean(command.isAttachReservationInfo());
        boolean attachAuthCode = TypeConverter.commandFieldToBoolean(command.isAuthCode());
        boolean forceAuthCode = TypeConverter.commandFieldToBoolean(command.isAuthCodeForceGeneration());
        res.getDomain().addAll(resDomainType(auth.getUser(), searchRes.getResults(), attachReservationInfo, attachAuthCode, forceAuthCode));
        return ResponseTypeFactory.success(res);
    }

    private void excludeNRPStatusDeletedDomains(DomainSearchCriteria criteria) {
        if (Validator.isEmpty(criteria.getNrpStatuses())) {
            criteria.setNrpStatuses(new ArrayList<NRPStatus>(Arrays.asList(NRPStatus.values())));
        }
        criteria.removeNRPStatus(NRPStatus.Deleted);
    }

    private List<ResDomainType> resDomainType(AuthenticatedUser user, List<Domain> results, boolean attachReservationInfo, boolean attachAuthCode, boolean forceAuthCode) throws IedrApiException {
        List<ResDomainType> res = new ArrayList<ResDomainType>();
        for (Domain d : results) {
            String authCode = d.getAuthCode();
            if (forceAuthCode) {
                try {
                    authCode = getCommonAppService().generateOrProlongAuthCode(user, d.getName()).getAuthcode();
                } catch (Exception e) {
                    throw mapException(e);
                }
            }
            if (!attachAuthCode) {
                authCode = null;
            }
            res.add(rdt(d, attachReservationInfo, authCode));
        }
        return res;
    }

    private ResDomainType rdt(Domain d, boolean attachReservationInfo, String authCode) {
        ResDomainType t = new ResDomainType();
        t.setDeleteDate(d.getDeletionDate());
        t.setName(d.getName());
        t.setRegDate(d.getRegistrationDate());
        t.setRenDate(d.getRenewDate());
        t.setStatus(DomainConversionHelper.convertDsmState(d.getDsmState(), d.isPublished()));
        t.setSuspendDate(d.getSuspensionDate());
        t.setTransDate(d.getTransferDate());
        t.setAuthCode(authCode);
        if (attachReservationInfo) {
            t.setReservationPending(d.hasPendingADPReservations() || d.hasPendingCCReservations());
        }
        return t;
    }
}
