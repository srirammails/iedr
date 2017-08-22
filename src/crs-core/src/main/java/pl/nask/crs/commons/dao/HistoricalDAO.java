package pl.nask.crs.commons.dao;

import java.util.Date;

/**
 * @author Kasia Fulara
 */
public interface HistoricalDAO {

    void create(String name, Date changeDate, String changedBy);
}
