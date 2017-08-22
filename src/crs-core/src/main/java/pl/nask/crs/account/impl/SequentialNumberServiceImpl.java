package pl.nask.crs.account.impl;

import pl.nask.crs.account.AccountVersionService;
import pl.nask.crs.account.dao.SequentialNumberDAO;
import pl.nask.crs.commons.SequentialNumberGenerator;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public class SequentialNumberServiceImpl implements AccountVersionService, SequentialNumberGenerator {

    SequentialNumberDAO dao;

    public SequentialNumberServiceImpl(SequentialNumberDAO dao) {
        this.dao = dao;
    }

    @Override
    public int getNextAccountVersion() {
        return (int) getNextId();
    }

	@Override
	public long getNextId() {
		return dao.getNext();
	}
}
