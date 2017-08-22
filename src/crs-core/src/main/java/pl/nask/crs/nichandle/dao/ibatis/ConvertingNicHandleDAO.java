package pl.nask.crs.nichandle.dao.ibatis;

import pl.nask.crs.commons.dao.ConvertingGenericDAO;
import pl.nask.crs.commons.dao.Converter;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.nichandle.dao.ibatis.objects.InternalNicHandle;
import pl.nask.crs.defaults.ResellerDefaults;
import pl.nask.crs.nichandle.dao.NicHandleDAO;
import pl.nask.crs.nichandle.NicHandle;
import pl.nask.crs.accounts.search.AccountSearchCriteria;

/**
 * @author Marianna Mysiorska
 */
public class ConvertingNicHandleDAO extends ConvertingGenericDAO<InternalNicHandle, NicHandle, String> implements NicHandleDAO {

    InternalNicHandleIBatisDAO internalDao;

    public ConvertingNicHandleDAO(InternalNicHandleIBatisDAO internalDao, Converter<InternalNicHandle, NicHandle> internalConverter) {
        super(internalDao, internalConverter);
        Validator.assertNotNull(internalDao, "internal dao");
        Validator.assertNotNull(internalConverter, "internal converter");
        this.internalDao = internalDao;

    }

    public Long getNumberOfAssignedDomains(String nicHandleId) {
        Validator.assertNotNull(nicHandleId, "nic handle id");
        return internalDao.getNumberOfAssignedDomains(nicHandleId);
    }

    public Long getNumberOfAccountsForIdAndNicHandle(AccountSearchCriteria criteria) {
        Validator.assertNotNull(criteria, "account search criteria");
        return internalDao.getNumberOfAccountsForIdAndNicHandle(criteria);
    }

    public Long getNumberOfTicketsForNicHandle(String nicHandleId) {
        Validator.assertNotNull(nicHandleId, "nic handle id");
        return internalDao.getNumberOfTicketsForNicHandle(nicHandleId);
    }

    public String getAccountStatus(long id){
        return internalDao.getAccountStatus(id);
    }

    @Override
    public void deleteById(String id) {
    	internalDao.deleteById(id);
    }
    
    @Override
    public void deleteMarkedNichandles() {
    	internalDao.deleteMarkedNichandles();    	
    }
    
    @Override
    public NicHandle getDirectNhForContact(String nicHandleId) {
    	Validator.assertNotNull(nicHandleId, "nicHandleId");
    	String directId = internalDao.getBillingNhForContact(nicHandleId);
    	if (directId == null) {
    		return null;
    	} else {
    		return get(directId);    	
    	}
    }
}
