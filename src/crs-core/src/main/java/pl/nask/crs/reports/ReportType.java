package pl.nask.crs.reports;

/**
 * @author: Marcin Tkaczyk
 */
public enum ReportType {
    ALL("All"), TICKET_REVISIONS("Tickets Revisions"), DOCUMENTS_LOGGED("Documents Logged");
    private String name;

    ReportType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
