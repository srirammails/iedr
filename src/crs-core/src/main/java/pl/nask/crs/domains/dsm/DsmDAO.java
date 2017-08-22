package pl.nask.crs.domains.dsm;

import java.util.List;

public interface DsmDAO {

	List<String> getValidEventNames(String domainName);

	DsmTransition getTransitionFor(String domainName, DsmEventName eventName);

	boolean checkEvent(String domainName, DsmEventName eventName);

}
