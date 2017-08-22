<?php

/**
 * file which defines AccountsGridController class
 * 
 * @category  NRC
 * @package   IEDR_New_Registrars_Console
 * @author    IEDR <asd@iedr.ie>
 * @copyright 2011 IEDR
 * @license   http://www.iedr.ie/ (C) IEDR 2011
 * @version   CVS: $Id:$
 * @link      https://statistics.iedr.ie/
 * @see       References to other sections (if any)...
 */

/**
 * Specialised GridController for handling grids with invoices and account-payment related actions
 * 
 * @category  NRC
 * @package   IEDR_New_Registrars_Console
 * @author    IEDR <asd@iedr.ie>
 * @copyright 2011 IEDR
 * @license   http://www.iedr.ie/ (C) IEDR 2011
 * @version   Release: @package_version@
 * @link      https://statistics.iedr.ie/
 */
class AccountsGridController extends GridController {

   /**
    * filename to use with grid data export functions in current request
    * @var    string
    * @access protected
    */
   protected $exportFileName;

   /**
    * action which renders menu
    * 
    * @return void  
    * @access public
    */
   public function actionMenu() {
      $this->render('menu');
   }

   /**
    * get base path for for view to display ; views/accountsgrid/ contains shared view confirm_action.php and results.php
    * 
    * @return string    Return description (if any) ...
    * @access protected
    */
   protected function getViewBase() {
      return '/accountsgrid/';
   }

   /**
    * Override of GridController function called before rendering confirm page; sets model data according to request type
    * 
    * @param  object    $gs_model model for current request
    * @return void     
    * @access protected
    */
   protected function processGridActionCommand($model) {
      parent::processGridActionCommand($model);
      switch ($model->command) {
         case 'payonline':
            $model->needCreditCard = 1;
            break;
      }
   }

   public function setStatusDomain($domainName, &$model, $action) {
      $result = null;
      $errors = '';

      CRSDomainAppService_service::view($result, $errors, Yii::app()->user->authenticatedUser, $domainName);
      if ($result != null) {
         switch ($action) {
            case 'autorenew':
               $model->commandresults['message'][] = $result->domain->dsmState->renewalMode;
               break;
            case 'voluntary':
               $model->commandresults['message'][] = $result->domain->dsmState->shortNrpStatus;
               break;
         }
      } else {
         $model->commandresults['message'] = $errors;
      }
      $model->commandresults[$action][] = $result->domain;
   }

   /**
    * command execution after confirmation-accept, makes calls to back-end systems
    * 
    * makes calls to back-end payment or msd functions for selected invoices or domains, depending on command chosen
    * 
    * @param  mixed     $model model for current request
    * @return void     
    * @access protected
    */
   protected function execCommand($model) {
      $model->commandresults = array();
      if (!isset($model->euros_amount))
         $model->euros_amount = 0.0;

      $pRequest = ($model->needCreditCard) ? CreditCardFormSegmentModel::asWSAPIPaymentObject($model) : null;
      $isTest = '0';
      $domains = array();
      $this->backend_errors = array();
      $result = null;
      $user = Yii::app()->user->authenticatedUser;

      foreach ($model->domainListToArray() as $domain) {
         $domains[] = $domain;
      }

      Yii::log('ACCOUNTS GRID CONTROLLER execCommand ' . print_r($model->command, true));

      switch ($model->command) {
         case 'paydeposit':
            $paymentRequest = CreditCardFormSegmentModel::asWSAPIPaymentObject($model);
            $domains = GridUtility::getDomainsWithPeriod($model);
            CRSPaymentAppService_service::pay($result, $this->backend_errors, $user, $domains, 'ADP', null, $isTest);
            if ($result != NULL) {
               Yii::log('PAYDEPOSIT NOT NULL ' . print_r($result, true));
            } else {
               Yii::log('PAYDEPOSIT IS NULL' . print_r($this->backend_errors, true));
            }
            break;
         case 'payonline':
            $paymentRequest = CreditCardFormSegmentModel::asWSAPIPaymentObject($model);
            $domains = GridUtility::getDomainsWithPeriod($model);
            CRSPaymentAppService_service::pay($result, $this->backend_errors, $user, $domains, GridUtility::getCardType($model), $paymentRequest, $isTest);
            if ($result != NULL) {
               Yii::log('PAYONLINE NOT NULL ' . print_r($result, true));
            } else {
               Yii::log('PAYONLINE IS NULL ' . print_r($this->backend_errors, true));
            }
            break;
         case 'voluntary':
            for ($i = 0; $i < count($domains); $i++) {
               CRSDomainAppService_service::enterVoluntaryNRP($this->backend_errors, $user, $domains[$i]);
               if (count($this->backend_errors) == 0) {
                  Yii::log('ENTER VOLUNTARY NRP SUCCESSFUL');
               } else {
                  Yii::log('ENTER VOLUNTARY NRP NOT SUCCESSFUL ' . print_r($this->backend_errors, true));
               }
               $this->setStatusDomain($domains[$i], $model, 'voluntary');
               $this->backend_errors = '';
            }
            break;
         case 'removefromvoluntary':
            Yii::log('removefromvoluntary command ' . print_r($domains, true));
            $tmpDomains = array();
            $errors = array();
            for ($i = 0; $i < count($domains); $i++) {
                $domainName = $domains[$i];
                CRSDomainAppService_service::removeFromVoluntaryNRP($this->backend_errors, Yii::app()->user->authenticatedUser, $domainName);
               if (count($this->backend_errors) == 0) {
                  Yii::log("REMOVEFROMVOLUNTARY SUCCESSFULL " . $domainName);
                  $tmpDomains[] = $domainName;
               } else {
                  Yii::log("REMOVEFROMVOLUNTARY NOT SUCCESSFULL " . $domainName . " " . print_r($this->backend_errors,true));
                  $errors[] = $domainName;
               }
               $model->commandresults['removefromvoluntaryfailed'] = $errors;
               $model->commandresults['removefromvoluntary'] = $tmpDomains;
            }
            break;
         case 'autorenew':
            $domains = GridUtility::getDomainsWithRenewalMode($model);
            Yii::log('MODEL AUTO RENEW= '.print_r($domains,true));
            foreach ($domains as $domain => $renewalMode) {
               CRSDomainAppService_service::modifyRenewalMode($this->backend_errors, $user, $domain, $renewalMode);
               if (count($this->backend_errors) == 0) {
                  Yii::log('AUTO RENEW SUCCESSFULL ' . $domain);
               } else {
                  Yii::log('AUTO RENEW NOT SUCCESSFULL' . $domain . '  ' . print_r($this->backend_errors,true));
               }
               $this->setStatusDomain($domain, $model, 'autorenew');
               $this->backend_errors = '';
            }
            break;
         default:
            Yii::log('DEFAULT OPTION!!!');
            break;
      }

      if ($result != NULL) {
         switch ($model->command) {
            case 'voluntary':
               // 
               break;
            case 'autorenew':
               //
               break;
            case 'payonline':
               $model->commandresults['payonline'] = $result;
               break;
            case 'paydeposit':
               $model->commandresults['paydeposit'] = $result;
               break;
         }
      } else {
         if ($model->command != 'voluntary' && $model->command != 'autorenew') {
            $model->commandresults['ERROR'] = WSAPIError::getErrors($this->backend_errors);
         }
      }
   }

