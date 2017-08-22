package pl.nask.crs.commons;

import pl.nask.crs.security.authentication.AuthenticatedUser;

/**
 * Carries meta information about the operation being executed
 *  
 * @author Artur Gniadzik
 *
 */
public class OpInfo {
	private String userName; // not empty
	private String superUserName; // if the operation is performed on behalf of the actor
	private String remark;
	
	public OpInfo(AuthenticatedUser user) {
		this.userName = user.getUsername();
		this.superUserName = user.getSuperUserName();				
	}

	public OpInfo(AuthenticatedUser user, String remark) {
		this(user);
		this.remark = remark;
	}

	public OpInfo(String userName) {
		this.userName = userName;
	}

	public OpInfo(String userName, String remark) {
		this(userName);
		this.remark = remark;
	}

	public OpInfo(String userName, String superUserName, String remark) {
		this(userName, remark);
		this.superUserName = superUserName;
	}

	public String getRemark() {
		return remark;
	}
	
	public String getActorName() {
		return superUserName == null ? userName : superUserName;
	}		
	
	public String getActorNameForRemark() {
		return superUserName == null ? userName : superUserName + " B/O " + userName;
	}
	
	public String getSuperUserName() {
		return superUserName;
	}
}
