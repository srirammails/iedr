package pl.nask.crs.web.email;

import org.apache.struts2.interceptor.validation.SkipValidation;
import pl.nask.crs.app.email.EmailGroupAppService;
import pl.nask.crs.app.email.EmailTemplateAppService;
import pl.nask.crs.app.emaildisabler.EmailDisablerAppService;
import pl.nask.crs.commons.email.EmailDisabler;
import pl.nask.crs.commons.email.EmailGroup;
import pl.nask.crs.commons.email.EmailTemplate;
import pl.nask.crs.commons.email.search.EmailDisablerSearchCriteria;
import pl.nask.crs.commons.email.search.EmailDisablerSuppressInfo;
import pl.nask.crs.commons.email.search.EmailTemplateSearchCriteria;
import pl.nask.crs.web.security.AuthenticatedUserAwareAction;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class EmailGroupAction extends AuthenticatedUserAwareAction {

    private EmailGroup group;
    private List<EmailDisabler> emailDisablers;

    private long id;

    private String previousAction;

    private EmailGroupAppService service;
    private EmailDisablerAppService emailDisablerAppService;

    public EmailGroupAction(EmailGroupAppService service, EmailDisablerAppService emailDisablerAppService) {
        this.service = service;
        this.emailDisablerAppService = emailDisablerAppService;
    }

    @SkipValidation
    public String view() {
        group = service.get(getUser(), id);
        return SUCCESS;
    }

    @SkipValidation
    public String input() {
        group = service.get(getUser(), id);

        emailDisablers = emailDisablerAppService.getAllOfEmailGroup(getUser(), id);

        return INPUT;
    }

    @SkipValidation
    public String newGroup() {
        group = new EmailGroup();
        return INPUT;
    }

    public String update() {
        service.update(getUser(), group);
        return SUCCESS;
    }

    public String create() {
        service.create(getUser(), group);
        id = group.getId();
        return SUCCESS;
    }

    @SkipValidation
    public String delete() {
        service.delete(getUser(), group);
        return SUCCESS;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public EmailGroup getGroup() {
        return group;
    }

    public void setGroup(EmailGroup group) {
        this.group = group;
    }

    public String getPreviousAction() {
        return previousAction;
    }

    public void setPreviousAction(String previousAction) {
        this.previousAction = previousAction;
    }

    public boolean actionWillBeDestructive() {
        return !emailDisablers.isEmpty();
    }

    public int getAffectedUsersCount() {
        Set<String> affectedUsers = new HashSet<String>();
        for (EmailDisabler disabler : emailDisablers) {
            affectedUsers.add(disabler.getNicHandle());
        }
        return affectedUsers.size();
    }

    public int getDisablingsCount() {
        return emailDisablers.size();
    }
}
