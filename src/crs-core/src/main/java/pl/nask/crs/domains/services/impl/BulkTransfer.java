package pl.nask.crs.domains.services.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import pl.nask.crs.accounts.Account;
import pl.nask.crs.accounts.services.AccountSearchService;
import pl.nask.crs.commons.OpInfo;
import pl.nask.crs.contacts.Contact;
import pl.nask.crs.defaults.ResellerDefaults;
import pl.nask.crs.defaults.ResellerDefaultsService;
import pl.nask.crs.defaults.exceptions.DefaultsNotFoundException;
import pl.nask.crs.domains.Domain;
import pl.nask.crs.domains.NRPStatus;
import pl.nask.crs.domains.dao.BulkTransferDAO;
import pl.nask.crs.domains.dsm.DomainStateMachine;
import pl.nask.crs.domains.dsm.events.TransferToRegistrar;
import pl.nask.crs.domains.exceptions.DomainNotFoundException;
import pl.nask.crs.domains.services.BulkTransferException;
import pl.nask.crs.domains.services.BulkTransferValidationException;
import pl.nask.crs.domains.services.DomainSearchService;
import pl.nask.crs.domains.services.DomainService;
import pl.nask.crs.domains.transfer.BulkTransferRequest;
import pl.nask.crs.domains.transfer.TransferredDomain;
import pl.nask.crs.nichandle.NicHandle;
import pl.nask.crs.nichandle.exception.NicHandleNotFoundException;
import pl.nask.crs.nichandle.service.NicHandleSearchService;
import pl.nask.crs.nichandle.service.NicHandleService;
import pl.nask.crs.security.authentication.AuthenticatedUser;

public class BulkTransfer {
	// id of the bulk transfer object in the database 
	private final long bulkTransferId;
	private BulkTransferRequest transferRequest;
	
	private List<Domain> validatedDomains = new ArrayList<Domain>();
	
	private List<String> validationErrors = new ArrayList<String>();

	private DomainService domainService;
	private DomainSearchService domainSearchService;

	private BulkTransferDAO dao;
	
	private DomainStateMachine dsm;

	private NicHandleSearchService nicHandleSearchService;
	private NicHandleService nicHandleService;
	private ResellerDefaultsService defaultsService;

	private AccountSearchService accountService;
	private Account losingAccount;
	private Account gainingAccount;
	private Contact gainingTechContact;
	private String hostmasterHandle;
	

	public BulkTransfer(long bulkTransferId, String hostmasterHandle) {
		this.bulkTransferId = bulkTransferId;
		this.hostmasterHandle = hostmasterHandle;
	}
	
	/**
	 * performs bulk transfer on all domains from the request, which were not transfered yet.
	 * @param user logged in user effecting the action
	 * 
	 * @throws BulkTransferValidationException if the transfer request is already closed or some of the domains don't pass the validation rules 
	 * @throws BulkTransferException 
	 */
	public void transferDomains(AuthenticatedUser user) throws BulkTransferValidationException, BulkTransferException, DefaultsNotFoundException {
		transferDomains(user, false);						
	}
	
	/**
	 * performs bulk transfer on all domains from the request, which were not transfered yet and pass the validation. Domains, which don't pass the validation will not be transferred.
	 * @param user logged in user effecting the action
	 * 
	 * @throws BulkTransferValidationException if the transfer request is already closed. 
	 * @throws BulkTransferException 
	 */
	public void transferValidDomains(AuthenticatedUser user) throws BulkTransferValidationException, BulkTransferException, DefaultsNotFoundException {
		transferDomains(user, true);
	}
	
	private void transferDomains(AuthenticatedUser user, boolean skipDomainsWithValidationErrors) throws BulkTransferValidationException, BulkTransferException, DefaultsNotFoundException {
		updateTransferRequest();		
		validateTransferRequest();			
		validateDomains();
		
		if (!skipDomainsWithValidationErrors) {
			if (!validationErrors.isEmpty()) {
				throw new BulkTransferValidationException(validationErrors);
			}
		}
		
		performTransfer(user);
	}

	private void validateDomains() {
		for (TransferredDomain td: transferRequest.getRequestedDomains()) {
			if (td.getTransferDate() == null) {
				Domain d;
				try {
					d = domainSearchService.getDomain(td.getDomainName());
					List<String> msg = validate(d);
					if (msg.isEmpty()) {
						validatedDomains.add(d);				
					} else {
						validationErrors.addAll(msg);
					}			
				} catch (DomainNotFoundException e) {
					validationErrors.add(td.getDomainName() + " not found");
				}
			}
		}
	}

