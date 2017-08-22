package pl.nask.crs.ticket.search;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import pl.nask.crs.commons.search.SearchCriteria;
import pl.nask.crs.commons.utils.DateUtils;
import pl.nask.crs.ticket.CustomerStatusEnum;
import pl.nask.crs.ticket.FinancialStatusEnum;
import pl.nask.crs.ticket.Ticket;
import pl.nask.crs.ticket.operation.DomainOperation;
import pl.nask.crs.ticket.operation.DomainOperation.DomainOperationType;

/**
 * todo: fix me - wrong criteria, see the use case to design the criteria
 * properly (prefix search!)
 * 
 * @author Kasia Fulara
 */
public class TicketSearchCriteria implements SearchCriteria<Ticket> {

    private Date from;
    private Date to;    
    private Date changeDatefrom;
    private Date changeDateTo;
    private Integer adminStatus;
    private Integer techStatus;
    private CustomerStatusEnum customerStatus;
    private String nicHandle;
    private String domainName;
    private String domainHolder;
    private Long accountId; // reseller account id
    private String category;
    private String clazz;
    private List<DomainOperationType> type;
    private String billNicHandle;
    private FinancialStatusEnum financialStatus;
    private String creatorNh;

    public TicketSearchCriteria() {
    }

    public TicketSearchCriteria(Date from, Date to, Integer adminStatus,
            Integer techStatus, String nicHandle, String domainName,
            String domainHolder, Long accountId) {
        setFrom(from);
        setTo(to);
        this.adminStatus = adminStatus;
        this.techStatus = techStatus;
        this.nicHandle = nicHandle;
        this.domainName = domainName;
        this.domainHolder = domainHolder;
        this.accountId = accountId;
    }

    public Date getFrom() {
        return from;
    }

    public void setFrom(Date from) {
        this.from = DateUtils.startOfDay(from);
    }

    public Date getTo() {
        return to;
    }

    public void setTo(Date to) {
        this.to = DateUtils.endOfDay(to);
    }

    public Date getChangeDateFrom() {
        return changeDatefrom;
    }

    public void setChangeDateFrom(Date from) {
        this.changeDatefrom = DateUtils.startOfDay(from);
    }

    public Date getChangeDateTo() {
        return changeDateTo;
    }

    public void setChangeDateTo(Date to) {
        this.changeDateTo = DateUtils.endOfDay(to);
    }

    
    public Integer getAdminStatus() {
        return adminStatus;
    }

    public void setAdminStatus(Integer adminStatus) {
        this.adminStatus = adminStatus;
    }

    public Integer getTechStatus() {
        return techStatus;
    }

    public void setTechStatus(Integer techStatus) {
        this.techStatus = techStatus;
    }

    public CustomerStatusEnum getCustomerStatus() {
        return customerStatus;
    }

    public void setCustomerStatus(CustomerStatusEnum customerStatus) {
        this.customerStatus = customerStatus;
    }

    public String getNicHandle() {
        return nicHandle;
    }

    public void setNicHandle(String nicHandle) {
        this.nicHandle = nicHandle;
    }

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    public String getDomainHolder() {
        return domainHolder;
    }

    public void setDomainHolder(String domainHolder) {
        this.domainHolder = domainHolder;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    public void setTicketType(DomainOperationType... type) {
        if (type == null) {
            this.type = null;
        } else {
            this.type = Arrays.asList(type);
        }
    }

    //ws require simple getter
    public void setType(List<DomainOperationType> type) {
        this.type = type;
    }

    public List<DomainOperationType> getType() {
        return type;
    }

    public String getBillNicHandle() {
        return billNicHandle;
    }

    public void setBillNicHandle(String billNicHandle) {
        this.billNicHandle = billNicHandle;
    }

    public FinancialStatusEnum getFinancialStatus() {
        return financialStatus;
    }

    public void setFinancialStatus(FinancialStatusEnum financialStatus) {
        this.financialStatus = financialStatus;
    }
    
    public void setCreatorNh(String creatorNh) {
		this.creatorNh = creatorNh;
	}
    
    public String getCreatorNh() {
		return creatorNh;
	}

    // ws problem with collections with null values
    public void filterValues() {
        if (type != null) {
            type.remove(null);
        }
    }
}
