package pl.nask.crs.app.nichandles.wrappers;

import java.util.HashSet;
import java.util.Set;

import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.user.Group;
import pl.nask.crs.user.service.AuthorizationGroupsFactory;

/**
 * @author Marianna Mysiorska, Artur Gniadzik
 */
public class PermissionGroupsWrapper {

    private AuthorizationGroupsFactory authorizationGroupsFactory;
    private Boolean guest;
    private Boolean registrar;
    private Boolean customerService;
    private Boolean finance;
    private Boolean financeLead;
    private Boolean hostmaster;
    private Boolean hostmasterLead;
    private Boolean batch;
    private Boolean technical;
    private Boolean technicalLead;
    private Boolean superRegistrar;
    private Boolean direct;


    public PermissionGroupsWrapper() {
    }

    public PermissionGroupsWrapper(AuthorizationGroupsFactory authorizationGroupsFactory) {
        Validator.assertNotNull(authorizationGroupsFactory, "authorization group factory");
        this.authorizationGroupsFactory = authorizationGroupsFactory;
    }
    
    public Boolean getDefault() {
    	return true;
    }

    public Boolean getGuest() {
        return guest == null ? false : guest;
    }

    public void setGuest(Boolean guest) {
        this.guest = guest;
    }

    public Boolean getRegistrar() {
        return registrar == null ? false : registrar;
    }

    public void setRegistrar(Boolean web) {
        this.registrar = web;
    }

    public Boolean getDirect() {
        return direct == null ? false : direct;
    }

    public void setDirect(Boolean direct) {
        this.direct = direct;
    }

    public Boolean getCustomerService() {
        return customerService == null ? false : customerService;
    }

    public void setCustomerService(Boolean customerService) {
        this.customerService = customerService;
    }

    public Boolean getFinance() {
        return finance == null ? false : finance;
    }

    public void setFinance(Boolean finance) {
        this.finance = finance;
    }

    public Boolean getFinanceLead() {
        return financeLead == null ? false : financeLead;
    }

    public void setFinanceLead(Boolean financeLead) {
        this.financeLead = financeLead;
    }

    public Boolean getHostmaster() {
        return hostmaster == null ? false : hostmaster;
    }

    public void setHostmaster(Boolean hostmaster) {
        this.hostmaster = hostmaster;
    }

    public Boolean getHostmasterLead() {
        return hostmasterLead == null ? false : hostmasterLead;
    }

    public void setHostmasterLead(Boolean hostmasterLead) {
        this.hostmasterLead = hostmasterLead;
    }

    public Boolean getBatch() {
        return batch == null ? false : batch;
    }

    public void setBatch(Boolean batch) {
        this.batch = batch;
    }

    public Boolean getTechnical() {
        return technical == null ? false : technical;
    }

    public void setTechnical(Boolean technical) {
        this.technical = technical;
    }

    public Boolean getTechnicalLead() {
        return technicalLead == null ? false : technicalLead;
    }

    public void setTechnicalLead(Boolean technicalLead) {
        this.technicalLead = technicalLead;
    }
    
    public Boolean getSuperRegistrar() {
        return superRegistrar == null ? false : superRegistrar;
    }

    public void setSuperRegistrar(Boolean superRegistrar) {
        this.superRegistrar = superRegistrar;
    }


    public Set<Group> getPermissionGroups(){
        HashSet<Group> groups = new HashSet<Group>();
        if (getGuest())
            groups.add(authorizationGroupsFactory.getGroupByName("Guest"));
        if (getRegistrar())
            groups.add(authorizationGroupsFactory.getGroupByName("Registrar"));
        if (getCustomerService())
            groups.add(authorizationGroupsFactory.getGroupByName("CustomerService"));
        if (getFinance())
            groups.add(authorizationGroupsFactory.getGroupByName("Finance"));
        if (getFinanceLead())
            groups.add(authorizationGroupsFactory.getGroupByName("FinanceLead"));
        if (getHostmaster())
            groups.add(authorizationGroupsFactory.getGroupByName("Hostmaster"));
        if (getHostmasterLead())
            groups.add(authorizationGroupsFactory.getGroupByName("HostmasterLead"));
        if (getBatch())
            groups.add(authorizationGroupsFactory.getGroupByName("Batch"));
        if (getTechnical())
            groups.add(authorizationGroupsFactory.getGroupByName("Technical"));
        if (getTechnicalLead())
            groups.add(authorizationGroupsFactory.getGroupByName("TechnicalLead"));
        if (getSuperRegistrar())
            groups.add(authorizationGroupsFactory.getGroupByName("SuperRegistrar"));
        if (getDirect())
            groups.add(authorizationGroupsFactory.getGroupByName("Direct"));
        if (getDefault())
        	groups.add(authorizationGroupsFactory.getGroupByName("Default"));
        return groups;
    }
    
    public void setGroup(Group g, boolean set) {
        String name = g.getName();
        if("Batch".equals(name)) {
            setBatch(set);           
        } else if ("CustomerService".equals(name)) {
            setCustomerService(set);
        } else if ("Finance".equals(name)) {
            setFinance(set);
    	} else if("FinanceLead".equals(name)) {
            setFinanceLead(set);
		} else if ("Guest".equals(name)) {
            setGuest(set);
		} else if ("Hostmaster".equals(name)) {
            setHostmaster(set);
		} else if ("HostmasterLead".equals(name)) {
            setHostmasterLead(set);
		} else if ("Technical".equals(name)) {
            setTechnical(set);
		} else if ("TechnicalLead".equals(name)) {
            setTechnicalLead(set);
		} else if ("Registrar".equals(name)) {
            setRegistrar(set);
		} else if ("SuperRegistrar".equals(name)) {
            setSuperRegistrar(set);
        } else if ("Direct".equals(name)) {
            setDirect(set);
        } else if ("Default".equals(name)) {
        	// do nothing - the default group is always set
        } else
            throw new IllegalArgumentException("Cannot " + (set ? "enable" : "disable") + " group " + g);
    }
}
