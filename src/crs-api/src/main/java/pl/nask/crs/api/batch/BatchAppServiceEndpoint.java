package pl.nask.crs.api.batch;

import javax.jws.WebService;

import pl.nask.crs.api.WsSessionAware;
import pl.nask.crs.api.vo.AuthenticatedUserVO;
import pl.nask.crs.app.AppServicesRegistry;
import pl.nask.crs.app.ServicesRegistry;
import pl.nask.crs.commons.OpInfo;
import pl.nask.crs.security.authentication.AccessDeniedException;

@WebService(serviceName = "CRSBatchAppService", endpointInterface = "pl.nask.crs.api.batch.CRSBatchAppService")
public class BatchAppServiceEndpoint extends WsSessionAware implements CRSBatchAppService {
	private AppServicesRegistry appServicesRegistry;
    private ServicesRegistry servicesRegistry;

    public void setAppServicesRegistry(AppServicesRegistry appServicesRegistry) {
		this.appServicesRegistry = appServicesRegistry;
	}

    public void setServicesRegistry(ServicesRegistry servicesRegistry) {
        this.servicesRegistry = servicesRegistry;
    }

    @Override
    public void runInvoicing(AuthenticatedUserVO user) {
        appServicesRegistry.getInvoicingAppService().runInvoicing(user);
    }

    @Override
    public void runTransactionInvalidation(AuthenticatedUserVO user) {
    	appServicesRegistry.getInvoicingAppService().runTransactionInvalidation(user);
    }
    
    @Override
    public void pushQ(AuthenticatedUserVO user) {
    	appServicesRegistry.getDomainAppService().pushQ(user, new OpInfo("WS"));
    }

    @Override
    public void runNotificationProcess(AuthenticatedUserVO user) {
    	appServicesRegistry.getDomainAppService().runNotificationProcess(user);
    }

    @Override
    public void autorenewAll(AuthenticatedUserVO user) throws AccessDeniedException {
        appServicesRegistry.getPaymentAppService().autorenewAll(user);
    }
}
