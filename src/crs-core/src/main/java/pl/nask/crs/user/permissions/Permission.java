package pl.nask.crs.user.permissions;

/**
 * @author Patrycja Wegrzynowicz
 */
public interface Permission {

    String getName();

    /**
     * @return the unique permission ID
     */
    String getId();
    /**
     * 
     * @param permission
     * 
     * @return true if the permission implies the permission given as a parameter, false if it doesn't.
     * 
     * @throws PermissionDeniedException if the permission implies the opposite (case in which the permission directly prohibits the operation to be performed).
     * Used for indicating, what is the cause of preventing the user from performing the action.
     */
    boolean implies(PermissionQuery permission) throws PermissionDeniedException;
    
    /**
     * returns a human-readable description of this permission
     * 
     * @return
     */
    String getDescription();

}
