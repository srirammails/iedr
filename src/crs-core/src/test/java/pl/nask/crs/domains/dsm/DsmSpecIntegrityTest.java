package pl.nask.crs.domains.dsm;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.AssertJUnit;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import pl.nask.crs.commons.config.ApplicationConfig;
import pl.nask.crs.domains.AbstractContextAwareTest;
import pl.nask.crs.domains.CustomerType;
import pl.nask.crs.domains.DomainHolderType;
import pl.nask.crs.domains.NRPStatus;
import pl.nask.crs.domains.RenewalMode;
import pl.nask.crs.domains.dsm.DsmActionsRegistry;
import pl.nask.crs.domains.dsm.DsmEventName;

/**
 * Checks, if the implementation of DSM is full (all actions defined in the spec are implemented in CRS-CORE)   
 * 
 * TODO: test all DSM statuses!
 * 
 * @author Artur Gniadzik
 *
 */
public class DsmSpecIntegrityTest extends AbstractContextAwareTest {
	@Resource
	DsmTestDAO dao;
	
	@Resource
	ApplicationConfig applicationConfig;
	
	DsmActionsRegistry actionRegistry = new DsmActionsRegistry(applicationConfig);
	{
		actionRegistry.initActionFactories();
	}
	
	
	List<String> errors = new ArrayList<String>();

	private String errorMsg;
	
	@BeforeMethod
	public void clearErrors() {
		errors.clear();
	}
	
	@AfterMethod
	public void failIfErrorInTest() {
		if (!errors.isEmpty()) {
			AssertJUnit.fail(errorMsg + " : " + errors.toString());
		}
	}
	
	@Test
	public void testActions() {
		List<String> actions = dao.getActions();
		for (String a: actions) {
			checkAction(a);
		}		
	}		
	
	@Test
	public void testEventNames() {
		List<String> events = dao.getEvents();
		for (String e: events) {
			checkEvent(e);
		}
        for (DsmEventName eventName : DsmEventName.values()) {
            if (!events.contains(eventName.name())) {
                AssertJUnit.fail("DsmEventName contains event that cannot be found in db: " + eventName);
            }
        }

    }

    private void checkEvent(String e) {
		try {
			DsmEventName event = DsmEventName.valueOf(e);		
		} catch (IllegalArgumentException ex) {
			logError("Missing events (DsmEventName)", e);		
		}
	}

	@Test
	public void testNRPStatuses() {
		List<String> statuses = dao.getNrpStatuses(); 
		for (String s: statuses) {
			checkStatus(s);
		}
	}
	
	@Test
	public void testRenewalModes() {
		List<String> modes = dao.getRenewalModes(); 
		for (String s: modes) {
			checkRenewalMode(s);
		}
	}
	
	@Test
	public void testCustTypes() {
		List<String> modes = dao.getAllCustTypes(); 
		for (String s: modes) {
			checkCustType(s);
		}
	}
	
	private void checkCustType(String s) {
		if (s == null)
			return;
		CustomerType status = CustomerType.forCode(s);
		if (status == null || !status.getCode().equals(s)) {
			logError("CustType missing", s);
		}
		
	}

	@Test
	public void testHolderTypes() {
		List<String> modes = dao.getAllHolderTypes(); 
		for (String s: modes) {
			checkHolderType(s);
		}
	}

	private void checkHolderType(String s) {
		if (s == null)
			return;
		try {
			DomainHolderType status = DomainHolderType.forCode(s);
		} catch (Exception ex) {
			logError("DomainHolderType missing", s);		
		}
	}

	private void checkRenewalMode(String s) {
		if (s == null)
			return;
		RenewalMode status = RenewalMode.forCode(s);
		if (status == null || !status.getCode().equals(s)) {
			logError("RenewalMode missing", s);
		}
	}

	private void checkStatus(String s) {
		if (s == null)
			return;
		try {
			NRPStatus status = NRPStatus.forCode(s);
		} catch (Exception ex) {
			logError("Status missing (NRPStatus)", s);
		}
	}

	private void checkAction(String action) {
		try {
			if (!actionRegistry.hasActionFactoryFor(action)) {
				logError("Missing Actions (DsmActionsRegistry)", action);
			}
		} catch (Exception e) {
			logError("Error getting actions (DsmActionsRegistry)", action);			
		}
				
	}

	private void logError(String errorType, String message) {
		errorMsg = errorType;
		errors.add(message);
	}
}
