package pl.nask.crs.documents.manager;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;

import pl.nask.crs.commons.config.ApplicationConfig;
import pl.nask.crs.commons.config.IncomingDocsConfig;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.documents.DocumentFile;
import pl.nask.crs.documents.DocumentFileType;
import pl.nask.crs.documents.IncomingDocumentsManager;
import pl.nask.crs.documents.exception.DocumentFileMovingException;
import pl.nask.crs.documents.exception.DocumentGeneralException;
import pl.nask.crs.documents.exception.NoSuchDirectoryException;
import pl.nask.crs.documents.exception.NoSuchDocumentException;
import pl.nask.crs.documents.exception.WrongFaxFileExtensionException;

/**
 * @author Piotr Tkaczyk
 * @author Marianna Mysiorska
 * @author Artur Gniadzik
 */

public class IncomingDocumentsManagerImpl implements IncomingDocumentsManager {

    public static final int FILENAME_INDEX_LIMIT = 1000;
    private static Logger LOGGER = Logger.getLogger(IncomingDocumentsManagerImpl.class);

    private final static String COMPRESSIONED_RESOURCE_POSTFIX = ".gz";

    private ApplicationConfig applicationConfig;

    private boolean ignoreFiles = true;  

    private final String consoleUploadDir;
    private final String mailUploadDir;
    private final String documentsDir;

    public IncomingDocumentsManagerImpl(String consoleUploadDir, String mailUploadDir, String documentsDir) {
        this.consoleUploadDir = consoleUploadDir;
        this.mailUploadDir = mailUploadDir;
        this.documentsDir = documentsDir;
    }

    @Override
    public List<DocumentFile> getNewDocumentFiles() throws NoSuchDirectoryException, WrongFaxFileExtensionException {
        List<DocumentFile> newDocs = new ArrayList<DocumentFile>();

        IncomingDocsConfig cfg = applicationConfig.getIncomingDocsConfig();

        for (String mappingKey: cfg.getMappingKeys()){
            DocumentFileType docFileType = DocumentFileType.valueOf(mappingKey);
            if (!docFileType.isAssigned()) {
                File docDir = getDirectoryAsFile(cfg.getMappingFor(mappingKey));

                File[] files = docDir.listFiles();

                for (File f : files) {
                    DocumentFile documentFile = getDocumentFile(f.getName(), docFileType);
                    if (documentFile != null)
                        newDocs.add(documentFile);
                }
            }
        }

        return newDocs;
    }

    @Override
    public int getNewDocumentFilesCount() throws NoSuchDirectoryException, WrongFaxFileExtensionException {
        return getNewDocumentFiles().size();
    }

    @Override
    public void markDocumentFileAsAssigned(DocumentFile documentFile) throws DocumentGeneralException {
        Validator.assertNotNull(documentFile, "Document cannot be null.");

        DocumentFileType docFileType = documentFile.getFileType();
        Validator.assertNotNull(docFileType, "Document file type cannot be null");

        if (docFileType.isAssigned())
            throw new DocumentGeneralException("Assigned document cannot be assignned onec again.");

        File tempFile = getFileByDocument(documentFile);

        if (!tempFile.exists())
            throw new NoSuchDocumentException("File: " + documentFile.getFileName() + " by path: " + tempFile.getAbsolutePath());


        final String assigedDir = getDirMapping(DocumentFileType.getOppositType(docFileType));

        final File assignedFileDir = getDirectoryAsFile(assigedDir);

        final String newFilename = DocumentFile.getNormalizedFilenameWithAddDate(documentFile.getFileName(), documentFile.getAddDate());
        final File destFile = new File(assignedFileDir, newFilename);

        try {
            FileUtils.moveFile(tempFile, destFile);
        } catch (IOException e) {
            throw new DocumentFileMovingException("Document file: " + documentFile.getFileName() + " cannot be moved to assigned directory.", e);
        }
    }

    @Override
    public void markFileAs(DocumentFileType type, File file) throws NoSuchDirectoryException, NoSuchDocumentException, DocumentFileMovingException {
        if (!file.exists())
            throw new NoSuchDocumentException("File by path: " + file.getAbsolutePath());

        String targetDir = getDirMapping(type);
        File targetFileDir = getDirectoryAsFile(targetDir);

        final String origFilename = file.getName();
        File targetFile = new File(targetFileDir, origFilename);

        int i = 1;
        while (i < FILENAME_INDEX_LIMIT && targetFile.exists()) {
            targetFile = new File(targetFileDir, indexedFilename(origFilename, i++));
        }
        if (!targetFile.exists()) {
            try {
                FileUtils.moveFile(file, targetFile);
            } catch (IOException e) {
                throw new DocumentFileMovingException("Document file: " + file.getAbsolutePath() + " cannot be moved to assigned directory.", e);
            }
        } else {
            LOGGER.warn("Tried to mark file " + origFilename + " as " + type.name() + " but the name is already taken, ignoring");
        }

    }

    private String indexedFilename(String name, int index) {
        return FilenameUtils.getBaseName(name) + "-" + Integer.toString(index) + "." + FilenameUtils.getExtension(name);
    }

    public void setIgnoreFilesWithUnsupportedExtension(boolean ignoreFiles) {
        this.ignoreFiles = ignoreFiles;
    }

