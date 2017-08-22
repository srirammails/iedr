package pl.nask.crs.accounts.services.impl;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import pl.nask.crs.commons.search.SortCriteriaHelper;
import pl.nask.crs.commons.search.SortCriterion;

public class AccountSortCriteria {
	 private final static String[] validNames = {
		 "id", "name", "billingContactId", "billingContactName", "billingContactEmail", "billingContactCompanyName",
	        "billingContactCountry", "status", "address", "county", "country", "webAddress",
	        "invoiceFreq", "nextInvMonth", "phone", "fax", "tariff", "remark", "creationDate",
	        "statusChangeDate", "changeDate", "agreementSigned", "ticketEdit"
	     };
		 
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
