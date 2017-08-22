package pl.nask.crs.entities.exceptions;

/**
 * @author: Marcin Tkaczyk
 */
public class HolderClassNotExistException extends Exception {
	private static final long serialVersionUID = -5523051646268991575L;
	
	private String holderClass;

	public HolderClassNotExistException(String holderClass) {
    	this.holderClass = holderClass;
    }

	public String getDomainHolderClass() { 
		return holderClass;
	}
	
	@Override
	public String getMessage() {
		return "Holder class does not exist: " + holderClass;
	}
}
