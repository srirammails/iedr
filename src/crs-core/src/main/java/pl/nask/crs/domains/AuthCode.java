package pl.nask.crs.domains;

import java.util.Date;

public class AuthCode {

    private final String authcode;
    private final Date validUntil;

    public AuthCode(String authcode, Date validUntil) {
        this.authcode = authcode;
        this.validUntil = validUntil;
    }

    public String getAuthcode() {
        return authcode;
    }

    public Date getValidUntil() {
        return validUntil;
    }
}
