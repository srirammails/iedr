package pl.nask.crs.domains.dao.ibatis.converters;

import org.apache.log4j.Logger;
import pl.nask.crs.commons.dao.AbstractConverter;
import pl.nask.crs.domains.Domain;
import pl.nask.crs.domains.dao.ibatis.objects.InternalHistoricalDomain;
import pl.nask.crs.history.HistoricalObject;

public class HistoricalDomainConverter extends AbstractConverter<InternalHistoricalDomain, HistoricalObject<Domain>> {
    private DomainConverter domainConverter = new DomainConverter();
    private static final Logger logger = Logger.getLogger(HistoricalDomainConverter.class);

    protected HistoricalObject<Domain> _to(InternalHistoricalDomain src) {
        try {
            return new HistoricalObject<Domain>(
                    src.getChangeId(),
                    domainConverter.to(src),
                    src.getHistChangeDate(),
                    src.getChangedByNicHandle());
        } catch (Exception e) {
            logger.error("Cannot convert Ticket History record with id=" + src.getName() + ". Error message was: " + e.getMessage());
            return null;
        }    }

    protected InternalHistoricalDomain _from(HistoricalObject<Domain> domain) {
        throw new UnsupportedOperationException();
    }

}
