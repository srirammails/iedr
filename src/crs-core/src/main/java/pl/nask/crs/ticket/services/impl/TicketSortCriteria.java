package pl.nask.crs.ticket.services.impl;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import pl.nask.crs.commons.search.SortCriteriaHelper;
import pl.nask.crs.commons.search.SortCriterion;

public class TicketSortCriteria {
	 private final static String[] validNames = {"id", 
	 "type", 
	 "domainName",
	 "domainNameFR",
	 "domainHolder", 
	 "domainHolderFR", 
	 "resellerAccountId", 
	 "resellerAccountName",
     "agreementSigned", 
     "ticketEdit", 
     "resellerAccountFR", 
     "domainHolderClass", 
     "domainHolderClassFR",
     "domainHolderCat", 
     "domainHolderCatFR", 
     "adminContact1NH", 
     "adminContact1Name", 
     "adminContact1Email", 
     "adminContact1CompanyName", 
     "adminContact1Country", 
     "adminContact1FR",
     "adminContact2NH", 
     "adminContact2Name", 
     "adminContact2Email", 
     "adminContact2CompanyName", 
     "adminContact2Country", 
     "adminContact2FR",
     "techContactNH", 
     "techContactName", 
     "techContactEmail", 
     "techContactCompanyName", 
     "techContactCountry", 
     "techContactFR", 
     "billingContactNH", 
     "billingContactName", 
     "billingContactEmail", 
     "billingContactCompanyName", 
     "billingContactCountry", 
     "billingContactFR",
     "creatorNH", 
     "creatorName", 
     "creatorEmail", 
     "creatorCompanyName", 
     "creatorCountry",
     "ns1", 
     "ns1FR", 
     "ip1", 
     "ip1FR", 
     "ns2", 
     "ns2FR", 
     "ip2", 
     "ip2FR",
     "ns3", 
     "ns3FR", 
     "ip3", 
     "ip3FR",
     "adminStatus", "adminStatusChangeDate",
     "techStatus", "techStatusChangeDate",
     "checkedOut", "checkedOutToNH", "checkedOutToName",
     "renewalDate", "changeDate",
     "requestersRemark",
     "hostmastersRemark",
     "creationDate",        
     "clikPaid"};
	 
	 private final static Set<String> validNamesSet = new HashSet<String>(Arrays.asList(validNames));
	 
	 
	 public static boolean isValid(SortCriterion crit) {
		return SortCriteriaHelper.isValid(crit, validNamesSet);
	 }
	 
	public static List<SortCriterion> filterValid(List<SortCriterion> src) {
		return SortCriteriaHelper.filterValid(src, validNamesSet);
	}

	public static void validate(List<SortCriterion> sortCriteria) {
		SortCriteriaHelper.validate(sortCriteria, validNamesSet);
	}
}
