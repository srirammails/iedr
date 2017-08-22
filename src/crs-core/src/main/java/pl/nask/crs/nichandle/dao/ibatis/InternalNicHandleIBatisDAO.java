package pl.nask.crs.nichandle.dao.ibatis;

import pl.nask.crs.commons.dao.ibatis.GenericIBatisDAO;
import pl.nask.crs.nichandle.dao.ibatis.objects.InternalNicHandle;
import pl.nask.crs.nichandle.dao.ibatis.objects.Telecom;
import pl.nask.crs.defaults.ResellerDefaults;
import pl.nask.crs.accounts.search.AccountSearchCriteria;

import java.util.List;
import java.util.Set;

/**
 * @author Marianna Mysiorska
 */
public class InternalNicHandleIBatisDAO extends GenericIBatisDAO<InternalNicHandle, String> {

    public InternalNicHandleIBatisDAO() {
        setGetQueryId("nicHandle.getNicHandleByNicHandleId");
        setFindQueryId("nicHandle.findNicHandle");
        setCountFindQueryId("nicHandle.countTotalSearchResult");
        setLockQueryId("nicHandle.getLockedNicHandleByNicHandleId");
    }

    /**
     * Returns the number of domains assinged to the nicHandle in the Contact table
     * @param nicHandleId id of the nicHandle
     * @return number of assigned domains
     */
    public Long getNumberOfAssignedDomains(String nicHandleId){
        return performQueryForObject("nicHandle.getNumberOfAssignedDomains", nicHandleId);
    }

    /**
     * Returns the number of accounts with given id and nic handle (as billing contact)
     * @param criteria id and/or nic handle
     * @return number of accounts
     */
    public Long getNumberOfAccountsForIdAndNicHandle(AccountSearchCriteria criteria){
        return performQueryForObject("nicHandle.getNumberOfAccountsForIdAndNicHandle", criteria);
    }

    public Long getNumberOfTicketsForNicHandle(String nicHandleId){
        return performQueryForObject("nicHandle.getNumberOfTicketsForNicHandle", nicHandleId);
    }

    public String getAccountStatus(long id){
        return performQueryForObject("nicHandle.getAccountStatus", id);
    }

    /**
     * Updates nic handle.
     * Deletes old phones and faxes, and inserts new ones.
     * @param internalNicHandle
     */
    public void update(InternalNicHandle internalNicHandle) {
        performUpdate("nicHandle.updateNicHandle", internalNicHandle);
        performUpdate("nicHandle.deleteTelecoms", internalNicHandle.getNicHandleId());
        if (!internalNicHandle.getTelecoms().isEmpty()) {
            performInsert("nicHandle.insertTelecoms", internalNicHandle);
        }
        if (internalNicHandle.vatNoNotEmpty()) {
            performUpdate("nicHandle.insertOrUpdateVAT", internalNicHandle);
        } else {
            performUpdate("nicHandle.deleteVAT", internalNicHandle.getNicHandleId());
        }
    }

    public void create(InternalNicHandle internalNicHandle){
        performInsert("nicHandle.insertNicHandle", internalNicHandle);
        if (!internalNicHandle.getTelecoms().isEmpty()) {
            performInsert("nicHandle.insertTelecoms", internalNicHandle);
        }
        if (internalNicHandle.vatNoNotEmpty())
            performInsert("nicHandle.insertOrUpdateVAT", internalNicHandle);
    }

    @Override
    public void deleteById(String id) {
    	logger.debug("Deleting " + id);
    	performDelete("nicHandle.deleteTelecoms", id);
    	performDelete("nicHandle.deleteContacts", id);
    	performDelete("nicHandle.deleteAccess", id);
    	performDelete("nicHandle.deleteNicHandle", id);    	
    }

	public void deleteMarkedNichandles() {
		logger.debug("Looking for nic handles to be deleted");	
		List<String> list = performQueryForList("nicHandle.findDeleted", "");
		logger.debug("Found " + list.size() + " nichandles marked for deletion");
		for (String nh: list) {
			deleteById(nh);
		}
	}

	public String getBillingNhForContact(String nicHandleId) {
		List<String> res = performQueryForList("nicHandle.findBillingNH", nicHandleId);
		if (res.size() == 0) {
			return null;
		} else if (res.size() > 1) {
			logger.warn("nicHandle.findBillingNH: Expected one result to be found, but got " + res.size() + " results for nh=" + nicHandleId + ". Results are: " + res + ", will return null.");
			return null;
		} else {
			return res.get(0);
		}
	}
}
