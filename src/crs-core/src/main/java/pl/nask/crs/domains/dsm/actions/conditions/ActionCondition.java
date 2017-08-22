package pl.nask.crs.domains.dsm.actions.conditions;

import pl.nask.crs.domains.Domain;
import pl.nask.crs.domains.dsm.DsmEvent;

public interface ActionCondition {
	boolean accept(Domain domain, DsmEvent event);
}