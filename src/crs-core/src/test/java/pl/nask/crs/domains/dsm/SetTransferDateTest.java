package pl.nask.crs.domains.dsm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import org.easymock.EasyMock;
import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pl.nask.crs.accounts.Account;
import pl.nask.crs.contacts.Contact;
import pl.nask.crs.domains.Domain;
import pl.nask.crs.domains.dao.DomainDAO;
import pl.nask.crs.domains.dsm.DsmAction;
import pl.nask.crs.domains.dsm.DsmActionFactory;
import pl.nask.crs.domains.dsm.actions.SetTransferDate;
import pl.nask.crs.domains.dsm.events.TransferToRegistrar;
import pl.nask.crs.domains.nameservers.Nameserver;


public class SetTransferDateTest {

	private DomainDAO dao; 
	
	@BeforeMethod
	public void setUp() {
		dao = EasyMock.createMock(DomainDAO.class);		
	}
	
	@Test
	public void shouldSetTransferDateToTheCurrentDate() {		
		DsmAction action = newSetTransferDate();		
		Domain domain = anyDomain();
		Assert.assertNull(domain.getTransferDate());
		TransferToRegistrar event = new TransferToRegistrar(new Contact("old"), new Contact("new"));
		action.invoke(null, domain, event);
		
		AssertJUnit.assertNotNull("domain.transferDate", domain.getTransferDate());
	}
	
	private SetTransferDate newSetTransferDate() {
		DsmActionFactory factory = new SetTransferDate(dao);
		return (SetTransferDate) factory.getAction(null);
	}

	private Domain anyDomain() {
			Date anyDate = new Date();
			return new Domain(
					"name.ie", 
					"holder", 
					"holderClass", 
					"holderCategory", 
					new Contact("any"), 
					new Account(1), 
					anyDate, 
					anyDate, 
					"remark", 
					anyDate, 
					false, 
					Arrays.asList(new Contact("any")), 
					Arrays.asList(new Contact("any")), 
					Arrays.asList(new Contact("any")), 
					new ArrayList<Nameserver>());
	}

	@Test
	public void shouldWriteTransfersHistRecord() {
		// prepare mock
		EasyMock.reset(dao);
		SetTransferDate action = newSetTransferDate();
		dao.createTransferRecord("name.ie", action.getNewTransferDate(), "old", "new");
		EasyMock.replay(dao);
		
		Domain domain = anyDomain();
		TransferToRegistrar event = new TransferToRegistrar(new Contact("old"), new Contact("new"));
		action.invoke(null, domain, event);
		EasyMock.verify(dao);
	}
}
