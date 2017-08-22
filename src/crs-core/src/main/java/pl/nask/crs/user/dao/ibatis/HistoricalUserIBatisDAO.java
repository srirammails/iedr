package pl.nask.crs.user.dao.ibatis;

import pl.nask.crs.commons.dao.Converter;
import pl.nask.crs.commons.dao.ConvertingGenericDAO;
import pl.nask.crs.history.HistoricalObject;
import pl.nask.crs.user.User;
import pl.nask.crs.user.dao.HistoricalUserDAO;
import pl.nask.crs.user.dao.ibatis.objects.InternalHistoricalUser;

public class HistoricalUserIBatisDAO extends ConvertingGenericDAO<InternalHistoricalUser, HistoricalObject<User>, String> implements HistoricalUserDAO {

    public HistoricalUserIBatisDAO(InternalHistoricalUserIBatisDAO internalDao, Converter<InternalHistoricalUser, HistoricalObject<User>> internalConverter) {
        super(internalDao, internalConverter);
    }
}
