package pl.nask.crs.iedrapi.tests;

import ie.domainregistry.ieapi_domain_1.DsmStateType;
import ie.domainregistry.ieapi_ticket_1.ContactType;
import ie.domainregistry.ieapi_ticket_1.HolderType;
import ie.domainregistry.ieapi_ticket_1.InfDataType;
import ie.domainregistry.ieapi_ticket_1.NsType;
import ie.domainregistry.ieapicom_1.ContactAttrType;
import pl.nask.crs.commons.utils.Validator;

import javax.xml.datatype.XMLGregorianCalendar;
import java.util.Iterator;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;

/**
 * (C) Copyright 2011 NASK
 * Software Research & Development Department
 */
public final class TestHelper {

    private TestHelper() {}

    public static void compareTickets(InfDataType actual, InfDataType expected) {
        compareTickets(actual, expected, null);
    }

    public static void compareTickets(InfDataType actual, InfDataType expected, XMLGregorianCalendar expectedDate) {
        assertEquals(actual.getDnsStatus(), expected.getDnsStatus());
        assertEquals(actual.getHostmasterRemarks(), expected.getHostmasterRemarks());
        assertEquals(actual.getHostmasterStatus(), expected.getHostmasterStatus());
        assertEquals(actual.getAccount(), expected.getAccount());
        compareContacts(actual.getContact(), expected.getContact());
        compareHolder(actual.getHolder(), expected.getHolder());
        assertEquals(actual.getName(), expected.getName());
        compareNs(actual.getNs(), expected.getNs());
        assertEquals(actual.getType(), expected.getType());
        if (expectedDate == null) {
            compareDatesSkippingHours(actual.getRegDate(), expected.getRegDate());
            compareDatesSkippingHours(actual.getRenDate(), expected.getRenDate());
        } else {
            compareDatesSkippingHours(actual.getRegDate(), expectedDate);
            compareDatesSkippingHours(actual.getRenDate(), expectedDate);
        }
    }

    private static void compareDatesSkippingHours(XMLGregorianCalendar actual, XMLGregorianCalendar expected) {
        if (actual == null) {
            assertNull(expected);
        } else {
            assertEquals(actual.getYear(), expected.getYear());
            assertEquals(actual.getMonth(), expected.getMonth());
            assertEquals(actual.getDay(), expected.getDay());
        }
    }

    public static void compareTicketsSkipAdminContactAndDates(InfDataType actual, InfDataType expected) {
        assertEquals(actual.getDnsStatus(), expected.getDnsStatus());
        if (!(Validator.isEmpty(actual.getHostmasterRemarks()) && Validator.isEmpty(expected.getHostmasterRemarks()))) {
            assertEquals(actual.getHostmasterRemarks(), expected.getHostmasterRemarks());
        }
        assertEquals(actual.getHostmasterStatus(), expected.getHostmasterStatus());
        assertEquals(actual.getAccount(), expected.getAccount());
        compareContactsWithAdminContactSkipped(actual.getContact(), expected.getContact());
        compareHolder(actual.getHolder(), expected.getHolder());
        assertEquals(actual.getName(), expected.getName());
        compareNs(actual.getNs(), expected.getNs());
        assertEquals(actual.getType(), expected.getType());
    }

    public static void compareNs(List<NsType> actual, List<NsType> expected) {
        assertEquals(actual.size(), expected.size());
        for (int i = 0; i < expected.size(); i++) {
            assertEquals(actual.get(i).getNsName(), expected.get(i).getNsName());
            List<String> actualNsAddreses = actual.get(i).getNsAddr();
            List<String> expectedNsAddreses = actual.get(i).getNsAddr();
            removeEmptyStringsFromList(actualNsAddreses);
            removeEmptyStringsFromList(expectedNsAddreses);
            assertEquals(actualNsAddreses.toArray(), expectedNsAddreses.toArray());
        }

    }

    public static void removeEmptyStringsFromList(List<String> list) {
        for (Iterator<String> i = list.iterator() ; i.hasNext();) {
            String s = i.next();
            if (Validator.isEmpty(s)) {
                i.remove();
            }
        }
    }

    public static void compareHolder(HolderType actual, HolderType expected) {
        assertEquals(actual.getClazz(), expected.getClazz());
        assertEquals(actual.getHolderRemarks(), expected.getHolderRemarks());
        assertEquals(actual.getHolderName().getCategory(), expected.getHolderName().getCategory());
        assertEquals(actual.getHolderName().getValue(), expected.getHolderName().getValue());
    }


    public static void compareContacts(List<ContactType> actual, List<ContactType> expected) {
        assertEquals(actual.size(), expected.size());
        for (int i = 0; i < expected.size(); i++) {
            assertEquals(actual.get(i).getType(), expected.get(i).getType());
            assertEquals(actual.get(i).getValue(), expected.get(i).getValue());
        }
    }

