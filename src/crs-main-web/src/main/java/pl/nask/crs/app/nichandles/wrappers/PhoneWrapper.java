package pl.nask.crs.app.nichandles.wrappers;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class PhoneWrapper {
	private List<String> currentList;
	private Set<String> currentSet;
	
	/**
	 * Constructs a wrapper around the given set of phones (strings). 
	 * @param phones
	 */
	public PhoneWrapper(Set<String> phones) {
		this.currentSet = phones;
		this.currentList = new ArrayList<String>();
		currentList.addAll(phones);
	}
	
	public List<String> getCurrentList() {
		return currentList;
	}

	public void setPhone(List<String> phones) {
	    // first element of the list is artificial
        phones = phones.subList(1, phones.size());

        currentSet.clear();
	    currentList.clear();
	    
        currentSet.addAll(phones);
		//this approach maintains order
		for(String p: phones) {
			if(!currentList.contains(p))
				currentList.add(p);
		}
	}    
}
 