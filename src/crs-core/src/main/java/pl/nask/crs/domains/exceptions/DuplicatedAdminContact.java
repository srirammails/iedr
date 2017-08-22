package pl.nask.crs.domains.exceptions;

public class DuplicatedAdminContact extends IllegalArgumentException {
	public DuplicatedAdminContact() {
	}

	public DuplicatedAdminContact(DuplicatedAdminContact e) {
		super(e);
	}
	
	
}
