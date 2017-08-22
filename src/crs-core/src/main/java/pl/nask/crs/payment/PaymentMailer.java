package pl.nask.crs.payment;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import pl.nask.crs.commons.email.Email;
import pl.nask.crs.commons.email.service.EmailSender;
import pl.nask.crs.commons.email.service.EmailSendingException;

/**
 * @author: Marcin Tkaczyk
 */
public class PaymentMailer {

    private EmailSender emailSender;
    private String signature = "<br>Kind Regards<br>Accounts<br>IE Domain Registry Ltd<br>Ph: +353 1 2365422<br>Fax: +353 1 2300871<br>Registered Office: Harbour Square, Block 2, 4th Floor, Dun Laoghaire, Co. Dublin.<br>Registered in Ireland. No: 315315";
    private final static Logger log = Logger.getLogger(PaymentMailer.class);

    public PaymentMailer(EmailSender emailSender) {
        this.emailSender = emailSender;
    }

    public void sendTopUpEmail(OldPaymentEmailParameters params) {
        StringBuilder body = new StringBuilder(1000);
        body.append("<html><body>");
        body.append("Dear ").append(params.getAccountName());
        body.append(",<br><br>Nic-Handle : ").append(params.getNicHandleId());
        body.append("<br><br>You have successfully lodged ").append(params.getAmount()).append(" Euro to your deposit account using your credit card.<br>");
        body.append("Your current deposit balance is: ").append(params.getCloseBalance()).append(" Euro.<br>");
        body.append("The Order ID of the transaction was: ").append(params.getOrderId()).append(".<br>");
        body.append(signature).append("</body></html>");
        sendMail(body.toString(), params);
    }

    private void sendMail(String body, OldPaymentEmailParameters params) {
        Email email = new Email();
        email.setText(body);
        email.setSubject(params.getSubject());

        List<String> toList = new ArrayList<String>();
        toList.add(params.getEmail());
        email.setToList(toList);

        List<String> bccList = new ArrayList<String>();
        if (params.getBcc() != null) {
            bccList.add(params.getBcc());
        }
        email.setBccList(bccList);

        try {
            emailSender.sendHtmlEmail(email);
        } catch (EmailSendingException e) {
            log.error("Error sending email: " + e, e);
        }
        log.info("Email sent to: " + params.getEmail());
        log.info("Email content : \n" + body);
    }
}
