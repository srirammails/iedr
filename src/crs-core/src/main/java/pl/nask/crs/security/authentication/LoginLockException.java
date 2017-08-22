package pl.nask.crs.security.authentication;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * (C) Copyright 2013 NASK
 * Software Research & Development Department
 */
@XmlType
@XmlAccessorType(XmlAccessType.FIELD)
public class LoginLockException extends AuthenticationException {

    private long lockTimeInSeconds;

    public LoginLockException(long lockTimeInSeconds) {
        super("Login Locked :" + lockTimeInSeconds);
        this.lockTimeInSeconds = lockTimeInSeconds;
    }

    public long getLockTimeInSeconds() {
        return lockTimeInSeconds;
    }
}
