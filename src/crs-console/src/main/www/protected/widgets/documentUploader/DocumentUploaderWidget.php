<?php

class DocumentUploaderWidget extends CWidget {

    public $uploaderModel = null;
    public $domains = array();

    public function run() {
        $hasMultipleDomains = is_array($this->domains) && count($this->domains) > 1;

        $this->render('application.widgets.documentUploader.view', array(
            'uploader' => $this->uploaderModel,
            'multipleDomains' => $hasMultipleDomains,
            'domains' => $this->domains,
        ));
    }
}
