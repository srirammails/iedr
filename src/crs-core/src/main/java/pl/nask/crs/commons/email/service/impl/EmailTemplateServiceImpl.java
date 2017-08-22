package pl.nask.crs.commons.email.service.impl;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.log4j.Logger;
import pl.nask.crs.commons.OpInfo;
import pl.nask.crs.commons.email.EmailGroup;
import pl.nask.crs.commons.email.EmailTemplate;
import pl.nask.crs.commons.email.dao.EmailGroupDAO;
import pl.nask.crs.commons.email.dao.EmailTemplateDAO;
import pl.nask.crs.commons.email.dao.HistoricalEmailTemplateDAO;
import pl.nask.crs.commons.email.search.EmailTemplateSearchCriteria;
import pl.nask.crs.commons.email.service.EmailDisablerService;
import pl.nask.crs.commons.email.service.EmailSendingException;
import pl.nask.crs.commons.email.service.EmailTemplateNamesEnum;
import pl.nask.crs.commons.email.service.EmailTemplateSender;
import pl.nask.crs.commons.email.service.EmailTemplateService;
import pl.nask.crs.commons.email.service.MapBasedEmailParameters;
import pl.nask.crs.commons.email.service.ParameterNameEnum;
import pl.nask.crs.commons.email.service.TemplateInstantiatingException;
import pl.nask.crs.commons.email.service.TemplateNotFoundException;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SearchResult;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.history.HistoricalObject;

import java.util.Date;
import java.util.List;

public class EmailTemplateServiceImpl implements EmailTemplateService {

    public static final Logger LOG = Logger.getLogger(EmailTemplateServiceImpl.class);

    private EmailTemplateDAO emailDao;
    private EmailGroupDAO groupDao;
    private HistoricalEmailTemplateDAO historicalTemplateDao;
    private EmailDisablerService disablerService;
    private EmailTemplateSender sender;

    public EmailTemplateServiceImpl(EmailTemplateDAO dao, EmailGroupDAO groupDao, HistoricalEmailTemplateDAO historicalDao, EmailDisablerService disablerService, EmailTemplateSender sender) {
        this.emailDao = dao;
        this.groupDao = groupDao;
        this.historicalTemplateDao = historicalDao;
        this.disablerService = disablerService;
        this.sender = sender;
    }

    @Override
    public LimitedSearchResult<EmailTemplate> findEmailTemplate(EmailTemplateSearchCriteria criteria, long offset, long limit, List<SortCriterion> orderBy) {
        return emailDao.find(criteria, offset, limit, orderBy);
    }

    @Override
    public SearchResult<EmailTemplate> findEmailTemplate(EmailTemplateSearchCriteria criteria) {
        return emailDao.find(criteria);
    }

    @Override
    public EmailTemplate getEmailTemplate(int id) {
        return emailDao.get(id);
    }

    @Override
    public EmailTemplate save(EmailTemplate template, OpInfo op) {
        emailDao.lock(template.getId());
        EmailTemplate dbTemplate = emailDao.get(template.getId());

        long groupId   = template.getGroup() != null ? template.getGroup().getId() : EmailGroup.EMPTY_ID;
        if (groupId == EmailGroup.EMPTY_ID) {
            template.setGroup(null);
        }
        if (areDifferent(template, dbTemplate)) {
            emailDao.update(template);

            boolean alreadyForceEnabled = false;
            if (template.isSuppressible() != dbTemplate.isSuppressible() && !template.isSuppressible()) {
                // got turned off, need to clean all present emailDisablers
                disablerService.removeDisablingsOfTemplate(template.getId(), op);
                alreadyForceEnabled = true;
            }
            if (!alreadyForceEnabled && template.getGroup() != null) {
                EmailGroup grp = groupDao.get(template.getGroup().getId());
                if (grp != null && !grp.getVisible()) {
                    // set to a group that is invisible, remove all disablings
                    disablerService.removeDisablingsOfTemplate(template.getId(), op);
                }
            }

            insertToHistory(template, op.getActorName());

            sendNotification(op.getActorName(), template, dbTemplate);
        }
        return template;
    }

