package pl.nask.crs.web.nichandles;

import org.apache.struts2.interceptor.ServletRequestAware;
import pl.nask.crs.app.nichandles.NicHandleAppService;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.nichandle.NicHandle;
import pl.nask.crs.web.security.AuthenticatedUserAwareAction;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Marianna Mysiorska, Artur Gniadzik
 */
public class NicHandleResetPasswordAction extends AuthenticatedUserAwareAction implements ServletRequestAware {

    public static final String INPUT = "input";

    private final NicHandleAppService nicHandleAppService;
    private NicHandle nicHandle;
    private String nicHandleId;
    private String newPassword1;
    private String newPassword2;

    private HttpServletRequest request;

    public NicHandleResetPasswordAction(NicHandleAppService nicHandleAppService) {
        Validator.assertNotNull(nicHandleAppService, "nicHandleAppService");
        this.nicHandleAppService = nicHandleAppService;
    }

    public String getNewPassword1() {
        return newPassword1;
    }

    public void setNewPassword1(String newPassword1) {
        this.newPassword1 = newPassword1;
    }

    public String getNewPassword2() {
        return newPassword2;
    }

    public void setNewPassword2(String newPassword2) {
        this.newPassword2 = newPassword2;
    }

    public String getNicHandleId() {
        return nicHandleId;
    }

    public void setNicHandleId(String nicHandleId) {
        this.nicHandleId = nicHandleId;
    }

    public NicHandle getNicHandle() {
        return nicHandle;
    }

    public void setNicHandle(NicHandle nicHandle) {
        this.nicHandle = nicHandle;
    }

    public String input() throws Exception {
        nicHandle = nicHandleAppService.get(getUser(), nicHandleId);
        return INPUT;
    }

    public String saveNewPassword() throws Exception {
        nicHandle = nicHandleAppService.saveNewPassword(getUser(), newPassword1, newPassword2, nicHandleId);
        return SUCCESS;
    }

    public String resetPassword() throws Exception {
        nicHandleAppService.resetPassword(getUser(), nicHandleId, request.getRemoteAddr());
        return SUCCESS;
    }

    @Override
    public void setServletRequest(HttpServletRequest request) {
        this.request = request;
    }
}
