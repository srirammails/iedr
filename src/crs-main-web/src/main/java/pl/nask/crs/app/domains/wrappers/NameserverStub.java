package pl.nask.crs.app.domains.wrappers;

import pl.nask.crs.domains.nameservers.Nameserver;

public class NameserverStub {
    private String name;
    private String ip;

    public NameserverStub(Nameserver nameserver) {
        if (nameserver != null) {
            this.name = nameserver.getName();
            this.ip = nameserver.getIpAddressAsString();
        }
    }

    public String getName() {
        return name;
    }

    public String getIp() {
        return ip;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}

