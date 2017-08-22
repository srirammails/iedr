package pl.nask.crs.app.emaildisabler.impl;

import java.util.List;

import pl.nask.crs.app.emaildisabler.EmailDisablerAppService;
import pl.nask.crs.commons.OpInfo;
import pl.nask.crs.commons.email.EmailDisabler;
import pl.nask.crs.commons.email.search.EmailDisablerSuppressInfo;
import pl.nask.crs.commons.email.service.EmailDisablerService;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.security.authentication.AuthenticatedUser;

import org.springframework.transaction.annotation.Transactional;

public class EmailDisablerAppServiceImpl implements EmailDisablerAppService {

    private final EmailDisablerService emailDisablerService;

    public EmailDisablerAppServiceImpl(EmailDisablerService emailDisablerService){
        this.emailDisablerService = emailDisablerService;
    }

    @Override
    public List<EmailDisabler> getAllFor(AuthenticatedUser user)
            throws AccessDeniedException {
        return emailDisablerService.findAllEmailDisablersWithAddInfoForUser(user.getUsername());
    }

    @Override
    public List<EmailDisabler> getAllOfEmailGroup(AuthenticatedUser user, long groupId)
            throws AccessDeniedException {
        return emailDisablerService.findAllOfTemplatesBelongingToGroup(groupId);
    }

    @Override
    public List<EmailDisabler> getAllOfTemplate(AuthenticatedUser user, int templateId)
            throws AccessDeniedException {
        return emailDisablerService.findAllOfTemlpate(templateId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<EmailDisablerSuppressInfo> modifySuppressionMode(
        AuthenticatedUser user,
        List<EmailDisablerSuppressInfo> emaildisablerInfo
    ){
    	OpInfo opInfo = new OpInfo(user);
        return this.emailDisablerService.updateEmailStatus(emaildisablerInfo, opInfo);
    }

}
