package pl.nask.crs.country.dao.ibatis;

import pl.nask.crs.commons.dao.ibatis.GenericIBatisDAO;
import pl.nask.crs.country.Country;
import pl.nask.crs.country.dao.CountryDAO;

public class CountryIBatisDAO extends GenericIBatisDAO<Country, Long> implements CountryDAO {
    public CountryIBatisDAO() {
        setFindQueryId("country.findCountries");
        setGetQueryId("country.getCountry");
    }
}
