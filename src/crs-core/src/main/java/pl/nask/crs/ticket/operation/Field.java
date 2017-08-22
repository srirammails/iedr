package pl.nask.crs.ticket.operation;

/**
 * @author Patrycja Wegrzynowicz
*/
public enum Field {
    DOMAIN_NAME_FAIL(10),
    DOMAIN_HOLDER_FAIL(11),
    ACCOUNT_NAME_FAIL(12),
    ADMIN1_CONTACT1_FAIL(13),
    ADMIN_CONTACT2_FAIL(14),
    TECH_CONTACT_FAIL(15),
    BILLING_CONTACT_FAIL(16),
    DNS_FAIL(18),
    IP_FAIL(17),
    CATEGORY_FAIL(19),
    CLASS_FAIL(20);

    private int dataField;

    Field(int dataField) {
        this.dataField = dataField;
    }

    public int getDataFieldValue() {
        return dataField;
    }
}
