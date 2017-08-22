package pl.nask.crs.domains.services.impl;

import java.util.Date;

import pl.nask.crs.commons.OpInfo;
import pl.nask.crs.domains.Domain;
import pl.nask.crs.domains.dao.DomainDAO;
import pl.nask.crs.domains.dao.HistoricalDomainDAO;

/**
 * 
 * @author Artur Gniadzik
 *
 */
public class DomainServiceHelper {

	private final DomainDAO domainDao;
	private final HistoricalDomainDAO historicalDomainDao;

	public DomainServiceHelper(DomainDAO domainDAO, HistoricalDomainDAO historicalDomainDAO) {
		this.domainDao = domainDAO;
		this.historicalDomainDao = historicalDomainDAO;
	}

    public void updateDomainAndHistory(Domain domain, OpInfo opInfo) {
    	updateHistory(domain, opInfo);
        domainDao.update(domain);
    }
    
    public void updateDomainAndHistory(Domain domain, int targetState, OpInfo opInfo) {
        updateHistory(domain, opInfo);
        domainDao.update(domain, targetState);
    }
    
    public void delete(String domainName, Date deletionDate, OpInfo opInfo) {
        Domain domain = domainDao.get(domainName);
        updateHistory(domain, opInfo);
        domainDao.deleteById(domainName, deletionDate);
    }

    private void updateHistory(Domain domain, OpInfo opInfo) {
        domain.updateChangeDate();
        historicalDomainDao.create(domain.getName(), domain.getChangeDate(), opInfo.getActorName());
        domain.updateRemark(opInfo.getActorNameForRemark());
    }
}
