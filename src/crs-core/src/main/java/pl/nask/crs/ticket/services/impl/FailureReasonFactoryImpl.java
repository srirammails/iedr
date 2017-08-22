package pl.nask.crs.ticket.services.impl;

import pl.nask.crs.commons.dao.GenericDAO;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.ticket.operation.FailureReason;
import pl.nask.crs.ticket.operation.Field;
import pl.nask.crs.ticket.services.FailureReasonFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Kasia Fulara
 * @author Patrycja Wegrzynowicz
 */
public class FailureReasonFactoryImpl implements FailureReasonFactory {

    private GenericDAO<FailureReason, Integer> failureReasonDao;

    public FailureReasonFactoryImpl(GenericDAO<FailureReason, Integer> failureReasonDAO) {
        Validator.assertNotNull(failureReasonDAO, "failure reason dao");
        this.failureReasonDao = failureReasonDAO;
    }

    public FailureReason getEntry(Integer id) {
        return failureReasonDao.get(id);
    }

    public List<FailureReason> getEntries() {
        return failureReasonDao.find(null).getResults();
    }

    public List<FailureReason> getFailureReasonsByField(Field dataField) {
        // it's a very short list, no harm to filter it here instead of a dao layer
        List<FailureReason> ret = new ArrayList<FailureReason>();
        for (FailureReason fr : getEntries()) {
            if (fr.getDataField() == dataField.getDataFieldValue()) {
                ret.add(fr);
            }
        }
        return ret;
    }

}
