package pl.nask.crs.documents.dao.ibatis.converters;

import pl.nask.crs.commons.dao.AbstractConverter;
import pl.nask.crs.documents.Document;
import pl.nask.crs.documents.DocumentFile;
import pl.nask.crs.documents.dao.ibatis.objects.InternalDocument;
import pl.nask.crs.documents.DocumentPurpose;

/**
 * @author Marianna Mysiorska
 */
public class DocumentConverter extends AbstractConverter<InternalDocument, Document> {

    protected Document _to(InternalDocument src) {
        if (src == null) return null;
        DocumentFile documentFile = new DocumentFile(src.getDocFilename(), src.getDocType());
        return new Document(
                src.getId(),
                src.getDate(),
                documentFile,
                DocumentPurpose.fromValue(src.getDocPurpose()),
                src.getDocSource(),
                src.getAccountNumber(),
                src.getDomains(),
                src.getCreatorNicHandleId()
        );
    }

    protected InternalDocument _from(Document document) {
        if (document == null) return null;
        return new InternalDocument(
                document.getId(),
                document.getDate(),
                document.getDocumentFile().getFileType().getType(),
                document.getDocumentFile().getFileName(),
                document.getDocPurpose().getValue(),
                document.getDocSource(),
                document.getAccountNumber(),
                document.getDomains(),
                document.getCreatorNicHandleId()
        );
    }
}
