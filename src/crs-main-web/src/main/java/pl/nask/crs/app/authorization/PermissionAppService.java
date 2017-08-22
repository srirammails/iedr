package pl.nask.crs.app.authorization;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import pl.nask.crs.app.accounts.AccountAppService;
import pl.nask.crs.app.authorization.permissions.*;
import pl.nask.crs.app.authorization.queries.AccountSavePermissionQuery;
import pl.nask.crs.app.authorization.queries.NicHandleCreatePermissionQuery;
import pl.nask.crs.app.authorization.queries.SimpleChangeLevelPermissionQuery;
import pl.nask.crs.app.config.ConfigAppService;
import pl.nask.crs.app.document.DocumentAppService;
import pl.nask.crs.app.domains.BulkTransferAppService;
import pl.nask.crs.app.domains.DomainAppService;
import pl.nask.crs.app.email.EmailGroupAppService;
import pl.nask.crs.app.email.EmailTemplateAppService;
import pl.nask.crs.app.nichandles.NicHandleAppService;
import pl.nask.crs.app.payment.PaymentAppService;
import pl.nask.crs.app.tickets.TicketAppService;
import pl.nask.crs.app.users.UserAppService;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.scheduler.SchedulerCron;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.security.authorization.AuthorizationService;
import pl.nask.crs.user.permissions.*;

/**
 * @author Pawel pixel Kleniewski
 * @author Kasia Fulara
 */
public class PermissionAppService {
    private static final Logger log = Logger.getLogger(PermissionAppService.class);

    private static final Map<String, Object> permissions;

