package pl.nask.crs.iedrapi.tests;

import org.testng.annotations.Test;

/**
 * @author: Marcin Tkaczyk
 */
public class AuthenticationTest extends TestUtil {
    String result = null;

    @Test
    public void simpleLogin() {
        String loginCommand = xmlFileToString("/commands/login.xml");
        String logoutCommand = xmlFileToString("/commands/logout.xml");

        result = sendFirstRequest(loginCommand);
        assertResultMatch("Simple login", result, "/commands/login_response.xml");

        result = sendRequest(loginCommand);
        assertResultMatch("Second login in the same session", result, "/commands/response_command_use_error.xml");

        result = sendRequest(logoutCommand);
        assertResultMatch("Logout", result, "/commands/logout_response.xml");

        result = sendRequest(logoutCommand);
        assertResultMatch("Second logout in the same session", result, "/commands/response_command_use_error.xml");
    }

    @Test
    public void loginWithPassChange() {
        String logoutCommand = xmlFileToString("/commands/logout.xml");

        String changePassLoginCommand = xmlFileToString("/commands/login_change_password_ok.xml");
        result = sendFirstRequest(changePassLoginCommand);
        assertResultMatch("Change password", result, "/commands/login_response.xml");
        result = sendRequest(logoutCommand);

        changePassLoginCommand = xmlFileToString("/commands/login_change_password_same_password.xml");
        result = sendFirstRequest(changePassLoginCommand);
        assertResultMatch("Change password - new password equal to current", result, "/commands/login_change_pass_same_pass_response.xml");

        changePassLoginCommand = xmlFileToString("/commands/login_change_password_to_easy.xml");
        result = sendFirstRequest(changePassLoginCommand);
        assertResultMatch("Change password - password to easy", result, "/commands/login_change_pass_to_easy_response.xml");
    }

}
