package pl.nask.crs.web.domains;

import pl.nask.crs.app.domains.DomainAppService;
import pl.nask.crs.commons.exceptions.EmptyRemarkException;
import pl.nask.crs.domains.exceptions.DomainNotFoundException;
import pl.nask.crs.web.security.AuthenticatedUserAwareAction;

import java.util.List;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public class DomainForceDSMStateChangeAction extends AuthenticatedUserAwareAction {

    private DomainAppService domainAppService;

    private ForceDSMChangeWrapper wrapper;

    public DomainForceDSMStateChangeAction(DomainAppService domainAppService) {
        this.domainAppService = domainAppService;
    }

    public ForceDSMChangeWrapper getWrapper() {
        return wrapper;
    }

    public void setWrapper(ForceDSMChangeWrapper wrapper) {
        this.wrapper = wrapper;
    }

    public List<Integer> getStateIds() {
        return domainAppService.getDsmStates(getUser());
    }

    public String force() {
        try {
            domainAppService.forceDSMState(getUser(), wrapper.getDomainNamesAsList(), wrapper.getState(), wrapper.getRemark());
            addActionMessage("Force DSM state success");
            return SUCCESS;
        } catch (DomainNotFoundException e) {
            addActionError("Domain not found: " + e.getDomainName());
            return ERROR;
        } catch (EmptyRemarkException e) {
            addActionError("Remark cannot be empty");
            return ERROR;
        } catch (Exception e) {
            addActionError("ERROR: " + e.getMessage());
            return ERROR;
        }
    }

    @Override
    public String input() throws Exception {
        wrapper = new ForceDSMChangeWrapper();
        return super.input();
    }
}
