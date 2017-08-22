package pl.nask.crs.api.vo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlType
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class NameserverValidationVO {

    public static enum Status {
        OK(0),
        DNS_EXEC_FAILED(1),
        DNS_CONFIGURATION_ERROR(2);

        private final int id;

        private Status(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }
    }
    Status status;

    String message;

    public NameserverValidationVO() {};

    public NameserverValidationVO(Status status, String message) {
        this.status = status;
        this.message = message;
    }

    /**
     * Status of validation - 0 in case of success, != 0 in case of error
     * @return
     */
    public int getStatus() {
        return status.getId();
    }

    /**
     * In case of error descriptive message of error.
     * @return
     */
    public String getMessage() {
        return message;
    }
}
