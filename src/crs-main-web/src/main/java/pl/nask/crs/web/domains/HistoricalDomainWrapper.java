package pl.nask.crs.web.domains;

import pl.nask.crs.app.domains.wrappers.AbstractDomainWrapper;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.domains.Domain;
import pl.nask.crs.history.HistoricalObject;

/**
 * @author Piotr Tkaczyk
 */
public class HistoricalDomainWrapper extends AbstractDomainWrapper {
    private HistoricalObject<Domain> historicalDomain;

    public HistoricalDomainWrapper(HistoricalObject<Domain> hDomain) {
        super (hDomain.getObject());
        Validator.assertNotNull(hDomain, "historical domain");
        historicalDomain = hDomain;
    }

    /**
     * returns updated (if needed) domain object. use
     * {@link #getWrappedDomain()} to access plain domain object
     */
    public HistoricalObject<Domain> getHistoricalDomain() {
        return historicalDomain;
    }
}
