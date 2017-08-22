package pl.nask.crs.web.accounts;

import org.apache.log4j.Logger;

import pl.nask.crs.accounts.AccountTariff;
import pl.nask.crs.app.accounts.AccountAppService;
import pl.nask.crs.app.accounts.wrappers.AccountWrapper;
import pl.nask.crs.app.payment.PaymentAppService;
import pl.nask.crs.app.utils.ContactHelper;
import pl.nask.crs.commons.dictionary.Dictionary;
import pl.nask.crs.contacts.Contact;
import pl.nask.crs.contacts.exceptions.ContactCannotChangeException;
import pl.nask.crs.country.CountryFactory;

/**
 * @author Marianna Mysiorska
 */
public class AccountEditAction extends AbstractAccountAction {
	private static final Logger log = Logger.getLogger(AccountEditAction.class);

    public static final String INPUT = "input";
    public static final String SAVE = "save";
    public static final String HOSTMASTERS_REMARK_EDIT_TEXT = "Edit";

    public AccountEditAction(AccountAppService accountAppService, CountryFactory countryFactory, Dictionary<String, AccountTariff> tariffsDictionary, PaymentAppService paymentAppService) {
        super(accountAppService, countryFactory, tariffsDictionary, paymentAppService);
    }

    public String input() throws Exception {
        account = accountAppService.get(getUser(), getId());
        wrapper = new AccountWrapper(account);
        hostmastersRemark = HOSTMASTERS_REMARK_EDIT_TEXT;
        return INPUT;
    }

    public String save() throws Exception {
        // validation moved to external xml (AccountEditAction-validation.xml)
        try {
            account = accountAppService.save(getUser(), account, wrapper, hostmastersRemark);
            wrapper = new AccountWrapper(account);
            setId(account.getId());
            return SAVE;
        } catch (ContactCannotChangeException ex) {
            addActionError("The billing contact cannot change from " + ex.getFrom() + " to " + ex.getTo());
            return ERROR;
        }
    }

    public String makeContactInfo(Contact contact){
        return ContactHelper.makeContactInfo(contact);
    }    

}
