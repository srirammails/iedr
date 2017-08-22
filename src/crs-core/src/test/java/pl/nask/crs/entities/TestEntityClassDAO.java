package pl.nask.crs.entities;

import org.testng.annotations.Test;
import org.testng.AssertJUnit;

import javax.annotation.Resource;

import pl.nask.crs.commons.AbstractTest;
import pl.nask.crs.commons.search.SearchResult;
import pl.nask.crs.entities.dao.EntityClassDAO;

/**
 * @author Artur Gniadzik
 * @author Kasia Fulara
 */
public class TestEntityClassDAO extends AbstractTest {

    @Resource
    private EntityClassDAO entityClassDAO;

    private static final long ENTITY_CLASS_ID = 1;

    @Test
    public void testFind() {
        SearchResult<EntityClass> r = entityClassDAO.find(null);
        AssertJUnit.assertNotNull("No results found", r);
        AssertJUnit.assertNotNull("No results found", r.getResults());
        AssertJUnit.assertTrue("No results found", r.getResults().size() > 0);
        AssertJUnit.assertNotNull("Name field not filled", r.getResults().get(0)
                .getName());
    }

    @Test
    public void testGet() {
        EntityClass r = entityClassDAO.get(ENTITY_CLASS_ID);
        AssertJUnit.assertNotNull("No results found", r);
        AssertJUnit.assertNotNull("Name field not filled", r.getName());
    }
}
