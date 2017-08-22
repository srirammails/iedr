package pl.nask.crs.iedrapi.impl;

import ie.domainregistry.ieapi_1.LoginType;
import ie.domainregistry.ieapi_1.ResponseType;

import org.apache.log4j.Logger;

import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.iedrapi.APISessionAwareCommandHandler;
import pl.nask.crs.iedrapi.ApiValidator;
import pl.nask.crs.iedrapi.AuthData;
import pl.nask.crs.iedrapi.IedrApiConfig;
import pl.nask.crs.iedrapi.ResponseTypeFactory;
import pl.nask.crs.iedrapi.ValidationCallback;
import pl.nask.crs.iedrapi.exceptions.ApiAuthenticationException;
import pl.nask.crs.iedrapi.exceptions.CommandFailed;
import pl.nask.crs.iedrapi.exceptions.CommandUseError;
import pl.nask.crs.iedrapi.exceptions.IedrApiException;
import pl.nask.crs.iedrapi.exceptions.ParamValuePolicyErrorException;
import pl.nask.crs.iedrapi.exceptions.ReasonCode;
import pl.nask.crs.nichandle.exception.EmptyPasswordException;
import pl.nask.crs.nichandle.exception.NicHandleEmailException;
import pl.nask.crs.nichandle.exception.NicHandleNotFoundException;
import pl.nask.crs.nichandle.exception.PasswordTooEasyException;
import pl.nask.crs.nichandle.exception.PasswordsDontMatchException;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.security.authentication.AuthenticationException;
import pl.nask.crs.user.exceptions.PasswordAlreadyExistsException;

public class LoginCommandHandler extends AbstractCommandHandler implements APISessionAwareCommandHandler<LoginType> {
	private static final Logger log = Logger.getLogger(LoginCommandHandler.class);
	      
    @Override
    public ResponseType handle(AuthData auth, LoginType command, ValidationCallback callback) throws AccessDeniedException, IedrApiException {
        if (auth.isUserLoggedIn()) {
            throw new CommandUseError();
        }
        try{
            try {
                String username = command.getClID();
                String password = command.getPw();
                String newPassword = command.getNewPW();
                //  TODO: check the code (is session id saved into db?)
                ApiValidator.paramNotEmpty("clid", username);
             
                IedrApiConfig.getUserAwareAppender().registerAppender(username);
                
                ApiValidator.paramNotEmpty("pw", password);  
                ApiValidator.assertNoError(callback);

                AuthenticatedUser token = getAuthService().authenticate(username, password, false, auth.getRemoteAddr(), false, null, false, "api");

                if (!Validator.isEmpty(command.getNewPW())) {
                    getNicHandleAppService().saveNewPassword(token, newPassword, newPassword, username);
                }

                auth.login(token);
                return ResponseTypeFactory.success();

            } catch (AuthenticationException e) {
                throw new ApiAuthenticationException(e); 
            } catch (PasswordTooEasyException e) {
                throw new ParamValuePolicyErrorException(ReasonCode.NEW_PASSWORD_TO_EASY);
            } catch (NicHandleNotFoundException e) {
                throw new ApiAuthenticationException(e);
            } catch (NicHandleEmailException e) {
                log.warn("email exeption", e);
                // success - ignore errors while sending an email
                return ResponseTypeFactory.successNoRes();
            }  catch (PasswordAlreadyExistsException e) {
                throw new ParamValuePolicyErrorException(ReasonCode.NEW_PASSWORD_EQUALS_TO_CURRENT);
            } catch (EmptyPasswordException e) {
                // shall never happen
                throw new CommandFailed(e);
            } catch (PasswordsDontMatchException e) {
                throw new CommandFailed(e);
            } 

        } catch (IedrApiException e) {
            auth.logout();
            throw e;
        }
    }

}
