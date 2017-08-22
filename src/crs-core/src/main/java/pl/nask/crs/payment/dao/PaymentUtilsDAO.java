package pl.nask.crs.payment.dao;

import pl.nask.crs.payment.LimitsPair;

import java.util.Date;

/**
 * @author: Marcin Tkaczyk
 */
public interface PaymentUtilsDAO {

    LimitsPair getDepositLimits();
}
