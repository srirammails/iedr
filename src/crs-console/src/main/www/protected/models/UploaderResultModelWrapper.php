<?php

class UploaderResultModelWrapper {
    public static $status_to_human_readable = array(
        CRSDocumentAppService_uploadStatusVO::_OK => "Uploaded OK",
        CRSDocumentAppService_uploadStatusVO::_FILE_TOO_BIG => "File too big",
        CRSDocumentAppService_uploadStatusVO::_WRONG_FILE_TYPE => "Wrong file type",
        CRSDocumentAppService_uploadStatusVO::_UNKNOWN_DOMAINNAME => "Unknown domain name",
        CRSDocumentAppService_uploadStatusVO::_UPLOAD_NOT_BY_BILL_C => "Uploader is not bill_c",
        DocumentsModel::MISSING_DOMAINNAMES_UPLOAD_STATUS => "No domains selected for document",
        DocumentsModel::CANNOT_CREATE_TMP_FILE_UPLOAD_STATUS => "Could not upload file, try again later",
    );

    public $model;

    public function __construct($new_model = null) {
        $this->model = $new_model;
    }

    public function isEmpty() {
        return is_null($this->model) || (isset($this->model['result']) && empty($this->model['result']));
    }

    public function hasErrors() {
        if (is_null($this->model))
            return false;
        return isset($this->model['error']) || count($this->getDocsWithErrors());
    }

    public function getDocsWithErrors() {
        return array_filter($this->model['result'], function($e) {
            return $e->status != CRSDocumentAppService_uploadStatusVO::_OK;
        });
    }
    public function getHumanReadableResults() {
        return array_map(function($elem) {
            return array(
                'documentName' => $elem->documentName,
                'status' => isset(UploaderResultModelWrapper::$status_to_human_readable[$elem->status]) ? UploaderResultModelWrapper::$status_to_human_readable[$elem->status] : "Unknown error",
            );
        }, $this->model['result']);
    }
}