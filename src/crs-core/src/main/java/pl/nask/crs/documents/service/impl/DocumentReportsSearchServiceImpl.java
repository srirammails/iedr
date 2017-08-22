package pl.nask.crs.documents.service.impl;

import pl.nask.crs.documents.service.DocumentReportsSearchService;
import pl.nask.crs.documents.dao.DocumentReportsDAO;
import pl.nask.crs.reports.TicketReport;
import pl.nask.crs.reports.search.ReportsSearchCriteria;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.commons.utils.Validator;

import java.util.List;

/**
 * @author: Marcin Tkaczyk
 */
public class DocumentReportsSearchServiceImpl implements DocumentReportsSearchService {

    private DocumentReportsDAO documentReportsDAO;

    public DocumentReportsSearchServiceImpl(DocumentReportsDAO documentReportsDAO) {
        Validator.assertNotNull(documentReportsDAO, "document reports dao");
        this.documentReportsDAO = documentReportsDAO;
    }

    public LimitedSearchResult<TicketReport> find(ReportsSearchCriteria criteria, long offset, long limit, List<SortCriterion> orderBy) {
        return documentReportsDAO.findDocumentReports(criteria, offset, limit, orderBy);
    }
}
