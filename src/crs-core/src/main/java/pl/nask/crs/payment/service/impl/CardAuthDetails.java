package pl.nask.crs.payment.service.impl;

public class CardAuthDetails {

	private String authCode;
	private String passRef;

	public CardAuthDetails(String authCode, String passRef) {
		this.authCode = authCode;
		this.passRef = passRef;
	}
	
	public String getAuthCode() {
		return authCode;
	}
	
	public String getPassRef() {
		return passRef;
	}
}
