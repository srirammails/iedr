package pl.nask.crs.country;

import java.util.List;

import pl.nask.crs.commons.dictionary.Dictionary;
import pl.nask.crs.commons.search.SearchResult;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.country.dao.CountryDAO;
import pl.nask.crs.country.dao.CountyDAO;
import pl.nask.crs.country.search.CountySearchCriteria;

public class CountryFactory implements Dictionary<Long, Country> {
    private CountryDAO countryDAO;
    private CountyDAO countyDAO;
    private final static String NOT_SPECIFIED = "N/A";
    
    public CountryFactory(CountryDAO countryDAO, CountyDAO countyDAO) {
        super();
        this.countryDAO = countryDAO;
        this.countyDAO = countyDAO;
    }

    public List<Country> getEntries() {
        SearchResult<Country> res = countryDAO.find(null);
        
        for (Country c : res.getResults()) {
            fillWithCounties(c);
        }
        
        return res.getResults();
    }

    private void fillWithCounties(Country c) {
        c.getCounties().addAll(countyDAO.find(new CountySearchCriteria(Long.valueOf(c.getCode()))).getResults());
    }

    public Country getEntry(Long key) {
        Country c = countryDAO.get(key);
        fillWithCounties(c);
        return c;
    }      
    
    /**
     * returns true, if the country and the county exist in the dictionary, or if the country exists and the county may take any value 
     *
     * @param country
     * @param county
     * @param strictCountyValidation
     * @return
     */
    public void validate(String country, String county, boolean strictCountyValidation)  throws InvalidCountryException, InvalidCountyException {
    	List<Country> countries = countryDAO.find(new CountrySearchCriteria(country)).getResults();
    	if (countries.size() != 1)
    		throw new InvalidCountryException();

        // if county is not empty check if exist
        if (strictCountyValidation && !Validator.isEmpty(county)) {
            List<String> counties  = countyDAO.find(new CountySearchCriteria(county)).getResults();
            if (counties.size() == 0)
                throw new CountyNotExistsException(county);
        }

        Country c = countries.get(0);
    	fillWithCounties(c);

        if (c.getCounties().size() == 0 && (Validator.isEmpty(county) || county.equals(NOT_SPECIFIED) || !strictCountyValidation))
    		return;

        if (c.getCounties().size() != 0 && (Validator.isEmpty(county) || county.equals(NOT_SPECIFIED)))
            throw new CountyRequiredException();

        if (!c.getCounties().contains(county))
    		throw new InvalidCountyException(county); 
    }
    
    /**
     * returns true, if the country with given name exists in the 
     * 
     * @param country
     * @return
     */
    public void validateCountry(String country) throws InvalidCountryException {
    	if (countryDAO.find(new CountrySearchCriteria(country)).getResults().size() != 1)
    		throw new InvalidCountryException();    	
    }

	public String getCountryVatCategory(String country) {
		List<Country> countries = countryDAO.find(new CountrySearchCriteria(country)).getResults();
		if (countries.size() != 1) {
			throw new IllegalArgumentException("Cannot find vat_category code for country " + country+ ". Unexpected number of results " + countries.size());
		}
		
		return countries.get(0).getVatCategory();
	}
}
