package pl.nask.crs.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import pl.nask.crs.api.authentication.CRSAuthenticationService;
import pl.nask.crs.api.domain.CRSDomainAppService;
import pl.nask.crs.api.vo.AuthenticatedUserVO;
import pl.nask.crs.api.vo.ExtendedDomainInfoVO;
import pl.nask.crs.domains.exceptions.DomainNotFoundException;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.security.authentication.AuthenticationException;
import pl.nask.crs.security.authentication.InvalidTokenException;
import pl.nask.crs.security.authentication.UserNotAuthenticatedException;

/**
 * (C) Copyright 2011 NASK
 * Software Research & Development Department
 */
@ContextConfiguration(locations = {"/crs-api-config.xml"})
public class AuthenticationTest extends AbstractTransactionalTestNGSpringContextTests {

    @Autowired
    CRSAuthenticationService authenticationService;

    @Autowired
    CRSDomainAppService domainAppService;

    @Test
    public void authenticateTest() throws AuthenticationException, UserNotAuthenticatedException,
            InvalidTokenException, SessionExpiredException {
        AuthenticatedUser user = authenticationService.authenticate("GEORGE-IEDR", "Passw0rd!", "1.1.1.1", null);
        AssertJUnit.assertEquals("user name", "GEORGE-IEDR", user.getUsername());
        AssertJUnit.assertNotNull("token", user.getAuthenticationToken());
    }

    @Test
    public void doubleAuthenticationTest() throws AuthenticationException, UserNotAuthenticatedException,
            InvalidTokenException, SessionExpiredException {
        AuthenticatedUser user = authenticationService.authenticate("GEORGE-IEDR", "Passw0rd!", "1.1.1.1", null);
        AssertJUnit.assertEquals("user name", "GEORGE-IEDR", user.getUsername());
        AssertJUnit.assertNotNull("token", user.getAuthenticationToken());
        String token1 = user.getAuthenticationToken();
        user = authenticationService.authenticate("GEORGE-IEDR", "Passw0rd!", "1.1.1.1", null);
        String token2 = user.getAuthenticationToken();
        AssertJUnit.assertTrue("same token ids", !token1.equals(token2));
    }

    @Test
    public void useMethodWithAuthenticatedUserTest() throws AuthenticationException, UserNotAuthenticatedException,
            InvalidTokenException, SessionExpiredException, AccessDeniedException, DomainNotFoundException {
        AuthenticatedUserVO user = authenticationService.authenticate("GEORGE-IEDR", "Passw0rd!", "1.1.1.1", null);
        ExtendedDomainInfoVO extendedDomainInfoVO = domainAppService.view(user, "suka.ie");
        AssertJUnit.assertNotNull("extended domain info", extendedDomainInfoVO);
        AssertJUnit.assertNotNull("domain", extendedDomainInfoVO.getDomain());
    }

    @Test(expectedExceptions = UserNotAuthenticatedException.class)
    public void useMethodWithoutAuthenticatedUserTest() throws AuthenticationException, UserNotAuthenticatedException,
            InvalidTokenException, SessionExpiredException, AccessDeniedException, DomainNotFoundException {
        AuthenticatedUserVO user = new AuthenticatedUserVO(new AuthenticatedUser() {
            @Override
            public String getUsername() {
                return "qq";
            }

            @Override
            public String getAuthenticationToken() {
                return "qq";
            }
            
            @Override
            public String getSuperUserName() {            
            	return null;
            }
        });
        ExtendedDomainInfoVO extendedDomainInfoVO = domainAppService.view(user, "suka.ie");
    }

    @Test(expectedExceptions = InvalidTokenException.class)
    public void useMethodWithInvalidToken() throws AuthenticationException, UserNotAuthenticatedException,
            InvalidTokenException, SessionExpiredException, AccessDeniedException, DomainNotFoundException {
        AuthenticatedUserVO user = authenticationService.authenticate("GEORGE-IEDR", "Passw0rd!", "1.1.1.1", null);
        AuthenticatedUserVO newUser = authenticationService.authenticate("SAM-IEDR", "Passw0rd!", "1.1.1.1", null);
        user.setUsername(newUser.getUsername());
        ExtendedDomainInfoVO extendedDomainInfoVO = domainAppService.view(user, "suka.ie");
    }

    @Test(expectedExceptions = UserNotAuthenticatedException.class)
    public void switchWithInvalidTokenTest() throws Exception {
        AuthenticatedUserVO user = null;
        user = authenticationService.authenticate("IH4-IEDR", "Passw0rd!", "1.1.1.1", null);
        user.setAuthenticationToken("invalid token");
        user = authenticationService.switchUser(user, "G1-IEDR");
    }

    @Test(expectedExceptions = AccessDeniedException.class)
    public void switchWithoutPermissionTest() throws Exception {
        AuthenticatedUserVO user = authenticationService.authenticate("AAA22-IEDR", "Passw0rd!", "1.1.1.1", null);
        user = authenticationService.switchUser(user, "APIT2-IEDR");
    }

    @Test
    public void switchWithPermissionsTest() throws Exception {
        AuthenticatedUserVO user = authenticationService.authenticate("GEORGE-IEDR", "Passw0rd!", "1.1.1.1", null);
        AssertJUnit.assertEquals("GEORGE-IEDR", user.getUsername());
        Assert.assertNotNull(user.getAuthenticationToken());
        user = authenticationService.switchUser(user, "APIT2-IEDR");
        AssertJUnit.assertEquals("APIT2-IEDR", user.getUsername());
        Assert.assertNotNull(user.getAuthenticationToken());
        boolean isSwitched = authenticationService.isUserSwitched(user);
        AssertJUnit.assertTrue(isSwitched);
    }
    
    @Test
    public void maySwitchUserAfterAuthenticateAndSwitchUser() throws Exception {
        AuthenticatedUserVO user = authenticationService.authenticateAndSwitchUser("GEORGE-IEDR", "APIT2-IEDR", "Passw0rd!", "1.1.1.1");
        AssertJUnit.assertEquals("APIT2-IEDR", user.getUsername());
        Assert.assertNotNull(user.getAuthenticationToken());
        
        user = authenticationService.switchUser(user, "APITEST-IEDR");
        AssertJUnit.assertEquals("APITEST-IEDR", user.getUsername());
        Assert.assertNotNull(user.getAuthenticationToken());
        boolean isSwitched = authenticationService.isUserSwitched(user);
        AssertJUnit.assertTrue(isSwitched);
    }
}
