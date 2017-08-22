package pl.nask.crs.defaults.impl;

import pl.nask.crs.defaults.ResellerDefaultsService;
import pl.nask.crs.defaults.ResellerDefaults;
import pl.nask.crs.defaults.dao.DefaultsDAO;
import pl.nask.crs.defaults.exceptions.DefaultsNotFoundException;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public class ResellerDefaultsServiceImpl implements ResellerDefaultsService {

    private DefaultsDAO defaultsDAO;

    public ResellerDefaultsServiceImpl(DefaultsDAO defaultsDAO) {
        this.defaultsDAO = defaultsDAO;
    }

    @Override
    public ResellerDefaults get(String nicHandleId) throws DefaultsNotFoundException {
        ResellerDefaults defaults = defaultsDAO.get(nicHandleId);
        if (defaults == null) {
            throw new DefaultsNotFoundException("Defaults not found for reseller:" + nicHandleId);
        } else {
            return defaults;
        }
    }

    @Override
    public void create(ResellerDefaults resellerDefaults) {
        defaultsDAO.create(resellerDefaults);
    }

    @Override
    public void save(ResellerDefaults resellerDefaults) throws DefaultsNotFoundException {
        defaultsDAO.update(resellerDefaults);
    }
}
