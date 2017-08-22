package pl.nask.crs.domains.dsm.actions;

import java.util.Date;

import org.apache.commons.lang.time.DateUtils;

import pl.nask.crs.domains.Domain;
import pl.nask.crs.domains.dsm.DsmEvent;
import pl.nask.crs.security.authentication.AuthenticatedUser;

public class SetSuspensionDateCurrent extends AbstractDsmAction {
	@Override
	protected void invokeAction(AuthenticatedUser user, Domain domain, DsmEvent event) {
		domain.setSuspensionDate(DateUtils.addDays(new Date(), getGlobalConfig().getNRPConfig().getNrpMailedPeriod() + 1));
		domain.setDeletionDate(DateUtils.addDays(domain.getSuspensionDate(), getGlobalConfig().getNRPConfig().getNrpSuspendedPeriod()));
	}
}
