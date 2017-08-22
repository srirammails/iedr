package pl.nask.crs.entities;

import org.testng.annotations.Test;
import org.testng.AssertJUnit;

import javax.annotation.Resource;

import pl.nask.crs.commons.AbstractTest;
import pl.nask.crs.commons.search.SearchResult;
import pl.nask.crs.entities.dao.EntityCategoryDAO;
import pl.nask.crs.entities.search.EntityCategorySearchCriteria;

/**
 * @author Artur Gniadzik
 * @author Kasia Fulara
 */
public class TestEntityCategoryDAO extends AbstractTest {

    @Resource
    EntityCategoryDAO entityCategoryDAO;

    private static final long ENTITY_CLASS_ID = 1;
    private static final long ENTITY_CATEGORY_ID = 1;

    @Test
    public void testFind() {
        EntityCategorySearchCriteria c = new EntityCategorySearchCriteria();
        c.setEntityClass(ENTITY_CLASS_ID);

        SearchResult<EntityCategory> r = entityCategoryDAO.find(c);
        // wainting for new database...

        AssertJUnit.assertNotNull("No results found 1", r);
        AssertJUnit.assertNotNull("No results found 2", r.getResults());
        AssertJUnit.assertTrue("No results found 3", r.getResults().size() > 0);
        AssertJUnit.assertNotNull("Name field not filled", r.getResults().get(0)
                .getName());
    }

    @Test
    public void testFindAll() {
        EntityCategorySearchCriteria c = new EntityCategorySearchCriteria();

        SearchResult<EntityCategory> r = entityCategoryDAO.find(c);
        // wainting for new database...

        AssertJUnit.assertNotNull("No results found 1", r);
        AssertJUnit.assertNotNull("No results found 2", r.getResults());
        AssertJUnit.assertTrue("No results found 3", r.getResults().size() > 0);
        AssertJUnit.assertNotNull("Name field not filled", r.getResults().get(0)
                .getName());
    }

    @Test
    public void testGet() {
        EntityCategory r = entityCategoryDAO.get(ENTITY_CATEGORY_ID);
        AssertJUnit.assertNotNull("No results found", r);
        AssertJUnit.assertNotNull("Name field not filled", r.getName());
    }
}
