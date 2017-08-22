package pl.nask.crs.security;

import javax.annotation.Resource;

import org.apache.commons.lang.time.DateUtils;
import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import pl.nask.crs.commons.AbstractTest;
import pl.nask.crs.security.dao.LoginAttemptDAO;

import java.util.Calendar;
import java.util.Date;

/**
 * (C) Copyright 2013 NASK
 * Software Research & Development Department
 */
public class LoginAttemptDAOTest extends AbstractTest {

    @Resource
    LoginAttemptDAO dao;

    @Test
    public void testCreate() throws Exception {
        Date aDate = DateUtils.setMilliseconds(new Date(), 999);
        LoginAttempt loginAttempt1 = LoginAttempt.newSuccessInstance("nic1", "1.1.1.1");
        LoginAttempt loginAttempt2 = LoginAttempt.newFailedInstance("nic2", "2.2.2.2", Cause.INVALID_PASSWORD, 10);
        loginAttempt1.setDate(aDate);

        long attemptId1 = dao.createAttempt(loginAttempt1);
        long attemptId2 = dao.createAttempt(loginAttempt2);

        LoginAttempt fromDB1 = dao.get(attemptId1);
        LoginAttempt fromDB2 = dao.get(attemptId2);

        assertEquals(loginAttempt1, fromDB1);
        assertEquals(loginAttempt2, fromDB2);

        AssertJUnit.assertEquals(0, fromDB1.getFailureCount());
        AssertJUnit.assertTrue(fromDB2.getFailureCount() > 0);
    }

    private void assertEquals(LoginAttempt expected, LoginAttempt actual) {
        AssertJUnit.assertNotNull(expected);
        AssertJUnit.assertNotNull(actual);
        AssertJUnit.assertEquals(expected.getNicHandleId(), actual.getNicHandleId());
        Assert.assertEquals(DateUtils.truncate(expected.getDate(), Calendar.SECOND), actual.getDate());
        AssertJUnit.assertEquals(expected.getClientIP(), actual.getClientIP());
        AssertJUnit.assertEquals(expected.getCause(), actual.getCause());
        AssertJUnit.assertEquals(expected.isSuccessful(), actual.isSuccessful());
    }

    @Test
    public void testGetLastByIP() throws Exception {
        LoginAttempt attempt = dao.getLastAttemptByClientIP("6.6.6.6");
        AssertJUnit.assertNotNull(attempt);
        AssertJUnit.assertEquals(2, attempt.getId());
        AssertJUnit.assertEquals("wrong", attempt.getNicHandleId());
        Assert.assertNotNull(attempt.getDate());
        AssertJUnit.assertEquals("6.6.6.6", attempt.getClientIP());
        AssertJUnit.assertEquals(Cause.INVALID_NIC, attempt.getCause());
        AssertJUnit.assertEquals(3, attempt.getFailureCount());
    }

    @Test
    public void testGetLastByNic() throws Exception {
        LoginAttempt attempt = dao.getLastAttemptByNic("wrong");
        AssertJUnit.assertNotNull(attempt);
        AssertJUnit.assertEquals(2, attempt.getId());
        AssertJUnit.assertEquals("wrong", attempt.getNicHandleId());
        Assert.assertNotNull(attempt.getDate());
        AssertJUnit.assertEquals("6.6.6.6", attempt.getClientIP());
        AssertJUnit.assertEquals(Cause.INVALID_NIC, attempt.getCause());
        AssertJUnit.assertEquals(3, attempt.getFailureCount());
    }
}
