package pl.nask.crs.iedrapi.exceptions;

/**
 * @author: Marcin Tkaczyk
 */
public class Value {
    private String tag;
    private String namespace;
    private String value;

    public Value(String tag, String namespace, String value) {
        this.tag = tag;
        this.namespace = namespace;
        this.value = value;
    }

    public String getTag() {
        return tag;
    }

    public String getNamespace() {
        return namespace;
    }

    public String getValue() {
        return value;
    }
}
