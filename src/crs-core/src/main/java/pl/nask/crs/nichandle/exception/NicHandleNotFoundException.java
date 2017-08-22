package pl.nask.crs.nichandle.exception;

/**
 * @author Marianna Mysiorska
 */
public class NicHandleNotFoundException  extends NicHandleException {

    private String nicHandleId;

    public String getNicHandleId() {
        return nicHandleId;
    }

    public NicHandleNotFoundException(String nicHandleId) {
        super("Nic handle not found: " + nicHandleId);
        this.nicHandleId = nicHandleId;
    }

	public NicHandleNotFoundException(Exception e, String nicHandleId) {
		super("Nic handle not found: " + nicHandleId, e);		
		this.nicHandleId = nicHandleId;
	}
}
