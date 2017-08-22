package pl.nask.crs.domains.dao;

import pl.nask.crs.commons.dao.GenericDAO;
import pl.nask.crs.domains.Domain;
import pl.nask.crs.history.HistoricalObject;

import java.util.Date;

public interface HistoricalDomainDAO extends GenericDAO<HistoricalObject<Domain>, String> {

    void create(String domainName, Date changeDate, String changedBy);

}
