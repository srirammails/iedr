package pl.nask.crs.domains.dsm.actions;

import org.apache.commons.lang.time.DateUtils;

import pl.nask.crs.domains.Domain;
import pl.nask.crs.domains.dsm.DsmEvent;
import pl.nask.crs.security.authentication.AuthenticatedUser;

public class SetSuspensionDateRenewal extends AbstractDsmAction {
	@Override
	protected void invokeAction(AuthenticatedUser user, Domain domain, DsmEvent event) {
		domain.setSuspensionDate(DateUtils.addDays(domain.getRenewDate(), getGlobalConfig().getNRPConfig().getNrpMailedPeriod() + 1));
		domain.setDeletionDate(DateUtils.addDays(domain.getSuspensionDate(), getGlobalConfig().getNRPConfig().getNrpSuspendedPeriod()));
	}
}
