package pl.nask.crs.api.common;

import javax.jws.WebService;

import pl.nask.crs.api.vo.CrsVersionInfoVO;
import pl.nask.crs.commons.config.ApplicationConfig;

@WebService(serviceName = "CRSInfo", endpointInterface = "pl.nask.crs.api.common.CRSInfo")
public class InfoEndpoint implements CRSInfo {
	private ApplicationConfig applicationConfig;
	
	@Override
	public CrsVersionInfoVO getVersionInfo() {
		return new CrsVersionInfoVO();
	}
	
	@Override
	public int getSessionDuration() {	
		return applicationConfig.getUserSessionTimeout();
	}
	
	@Override
	public ServerTime getServerTime() {
		return new ServerTime();
	}
	
	public void setApplicationConfig(ApplicationConfig applicationConfig) {
		this.applicationConfig = applicationConfig;
	}
}
