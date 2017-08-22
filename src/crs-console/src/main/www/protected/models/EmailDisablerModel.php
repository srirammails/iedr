<?php

class EmailDisablerModel extends GridModelBase
{
	public $defaultSortColumn = 'PK';
	
	public $disabledValuesByEmailId = array();
	
	public $successSubmitMessage = '';
	
	public $errorSubmitMessage = '';

	public $errorDisplayMessage = '';
	
	public $viewDataGrouped = array();
	
	public $columns = array(
			'PK' => array(
					'label' => 'Email ID',
					'width' => 5,
					'type' => 'eID',
			),
			'B' => array(
					'label' => 'Subject',
					'width' => 25,
					'type' => 'string',
			),
			'C' => array(
					'label' => 'External Recipients',
					'criteriafield' => null,
					'width' => 20,
					'type' => 'externalRecipients',
			),
			'D' => array(
					'label' => "Reason email can\'t be disabled",
					'width' => 20,
					'type' => 'string',
			),
				
			'E' => array(
					'label' => 'Current status',
					'width' => 10,
					'type' => 'disabledStatus',
			),
		);
	public function getSearch($searchparams) {
		return;
	}
	protected function getExportFilenameTag()
	{
		return 'EmailDisablerModel_';
	}

	public function addResults($o)
	{
		ModelUtility::hasProperty($this->columns, $o);
		$toList = is_array($o->emailTemplate->toList) ? implode(', ', $o->emailTemplate->toList) : $o->emailTemplate->toList;
		$ccList = is_array($o->emailTemplate->ccList) ? implode(', ', $o->emailTemplate->ccList) : $o->emailTemplate->ccList;
		$bccList = is_array($o->emailTemplate->bccList) ? implode(', ', $o->emailTemplate->bccList) : $o->emailTemplate->bccList;
		return array
		(
				'PK' => encode($o->emailTemplate->id),
				'B' => encode($o->emailTemplate->subject),
				'C' => array( 
						'to' => encode($toList),
						'cc' => encode($ccList),
						'bcc' => encode($bccList),
						),
				'D' => encode($o->emailTemplate->sendReason),
				'E' => array( 
						'eId' => encode($o->emailTemplate->id),
						'disabled' => encode($o->disabled),
						'suppressible' => encode($o->emailTemplate->suppressible),
						'nonSuppressibleReason' => encode($o->emailTemplate->nonSuppressibleReason),
						)
		);
	}

}
