package pl.nask.crs.nichandle.service;

import java.util.Collections;

import javax.annotation.Resource;

import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pl.nask.crs.nichandle.AbstractContextAwareTest;
import pl.nask.crs.nichandle.NewAccount;
import pl.nask.crs.nichandle.NicHandle;
import pl.nask.crs.nichandle.dao.NicHandleIdDAO;
import pl.nask.crs.user.Level;
import pl.nask.crs.user.User;
import pl.nask.crs.user.service.UserSearchService;
import pl.nask.crs.user.service.UserService;

public class CreateDirectAccountTest extends AbstractContextAwareTest {


    @Resource
    private NicHandleService service;
    @Resource
    private NicHandleSearchService searchService;
    @Resource
    private NicHandleIdDAO nicHandleIdDAO;
    @Resource
    private UserSearchService userSearchService;
    @Resource
    private UserService userService;
    
	private NewAccount newAcc;      
	
    @BeforeMethod
	public void createDirectAccount() throws Exception {
    	newAcc = service.createDirectAccount("name",
    			"cname", "email@email.em", "address", "Poland", "", Collections.singleton("11111111"), Collections.singleton("11111111"), 
    			null, "Marysia1", false);
    }
    
    @Test
    public void shouldHavePasswordSet() {
    	User u = userSearchService.get(newAcc.getNicHandleId());
    	AssertJUnit.assertNotNull("password", u.getPassword());
    	AssertJUnit.assertNotNull("salt", u.getSalt());
    }
    
    @Test
    public void shouldHaveDirectsPermissions() {
    	User u = userSearchService.get(newAcc.getNicHandleId());
    	AssertJUnit.assertTrue("Belongs to the Direct group", u.hasGroup(Level.Direct));
    }
    
    @Test
    public void shouldBeTheGuestAccount() throws Exception {    	
    	NicHandle nh = searchService.getNicHandle(newAcc.getNicHandleId());
    	AssertJUnit.assertEquals("AccountNo", 1L, nh.getAccount().getId());
    }
    
    @Test
    public void shouldBeSeflCreated() throws Exception {
    	// the nichandle creates itself, the creator's nh should match the nichandleId
    	Assert.assertNotNull(newAcc.getNicHandleId());
    	NicHandle nh = searchService.getNicHandle(newAcc.getNicHandleId());
    	AssertJUnit.assertEquals("creatorNh", newAcc.getNicHandleId(), nh.getCreator());
    }
	
}
