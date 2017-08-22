package pl.nask.crs.user.dao;

import pl.nask.crs.commons.dao.GenericDAO;
import pl.nask.crs.history.HistoricalObject;
import pl.nask.crs.user.User;

public interface HistoricalUserDAO  extends GenericDAO<HistoricalObject<User>, String> {
}