	private void validateTransferRequest() throws BulkTransferValidationException {
		if (transferRequest.isClosed()) {
			throw new BulkTransferValidationException(Arrays.asList("Transfer request already closed"));
		}
		
	}

	private void updateTransferRequest() throws DefaultsNotFoundException {
		dao.lock(bulkTransferId);
		transferRequest = dao.get(bulkTransferId);
		losingAccount = accountService.getAccount(transferRequest.getLosingAccount());
		gainingAccount = accountService.getAccount(transferRequest.getGainingAccount());
		ResellerDefaults defaults = defaultsService.get(gainingAccount.getBillingContact().getNicHandle());
		gainingTechContact = new Contact(defaults.getTechContactId());
	}

	
	private List<String> validate(Domain d) {
		List<String> msg = new ArrayList<String>();		
//		The system confirms that each selected domain currently has the losing customer as the bill-c [just a double-check].
		addError(validateBillingContact(d), msg);
//		6. The system confirms that each admin-c associated with each domain selected for transfer is not associated with any domain not selected for transfer.
//		7. The system confirms that each selected domain's current admin-c is associated with the same Account Number - that of the losing customer.
//		8. The system confirms that each domain's current admin-c is not associated with any domain which is not also associated with the losing customer's account.
		addError(validateAdminContacts(d), msg);
//		9. The system confirms that, for each domain, there is no unsettled transaction in the system, for any transaction type.
		addError(validateTransaction(d), msg);
//		10. The system confirms that no domain has an NRP status of P, T, XPA, XPI, XPV.
		addError(validateNRPStatus(d), msg);
//		11. The system confirms that no domain is in WIPO dispute.
		addError(validateWipo(d), msg);
		
		return msg;
	}

	private void addError(String msg, List<String> errors) {
		if (msg != null)
			errors.add(msg);
	}

	private String validateWipo(Domain d) {
		if (d.getDsmState().getWipoDispute() != null && d.getDsmState().getWipoDispute().booleanValue())
			return d.getName() + " is in WIPO";
		else 
			return null;
	}

	private String validateNRPStatus(Domain d) {
		NRPStatus st = d.getDsmState().getNRPStatus();
		
		switch (st) {
		case TransactionFailed:
		case PostTransactionAudit:
		case TransferPendingActive:
		case TransferPendingInvNrp:
		case TransferPendingVolNRP:
			return d.getName() + " has an illegal NRP Status: " + st;
		default:
			return null;
		}
	}

	private String validateTransaction(Domain d) {
		if(dao.hasUnsettledReservations(d.getName()))
			return d.getName() + " has unsettled reservations";
		else
			return null;
		
	}

	private String validateAdminContacts(Domain d) {
		for (Contact c: d.getAdminContacts()) {
			if (c != null && c.getNicHandle() != null) {
				NicHandle nh;
				//	7. The system confirms that each selected domain's current admin-c is associated with the same Account Number - that of the losing customer.
				try {
					nh = nicHandleSearchService.getNicHandle(c.getNicHandle());
					if (nh.getAccount().getId() != transferRequest.getLosingAccount())
						return d.getName() + " : admin contact " + c.getNicHandle() + " associated with a different account"; 
				} catch (NicHandleNotFoundException e) {				
					return d.getName() + " : admin contact does not exist : " + c.getNicHandle();
				}

				//	6. The system confirms that each admin-c associated with each domain selected for transfer is not associated with any domain not selected for transfer.
				List<String> dnames = dao.findDomainNamesWithSameContactNotInTransfer(bulkTransferId, c.getNicHandle());
				if (!dnames.isEmpty()) {
					return d.getName() + " : admin contact (" + c.getNicHandle() + ") is associated with domains, which are not in transfer : " + dnames; 
				}
				
				//	8. The system confirms that each domain's current admin-c is not associated with any domain which is not also associated with the losing customer's account.
				// actually this is a data integrity check... should receive one
				int i = dao.countAssociatedDomainsAccounts(c.getNicHandle());
				if (i > 1) {
					return d.getName() + " : admin contact (" + c.getNicHandle() + ") is associated with domains, which are associated with a different account";
				}
			}
		}
		
		return null;
	}

