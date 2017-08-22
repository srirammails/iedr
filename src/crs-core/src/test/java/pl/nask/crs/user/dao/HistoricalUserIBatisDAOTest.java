package pl.nask.crs.user.dao;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.history.HistoricalObject;
import pl.nask.crs.user.User;
import pl.nask.crs.user.search.HistoricalUserSearchCriteria;

@ContextConfiguration(locations = { "/users-config.xml", "/users-config-test.xml", "/test-config.xml", "/commons-base-config.xml" })
public class HistoricalUserIBatisDAOTest extends AbstractTransactionalTestNGSpringContextTests {

    @Autowired
    HistoricalUserDAO historicalUserDAO;
    
    @Autowired
    UserDAO userDAO;

    /*
     * check, if historical record is created
     */
    @Test
    public final void testCreate() {
        // prepare params
        String username = "IDL2-IEDR";
        String chngBy = "TEST";
        Date now = DateUtils.setMilliseconds(new Date(), 999);
        
        // create
        User user = userDAO.get(username);
        HistoricalObject<User> histUser = new HistoricalObject<User>(user, now, chngBy);
        historicalUserDAO.create(histUser);

        // verify
        User u = userDAO.get(username);
        HistoricalUserSearchCriteria c = new HistoricalUserSearchCriteria(username);
        LimitedSearchResult<HistoricalObject<User>> res = historicalUserDAO.find(c, 0, 10);

        // first historical object should contain current user
        AssertJUnit.assertNotNull(res.getResults());
        AssertJUnit.assertTrue(res.getResults().size() > 0);
        HistoricalObject<User> r = res.getResults().get(0);

        AssertJUnit.assertEquals(DateUtils.truncate(now, Calendar.SECOND), r.getChangeDate());
        AssertJUnit.assertEquals(u.getUsername(), r.getObject().getUsername());
        AssertJUnit.assertEquals(chngBy, r.getChangedBy());
        AssertJUnit.assertEquals(u.getPassword(), r.getObject().getPassword());
        AssertJUnit.assertEquals(u.getSalt(), r.getObject().getSalt());
        AssertJUnit.assertEquals(u.getPermissionGroups(), r.getObject().getPermissionGroups());
        AssertJUnit.assertEquals(u.getSecret(), r.getObject().getSecret());
    }
}
