package pl.nask.crs.domains.exceptions;

public class BulkExportAuthCodesException extends Exception {

    int domainCount;
    int totalDomainCount;

    public BulkExportAuthCodesException(String message, int count, int totalCount) {
        super(message);
        this.domainCount = count;
        this.totalDomainCount = totalCount;
    }

    public int getDomainCount() {
        return domainCount;
    }

    public int getTotalDomainCount() {
        return totalDomainCount;
    }

}
