package pl.nask.crs.web.menu;

import net.sf.navigator.menu.MenuComponent;
import net.sf.navigator.menu.PermissionsAdapter;
import pl.nask.crs.app.authorization.PermissionAppService;
import pl.nask.crs.security.authentication.AuthenticatedUser;

/**
 * @author Pawel pixel Kleniewski
 * @author Kasia Fulara
 */

public class MenuAdapter implements PermissionsAdapter {

    public static final String MENU_KEY = "menu-key";

    private AuthenticatedUser user;
    private PermissionAppService permissionAppService;

    public MenuAdapter(AuthenticatedUser user, PermissionAppService permissionAppService) {
        this.user = user;
        this.permissionAppService = permissionAppService;
    }

    public boolean isAllowed(MenuComponent menuComponent) {
        // no menu if there is no user or no permission service
        if (user == null || permissionAppService == null)
            return false;
        return permissionAppService.hasPermission(user, menuComponent.getName());

    }
}
