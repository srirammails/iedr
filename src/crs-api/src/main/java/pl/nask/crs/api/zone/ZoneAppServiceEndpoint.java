package pl.nask.crs.api.zone;

import java.util.List;

import javax.jws.WebService;

import pl.nask.crs.api.WsSessionAware;
import pl.nask.crs.api.validation.ValidationHelper;
import pl.nask.crs.api.vo.AuthenticatedUserVO;
import pl.nask.crs.app.commons.CommonAppService;

@WebService(serviceName = "CRSZoneAppService", endpointInterface = "pl.nask.crs.api.zone.CRSZoneAppService")
public class ZoneAppServiceEndpoint extends WsSessionAware implements CRSZoneAppService {
    private CommonAppService commonAppService;
	
    public void setCommonAppService(CommonAppService commonAppService) {
		this.commonAppService = commonAppService;
	}
    
	@Override
    public void zonePublished(AuthenticatedUserVO user, List<String> domainNames) {
        ValidationHelper.validate(user);
        validateSession(user);
        commonAppService.zonePublished(user, domainNames);
    }

    @Override
    public void zoneCommit(AuthenticatedUserVO user) {
        ValidationHelper.validate(user);
        validateSession(user);
        commonAppService.zoneCommit(user);
    }

    @Override
    public void zoneUnpublished(AuthenticatedUserVO user, List<String> domainNames) {
        ValidationHelper.validate(user);
        validateSession(user);
        commonAppService.zoneUnpublished(user, domainNames);
    }

}
