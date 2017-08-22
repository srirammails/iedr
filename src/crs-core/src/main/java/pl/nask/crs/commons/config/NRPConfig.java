package pl.nask.crs.commons.config;

public class NRPConfig {
	// a period for the domain to become in Mailed state before suspension.
	private final int NRP_MAILED_PERIOD;
	private final int NRP_SUSPENDED_PERIOD;
	
	public NRPConfig(GenericConfig baseConfig) {
		this.NRP_MAILED_PERIOD = (Integer) baseConfig.getEntryOrThrowNpe("nrp_mailed_period").getTypedValue();
		this.NRP_SUSPENDED_PERIOD = (Integer) baseConfig.getEntryOrThrowNpe("nrp_suspended_period").getTypedValue();
	}

	public int getNrpMailedPeriod() {
		return NRP_MAILED_PERIOD;
	}

	public int getNrpSuspendedPeriod() {
		return NRP_SUSPENDED_PERIOD;
	}
}
