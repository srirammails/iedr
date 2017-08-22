package pl.nask.crs.commons.config;

/**
 * (C) Copyright 2013 NASK
 * Software Research & Development Department
 */
public class LoginLockoutConfig {

    private final int INITIAL_LOCKOUT_PERIOD;
    private final int LOCKOUT_INCREASE_FACTOR;
    private final int MAXIMUM_LOCKOUT_PERIOD;

    public LoginLockoutConfig(GenericConfig genericConfig) {
        this.INITIAL_LOCKOUT_PERIOD = (Integer)genericConfig.getEntryOrThrowNpe("initial_lockout_period_seconds").getTypedValue();
        this.LOCKOUT_INCREASE_FACTOR = (Integer)genericConfig.getEntryOrThrowNpe("lockout_increase_factor_seconds").getTypedValue();
        this.MAXIMUM_LOCKOUT_PERIOD = (Integer)genericConfig.getEntryOrThrowNpe("maximum_lockout_period_seconds").getTypedValue();
    }

    // for test only
    public LoginLockoutConfig(int init, int increase, int max) {
        this.INITIAL_LOCKOUT_PERIOD = init;
        this.LOCKOUT_INCREASE_FACTOR = increase;
        this.MAXIMUM_LOCKOUT_PERIOD = max;
    }

    public int getInitialLockoutPeriod() {
        return INITIAL_LOCKOUT_PERIOD;
    }

    public int getLockoutIncreaseFactor() {
        return LOCKOUT_INCREASE_FACTOR;
    }

    public int getMaximumLockoutPeriod() {
        return MAXIMUM_LOCKOUT_PERIOD;
    }
}
