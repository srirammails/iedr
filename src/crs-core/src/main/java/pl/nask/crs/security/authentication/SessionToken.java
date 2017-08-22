package pl.nask.crs.security.authentication;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * (C) Copyright 2011 NASK
 * Software Research & Development Department
 */
public class SessionToken {

    private long timestamp;

    private final String userName;

    private final String tokenId;

    private String superUserName;

    public SessionToken(String userName, String tokenId) {
        this.userName = userName;
        this.tokenId = tokenId;
        refresh();
    }

    public SessionToken(String userName, String tokenId, String superUserName, String superUserTokenId) {
        this.userName = userName;
        this.tokenId = tokenId;
        this.superUserName = superUserName;
        refresh();
    }

    public String getUserName() {
        return userName;
    }

    public String getTokenId() {
        return tokenId;
    }

    public String getSuperUserName() {
        return superUserName;
    }

    public boolean isExpired(long sessionTimeout) {
        return timestamp + sessionTimeout < new Date().getTime();
    }

    public void refresh() {
        this.timestamp = new Date().getTime();
    }
}
