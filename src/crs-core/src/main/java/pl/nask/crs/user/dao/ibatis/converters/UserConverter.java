package pl.nask.crs.user.dao.ibatis.converters;

import pl.nask.crs.commons.dao.AbstractConverter;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.user.User;
import pl.nask.crs.user.dao.ibatis.objects.InternalUser;
import pl.nask.crs.user.service.AuthorizationGroupsFactory;

/**
 * @author Kasia Fulara
 */
public class UserConverter extends AbstractConverter<InternalUser, User> {

    AuthorizationGroupsFactory groupsFactory;

    public UserConverter(AuthorizationGroupsFactory groupsFactory) {
        Validator.assertNotNull(groupsFactory, "authorization groups factory");
        this.groupsFactory = groupsFactory;
    }

    protected User _to(InternalUser internalUser) {
        return new User(internalUser.getUsername(), internalUser.getPassword(), internalUser.getSalt(), internalUser.getSecret(), internalUser.getName(),
                groupsFactory.getGroups(internalUser.getLevel()), groupsFactory.getPermissionsByName(internalUser.getPermissionNames()), internalUser.isUseTwoFactorAuthentication());
    }

    protected InternalUser _from(User user) {
        return new InternalUser(user.getUsername(), user.getPassword(), user.getSalt(), user.getSecret(), user.getName(), groupsFactory.getLevel(user.getPermissionGroups()), user.isUseTwoFactorAuthentication());
    }
}
