package pl.nask.crs.ticket.exceptions;

import pl.nask.crs.contacts.Contact;

/**
 * @author Patrycja Wegrzynowicz
 */
public class TicketAlreadyCheckedOutException extends TicketException {

    private Contact contact;

    public TicketAlreadyCheckedOutException(Contact contact) {
        this.contact = contact;
    }

    public Contact getContact() {
        return contact;
    }

}
