package pl.nask.crs.app.commons.impl;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import pl.nask.crs.app.ServicesRegistry;
import pl.nask.crs.app.ValidationHelper;
import pl.nask.crs.commons.OpInfo;
import pl.nask.crs.commons.config.ApplicationConfig;
import pl.nask.crs.commons.email.service.EmailParameters;
import pl.nask.crs.commons.email.service.EmailTemplateNamesEnum;
import pl.nask.crs.commons.exceptions.EmptyRemarkException;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.domains.Domain;
import pl.nask.crs.domains.exceptions.DomainNotFoundException;
import pl.nask.crs.domains.exceptions.DuplicatedAdminContact;
import pl.nask.crs.domains.nameservers.Nameserver;
import pl.nask.crs.nichandle.NicHandle;
import pl.nask.crs.nichandle.exception.NicHandleNotActiveException;
import pl.nask.crs.nichandle.exception.NicHandleNotFoundException;
import pl.nask.crs.security.authentication.AuthenticatedUser;

public class DnsModification {
	private final static Logger LOG = Logger.getLogger(DnsModification.class);

	private ServicesRegistry servicesRegistry;
	private ApplicationConfig applicationConfig;
	
	private AuthenticatedUser user;
	private List<Nameserver> nameservers;
	private String customerRemark;
	private List<String> domainNames;
	private boolean compareIp = true;
	
	private List<String> modifiedDomains = new LinkedList<String>();
	private Map<String, Domain> domains = new HashMap<String, Domain>();
	private Map<String, List<Nameserver>> oldNameservers = new HashMap<String, List<Nameserver>>();

	private Map<String, List<String>> validationErrors = new HashMap<String, List<String>>();

	private List<Exception> validationExceptions = new LinkedList<Exception>();

	public DnsModification(AuthenticatedUser user, List<String> domainNames,
			List<Nameserver> nameservers, String customerRemark) {
		this.user = user;
		this.nameservers = nameservers;
		this.customerRemark = customerRemark;
		this.domainNames = domainNames;
	}

	public void setServiceRegistry(ServicesRegistry servicesRegistry) {
		this.servicesRegistry = servicesRegistry;
	}

	public void perform() throws DuplicatedAdminContact, DomainNotFoundException, NicHandleNotFoundException, NicHandleNotActiveException, EmptyRemarkException, NameserversValidationException {
		markModifiedDomains();
		validateNameservers();
		updateDomains();
		sendConfirmationEmails();
	}
	
	
	private void sendConfirmationEmails() {
		for (Map.Entry<String, Domain> pair: domains.entrySet()) {
			sendConfirmationEmail(user, pair.getValue(), oldNameservers.get(pair.getKey()), nameservers);
		}
	}

	private void updateDomains() throws DuplicatedAdminContact, DomainNotFoundException, NicHandleNotFoundException, NicHandleNotActiveException, EmptyRemarkException {
		for (Domain domain: domains.values()) {
			domain.setNameservers(nameservers);
			domain.setRemark(customerRemark);
			servicesRegistry.getDomainService().save(domain, new OpInfo(user));
		}
	}

	private void validateNameservers() throws NameserversValidationException {		
		for (String domainName: modifiedDomains) {
			try {
				ValidationHelper.checkDomainNs(domainName, nameservers, applicationConfig.getNameserverMinCount(), applicationConfig.getNameserverMaxCount());
				for (Nameserver ns : nameservers) {
					servicesRegistry.getDnsCheckService().check(user.getUsername(), domainName, ns.getName(), ns.getIpAddress() == null ? null : ns.getIpAddress().getAddress());
				}
			} catch (Exception e) {
				logError(domainName, e);
			}
		}
		if (!validationErrors.isEmpty()) {
			throwValidationException();			
		}
	}

	private void throwValidationException() throws NameserversValidationException {
		throw new NameserversValidationException(validationErrors, validationExceptions );
		
	}

	private void logError(String domainName, Exception e) {
		List<String> errors = validationErrors.get(domainName);
		if (errors == null) {
			errors = new LinkedList<String>();
			validationErrors.put(domainName, errors);
		}
		errors.add(e.getMessage());
		validationExceptions.add(e);
	}

	private void markModifiedDomains() {
		for (String domainName: domainNames) {
			Domain domain = servicesRegistry.getDomainSearchService().getDomain(domainName);
			if (nameserversListModified(nameservers, domain.getNameservers())) {
				modifiedDomains.add(domainName);
				domains.put(domainName, domain);
				oldNameservers.put(domainName, domain.getNameservers());
			}
		}
	}

	private void sendConfirmationEmail(AuthenticatedUser user, Domain domain, List<Nameserver> oldList, List<Nameserver> newNameservers) {
		String username = (user == null) ? null : user.getUsername();
		NicHandle creator = getNic(username);
		NicHandle billingNh = getNic(domain.getBillingContact().getNicHandle());
		NicHandle techNh = getNic(domain.getTechContacts().get(0).getNicHandle());
		NicHandle adminNh = getNic(domain.getAdminContacts().get(0).getNicHandle());

		EmailParameters params = new TacDnsEmailParameters(
				username,
				creator,
				domain.getName(),
				techNh,
				adminNh,
				billingNh,
				oldList, newNameservers);

		try {
			boolean modifiedByBillC = user.getUsername().equalsIgnoreCase(billingNh.getNicHandleId()); 
			EmailTemplateNamesEnum template = null;

			if (billingNh.getAccount().getId() == (long) applicationConfig.getGuestAccountId()) {
				if (modifiedByBillC) {
					template = EmailTemplateNamesEnum.TAC_DNS_MOD_DIRECT_BILL;
				} else {
					template = EmailTemplateNamesEnum.TAC_DNS_MOD_DIRECT_AT;
				}
			} else {
				if (modifiedByBillC) {
					template = EmailTemplateNamesEnum.TAC_DNS_MOD_REG_BILL;
				} else {					
					template = EmailTemplateNamesEnum.TAC_DNS_MOD_REG_AT;
				}
			}
			servicesRegistry.getEmailTemplateSender().sendEmail(template.getId(), params);
		} catch (Exception e) {
			LOG.warn("Error sending email notification", e);
		}
	}

	private boolean nameserversListModified(List<Nameserver> newList, List<Nameserver> oldList) {
		if (newList == null)
			return false;

		if (oldList == null)
			return true;

		if (newList.size() != oldList.size())
			return true;

		for (int i = 0; i < newList.size(); i++) {
			Nameserver newValue = newList.get(i);
			Nameserver old = oldList.get(i);
			if (modified(newValue, old)) {
				return true;
			}
		}

		return false;
	}

	private NicHandle getNic(String name) {
		try {
			return servicesRegistry.getNicHandleSearchService().getNicHandle(name);
		} catch (NicHandleNotFoundException e) {
			LOG.warn("NicHandle not found!", e);
			return null;
		}
	}

	private boolean modified(Nameserver newValue, Nameserver old) {
		if (old == newValue)
			return false;

		if (old == null || newValue == null)
			return true;

		if (!Validator.isEqual(newValue.getName(), old.getName()))
			return true;
		
		if (!isCompareIp()) {
			return false; 
		} else if (!Validator.isEqual(newValue.getIpAddressAsString(), old.getIpAddressAsString())) {
			return true;
		}	

		return false;
	}

	public boolean isCompareIp() {
		return compareIp;
	}

	public void setCompareIp(boolean compareIp) {
		this.compareIp = compareIp;
	}
	
	public void setApplicationConfig(ApplicationConfig applicationConfig) {
		this.applicationConfig = applicationConfig;
	}
}