   /**
    * returns export filename prefix for grid data export
    * 
    * @return string   filename prefix
    * @access protected
    * @see actionGetgriddata
    */
   protected function getExportFilenameTag() {
      return $this->exportFileName;
   }

   /**
    * calls model->addResults
    * 
    * @param  object    $model     model to add results to
    * @param  unknown   $resultObj result passed to model->addResults
    * @return object    array returned by model->addResults
    * @access protected
    */
   protected function addResult($model, $resultObj) {
      return $model->addResults($resultObj);
   }

   // overridden GridController functions

   /**
    * specialises parent class's getdata function to get invoices instead of domains
    * 
    * @param  object    $model    model containing request data
    * @param  object    $criteria search criteria from jqGrid
    * @param  unknown   $sort     sort parameters from jqGrid
    * @param  integer   $perpage  records per page
    * @param  integer   $pagenum  page number to fetch
    * @return array     array of data for formatting as JSON, CSV, etc
    * @access protected
    */
   protected function getData($model, $criteria, $sort, $perpage, $pagenum) {
      $pagedata = array('records' => 0, 'page' => $pagenum, 'total' => 0, 'rows' => array());
      $offset = $perpage * ($pagenum - 1);
      $limit = $perpage;

      $result = null;

      $dates = '';
      if (isset($model->date)) {
         if ($model->date == '1') {
            $currentDate = strtotime(date('Y-m-d'));
            $temporaryDate = strtotime('+1 month', $currentDate);
            $dates = date("Y-m-d", $temporaryDate);
            Yii::log('DATES= ' . print_r($dates, true));
         } else if ($model->date == '0') {
            $dates = date("Y-m-d");
         } else {
            $dates = $model->date;
            $dates .= '-01';
            $dates = date("Y-m-d", strtotime($dates));
         }
      }
      
      Yii::log('furutre rew! '.print_r($dates,true));
      CRSDomainAppService_service::getDomainsForRenewalFuturePayment($result, $this->backend_errors, Yii::app()->user->authenticatedUser, $dates,$criteria, $offset, $limit, $sort);
      if (count($this->backend_errors) == 0 && $result != null) {
         if ($result->totalResults != 0) {
            $pagedata['records'] = $result->totalResults;
            $pagedata['total'] = math_div($result->totalResults, $perpage) + 1;
            if (is_array($result->results)) {
               Yii::log('IS_ARRAY');
               for ($i = 0; $i < count($result->results); $i++) {
                  $r = $this->addResult($model, $result->results[$i]);
                  if ($r != null)
                     $pagedata['rows'][] = $r;
               }
            } else {
               if (is_object($result->results)) {
                  Yii::log('IS_OBJECT1= ' . print_r($result->results, true));
                  $row = $model->addResults($result->results);
                  $pagedata['rows'][] = $row;
               } else {
                  Yii::log('no results : ' . print_r($result, true), 'warning', 'AccountsGridController::getData()' . __LINE__);
               }
            }
         } else {
            Yii::log('No records found.');
         }
      } else {
         Yii::log('AccountsGridController::getData() null result ' . print_r($this->backend_errors, true));
      }
      return $pagedata;
   }

}

