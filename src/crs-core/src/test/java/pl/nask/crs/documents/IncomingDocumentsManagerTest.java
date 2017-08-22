package pl.nask.crs.documents;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.AssertJUnit;
import java.io.File;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import pl.nask.crs.documents.exception.NoSuchDirectoryException;
import pl.nask.crs.documents.exception.NoSuchDocumentException;

/**
 * @author Piotr Tkaczyk
 * @author Marianna Mysiorska
 */
public class IncomingDocumentsManagerTest extends AbstractContextAwareTest {
	@Resource
	private IncomingDocumentsManager incomingDocumentsManager;
	
    private List<String> testFaxFiles = Arrays.asList("1.tiff", "2.TIFF", "3.tif", "4.jpg", "file", "wrong.", "aaa.bbb.ccc");

    @BeforeMethod
	public void init() throws Exception {       
        removeTestDirectory();
    }

    @Test (expectedExceptions = NoSuchDirectoryException.class)
    public void testNewFaxDirectoryNotExist() throws Exception {
        incomingDocumentsManager.getNewDocumentFiles();
    }

    @Test (expectedExceptions = NoSuchDirectoryException.class)
    public void testAssignedFaxDirectoryNotExist() throws Exception {
        DocumentFile documentFile = new DocumentFile("test123.tiff", DocumentFileType.FAX_NEW);        
        incomingDocumentsManager.markDocumentFileAsAssigned(documentFile);
    }

    @Test
    public void testGetNewDocuments() throws Exception {
        createTestDirectory();

        List<DocumentFile> documentFileList = incomingDocumentsManager.getNewDocumentFiles();

        AssertJUnit.assertNotNull(documentFileList);
        AssertJUnit.assertEquals(3, documentFileList.size());

        for(DocumentFile documentFile : documentFileList)
            AssertJUnit.assertTrue(testFaxFiles.contains(documentFile.getFileName()));

        removeTestDirectory();
    }

    @Test
    public void testMarkAsAssigned() throws Exception {
        createTestDirectory();
        String testFilename = "1.tiff";
        String newFilename = DocumentFile.getNormalizedFilenameWithAddDate(testFilename, null);

        DocumentFile document = new DocumentFile(testFilename, DocumentFileType.FAX_NEW);
        incomingDocumentsManager.markDocumentFileAsAssigned(document);
        DocumentFile assignedDocument = incomingDocumentsManager.getDocumentFile(newFilename, DocumentFileType.FAX_ASSIGNED);

        AssertJUnit.assertNotNull(assignedDocument);
        AssertJUnit.assertEquals(newFilename, assignedDocument.getFileName());


        File newDir = incomingDocumentsManager.getDirectory(DocumentFileType.FAX_NEW);
        File[] otherFiles = newDir.listFiles();

        AssertJUnit.assertEquals(6, otherFiles.length);

        File assignedDir = incomingDocumentsManager.getDirectory(DocumentFileType.FAX_ASSIGNED);
        File[] assignedFiles = assignedDir.listFiles();

        AssertJUnit.assertEquals(1, assignedFiles.length);

        removeTestDirectory();
    }

    @Test(expectedExceptions = NoSuchDocumentException.class)
    public void testMarkAsAssignedFail() throws Exception {
        createTestDirectory();

        DocumentFile document = new DocumentFile("notexists.tiff", DocumentFileType.FAX_NEW);
        incomingDocumentsManager.markDocumentFileAsAssigned(document);

        removeTestDirectory();
    }

    private void createTestDirectory() throws Exception {    	       
        File faxNewDirectory = incomingDocumentsManager.getDirectory(DocumentFileType.FAX_NEW);
        if(faxNewDirectory.exists())
            throw new Exception("Directory allready exists: " + faxNewDirectory);

        faxNewDirectory.mkdirs();

        File faxAssignedDirectory = incomingDocumentsManager.getDirectory(DocumentFileType.FAX_ASSIGNED);
        faxAssignedDirectory.mkdir();

        for (String testFaxFile : testFaxFiles) {
            File faxFile = new File(faxNewDirectory, testFaxFile);
            faxFile.createNewFile();
        }
    }

    private void removeTestDirectory() throws NoSuchDirectoryException {
        File faxNewDirectory = incomingDocumentsManager.getDirectory(DocumentFileType.FAX_NEW);
        deleteDirectory(faxNewDirectory);

        File assignedFaxesDir = incomingDocumentsManager.getDirectory(DocumentFileType.FAX_ASSIGNED);
        deleteDirectory(assignedFaxesDir);
    }

    private boolean deleteDirectory(File file) {
        File[] files = file.listFiles();
        if (files == null) return true;
        for ( File f : files) {
            if (f.isDirectory()) {
                deleteDirectory(f);
            } else {
                f.delete();
            }
        }

        return file.delete();
    }
}
