<?php

/**
 * Helper model for dealing with documents upload
 */
class DocumentsModel extends CFormModel {

    const FREE_FILENAME_LIMIT = 100;

    const MISSING_DOMAINNAMES_UPLOAD_STATUS = "MISSING_DOMAINNAMES";
    const CANNOT_CREATE_TMP_FILE_UPLOAD_STATUS = "CANNOT_CREATE_TMP_FILE";

    private $isSetup = false;

    public $documents;
    public $documentsDomains;

    private $docSettings;

    public function getDocumentSizeLimit() {
        return $this->getDocumentSettings()->documentSizeLimit;
    }

    public function getDocumentCountLimit() {
        return $this->getDocumentSettings()->documentCountLimit;
    }

    public function getAllowedTypes() {
        return $this->getDocumentSettings()->documentAllowedTypes;
    }

    public function setFromRequest($domainsAllowedForAssociation = array()) {
        $this->documents = CUploadedFile::getInstancesByName('documents');
        $this->documentsDomains = array();
        $allowedDomains = is_array($domainsAllowedForAssociation) ? $domainsAllowedForAssociation : array($domainsAllowedForAssociation);
        $filenames = array_map(function($file) {
            return $file->getName();
        }, $this->documents);

        if (isset($_POST['documentsDomains']) && is_array($_POST['documentsDomains'])) {
            foreach ($_POST['documentsDomains'] as $documentName => $listOfDomains) {
                if (in_array($documentName, $filenames)) {
                    // checkboxes are passed as array arry[document][domain], only those selected
                    // are uploaded by browser with value "on"
                    $listOfDomains = array_keys($listOfDomains);
                    $domains = array_intersect($listOfDomains, $allowedDomains);
                    $this->documentsDomains[$documentName] = $domains;
                }
            }
        } else if (count($allowedDomains) == 1) {
            // if only one allowedDomain is present, assume every document is assigned with that domain
            $domainName = $allowedDomains[0];
            foreach ($filenames as $filename) {
                $this->documentsDomains[$filename] = array($domainName);
            }
        }
        $this->isSetup = true;
    }

    public function isSetup() {
        return $this->isSetup;
    }

    public function uploadDocuments($purpose) {
        if (!$this->isSetup) {
            throw new CException("Trying to upload documents without first initializing documents model");
        }

        $serviceResult = array();
        $notUploadedDocumentsResult = array();
        $backend_errors = array();
        $uploads = array();
        foreach ($this->documents as $document) {
            $documentName = $document->getName();
            if (!empty($this->documentsDomains[$documentName])) {
                $filename = $this->prepareFileForUpload($document);
                if (!is_null($filename)) {
                    $upload = new CRSDocumentAppService_documentUploadVO();
                    $upload->filename = $filename;
                    $upload->domains = $this->documentsDomains[$documentName];
                    $uploads[] = $upload;
                } else {
                    Yii::log("Could not prepare upload of file $documentName", "error");
                    $notUploadedDocumentsResult[] = $this->createUploaderResult($documentName, DocumentsModel::CANNOT_CREATE_TMP_FILE_UPLOAD_STATUS);
                }
            } else {
                Yii::log("Document $documentName was uploaded with no matching domain names, was ignored as a result", "warning");
                $notUploadedDocumentsResult[] = $this->createUploaderResult($documentName, DocumentsModel::MISSING_DOMAINNAMES_UPLOAD_STATUS);
            }
        }
        if (!empty($uploads)) {
            CRSDocumentAppService_service::handleUpload($serviceResult, $backend_errors, Yii::app()->user->authenticatedUser, $uploads, $purpose);
            if (count($backend_errors) > 0) {
                Yii::log("Failed to upload documents: " . WSAPIError::getErrorsNotEmpty($backend_errors), "error", ' ');
                $this->addError("documents", "Error while saving documents on the server. Please notify IEDR of the problem you've just encountered.");
                return array('error' => $backend_errors);
            }
        } else {
            $uploadCount = count($this->documents);
            Yii::log("There were $uploadCount documents uploaded, but none was suitable for upload", "warning");
        }
        if (!is_array($serviceResult)) {
            $serviceResult = array($serviceResult);
        }
        return array('result' => array_merge($serviceResult, $notUploadedDocumentsResult));
    }

    public function rules() {
        return array(
            array('documents', 'file',
                'allowEmpty' => true,
                'types' => $this->getAllowedTypes(),
                'maxFiles' => $this->getDocumentCountLimit(),
                'maxSize' => $this->getDocumentSizeLimit()
            )
        );
    }

    private function getDocumentSettings() {
        if (is_null($this->docSettings)) {
            $response = null;
            $backend_errors;
            CRSDocumentAppService_service::getDocumentSettings($response, $backend_errors, Yii::app()->user->authenticatedUser);
            if (empty($backend_errors)) {
                $this->docSettings = $response;
            } else {
                Yii::log("Failed to get document settings: " . WSAPIError::getErrorsNotEmpty($backend_errors), "error", ' ');
                throw new CException("Filed to get document settings");
            }
        }
        return $this->docSettings;
    }

    private function prepareFileForUpload($document) {
        $upload_dir = Yii::app()->params['uploader_share_dir'];
        if (!is_dir($upload_dir)) {
            Yii::log("Mount for uploading document is not a directory: $upload_dir", "error", ' ');
            throw new CException("Missing upload directory setting");
        }

        $path_parts = pathinfo($document->getName());
        // protection against path traversal
        $filename = $path_parts['basename'];
        $filename_fullpath = join(DIRECTORY_SEPARATOR, array($upload_dir, $filename));
        $i = 1;
        while (file_exists($filename_fullpath)) {
            $filename = $path_parts['filename'] . "-" . $i++ . "." . $path_parts['extension'];
            $filename_fullpath = join(DIRECTORY_SEPARATOR, array($upload_dir, $filename));
            if ($i > DocumentsModel::FREE_FILENAME_LIMIT) {
                return NULL;
            }
        }
        move_uploaded_file($document->getTempName(), $filename_fullpath);
        chmod($filename_fullpath, 0644);
        return $filename;
    }

    private function createUploaderResult($docName, $status) {
        $tmpResult = new CRSDocumentAppService_uploadResultVO();
        $tmpResult->documentName = $docName;
        $tmpResult->status = $status;
        return $tmpResult;
    }
}
