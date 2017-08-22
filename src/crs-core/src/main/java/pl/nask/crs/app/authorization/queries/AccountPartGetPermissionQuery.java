package pl.nask.crs.app.authorization.queries;

import pl.nask.crs.user.permissions.NamedPermissionQuery;

/**
 * (C) Copyright 2011 NASK
 * Software Research & Development Department
 */
public class AccountPartGetPermissionQuery extends NamedPermissionQuery {

    private final long accountId;
    private final String nicHandleId;

    public AccountPartGetPermissionQuery(String name, long accountId, String nicHandleId) {
        super(name);
        this.accountId = accountId;
        this.nicHandleId = nicHandleId;
    }

    public long getAccountId() {
        return accountId;
    }

    public String getNicHandleId() {
        return nicHandleId;
    }
}
