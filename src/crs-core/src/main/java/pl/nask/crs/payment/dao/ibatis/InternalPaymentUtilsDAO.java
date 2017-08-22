package pl.nask.crs.payment.dao.ibatis;

import pl.nask.crs.commons.dao.ibatis.SqlMapClientLoggingDaoSupport;
import pl.nask.crs.payment.LimitsPair;

import java.util.Collections;

/**
 * @author: Marcin Tkaczyk
 */
public class InternalPaymentUtilsDAO extends SqlMapClientLoggingDaoSupport {

    public LimitsPair getDepositLimits() {
        Double min = performQueryForObject("payment-utils.getMinDeposit", Collections.emptyMap());
        Double max = performQueryForObject("payment-utils.getMaxDeposit", Collections.emptyMap());
        LimitsPair res = new LimitsPair(min == null ? 0 : min, max == null ? 0 : max);
        return res;
    }
}