    static {
        permissions = new HashMap<String, Object>();
        //permissions.put("ticketSection", new TicketsPermission(TicketAppService.class.getCanonicalName()));
        permissions.put("login", new NamedPermissionQuery(new LoginPermission(LoginPermission.CRS).getName()));
        permissions.put("tickets", new NamedPermissionQuery(TicketAppService.class.getCanonicalName() + ".search"));
        permissions.put("ticketshistory", new NamedPermissionQuery(TicketAppService.class.getCanonicalName() + ".view"));
        // permissions.put("domainSection", new DomainWithoutSavePermission(DomainAppService.class.getCanonicalName()));
        permissions.put("domains", new NamedPermissionQuery(DomainAppService.class.getCanonicalName() + ".search"));
        permissions.put("historicalDomains", new NamedPermissionQuery(DomainAppService.class.getCanonicalName() + ".view"));
        permissions.put("exportAuthCodes", new NamedPermissionQuery(DomainAppService.class.getCanonicalName() + ".bulkExportAuthCodes"));
        permissions.put("emailTemplates", new NamedPermissionQuery(EmailTemplateAppService.class.getCanonicalName() + ".search"));
        permissions.put("emailgroup", new NamedPermissionQuery(EmailGroupAppService.class.getCanonicalName() + ".search"));
        //permissions.put("nicHandlesSection", new NicHandleWithoutEditPermission(NicHandleAppService.class.getCanonicalName()));
        permissions.put("nicHandles", new NamedPermissionQuery(NicHandleAppService.class.getCanonicalName() + ".search"));
        permissions.put("nicHandlesCreate", new NicHandleWithoutEditPermission("nicHandleWithoutEditPermission", NicHandleAppService.class.getCanonicalName() + ".create"));
        permissions.put("accountsSearch", new NamedPermissionQuery(AccountAppService.class.getCanonicalName() + ".search"));
        permissions.put("accountsCreate", new NamedPermissionQuery(AccountAppService.class.getCanonicalName() + ".create"));
        permissions.put("documents", CombinedPermissionQuery.alternativeOf("searchdocs", "newdocs"));
        permissions.put("searchdocs", new NamedPermissionQuery(DocumentAppService.class.getCanonicalName() + ".findDocuments"));
        permissions.put("newdocs", new NamedPermissionQuery(DocumentAppService.class.getCanonicalName() + ".getNewDocuments"));
        // assuming, that the user who has the access to the 'reports' has also access to all reports which are within it
        permissions.put("reports", new PackagePermissionQuery("pl.nask.crs.app.reports.ReportsAppService.findTotalDomains"));
        permissions.put("hmUsage", new PackagePermissionQuery("pl.nask.crs.app.reports.ReportsAppService.search"));
        
        permissions.put("DSM", CombinedPermissionQuery.alternativeOf("forceEvent", "forceState"));
        permissions.put("forceEvent", new NamedPermissionQuery(DomainAppService.class.getCanonicalName() + ".forceDSMEvent"));
        permissions.put("forceState", new NamedPermissionQuery(DomainAppService.class.getCanonicalName() + ".forceDSMState"));
        permissions.put("crsConfiguration", new NamedPermissionQuery(ConfigAppService.class.getCanonicalName() + ".getEntries"));

        // ticket revise buttons
//		permissions.put("ticketrevise.view.button", new NamedPermission(TicketAppService.class.getCanonicalName() + ".view"));
//		permissions.put("ticketrevise.history.button", new NamedPermission(TicketAppService.class.getCanonicalName() + ".history"));
//		permissions.put("ticketrevise.revise.button", new NamedPermission(TicketAppService.class.getCanonicalName() + ".revise"));
        permissions.put("ticketrevise.edit.button", new NamedPermissionQuery(TicketAppService.class.getCanonicalName() + ".save"));
//		permissions.put("ticketrevise.checkout.button", new NamedPermission(TicketAppService.class.getCanonicalName() + ".checkOut"));
//		permissions.put("ticketrevise.checkin.button", new NamedPermission(TicketAppService.class.getCanonicalName() + ".checkIn"));
        permissions.put("ticketrevise.alterstatus.button", new NamedPermissionQuery(TicketAppService.class.getCanonicalName() + ".alterStatus"));
//		permissions.put("ticketrevise.reassign.button", new NamedPermission(TicketAppService.class.getCanonicalName() + ".reassign"));
        permissions.put("ticketrevise.save.button", new NamedPermissionQuery(TicketAppService.class.getCanonicalName() + ".save"));
        permissions.put("ticketrevise.accept.button", new NamedPermissionQuery(TicketAppService.class.getCanonicalName() + ".accept"));
        permissions.put("ticketrevise.reject.button", new NamedPermissionQuery(TicketAppService.class.getCanonicalName() + ".reject"));
//		permissions.put("ticketrevise.update.button", new NamedPermission(TicketAppService.class.getCanonicalName() + ".update"));
        permissions.put("ticket.checkIn",  new NamedPermissionQuery(TicketAppService.class.getCanonicalName() + ".checkIn"));
        permissions.put("ticket.checkOut",  new NamedPermissionQuery(TicketAppService.class.getCanonicalName() + ".checkOut"));
        permissions.put("ticket.reassign",  new NamedPermissionQuery(TicketAppService.class.getCanonicalName() + ".reassign"));
        permissions.put("ticket.altesStatus",  new NamedPermissionQuery(TicketAppService.class.getCanonicalName() + ".alterStatus"));
        permissions.put("ticket.revise",  new NamedPermissionQuery(TicketAppService.class.getCanonicalName() + ".revise"));
        permissions.put("ticket.view",  new NamedPermissionQuery(TicketAppService.class.getCanonicalName() + ".view"));

        // ticket view
        permissions.put("ticketview.current.button", new NamedPermissionQuery(TicketAppService.class.getCanonicalName() + ".current"));

        // domain buttons
//		permissions.put("domain.view.button", new NamedPermission(DomainAppService.class.getCanonicalName() + ".view"));
        permissions.put("domain.edit.button", new NamedPermissionQuery(DomainAppService.class.getCanonicalName() + ".edit"));
        permissions.put("domain.save.button", new NamedPermissionQuery(DomainAppService.class.getCanonicalName() + ".save"));
        permissions.put("domain.alterstatus.button", new NamedPermissionQuery("domainViewAlterStatusButton"));
        permissions.put("domain.transfer.button", new NamedPermissionQuery(DomainAppService.class.getCanonicalName() + ".transfer"));
        permissions.put("domain.current.button", new NamedPermissionQuery(DomainAppService.class.getCanonicalName() + ".current"));
        permissions.put("domain.changeWipo.button", new NamedPermissionQuery(DomainAppService.class.getCanonicalName() + ".enterWipo"));
        permissions.put("domain.changeHolderType.button", new NamedPermissionQuery(DomainAppService.class.getCanonicalName() + ".updateHolderType"));
        permissions.put("domain.sendAuthCode.button", new NamedPermissionQuery(DomainAppService.class.getCanonicalName() + ".sendAuthCodeByEmail"));

        // nichandle buttons
        permissions.put("nichandle.edit.button", new NamedPermissionQuery(NicHandleAppService.class.getCanonicalName() + ".save"));
        permissions.put("nichandle.alterstatus.button", new NamedPermissionQuery(NicHandleAppService.class.getCanonicalName() + ".alterStatus"));
//		permissions.put("nichandle.savenewpassword.button", new NamedPermission(NicHandleAppService.class.getCanonicalName() + ".saveNewPassword"));
//		permissions.put("nichandle.generatenewpassword.button", new NamedPermission(NicHandleAppService.class.getCanonicalName() + ".generateNewPassword"));
        permissions.put("nichandle.create.button", new NamedPermissionQuery(NicHandleAppService.class.getCanonicalName() + "create"));
        permissions.put("nichandle.accesslevel.button", new NamedPermissionQuery(NicHandleAppService.class.getCanonicalName() + ".accessLevel"));
        permissions.put("nichandle.resetpassword.button", new NamedPermissionQuery(NicHandleAppService.class.getCanonicalName() + ".resetPassword"));
        permissions.put("nichandle.current.button", new NamedPermissionQuery(NicHandleAppService.class.getCanonicalName() + ".current"));
        permissions.put("nichandle.tfa.button", new NamedPermissionQuery(NicHandleAppService.class.getCanonicalName() + ".changeTfa"));

        // nichandle sections
        permissions.put("nichandle.edit.vat", new NamedPermissionQuery(NicHandleAppService.class.getCanonicalName() + ".editVat"));
        permissions.put("nichandle.edit.address", new NamedPermissionQuery(NicHandleAppService.class.getCanonicalName() + ".editAddress"));
        permissions.put("nichandle.edit.main", new NamedPermissionQuery(NicHandleAppService.class.getCanonicalName() + ".editMain"));

        // account buttons
        permissions.put("account.alterstatus.button", new NamedPermissionQuery(AccountAppService.class.getCanonicalName() + ".alterStatus"));
        permissions.put("account.save.button", new AccountSavePermissionQuery(AccountAppService.class.getCanonicalName() + ".save"));
        permissions.put("account.create.button", new AccountSavePermissionQuery(AccountAppService.class.getCanonicalName() + ".create"));
        permissions.put("account.edit.button", new AccountSavePermissionQuery(AccountAppService.class.getCanonicalName() + ".save"));
        permissions.put("account.current.button", new NamedPermissionQuery(AccountAppService.class.getCanonicalName() + ".current"));
        permissions.put("account.deposit.button", new NamedPermissionQuery(PaymentAppService.class.getCanonicalName() + ".viewDeposit"));
        permissions.put("account.deposit.correct.button", new NamedPermissionQuery(PaymentAppService.class.getCanonicalName() + ".correctDeposit"));

        // accesslevel edit - accessible if only user has a right to edit
        // permissions
        permissions.put("accessLevel.edit.button", new SimpleChangeLevelPermissionQuery(UserAppService.class.getCanonicalName() + ".setPermissionGroups", null, null));
        permissions.put("accessLevelPerms.edit.button", new NamedPermissionQuery(UserAppService.class.getCanonicalName() + ".addUserPermission"));
        
        permissions.put("account.edit.editFlags", new NamedPermissionQuery(AccountAppService.class.getCanonicalName()+".save.editFlags"));

        //manage vat
        permissions.put("manageVat", new NamedPermissionQuery(PaymentAppService.class.getCanonicalName() + ".addVatRate"));
        // view vat rates
        permissions.put("viewVat", new NamedPermissionQuery(PaymentAppService.class.getCanonicalName() + ".getValid"));

        //manage pricing
        permissions.put("pricing", new NamedPermissionQuery(PaymentAppService.class.getCanonicalName() + ".addPrice"));
        
        // scheduler
        permissions.put("Task", CombinedPermissionQuery.alternativeOf("taskCreate", "taskDelete", "taskUpdate"));
        permissions.put("taskCreate", new NamedPermissionQuery(SchedulerCron.class.getCanonicalName()+ ".addJobConfig"));
        permissions.put("taskDelete", new NamedPermissionQuery(SchedulerCron.class.getCanonicalName()+ ".removeJobConfig"));
        permissions.put("taskUpdate", new NamedPermissionQuery(SchedulerCron.class.getCanonicalName()+ ".modifyJobConfig"));
//        permissions.put("taskView", new NamedPermissionQuery(SchedulerCron.class.getCanonicalName() + ".getJobConfigs"));
//        permissions.put("jobView", new NamedPermissionQuery(SchedulerCron.class.getCanonicalName() + ".findJobs"));
//        permissions.put("jobHist", new NamedPermissionQuery(SchedulerCron.class.getCanonicalName() + ".findJobsHistory"));
        
        permissions.put("bulkTransferView", new NamedPermissionQuery(BulkTransferAppService.class.getCanonicalName() + ".createBulkTransferProcess"));
        permissions.put("bulkTransferCreate", new NamedPermissionQuery(BulkTransferAppService.class.getCanonicalName() + ".findTransfers"));
        permissions.put("bulkTransfer", CombinedPermissionQuery.alternativeOf("bulkTransferView", "bulkTransferCreate"));

        permissions.put("emailtemplate.save.button", new NamedPermissionQuery(EmailTemplateAppService.class.getCanonicalName() + ".saveEditableFields"));
        permissions.put("emailgroup.save.button", new NamedPermissionQuery(EmailGroupAppService.class.getCanonicalName() + ".update"));
        permissions.put("emailgroup.new.button", new NamedPermissionQuery(EmailGroupAppService.class.getCanonicalName() + ".create"));
        permissions.put("emailgroup.delete.button", new NamedPermissionQuery(EmailGroupAppService.class.getCanonicalName() + ".delete"));
    }


    private AuthorizationService authorizationService;

    public PermissionAppService(AuthorizationService authorizationService) {
        Validator.assertNotNull(authorizationService, "authorization service");

        this.authorizationService = authorizationService;
    }

    public boolean hasPermission(AuthenticatedUser user, String permissionName) {
        Object permission = permissions.get(permissionName) ;

        if (permission instanceof NicHandleWithoutEditPermission) {
            return hasPermission(user, new NicHandleCreatePermissionQuery(((NicHandleWithoutEditPermission) permission).getName(), null, user));
        }

        if (permission instanceof PermissionQuery) {
        	return hasPermission(user, (PermissionQuery) permission);
        }
        
        if (permission instanceof CombinedPermissionQuery) {
        	return ((CombinedPermissionQuery) permission).isMetFor(this, user);
        }

        log.debug("Unmapped permission name "+permissionName);
        return true;
    }

    public boolean hasPermission(AuthenticatedUser user, PermissionQuery permission) {

        try {
            authorizationService.authorize(user, permission);
        } catch (PermissionDeniedException e) {
            return false;
        } catch (Exception e) {
            log.warn("Exception while checking permission " + permission);
            return false;
        }
        return true;
    }

}
