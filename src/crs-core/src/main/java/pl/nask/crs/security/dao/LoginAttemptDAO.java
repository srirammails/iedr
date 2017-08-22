package pl.nask.crs.security.dao;

import pl.nask.crs.commons.dao.GenericDAO;
import pl.nask.crs.security.LoginAttempt;

/**
 * (C) Copyright 2013 NASK
 * Software Research & Development Department
 */
public interface LoginAttemptDAO extends GenericDAO<LoginAttempt, Long>{

    long createAttempt(LoginAttempt loginAttempt);

    LoginAttempt getLastAttemptByClientIP(String clientIP);

    LoginAttempt getLastAttemptByNic(String nicHandleId);
}
