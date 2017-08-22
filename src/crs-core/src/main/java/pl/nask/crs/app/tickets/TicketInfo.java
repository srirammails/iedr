package pl.nask.crs.app.tickets;

import java.util.Collection;
import java.util.Collections;
import java.util.TreeSet;

import pl.nask.crs.payment.PaymentMethod;

/**
 * @author Patrycja Wegrzynowicz
 */
public class TicketInfo {

    private String domainName;

    private String ticketHolder;

    private String previousHolder;

    private Collection<String> relatedDomainNames;
    
    private Collection<String> pendingDomainNames;

    private boolean documents;
    
    // payment method for the registration ticket
    private PaymentMethod paymentMethod;

    public TicketInfo() {
        this(null, null, null, null, null);
    }

    public TicketInfo(String domainName, String ticketHolder, String previousHolder, Collection<String> relatedDomainNames, Collection<String> pendingDomainNames) {
        this(domainName, ticketHolder, previousHolder, relatedDomainNames, pendingDomainNames, false, null);
    }

    public TicketInfo(String domainName, String ticketHolder, String previousHolder, Collection<String> relatedDomainNames, Collection<String> pendingDomainNames, boolean faxes, PaymentMethod paymentMethod) {
        this.domainName = domainName;
        this.ticketHolder = ticketHolder;
        this.previousHolder = previousHolder;
        this.pendingDomainNames = pendingDomainNames == null ? Collections.EMPTY_SET : new TreeSet<String>(pendingDomainNames);
        this.relatedDomainNames = relatedDomainNames == null ? Collections.EMPTY_SET : new TreeSet<String>(relatedDomainNames);
        this.documents = faxes;
        this.paymentMethod = paymentMethod;
    }

    public String getDomainName() {
        return domainName;
    }

    public String getTicketHolder() {
        return ticketHolder;
    }

    public String getPreviousHolder() {
        return previousHolder;
    }

    public Collection<String> getRelatedDomainNames() {
        return relatedDomainNames;
    }

    // feature #1223 - display owned and new (pending) domain names in different colour schemes
    public String getRelatedDomainNamesAsString() {
        StringBuilder ret = new StringBuilder();
        if (relatedDomainNames.isEmpty() && pendingDomainNames.isEmpty())
            return "";
        
        ret.append("<table>");
        appendDomainNames(relatedDomainNames, ret, null);
        
                
        if (!pendingDomainNames.isEmpty()) {
            appendDomainNames(pendingDomainNames, ret, "#C72222");                        
        }
        ret.append("</table>");
        return ret.toString();
    }
    
    

    private void appendDomainNames(Collection<String> domainNames, StringBuilder ret, String colorCode) {
        if (domainNames.isEmpty())
            return;
        int i = 0;
        String openTd = "<td>";
        if (colorCode != null)
            openTd = "<td style=\"color:" + colorCode + ";\"/>";
        
        ret.append("<tr>");
        for (String rdn : domainNames) {
            i++;
            if (i % 3 == 1)
                ret.append("<tr>");            
            ret.append(openTd).append(rdn).append("</td>");            
            
            if (i % 3 == 0)
                ret.append("</tr>");            
        }
        
        if (i % 3 != 0)
            ret.append("</tr>");        
    }

    public boolean isDocuments() {
        return documents;
    }

    public Collection<String> getPendingDomainNames() {
        return pendingDomainNames;
    }
    
    public PaymentMethod getPaymentMethod() {
		return paymentMethod;
	}
}
