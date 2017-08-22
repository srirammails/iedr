package pl.nask.crs.user.permissions;

/**
 * @author Kasia Fulara
 */
public class LoginPermission extends NamedPermission {
	public static final String CRS = "crs";
	public static final String WS = "ws";
	public static final String API = "api";
	private final String discriminator;
	
    public LoginPermission(String discriminator) {
        super(discriminator + "LoginPermission", LoginPermission.class.getCanonicalName() + "." + discriminator);
        this.discriminator = discriminator;
    }
    
    @Override
    public String getDescription() {
    	return "Grants permission to log into " + appName();
    }

	private String appName() {
		if (API.equals(discriminator)) return "IEDR-API";
		if (CRS.equals(discriminator)) return "CRS-WEB";
		if (WS.equals(discriminator)) return "CRS-WS";
		
		return discriminator;
	}
}
