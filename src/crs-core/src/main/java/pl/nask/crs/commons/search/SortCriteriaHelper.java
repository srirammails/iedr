package pl.nask.crs.commons.search;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SortCriteriaHelper {
	
	public static boolean isValid(SortCriterion crit, Set<String> validNamesSet) {
		if (crit == null)
			return true;
		if (crit.getSortBy() == null)
			return false;
		if (validNamesSet.contains(crit.getSortBy()))
			return true;
	
		return false;
	 }
	 
	public static List<SortCriterion> filterValid(List<SortCriterion> src, Set<String> validNamesSet) {
		if (src == null)
			return null;
		
		List<SortCriterion> res = new ArrayList<SortCriterion>();
		for (SortCriterion c: src) {
			if (c != null && isValid(c, validNamesSet))
				res.add(c);
		}
		
		return res ;
	}

	public static void validate(List<SortCriterion> sortCriteria, Set<String> validNamesSet) {
		if (sortCriteria == null)
			return;
		
		for (SortCriterion crit: sortCriteria) {
			if (!isValid(crit, validNamesSet))
				throw new IllegalArgumentException("Unsupported SortCriterium: " + crit.toString());
		}
	}	

}
