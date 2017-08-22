package pl.nask.crs.documents.dao.ibatis;

import java.util.ArrayList;
import java.util.List;

import pl.nask.crs.commons.dao.ibatis.GenericIBatisDAO;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SearchCriteria;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.documents.dao.ibatis.objects.InternalDocumentReport;
import pl.nask.crs.reports.search.ReportsSearchCriteria;

/**
 * @author: Marcin Tkaczyk, Artur Gniadzik
 */
public class InternalDocumentReportsIBatisDAO extends GenericIBatisDAO<InternalDocumentReport, String> {

	public LimitedSearchResult<InternalDocumentReport> findDocumentReports(ReportsSearchCriteria criteria, long offset, long limit, List<SortCriterion> sortBy) {
		String findQuery = null;
		String countFindQuery = null;

		switch (criteria.getReportTimeGranulation()) {
		case DAY:
			findQuery = "document-reports.getDocumentReportsDayGranulation";
			countFindQuery = "document-reports.getDocumentReportsCountDayGranulation";
			break;
		case MONTH:
			findQuery = "document-reports.getDocumentReportsMonthGranulation";
			countFindQuery = "document-reports.getDocumentReportsCountMonthGranulation";
			break;
		case YEAR:
			findQuery = "document-reports.getDocumentReportsYearGranulation";
			countFindQuery = "document-reports.getDocumentReportsCountYearGranulation";
			break;
		default:
			throw new IllegalArgumentException(criteria.getReportTimeGranulation().name());
		}

		FindParameters params = new FindParameters(criteria).setLimit(offset, limit).setOrderBy(sortBy);

		Integer total = performQueryForObject(countFindQuery, params);

		List<InternalDocumentReport> list;
		if (total == 0) {
			list = new ArrayList<InternalDocumentReport>();
		} else {
			list = performQueryForList(findQuery, params);
		}

		return new LimitedSearchResult<InternalDocumentReport>((SearchCriteria)criteria, sortBy, limit, offset, list, total);
	}
}
