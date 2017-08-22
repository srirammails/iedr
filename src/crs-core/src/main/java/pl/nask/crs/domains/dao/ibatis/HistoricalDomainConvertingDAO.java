package pl.nask.crs.domains.dao.ibatis;

import pl.nask.crs.commons.dao.Converter;
import pl.nask.crs.commons.dao.ConvertingGenericDAO;
import pl.nask.crs.domains.Domain;
import pl.nask.crs.domains.dao.HistoricalDomainDAO;
import pl.nask.crs.domains.dao.ibatis.objects.InternalHistoricalDomain;
import pl.nask.crs.history.HistoricalObject;

import java.util.Date;

/**
 * @author Kasia Fulara
 */
public class HistoricalDomainConvertingDAO extends ConvertingGenericDAO<InternalHistoricalDomain, HistoricalObject<Domain>, String> implements HistoricalDomainDAO {

    public HistoricalDomainConvertingDAO(InternalHistoricalDomainIbatisDAO internalDao, Converter<InternalHistoricalDomain, HistoricalObject<Domain>> internalConverter) {
        super(internalDao, internalConverter);
    }

    @Override
    public void create(String domainName, Date changeDate, String changedBy) {
        InternalHistoricalDomain domain = new InternalHistoricalDomain();
        domain.setName(domainName);
        domain.setHistChangeDate(changeDate);
        domain.setChangedByNicHandle(changedBy);
        getInternalDao().create(domain);
    }

}
