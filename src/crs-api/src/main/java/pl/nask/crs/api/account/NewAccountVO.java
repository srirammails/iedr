package pl.nask.crs.api.account;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

import pl.nask.crs.nichandle.NewAccount;

@XmlType
@XmlAccessorType(XmlAccessType.PROPERTY)
public class NewAccountVO {
	private String nicHandleId;
	private String secret;
	
	NewAccountVO() {
	}
	
	public NewAccountVO(NewAccount newAcc) {
		this(newAcc.getNicHandleId(), newAcc.getSecret());
	}

	public NewAccountVO(String nicHandleId, String secret) {
		this.nicHandleId = nicHandleId;
		this.secret = secret;
	}

	@XmlAttribute(name="nicHandleId")
	public String getNicHandle() {
		return nicHandleId;
	}
	
	@XmlAttribute(name="secret")
	public String getSecret() {
		return secret;
	}
}
