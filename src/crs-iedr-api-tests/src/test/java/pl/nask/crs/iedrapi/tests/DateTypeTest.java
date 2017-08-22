package pl.nask.crs.iedrapi.tests;

import ie.domainregistry.ieapi_account_1.ResRecordType;
import ie.domainregistry.ieapi_domain_1.CreDataType;
import ie.domainregistry.ieapi_domain_1.InfDataType;

import java.util.Date;

import org.testng.annotations.Test;


/**
 * 
 * Checks, if all types required to be a date (without a timezone) are type of Date (not XmlGregorianCalendar)
 * (if compilation fails, api commands were recompiled from xsd
 * 
 * @author Artur Gniadzik
 *
 */
public class DateTypeTest {
	@Test
	public void testDomainCreDateType() {
		CreDataType t = new CreDataType();
		Date d = t.getCreDate();
		Date d1 = t.getExDate();
	}
	
	@Test
	public void testDomainInfDateType() {
		InfDataType t = new InfDataType();
		Date d = t.getRegDate();
		Date d1 = t.getRenDate();
	}
	
	@Test
	public void testContactCreDateType() {
		ie.domainregistry.ieapi_contact_1.CreDataType t = new ie.domainregistry.ieapi_contact_1.CreDataType();
		Date d = t.getCrDate();
	}
	
	@Test
	public void testContactInfDateType() {
		ie.domainregistry.ieapi_contact_1.InfDataType t = new ie.domainregistry.ieapi_contact_1.InfDataType();
		Date d = t.getCrDate();
	}
	
	@Test
	public void testAccountResRecordType() {
		ResRecordType r = new ResRecordType();
		Date d = r.getMsdDate();
		Date d1 = r.getRegDate();
		Date d2 = r.getRenDate();
		Date d3 = r.getTrnDate();
	}
	
}
