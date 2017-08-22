package pl.nask.crs.commons.email.service.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.log4j.Logger;

import pl.nask.crs.commons.email.Email;
import pl.nask.crs.commons.email.service.EmailSender;
import pl.nask.crs.commons.email.service.EmailSendingException;
import pl.nask.crs.commons.utils.Validator;

import com.sun.mail.smtp.SMTPTransport;

/**
 * @author Kasia Fulara, Jakub Laszkiewicz
 */
public class EmailSenderImpl implements EmailSender {

    private final static Logger LOG = Logger.getLogger(EmailSenderImpl.class);

    private String mailer;
    private String mailhost;
    private Integer port;
    private String userName;
    private String userPassword;
    private boolean ssl;
    private boolean tls;
    private String mailSmtpFrom;
    private long timeout = 10000;

    //TODO move it to properties
    private String specialBccField;

    public EmailSenderImpl(String mailer, String mailhost, Integer port, String userName, String userPassword, String from) {
        this(mailer, mailhost, port, userName, userPassword, false, false, from);
    }

    public EmailSenderImpl(String mailer, String mailhost, Integer port, String userName, String userPassword, String from, String specialBccField) {
        this(mailer, mailhost, port, userName, userPassword, false, false, from);
        this.specialBccField = specialBccField;
    }

    public EmailSenderImpl(String mailer, String mailhost, String userName, String userPassword, String from) {
        this(mailer, mailhost, null, userName, userPassword, false, false, from);
    }

    public EmailSenderImpl(String mailer, String mailhost, String userName, String userPassword, String from, String specialBccField) {
        this(mailer, mailhost, null, userName, userPassword, false, false, from);
        this.specialBccField = specialBccField;
    }

    public EmailSenderImpl(String mailer, String mailhost, Integer port,
                           String userName, String userPassword, boolean ssl, boolean tls, String mailSmtpFrom) {
//        Validator.assertNotEmpty(mailer, "mailer");
        Validator.assertNotEmpty(mailhost, "mailhost");
        Validator.assertNotNull(ssl, "ssl");
        Validator.assertNotNull(tls, "tls");
        Validator.assertNotNull(mailSmtpFrom, "from");
        this.mailer = mailer;
        this.mailhost = mailhost;
        this.port = port;
        this.userName = userName;
        this.userPassword = userPassword;
        this.ssl = ssl;
        this.tls = tls;
        this.mailSmtpFrom = mailSmtpFrom;
    }

    /**
     * JNDI workaround (problem with null entry value)
     *
     * @param port
     */
    public void setPort(String port) {
        if(Validator.isEmpty(port)) {
            this.port = null;
        } else {
            this.port = Integer.valueOf(port);
        }
    }

    private String getProtocol() {
        return ssl ? "smtps" : "smtp";
    }

    public void sendEmail(Email email) throws IllegalArgumentException, EmailSendingException {
    	addSpecialBcc(email);
        String parsedTo = createAddresseeList(email.getToList());
        String parsedCC = createAddresseeList(email.getCcList());
        String parsedBcc = createAddresseeList(email.getBccList());
        String from = Validator.isEmpty(email.getMailSmtpFrom()) ? mailSmtpFrom : email.getMailSmtpFrom();
        try {
            send(from, parsedTo, parsedCC, parsedBcc, email.getSubject(), email.getText(), false, email.getAttachments());
        } catch (MessagingException e) {
            Logger.getLogger(EmailSenderImpl.class).error(e);
            throw new EmailSendingException("Mail params : mailer=" + mailer +", mailhost=" + mailhost + ", username=" + userName + ", userPassword=" + userPassword, e);
        } catch (FileNotFoundException e) {
            Logger.getLogger(EmailSenderImpl.class).error(e);
            throw new EmailSendingException(e);
        }
        LOG.debug("Sent email: " + email);
    }

    private void addSpecialBcc(Email email) {
        if (specialBccField != null) {
            List<String> newBccList = new ArrayList<String>(email.getBccList());
            newBccList.add(specialBccField);
            email.setBccList(newBccList);
        }
    }

