package pl.nask.crs.domains.dao.ibatis.converters;

import pl.nask.crs.domains.dao.ibatis.objects.InternalNameserver;
import pl.nask.crs.domains.nameservers.Nameserver;
import pl.nask.crs.domains.nameservers.IPAddress;
import pl.nask.crs.commons.dao.AbstractConverter;

/**
 * @author Kasia Fulara
 */
public class NameserverConverter extends AbstractConverter<InternalNameserver, Nameserver> {

    protected Nameserver _to(InternalNameserver src) {
        IPAddress ip = src.getIpAddress() == null ? null : new IPAddress(src.getIpAddress());
        return new Nameserver(
                src.getName(),
                ip,
                src.getDnsOrder()
        );
    }

    protected InternalNameserver _from(Nameserver nameserver) {
        InternalNameserver ret = new InternalNameserver();
        ret.setName(nameserver.getName());
        ret.setIpAddress(nameserver.getIpAddressAsString());
        ret.setDnsOrder(nameserver.getOrder());
        return ret;
    }
}
