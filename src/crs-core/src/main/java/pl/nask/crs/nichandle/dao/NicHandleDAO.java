package pl.nask.crs.nichandle.dao;

import pl.nask.crs.accounts.search.AccountSearchCriteria;
import pl.nask.crs.commons.dao.GenericDAO;
import pl.nask.crs.nichandle.NicHandle;

/**
 * @author Marianna Mysiorska
 */
public interface NicHandleDAO  extends GenericDAO<NicHandle, String> {

    public Long getNumberOfAssignedDomains(String nicHandleId);

    /**
     * Returns the number of account for given id and billing contact (nic handle)
     * This method is here because the module nic-handle-logic cannot depend on module account-logic
     * @param criteria id and billing contact
     * @return number of account fulfilling the criteria
     */
    public Long getNumberOfAccountsForIdAndNicHandle(AccountSearchCriteria criteria);

    public Long getNumberOfTicketsForNicHandle(String nicHandleId);

    /**
     * Method returns the status of an account with given id.
     * Must be here, because nic-handles-logic module cannot depend on account-logic module (to avoid cyclic dependation)
     * @param id Account id
     * @return Account status
     */
    public String getAccountStatus(long id);

    public void deleteById(String nicHandleId);

	public void deleteMarkedNichandles();

	public NicHandle getDirectNhForContact(String nicHandleId);	
}
