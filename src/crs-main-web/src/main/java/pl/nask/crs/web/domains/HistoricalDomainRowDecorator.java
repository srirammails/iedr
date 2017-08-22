package pl.nask.crs.web.domains;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.displaytag.decorator.TableDecorator;

/**
 * @author Piotr Tkaczyk
 */
public class HistoricalDomainRowDecorator extends TableDecorator {


    public String addRowId() {
//        HistoricalObject<Domain> hDomain = (HistoricalObject<Domain>) getCurrentRowObject();
//        String changeDate = getFormatedDate(hDomain.getChangeDate());
//
//        Integer page = (Integer) getPageContext().getAttribute("page");
//
//        StringBuffer sb = new StringBuffer();
//        sb.append("\" onclick=\"location.href='historical-domain-view.action?");
//        sb.append("changeDate=").append(changeDate);
//        sb.append("&criteria.domainName=").append(hDomain.getObject().getName());
//
//        if (page != null) {
//            sb.append("&page=").append(page);
//        }
//
//
//        sb.append("'\" unused=\"");
//
//        return sb.toString();
        return super.addRowId();
    }


    public String getFormatedDate(Date date) {
        if (date != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyyHHmmss");
            return sdf.format(date);
        }
        return null;
    }

}
