package pl.nask.crs.documents.search;

import pl.nask.crs.commons.search.SearchCriteria;
import pl.nask.crs.commons.utils.DateUtils;
import pl.nask.crs.documents.Document;

import java.util.Calendar;
import java.util.Date;

/**
 * @author Marianna Mysiorska
 */
public class DocumentSearchCriteria implements SearchCriteria<Document> {

    private String domainName;

    private String docSource;

    private Date from;

    private Date to;

    public DocumentSearchCriteria() {
    }

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        if (domainName == null || domainName.trim().length() == 0)
            this.domainName = null;
        else
            this.domainName = domainName;
    }

    public String getDocSource() {
        return docSource;
    }

    public void setDocSource(String docSource) {
        if (docSource == null || docSource.trim().length() == 0)
            this.docSource = null;
        else
            this.docSource = docSource;
    }

    public Date getFrom() {
        return from;
    }

    public void setFrom(Date from) {
        this.from = DateUtils.startOfDay(from);
    }

    public Date getTo() {
        return to;
    }

    public void setTo(Date to) {
        this.to = DateUtils.endOfDay(to);
    }
}
