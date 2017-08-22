package pl.nask.crs.contacts.dao.ibatis.objects;

/**
 * @author Patrycja Wegrzynowicz
 */
public class InternalContact {

    public static final String ADMIN = "A";

    public static final String TECH = "T";

    public static final String BILLING = "B";

    public static final String CREATOR = "C";

    private String nicHandle;

    private String name;

    private String type;

    private String email;

    private String domainName;

    private String companyName;

    private String country;

    private String county;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    public String getNicHandle() {
        return nicHandle;
    }

    public void setNicHandle(String nicHandle) {
        this.nicHandle = nicHandle;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public boolean isAdmin() {
        return ADMIN.equalsIgnoreCase(type);
    }

    public boolean isTech() {
        return TECH.equalsIgnoreCase(type);
    }

    public boolean isBilling() {
        return BILLING.equalsIgnoreCase(type);
    }

    public boolean isEmpty() {
        return nicHandle == null;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }
}
