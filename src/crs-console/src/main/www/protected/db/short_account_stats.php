<?php

/**
 * defines functions to do with the fetching of account statistics data
 * 
 * @category  NRC
 * @package   IEDR_New_Registrars_Console
 * @author    IEDR <asd@iedr.ie>
 * @copyright 2011 IEDR
 * @license   http://www.iedr.ie/ (C) IEDR 2011
 * @version   CVS: $Id:$
 * @link      https://statistics.iedr.ie/
 */

/**
 * gets, for a Nic Handle, via cache, Deposit Balance
 * 
 * @param  string $nic Nic Handle
 * @return float  Deposit Balance
 */

function getDepositBalance($nic) { 
      $result = new CRSPaymentAppService_depositVO();
      $result->availableFunds = 0.0;
		$errors = null;
		
		CRSPaymentAppService_service::viewDeposit($result, $errors, Yii::app()->user->authenticatedUser);
      if($result != null) {
         if(count($errors) != 0) {
            Yii::log('viewDeposit has some errors = '.print_r($errors, true));
         }
      } else {
         Yii::log('CRSPaymentAppService_service::viewDeposit is null'.print_r($errors, true));
      }
       
      return $result->availableFunds;
	}
