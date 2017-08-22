package pl.nask.crs.nichandle;

public class NewAccount {
	private String nicHandleId;
	private String secret;
	
	public NewAccount(String nicHandleId, String secret) {
		super();
		this.nicHandleId = nicHandleId;
		this.secret = secret;
	}

	public String getNicHandleId() {
		return nicHandleId;
	}
	
	public String getSecret() {
		return secret;
	}

}
