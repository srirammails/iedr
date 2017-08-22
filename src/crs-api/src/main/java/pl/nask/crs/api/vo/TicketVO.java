package pl.nask.crs.api.vo;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import pl.nask.crs.ticket.AdminStatusEnum;
import pl.nask.crs.app.tickets.TicketModel;
import pl.nask.crs.payment.PaymentMethod;
import pl.nask.crs.ticket.FinancialStatusEnum;
import pl.nask.crs.ticket.TechStatusEnum;
import pl.nask.crs.ticket.Ticket;

@XmlType
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class TicketVO {

	private long id;

    private String type;

    private FailureReasonsVO operation;

    private AdminStatusEnum adminStatus;

    private Date adminStatusChangeDate;
    
    private TechStatusEnum techStatus;

    private Date techStatusChangeDate;

    private FinancialStatusEnum financialStatus;

    private Date financialStatusChangeDate;

    private String requestersRemark;

    private String hostmastersRemark;

    private String creator;

    private Date creationDate;

    private Date changeDate;

    private String checkedOutTo;

    private boolean clikPaid;

    private boolean hasDocuments;

    private String domainName;

    private String ticketHolder;

    private String previousHolder;
    
    private Date domainRenevalDate;
    
    private String charityCode;
    
    private int period;
    
    private PaymentMethod paymentType;

    public TicketVO() {
	}
    
	public TicketVO(TicketModel view) {
		this(view.getTicket());
		
		ticketHolder = view.getAdditionalInfo().getTicketHolder();
		previousHolder = view.getAdditionalInfo().getPreviousHolder();
		paymentType = view.getAdditionalInfo().getPaymentMethod();
	}

	public TicketVO(Ticket t) {
		id = t.getId();
		domainName = t.getOperation().getDomainNameField().getNewValue();
        type = t.getOperation().getType().getFullName();
        operation = new FailureReasonsVO(t.getOperation());
		adminStatus = AdminStatusEnum.valueOf(t.getAdminStatus());
		adminStatusChangeDate = t.getAdminStatusChangeDate();
		techStatus = TechStatusEnum.valueOf(t.getTechStatus());
		techStatusChangeDate = t.getTechStatusChangeDate();
        financialStatus = FinancialStatusEnum.valueForId(t.getFinancialStatus().getId());
        financialStatusChangeDate = t.getFinancialStatusChangeDate();
		requestersRemark = t.getRequestersRemark() == null ? null : t.getRequestersRemark();
		hostmastersRemark = t.getHostmastersRemark() == null ? null : t.getHostmastersRemark();
		creator = t.getCreator().getNicHandle();
		creationDate = t.getCreationDate();
		changeDate = t.getChangeDate();
		checkedOutTo = t.getCheckedOutTo() == null ? null : t.getCheckedOutTo().getNicHandle();
		clikPaid = t.isClikPaid();
		hasDocuments = t.isHasDocuments();
		domainRenevalDate = t.getOperation().getRenewalDate();		
		charityCode = t.getCharityCode();
		period = t.getDomainPeriod() != null ? t.getDomainPeriod().getYears() : 0;
	}

	public long getId() {
		return id;
	}

	public String getType() {
		return type;
	}

	public FailureReasonsVO getOperation() {
		return operation;
	}

	public AdminStatusEnum getAdminStatus() {
		return adminStatus;
	}

	public Date getAdminStatusChangeDate() {
		return adminStatusChangeDate;
	}

	public TechStatusEnum getTechStatus() {
		return techStatus;
	}

	public Date getTechStatusChangeDate() {
		return techStatusChangeDate;
	}

	public String getRequestersRemark() {
		return requestersRemark;
	}

	public String getHostmastersRemark() {
		return hostmastersRemark;
	}

	public String getCreator() {
		return creator;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public Date getChangeDate() {
		return changeDate;
	}

	public String getCheckedOutTo() {
		return checkedOutTo;
	}

	public boolean isClikPaid() {
		return clikPaid;
	}

	public boolean isHasDocuments() {
		return hasDocuments;
	}

	public String getDomainName() {
		return domainName;
	}

	public String getTicketHolder() {
		return ticketHolder;
	}

	public String getPreviousHolder() {
		return previousHolder;
	}

	public Date getDomainRenevalDate() {
		return domainRenevalDate;
	}

	public String getCharityCode() {
		return charityCode;
	}

	public int getPeriod() {
		return period;
	}

	public PaymentMethod getPaymentType() {
		return paymentType;
	}

	
}
