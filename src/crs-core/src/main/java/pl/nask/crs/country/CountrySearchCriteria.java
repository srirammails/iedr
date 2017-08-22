package pl.nask.crs.country;

import pl.nask.crs.commons.search.SearchCriteria;

public class CountrySearchCriteria implements SearchCriteria<Country> {

	private String countryName;

	public CountrySearchCriteria(String country) {
		this.countryName = country;
	}

	public String getCountryName() {
		return countryName;
	}	
}
