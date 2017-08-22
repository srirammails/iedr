package pl.nask.crs.country;

/**
 * @author: Marcin Tkaczyk
 */
public class CountyNotExistsException extends InvalidCountyException {

	public CountyNotExistsException(String county) {
		super(county);
	}
}
