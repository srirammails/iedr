package pl.nask.crs.web.task;

import java.util.List;

import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.scheduler.Job;
import pl.nask.crs.scheduler.JobSearchCriteria;
import pl.nask.crs.scheduler.SchedulerCron;
import pl.nask.crs.web.GenericSearchAction;
import pl.nask.crs.web.displaytag.TableParams;
import pl.nask.crs.web.displaytag.TicketsPaginatedList;


/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public class JobHistViewAction extends GenericSearchAction<Job, JobSearchCriteria> {

    private SchedulerCron scheduler;

	public JobHistViewAction(SchedulerCron scheduler) {
		this.scheduler = scheduler;
	}
    
    protected TicketsPaginatedList<Job> performSearch(JobSearchCriteria searchCriteria, TableParams tableParams, int pageSize) throws Exception {
        List<SortCriterion> orderBy = tableParams.createSortingCriteria(getDefaultSortBy());
        // feature #747 - use hidden search criteria to perform search
        LimitedSearchResult<Job> searchResult = scheduler.findJobsHistory(getUser(), searchCriteria, ((long) (tableParams.getPage() - 1))
                * pageSize, pageSize, orderBy);
        return new TicketsPaginatedList<Job>(searchResult.getResults(), (int) searchResult
                .getTotalResults(),
                tableParams, pageSize);
    }

	@Override
    protected JobSearchCriteria createSearchCriteria() {
        return new JobSearchCriteria();
    }
}
