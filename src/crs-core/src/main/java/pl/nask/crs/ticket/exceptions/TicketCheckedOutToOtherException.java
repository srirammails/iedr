package pl.nask.crs.ticket.exceptions;

/**
 * @author Patrycja Wegrzynowicz
 */
public class TicketCheckedOutToOtherException extends TicketException {

    private String checkedOutTo;

    private String changingBy;

    public TicketCheckedOutToOtherException(String checkedOutTo, String changingBy) {
        this.checkedOutTo = checkedOutTo;
        this.changingBy = changingBy;
    }

    public String getCheckedOutTo() {
        return checkedOutTo;
    }

    public String getChangingBy() {
        return changingBy;
    }
}
