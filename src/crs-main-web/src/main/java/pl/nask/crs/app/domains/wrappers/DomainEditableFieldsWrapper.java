package pl.nask.crs.app.domains.wrappers;

/**
 * Represents all fields that are editable in domain
 *
 * @author Kasia Fulara
 */
public class DomainEditableFieldsWrapper {

    private String domainHolder;
    private String holderClass;
    private String holderCategory;
    private String adminContact1;
    private String adminContact2;
    private String techContact;
    private String billContact;
    private String accountNo;
    private String remark;


    public String getDomainHolder() {
        return domainHolder;
    }

    public void setDomainHolder(String domainHolder) {
        this.domainHolder = domainHolder;
    }

    public String getHolderClass() {
        return holderClass;
    }

    public void setHolderClass(String holderClass) {
        this.holderClass = holderClass;
    }

    public String getHolderCategory() {
        return holderCategory;
    }

    public void setHolderCategory(String holderCategory) {
        this.holderCategory = holderCategory;
    }

    public String getAdminContact1() {
        return adminContact1;
    }

    public void setAdminContact1(String adminContact1) {
        this.adminContact1 = adminContact1;
    }

    public String getAdminContact2() {
        return adminContact2;
    }

    public void setAdminContact2(String adminContact2) {
        this.adminContact2 = adminContact2;
    }

    public String getTechContact() {
        return techContact;
    }

    public void setTechContact(String techContact) {
        this.techContact = techContact;
    }

    public String getBillContact() {
        return billContact;
    }

    public void setBillContact(String billContact) {
        this.billContact = billContact;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
