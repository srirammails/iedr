package pl.nask.crs.payment.dao.ibatis;

import pl.nask.crs.payment.dao.PaymentUtilsDAO;
import pl.nask.crs.payment.LimitsPair;
import pl.nask.crs.commons.utils.Validator;

import java.util.Date;

/**
 * @author: Marcin Tkaczyk
 */
public class PaymentUtilsDAOImpl implements PaymentUtilsDAO {

    InternalPaymentUtilsDAO internalDAO;

    public PaymentUtilsDAOImpl(InternalPaymentUtilsDAO internalDAO) {
        this.internalDAO = internalDAO;
    }

    public LimitsPair getDepositLimits() {
        return internalDAO.getDepositLimits();
    }

}
