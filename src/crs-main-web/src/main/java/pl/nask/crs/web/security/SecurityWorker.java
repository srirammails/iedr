package pl.nask.crs.web.security;

import net.sf.navigator.menu.MenuComponent;

import org.apache.log4j.Logger;

import com.opensymphony.xwork2.ActionContext;

/**
 * @author Artur Gniadzik
 */
public class SecurityWorker {
    private static final SecurityWorker instance = new SecurityWorker();
    private Logger log = Logger.getLogger(SecurityWorker.class);


    private SecurityWorker() {
    }

    public static SecurityWorker getInstance() {
        return instance;
    }


    /**
     * Checks, if the user has permissions to run the action.
     *
     * @param userName user name
     * @param ctx      action context
     * @return
     */
    public boolean hasPermission(String username, ActionContext ctx) {
        return username != null;
    }

    /**
     * Checks, if the user has permissions to access the menu component
     *
     * @param userName      user name
     * @param menuComponent menu component
     * @return
     */
    public boolean hasPermission(String username, MenuComponent menuComponent) {
        return username != null;
    }


}
