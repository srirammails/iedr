package pl.nask.crs.nichandle.testhelp;

import static org.testng.AssertJUnit.assertNull;
import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertTrue;
import pl.nask.crs.commons.utils.DateUtils;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.nichandle.NicHandle;
import pl.nask.crs.nichandle.Vat;
import pl.nask.crs.nichandle.search.NicHandleSearchCriteria;
import pl.nask.crs.nichandle.search.HistoricalNicHandleSearchCriteria;
import pl.nask.crs.history.HistoricalObject;
import pl.nask.crs.accounts.Account;

import java.util.*;
import java.sql.Timestamp;

import static java.util.Calendar.*;

/**
 * @author Marianna Mysiorska
 */
public class NicHandleTestHelp {

    public static Date date = new Date(1238783600000L);

    public static NicHandleSearchCriteria criteria1;
    public static HistoricalNicHandleSearchCriteria histCriteria1;
    public static String nicHandleIdAA11;

    static {
        criteria1 = new NicHandleSearchCriteria();
        histCriteria1 = new HistoricalNicHandleSearchCriteria();
        criteria1.setNicHandleId("AAE");
        histCriteria1.setNicHandleId("AA11-IEDR");
        nicHandleIdAA11 = "AA11-IEDR";
    }

    public static void compareNicHandleList(List<NicHandle> actualNH, List<NicHandle> expectedNH){
        assertEquals(actualNH.size(), expectedNH.size());
        for (int i = 0; i < actualNH.size(); i++) {
            compareNicHandleNotExactDates(actualNH.get(i), expectedNH.get(i));
        }
    }

    public static void compareHistoricalNHList(List<HistoricalObject<NicHandle>> expected, List<HistoricalObject<NicHandle>> actual){
        if (expected == null)
            assertNull(actual);
        else{
            assertEquals(actual.size(), expected.size());
            for(int i = 0; i< actual.size(); i++){
                compareHistoricalNicHandles(actual.get(i), expected.get(i));
            }
        }
    }

    public static void compareHistoricalNicHandles(HistoricalObject actual, HistoricalObject expected){
        assertEquals(actual.getChangeDate().getTime(), expected.getChangeDate().getTime());
        assertEquals(actual.getChangedBy(), expected.getChangedBy());
        compareNicHandleNotExactDates((NicHandle)actual.getObject(), (NicHandle)expected.getObject());
    }

    public static void compareNicHandle(NicHandle actual, NicHandle expected) {
        if (actual == null)
            assertNull(expected);
        else {
            compareNicHandlesNoDates(actual, expected);
            assertEquals(actual.getStatusChangeDate(), expected.getStatusChangeDate());
            assertEquals(actual.getRegistrationDate(), expected.getRegistrationDate());
            assertEquals(actual.getChangeDate(), expected.getChangeDate());
        }
    }

    public static void compareNicHandleNotExactDates(NicHandle actual, NicHandle expected) {
        if (actual == null)
            assertNull(expected);
        else {
        	compareNicHandlesNoDates(actual, expected);
        	assertSameDate("StatusChangeDate", actual.getStatusChangeDate(), expected.getStatusChangeDate());
        	assertSameDate("RegDate", actual.getRegistrationDate(), expected.getRegistrationDate());
        	assertSameDate("StatusChangeDate", actual.getStatusChangeDate(), expected.getStatusChangeDate());
        	assertAlmostSameTime("ChangeDate", actual.getChangeDate(), expected.getChangeDate());
        	
            Calendar actStsCal = Calendar.getInstance();
            Calendar expStsCal = Calendar.getInstance();

            actStsCal.setTime(actual.getStatusChangeDate());
            expStsCal.setTime(expected.getStatusChangeDate());
        }
    }

    private static void assertAlmostSameTime(String dateType, Date actual, Date expected) {
    	if (!DateUtils.isAlmostSameTime(actual, expected, 10)) {
    		Calendar actChngCal = Calendar.getInstance();
    		Calendar expChngCal = Calendar.getInstance();
    		actChngCal.setTime(actual);
    		expChngCal.setTime(expected);
    		assertEquals(dateType + ":year", actChngCal.get(YEAR), expChngCal.get(YEAR));
    		assertEquals(dateType + ":month", actChngCal.get(MONTH), expChngCal.get(MONTH));
    		assertEquals(dateType + ":day", actChngCal.get(DAY_OF_MONTH), expChngCal.get(DAY_OF_MONTH));
    		assertEquals(dateType + ":hour", actChngCal.get(HOUR_OF_DAY), expChngCal.get(HOUR_OF_DAY));
    		assertEquals(dateType + ":minute", actChngCal.get(MINUTE), expChngCal.get(MINUTE));
    		assertEquals(dateType + ":second", actChngCal.get(SECOND), expChngCal.get(SECOND));
    	}
	}

