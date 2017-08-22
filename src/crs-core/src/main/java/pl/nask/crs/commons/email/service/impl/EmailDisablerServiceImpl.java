package pl.nask.crs.commons.email.service.impl;

import org.apache.log4j.Logger;
import pl.nask.crs.commons.OpInfo;
import pl.nask.crs.commons.email.EmailDisabler;
import pl.nask.crs.commons.email.EmailGroup;
import pl.nask.crs.commons.email.EmailTemplate;
import pl.nask.crs.commons.email.dao.EmailDisablerDAO;
import pl.nask.crs.commons.email.dao.EmailTemplateDAO;
import pl.nask.crs.commons.email.dao.HistoricalEmailDisablerDAO;
import pl.nask.crs.commons.email.search.EmailDisablerKey;
import pl.nask.crs.commons.email.search.EmailDisablerSearchCriteria;
import pl.nask.crs.commons.email.search.EmailDisablerSuppressInfo;
import pl.nask.crs.commons.email.search.EmailTemplateSearchCriteria;
import pl.nask.crs.commons.email.service.*;

import java.util.*;

public class EmailDisablerServiceImpl implements EmailDisablerService {

    public static final Logger LOG = Logger.getLogger(EmailDisablerServiceImpl.class);

    EmailTemplateDAO emailTemplateDao;
    EmailDisablerDAO dao;
    HistoricalEmailDisablerDAO histDao;

    private EmailTemplateSender sender;

    public EmailDisablerServiceImpl(EmailDisablerDAO dao, HistoricalEmailDisablerDAO histDao, EmailTemplateDAO emailTemplateDao, EmailTemplateSender sender) {
        this.dao = dao;
        this.histDao = histDao;
        this.emailTemplateDao = emailTemplateDao;
        this.sender = sender;
    }

    @Override
    public boolean isTemplateDisabledForNH(int templateId, String nicHandle) {
        EmailDisabler disabler = dao.get(new EmailDisablerKey(templateId, nicHandle));
        return disabler == null || disabler.isDisabled();
    }

    @Override
    public List<EmailDisabler> findAllEmailDisablersWithAddInfoForUser(String nicHandle) {
        return dao.findWithEmailTempAndEmailGroup(nicHandle);
    }

    @Override
    public List<EmailDisabler> findAllOfTemplatesBelongingToGroup(long groupId) {
        return dao.findAllDisablingsOfTemplatesBelongingToGroup(groupId);
    }

    @Override
    public List<EmailDisabler> findAllOfTemlpate(int templateId) {
        return dao.findAllDisablingsOfTemplate(templateId);
    }

    @Override
    public List<EmailDisablerSuppressInfo> updateEmailStatus(
        List<EmailDisablerSuppressInfo> emaildisablerInfo,
        OpInfo opInfo
    ){
        List<EmailTemplate> emailTemplates = this.emailTemplateDao.findAndLockInShareMode(new EmailTemplateSearchCriteria()).getResults();
        this.dao.findAndLockForUpdate(new EmailDisablerSearchCriteria(null, opInfo.getActorName(), null));
        List<EmailDisablerSuppressInfo> suppressibleEmailDisablers = this.removeNonSuppressibleIds(emaildisablerInfo, emailTemplates);
        List<Integer> affectedEmails = new LinkedList<Integer>();
        for (EmailDisablerSuppressInfo info : suppressibleEmailDisablers) {
            affectedEmails.add(info.getEmailId());
        }

        if (!suppressibleEmailDisablers.isEmpty()) {
            this.dao.insertOrUpdate(suppressibleEmailDisablers);

            insertToHistory(affectedEmails, Collections.singletonList(opInfo.getActorName()), opInfo.getActorName());

            sendConfirmationEmail(opInfo.getActorName(), suppressibleEmailDisablers, emailTemplates);
        }
        return suppressibleEmailDisablers;
    }

