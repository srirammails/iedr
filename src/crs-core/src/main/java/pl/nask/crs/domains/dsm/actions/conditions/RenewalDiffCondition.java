package pl.nask.crs.domains.dsm.actions.conditions;

import java.util.Date;

import pl.nask.crs.commons.utils.DateUtils;
import pl.nask.crs.domains.Domain;
import pl.nask.crs.domains.dsm.DsmEvent;

public class RenewalDiffCondition implements ActionCondition {

	private int diffDays;

	public RenewalDiffCondition(String condition) {
		this.diffDays = Integer.parseInt(condition);
	}

	@Override
	public boolean accept(Domain domain, DsmEvent event) {
		Date calculatedRenewalDate = DateUtils.startOfDay(DateUtils.getCurrDate(diffDays));
		Date realRenewalDate = DateUtils.startOfDay(domain.getRenewDate());
		
		return realRenewalDate.before(calculatedRenewalDate);
	}
}