	private static void assertSameDate(String dateType, Date actual, Date expected) {
        Calendar actCal = Calendar.getInstance();
        Calendar expCal = Calendar.getInstance();
        actCal.setTime(actual);
        expCal.setTime(expected);
        assertEquals(dateType + ":year", actCal.get(YEAR), expCal.get(YEAR));
        assertEquals(dateType + ":month", actCal.get(MONTH), expCal.get(MONTH));
        assertEquals(dateType + ":day", actCal.get(DAY_OF_MONTH), expCal.get(DAY_OF_MONTH));
	}

	private static void compareNicHandlesNoDates(NicHandle actual, NicHandle expected) {
    	assertEquals("VatCategory", expected.getVatCategory(), actual.getVatCategory());
        assertEquals("NicHandleId", expected.getNicHandleId(), actual.getNicHandleId());
        assertEquals("Name", expected.getName(), actual.getName());
        assertEquals("Account.id", expected.getAccount().getId(), actual.getAccount().getId());
        assertEquals("Company Name", expected.getCompanyName(), actual.getCompanyName());
        assertEquals("Address", expected.getAddress(), actual.getAddress());
        assertEquals("County", expected.getCounty(), actual.getCounty());
        assertEquals("Country", expected.getCountry(), actual.getCountry());
        assertEquals("Email", expected.getEmail(), actual.getEmail());
        assertEquals("Status", expected.getStatus(), actual.getStatus());
        assertEquals("isBillCInd()", expected.isBillCInd(), actual.isBillCInd());
        assertEquals("NicHandleRemark", expected.getNicHandleRemark(), actual.getNicHandleRemark());
        assertEquals("Creator", expected.getCreator(), actual.getCreator());
        compareStringSets("Faxes", expected.getFaxes(), actual.getFaxes());
        compareStringSets("Phones", expected.getPhones(), actual.getPhones());
        
        if (expected.getVat() == null)
            assertNull("VAT", actual.getVat());
        else if (Validator.isEmpty(expected.getVat().getVatNo())) {
            assertTrue("Empty VAT No", Validator.isEmpty(actual.getVat().getVatNo()));
        } else {
            assertEquals("VAT No", expected.getVat().getVatNo(), actual.getVat().getVatNo());
        }
	}

	public static void compareStringSets(String setName, Set<String> actual, Set<String> expected){
        if (actual == null)
            assertNull(setName, expected);
        else {
            assertEquals(setName + ".size", actual.size(), expected.size());
            for (String s: actual){
                assertTrue("actual " + setName + " contains " + s, expected.contains(s));
            }
            for (String s: expected){
                assertTrue("expected " + setName + " contains " + s, actual.contains(s));
            }
        }
    }
    
    public static List<NicHandle> createNHAAE(){
        List<NicHandle> nhs = new ArrayList<NicHandle>();
        nhs.add(createNHAAE359());
        nhs.add(createNHAAE553());
        return nhs;
    }

    public static List<NicHandle> createNHAAELimited(){
        List<NicHandle> nhs = new ArrayList<NicHandle>();
        nhs.add(createNHAAE359());
        return nhs;
    }

    public static List<HistoricalObject<NicHandle>> createNHABD275(){
        ArrayList<HistoricalObject<NicHandle>> results = new ArrayList<HistoricalObject<NicHandle>>();
        results.add(createNHABD0());
        results.add(createNHABD1());
        results.add(createNHABD2());
        return results;
    }
    
    public static NicHandle createNHAA11(){
        HashSet<String> faxes = new HashSet<String>();
        HashSet<String> phones = new HashSet<String>();
        faxes.add("33333");
        phones.add("22222");
        return new NicHandle("AA11-IEDR", "Aine Andrews", new Account(103, "Kerna Communications"), "Art Teachers Association", "NHAddress000001", phones, faxes, "Co. Cork", "Ireland", "NHEmail000001@server.kom", NicHandle.NHStatus.Active, new Date(1038783600000L), new Date(61558), new Timestamp(1195038000000L), true, "updated bill-c from waived-iedr ", "AA11-IEDR", new Vat("GB747832695"), "A");
    }

