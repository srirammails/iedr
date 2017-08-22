package pl.nask.crs.documents.dao.ibatis;

import pl.nask.crs.commons.dao.ibatis.GenericIBatisDAO;
import pl.nask.crs.documents.DocumentFile;
import pl.nask.crs.documents.dao.ibatis.objects.InternalDocument;

/**
 * @author Marianna Mysiorska
 */
public class InternalDocumentIBatisDAO extends GenericIBatisDAO<InternalDocument, Long> {

    public InternalDocumentIBatisDAO() {
        setGetQueryId("document.getFaxById");
        setFindQueryId("document.findFax");
        setCountFindQueryId("document.countTotalSearchResult");
        setDeleteQueryId("document.deleteDocById");
        setSortMapping(
                new String[]{"docSource", "docPurpose", "date", "id", "docType", "domain_name"},
                new String[]{"IND.DOC_SOURCE", "IND.DOC_PURPOSE", "IND.CR_DATE", "IND.DOC_ID", "IND.DOC_TYPE", "domain_name"});
    }

    public void create(InternalDocument document) {
        String newFilename = DocumentFile.getNormalizedFilenameWithAddDate(document.getDocFilename(), document.getDate());
        document.setDocFilename(newFilename);
        performInsert("document.insertFax", document);
        if (!document.getDomains().isEmpty()) {
            performInsert("document.insertFaxDomain", document);
        }
    }

    public void update(InternalDocument document) {
        Long id = document.getId();
        performDelete("document.deleteDocDomainById", id);
        if (!document.getDomains().isEmpty()) {
            performInsert("document.insertFaxDomain", document);
        }
    }
}