package pl.nask.crs.domains.dsm.actions;

import java.util.Date;

import pl.nask.crs.domains.Domain;
import pl.nask.crs.domains.dao.DomainDAO;
import pl.nask.crs.domains.dsm.DsmAction;
import pl.nask.crs.domains.dsm.DsmEvent;
import pl.nask.crs.security.authentication.AuthenticatedUser;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public class SetTransferDate extends AbstractDsmAction {

	private DomainDAO dao;
	private Date dateToSet; 
	
	public SetTransferDate(DomainDAO dao) {
		this.dao = dao;
	}
	
	SetTransferDate(DomainDAO dao, Date date) {
		this.dao = dao;
		dateToSet = date;
	}

	@Override
	public DsmAction getAction(String param) {
		return new SetTransferDate(dao, new Date());
	}
	
    @Override
    protected void invokeAction(AuthenticatedUser user, Domain domain, DsmEvent event) throws Exception {
    	if (dateToSet == null) {
    		throw new IllegalStateException("dateToSet is null: was the right constructor called?");
    	}    	
        domain.setTransferDate(dateToSet);
        
        dao.createTransferRecord(domain.getName(), dateToSet, event.getStringParameter(DsmEvent.OLD_BILL_C), event.getStringParameter(DsmEvent.NEW_BILL_C));
    }
    
    public Date getNewTransferDate() {
		return dateToSet;
	}
}
