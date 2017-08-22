package pl.nask.crs.web.nichandles;

import pl.nask.crs.app.users.UserAppService;
import pl.nask.crs.user.User;
import pl.nask.crs.web.security.AuthenticatedUserAwareAction;

public class NicHandleTfaAction extends AuthenticatedUserAwareAction {
    private String nicHandleId;
	private UserAppService userAppService;
	private boolean tfaUsed;
    
    public NicHandleTfaAction(UserAppService userAppService) {
		this.userAppService = userAppService;
	}
    
    public void setNicHandleId(String nicHandleId) {
		this.nicHandleId = nicHandleId;
	}
    
    public String getNicHandleId() {
		return nicHandleId;
	}
    
    @Override
    public String input() throws Exception {
    	User user = userAppService.getUser(nicHandleId);
    	if (user == null) {
    		addActionError("User doesn't have a password set");
    		return ERROR;
    	} else {
    		this.tfaUsed = user.isUseTwoFactorAuthentication();
    		return INPUT;
    	}
    }
    
    public String enable() {
    	userAppService.changeTfa(getUser(), nicHandleId, true);
    	return SUCCESS;
    }
    
    public String disable() {
    	userAppService.changeTfa(getUser(), nicHandleId, false);
    	return SUCCESS;
    }
    
    public boolean isTfaUsed() {
		return tfaUsed;
	}
}
