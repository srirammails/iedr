package pl.nask.crs.app.domains.wrappers;

import java.util.ArrayList;
import java.util.List;

import pl.nask.crs.commons.dns.validator.DomainNameValidator;
import pl.nask.crs.commons.dns.validator.IPAddressValidator;
import pl.nask.crs.commons.dns.validator.InvalidDomainNameException;
import pl.nask.crs.commons.dns.validator.InvalidIPAddressException;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.domains.nameservers.IPAddress;
import pl.nask.crs.domains.nameservers.Nameserver;

import com.opensymphony.xwork2.ActionSupport;

/**
 * Wrapper for the list of dns servers.
 * 
 * @author Artur Gniadzik
 */
public class DnsWrapper {

    private final List<NameserverStub> currentNameservers;

    private List<Nameserver> generatedNameservers = new ArrayList<Nameserver>();
    private boolean dirty = true;

    public DnsWrapper(List<Nameserver> namesevers) {
        this.currentNameservers = new ArrayList<NameserverStub>();

        if (namesevers != null) {
            for (Nameserver ns : namesevers) {
                this.currentNameservers.add(new NameserverStub(ns));
            }
        }
    }

    public List<NameserverStub> getCurrentNameservers() {
        return currentNameservers;
    }
    
    public void setNewNames(String[] names) {        
        dirty = true;
        // first element of the list is artificial so indexing should be started from 1
        trim(currentNameservers, names.length - 1);
        for (int i = 1; i < names.length; i++) {
        NameserverStub ns = getNs(currentNameservers, i - 1);
        ns.setName(names[i]);
        }
    }
    
    public void setNewIps(String[] ips) {
        dirty = true;
        // first element of the list is artificial so indexing should be started from 1
        trim(currentNameservers, ips.length - 1);
        for (int i = 1; i < ips.length; i++) {
            NameserverStub ns = getNs(currentNameservers, i - 1);
            ns.setIp(ips[i]);
        }
    }
    
    private void trim(List<NameserverStub> currentNameservers, int length) {
        while (currentNameservers.size() > length) {
            currentNameservers.remove(currentNameservers.size() - 1);
        }        
    }

    private NameserverStub getNs(List<NameserverStub> currentNameservers, int i) {
        NameserverStub ns = null;
        if (currentNameservers.size() <= i ) {
            ns = new NameserverStub(null);
            currentNameservers.add(ns);
        } else {
            ns = currentNameservers.get(i);
            if(ns==null) {
                ns = new NameserverStub(null);
                currentNameservers.set(i, ns);
            }
        }
        
        return ns;
    }

    /**
     * creates and returns list of the namesevers with data hold by namesevers.
     * Only valid nameservers are on the list! stubs
     * 
     * @return
     */
    public List<Nameserver> createNameserversList() {                
        if (dirty) {
            generatedNameservers.clear();
            for (int i = 0; i < currentNameservers.size(); i++) {
                NameserverStub w = currentNameservers.get(i);
                if (validateNameserver(null, null, w)) {
                    Nameserver ns = new Nameserver(w.getName(), new IPAddress(w.getIp()));
                    ns.setOrder(i); // set order here...
                    generatedNameservers.add(ns);
                }
            }
            dirty = false;
        }

        return generatedNameservers;
    }

    //
//    /**
//     * validates nameservers.
//     * 
//     * @param path
//     *            root (prefix) for the dns fields
//     * @return true if validation was successfull
//     */
//    public boolean validateNameservers(ActionSupport as, String path) {
//        boolean res = true;
//        for (int i=0; i<currentNameservers.size(); i++) {
//            String fieldname = path + ".currentNameservers[" + i + "]";
//            NameserverStub ns = currentNameservers.get(i);
//            res = res & validateNameserver(as, fieldname, ns);
//            
//            
//        }
//        return res;
//    }

    private boolean validateNameserver(ActionSupport as, String fieldname, NameserverStub ns) {
        boolean res = true;
        if (Validator.isEmpty(ns.getName())) {
            if (as != null)
                as.addFieldError(fieldname + ".name", "Name cannot be empty");
            res = false;
        } else {
            try {
                DomainNameValidator.validateName(ns.getName());
            } catch (InvalidDomainNameException e) {
                if (as != null)
                    as.addFieldError(fieldname + ".name", "Wrong nameserver name");
                res = false;
            }
        }

        if (!Validator.isEmpty(ns.getIp())) {
            try {
                IPAddressValidator.getInstance().validate(ns.getIp());
            } catch (InvalidIPAddressException e) {
                if (as != null)
                    as.addFieldError(fieldname + ".ip", "Wrong IP address");
                res = false;
            }
        }

        return res;
    }

}
 