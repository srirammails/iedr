package pl.nask.crs.domains.dao.ibatis;

import pl.nask.crs.commons.dao.ibatis.GenericIBatisDAO;
import pl.nask.crs.domains.dao.ibatis.objects.InternalHistoricalDomain;

public class InternalHistoricalDomainIbatisDAO extends GenericIBatisDAO<InternalHistoricalDomain, String> {

    public InternalHistoricalDomainIbatisDAO() {
        setFindQueryId("historic-domain.findDomainHistory");
        setCountFindQueryId("historic-domain.countDomainHistory");
        setCreateQueryId("historic-domain.createHistoricalDomain");
    }

    @Override
    public void create(InternalHistoricalDomain internalHistoricalDomain) {
        super.create(internalHistoricalDomain);
        performInsert("historic-domain.createHistoricalDNS", internalHistoricalDomain);
        performInsert("historic-domain.createHistoricalContact", internalHistoricalDomain);
    }

}
