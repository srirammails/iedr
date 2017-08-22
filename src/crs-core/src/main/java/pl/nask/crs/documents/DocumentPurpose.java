package pl.nask.crs.documents;

public enum DocumentPurpose {
    REGISTRATION("New Reg"),
    TRANSFER("Bill-C Transfer"),
    DELETION("Deletion"),
    GENERAL_MOD("General Mod"),
    MISCELLANEOUS("Misc"),
    DNS_MOD("Name Server Mod"),
    UNNECESSARY("Unnecessary");

    private String value;

    private DocumentPurpose(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static DocumentPurpose fromValue(String v) {
        for (DocumentPurpose c: DocumentPurpose.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }
}
