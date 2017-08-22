package pl.nask.crs.app.commons.impl;

import java.util.List;
import java.util.Locale;

import pl.nask.crs.commons.email.service.MapBasedEmailParameters;
import pl.nask.crs.commons.email.service.ParameterNameEnum;
import pl.nask.crs.commons.utils.TableFormatter;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.domains.nameservers.Nameserver;
import pl.nask.crs.nichandle.NicHandle;
import pl.nask.crs.payment.email.PaymentEmailParameters;
import pl.nask.crs.security.authentication.AuthenticatedUser;

public class TacDnsEmailParameters extends MapBasedEmailParameters {

	private String loggedInNhId;
	private String billingNhId;
	private String username;

	public TacDnsEmailParameters(String username, NicHandle creatorNh, String domainName, NicHandle techC, NicHandle adminC, NicHandle billingNh,	List<Nameserver> oldList, List<Nameserver> newNameservers) {
		Validator.assertNotNull(creatorNh, "creatorNh");
		Validator.assertNotNull(techC, "techC");
		Validator.assertNotNull(adminC, "adminC");
		Validator.assertNotNull(billingNh, "billingNh");
		Validator.assertNotNull(oldList, "oldNameservers");
		Validator.assertNotNull(newNameservers, "newNameservers");
		set(ParameterNameEnum.CREATOR_C_EMAIL, creatorNh.getEmail());
		set(ParameterNameEnum.CREATOR_C_NAME, creatorNh.getName());
		set(ParameterNameEnum.TECH_C_EMAIL, techC.getEmail());
		set(ParameterNameEnum.ADMIN_C_EMAIL, adminC.getEmail());
		set(ParameterNameEnum.DOMAIN, domainName);
		set(ParameterNameEnum.BILL_C_EMAIL, billingNh.getEmail());
		set(ParameterNameEnum.BILL_C_NAME, billingNh.getName());
		set(ParameterNameEnum.DNS_LIST_OLD, makeFormatter(oldList));
		set(ParameterNameEnum.DNS_LIST_NEW, makeFormatter(newNameservers));
		set(ParameterNameEnum.REGISTRAR_NAME, billingNh.getName());
		this.loggedInNhId = creatorNh.getNicHandleId();
		this.billingNhId = billingNh.getNicHandleId();
		this.username =  username;
	}

	public String getLoggedInNicHandle()
    {
        return this.username;
    }

	public String getAccountRelatedNicHandle()
	{
		return this.billingNhId;
	}
	
    public String getDomainName()
    {
        return this.getParameterValue(ParameterNameEnum.DOMAIN.toString(), false); 
    }

	//getParameterValue(String name, boolean html)
	private TableFormatter makeFormatter(List<Nameserver> list) {
		TableFormatter formatter = getFormatter();
		for (Nameserver ns: list) {
			formatter.addDataLine(new Object[] {ns.getName(), ns.getIpAddressAsString()});			
		}
		
		return formatter;
	}

	private TableFormatter getFormatter() {
		TableFormatter formatter = new TableFormatter(Locale.getDefault());
		formatter.addColumn("host", 60, TableFormatter.leftAlignedStringFormat(60), true);
		formatter.addColumn("ip", 60, TableFormatter.leftAlignedStringFormat(60), true);
		
		return formatter;
	}
}
