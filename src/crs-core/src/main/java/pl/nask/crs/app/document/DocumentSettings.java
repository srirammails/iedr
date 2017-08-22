package pl.nask.crs.app.document;

import java.util.Collections;
import java.util.List;

public class DocumentSettings {
    private final int documentSizeLimit;
    private final int documentCountLimit;
    private final List<String> allowedTypes;

    public DocumentSettings(int documentSizeLimit, int documentCountLimit, List<String> allowedTypes) {
        this.documentSizeLimit = documentSizeLimit;
        this.documentCountLimit = documentCountLimit;
        this.allowedTypes = Collections.unmodifiableList(allowedTypes);
    }

    public int getDocumentSizeLimit() {
        return documentSizeLimit;
    }

    public int getDocumentCountLimit() {
        return documentCountLimit;
    }

    public List<String> getAllowedTypes() {
        return allowedTypes;
    }

}
