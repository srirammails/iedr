package pl.nask.crs.documents.dao.ibatis;

import pl.nask.crs.commons.dao.Converter;
import pl.nask.crs.commons.dao.ConvertingGenericDAO;
import pl.nask.crs.commons.dao.GenericDAO;
import pl.nask.crs.documents.Document;
import pl.nask.crs.documents.dao.ibatis.objects.InternalDocument;

/**
 * @author Marianna Mysiorska
 */
public class ConvertingDocumentDAO extends ConvertingGenericDAO<InternalDocument, Document, Long> implements DocumentDAO {

    public ConvertingDocumentDAO(GenericDAO<InternalDocument, Long> internalDAO, Converter<InternalDocument, Document> internalConverter) {
        super(internalDAO, internalConverter);
    }

}
