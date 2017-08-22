package pl.nask.crs.contacts.dao.ibatis.converters;

import pl.nask.crs.commons.dao.AbstractConverter;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.contacts.Contact;
import pl.nask.crs.contacts.dao.ibatis.objects.InternalContact;

/**
 * @author Patrycja Wegrzynowicz
 * @author Kasia Zimnoch
 */
public class ContactConverter extends AbstractConverter<InternalContact, Contact> {

    private String type;

    public ContactConverter() {
    }

    public ContactConverter(String type) {
        this.type = type;
    }

    protected Contact _to(InternalContact src) {
        return new Contact(
                src.getNicHandle(),
                src.getName(),
                src.getEmail(),
                src.getCompanyName(),
                src.getCountry(),
                src.getCounty());
    }

    protected InternalContact _from(Contact contact) {
        InternalContact ret = new InternalContact();
        ret.setNicHandle(contact.getNicHandle());
        ret.setName(contact.getName());
        ret.setEmail(contact.getEmail());
        Validator.assertNotEmpty(type, "internal contact type");
        ret.setType(type);
        ret.setCompanyName(contact.getCompanyName());
        ret.setCountry(contact.getCountry());
        ret.setCounty(contact.getCounty());
        return ret;
    }
}
