package pl.nask.crs.api.vo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.Date;

@XmlType
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class AuthCodeVO {

    private String authCode;
    private Date validUntil;

    public AuthCodeVO() {
    }

    public AuthCodeVO(String authCode, Date validUntil) {
        this.authCode = authCode;
        this.validUntil = validUntil;
    }
}
