package pl.nask.crs.nichandle.service;

import java.util.Set;

import pl.nask.crs.accounts.exceptions.AccountNotActiveException;
import pl.nask.crs.accounts.exceptions.AccountNotFoundException;
import pl.nask.crs.commons.exceptions.EmptyRemarkException;
import pl.nask.crs.commons.exceptions.NicHandleAssignedToDomainException;
import pl.nask.crs.commons.exporter.ExportException;
import pl.nask.crs.country.InvalidCountryException;
import pl.nask.crs.country.InvalidCountyException;
import pl.nask.crs.nichandle.NewAccount;
import pl.nask.crs.nichandle.NicHandle;
import pl.nask.crs.nichandle.exception.EmptyPasswordException;
import pl.nask.crs.nichandle.exception.NicHandleEmailException;
import pl.nask.crs.nichandle.exception.NicHandleIsAccountBillingContactException;
import pl.nask.crs.nichandle.exception.NicHandleIsTicketContactException;
import pl.nask.crs.nichandle.exception.NicHandleNotFoundException;
import pl.nask.crs.nichandle.exception.PasswordTooEasyException;
import pl.nask.crs.nichandle.exception.PasswordsDontMatchException;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.token.TokenExpiredException;
import pl.nask.crs.user.exceptions.InvalidOldPasswordException;
import pl.nask.crs.user.exceptions.PasswordAlreadyExistsException;

/**
 * @author Marianna Mysiorska
 */
public interface NicHandleService {

    public void save(NicHandle nicHandle, String hostmastersRemark, String hostmasterHandle, String superHostmasterHandle, String loggedUserName)
            throws NicHandleNotFoundException, EmptyRemarkException, AccountNotFoundException, AccountNotActiveException, NicHandleIsAccountBillingContactException, NicHandleEmailException, InvalidCountryException, InvalidCountyException, ExportException ;

    public void alterStatus(String nicHandleId, NicHandle.NHStatus status, String hostmasterHandle, String superHostmasterHandle, String hostmastersRemark)
            throws NicHandleNotFoundException, NicHandleAssignedToDomainException, EmptyRemarkException, NicHandleIsAccountBillingContactException, NicHandleIsTicketContactException;

    public NicHandle createNicHandle(String name, String companyName, String email, String address, String county, String country, Long accountNumber, String accountName, Set<String> phones, Set<String> faxes, String nicHandleRemark, String nicHandleCreator, String vatNo, boolean sendNotificationEmail, String superNicHandle, boolean generatePassword, boolean strictCountyValidation)
            throws AccountNotFoundException, NicHandleNotFoundException, EmptyRemarkException, NicHandleEmailException, PasswordAlreadyExistsException, AccountNotActiveException, InvalidCountryException, InvalidCountyException, ExportException;

    public void saveNewPassword(String password1, String password2, String nicHandleId, String hostmasterHandle, String superHostmasterHandle, String loggedUserName)
            throws EmptyPasswordException, PasswordsDontMatchException, PasswordTooEasyException, NicHandleNotFoundException, NicHandleEmailException, PasswordAlreadyExistsException;

    public void changePassword(String oldPassword, String password1, String password2, String nicHandleId, String hostmasterHandle, String superHostmasterHandle)
            throws EmptyPasswordException, PasswordsDontMatchException, PasswordTooEasyException, NicHandleNotFoundException, NicHandleEmailException, PasswordAlreadyExistsException, InvalidOldPasswordException;
    public void resetPassword(AuthenticatedUser user, String nicHandleId, String hostmasterHandle, String ipAddress)
            throws NicHandleNotFoundException, NicHandleEmailException;

    public void confirmNicHandleIsNotAssignedToAnyDomain(String nicHandleId)
            throws NicHandleAssignedToDomainException;

    public void delete(String nicHandle);

	public void removeDeletedNichandles();

	public void removeUserPermission(String nicHandleId, String permissionName);

	public void addUserPermission(String nicHandleId, String permissionName);

	public NewAccount createDirectAccount(String name, String companyName, String email, String address, String country, String county, Set<String> phones, Set<String> faxes, String vatNo, String password, boolean useTfa) 
			throws AccountNotFoundException, AccountNotActiveException, NicHandleNotFoundException, NicHandleEmailException, EmptyRemarkException, PasswordAlreadyExistsException, InvalidCountryException, InvalidCountyException, ExportException;

	public String useToken(String token, String nicHandleId) throws TokenExpiredException;

	void triggerExport(String nicHandleId) throws ExportException, NicHandleNotFoundException;
}
