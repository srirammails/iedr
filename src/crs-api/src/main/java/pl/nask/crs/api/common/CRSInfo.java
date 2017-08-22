package pl.nask.crs.api.common;

import javax.jws.WebService;

import pl.nask.crs.api.vo.CrsVersionInfoVO;

@WebService(targetNamespace = "http://domainregistry.ie/")
public interface CRSInfo {

	CrsVersionInfoVO getVersionInfo();
	
	/**
	 * @return session duration in minutes
	 */
	int getSessionDuration();
	
	ServerTime getServerTime();
		
}
