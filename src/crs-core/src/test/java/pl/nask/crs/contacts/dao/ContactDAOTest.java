package pl.nask.crs.contacts.dao;

import javax.annotation.Resource;

import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import pl.nask.crs.commons.AbstractTest;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SearchResult;
import pl.nask.crs.contacts.Contact;
import pl.nask.crs.contacts.search.ContactSearchCriteria;

/**
 * @author Patrycja Wegrzynowicz
 * @author Marianna Mysiorska
 */
public class ContactDAOTest extends AbstractTest {

    @Resource
    ContactDAO contactDAO;

    @Test
    public void testFindAll() {
        ContactSearchCriteria all = new ContactSearchCriteria();
        SearchResult<Contact> res = contactDAO.find(all);
        AssertJUnit.assertEquals(934, res.getResults().size());
    }

    @Test
    public void testFindByName() {
        ContactSearchCriteria likeAName = new ContactSearchCriteria();
        likeAName.setName("A");
        SearchResult<Contact> res = contactDAO.find(likeAName);
        AssertJUnit.assertEquals(86, res.getResults().size());
    }

    @Test
    public void testLimitedFindAll() {
        ContactSearchCriteria all = new ContactSearchCriteria();
        LimitedSearchResult<Contact> res = contactDAO.find(all, 0, 50);
        AssertJUnit.assertEquals(934, res.getTotalResults());
        AssertJUnit.assertEquals(50, res.getResults().size());
    }

    @Test
    public void testLimitedFindByName() {
        ContactSearchCriteria likeAName = new ContactSearchCriteria();
        likeAName.setName("A");
        LimitedSearchResult<Contact> res = contactDAO.find(likeAName, 0, 50);
        AssertJUnit.assertEquals(86, res.getTotalResults());
        AssertJUnit.assertEquals(50, res.getResults().size());
    }

    @Test
    public void testFindByNicHandleIdAndType() {
        ContactSearchCriteria criteria = new ContactSearchCriteria();
        criteria.setNicHandle("AAP368-IEDR");
        criteria.setType("B");
        SearchResult<Contact> res = contactDAO.find(criteria);
        AssertJUnit.assertEquals(1, res.getResults().size());
    }

    @Test
    public void testFindByNicHandleIdAndTypeEmptyResult() {
        ContactSearchCriteria criteria = new ContactSearchCriteria();
        criteria.setNicHandle("AAE395-IEDR");
        criteria.setType("B");
        SearchResult<Contact> res = contactDAO.find(criteria);
        AssertJUnit.assertEquals(0, res.getResults().size());
    }

    @Test
    public void getContactStatus(){
        AssertJUnit.assertTrue(contactDAO.getContactStatus("AAA906-IEDR").equals("Active"));
    }

    @Test
    public void getContactStatusNotExists(){
        Assert.assertNull(contactDAO.getContactStatus("NOT-EXISTS"));
    }
}
