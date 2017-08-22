package pl.nask.crs.nichandle.email;

import pl.nask.crs.commons.email.service.EmailParameters;
import pl.nask.crs.commons.email.service.ParameterName;
import pl.nask.crs.commons.email.service.ParameterNameEnum;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.nichandle.NicHandle;

import java.util.List;

/**
 * @author Marianna Mysiorska
 */
public class NicHandleEmailParameters implements EmailParameters {

    private NicHandle nicHandle;
    private String passwordResetToken;
    private String username;
    private String accountRelatedNH;

    public NicHandleEmailParameters(NicHandle nicHandle, String passwordResetToken, 
            String username, String accountRelatedNH) {
        Validator.assertNotNull(nicHandle, "nic handle");
        Validator.assertNotEmpty(nicHandle.getEmail(), "nic handle email");
        Validator.assertNotEmpty(passwordResetToken, "password reset token");
        this.nicHandle = nicHandle;
        this.passwordResetToken = passwordResetToken;
        this.username = username;
        this.accountRelatedNH = accountRelatedNH;
    }

    public NicHandleEmailParameters(NicHandle nicHandle, String username, String accountRelatedNH){
        Validator.assertNotNull(nicHandle, "nic handle");
        Validator.assertNotEmpty(nicHandle.getEmail(), "nic handle email");
        this.nicHandle = nicHandle;
        this.username = username;
        this.accountRelatedNH = accountRelatedNH;
    }

    public String getLoggedInNicHandle()
    {
        return username;
    }

    public String getAccountRelatedNicHandle()
    {
        return this.accountRelatedNH;
    }

    public String getDomainName()
    {
        return null; // Unrelated to a particular domain
    }

    public List<ParameterName> getParameterNames() {
        return ParameterNameEnum.asList();
    }

    public String getParameterValue(String name, boolean html) {
        ParameterNameEnum param = ParameterNameEnum.forName(name);
        switch (param){
            case BILL_C_NAME:
                return nicHandle.getName();
            case BILL_C_NIC:
                return nicHandle.getNicHandleId();
            case BILL_C_EMAIL:
                return nicHandle.getEmail();
            case NIC:
                return nicHandle.getNicHandleId();
            case NIC_NAME:
                return nicHandle.getName();
            case PASSWORD_TOKEN:
                return passwordResetToken;
            case NIC_EMAIL:
                return nicHandle.getEmail();
            default:
                return null;
        }
    }

}
