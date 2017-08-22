package pl.nask.crs.entities.exceptions;

/**
 * @author: Marcin Tkaczyk
 */
public class HolderCategoryNotExistException extends Exception {
	private static final long serialVersionUID = 739141510361395399L;
	private final String holderCategory;

	public HolderCategoryNotExistException(String holderCategory) {
		this.holderCategory = holderCategory;
    }

	public String getDomainHolderCategory() {	
		return holderCategory;
	}

	@Override
	public String getMessage() {
		return "Holder category does not exist: " + holderCategory;
	}
}
