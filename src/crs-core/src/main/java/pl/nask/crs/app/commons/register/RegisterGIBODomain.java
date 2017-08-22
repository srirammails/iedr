package pl.nask.crs.app.commons.register;

import java.util.Arrays;

import pl.nask.crs.accounts.Account;
import pl.nask.crs.app.AppServicesRegistry;
import pl.nask.crs.app.ServicesRegistry;
import pl.nask.crs.app.commons.TicketRequest;
import pl.nask.crs.app.tickets.exceptions.DomainNameExistsOrPendingException;
import pl.nask.crs.commons.OpInfo;
import pl.nask.crs.domains.NewDomain;
import pl.nask.crs.entities.exceptions.ClassCategoryPermissionException;
import pl.nask.crs.entities.exceptions.ClassDontMatchCategoryException;
import pl.nask.crs.entities.exceptions.HolderCategoryNotExistException;
import pl.nask.crs.entities.exceptions.HolderClassNotExistException;
import pl.nask.crs.nichandle.NicHandle;
import pl.nask.crs.nichandle.exception.NicHandleNotActiveException;
import pl.nask.crs.nichandle.exception.NicHandleNotFoundException;
import pl.nask.crs.payment.exceptions.NotAdmissiblePeriodException;
import pl.nask.crs.security.authentication.AuthenticatedUser;

public class RegisterGIBODomain {

    private final AppServicesRegistry appRegistry;
    private final ServicesRegistry registry;
    private final AuthenticatedUser user;
    private final TicketRequest request;

    public RegisterGIBODomain(AppServicesRegistry appRegistry, ServicesRegistry registry, AuthenticatedUser user, TicketRequest request) {
        this.appRegistry = appRegistry;
        this.registry = registry;
        this.user = user;
        this.request = request;
    }

    public void run() throws CharityRegistrationNotPossibleException, NotAdmissiblePeriodException, DomainNameExistsOrPendingException,
            NicHandleNotFoundException, NicHandleNotActiveException, HolderClassNotExistException,
            HolderCategoryNotExistException, ClassDontMatchCategoryException, ClassCategoryPermissionException {
        // GIBO domain can't be a charity domain, BPR req#0205
        if (request.isCharity()) {
            throw new CharityRegistrationNotPossibleException(request.getDomainName());
        }
        // REQ#0203 MultiYear Registrations are not permitted for GIBO domains
        RegisterDomain.checkPeriodInYears(request);

        String domainName = request.getDomainName().toLowerCase();
        NicHandle nh = registry.getNicHandleSearchService().getNicHandle(user.getUsername());
        Account account = registry.getAccountSearchService().getAccount(nh.getAccount().getId());
        NewDomain domain = new NewDomain(
                domainName,
                request.getDomainHolder(),
                request.getDomainHolderClass(),
                request.getDomainHolderCategory(),
                user.getUsername(),
                account.getId(),
                request.getRequestersRemark(),
                Arrays.asList(request.getTechContactNicHandle()),
                Arrays.asList(account.getBillingContact().getNicHandle()),
                request.getAdminContacts(),
                request.getNameservers(),
                request.getRegPeriod());
        // check, if the domain is available
        if (appRegistry.getTicketAppService().internalGetTicketForDomain(domainName) != null
                ||
                appRegistry.getDomainAppService().checkDomainExists(user, domainName)) {
            throw new DomainNameExistsOrPendingException(domainName);
        }

        registry.getDomainService().create(user, domain, new OpInfo(user));
    }

}
