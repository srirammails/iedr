package pl.nask.crs.ticket.dao.ibatis.objects;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import pl.nask.crs.ticket.operation.DomainOperation;

/**
 * @author Patrycja Wegrzynowicz
 */
public class InternalTicket implements Serializable {

    static public enum DataSet {
        /**
         * InternalTicket is missing DNS data
         */
        SIMPLE("simple"),
        /**
         * InternalTicket contains all data, including DNS
         */
        FULL("full");

        private String name;

        private DataSet(String name) {
            this.name = name;
        }

        static public DataSet fromName(String name) {
            if (name.equals(SIMPLE.name))
                return SIMPLE;
            else if (name.equals(FULL.name))
                return FULL;
            else
                throw new IllegalArgumentException("Unknown type of InternalTicket: " + name);
        }
    }

    /**
     * Describes type of result from DB.
     * <ul>
     *     <li><code>simple</code> is missing DNS entries</li>
     *     <li><code>full</code> includes DNS servers</li>
     * </ul>
     * It's illegal to try to read DNS servers from a <code>simple</code> result type. First set of nameservers automatically sets
     * type of dataSetType to extended.
     */
    private DataSet dataSetType = DataSet.SIMPLE;

    private long id;

    private DomainOperation.DomainOperationType type;

    private String domainName;

    private Integer domainNameFailureReason;
    private String domainHolder;

    private Integer domainHolderFailureReason;
    private String domainHolderClass;

    private Integer domainHolderClassFailureReason;
    private String domainHolderCategory;

    private Integer domainHolderCategoryFailureReason;
    private long resellerAccountId;

    private String resellerAccountName;
    private Integer resellerAccountFailureReason;
    private boolean resellerAccountAgreementSigned;
    private boolean resellerAccountTicketEdit;
    private String adminContact1NicHandle;

    private String adminContact1Name;
    private String adminContact1Email;
    private String adminContact1CompanyName;
    private String adminContact1Country;
    private Integer adminContact1FailureReason;
    private String adminContact2NicHandle;

    private String adminContact2Name;
    private String adminContact2Email;
    private String adminContact2CompanyName;
    private String adminContact2Country;
    private Integer adminContact2FailureReason;
    private String techContactNicHandle;

    private String techContactName;
    private String techContactEmail;
    private String techContactCompanyName;
    private String techContactCountry;
    private Integer techContactFailureReason;
    private String billingContactNicHandle;

    private String billingContactName;
    private String billingContactEmail;
    private String billingContactCompanyName;
    private String billingContactCountry;
    private Integer billingContactFailureReason;
    private List<InternalTicketNameserver> nameservers;

    private String requestersRemark;

    private String hostmastersRemark;

    private int adminStatus;

    private Date adminStatusChangeDate;

    private int techStatus;

    private Date techStatusChangeDate;

    private int financialStatus;

    private Date financialStatusChangeDate;
    private int customerStatus;

    private Date customerStatusChangeDate;
    private String checkedOut;

    private String checkedOutToNicHandle;

    private String checkedOutToName;

    private String creatorNicHandle;

    private String creatorName;

    private String creatorEmail;

    private String creatorCompanyName;

    private String creatorCountry;

    private Date creationDate;

    private Date changeDate;

    private Date renewalDate;

    private boolean clikPaid;

    private long documentsCount;

    private Integer domainPeriod;

    private String charityCode;

    public DataSet getDataSetType() {
        return dataSetType;
    }