    public static void compareContactsWithAdminContactSkipped(List<ContactType> actual, List<ContactType> expected) {
        assertEquals(actual.size(), expected.size());
        for (int i = 0; i < expected.size(); i++) {
            assertEquals(actual.get(i).getType(), expected.get(i).getType());
            if (!actual.get(i).getType().equals(ContactAttrType.ADMIN))
                assertEquals(actual.get(i).getValue(), expected.get(i).getValue());
        }
    }

    public static void compareDomainsInfo(ie.domainregistry.ieapi_domain_1.InfDataType responseValue, ie.domainregistry.ieapi_domain_1.InfDataType exampleValue) {
        assertEquals(responseValue.getName(), exampleValue.getName(), "domain info result : domain name");
        assertEquals(responseValue.getAccount(), exampleValue.getAccount(), "domain info result : account");
        assertEquals(responseValue.getHolder().getHolderName().getValue(), exampleValue.getHolder().getHolderName().getValue(), "domain info result : holder");
        assertStatusEquals(responseValue.getStatus(), exampleValue.getStatus());
        assertEquals(responseValue.getRegDate(), exampleValue.getRegDate(), "domain info result : registration date");
        assertEquals(responseValue.getRenDate(), exampleValue.getRenDate(), "domain info result : renewal date");
        assertEquals(responseValue.getSuspendDate(), exampleValue.getSuspendDate(), "domain info result : suspend date");
        assertEquals(responseValue.getDeleteDate(), exampleValue.getDeleteDate(), "domain info result : delete date");
        compareNsLists(responseValue.getNs(), exampleValue.getNs(), "domain info result");
    }
    
    public static void assertStatusEquals(DsmStateType actual, DsmStateType expected) {
    	assertEquals(actual.getHolderType(), expected.getHolderType(), "domain status : holderType");
    	assertEquals(actual.getRenewalStatus(), expected.getRenewalStatus(), "domain status : renewalStatus");
    	assertEquals(actual.getRenewalMode(), expected.getRenewalMode(), "domain status : renewalMode");
    	assertEquals(actual.getWipo(), expected.getWipo(), "domain status: wipo");
    }

    public static void compareNsLists(List<ie.domainregistry.ieapi_domain_1.NsType> responseList, List<ie.domainregistry.ieapi_domain_1.NsType> exampleList, String message) {
        assertEquals(responseList.size(), exampleList.size(), "ns list size");
        for (int i = 0; i < responseList.size(); i++) {
            assertEquals(responseList.get(i).getNsName(), exampleList.get(i).getNsName(), message + " : ns" + i +" name");
            if (!areIpAddressesEmpty(responseList.get(i).getNsAddr(), exampleList.get(i).getNsAddr()))
                assertEquals(responseList.get(i).getNsAddr(), exampleList.get(i).getNsAddr(), message + " : ns" + i + " ip");
        }
    }

    public static void compareStatuses(DsmStateType responseState, DsmStateType exampleState, String message) {
        assertEquals(responseState.getHolderType(), exampleState.getHolderType(), message);
        assertEquals(responseState.getRenewalStatus(), exampleState.getRenewalStatus(), message);
        assertEquals(responseState.getRenewalMode(), exampleState.getRenewalMode(), message);
        assertEquals(responseState.getWipo(), exampleState.getWipo(), message);
    }

    public static void compareHolders(HolderType responseHolder, HolderType exampleHolder, String message) {
        assertEquals(responseHolder.getClazz(), exampleHolder.getClazz(), message);
        assertEquals(responseHolder.getHolderRemarks(), exampleHolder.getHolderRemarks(), message);
        assertEquals(responseHolder.getHolderName().getCategory(), exampleHolder.getHolderName().getCategory(), message);
        assertEquals(responseHolder.getHolderName().getValue(), exampleHolder.getHolderName().getValue(), message);
    }

    public static void compareHolders(ie.domainregistry.ieapi_domain_1.HolderType responseHolder, ie.domainregistry.ieapi_domain_1.HolderType exampleHolder, String message) {
        assertEquals(responseHolder.getClazz(), exampleHolder.getClazz(), message);
        assertEquals(responseHolder.getHolderRemarks(), exampleHolder.getHolderRemarks(), message);
        assertEquals(responseHolder.getHolderName().getCategory(), exampleHolder.getHolderName().getCategory(), message);
        assertEquals(responseHolder.getHolderName().getValue(), exampleHolder.getHolderName().getValue(), message);
    }

    private static boolean areIpAddressesEmpty(String actual, String example) {
        return Validator.isEmpty(actual) && Validator.isEmpty(example);
    }

}
