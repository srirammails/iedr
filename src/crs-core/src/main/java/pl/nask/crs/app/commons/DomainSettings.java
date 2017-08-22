package pl.nask.crs.app.commons;

public class DomainSettings {
    private final int minNameserverCount;
    private final int maxNameserverCount;
    private final int failureLimit;

    public DomainSettings(int minCount, int maxCount, int failureLimit) {
        this.minNameserverCount = minCount;
        this.maxNameserverCount = maxCount;
        this.failureLimit = failureLimit;
    }

    public int getMinNameserverCount() {
        return minNameserverCount;
    }

    public int getMaxNameserverCount() {
        return maxNameserverCount;
    }

    public int getFailureLimit() {
        return failureLimit;
    }
}
