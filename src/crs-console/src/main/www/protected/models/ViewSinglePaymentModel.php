<?php

class ViewSinglePaymentModel extends CFormModel {
	
	/**
	 * holds the transaction data
	 * 
	 * @var unknown
	 */
	public $transaction;
	
	public $transactionId;

	public $backend_errors;
	
	public function __construct($transactionId) {
		$this->transactionId = $transactionId;
		CRSPaymentAppService_service::getTransactionInfo($this->transaction, $this->backend_errors, Yii::app()->user->authenticatedUser, $transactionId);
	}
}