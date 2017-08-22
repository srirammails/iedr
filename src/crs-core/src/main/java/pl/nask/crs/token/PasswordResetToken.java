package pl.nask.crs.token;

import java.util.Date;

public class PasswordResetToken {
	private String nicHandle;
	private Date expires;
	private String token;
	private String remoteIp;

	public boolean isValid(String nicHandleId) {
		return
			expires.after(new Date()) 
			&& nicHandleId != null
			&& nicHandleId.equalsIgnoreCase(nicHandle);
	}

	public String getNicHandle() {
		return nicHandle;
	}

	public void setNicHandle(String nicHandle) {
		this.nicHandle = nicHandle;
	}

	public Date getExpires() {
		return expires;
	}

	public void setExpires(Date expires) {
		this.expires = expires;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getRemoteIp() {
		return remoteIp;
	}

	public void setRemoteIp(String remoteIp) {
		this.remoteIp = remoteIp;
	}

	

}
