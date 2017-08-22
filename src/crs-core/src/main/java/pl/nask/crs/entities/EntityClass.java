package pl.nask.crs.entities;


/**
 * It represents a class of an entity being a domain holder (true?).
 * <p/>
 * The dictionary of all available EntityClass instances is stored in a
 * database. This class must not be instantiated by hand, instead the factory
 * must be used.
 * <p/>
 * todo: private, protected, or package access to the constructor - how to deal
 * with iBATIS?
 *
 * @author Patrycja Wegrzynowicz
 */
public interface EntityClass {

    public long getId();

    public String getName();
}
