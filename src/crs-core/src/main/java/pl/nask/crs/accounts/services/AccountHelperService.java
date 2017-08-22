package pl.nask.crs.accounts.services;

import pl.nask.crs.accounts.Account;
import pl.nask.crs.accounts.exceptions.AccountNotFoundException;
import pl.nask.crs.nichandle.NicHandle;

public interface AccountHelperService {
	NicHandle getIEDRAccount();

	String getVatNo(long accountNumber) throws AccountNotFoundException;
}
