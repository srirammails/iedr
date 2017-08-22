package pl.nask.crs.api.vo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import pl.nask.crs.ticket.dao.ibatis.objects.InternalStatus;
import pl.nask.crs.ticket.operation.FailureReason;
import pl.nask.crs.ticket.operation.Field;
import pl.nask.crs.ticket.operation.SimpleDomainFieldChange;

@XmlType
@XmlAccessorType(XmlAccessType.FIELD)
public class SimpleDomainFieldChangeEditVO {
	
	@XmlElement(required=true)
	private String newValue;
	private Integer failureReasonId;

	public SimpleDomainFieldChangeEditVO() {
	}
	
	SimpleDomainFieldChangeEditVO(SimpleDomainFieldChange<String> dc) {
		this(dc.getNewValue(), dc.getFailureReason() == null ? null : dc.getFailureReason().getId());
	}
	
	SimpleDomainFieldChangeEditVO(String newValue, Integer failureReason) {
		this.newValue = newValue;
		this.failureReasonId = failureReason;
	}
	
	public FailureReason makeFailureReason(Field field) {
		InternalStatus res = new InternalStatus();
		res.setDataField(field.getDataFieldValue());
		res.setId(getFailureReasonId());
		return res;
	}
	

	public String getNewValue() {
		return newValue;
	}

	public int getFailureReasonId() {
		return failureReasonId == null ? 0 : failureReasonId;
	}
	
}
