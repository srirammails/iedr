package pl.nask.crs.domains.dsm.actions.conditions;

import pl.nask.crs.domains.Domain;
import pl.nask.crs.domains.dsm.DsmEvent;

public enum DirectTypeCondition implements ActionCondition {
	REGULAR {
		@Override
		public boolean accept(Domain domain, DsmEvent event) {
			return nhMatch(domain);
		}
	}, LARGE {
		@Override
		public boolean accept(Domain domain, DsmEvent event) {		
			return !nhMatch(domain);
		}
	};	
	
	private static boolean nhMatch(Domain domain) {
		String billingNh = domain.getBillingContact().getNicHandle();
		String adminC = domain.getAdminContacts().get(0).getNicHandle();
		return billingNh.equalsIgnoreCase(adminC);
	}

	@Override
	public abstract boolean accept(Domain domain, DsmEvent event);
}