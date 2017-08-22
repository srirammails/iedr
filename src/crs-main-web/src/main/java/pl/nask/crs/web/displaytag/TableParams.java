package pl.nask.crs.web.displaytag;

import java.util.ArrayList;
import java.util.List;

import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.commons.utils.Validator;

/**
 * Encapsulates displaytag:table parameters (sorting, paging)
 * 
 * 
 * 
 * (C) Copyright 2008 NASK Software Research & Development Department
 * 
 * @author Artur Gniadzik
 * 
 */
public class TableParams {
    // determines sorting direction
    private boolean ascending;
    // number of the result page to be displayed
    private int page = 1;
    // sorting criterium
    private String sortBy;
    
    
    public boolean isAscending() {
        return ascending;
    }

    public void setAscending(boolean ascending) {
        this.ascending = ascending;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        if (page <= 0) { 
        	this.page = 1;
        } else {
        	this.page = page;
        }
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    /**
     * If sorting criterium was set, it creates and returns list containing this
     * criterium, if no criterium (or an empty one) is set, null is returned.
     * 
     */
    public List<SortCriterion> createSortingCriteria(String defaultSortBy) {
        if (!Validator.isEmpty(sortBy)) {
            ArrayList<SortCriterion> orderBy = new ArrayList<SortCriterion>();
            orderBy.add(new SortCriterion(sortBy, ascending));
            return orderBy;
        } else if (!Validator.isEmpty(defaultSortBy)){
            ArrayList<SortCriterion> orderBy = new ArrayList<SortCriterion>();
            orderBy.add(new SortCriterion(defaultSortBy, ascending));
            return orderBy;
        }
        return null;
    }
    
    
}
