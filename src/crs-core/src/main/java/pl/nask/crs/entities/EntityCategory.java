package pl.nask.crs.entities;

/**
 * It represents a category of an entity being a domain holder (true?). A
 * category is another discriminator in addition to a class of an entity. It is
 * correlated with a class of an enity - each class has a set of its available
 * categories.
 * <p/>
 * The dictionary of all available EntityCategory instances is stored in a
 * database. This class must not be instantiated by hand, instead the factory
 * must be used.
 * <p/>
 * todo: private, protected, or package access to the constructor - how to deal
 * with iBATIS?
 *
 * @author Patrycja Wegrzynowicz
 */
public interface EntityCategory {

    public long getId();

    public String getName();
}
