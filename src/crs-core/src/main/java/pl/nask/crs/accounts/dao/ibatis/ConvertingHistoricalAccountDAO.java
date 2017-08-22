package pl.nask.crs.accounts.dao.ibatis;

import java.util.Date;

import pl.nask.crs.accounts.Account;
import pl.nask.crs.accounts.dao.HistoricalAccountDAO;
import pl.nask.crs.accounts.dao.ibatis.objects.InternalHistoricalAccount;
import pl.nask.crs.commons.dao.Converter;
import pl.nask.crs.commons.dao.ConvertingGenericDAO;
import pl.nask.crs.commons.dao.GenericDAO;
import pl.nask.crs.history.HistoricalObject;

/**
 * @author Marianna Mysiorska
 */
public class ConvertingHistoricalAccountDAO extends ConvertingGenericDAO<InternalHistoricalAccount, HistoricalObject<Account>, Long> implements HistoricalAccountDAO {

    public ConvertingHistoricalAccountDAO(GenericDAO<InternalHistoricalAccount, Long> internalDao, Converter<InternalHistoricalAccount, HistoricalObject<Account>> internalConverter) {
        super(internalDao, internalConverter);
    }

    public void create(Long id, Date changeDate, String changedBy) {
        InternalHistoricalAccount iha = new InternalHistoricalAccount();
        iha.setId(id);
        iha.setHistChangeDate(changeDate);
        iha.setChangedByNicHandle(changedBy);
        getInternalDao().create(iha);
    }
}