    public static NicHandle createNHAAE359(){
        return new NicHandle("AAE359-IEDR", "Iron Mountain Inc", new Account(1, "GUEST ACCOUNT"), "Iron Mountain, Inc.", "NHAddress000007", null, null, "Delaware", "USA", "NHEmail000007@server.kom", NicHandle.NHStatus.Active, new Date(1050530400000L), new Date(61558), new Timestamp(1213632700000L), true, "Transfer to Iron Mountain, order id 20080616162237-1052-nicorette", "AAE359-IEDR", new Vat(null), "S");
    }
    public static NicHandle createNHAAE553(){
        return new NicHandle("AAE553-IEDR", "Blacknight.ie", new Account(237, "Blacknight.ie"), "Blacknight Internet Solutions Ltd", "NHAddress000008", null, null, "Co. Carlow", "Ireland", "NHEmail000008@server.kom", NicHandle.NHStatus.Active, new Date(1189116000000L), new Date(1166569200000L), new Timestamp(1200650008000L), true, "AAE553-IEDR by CIARA-IEDR on 18/01/2008 09:53:28", "EMAIL-IEDR", new Vat(null), "S");
    }

    public static NicHandle createNHnew(){
        return new NicHandle("MYMY-IEDR", "Blacknight.ie", new Account(237, "Blacknight.ie"), "Blacknight Internet Solutions Ltd", "NHAddress000008", null, null, "Co. Carlow", "Ireland", "NHEmail000008@server.kom", NicHandle.NHStatus.Active, new Date(1189116000000L), new Date(1166569200000L), new Timestamp(1200650008000L), true, "AAE553-IEDR by CIARA-IEDR on 18/01/2008 09:53:28", "EMAIL-IEDR", new Vat(null), null);
    }

    public static HistoricalObject<NicHandle> createHistoricalNicHandleAA11(){
        NicHandle nh = new NicHandle("AA11-IEDR", "Aine Andrews", new Account(103, "Kerna Communications"), "Art Teachers Association", "NHAddress000001", null, null, "Co. Cork", "Ireland", "NHEmail000001@server.kom", NicHandle.NHStatus.Active, new Date(1038783600000L), new Date(61558), new Timestamp(1195038000000L), true, "updated bill-c from waived-iedr ", "AA11-IEDR", new Vat(null), null);
        return new HistoricalObject<NicHandle>(nh , date, "TEST-IEDR");
    }


    public static HistoricalObject<NicHandle> createNHABD0(){
        HashSet<String> phones = new HashSet<String>();
        phones.add("+353868572770");
        phones.add("+353868572771");
        NicHandle nh = new NicHandle("ABD275-IEDR", "Colette Murray", new Account(101, "Irish Domains"), "Airtec Compressors Ltd.", "address2", phones, null, "Co. Cork", "Ireland", "email1@aa.a", NicHandle.NHStatus.Renew, new Date(1196895600000L), new Date(61558), new Timestamp(1196932707000L), false, "Edit - Via Reseller Console", "IDL2-IEDR", new Vat(null), null);
        return new HistoricalObject<NicHandle>(nh , new Date(1196932707000L), "Console");
    }

    public static HistoricalObject<NicHandle> createNHABD1(){
        HashSet<String> phones = new HashSet<String>();
        phones.add("021 431 2222");
        NicHandle nh = new NicHandle("ABD275-IEDR", "Colette Murray",  new Account(101, "Irish Domains"), "Airtec Compressors Ltd.", "address2", phones, null, "Co. Cork", "Ireland", "email2@aa.a", NicHandle.NHStatus.Renew, new Date(1196895600000L), new Date(61558), new Timestamp(1196932707000L), false, "re-activated by PAUL-IEDR on 06/12/2007 09:18:31", null, new Vat(null), null);
        return new HistoricalObject<NicHandle>(nh , new Date(1196673511000L), "PHOENIX");
    }

    public static HistoricalObject<NicHandle> createNHABD2(){
        HashSet<String> phones = new HashSet<String>();
        HashSet<String> faxes = new HashSet<String>();
        phones.add("55");
        phones.add("34");
        phones.add("33");
        faxes.add("12");
        faxes.add("11");
        NicHandle nh = new NicHandle("ABD275-IEDR", "Colette Murray", new Account(101, "Irish Domains"), "Airtec Compressors Ltd.", "address3", phones, faxes, "Co. Cork", "Ireland", "email2@aa.a", NicHandle.NHStatus.Active, new Date(1196895600000L), new Date(61558), new Timestamp(1196932707000L), false, "remark", null, new Vat(null), null);
        return new HistoricalObject<NicHandle>(nh , new Date(1196500711000L), "PHOENIX");
    }

}
