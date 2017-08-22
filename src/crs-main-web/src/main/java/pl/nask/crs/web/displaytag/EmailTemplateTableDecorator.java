package pl.nask.crs.web.displaytag;

import org.apache.commons.lang.StringUtils;
import org.displaytag.decorator.TableDecorator;
import pl.nask.crs.commons.email.EmailTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class EmailTemplateTableDecorator extends TableDecorator {

    private static final String EMAILS_SEPARATOR = ", ";
    private static final String LISTS_SEPARATOR = "<br/>";

    public String getInternalRecipients() {
        EmailTemplate t = (EmailTemplate) getCurrentRowObject();
        return composeRecipientsList(t.getInternalToList(), t.getInternalCcList(), t.getInternalBccList());
    }

    public String getRecipients() {
        EmailTemplate t = (EmailTemplate) getCurrentRowObject();
        return composeRecipientsList(t.getToList(), t.getCcList(), t.getBccList());
    }

    /**
     * Creates a textual description to whom email will be sent based on to, cc and bcc lists.
     * If any list is empty is will not be included, otherwise output format is
     * <pre>
     *     to: comma-separated list of emails
     *     cc: comma-separated list of emails
     *     bcc: comma-separated list of email
     * </pre>
     * @param to
     * @param cc
     * @param bcc
     * @return
     */
    private String composeRecipientsList(List<String> to, List<String> cc, List<String> bcc) {
        List<String> l = new ArrayList<String>();

        String to_list = composeSingleAddressesLine("to", to);
        if (to_list != null) { l.add(to_list); }
        String cc_list = composeSingleAddressesLine("cc", cc);
        if (cc_list != null) { l.add(cc_list); }
        String bcc_list = composeSingleAddressesLine("bcc", bcc);
        if (bcc_list != null) { l.add(bcc_list); }

        return StringUtils.join(l, LISTS_SEPARATOR);
    }

    private String composeSingleAddressesLine(String header, List<String> emails) {
        if (emails == null || emails.isEmpty()) {
            return null;
        }
        return String.format("%s: %s", header, StringUtils.join(emails, EMAILS_SEPARATOR));
    }

}