    @Override
    public void onGroupDeleted(EmailGroup group, OpInfo op) {
        // update all templates that belonged to given group
        EmailTemplateSearchCriteria criteria = new EmailTemplateSearchCriteria();
        criteria.setGroupId(group.getId());
        List<EmailTemplate> affected = emailDao.findAndLockForUpdate(criteria).getResults();
        final String actorName = op.getActorName();
        for (EmailTemplate template : affected) {
            template.setGroup(null);
            emailDao.update(template);
            insertToHistory(template, actorName);
        }
    }

    private boolean areDifferent(EmailTemplate a, EmailTemplate b) {
        return getGroupId(a) != getGroupId(b)
                || a.isSuppressible() != b.isSuppressible()
                || !safeString(a.getSendReason()).equals(safeString(b.getSendReason()))
                || !safeString(a.getNonSuppressibleReason()).equals(safeString(b.getNonSuppressibleReason()));
    }

    private long getGroupId(EmailTemplate template) {
        return template.getGroup() != null ? template.getGroup().getId() : EmailGroup.EMPTY_ID;
    }

    private void insertToHistory(EmailTemplate template, String actor) {
        historicalTemplateDao.create(new HistoricalObject<EmailTemplate>(template, new Date(), actor));
    }

    private void sendNotification(String user, EmailTemplate curr, EmailTemplate prev) {
        MapBasedEmailParameters params = new MapBasedEmailParameters();
        params.set(ParameterNameEnum.NIC_NAME, user);
        params.set(ParameterNameEnum.DATE, DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
        params.set(ParameterNameEnum.INFO, makeInfo(curr, prev));
        final int templateId = EmailTemplateNamesEnum.EMAIL_TEMPLATE_CHANGE_NOTIFICATION.getId();
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

    private String getGroupName(EmailTemplate tpl) {
        if (tpl.getGroup() == null) {
            return "none";
        } else {
            String name = tpl.getGroup().getName();
            if (name == null) {
                EmailGroup group = groupDao.get(tpl.getGroup().getId());
                if (group != null) {
                    name = group.getName();
                } else {
                    LOG.error("Email template set with non-existing group");
                    name = "unknown";
                }
            }
            return name;
        }
    }

    private String safeString(String in) {
        if (in == null)
            return "";
        else
            return in;
    }

    private String makeInfo(EmailTemplate curr, EmailTemplate prev) {
        StringBuilder builder = new StringBuilder();
        builder.append("Email template with E_ID=").append(curr.getId()).append(" edit:\n");
        if (getGroupId(curr) != getGroupId(prev)) {
            builder.append("Group changed from \"").append(getGroupName(prev)).append("\" to \"").append(getGroupName(curr)).append("\"\n");
        }
        if (prev.isSuppressible() != curr.isSuppressible()) {
            builder.append("Is suppressible from \"").append(prev.isSuppressible() ? "Y" : "N").append("\" to \"").append(curr.isSuppressible() ? "Y" : "N").append("\"\n");
        }
        final String prevSendReason = safeString(prev.getSendReason());
        final String currSendReason = safeString(curr.getSendReason());
        if (!prevSendReason.equals(currSendReason)) {
            builder.append("Send reason from \"").append(prevSendReason).append("\" to \"").append(currSendReason).append("\"\n");
        }

        final String prevNonSuppressibleReason = safeString(prev.getNonSuppressibleReason());
        final String currNonSuppressibleReason = safeString(curr.getNonSuppressibleReason());
        if (!prevNonSuppressibleReason.equals(currNonSuppressibleReason)) {
            builder.append("Non suppressible reason from \"").append(prevNonSuppressibleReason).append("\" to \"").append(currNonSuppressibleReason).append("\"\n");
        }
        return builder.toString();
    }
}
