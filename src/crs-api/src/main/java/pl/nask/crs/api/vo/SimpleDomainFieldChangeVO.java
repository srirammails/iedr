package pl.nask.crs.api.vo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

import pl.nask.crs.accounts.Account;
import pl.nask.crs.contacts.Contact;
import pl.nask.crs.ticket.dao.ibatis.objects.InternalStatus;
import pl.nask.crs.ticket.operation.FailureReason;
import pl.nask.crs.ticket.operation.Field;
import pl.nask.crs.ticket.operation.SimpleDomainFieldChange;

@XmlType
@XmlAccessorType(XmlAccessType.FIELD)
public class SimpleDomainFieldChangeVO extends SimpleDomainFieldChangeEditVO {

	private String oldValue;
	private String failureReason;

	public SimpleDomainFieldChangeVO() {
	}

	public static SimpleDomainFieldChangeVO newAccountChangeVO(SimpleDomainFieldChange<Account> dc) {
		return new SimpleDomainFieldChangeVO(str(dc.getCurrentValue()), str(dc.getNewValue()), dc.getFailureReason());
	}
	
	private static String str(Account a) {
		if (a == null)
			return null;
		else
			return "" + a.getId();
	}
	
	private static String str(Contact c) {
		if (c == null)
			return null;
		else 
			return c.getNicHandle();
	}

	public SimpleDomainFieldChangeVO(SimpleDomainFieldChange<String> dc) {
		super(dc);
		this.oldValue = dc.getCurrentValue() == null ? null : dc.getCurrentValue();
		this.failureReason = dc.getFailureReason() == null ? null : dc.getFailureReason().getDescription();
	}
	
	SimpleDomainFieldChangeVO(String currentValue, String newValue, FailureReason fr) {
		super(newValue, fr == null ? 0 : fr.getId());
		this.oldValue = currentValue;
		this.failureReason = fr == null ? null : fr.getDescription();
	}
	

	public FailureReason makeFailureReason(Field field) {
		InternalStatus res = new InternalStatus();
		res.setDataField(field.getDataFieldValue());
		res.setId(getFailureReasonId());
		res.setDescription(failureReason);
		return res;
	}

	public SimpleDomainFieldChange<String> makeStringFieldChange(Field field) {		
		SimpleDomainFieldChange<String> chng = new SimpleDomainFieldChange<String>(oldValue, getNewValue(), makeFailureReason(field));
		
		return chng;
	}
	
	public static SimpleDomainFieldChangeVO newContactChangeVO(SimpleDomainFieldChange<Contact> c) {
		
		return new SimpleDomainFieldChangeVO(str(c.getCurrentValue()), str(c.getNewValue()), c.getFailureReason());
	}


}
