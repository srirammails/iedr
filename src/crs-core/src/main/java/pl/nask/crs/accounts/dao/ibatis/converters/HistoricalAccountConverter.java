package pl.nask.crs.accounts.dao.ibatis.converters;

import pl.nask.crs.accounts.dao.ibatis.objects.InternalHistoricalAccount;
import pl.nask.crs.accounts.Account;
import pl.nask.crs.history.HistoricalObject;
import pl.nask.crs.commons.dao.AbstractConverter;
import pl.nask.crs.commons.utils.Validator;

/**
 * @author Marianna Mysiorska
 */
public class HistoricalAccountConverter extends AbstractConverter<InternalHistoricalAccount, HistoricalObject<Account>> {

    private AccountConverter accountConverter;

    public HistoricalAccountConverter(AccountConverter accountConverter) {
        Validator.assertNotNull(accountConverter, "account converter");
        this.accountConverter = accountConverter;
    }

    protected HistoricalObject<Account> _to(InternalHistoricalAccount src) {
        return new HistoricalObject<Account>(
                src.getChangeId(),
                accountConverter.to(src),
                src.getHistChangeDate(),
                src.getChangedByNicHandle()
        );
    }


    protected InternalHistoricalAccount _from(HistoricalObject<Account> accountHistoricalObject) {
        throw new UnsupportedOperationException();
    }
}
