package pl.nask.crs.api.zone;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import pl.nask.crs.api.vo.AuthenticatedUserVO;

@WebService(targetNamespace = "http://domainregistry.ie/")
public interface CRSZoneAppService {

    @WebMethod
    public void zonePublished(
            @WebParam(name = "user") AuthenticatedUserVO user,
            @WebParam(name = "domainNames") List<String> domainNames);

    @WebMethod
    public void zoneUnpublished(
            @WebParam(name = "user") AuthenticatedUserVO user,
            @WebParam(name = "domainNames") List<String> domainNames);

    @WebMethod
    void zoneCommit(@WebParam(name = "user") AuthenticatedUserVO user);
}
