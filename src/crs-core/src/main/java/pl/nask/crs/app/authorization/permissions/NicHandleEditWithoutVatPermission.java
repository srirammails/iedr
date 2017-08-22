package pl.nask.crs.app.authorization.permissions;

import org.apache.log4j.Logger;

import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.country.CountryFactory;
import pl.nask.crs.nichandle.NicHandle;
import pl.nask.crs.nichandle.dao.NicHandleDAO;

/**
 * @author Marianna Mysiorska
 */
public class NicHandleEditWithoutVatPermission extends NicHandlePartEditPermission {
	private final static Logger LOG = Logger.getLogger(NicHandleEditWithoutVatPermission.class);
	private CountryFactory countryFactory; 
	
    public NicHandleEditWithoutVatPermission(String id, String name, NicHandleDAO nicHandleDAO, CountryFactory countryFactory) {
        super(id, name, nicHandleDAO);
		this.countryFactory = countryFactory;
    }

    @Override
    protected boolean changeAllowed(NicHandle oldNicHandle, NicHandle nicHandle) {
    	LOG.debug("isChangeAllowed");
    	
    	return vatNumberNotChanged(oldNicHandle, nicHandle)
    			&& vatCategoryNotChanged(oldNicHandle, nicHandle)
    			&& countryChangeLeavesVatCategoryUnaffected(oldNicHandle, nicHandle);    	
    }
    
    private boolean countryChangeLeavesVatCategoryUnaffected(NicHandle oldNicHandle, NicHandle nicHandle) {
    	if (Validator.isEqual(oldNicHandle.getCountry(), nicHandle.getCountry()))
    		return true;
    	
    	String oldVatCategory = nicHandle.getVatCategory();
    	String newCountry = nicHandle.getCountry();
    	String newVatCategory = countryFactory.getCountryVatCategory(newCountry);
    	
    	boolean result = Validator.isEqual(oldVatCategory, newVatCategory);
    	if (!result && LOG.isDebugEnabled()) {
    		String msg = String.format("country change affects VAT category. Country: %s => %s, current vat category: %s, new country vat category: %s", new Object[]{oldNicHandle.getCountry(), nicHandle.getCountry(), oldVatCategory, newVatCategory});
    		LOG.debug(msg);
    	}
    	
    	return result;
	}

	private boolean vatCategoryNotChanged(NicHandle oldNicHandle, NicHandle nicHandle) {
    	String oldVatCategory = oldNicHandle.getVatCategory();
    	String newVatCategory = nicHandle.getVatCategory();
    	
    	boolean result = Validator.isEqual(oldVatCategory, newVatCategory);
    	
    	if (!result && LOG.isDebugEnabled()) {
    		String msg = String.format("Vat Category changed: %s => %s", new Object[]{oldVatCategory, newVatCategory});
    		LOG.debug(msg);
    	}
        
    	return result;
	}

	protected boolean vatNumberNotChanged(NicHandle oldNicHandle, NicHandle nicHandle) {
    	String newVatNo = getVatNo(nicHandle);
        String oldVatNo = getVatNo(oldNicHandle);

        boolean result = Validator.isEqual(oldVatNo, newVatNo);
        
        if (!result && LOG.isDebugEnabled()) {
    		String msg = String.format("Vat number changed: %s => %s", new Object[]{oldVatNo, newVatNo});
    		LOG.debug(msg);
    	}
        
        return result;
    }

	private String getVatNo(NicHandle nicHandle) {
		if (nicHandle.getVat() == null) 
			return null;
		return nicHandle.getVat().getVatNo();
	}
	
	@Override
	public String getDescription() {
		if (getClass() != NicHandleEditWithoutVatPermission.class)
			return null;
		return "Edit NicHandle data without changing VAT category or VAT number";
	}
}
