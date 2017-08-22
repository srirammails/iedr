package pl.nask.crs.user.dao.ibatis.objects;

import java.util.List;

import pl.nask.crs.commons.utils.Validator;

/**
 * @author Kasia Fulara
 */
public class InternalUser {

    private String username;
    private String password;
    private String salt;
    private String secret;
    private String name;
    private int level;
    private List<String> permissionNames;
    private boolean useTwoFactorAuthentication;

    public InternalUser() {

    }

    public InternalUser(String username, String password, String salt, String secret, String name, int level, boolean useTwoFactorAuthentication) {
        Validator.assertNotEmpty(username, "username");

        this.username = username;
        this.name = name;
        this.password = password;
        this.salt = salt;
        this.secret = secret;
        this.level = level;
        this.useTwoFactorAuthentication = useTwoFactorAuthentication;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public List<String> getPermissionNames() {
		return permissionNames;
	}

    public void setPermissionNames(List<String> permissionNames) {
		this.permissionNames = permissionNames;
	}

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }
    
    public boolean isUseTwoFactorAuthentication() {
		return useTwoFactorAuthentication;
	}
    
    public void setUseTwoFactorAuthentication(boolean useTwoFactorAuthentication) {
		this.useTwoFactorAuthentication = useTwoFactorAuthentication;
	}

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    @Override
    public boolean equals(Object obj) {
        if (username == null)
            return false;
        if (!(obj instanceof InternalUser))
            return false;

        return username.equals(((InternalUser) obj).getUsername());
    }
    
    @Override
    public int hashCode() {
        return username == null ? 0 : username.hashCode();        
    }
}