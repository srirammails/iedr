package pl.nask.crs.payment;

import pl.nask.crs.commons.search.SearchCriteria;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public class DepositInfoSearchCriteria implements SearchCriteria<DepositInfo> {

    private String nicHandleId;
    private String accountBillNH;

    public String getNicHandleId() {
        return nicHandleId;
    }

    public void setNicHandleId(String nicHandleId) {
        this.nicHandleId = nicHandleId;
    }

    public String getAccountBillNH() {
        return accountBillNH;
    }

    public void setAccountBillNH(String accountBillNH) {
        this.accountBillNH = accountBillNH;
    }
}
