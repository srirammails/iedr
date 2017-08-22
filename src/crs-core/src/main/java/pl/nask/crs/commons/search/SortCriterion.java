package pl.nask.crs.commons.search;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import pl.nask.crs.commons.utils.Validator;

/**
 * 
 * (C) Copyright 2008 NASK Software Research & Development Department
 * 
 * @author Artur Gniadzik
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class SortCriterion {
    private String sortBy;
    private boolean ascending;

    /**
     * use {@link #SortCriterion(String, boolean)} instead - this is left public only to let Enunciate generate WS documentation properly
     */
    public SortCriterion() {
    	// for jax-ws
    }
    
    /**
     * Creates sorting criterion. Sorting criterion contains of the
     * column/property name and the sorting direction
     * 
     * @param sortBy
     *            name of the column - null or empty string is forbidden
     * @param ascending
     *            true represents ascending order, false represents descending
     *            order.
     * @throws IllegalArgumentException
     *             if sortBy is null or an empty string
     */
    public SortCriterion(String sortBy, boolean ascending) {
        Validator.assertNotEmpty(sortBy, "sortBy");
        this.sortBy = sortBy;
        this.ascending = ascending;
    }

    public String getSortBy() {
        return sortBy;
    }

    public boolean isAscending() {
        return ascending;
    }

    public String getDirection() {
        return ascending ? "ASC" : "DESC";
    }
    
    @Override
    public String toString() {
    	return "sortBy=" + sortBy + ", ascending=" + ascending;
    }
}
