package pl.nask.crs.api.batch;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import pl.nask.crs.api.vo.AuthenticatedUserVO;
import pl.nask.crs.security.authentication.AccessDeniedException;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 *
 * @author Artur Gniadzik
 *
 */
@WebService(targetNamespace = "http://domainregistry.ie/")
public interface CRSBatchAppService {
    @WebMethod
    void runInvoicing(@WebParam(name = "user") AuthenticatedUserVO user);

    @WebMethod
    void runTransactionInvalidation(@WebParam(name = "user") AuthenticatedUserVO user);

    @WebMethod    
    public void pushQ(
	@WebParam(name = "user") AuthenticatedUserVO user);
    
    @WebMethod
    public void runNotificationProcess(
    @WebParam(name = "user") AuthenticatedUserVO user);

    /**
     * Performs an autorenew operation on all domains, which renewal date is due and their renewal mode allows to perform an autorenew.
     *
     * @param user user, which has privileges to invoke thios method
     * @throws pl.nask.crs.security.authentication.AccessDeniedException
     */
    @WebMethod
    public void autorenewAll(
            @WebParam (name = "user") AuthenticatedUserVO user
    ) throws AccessDeniedException;

}
