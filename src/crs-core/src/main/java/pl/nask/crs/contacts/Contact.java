package pl.nask.crs.contacts;

import pl.nask.crs.commons.utils.Validator;

/**
 * It represents an entity identified by a nic handle.
 *
 * @author Patrycja Wegrzynowicz
 * @author Kasia Fulara
 */
public class Contact {

    private String nicHandle;

    private String name;

    private String email;
    
    private String companyName;

    private String country;

    private String county;

    public Contact() {
    }

    public Contact(String nicHandle) {
        this(nicHandle, null);
    }

    public Contact(String nicHandle, String name) {
        Validator.assertNotNull(nicHandle, "nic handle");
        this.nicHandle = nicHandle;
        this.name = name;
    }

    public Contact(String nicHandle, String name, String email) {
        this.nicHandle = nicHandle;
        this.name = name;
        this.email = email;
    }

    public Contact(String nicHandle, String name, String email, String companyName, String country, String county) {
        this.nicHandle = nicHandle;
        this.name = name;
        this.email = email;
        this.companyName = companyName;
        this.country = country;
        this.county = county;
    }

    /**
     * Returns the nic handle of this contact. A nic handle is a unique identifier of a contact.
     *
     * @return the nic handle of the contact; never null
     */
    public String getNicHandle() {
        return nicHandle;
    }


    /**
     * Returns the name of the contact - more verbose description of the contact.
     *
     * @return the name of the contact.
     */
    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getCountry() {
        return country;
    }

    public String getCounty() {
        return county;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Contact)) return false;

        Contact contact = (Contact) o;

        if (nicHandle != null ? !nicHandle.equals(contact.nicHandle) : contact.nicHandle != null) return false;

        return true;
    }

    public int hashCode() {
        int result;
        result = (nicHandle != null ? nicHandle.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return String.format("Contact[NH=%s, name=%s]", nicHandle, name);
    }
}
