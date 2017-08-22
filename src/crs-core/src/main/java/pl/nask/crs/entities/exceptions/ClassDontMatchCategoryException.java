package pl.nask.crs.entities.exceptions;

/**
 * @author: Marcin Tkaczyk
 */
public class ClassDontMatchCategoryException extends Exception {
	private static final long serialVersionUID = -8935230956710815892L;

	private final String holderClass;
	private final String holderCategory;

	public ClassDontMatchCategoryException(String holderClass, String holderCategory) {
		this.holderClass = holderClass;
		this.holderCategory = holderCategory;
    }

	public String getDomainHolderClass() {
		return holderClass;
	}
	
	public String getHolderCategory() {
		return holderCategory;
	}
	
	@Override
	public String getMessage() {
		return String.format("Class (%s) don't match category (%s)", holderClass, holderCategory);
	}
}