    @Override
    public void sendHtmlEmail(Email email) throws IllegalArgumentException, EmailSendingException {
        addSpecialBcc(email);
        String parsedTo = createAddresseeList(email.getToList());
        String parsedCC = createAddresseeList(email.getCcList());
        String parsedBcc = createAddresseeList(email.getBccList());
        String from = email.getMailSmtpFrom() == null ? mailSmtpFrom : email.getMailSmtpFrom();
        try {
            send(from, parsedTo, parsedCC, parsedBcc, email.getSubject(), email.getText(), true, email.getAttachments());
        } catch (MessagingException e) {
            Logger.getLogger(EmailSenderImpl.class).error(e);
            throw new EmailSendingException("Mail params : mailer=" + mailer +", mailhost=" + mailhost + ", username=" + userName + ", userPassword=" + userPassword, e);
        } catch (FileNotFoundException e) {
            Logger.getLogger(EmailSenderImpl.class).error(e);
            throw new EmailSendingException(e);
        }
        LOG.debug("Sent email: " + email);
    }

    private void send(String from, String to, String cc, String bcc, String subject, String body, boolean isHtml, List<File> files) throws MessagingException, FileNotFoundException {
        Session session = init();
        MimeMessage message = createMessage(session, from, to, cc, bcc, subject);
        Multipart multipart = new MimeMultipart();
        addTextMessage(multipart, body, isHtml);
        addAttachments(multipart, files);
        message.setContent(multipart);
        sendMessage(session, message);
    }

    private void addTextMessage(final Multipart multipart, String body, boolean isHtml) throws MessagingException {
        BodyPart bodyPart = new MimeBodyPart();
        if (isHtml) {
            bodyPart.setContent(body, "text/html");
        } else {
            bodyPart.setText(body);
        }
        multipart.addBodyPart(bodyPart);
    }

    private void addAttachments(final Multipart multipart, List<File> files) throws MessagingException, FileNotFoundException {
        if (!Validator.isEmpty(files)) {
            BodyPart bodyPart = null;
            for (File file : files) {
                if (!file.isFile()) {
                    throw new FileNotFoundException(file.getAbsolutePath());
                }
                bodyPart = new MimeBodyPart();
                FileDataSource fileDataSource = new FileDataSource(file);
                bodyPart.setDataHandler(new DataHandler(fileDataSource));
                bodyPart.setFileName(file.getName());
                multipart.addBodyPart(bodyPart);
            }
        }
    }

    private String createAddresseeList(List<String> addresseeList) {
        StringBuilder ret = new StringBuilder();
        for (String addresse : addresseeList) {
            ret.append(addresse).append(", ");
        }
        int len = ret.length();
        if (len > 0) {
            ret.deleteCharAt(len - 1);
        }
        return ret.toString();
    }

    private void sendMessage(Session session, Message message) throws MessagingException {
        SMTPTransport transport = (SMTPTransport) session.getTransport(getProtocol());
        try {
            if (isUser()) {
                if (port != null)
                    transport.connect(mailhost, port, userName, userPassword);
                else
                    transport.connect(mailhost, userName, userPassword);
            } else
                transport.connect();
            transport.sendMessage(message, message.getAllRecipients());
        } finally {
            transport.close();
        }
    }

    private MimeMessage createMessage(Session session,
                                      String from,
                                      String to,
                                      String cc,
                                      String bcc,
                                      String subject) throws MessagingException {
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(from));
        message.addRecipients(Message.RecipientType.TO, InternetAddress.parse(to, true));
        message.addRecipients(Message.RecipientType.CC, InternetAddress.parse(cc, true));
        message.addRecipients(Message.RecipientType.BCC, InternetAddress.parse(bcc, true));
        message.setSubject(subject);
        message.setHeader("X-Mailer", mailer);
        message.setSentDate(new Date());
        return message;
    }

    private Session init() {
        Properties props = System.getProperties();
        props.put("mail." + getProtocol() + ".host", mailhost);

        if (mailSmtpFrom != null) {
            props.put("mail.smtp.from", mailSmtpFrom);
        }
        if (port != null) {
            props.put("mail." + getProtocol() + ".port", port.toString());
        }
        if (isUser()) {
            props.put("mail." + getProtocol() + ".auth", "true");
        }

        props.put("mail." + getProtocol() + ".starttls.enable", String.valueOf(tls));
        if (!props.containsKey("mail.smtp.connectiontimeout")) {
        	props.put("mail.smtp.connectiontimeout", String.valueOf(timeout));
        }
        Session session = Session.getInstance(props);
        return session;
    }

    private boolean isUser() {
        return userName != null || userPassword != null;
    }
    
    @Override
    public String toString() {
    	return "EmailSender[mailhost=" + mailhost + "]";
    }
}
