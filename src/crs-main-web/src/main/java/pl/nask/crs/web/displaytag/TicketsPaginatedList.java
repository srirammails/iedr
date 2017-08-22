package pl.nask.crs.web.displaytag;

import java.util.List;

import org.displaytag.pagination.PaginatedList;
import org.displaytag.properties.SortOrderEnum;

public class TicketsPaginatedList<T> implements PaginatedList {

    private int totalResults;
    private List<T> results;
    private int objectsPerPage;
    private int pageNumber;
    private String sortCriterion;
    private boolean ascending;

    public int getFullListSize() {
        return totalResults;
    }

    public List<T> getList() {
        return results;
    }

    public int getObjectsPerPage() {
        return objectsPerPage;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public String getSearchId() {
        return null;
    }

    public TicketsPaginatedList(List<T> results, int totalResults,
            int pageNumber, int objectsPerPage) {
        this(results, totalResults, pageNumber, objectsPerPage, null, false);
    }

    public TicketsPaginatedList(List<T> results, int totalResults,
            int pageNumber, int objectsPerPage, String sortCriterion,
            boolean ascending) {
        super();
        this.results = results;
        this.totalResults = totalResults;
        this.pageNumber = pageNumber;
        this.objectsPerPage = objectsPerPage;
        this.ascending = ascending;
        this.sortCriterion = sortCriterion;
    }

    public TicketsPaginatedList(List<T> results, int totalResults, TableParams tableParams, int pageSize) {
        this(results, totalResults, tableParams.getPage(), pageSize, tableParams.getSortBy(), tableParams.isAscending());
    }

    public String getSortCriterion() {
        return sortCriterion;
    }

    public SortOrderEnum getSortDirection() {
        return ascending ? SortOrderEnum.ASCENDING : SortOrderEnum.DESCENDING;
    }

}
