package pl.nask.crs.iedrapi;

import javax.servlet.http.HttpSession;

import pl.nask.crs.api.vo.AuthenticatedUserVO;
import pl.nask.crs.security.authentication.AuthenticatedUser;

/**
 * Holds user authentication data
 * 
 * @author Artur Gniadzik
 *
 */
public class AuthData {
	private AuthenticatedUser user;
	private HttpSession session;
	private String remoteAddr;
	
	private AuthData() {
		
	}
	
	public String getUsername() {
		return user == null ? null : user.getUsername();
	}
	
	public AuthenticatedUserVO getUser() {
		return new AuthenticatedUserVO(user);
	}

	/**
	 * returns a new instance of the AuthData
	 * @param session
	 * @param remoteAddr
	 * @return
	 */
	public static AuthData getInstance(HttpSession session, String remoteAddr) {
		AuthData authData = new AuthData();
		authData.session = session;
		authData.remoteAddr = remoteAddr;
		authData.user = (AuthenticatedUser) session.getAttribute("user");
		
		return authData;
	}
	
	public boolean isUserLoggedIn() {
		return user != null;
	}
	
	public void logout() {
		user = null;
		session.invalidate();
	}
	
	public void login(AuthenticatedUser user) {
		session.setAttribute("user", user);
		
		this.user = user;
	}
	
	public String getRemoteAddr() {
		return remoteAddr;
	}
}
