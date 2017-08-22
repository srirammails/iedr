package pl.nask.crs.api.vo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

import pl.nask.crs.api.validation.ValidationHelper;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.ticket.operation.Field;
import pl.nask.crs.ticket.operation.NameserverChange;
import pl.nask.crs.ticket.operation.SimpleDomainFieldChange;

@XmlType
@XmlAccessorType(XmlAccessType.FIELD)
public class NameserverChangeVO {

	private SimpleDomainFieldChangeVO name;

	private SimpleDomainFieldChangeVO ipAddress;
	
	public NameserverChangeVO() {
	}
	
	public NameserverChangeVO(NameserverChange chg) {
		this.ipAddress = ValidationHelper.isEmptySimpleDomainFieldChange(chg.getIpAddress()) ? null : new SimpleDomainFieldChangeVO(chg.getIpAddress());
		this.name = ValidationHelper.isEmptySimpleDomainFieldChange(chg.getName()) ? null : new SimpleDomainFieldChangeVO(chg.getName());
	}

	public SimpleDomainFieldChangeVO getName() {
		return name;
	}

	public SimpleDomainFieldChangeVO getIpAddress() {
		return ipAddress;
	}

	public void setName(SimpleDomainFieldChangeVO name) {
		if (name != null) {
			this.name = name;	
		} else {
			this.name = new SimpleDomainFieldChangeVO(null, null, null);
		}
	}
	
	public NameserverChange makeNameserverChange() {
		NameserverChange chng = new NameserverChange(				
				name != null ? name.makeStringFieldChange(Field.DNS_FAIL) : new SimpleDomainFieldChange<String>(null, null, null), 
				ipAddress != null ? ipAddress.makeStringFieldChange(Field.IP_FAIL) : new SimpleDomainFieldChange<String>(null, null, null)
				);
		return chng ;
	}	
}
