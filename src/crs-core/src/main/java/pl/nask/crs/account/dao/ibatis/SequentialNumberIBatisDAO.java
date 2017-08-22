package pl.nask.crs.account.dao.ibatis;

import pl.nask.crs.account.dao.SequentialNumberDAO;
import pl.nask.crs.commons.dao.ibatis.SqlMapClientLoggingDaoSupport;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public class SequentialNumberIBatisDAO extends SqlMapClientLoggingDaoSupport implements SequentialNumberDAO {

    private final String sequenceName;

    public SequentialNumberIBatisDAO(String sequenceName) {
		this.sequenceName = sequenceName; 
	}

    public Long getNext() {
        return performInsert("sequentialNumber.insertNull", sequenceName);
    }
}
