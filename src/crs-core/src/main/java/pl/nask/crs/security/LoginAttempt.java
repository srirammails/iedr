package pl.nask.crs.security;

import pl.nask.crs.commons.utils.Validator;

import java.util.Date;

/**
 * (C) Copyright 2013 NASK
 * Software Research & Development Department
 */
public class LoginAttempt {

    private long id;
    private String nicHandleId;
    private Date date;
    private String clientIP;
    private Cause cause;
    private int failureCount;

    public LoginAttempt() {}

    public static LoginAttempt newSuccessInstance(String nicHandleId, String clientIP) {
        return new LoginAttempt(nicHandleId, new Date(), clientIP, null, 0);
    }

    public static LoginAttempt newFailedInstance(String nicHandleId, String clientIP, Cause cause, int failureCount) {
        Validator.assertNotNull(cause, "LoginAttempt cause");
        return new LoginAttempt(nicHandleId, new Date(), clientIP, cause, failureCount);
    }

    private LoginAttempt(String nicHandleId, Date date, String clientIP, Cause cause, int failureCount) {
        this.nicHandleId = nicHandleId;
        this.date = date;
        this.clientIP = clientIP;
        this.cause = cause;
        this.failureCount = failureCount;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNicHandleId() {
        return nicHandleId;
    }

    public void setNicHandleId(String nicHandleId) {
        this.nicHandleId = nicHandleId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getClientIP() {
        return clientIP;
    }

    public void setClientIP(String clientIP) {
        this.clientIP = clientIP;
    }

    public boolean isSuccessful() {
        return cause == null;
    }

    public Cause getCause() {
        return cause;
    }

    public void setCause(Cause cause) {
        this.cause = cause;
    }

    public int getFailureCount() {
        return failureCount;
    }

    public void setFailureCount(int failureCount) {
        this.failureCount = failureCount;
    }

    @Override
    public String toString() {
        return String.format("LoginAttempt[id=%s, nicHandleId=%s, date=%s, cause=%s]", id, nicHandleId, date, cause);
    }
}
