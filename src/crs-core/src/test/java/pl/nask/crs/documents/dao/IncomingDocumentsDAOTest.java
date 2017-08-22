package pl.nask.crs.documents.dao;

import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SearchResult;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.documents.AbstractContextAwareTest;
import pl.nask.crs.documents.Document;
import pl.nask.crs.documents.DocumentFile;
import pl.nask.crs.documents.dao.ibatis.ConvertingDocumentDAO;
import pl.nask.crs.documents.search.DocumentSearchCriteria;

/**
 * @author Marianna Mysiorska
 */
public class IncomingDocumentsDAOTest extends AbstractContextAwareTest {

    @Resource
    ConvertingDocumentDAO documentDAO;

//TODO: CRS-72
//    @Test
//    public void getDoc() {
//        Document actualDoc = documentDAO.get(1l);
//        Document expectedDoc = createDocId1();
//        compareDocs(actualDoc, expectedDoc);
//        compareDocsDomains(actualDoc, expectedDoc);
//    }
//
//    @Test
//    public void findAllDocs() {
//        DocumentSearchCriteria criteria = new DocumentSearchCriteria();
//        SearchResult<Document> result = documentDAO.find(criteria, idSortCrit());
//        List<Document> actualDocs = result.getResults();
//        List<Document> expectedDocs = createAllDocs(actualDocs);
//        compareDocList(actualDocs, expectedDocs);
//    }
//
//    @Test
//    public void findDocsByDomain() {
//        DocumentSearchCriteria criteria = new DocumentSearchCriteria();
//        criteria.setDomainName("comp.ie");
//        SearchResult<Document> result = documentDAO.find(criteria, idSortCrit());
//        List<Document> actualDocs = result.getResults();
//        List<Document> expectedDocs = createDocsDomain();
//        compareDocList(actualDocs, expectedDocs);
//    }
//
//    @Test
//    public void findDocsByDomainWithLimit() {
//        DocumentSearchCriteria criteria = new DocumentSearchCriteria();
//        criteria.setDomainName("comp.ie");
//        LimitedSearchResult<Document> actualDocs = documentDAO.find(criteria, 0, 1, idSortCrit());
//        List<Document> expectedDocs = createDocsDomainLimited();
//        compareDocList(actualDocs.getResults(), expectedDocs);
//    }
//
//    @Test
//    public void findDocsBySource() {
//        DocumentSearchCriteria criteria = new DocumentSearchCriteria();
//        criteria.setDocSource("Ne");
//        SearchResult<Document> result = documentDAO.find(criteria, idSortCrit());
//        List<Document> actualDocs = result.getResults();
//        List<Document> expectedDocs = createDocsSource(actualDocs);
//        compareDocList(actualDocs, expectedDocs);
//    }
//
//    @Test
//    public void findDocsBySourceWithLimit() {
//        DocumentSearchCriteria criteria = new DocumentSearchCriteria();
//        criteria.setDocSource("Ne");
//        LimitedSearchResult<Document> actualDocs = documentDAO.find(criteria, 1, 1, idSortCrit());
//        List<Document> expectedDocs = createDocsSourceLimited();
//        compareDocList(actualDocs.getResults(), expectedDocs);
//    }
//
//    @Test
//    public void findDocsByDomainAndSource() {
//        DocumentSearchCriteria criteria = new DocumentSearchCriteria();
//        criteria.setDomainName("comp.ie");
//        criteria.setDocSource("Ne");
//        SearchResult<Document> result = documentDAO.find(criteria, idSortCrit());
//        List<Document> actualDocs = result.getResults();
//        List<Document> expectedDocs = createDocsDomainSource();
//        compareDocList(actualDocs, expectedDocs);
//    }
//
//    @Test
//    public void findDocsByDomainAndSourceWithLimit() {
//        DocumentSearchCriteria criteria = new DocumentSearchCriteria();
//        criteria.setDomainName("comp.ie");
//        criteria.setDocSource("Ne");
//        LimitedSearchResult<Document> actualDocs = documentDAO.find(criteria, 0, 1, idSortCrit());
//        List<Document> expectedDocs = createDocsDomainSourceLimited();
//        compareDocList(actualDocs.getResults(), expectedDocs);
//    }
//
//    @Test
//    public void findDocsByFromTo() {
//        DocumentSearchCriteria criteria = new DocumentSearchCriteria();
//        criteria.setFrom(new Date(1224663365000l));
//        criteria.setTo(new Date(1224836231000l));
//        SearchResult<Document> result = documentDAO.find(criteria, idSortCrit());
//        List<Document> actualDocs = result.getResults();
//        List<Document> expectedDocs = createDocsFromTo();
//        compareDocList(actualDocs, expectedDocs);
//    }
//
//    @Test
//    public void findDocsByFromToWithLimit() {
//        DocumentSearchCriteria criteria = new DocumentSearchCriteria();
//        criteria.setFrom(new Date(1224663365000l));
//        criteria.setTo(new Date(1224836231000l));
//        LimitedSearchResult<Document> actualDocs = documentDAO.find(criteria, 0, 1, idSortCrit());
//        List<Document> expectedDocs = createDocsFromToLimited();
//        compareDocList(actualDocs.getResults(), expectedDocs);
//    }
//
//    @Test
//    public void findDocsByFromToDomainSource() {
//        DocumentSearchCriteria criteria = new DocumentSearchCriteria();
//        criteria.setFrom(new Date(1224663365000l));
//        criteria.setTo(new Date(1224836231000l));
//        criteria.setDomainName("comp.ie");
//        criteria.setDocSource("Ne");
//        SearchResult<Document> result = documentDAO.find(criteria, idSortCrit());
//        List<Document> actualDocs = result.getResults();
//        List<Document> expectedDocs = createDocsFromToDomainSource();
//        compareDocList(actualDocs, expectedDocs);
//    }
//
//    @Test
//    public void findDocsByFromToDomainSourceWithLimit() {
//        DocumentSearchCriteria criteria = new DocumentSearchCriteria();
//        criteria.setFrom(new Date(1224663365000l));
//        criteria.setTo(new Date(1224836231000l));
//        criteria.setDomainName("comp.ie");
//        criteria.setDocSource("Ne");
//        LimitedSearchResult<Document> actualDocs = documentDAO.find(criteria, 1, 1, idSortCrit());
//        List<Document> expectedDocs = createDocsFromToDomainSourceLimited();
//        compareDocList(actualDocs.getResults(), expectedDocs);
//    }
//
//    private List<SortCriterion> idSortCrit() {
//        return Arrays.asList(new SortCriterion("id", true));
//    }
//
//    @Test
//    public void createDoc() {
//        List<String> domains = new ArrayList<String>();
//        domains.add("elvis.ie");
//        domains.add("presley.ie");
//        domains.add("jo.ie");
//        Document f = new Document(new DocumentFile("fax006.tif", "fax"), "New Reg", "SOURCE IE", 104, domains, null);
//        documentDAO.create(f);
//    }
//
//    @Test
//    public void updateDocDomain() {
//        List<String> domains = new ArrayList<String>();
//        domains.add("elvis.ie");
//        domains.add("presley.ie");
//        domains.add("beforeUpdate.ie");
//        Document document = new Document(new DocumentFile("fax006.tif", "fax"), "New Reg", "UPDATE TEST SOURCE IE", 104, domains, null);
//        documentDAO.create(document);
//
//        DocumentSearchCriteria criteria = new DocumentSearchCriteria();
//        criteria.setDomainName("beforeUpdate.ie");
//        criteria.setDocSource("UPDATE TEST SOURCE IE");
//
//        LimitedSearchResult<Document> actualDocs = documentDAO.find(criteria, 0, 1);
//        document = actualDocs.getResults().get(0);
//        int beforeUpdateDomainCount = document.getDomains().size();
//        AssertJUnit.assertEquals(beforeUpdateDomainCount, 3);
//        domains = new ArrayList<String>();
//        domains.add("elvis.ie");
//        domains.add("presley.ie");
//        domains.add("beforeUpdate.ie");
//        domains.add("afterUpdate.ie");
//        document.setDomains(domains);
//        documentDAO.update(document);
//        criteria.setDomainName("afterUpdate.ie");
//        actualDocs = documentDAO.find(criteria, 0, 1);
//        document = actualDocs.getResults().get(0);
//        int afterUpdateDomainCount = document.getDomains().size();
//        AssertJUnit.assertEquals(afterUpdateDomainCount, 4);
//    }
//
//    @Test
//    public void createDocWithCreatorId() {
//        List<String> domains = new ArrayList<String>();
//        domains.add("elvis.ie");
//        domains.add("presley.ie");
//        domains.add("jo.ie");
//        Document document = new Document(new DocumentFile("fax006.tif", "fax"), "New Reg", "CREATE TEST SOURCE IE", 104, domains, "IDL2-IEDR");
//        documentDAO.create(document);
//
//        DocumentSearchCriteria criteria = new DocumentSearchCriteria();
//        criteria.setDomainName("elvis.ie");
//        criteria.setDocSource("CREATE TEST SOURCE IE");
//        LimitedSearchResult<Document> docs = documentDAO.find(criteria, 0, 1);
//
//        AssertJUnit.assertEquals(1, docs.getResults().size());
//    }
//
//    protected List<Document> createAllDocs(List<Document> actualDocs) {
//        List<Document> docs = new ArrayList<Document>();
//        docs.add(createDocId1());
//        docs.add(createDocId2());
//        docs.add(createDocId3());
//        docs.add(createDocId4());
//        docs.add(createDocId5(actualDocs));
//        docs.add(createDocId6());
//        docs.add(createDocId7());
//        docs.add(createDocId8());
//        docs.add(createDocId9());
//        docs.add(createDocId10());
//        docs.add(createDocId11());
//        docs.add(createDocId12());
//        docs.add(createDocId13());        
//        return docs;
//    }
//
//    protected List<Document> createDocsDomain() {
//        List<Document> docs = new ArrayList<Document>();
//        docs.add(createDocId3LimitedDomain());
//        docs.add(createDocId4());
//        return docs;
//    }
//
//    protected List<Document> createDocsDomainLimited() {
//        List<Document> docs = new ArrayList<Document>();
//        docs.add(createDocId3LimitedDomain());
//        return docs;
//    }
//
//    protected List<Document> createDocsSource(List<Document> actualDocs) {
//        List<Document> docs = new ArrayList<Document>();
//        docs.add(createDocId2());
//        docs.add(createDocId3());
//        docs.add(createDocId4());
//        docs.add(createDocId5(actualDocs));
//        docs.add(createDocId7());
//        docs.add(createDocId8());
//        docs.add(createDocId9());
//        docs.add(createDocId10());
//        docs.add(createDocId11());
//        docs.add(createDocId12());
//        docs.add(createDocId13());        
//        return docs;
//    }
//
//    protected List<Document> createDocsSourceLimited() {
//        List<Document> docs = new ArrayList<Document>();
//        docs.add(createDocId3());
//        return docs;
//    }
//
//    protected List<Document> createDocsDomainSource() {
//        List<Document> docs = new ArrayList<Document>();
//        docs.add(createDocId3LimitedDomain());
//        docs.add(createDocId4());
//        return docs;
//    }
//
//    protected List<Document> createDocsDomainSourceLimited() {
//        List<Document> docs = new ArrayList<Document>();
//        docs.add(createDocId3LimitedDomain());
//        return docs;
//    }
//
//    protected List<Document> createDocsFromTo() {
//        List<Document> docs = new ArrayList<Document>();
//        docs.add(createDocId2());
//        docs.add(createDocId3());
//        docs.add(createDocId4());
//        docs.add(createDocId7());
//        docs.add(createDocId8());        
//        return docs;
//    }
//
//    protected List<Document> createDocsFromToLimited() {
//        List<Document> docs = new ArrayList<Document>();
//        docs.add(createDocId2());
//        return docs;
//    }
//
//    protected List<Document> createDocsFromToSource() {
//        List<Document> docs = new ArrayList<Document>();
//        docs.add(createDocId2());
//        docs.add(createDocId3());
//        docs.add(createDocId4());
//        return docs;
//    }
//
//    protected List<Document> createDocsFromToSourceLimited() {
//        List<Document> docs = new ArrayList<Document>();
//        docs.add(createDocId3());
//        return docs;
//    }
//
//    protected List<Document> createDocsFromToDomain() {
//        List<Document> docs = new ArrayList<Document>();
//        docs.add(createDocId3());
//        docs.add(createDocId3LimitedDomain());
//        return docs;
//    }
//
//    protected List<Document> createDocsFromToDomainLimited() {
//        List<Document> docs = new ArrayList<Document>();
//        docs.add(createDocId3LimitedDomain());
//        return docs;
//    }
//
//    protected List<Document> createDocsFromToDomainSource() {
//        List<Document> docs = new ArrayList<Document>();
//        docs.add(createDocId3LimitedDomain());
//        docs.add(createDocId4());
//        return docs;
//    }
//
//    protected List<Document> createDocsFromToDomainSourceLimited() {
//        List<Document> docs = new ArrayList<Document>();
//        docs.add(createDocId4());
//        return docs;
//    }
//
//    protected Document createDocId1() {
//        List<String> domains = new ArrayList<String>();
//        domains.add("easy.ie");
//        domains.add("george.ie");
//        return new Document(1l, new Date(1224576866000l), new DocumentFile("fax001.tif", "fax"), "Bill-C Transfer", "Indigo NOC", 100, domains, null);
//    }
//
//    protected Document createDocId2() {
//        List<String> domains = new ArrayList<String>();
//        domains.add("movie.ie");
//        return new Document(2l, new Date(1224663366000l), new DocumentFile("fax002.tif", "fax"), "Deletion", "Nell i Stas", null, domains, null);
//    }
//
//    protected Document createDocId3() {
//        List<String> domains = new ArrayList<String>();
//        domains.add("comp.ie");
//        domains.add("book.ie");
//        return new Document(3l, new Date(1224749811000l), new DocumentFile("att001.pdf", "attachment"), "New Reg", "NetNames UK", 104, domains, null);
//    }
//
//    protected Document createDocId4() {
//        List<String> domains = new ArrayList<String>();
//        domains.add("comp.ie");
//        domains.add("book.ie");
//        return new Document(4l, new Date(1224836231000l), new DocumentFile(null, "paper"), "New Reg", "NetNames UK", 104, domains, null);
//    }
//
//    protected Document createDocId5(List<Document> actualDocs) {
//        List<String> domains = new ArrayList<String>();
//        domains.add("comp.ie");
//        domains.add("book.ie");
//
//        //currentDate for that document is generated dynamicaly in sql question , that why have to get it from actualDocs list
//        Date currentDate = null;
//        for (Document d : actualDocs) {
//            if (d.getId() == 5l)
//                currentDate = d.getDate();
//        }
//        return new Document(5l, currentDate, new DocumentFile(null, "paper"), "New Reg", "NetNames UK", 104, domains, null);
//    }
//
//    protected Document createDocId6() {
//        return new Document(6l, new Date(1224576866000L), new DocumentFile("fax001.tif", "fax"), "Bill-C Transfer", "Indigo NOC", 100, new ArrayList<String>(), null);
//    }
//
//    protected Document createDocId7() {
//        return new Document(7l, new Date(1224663366000L), new DocumentFile("fax002.tif", "fax"), "Deletion", "Nell i Stas", null, new ArrayList<String>(), null);
//    }
//
//    protected Document createDocId8() {
//        return new Document(8l, new Date(1224749811000L), new DocumentFile("att001.pdf", "attachment"), "New Reg", "NetNames UK", 104, new ArrayList<String>(), null);
//    }
//
//    protected Document createDocId9() {
//        return new Document(9l, new Date(1227518231000L), new DocumentFile(null, "paper"), "New Reg", "NetNames UK", 104, new ArrayList<String>(), null);
//    }
//
//    protected Document createDocId10() {
//        return new Document(10l, new Date(1256372231000L), new DocumentFile(null, "paper"), "New Reg", "NetNames UK", 104, new ArrayList<String>(), null);
//    }
//
//    protected Document createDocId11() {
//        return new Document(11l, new Date(1256372231000L), new DocumentFile(null, "paper"), "New Reg", "NetNames UK", 104, new ArrayList<String>(), null);
//    }
//
//    protected Document createDocId12() {
//        return new Document(12l, new Date(1259054231000L), new DocumentFile(null, "paper"), "New Reg", "NetNames UK", 104, new ArrayList<String>(), null);
//    }
//
//    protected Document createDocId13() {
//        return new Document(13l, new Date(1259054231000L), new DocumentFile(null, "paper"), "New Reg", "NetNames UK", 104, new ArrayList<String>(), null);
//    }
//
//    protected Document createDocId3LimitedDomain() {
//        List<String> domains = new ArrayList<String>();
//        domains.add("comp.ie");
//        return new Document(3l, new Date(1224749811000l), new DocumentFile("att001.pdf", "attachment"), "New Reg", "NetNames UK", 104, domains, null);
//    }
//    
//
//    protected void compareDocs(Document actual, Document expected) {
//        AssertJUnit.assertEquals(actual.getId(), expected.getId());
//        AssertJUnit.assertEquals(actual.getDate(), expected.getDate());
//        AssertJUnit.assertEquals(actual.getDocPurpose(), expected.getDocPurpose());
//        AssertJUnit.assertEquals(actual.getDocSource(), expected.getDocSource());
//        AssertJUnit.assertEquals(actual.getAccountNumber(), expected.getAccountNumber());
//        AssertJUnit.assertEquals(actual.getDocumentFile().getFileType(), expected.getDocumentFile().getFileType());
//        AssertJUnit.assertEquals(actual.getDocumentFile().getFileName(), expected.getDocumentFile().getFileName());
//        AssertJUnit.assertEquals(actual.getDocumentFile().getModificationDate(), expected.getDocumentFile().getModificationDate());
//    }
//
//    protected void compareDocsDomains(Document actual, Document expected) {
//        if (actual.getDomains() == null) {
//            AssertJUnit.assertNull(expected.getDomains());
//        } else {
//            AssertJUnit.assertEquals(actual.getDomains().size(), expected.getDomains().size());
//            for (String domain : actual.getDomains())
//                AssertJUnit.assertTrue(expected.getDomains().contains(domain));
//            for (String domain : expected.getDomains())
//                AssertJUnit.assertTrue(expected.getDomains().contains(domain));
//        }
//    }
//
//    protected void compareDocList(List<Document> actualDocs, List<Document> expectedDocs) {
//        AssertJUnit.assertEquals(actualDocs.size(), expectedDocs.size());
//        // sort
//        // Comparator<Document> c = new Comparator<Document>() {
//        // public int compare(Document o1, Document o2) {
//        // return o1.getId().compareTo(o2.getId());
//        // }
//        // };
//        //        
//        // actualDocs = new ArrayList<Document>(actualDocs);
//        // Collections.sort(actualDocs, c);
//        // Collections.sort(expectedDocs, c);
//        for (int i = 0; i < actualDocs.size(); i++) {
//            compareDocs(actualDocs.get(i), expectedDocs.get(i));
//        }
//    }
}
