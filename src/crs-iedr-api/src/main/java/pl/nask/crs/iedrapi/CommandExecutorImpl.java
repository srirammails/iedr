package pl.nask.crs.iedrapi;

import static pl.nask.crs.iedrapi.CommandTypeEnum.CHECK;
import static pl.nask.crs.iedrapi.CommandTypeEnum.CREATE;
import static pl.nask.crs.iedrapi.CommandTypeEnum.DELETE;
import static pl.nask.crs.iedrapi.CommandTypeEnum.INFO;
import static pl.nask.crs.iedrapi.CommandTypeEnum.MODIFY;
import static pl.nask.crs.iedrapi.CommandTypeEnum.PAY;
import static pl.nask.crs.iedrapi.CommandTypeEnum.QUERY;
import static pl.nask.crs.iedrapi.CommandTypeEnum.NRP;
import ie.domainregistry.ieapi_1.CommandType;
import ie.domainregistry.ieapi_1.LoginType;
import ie.domainregistry.ieapi_1.ReadWriteType;
import ie.domainregistry.ieapi_1.ResponseType;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import pl.nask.crs.iedrapi.exceptions.AuthorizationErrorException;
import pl.nask.crs.iedrapi.exceptions.CommandFailed;
import pl.nask.crs.iedrapi.exceptions.CommandUseError;
import pl.nask.crs.iedrapi.exceptions.IedrApiException;
import pl.nask.crs.iedrapi.exceptions.UnimplementedCommand;
import pl.nask.crs.iedrapi.handlerreg.HandlerRegistry;
import pl.nask.crs.security.authentication.AccessDeniedException;

public class CommandExecutorImpl implements CommandExecutor {
	
	private static final Logger log = Logger.getLogger(CommandExecutorImpl.class);
	
    private APISessionAwareCommandHandler<LoginType> loginHandler;
    private APISessionAwareCommandHandler<Object> logoutHandler;

	private HandlerRegistry handlerRegistry;
	
	
	
	
	public CommandExecutorImpl()  throws JAXBException {
		
	}
	
//	/* (non-Javadoc)
//	 * @see pl.nask.crs.iedrapi.CommandExecutor#execute(java.lang.String, javax.servlet.http.HttpSession, java.lang.String)
//	 */
//	@Transactional(rollbackFor=Exception.class, propagation=Propagation.NESTED)
//    public String execute(String requestCmd, HttpSession session, String remoteAddr) throws CommandExecutorFailedCommand {
//    	
//    	AuthData authData = AuthData.getInstance(session, remoteAddr);
//    	
//    	ResponseType result = ResponseTypeFactory.createResponse(2400);
//
//    	String res = findResponse(authData, requestCmd);
//    	if (res != null)
//    		return res;
//    		
//    	CommandType command = null;
//    	try {
//    		command = unmarshal(requestCmd);
//    		// execute the command
//    		log.debug("executing...");
//    		result = execute(authData, command);
//
//    		// store result only if there was no error 
//    		if (command != null)
//				result.setTid(command.getTid());
//			
//			res = marshal(result);
//			
//			if (isStorable(command))
//				storeResult(authData, requestCmd, res);
//		} catch (CommandSyntaxErrorException e) {
//			log.error("Unparseable command", e);
//			throw new CommandExecutorFailedCommand(marshal(e.getResponseType()), e);
//		} catch (IedrApiException e) {
//			log.warn("Error executing command", e);
//			throw new CommandExecutorFailedCommand(marshal(e.getResponseType()), e);
//		} catch (Exception e) {
//			log.warn("Error executing command", e);
//			throw new CommandExecutorFailedCommand(marshal(new CommandFailed(e).getResponseType()), e);
//		} 
//		return res;
//	}
	
	
	@Transactional(rollbackFor=Exception.class, propagation=Propagation.NESTED)
	@Override
	public ResponseType execute(AuthData auth, CommandType command, ValidationCallback callback) throws IedrApiException {
		ResponseType res;
		try {
			res = doExecute(auth, command, callback);                
			// only AccessDeniedException and IedrApiException are 
			// expected. Any other should cause 'command failed'			
		} catch (AccessDeniedException e) {			
			throw new AuthorizationErrorException(e);		
		} catch (RuntimeException e) {
			throw new CommandFailed(e);
		}
		return res;
	}
	
    public ResponseType doExecute(AuthData auth, CommandType command, ValidationCallback callback) throws AccessDeniedException, IedrApiException {
        
        if (command.getLogin() != null)    
            return loginHandler.handle(auth, command.getLogin(), callback);
        if (command.getLogout() != null)
            return logoutHandler.handle(auth, command.getLogout(), callback);

        // rest of the methods require authorization
        if (auth.isUserLoggedIn()) {
			if (command.getCreate() != null) {
				return handle(auth, command.getCreate(), CREATE, callback);
            } else if (command.getInfo() != null) {
            	return handle(auth, command.getInfo(), INFO, callback);
            } else if (command.getModify() != null) {
            	return handle(auth, command.getModify(), MODIFY, callback);
            } else if (command.getQuery() != null) {
            	return handle(auth, command.getQuery(), QUERY, callback);
            } else if (command.getCheck() != null) {
            	return handle(auth, command.getCheck(), CHECK, callback);
            } else if (command.getNrp() != null) {
            	return handle(auth, command.getNrp(), NRP, callback);
            } else if (command.getDelete() != null) {
            	return handle(auth, command.getDelete(), DELETE, callback);
            } else if (command.getPay() != null) {
            	return handle(auth, command.getPay(), PAY, callback);
            } else {
            	log.error("Unrecognized command type");
            	throw new UnimplementedCommand();
            }
        } else {
            // invalid session - user not logged in
        	auth.logout();
            throw new CommandUseError();
        }
    }

    private ResponseType handle(AuthData auth, ReadWriteType cmd, CommandTypeEnum cmdType, ValidationCallback callback) throws AccessDeniedException, IedrApiException {
    	JAXBElement jaxbCmd = (JAXBElement) cmd.getAny();
    	APICommandHandler handler = handlerRegistry.getHandler(cmdType, jaxbCmd.getName().getLocalPart(), jaxbCmd.getValue().getClass());
    	
    	if (handler == null) {
    		log.error("Cannot find handler for parameters: cmdType=" + cmdType + ", cmdName=" + jaxbCmd.getName().getLocalPart() + ", class=" + jaxbCmd.getValue().getClass());
    		throw new UnimplementedCommand();
    	}
    	
    	return handler.handle(auth, jaxbCmd.getValue(), callback);
    }

    /*
     * simple setters 
     */

	public void setLoginHandler(APISessionAwareCommandHandler<LoginType> loginHandler) {
        this.loginHandler = loginHandler;
    }

    public void setLogoutHandler(APISessionAwareCommandHandler<Object> logoutHandler) {
        this.logoutHandler = logoutHandler;
    }  
    
    public void setHandlerRegistry(HandlerRegistry handlerRegistry) {
		this.handlerRegistry = handlerRegistry;
	}
	   
}
