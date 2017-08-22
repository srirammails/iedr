package pl.nask.crs.commons.email;

import pl.nask.crs.commons.utils.Validator;

import java.util.ArrayList;
import java.util.List;

/**
 * @author pawelk, kasiaf
 */
public class EmailTemplate {

    private int id;

    private String name;
    private String text;
    private String subject;
    private List<String> toList = new ArrayList<String>();
    private List<String> ccList = new ArrayList<String>();
    private List<String> bccList = new ArrayList<String>();
    private List<String> internalToList = new ArrayList<String>();
    private List<String> internalCcList = new ArrayList<String>();
    private List<String> internalBccList = new ArrayList<String>();
    private String mailSmtpFrom;
    private boolean active;
    private boolean html;
    private boolean suppressible;
    private String sendReason;
    private String nonSuppressibleReason;
    private EmailGroup group;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        Validator.assertNotNull(name, "template name");
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public List<String> getToList() {
        return toList;
    }

    public void setToList(List<String> toList) {
        Validator.assertNotNull(toList, "toList");
        this.toList = toList;
    }

    public List<String> getCcList() {
        return ccList;
    }

    public void setCcList(List<String> ccList) {
        Validator.assertNotNull(ccList, "ccList");
        this.ccList = ccList;
    }

    public List<String> getBccList() {
        return bccList;
    }

    public void setBccList(List<String> bccList) {
        Validator.assertNotNull(bccList, "bccList");
        this.bccList = bccList;
    }

    public List<String> getInternalToList() {
        return internalToList;
    }

    public void setInternalToList(List<String> internalToList) {
        Validator.assertNotNull(internalToList, "internalToList");
        this.internalToList = internalToList;
    }

    public List<String> getInternalCcList() {
        return internalCcList;
    }

    public void setInternalCcList(List<String> internalCcList) {
        Validator.assertNotNull(internalCcList, "internalCcList");
        this.internalCcList = internalCcList;
    }

    public List<String> getInternalBccList() {
        return internalBccList;
    }

    public void setInternalBccList(List<String> internalBccList) {
        Validator.assertNotNull(internalBccList, "internalBccList");
        this.internalBccList = internalBccList;
    }

    public String getMailSmtpFrom() {
        return mailSmtpFrom;
    }

    public void setMailSmtpFrom(String mailSmtpFrom) {
        this.mailSmtpFrom = mailSmtpFrom;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

	public boolean isHtml() {
		return html;
	}

	public void setHtml(boolean html) {
		this.html = html;
	}

    public boolean isSuppressible() {
        return suppressible;
    }

    public void setSuppressible(boolean suppressible) {
        this.suppressible = suppressible;
    }

    public String getSendReason() {
        return sendReason;
    }

    public void setSendReason(String sendReason) {
        this.sendReason = sendReason;
    }

    public String getNonSuppressibleReason() {
        return nonSuppressibleReason;
    }

    public void setNonSuppressibleReason(String nonSuppressibleReason) {
        this.nonSuppressibleReason = nonSuppressibleReason;
    }

    public EmailGroup getGroup() {
        return group;
    }

    public void setGroup(EmailGroup group) {
        this.group = group;
    }

}
