package pl.nask.crs.defaults;

import pl.nask.crs.defaults.exceptions.DefaultsNotFoundException;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public interface ResellerDefaultsService {

    ResellerDefaults get(String nicHandleId) throws DefaultsNotFoundException;

    void create(ResellerDefaults resellerDefaults);

    void save(ResellerDefaults resellerDefaults) throws DefaultsNotFoundException;
}
