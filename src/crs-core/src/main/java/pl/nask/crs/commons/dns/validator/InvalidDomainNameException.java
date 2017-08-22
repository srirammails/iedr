package pl.nask.crs.commons.dns.validator;

public class InvalidDomainNameException extends RuntimeException {

    public enum Reason {
        NULL_NAME, PATTERN_MISMATCH, NAME_TOO_LONG, LABEL_TOO_LONG, LABEL_EMPTY, LABEL_FIRT_CHAR_NOT_LETTER, LABEL_LAST_CHAR_NOT_LETTER_OR_DIGIT, LABEL_FIRST_CHAR_NOT_LETTER_OR_DIGIT, NOT_ENOUGHT_LABELS, NOT_VALID_IEDR_NAME, TOO_MANY_LABELS
    }

    private String name;
    private Reason reason;

    public InvalidDomainNameException(String name, Reason reason) {
        this.name = name;
        this.reason = reason;
    }

    public String getName() {
        return this.name;
    }

    public Reason getReason() {
        return this.reason;
    }

    public String getMessage() {
        return "name: [" + name + "] is invalid [" + reason + "]";
    }
}
