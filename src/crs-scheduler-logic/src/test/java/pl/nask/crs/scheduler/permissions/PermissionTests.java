package pl.nask.crs.scheduler.permissions;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.AssertJUnit;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import pl.nask.crs.scheduler.SchedulerCron;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.user.Level;
import pl.nask.crs.user.service.UserService;

@ContextConfiguration(locations = {"/scheduler-config.xml"})
public class PermissionTests extends AbstractTransactionalTestNGSpringContextTests {

	@Autowired
	SchedulerCron scheduler;
	
	@Autowired
	UserService userService;
	
	Set<Level> grants = new HashSet<Level>();
	{
		grants.add(Level.Technical);
		grants.add(Level.TechnicalLead);
	}
	String nhName = "AAG45-IEDR";
	AuthenticatedUser user = new AuthenticatedUser() {
		public String getAuthenticationToken() {return null;}
		public String getSuperUserName() {return null;}
		public String getUsername() {
			return nhName;
		}
	};
	
	@DataProvider(name="grantsForJobConfigModifications")
	public Object[][] grantsForJobConfigModifications() {
		Object[][] res = new Object[Level.values().length][2];
		for (Level l: Level.values()) {
			res[l.ordinal()] = new Object[]{l, grants.contains(l)};
		}
		
		return res;
		
	}
	
	@Test(dataProvider="grantsForJobConfigModifications")
	public void checkPermissionForAddJobConfig(Level level, boolean permissionGranted) {
		try {
			setUserLevel(level);
			scheduler.addJobConfig(user, null, null);
			assertPermissionNotGranted(permissionGranted);
		} catch (AccessDeniedException e) {
			assertPermissionGranted(permissionGranted);
		} catch (Exception e) {
			assertPermissionNotGranted(permissionGranted);
		}
	}
	
	@Test(dataProvider="grantsForJobConfigModifications")
	public void checkPermissionForRemoveJobConfig(Level level, boolean permissionGranted) {
		try {
			setUserLevel(level);
			scheduler.removeJobConfig(user, 0);
			assertPermissionNotGranted(permissionGranted);
		} catch (AccessDeniedException e) {
			assertPermissionGranted(permissionGranted);
		} catch (Exception e) {
			assertPermissionNotGranted(permissionGranted);
		}
	}
	
	@Test(dataProvider="grantsForJobConfigModifications")
	public void checkPermissionForModifyJobConfig(Level level, boolean permissionGranted) {
		try {
			setUserLevel(level);
			scheduler.modifyJobConfig(user, 0, null);
			assertPermissionNotGranted(permissionGranted);
		} catch (AccessDeniedException e) {
			assertPermissionGranted(permissionGranted);
		} catch (Exception e) {
			assertPermissionNotGranted(permissionGranted);
		}
	}
	
	@Test(dataProvider="grantsForJobConfigModifications")
    public void checkPermissionForFindJobs(Level level, boolean permissionGranted) {
		try {
			setUserLevel(level);
			scheduler.findJobs(user, null, 0, 10, null);
			assertPermissionNotGranted(permissionGranted);
		} catch (AccessDeniedException e) {
			assertPermissionGranted(permissionGranted);
		} catch (Exception e) {
			assertPermissionNotGranted(permissionGranted);
		}
    }
    
	@Test(dataProvider="grantsForJobConfigModifications")
    public void checkPermissionForFindJobsHistory(Level level, boolean permissionGranted) {
		try {
			setUserLevel(level);
			scheduler.findJobsHistory(user, null, 0, 10, null);
			assertPermissionNotGranted(permissionGranted);
		} catch (AccessDeniedException e) {
			assertPermissionGranted(permissionGranted);
		} catch (Exception e) {
			assertPermissionNotGranted(permissionGranted);
		}
    }

	@Test(dataProvider="grantsForJobConfigModifications")
    public void checkPermissionForGetJobConfigs(Level level, boolean permissionGranted) {
		try {
			setUserLevel(level);
			scheduler.getJobConfigs(user);
			assertPermissionNotGranted(permissionGranted);
		} catch (AccessDeniedException e) {
			assertPermissionGranted(permissionGranted);
		} catch (Exception e) {
			assertPermissionNotGranted(permissionGranted);
		}
    }
    
	@Test(dataProvider="grantsForJobConfigModifications")
    public void checkPermissionForGetJobConfig(Level level, boolean permissionGranted) {
		try {
			setUserLevel(level);
			scheduler.getJobConfig(user, 0);
			assertPermissionNotGranted(permissionGranted);
		} catch (AccessDeniedException e) {
			assertPermissionGranted(permissionGranted);
		} catch (Exception e) {
			assertPermissionNotGranted(permissionGranted);
		}
    }

	private void assertPermissionGranted(boolean permissionGranted) {
		if (permissionGranted) {
			AssertJUnit.fail("Permission should be granted");
		}
		
	}

	private void assertPermissionNotGranted(boolean permissionGranted) {
		if (!permissionGranted) {
			AssertJUnit.fail("Permission should not be granted");
		}
	}

	private void setUserLevel(Level level) {
		userService.setUserGroup(nhName, level, nhName);
	}
}
