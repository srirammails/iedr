package pl.nask.crs.accounts.dao.ibatis;

import pl.nask.crs.accounts.InternalRegistrar;
import pl.nask.crs.accounts.dao.ibatis.objects.InternalAccount;
import pl.nask.crs.accounts.dao.AccountDAO;
import pl.nask.crs.accounts.Account;
import pl.nask.crs.commons.dao.ConvertingGenericDAO;
import pl.nask.crs.commons.dao.Converter;
import pl.nask.crs.commons.utils.Validator;

import java.util.List;

/**
 * @author Marianna Mysiorska
 */
public class ConvertingAccountDAO extends ConvertingGenericDAO<InternalAccount, Account, Long> implements AccountDAO {

    InternalAccountIBatisDAO internalDao;

    public ConvertingAccountDAO(InternalAccountIBatisDAO internalDao, Converter<InternalAccount, Account> internalConverter) {
        super(internalDao, internalConverter);
        Validator.assertNotNull(internalDao, "internal dao");
        Validator.assertNotNull(internalConverter, "internal converter");
        this.internalDao = internalDao;
    }

    public Long createAccount(Account account) {
        Validator.assertNotNull(account, "account");
        Validator.assertNotNull(account.getName(), "account name");
        Validator.assertNotNull(account.getBillingContact(), "account billing contact");
        Validator.assertNotNull(account.getBillingContact().getNicHandle(), "account billing contact id");
        Validator.assertNotNull(account.getAddress(), "account address");
        Validator.assertNotNull(account.getCounty(), "account county");
        Validator.assertNotNull(account.getCountry(), "account country");
        Validator.assertNotNull(account.getWebAddress(), "account web address");
        Validator.assertNotNull(account.getPhone(), "account phone");
        Validator.assertNotNull(account.getFax(), "account fax");
        Validator.assertNotNull(account.getStatusChangeDate(), "account status change date");
        Validator.assertNotNull(account.getTariff(), "account tariff");
        Validator.assertNotNull(account.getInvoiceFreq(), "account inv freq");
        Validator.assertNotNull(account.getNextInvMonth(), "account next inv month");
        Validator.assertNotNull(account.getRemark(), "account remark");
        account.validateFlags();
        return internalDao.createAccount(getInternalConverter().from(account));
    }

    @Override
    public List<InternalRegistrar> getRegistrarForInternalLogin() {
        return internalDao.getRegistrarForLogin();
    }
}
