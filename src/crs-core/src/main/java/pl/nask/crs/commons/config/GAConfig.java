package pl.nask.crs.commons.config;

/**
 * (C) Copyright 2013 NASK
 * Software Research & Development Department
 */
public class GAConfig {

    private final int GA_PAST_INTERVALS;
    private final int GA_FUTURE_INTERVALS;

    public GAConfig(GenericConfig genericConfig) {
        this.GA_PAST_INTERVALS = (Integer)genericConfig.getEntryOrThrowNpe("ga_pastIntervals").getTypedValue();
        this.GA_FUTURE_INTERVALS = (Integer)genericConfig.getEntryOrThrowNpe("ga_futureIntervals").getTypedValue();
    }

    public int getPastIntervals() {
        return GA_PAST_INTERVALS;
    }

    public int getFutureIntervals() {
        return GA_FUTURE_INTERVALS;
    }
}
