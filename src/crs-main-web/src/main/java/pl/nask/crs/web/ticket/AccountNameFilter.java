package pl.nask.crs.web.ticket;

import com.opensymphony.xwork2.ActionSupport;

import java.util.HashMap;
import java.util.Map;

/*
 * for the autocompleter
 */
public class AccountNameFilter extends ActionSupport {

    //temporal implementation
    public Map<String, String> getAccountNames() {
        Map<String, String> m = new HashMap<String, String>();
        String n = null;
        if (ticket != null) {
            n = ticket.get("accountName");
        }
        if (n != null) {
            n = n.toLowerCase();
        }

        add(m, n, "Account name 1", "name1");
        add(m, n, "Second account name", "name2");
        add(m, n, "Third name", "name3");
        add(m, n, "Another name", "name4");
        add(m, n, "And another name", "name5");
        return m;
    }

    private void add(Map<String, String> m, String filter, String key, String value) {

        if (filter == null || key.toLowerCase().startsWith(filter))
            m.put(key, value);
    }

    private Map<String, String> ticket;

    public Map<String, String> getTicket() {
        return ticket;
    }

    public void setTicket(Map<String, String> ticket) {
        this.ticket = ticket;
    }
}
