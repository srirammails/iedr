package pl.nask.crs.user.dao.ibatis.converters;

import java.util.Collections;

import pl.nask.crs.commons.dao.AbstractConverter;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.history.HistoricalObject;
import pl.nask.crs.user.User;
import pl.nask.crs.user.dao.ibatis.objects.InternalHistoricalUser;
import pl.nask.crs.user.service.AuthorizationGroupsFactory;

public class HistoricalUserConverter extends AbstractConverter<InternalHistoricalUser, HistoricalObject<User>> {

    AuthorizationGroupsFactory groupsFactory;

    public HistoricalUserConverter(AuthorizationGroupsFactory groupsFactory) {
        Validator.assertNotNull(groupsFactory, "authorization groups factory");
        this.groupsFactory = groupsFactory;
    }
    
    @Override
    protected InternalHistoricalUser _from(HistoricalObject<User> dst) {
        InternalHistoricalUser u = new InternalHistoricalUser();
        u.setChangeDate(dst.getChangeDate());
        u.setChangedBy(dst.getChangedBy());
        u.setUsername(dst.getObject().getUsername());
        u.setPassword(dst.getObject().getPassword());
        u.setLevel(groupsFactory.getLevel(dst.getObject().getPermissionGroups()));
        u.setUseTwoFactorAuthentication(dst.getObject().isUseTwoFactorAuthentication());
        return u;
    }

    @Override
    protected HistoricalObject<User> _to(InternalHistoricalUser src) {
        User u = new User(src.getUsername(), src.getPassword(), src.getSalt(), src.getSecret(), null, groupsFactory.getGroups(src.getLevel()), Collections.EMPTY_MAP, src.isUseTwoFactorAuthentication());
        HistoricalObject<User> o = new HistoricalObject<User>(
                src.getChangeId(),
                u,
                src.getChangeDate(),
                src.getChangedBy());
        
        return o;
    }

}
