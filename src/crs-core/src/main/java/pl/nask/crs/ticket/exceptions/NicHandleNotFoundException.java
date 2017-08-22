package pl.nask.crs.ticket.exceptions;


/**
 * @author Patrycja Wegrzynowicz
 */
public class NicHandleNotFoundException extends TicketException {

    private String nicHandle;

    public NicHandleNotFoundException(String nicHandle) {
        this.nicHandle = nicHandle;
    }

    public NicHandleNotFoundException(Throwable cause, String nicHandle) {
		this.nicHandle = nicHandle;
	}

	public String getNicHandle() {
        return nicHandle;
    }

}
