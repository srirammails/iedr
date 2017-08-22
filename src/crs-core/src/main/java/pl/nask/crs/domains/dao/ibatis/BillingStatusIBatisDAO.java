package pl.nask.crs.domains.dao.ibatis;

import pl.nask.crs.domains.BillingStatus;
import pl.nask.crs.statuses.dao.ibatis.StatusIBatisDAO;

/**
 * @author Kasia Fulara
 */
public class BillingStatusIBatisDAO extends StatusIBatisDAO<BillingStatus, String> {

    public BillingStatusIBatisDAO(String getQueryId, String findQueryId) {
        super(getQueryId, findQueryId);
    }
}