    private List<EmailDisablerSuppressInfo> removeNonSuppressibleIds(
        List<EmailDisablerSuppressInfo> emaildisablerInfo, 
        List<EmailTemplate> emailTemplates
    ){

        Set<Integer> nonSuppressibleEmailTemplates = new HashSet<Integer>();
        for (EmailTemplate emailTemplate : emailTemplates) {
            if (!emailTemplate.isSuppressible()){
                nonSuppressibleEmailTemplates.add(emailTemplate.getId());
            } else {
                EmailGroup grp = emailTemplate.getGroup();
                if (grp != null && grp.getId() != EmailGroup.EMPTY_ID && !grp.getVisible()) {
                    nonSuppressibleEmailTemplates.add(emailTemplate.getId());
                }
            }
        }

        List<EmailDisablerSuppressInfo> suppressibleEmailDisablers = new LinkedList<EmailDisablerSuppressInfo>();
        for (EmailDisablerSuppressInfo emailDisablerInfo : emaildisablerInfo) {
            if (!nonSuppressibleEmailTemplates.contains(emailDisablerInfo.getEmailId())) {
                suppressibleEmailDisablers.add(emailDisablerInfo);
            }
        }
        return suppressibleEmailDisablers;
    }

    public void removeDisablingsOfTemplate(int templateId, OpInfo op) {
        List<EmailDisabler> disabled = dao.findAndLockForUpdate(new EmailDisablerSearchCriteria(templateId, null, null)).getResults();
        List<String> affectedUsers = new ArrayList<String>();
        for (EmailDisabler ed : disabled) {
            if (ed.isDisabled()) {
                affectedUsers.add(ed.getNicHandle());
            }
        }
        dao.enableTemplateForEveryone(templateId);
        insertToHistory(Collections.singletonList(templateId), affectedUsers, op.getActorName());
    }

    private void insertToHistory(List<Integer> affectedEmails, List<String> affectedUsers, String actor) {
        if(affectedEmails.isEmpty() || affectedUsers.isEmpty()) return;

        this.histDao.createForAll(affectedEmails, affectedUsers, new Date(), actor);
    }

    private void sendConfirmationEmail(String user, List<EmailDisablerSuppressInfo> changed, List<EmailTemplate> emailTemplates) {
        Map<Integer, EmailTemplate> templates = new TreeMap<Integer, EmailTemplate>();
        for (EmailTemplate t : emailTemplates) {
            templates.put(t.getId(), t);
        }

        StringBuilder builder = new StringBuilder();
        for (EmailDisablerSuppressInfo suppressInfo : changed) {
            final int emailId = suppressInfo.getEmailId();
            final EmailTemplate emailTemplate = templates.get(emailId);
            if (emailTemplate != null) {
                builder.append("Email ID: ").append(emailTemplate.getId())
                        .append(" with subject \"").append(emailTemplate.getSubject())
                        .append("\" is ").append(suppressInfo.isDisabled() ? "disabled" : "enabled")
                        .append("\n");
            }
        }

        String accountUserNH = changed.get(0).getNicHandle();
        Map<String, String> userDetails = dao.getUserDetailsByNicHandle(accountUserNH);
        String accountUserName = userDetails.get("name");
        String accountUserEmail = userDetails.get("email");
        String message = builder.toString();
        EmailDisabledEmailParameters params = new EmailDisabledEmailParameters(user, accountUserNH, accountUserEmail, accountUserName, message);
        final int templateId = EmailTemplateNamesEnum.EMAIL_DISABLER_CONFIRMATION.getId();
        try {
            sender.sendEmail(templateId, params);
        } catch (TemplateNotFoundException e) {
            LOG.error("Unknown email template " + templateId, e);
        } catch (TemplateInstantiatingException e) {
            LOG.error("Cannot instantiate email template", e);
        } catch (EmailSendingException e) {
            LOG.error("Error sending email", e);
        }
    }
}
