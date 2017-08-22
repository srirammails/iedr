package pl.nask.crs.defaults.dao.ibatis;

import pl.nask.crs.commons.dao.ibatis.GenericIBatisDAO;
import pl.nask.crs.defaults.ResellerDefaults;
import pl.nask.crs.defaults.dao.DefaultsDAO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public class DefaultsIbatisDAO extends GenericIBatisDAO<ResellerDefaults, String> implements DefaultsDAO {

    public DefaultsIbatisDAO() {
        setGetQueryId("defaults.getResellerDefaults");
        setCreateQueryId("defaults.create");
        setUpdateQueryId("defaults.update");
    }

    @Override
    public List<ResellerDefaults> getAll() {
        return performQueryForList("defaults.getAll");
    }

    @Override
    public void create(ResellerDefaults resellerDefaults) {
        super.create(resellerDefaults);
        updateNameservers(resellerDefaults);
    }

    @Override
    public void update(ResellerDefaults resellerDefaults) {
        super.update(resellerDefaults);
        updateNameservers(resellerDefaults);
    }

    private void updateNameservers(ResellerDefaults resellerDefaults) {
        final String nicHandleId = resellerDefaults.getNicHandleId();
        List<String> currentNameservers = get(nicHandleId).getNameservers();
        final int _ = -1;
        for (String oldNs : currentNameservers) {
            performDelete("defaults.deleteNameserver", createDNSParams(nicHandleId, oldNs, _));
        }
        int order = 0;
        for (String newNs : resellerDefaults.getNameservers()) {
            performInsert("defaults.createTicketNameserver", createDNSParams(nicHandleId, newNs, order++));
        }
    }

    private Map<String, Object> createDNSParams(String nicHandle, String nameserver, int order) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("nicHandle", nicHandle);
        params.put("nameserver", nameserver);
        params.put("order", order);
        return params;
    }
}