    public void setDataSetType(String dataSetType) {
        this.dataSetType = DataSet.fromName(dataSetType);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public DomainOperation.DomainOperationType getType() {
        return type;
    }

    public void setType(DomainOperation.DomainOperationType type) {
        this.type = type;
    }

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    public Integer getDomainNameFailureReason() {
        return domainNameFailureReason;
    }

    public void setDomainNameFailureReason(Integer domainNameFailureReason) {
        this.domainNameFailureReason = domainNameFailureReason;
    }

    public String getDomainHolder() {
        return domainHolder;
    }

    public void setDomainHolder(String domainHolder) {
        this.domainHolder = domainHolder;
    }

    public Integer getDomainHolderFailureReason() {
        return domainHolderFailureReason;
    }

    public void setDomainHolderFailureReason(Integer domainHolderFailureReason) {
        this.domainHolderFailureReason = domainHolderFailureReason;
    }

    public String getDomainHolderClass() {
        return domainHolderClass;
    }

    public void setDomainHolderClass(String domainHolderClass) {
        this.domainHolderClass = domainHolderClass;
    }

    public Integer getDomainHolderClassFailureReason() {
        return domainHolderClassFailureReason;
    }

    public void setDomainHolderClassFailureReason(Integer domainHolderClassFailureReason) {
        this.domainHolderClassFailureReason = domainHolderClassFailureReason;
    }

    public String getDomainHolderCategory() {
        return domainHolderCategory;
    }

    public void setDomainHolderCategory(String domainHolderCategory) {
        this.domainHolderCategory = domainHolderCategory;
    }

    public Integer getDomainHolderCategoryFailureReason() {
        return domainHolderCategoryFailureReason;
    }

    public void setDomainHolderCategoryFailureReason(Integer domainHolderCategoryFailureReason) {
        this.domainHolderCategoryFailureReason = domainHolderCategoryFailureReason;
    }

    public long getResellerAccountId() {
        return resellerAccountId;
    }

    public void setResellerAccountId(long resellerAccountId) {
        this.resellerAccountId = resellerAccountId;
    }

    public String getResellerAccountName() {
        return resellerAccountName;
    }

    public void setResellerAccountName(String resellerAccountName) {
        this.resellerAccountName = resellerAccountName;
    }

    public Integer getResellerAccountFailureReason() {
        return resellerAccountFailureReason;
    }

    public void setResellerAccountFailureReason(Integer resellerAccountFailureReason) {
        this.resellerAccountFailureReason = resellerAccountFailureReason;
    }

    public String getAdminContact1NicHandle() {
        return adminContact1NicHandle;
    }

    public void setAdminContact1NicHandle(String adminContact1NicHandle) {
        this.adminContact1NicHandle = adminContact1NicHandle;
    }

    public String getAdminContact1Name() {
        return adminContact1Name;
    }

    public void setAdminContact1Name(String adminContact1Name) {
        this.adminContact1Name = adminContact1Name;
    }

    public Integer getAdminContact1FailureReason() {
        return adminContact1FailureReason;
    }

    public void setAdminContact1FailureReason(Integer adminContact1FailureReason) {
        this.adminContact1FailureReason = adminContact1FailureReason;
    }

    public String getAdminContact2NicHandle() {
        return adminContact2NicHandle;
    }

    public void setAdminContact2NicHandle(String adminContact2NicHandle) {
        this.adminContact2NicHandle = adminContact2NicHandle;
    }

    public String getAdminContact2Name() {
        return adminContact2Name;
    }

    public void setAdminContact2Name(String adminContact2Name) {
        this.adminContact2Name = adminContact2Name;
    }

    public Integer getAdminContact2FailureReason() {
        return adminContact2FailureReason;
    }

    public void setAdminContact2FailureReason(Integer adminContact2FailureReason) {
        this.adminContact2FailureReason = adminContact2FailureReason;
    }

    public String getTechContactNicHandle() {
        return techContactNicHandle;
    }

    public void setTechContactNicHandle(String techContactNicHandle) {
        this.techContactNicHandle = techContactNicHandle;
    }

    public String getTechContactName() {
        return techContactName;
    }

    public void setTechContactName(String techContactName) {
        this.techContactName = techContactName;
    }

    public Integer getTechContactFailureReason() {
        return techContactFailureReason;
    }

    public void setTechContactFailureReason(Integer techContactFailureReason) {
        this.techContactFailureReason = techContactFailureReason;
    }

    public String getBillingContactNicHandle() {
        return billingContactNicHandle;
    }

    public void setBillingContactNicHandle(String billingContactNicHandle) {
        this.billingContactNicHandle = billingContactNicHandle;
    }

    public String getBillingContactName() {
        return billingContactName;
    }

    public void setBillingContactName(String billingContactName) {
        this.billingContactName = billingContactName;
    }

    public Integer getBillingContactFailureReason() {
        return billingContactFailureReason;
    }

    public void setBillingContactFailureReason(Integer billingContactFailureReason) {
        this.billingContactFailureReason = billingContactFailureReason;
    }

    public String getRequestersRemark() {
        return requestersRemark;
    }

    public void setRequestersRemark(String requestersRemark) {
        this.requestersRemark = requestersRemark;
    }

    public String getHostmastersRemark() {
        return hostmastersRemark;
    }

    public void setHostmastersRemark(String hostmastersRemark) {
        this.hostmastersRemark = hostmastersRemark;
    }

    public int getAdminStatus() {
        return adminStatus;
    }

    public void setAdminStatus(int adminStatus) {
        this.adminStatus = adminStatus;
    }

    public Date getAdminStatusChangeDate() {
        return adminStatusChangeDate;
    }

    public void setAdminStatusChangeDate(Date adminStatusChangeDate) {
        this.adminStatusChangeDate = adminStatusChangeDate;
    }

    public int getTechStatus() {
        return techStatus;
    }

    public void setTechStatus(int techStatus) {
        this.techStatus = techStatus;
    }

    public Date getTechStatusChangeDate() {
        return techStatusChangeDate;
    }

    public void setTechStatusChangeDate(Date techStatusChangeDate) {
        this.techStatusChangeDate = techStatusChangeDate;
    }

    public String getCheckedOut() {
        return checkedOut;
    }

    public void setCheckedOut(String checkedOut) {
        this.checkedOut = checkedOut;
    }

    public String getCheckedOutToNicHandle() {
        return checkedOutToNicHandle;
    }

    public void setCheckedOutToNicHandle(String checkedOutToNicHandle) {
        this.checkedOutToNicHandle = checkedOutToNicHandle;
    }

    public String getCheckedOutToName() {
        return checkedOutToName;
    }

    public void setCheckedOutToName(String checkedOutToName) {
        this.checkedOutToName = checkedOutToName;
    }

    public String getCreatorNicHandle() {
        return creatorNicHandle;
    }

    public void setCreatorNicHandle(String creatorNicHandle) {
        this.creatorNicHandle = creatorNicHandle;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getChangeDate() {
        return changeDate;
    }

    public void setChangeDate(Date changeDate) {
        this.changeDate = changeDate;
    }

    public boolean isClikPaid() {
        return clikPaid;
    }

    public void setClikPaid(boolean clikPaid) {
        this.clikPaid = clikPaid;
    }

    public String getAdminContact1Email() {
        return adminContact1Email;
    }

    public void setAdminContact1Email(String adminContact1Email) {
        this.adminContact1Email = adminContact1Email;
    }

    public String getAdminContact2Email() {
        return adminContact2Email;
    }

    public void setAdminContact2Email(String adminContact2Email) {
        this.adminContact2Email = adminContact2Email;
    }

    public String getTechContactEmail() {
        return techContactEmail;
    }

    public void setTechContactEmail(String techContactEmail) {
        this.techContactEmail = techContactEmail;
    }

    public String getBillingContactEmail() {
        return billingContactEmail;
    }

    public void setBillingContactEmail(String billingContactEmail) {
        this.billingContactEmail = billingContactEmail;
    }

    public String getAdminContact1CompanyName() {
        return adminContact1CompanyName;
    }

    public void setAdminContact1CompanyName(String adminContact1CompanyName) {
        this.adminContact1CompanyName = adminContact1CompanyName;
    }

    public String getAdminContact2CompanyName() {
        return adminContact2CompanyName;
    }

    public void setAdminContact2CompanyName(String adminContact2CompanyName) {
        this.adminContact2CompanyName = adminContact2CompanyName;
    }

    public String getTechContactCompanyName() {
        return techContactCompanyName;
    }

    public void setTechContactCompanyName(String techContactCompanyName) {
        this.techContactCompanyName = techContactCompanyName;
    }

    public String getBillingContactCompanyName() {
        return billingContactCompanyName;
    }

    public void setBillingContactCompanyName(String billingContactCompanyName) {
        this.billingContactCompanyName = billingContactCompanyName;
    }

    public String getAdminContact1Country() {
        return adminContact1Country;
    }

    public void setAdminContact1Country(String adminContact1Country) {
        this.adminContact1Country = adminContact1Country;
    }

    public String getAdminContact2Country() {
        return adminContact2Country;
    }

    public void setAdminContact2Country(String adminContact2Country) {
        this.adminContact2Country = adminContact2Country;
    }

    public String getTechContactCountry() {
        return techContactCountry;
    }

    public void setTechContactCountry(String techContactCountry) {
        this.techContactCountry = techContactCountry;
    }

    public String getBillingContactCountry() {
        return billingContactCountry;
    }

    public void setBillingContactCountry(String billingContactCountry) {
        this.billingContactCountry = billingContactCountry;
    }

    public String getCreatorCountry() {
        return creatorCountry;
    }

    public void setCreatorCountry(String creatorCountry) {
        this.creatorCountry = creatorCountry;
    }

    public String getCreatorEmail() {
        return creatorEmail;
    }

    public void setCreatorEmail(String creatorEmail) {
        this.creatorEmail = creatorEmail;
    }

    public String getCreatorCompanyName() {
        return creatorCompanyName;
    }

    public void setCreatorCompanyName(String creatorCompanyName) {
        this.creatorCompanyName = creatorCompanyName;
    }

    public void setRenewalDate(Date renewalDate) {
        this.renewalDate = renewalDate;
    }

    public Date getRenewalDate() {
        return renewalDate;
    }

    public void setDocumentsCount(long documentsCount) {
        this.documentsCount = documentsCount;
    }

    public long getDocumentsCount() {
        return documentsCount;
    }

    public List<InternalTicketNameserver> getNameservers() {
        if (dataSetType == DataSet.SIMPLE)
            throw new IllegalStateException("Simple internal ticket does not hold any information about nameservers");
        return nameservers;
    }

    public void setNameservers(List<InternalTicketNameserver> nameservers) {
        this.nameservers = nameservers;
        this.dataSetType = DataSet.FULL;
    }

    /**
     * returns ticket's nameserver entry
     * @param i
     * @return
     */
    public InternalTicketNameserver getInternalNameserver(int i) {
        return nameservers.get(i);
    }

	public boolean isResellerAccountAgreementSigned() {
		return resellerAccountAgreementSigned;
	}

	public void setResellerAccountAgreementSigned(
			boolean resellerAccountAgreementSigned) {
		this.resellerAccountAgreementSigned = resellerAccountAgreementSigned;
	}

	public boolean isResellerAccountTicketEdit() {
		return resellerAccountTicketEdit;
	}

	public void setResellerAccountTicketEdit(boolean resellerAccountTicketEdit) {
		this.resellerAccountTicketEdit = resellerAccountTicketEdit;
	}

    public Integer getDomainPeriod() {
        return domainPeriod;
    }

    public void setDomainPeriod(Integer domainPeriod) {
        this.domainPeriod = domainPeriod;
    }

    public String getCharityCode() {
        return charityCode;
    }

    public void setCharityCode(String charityCode) {
        this.charityCode = charityCode;
    }

	public int getFinancialStatus() {
		return financialStatus;
	}

	public void setFinancialStatus(int financialStatus) {
		this.financialStatus = financialStatus;
	}

	public Date getFinancialStatusChangeDate() {
		return financialStatusChangeDate;
	}

	public void setFinancialStatusChangeDate(Date financialStatusChangeDate) {
		this.financialStatusChangeDate = financialStatusChangeDate;
	}

    public int getCustomerStatus() {
        return customerStatus;
    }

    public void setCustomerStatus(int customerStatus) {
        this.customerStatus = customerStatus;
    }

    public Date getCustomerStatusChangeDate() {
        return customerStatusChangeDate;
    }

    public void setCustomerStatusChangeDate(Date customerStatusChangeDate) {
        this.customerStatusChangeDate = customerStatusChangeDate;
    }
}
