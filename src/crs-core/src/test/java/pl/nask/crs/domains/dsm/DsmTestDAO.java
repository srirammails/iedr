package pl.nask.crs.domains.dsm;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

public class DsmTestDAO extends SqlMapClientDaoSupport {

	public List<String> getActions() {
		return query("dsm-test.getAllActions");
	}

	public List<String> getEvents() {
		return query("dsm-test.getAllEvents");
	}

	public List<String> getNrpStatuses() {
		return query("dsm-test.getAllNRPStatuses");
	}

	public List<String> getRenewalModes() {
		return query("dsm-test.getAllRenewalModes");
	}

	public List<String> getAllHolderTypes() {
		return query("dsm-test.getAllHolderTypes");
	}

	public List<String> getAllCustTypes() {
		return query("dsm-test.getAllCustTypes");
	}

    private List<String> query(String query) {
        return getSqlMapClientTemplate().queryForList(query);
    }
}
