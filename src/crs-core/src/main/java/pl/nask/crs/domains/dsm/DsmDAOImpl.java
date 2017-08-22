package pl.nask.crs.domains.dsm;

import java.util.List;
import pl.nask.crs.commons.dao.ibatis.GenericIBatisDAO;

public class DsmDAOImpl extends GenericIBatisDAO implements DsmDAO {

	@Override
	public boolean checkEvent(String domainName, DsmEventName eventName) {
		FindParameters params = prepareFindParams(domainName, eventName);
		Integer count = (Integer) performQueryForObject("dsm.countEvents", params);
		return (count == 1);
	}

	private FindParameters prepareFindParams(String domainName, DsmEventName eventName) {
		return prepareFindParams(domainName).addCriterion("eventName", eventName.name());
	}

	private FindParameters prepareFindParams(String domainName) {
		FindParameters params = new FindParameters();
		params.addCriterion("domainName", domainName);
		return params;
	}

	@Override
	public DsmTransition getTransitionFor(String domainName, DsmEventName eventName) {
		FindParameters params = prepareFindParams(domainName, eventName);
		DsmTransition transition = (DsmTransition) performQueryForObject("dsm.getTransition", params);
		return transition;
	}

	@Override
	public List<String> getValidEventNames(String domainName) {
		return performQueryForList("dsm.getEventNames", prepareFindParams(domainName));
	}

}
