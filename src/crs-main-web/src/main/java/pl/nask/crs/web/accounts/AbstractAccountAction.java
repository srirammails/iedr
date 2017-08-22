package pl.nask.crs.web.accounts;

import java.util.List;

import org.apache.log4j.Logger;

import pl.nask.crs.accounts.Account;
import pl.nask.crs.accounts.AccountTariff;
import pl.nask.crs.app.accounts.AccountAppService;
import pl.nask.crs.app.accounts.wrappers.AccountWrapper;
import pl.nask.crs.app.payment.PaymentAppService;
import pl.nask.crs.commons.dictionary.Dictionary;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.country.Country;
import pl.nask.crs.country.CountryFactory;
import pl.nask.crs.web.security.AuthenticatedUserAwareAction;

public abstract class AbstractAccountAction extends AuthenticatedUserAwareAction {
	private final static Logger log = Logger.getLogger(AbstractAccountAction.class);

	protected AccountAppService accountAppService;
    protected List<Country> countries;
    protected Dictionary<String, AccountTariff> tariffsDictionary;
    protected PaymentAppService paymentAppService;
    protected List<AccountTariff> tariffs;
    protected List<String> statuses;
    protected Long id;
    
    protected Account account;
    protected AccountWrapper wrapper;
    protected String hostmastersRemark;

    public AbstractAccountAction(AccountAppService accountAppService, CountryFactory countryFactory, Dictionary<String, AccountTariff> tariffsDictionary, PaymentAppService paymentAppService) {
        Validator.assertNotNull(accountAppService, "account app service");
        Validator.assertNotNull(countryFactory, "country factory");
        Validator.assertNotNull(tariffsDictionary, "tariffs dictionary");
        Validator.assertNotNull(paymentAppService, "paymentAppService");
        this.accountAppService = accountAppService;
        this.countries = countryFactory.getEntries();
        this.tariffsDictionary = tariffsDictionary;
        this.paymentAppService = paymentAppService;
    }

    public List<String> getCategories() {
        return paymentAppService.getVatCategories(getUser());
    }

    public List<AccountTariff> getTariffs() {
	    if (tariffs == null) {
	        try {
	            tariffs = tariffsDictionary.getEntries();
	        } catch (Exception e) {
	            log.error("Error fetching tariffs", e);
	            addActionError("Error fetching tariffs");
	        }
	    }
	    return tariffs;
	}

	public AccountAppService getAccountAppService() {
	    return accountAppService;
	}

	public List<Country> getCountries() {
	    return countries;
	}

    public void setId(Long id) {
    	this.id = id;
    }

    public Long getId() {
        return id;
    }

    public List<String> getStatuses() {
        return statuses;
    }

    public void setStatuses(List<String> statuses) {
        this.statuses = statuses;
    }
    
    public AccountWrapper getWrapper() {
        return wrapper;
    }

    public void setWrapper(AccountWrapper wrapper) {
        this.wrapper = wrapper;
    }


    public void setHostmastersRemark(String hostmastersRemark) {
        this.hostmastersRemark = hostmastersRemark;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
    
    public String getHostmastersRemark() {
        return hostmastersRemark;
    }

    public Account getAccount() {
        return account;
    }
    
    public void setAccountAppService(AccountAppService accountAppService) {
        this.accountAppService = accountAppService;
    }

}