package pl.nask.crs.web.documents;

import org.displaytag.decorator.TableDecorator;
import pl.nask.crs.documents.Document;

/**
 * @author Piotr Tkaczyk
 */
public class DocumentsRowDecorator extends TableDecorator {


    public String addRowId() {
        Document d = (Document) getCurrentRowObject();
        String fileName = d.getDocumentFile().getFileName();
        String fileType = d.getDocumentFile().getFileTypeAsString();

        StringBuffer sb = new StringBuffer();
        sb.append("\" onclick=\"location.href='documents-view-new.action?");

        Long dId = d.getId();
        if (dId != null) {
            sb.append("document.id=").append(dId).append("&");
        }

        sb.append("document.fileName=").append(fileName);
        sb.append("&document.fileType=").append(fileType);
        sb.append("'\" unused=\"");
        return sb.toString();
    }
}
