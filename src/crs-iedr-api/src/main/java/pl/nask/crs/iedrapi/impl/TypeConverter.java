package pl.nask.crs.iedrapi.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import pl.nask.crs.commons.Period;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.domains.Domain;
import pl.nask.crs.iedrapi.IedrApiConfig;
import pl.nask.crs.nichandle.NicHandle;

public final class TypeConverter {
    private TypeConverter() {
    }

    public static String setToString(Set<String> values) {
        if (values == null || values.size() == 0)
            return null;
        StringBuilder sb = new StringBuilder();
        for (String v : values) {
            sb.append(v).append(", ");
        }
        sb.delete(sb.length() - 2, sb.length());

        return sb.toString();
    }

    @SuppressWarnings("unchecked")
    public static Set<String> stringToSet(String value) {
        if (Validator.isEmpty(value))
            return Collections.EMPTY_SET;
        String[] values = value.split(",");

        Set<String> res = new HashSet<String>();
        for (String v : values) {
            res.add(v.trim());
        }

        return res;
    }

    /**
     * converts 'page' value to 'offset' value basing on the pageSize
     *
     * @param page
     * @return
     */
    public static long pageToOffset(int page) {
        return IedrApiConfig.getPageSize() * (page - 1);
    }

    /**
     * converts 'totalResults' value to 'totalPages' value basing on the pageSize
     *
     * @param totalResults
     * @return
     */
    public static int totalResultsToPages(long totalResults) {
        return (int) (totalResults + IedrApiConfig.getPageSize() - 1) / IedrApiConfig.getPageSize();
    }

    public static List<String> nhToId(List<NicHandle> results) {
        List<String> res = new ArrayList<String>();

        for (NicHandle nh : results) {
            res.add(nh.getNicHandleId());
        }
        return res;
    }

    public static List<String> domainToName(List<Domain> result) {
        List<String> res = new ArrayList<String>();
        for (Domain d : result) {
            res.add(d.getName());
        }
        return res;
    }

    public static Map<String, Period> convertToDomainsPeriodMap(List<String> domainsNames, Period period) {
        HashMap<String, Period> ret = new HashMap<String, Period>();
        for (String domainName : domainsNames) {
            ret.put(domainName, period);
        }
        return ret;
    }

    public static boolean commandFieldToBoolean(Boolean field) {
        if (field == null) {
            return false;
        }
        return field.booleanValue();
    }
}