    public void setApplicationConfig(ApplicationConfig applicationConfig) {
        this.applicationConfig = applicationConfig;
    }

    @Override
    public DocumentFile getDocumentFile(String fileName, DocumentFileType docFileType) throws NoSuchDirectoryException, WrongFaxFileExtensionException {
        if (!checkFileExtension(fileName))
            return null;

        File dir = getDirectoryAsFile(getDirMapping(docFileType));
        File file = new File(dir, fileName);
        if (!file.exists() || !file.isFile())
            return null;

        DocumentFile documentFile = new DocumentFile(file.getName(), docFileType);
        Date modifcationDate = new Date(file.lastModified());
        documentFile.setModificationDate(modifcationDate);
        return documentFile;
    }

    public void deleteDocumentFile(DocumentFile documentFile) throws DocumentFileMovingException, NoSuchDirectoryException, WrongFaxFileExtensionException, NoSuchDocumentException {
        Validator.assertNotNull(documentFile, "document file");
        String fileName = documentFile.getFileName();
        Validator.assertNotEmpty(fileName, "file name");

        if (!checkFileExtension(fileName))
            throw new WrongFaxFileExtensionException("File: " + fileName + " cannot be deleted");

        deleteFile(documentFile);
    }

    public void deleteFile(DocumentFile documentFile) throws DocumentFileMovingException, NoSuchDocumentException, NoSuchDirectoryException {
        DocumentFileType fileType = documentFile.getFileType();
        String fileName = documentFile.getFileName();
        Validator.assertNotEmpty(fileName, "file name");
        Validator.assertNotNull(fileType, "file type");

        File file = getFileByDocument(documentFile);
        if (!file.exists() || !file.isFile()) {
            throw new DocumentFileMovingException("File: " + fileName + " cannot be deleted. Doesn't exist or is a directory.");
        }
        if (!file.delete()) {
            throw new DocumentFileMovingException("File: " + fileName + " cannot be deleted.");
        }
    }

    public boolean documentFileExists(DocumentFile documentFile) throws NoSuchDirectoryException, WrongFaxFileExtensionException {
        Validator.assertNotNull(documentFile, "document file");
        String fileName = documentFile.getFileName();
        String compressedFileName = fileName + COMPRESSIONED_RESOURCE_POSTFIX;
        DocumentFileType fileType = documentFile.getFileType();
        Validator.assertNotEmpty(fileName, "file name");
        Validator.assertNotNull(fileType, "file type");

        if (!checkFileExtension(fileName))
            return false;

        File dir = getDirectoryAsFile(getDirMapping(fileType));
        File file = new File(dir, fileName);
        File compressedFile = new File(dir, compressedFileName);
        return (file.exists() && file.isFile()) || (compressedFile.exists() && compressedFile.isFile());
    }

    private boolean checkFileExtension(String fileName) throws WrongFaxFileExtensionException {
        int dotLastIndex = fileName.lastIndexOf('.');
        if (dotLastIndex == -1 || dotLastIndex == 0 || dotLastIndex + 1 == fileName.length()) {
            LOGGER.warn("File without extension : " + fileName);
            if (ignoreFiles) {
                return false;
            } else {
                throw new WrongFaxFileExtensionException("" + fileName);
            }
        }
        String fileExtension = fileName.substring(fileName.lastIndexOf('.') + 1);

        if (!applicationConfig.getDocumentAllowedTypes().contains(fileExtension.toLowerCase())) {
            LOGGER.warn("File with not allowed extension: " + fileName);
            if (ignoreFiles) {
                return false;
            } else {
                throw new WrongFaxFileExtensionException("" + fileName);
            }
        }

        return true;
    }

    @Override
    public String getDirMapping(DocumentFileType documentFileType) {
        return applicationConfig.getIncomingDocsConfig().getMappingFor(documentFileType.name());
    }

    @Override
    public File getFileByDocument(DocumentFile docFile) throws NoSuchDirectoryException, NoSuchDocumentException {
        File dir;
        switch (docFile.getFileType()) {
        case UPLOAD_VIA_MAIL:
            dir = new File(mailUploadDir);
            break;
        case UPLOAD_VIA_CONSOLE:
            dir = new File(consoleUploadDir);
            break;
        default:
            dir = new File(documentsDir, getDirMapping(docFile.getFileType()));
            break;
        }
        if (!dir.isDirectory())
            throw new NoSuchDirectoryException();

        File result = new File(dir, docFile.getFileName());
        if (!result.isFile())
            throw new NoSuchDocumentException("No document named " + result + " in directory " + dir);
        return result;
    }

    @Override
    public File getDirectory(DocumentFileType documentFileType) {
        return new File(documentsDir, getDirMapping(documentFileType));
    }

    private File getDirectoryAsFile(String dir) throws NoSuchDirectoryException {
        Validator.assertNotEmpty(dir, "Directory value cannot be null or empty.");

        File serverDir = new File(documentsDir);
        if (!serverDir.exists() || !serverDir.isDirectory())
            throw new NoSuchDirectoryException(serverDir.getAbsolutePath());

        File fileDir = new File(serverDir, dir);

        if (!fileDir.exists() || !fileDir.isDirectory()) {
            throw new NoSuchDirectoryException(fileDir.getAbsolutePath());
        }

        return fileDir;
    }
}
