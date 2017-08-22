package pl.nask.crs.web.domains;

import com.opensymphony.xwork2.ActionContext;
import pl.nask.crs.app.domains.DomainAppService;
import pl.nask.crs.commons.exceptions.EmptyRemarkException;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.domains.dsm.DomainIllegalStateException;
import pl.nask.crs.domains.dsm.DsmEventName;
import pl.nask.crs.domains.exceptions.DomainNotFoundException;
import pl.nask.crs.web.security.AuthenticatedUserAwareAction;

import java.util.Arrays;
import java.util.List;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public class DomainForceDSMEventChangeAction extends AuthenticatedUserAwareAction {

    private ForceDSMChangeWrapper wrapper;

    private DomainAppService domainAppService;

    public DomainForceDSMEventChangeAction(DomainAppService domainAppService) {
        this.domainAppService = domainAppService;
    }

    public ForceDSMChangeWrapper getWrapper() {
        return wrapper;
    }

    public void setWrapper(ForceDSMChangeWrapper wrapper) {
        this.wrapper = wrapper;
    }

    public List<DsmEventName> getEventNames() {
        return Arrays.asList(DsmEventName.values());
    }

    public String force() throws Exception {
        try {
            domainAppService.forceDSMEvent(getUser(), wrapper.getDomainNamesAsList(), wrapper.getEventName(), wrapper.getRemark());
            addActionMessage("Force DSM event success");
            return SUCCESS;
        } catch (DomainNotFoundException e) {
            addActionError("Domain not found: " + e.getDomainName());
            return ERROR;
        } catch (EmptyRemarkException e) {
            addActionError("Remark cannot be empty");
            return ERROR;
        } catch (DomainIllegalStateException e) {
            addActionError("DSM event cannot proceed: " + e.getMessage());
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
