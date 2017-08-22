package pl.nask.crs.accounts.dao.ibatis;

import pl.nask.crs.statuses.dao.ibatis.StatusIBatisDAO;
import pl.nask.crs.accounts.AccountTariff;

public class AccountTariffsIBatisDAO extends StatusIBatisDAO<AccountTariff, String> {

    public AccountTariffsIBatisDAO(String getQueryId, String findQueryId) {
        super(getQueryId, findQueryId);
    }
}
