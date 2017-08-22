package pl.nask.crs.security.dao.ibatis;

import pl.nask.crs.commons.dao.ibatis.GenericIBatisDAO;
import pl.nask.crs.security.LoginAttempt;
import pl.nask.crs.security.dao.LoginAttemptDAO;

/**
 * (C) Copyright 2013 NASK
 * Software Research & Development Department
 */
public class LoginAttemptIbatisDAO extends GenericIBatisDAO<LoginAttempt, Long> implements LoginAttemptDAO {

    public LoginAttemptIbatisDAO() {
        setGetQueryId("login-attempts.getById");
    }

    @Override
    public long createAttempt(LoginAttempt loginAttempt) {
        return this.<Long>performInsert("login-attempts.createAttempt", loginAttempt);
    }

    @Override
    public LoginAttempt getLastAttemptByClientIP(String clientIP) {
        return performQueryForObject("login-attempts.getLastByIP", clientIP);
    }

    @Override
    public LoginAttempt getLastAttemptByNic(String nicHandleId) {
        return performQueryForObject("login-attempts.getLastByNic", nicHandleId);
    }
}
