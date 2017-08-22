package pl.nask.crs.domains.dao.ibatis.objects;

/**
 * @author Patrycja Wegrzynowicz
 * @author Kasia Fulara
 */
public class InternalNameserver {

    private String domainName;

    private String name;

    private String ipAddress;

    private Integer dnsOrder;

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    public Integer getDnsOrder() {
        return dnsOrder;
    }

    public void setDnsOrder(Integer dnsOrder) {
        this.dnsOrder = dnsOrder;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }
}
