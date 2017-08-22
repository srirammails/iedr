package pl.nask.crs.defaults.dao;

import pl.nask.crs.commons.dao.GenericDAO;
import pl.nask.crs.defaults.ResellerDefaults;

import java.util.List;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public interface DefaultsDAO extends GenericDAO<ResellerDefaults, String>{

    public List<ResellerDefaults> getAll();

}
