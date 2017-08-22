package pl.nask.crs.documents.dao.ibatis.objects;

import java.util.Date;

/**
 * @author: Marcin Tkaczyk
 */
public class InternalDocumentReport {
    private String hostmasterName;
    private Integer documentsCount;
    private Date reportForDate;

    public String getHostmasterName() {
        return hostmasterName;
    }

    public void setHostmasterName(String hostmasterName) {
        this.hostmasterName = hostmasterName;
    }

    public Integer getDocumentsCount() {
        return documentsCount;
    }

    public void setDocumentsCount(Integer documentsCount) {
        this.documentsCount = documentsCount;
    }

    public Date getReportForDate() {
        return reportForDate;
    }

    public void setReportForDate(Date reportForDate) {
        this.reportForDate = reportForDate;
    }

}