	private String validateBillingContact(Domain d) {
		if (d.getBillingContacts().size() == 0)
			return d.getName() + " does not have a billing contact!";		
		String domainBillC = d.getBillingContacts().get(0).getNicHandle();
		String losingBillC = losingAccount.getBillingContact().getNicHandle();
		if (!domainBillC.equals(losingBillC)) {
			return d.getName() + " has a different billing contact (" + domainBillC + ") than a losing account (" + losingBillC + ")";
		} else 
			return null;
	}

	private void performTransfer(AuthenticatedUser user) throws BulkTransferValidationException, BulkTransferException {
		/*
		 * 	12. FOR EACH DOMAIN:
	13. The system sets the domain's account to be that of the gaining customer.
	14. The system sets the domain's bill-c to be that of the gaining customer.
	15. DSM EVENT: TransferToRegistrar.  This will set the domain's renewal mode to N, if not already. The comment used for the domain's history record is that supplied by the actor.
	16. The system sets the domain's tech-c to be the default tech-C of the gaining customer.
	17. The system sets the admin-c's account to be that of the gaining customer.
	18. END FOR EACH DOMAIN
	19. The system records the successful bulk transfer details - including the ID (Nic) of the actor, date and time, and set of domains successfully transferred, along with the comment supplied by the actor.
	20. The system indicates that all domains have been successfully transferred.
		 */
		Date date = new Date();
		for (Domain d: validatedDomains) {
			transferDomain(user, d);
			markAsTransferred(d.getName(), date);
		}
	}

	private void markAsTransferred(String name, Date date) {		
		dao.markDomainAsTransferred(bulkTransferId, name, hostmasterHandle, date);
	}

    private void transferDomain(AuthenticatedUser user, Domain d) throws BulkTransferException {
        try {
            d.setResellerAccount(gainingAccount);
            d.setBillingContacts(Arrays.asList(gainingAccount.getBillingContact()));
            d.setRemark(transferRequest.getRemarks());
            d.setAuthCode(null);
            d.setAuthCodeExpirationDate(null);
            OpInfo opInfo = new OpInfo(hostmasterHandle, transferRequest.getRemarks());
            domainService.save(d, opInfo);
            dsm.handleEvent(user, d, new TransferToRegistrar(d.getBillingContact(), gainingAccount.getBillingContact()), opInfo);
            d.setTechContacts(Arrays.asList(gainingTechContact));
            d.setRemark("Completing domain bulk transfer");
            domainService.save(d, opInfo);
            String username = (user == null) ? null : user.getUsername();
            for (Contact c: d.getAdminContacts()) {
                if (c != null && c.getNicHandle() != null) {
                    updateAdminC(c.getNicHandle(), username);
                }
            }
        } catch (BulkTransferException e) {
            throw e;
        } catch (Exception e) {
            throw new BulkTransferException ("Error transferring domain", e);
        }
    }

	private void updateAdminC(String nicHandle, String loggedUserName) throws BulkTransferException {
		try {
			NicHandle adminc = nicHandleSearchService.getNicHandle(nicHandle);
			adminc.setAccount(gainingAccount);
			nicHandleService.save(adminc, transferRequest.getRemarks(), hostmasterHandle, hostmasterHandle, loggedUserName);
		} catch (Exception e) {
			throw new BulkTransferException("Error updating admin contact" + nicHandle, e);
		}
	}
	
	public void setBulkTransferDao(BulkTransferDAO dao) {
		this.dao = dao;
	}
	
	public void setAccountService(AccountSearchService accountService) {
		this.accountService = accountService;
	}
	
	public void setNicHandleSearchService(
			NicHandleSearchService nicHandleSearchService) {
		this.nicHandleSearchService = nicHandleSearchService;
	}
	
	public void setNicHandleService(NicHandleService nicHandleService) {
		this.nicHandleService = nicHandleService;
	}

    public void setDefaultsService(ResellerDefaultsService defaultsService) {
        this.defaultsService = defaultsService;
    }

    public void setDsm(DomainStateMachine dsm) {
		this.dsm = dsm;
	}
    
    public void setDomainSearchService(DomainSearchService domainSearchService) {
		this.domainSearchService = domainSearchService;
	}
    
    public void setDomainService(DomainService domainService) {
		this.domainService = domainService;
	}
}

