<?php

/**
 * defines TicketsController class
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
 * Controller for Ticket Grid
 * 
 * @category  NRC
 * @package   IEDR_New_Registrars_Console
 * @author    IEDR <asd@iedr.ie>
 * @copyright 2011 IEDR
 * @license   http://www.iedr.ie/ (C) IEDR 2011
 * @version   Release: @package_version@
 * @link      https://statistics.iedr.ie/
 * @see       References to other sections (if any)...
 */
class TicketsController extends GridController {

   const SESSION_KEY_DOCUMENT_UPLOAD_RESULT = 'document_upload_result_for_ticket_';

   /**
    * used to display breadcrumb trail on top of page. deprecated; prefer {@link printBreadcrumbTrail()}
    * 
    * @var    array 
    * @access public
    */
   public $breadcrumbs = array('Tickets'); 

   /**
    * render ticket grid, showing tickets created in the last N days ('days' defaults to 100, set in protected/config/main.php)
    * 
    * @return void  
    * @access public
    * @see    main.php, AllTicketsModel
    */
   public function actionMain() {
      $model = new AllTicketsModel();
      Yii::log(print_r($_POST, true), 'debug', 'TicketsController::actionMain()');
      if (Yii::app()->request->isPostRequest && isset($_POST['AllTicketsModel'])) {
         $model->attributes = $_POST['AllTicketsModel'];
      } else {
         if(isset($_POST['days'])) {
            $model->days = $_POST['days'];
         }
      }
      Yii::log(print_r($model, true), 'debug', 'TicketsController::actionMain()');
      Utility::writeActionToSession('tickets/main');
      $this->render('/tickets/main', array('model' => $model));
   }

   /**
    * render ticket-view page, conditionally displaying the edit button
    * 
    * @return void  
    * @access public
    * @see    ViewTicketModel, viewticket.php
    */
   public function actionViewticket() {
      $vt_model = new ViewTicketModel();
      $vt_model->attributes = $_GET;
      $uploadResult = null;
      if ($vt_model->validate()) {
         $ticketVO = $this->getTicket($vt_model->id);
         if ($ticketVO != null) {
            $vt_model->fillFromObject($ticketVO, $this->backend_errors);
         } else {
            $vt_model->errors = $this->backend_errors;
         }
         $uploadResultSessionKey = self::SESSION_KEY_DOCUMENT_UPLOAD_RESULT . $vt_model->id;
         if (AuthOnlyBaseController::isSavedInSession($uploadResultSessionKey)) {
            $uploadResult = AuthOnlyBaseController::safeDeserializeObjFromFromSession($uploadResultSessionKey);
            Yii::app()->user->setState($uploadResultSessionKey, NULL);
         }
      }
      else {
         $vt_model->errors = 'Invalid Ticket ID Specified';
         $this->redirect('index.php?r=tickets/main', true);    
      }
      $this->render('/tickets/viewticket', array('model' => $vt_model, 'uploadResult' => new UploaderResultModelWrapper($uploadResult)));
   }
   
   /**
    * render ticket edit page, and handlt ticket update data posted (redirects back to ticket view on success).
    * 
    * @return void  
    * @access public
    * @see    editticket.php, EditTicketModel, viewticket.php
    */
   public function actionEditticket() {
      $et_model = new EditTicketModel();
      $uploaderModel = new DocumentsModel();
      $ticketVO = null;

      $nictype = '';
      $returndata = '';

      if (isset($_POST['NicSearchModel']) and isset($_POST['NicSearchModel']['returningData'])) {
         $returndata = $_POST['NicSearchModel']['returningData'];
         $dd_model = AuthOnlyBaseController::safeDeserializeObj($returndata['formdata']);
         $nictype = $returndata['nictype'];
         $dd_model->$nictype = $returndata['nic'];
         $et_model = $dd_model;
         $retunurl = $_POST['NicSearchModel']['returningData']['returnurl'];
         $id = split('id=', $retunurl);
         $ticketVO = $this->getTicket($id[1]);
         $et_model->setReadOnlyDataFromObj($ticketVO);
      } else if (isset($_POST['Nichandle_Details']) and isset($_POST['Nichandle_Details']['returningData'])) {
         $returndata = $_POST['Nichandle_Details']['returningData'];
         $dd_model = AuthOnlyBaseController::safeDeserializeObj($returndata['formdata']);
         $nictype = $returndata['nictype'];
         $dd_model->$nictype = $returndata['nic'];
         $et_model = $dd_model;
         $retunurl = $_POST['Nichandle_Details']['returningData']['returnurl'];
         $id = split('id=', $retunurl);
         $ticketVO = $this->getTicket($id[1]);
         $et_model->setReadOnlyDataFromObj($ticketVO);
      } else {
         if(!array_key_exists('id', $_GET)) {
            $this->redirect(array('tickets/main'));
         }
         
         $ticket_id = $_GET['id'];
         if (is_numeric($ticket_id) and $ticket_id > 0) {
            $ticketVO = $this->getTicket($ticket_id);
            $ticketEditable = empty($ticketVO->checkedOutTo);
            if (!Yii::app()->request->isPostRequest) {
               $et_model->fillFromObject($ticketVO, $this->backend_errors);
               $et_model->editable = $ticketEditable ? "1" : "0";
            } else {
               $formWasEditable = $_POST['EditTicketModel']['editable'];
               $editAttemptInError = false;
               if ($formWasEditable) {
                  if ($ticketEditable) {
                     // posted data: update ticket if valid
                     $ticketVO = $this->getTicket($_POST['EditTicketModel']['id']);
                     $ticketVO->operation->adminContactsField = array();
                     $ticketVO->operation->adminContactsField[0]->newValue->nicHandle = strtoupper($_POST['EditTicketModel']['adminContact_0']);
                     $ticketVO->operation->adminContactsField[1]->newValue->nicHandle = strtoupper($_POST['EditTicketModel']['adminContact_1']);
                     $ticketVO->operation->techContactsField->newValue->nicHandle = strtoupper($_POST['EditTicketModel']['techContact']);

                     $ticketVO->operation->domainHolderField->newValue = $_POST['EditTicketModel']['domainHolder'];
                     $classCategory = split(":",$_POST['EditTicketModel']['applicant']);
                     $ticketVO->operation->domainHolderClassField->newValue = $classCategory[0];
                     $ticketVO->operation->domainHolderCategoryField->newValue = $classCategory[1];
                     $ticketVO->operation->nameservers = array();
                     $count = $_POST['EditTicketModel']['nameserversCount'];
                     for ($i = 0; $i < $count; $i++) {
                        $ticketVO->operation->nameservers[$i]->name->newValue = $_POST['EditTicketModel']['nameservers'][$i];
                        $ticketVO->operation->nameservers[$i]->ipAddress->newValue = $_POST['EditTicketModel']['ipAddresses'][$i];
                     }
                  } else {
                     $editAttemptInError = true;
                  }
               }
               $et_model->fillFromObject($ticketVO, $this->backend_errors);

               if(isset($_POST['EditTicketModel']['requestersRemark']))
                  $et_model->requestersRemark = $ticketVO->requestersRemark = $_POST['EditTicketModel']['requestersRemark'];
               $et_model->expiryDate = $ticketVO->expiryDate = '';
               $et_model->checkedOutTo = $ticketVO->checkedOutTo = '';
               $et_model->editable = $ticketEditable ? "1" : "0";
               
               $et_model->setReadOnlyDataFromObj($ticketVO);
               $this->performAjaxValidation($et_model);

               $purpose = NULL;
               // "Registration" and "Modification" are strings from DomainOperationType (java enum)
               if ($et_model->type == "Registration") {
                   $purpose = CRSDocumentAppService_uploadPurposeVO::_REGISTRATION;
               } else if ($et_model->type == "Modification") {
                   $purpose = CRSDocumentAppService_uploadPurposeVO::_MODIFICATION;
               }
               // eager check for validity - we want to always check both ticket and document models
               $valid = $ticketEditable ? $et_model->validate() : true;
               if (!is_null($purpose) && Utility::isLoggedInAs($et_model->billingContact)) {
                  $uploaderModel->setFromRequest($et_model->domainName);
                  $valid = $uploaderModel->validate() && $valid;
               }
               if ($valid) {
                  $ticketUpdated = $ticketEditable && $this->doUpdateTicket($ticketVO, $et_model);
                  if (!$ticketEditable || $ticketUpdated) {
                     if ($uploaderModel->isSetup()) {
                         $uploadResult = $uploaderModel->uploadDocuments($purpose);
                         $uploadResultSessionKey = self::SESSION_KEY_DOCUMENT_UPLOAD_RESULT . $et_model->id;
                         AuthOnlyBaseController::safeSerializeAndStoreObjInSession($uploadResult, $uploadResultSessionKey);
                     }
                     Yii::app()->user->setFlash('state', 'modified');
                     Yii::app()->user->setFlash('ticketUpdate', $ticketUpdated ? "done" : ($editAttemptInError ? "error" : ""));
                     $this->redirect(array('tickets/viewticket', 'id' => $et_model->id));
                  }
               }
            }
         }
         else
            $this->backend_errors .= 'invalid ticket ID';
         if ($this->backend_errors != null)
            $et_model->errors = WSAPIError::getErrorsNotEmpty($this->backend_errors);
      }

      if ($nictype == 'techContact') {
         $_SESSION['techNic'] = $returndata['nic'];
         $_SESSION['adminNic0'] = $et_model->adminContact_0;
         $_SESSION['adminNic1'] = $et_model->adminContact_1;
      } else if ($nictype == 'adminContact_0') {
         $_SESSION['techNic'] = $et_model->techContact;
         $_SESSION['adminNic0'] = $returndata['nic'];
         $_SESSION['adminNic1'] = $et_model->adminContact_1;
      } else if ($nictype == 'adminContact_1') {
         $_SESSION['techNic'] = $et_model->techContact;
         $_SESSION['adminNic0'] = $et_model->adminContact_0;
         $_SESSION['adminNic1'] = $returndata['nic'];
      }

      $this->render('/tickets/editticket', array('model' => $et_model, 'uploaderModel' => $uploaderModel));
   }

   /**
    * handles ticket delete; afterwards, diplays ticket grid with message (but probably should redirect instead)
    * 
    * @return void  
    * @access public
    * @see    main.php, AllTicketsModel
    */
   public function actionDeleteticket() {
      $model = new AllTicketsModel();
      /*if not update dates*/
      if(!array_key_exists('yt0', $_POST)) {
         if(array_key_exists('domainName', $_GET) && array_key_exists('id', $_GET)) {
             $domainName = encode($_GET['domainName']);
             $ticketId = $_GET['id'];
             if ($this->doDeleteTicket($ticketId)) {
               $model->message = "Success: Ticket for domain $domainName cancelled";
            } else {
               $model->message = "Error : Ticket not canceled for domain $domainName, please check your selection, and try again.";
            }
         } else {
            $model->message = "Error : Domain name not set, please check your selection, and try again.";
         }
      }
      $this->render('/tickets/main', array('model' => $model));
   }

   /**
    * performs ticket cancel action
    */
   protected function doDeleteTicket($ticketId) {
      CRSCommonAppService_service::cancel($this->backend_errors, Yii::app()->user->authenticatedUser, $ticketId);
      Yii::log('backe= '.print_r($this->backend_errors,true));
      return ($this->backend_errors == null);
   }

   /**
    * Perform ticket update
    * 
    * Ticket update via CRS-WS-API must:
    * 
    * - Fetch ticket in 'edit' mode
    * 
    * - 'Checkout' ticket, to lock it for updates
    * 
    * - reformat data changes as flattened domain-operations array {@link create_ticket_operations_from_submitted_data}
    * 
    * - Update ticket data
    * 
    * - 'Checkin' ticket, to unlock
    * 
    * @param  CRSTicketAppService_ticketVO   $ticketVO Ticket data from CRS-WS-API
    * @param  EditTicketModel    $et_model ticket edit data
    * @return boolean   true on success
    * @access protected
    */
   protected function doUpdateTicket($ticketVO, $et_model) {
      global $admin_sts_wsapi_strings;
      # proceed to update ticket
      $ticket_id = $et_model->id;
      $user = Yii::app()->user->authenticatedUser;
      # attempt to fetch for edit (possibly not authorized)
      
      $tmp = new EditTicketModel();
      $tmp = $ticketVO;
      
      CRSTicketAppService_service::edit($ticketVO, $this->backend_errors, $user, $ticket_id);
      if ($this->backend_errors == null) {
            $domainOperations = $this->create_ticket_operations_from_submitted_data($tmp, $et_model);
            CRSTicketAppService_service::update($this->backend_errors, $user, $ticket_id, $domainOperations, $et_model->requestersRemark, $et_model->clikPaid);
            if (count($this->backend_errors) != 0) {
                  $this->backend_errors .= ': failed update';
                  return false;
            } 
            return true;
      }
      $this->backend_errors .= ': failed edit';
      return false;
   }


   /**
    * transforms the supplied objects into an array of changes
    * 
    * @param  object    $ticketVO          Ticket object as fetch frmo CRS-WS-API with Ticket-ID
    * @param  object    $edit_ticket_model Vaildated Web Form Data of Ticket changes
    * @return array     array of data from merging ticket-object with web form ticket changes
    * @access protected
    */
   protected function create_ticket_operations_from_submitted_data($ticketVO, $edit_ticket_model) {
      $domainOperations = array();
      # extract class/category from applicant
      $clascat = explode(':', $edit_ticket_model->applicant);
      $edit_ticket_model->domainHolderClass = $clascat[0];
      $edit_ticket_model->domainHolderCategory = $clascat[1];
      # add change operations : iterate over usual change-fields
      
      if(isset($ticketVO->charityCode)) {
         $domainOperations['charityCode'] = $edit_ticket_model->charityCode;
      }
      
      if(isset($ticketVO->requestersRemark)) {
         $domainOperations['requestersRemark'] = $ticketVO->requestersRemark;
      }
      
      $opsarr = array('domainHolder', 'domainHolderClass', 'domainHolderCategory', 'domainName', 'resellerAccount',);
      foreach ($opsarr as $op) {
         $fld = $op . 'Field';
         $val = isset($edit_ticket_model->$op) ? $edit_ticket_model->$op : $ticketVO->operation->$fld->newValue;

         if(isset($ticketVO->operation->$fld->failureReasonId) ) {
            if ($val != null and $val != '')
               $domainOperations[$fld] = array(
                   'newValue' => $val,
                   'failureReasonId' => 0 + $ticketVO->operation->$fld->failureReasonId,
               );
         } else {
            if ($val != null and $val != '')
               $ticketVO->operation->$fld->failureReasonId = 0;
               $domainOperations[$fld] = array(
                   'newValue' => $val,
                   'failureReasonId' => 0 + $ticketVO->operation->$fld->failureReasonId,
               );
         }
      }
      # tech and billing contacts
      $opsarr = array('techContact', 'billingContact');
      foreach ($opsarr as $op) {
         $fld = $op . 'sField';
         $val = isset($edit_ticket_model->$op) ? $edit_ticket_model->$op : $ticketVO->operation->$fld->newValue->nicHandle;
         if ($val != null and $val != '')
            $domainOperations[$fld] = array(
                'newValue' => $val,
                    #'failureReasonId'	=> 0+$ticketVO->operation->$op->failureReasonId,
            );
      }

      # add change operations : iterate over admin contacts
      if (!is_array($ticketVO->operation->adminContactsField)) {
         $t = $ticketVO->operation->adminContactsField;
         $ticketVO->operation->adminContactsField = array();
         $ticketVO->operation->adminContactsField[0] = $t;
         $ticketVO->operation->adminContactsField[1] = $t;
      }
      $domainOperations['adminContactsField'] = array();
      foreach (array(0, 1) as $x) {
         $op = $ticketVO->operation->adminContactsField[$x];
         $fld = 'adminContact_' . $x;
         $val = isset($edit_ticket_model->$fld) ? $edit_ticket_model->$fld : $op->newValue->nicHandle;
         
        if(isset($op->newValue->failureReasonId)) {
              $domainOperations['adminContactsField'][$x] = array(
                  'newValue' => $val,
                  'failureReasonId' => 0 + ($op->newValue->failureReasonId),
              );
        } else {
           $domainOperations['adminContactsField'][$x] = array(
                  'newValue' => $val,
                  'failureReasonId' => 0);
        }
      }
      # add change operations : iterate over domains
      
      
      
      if (!is_array($ticketVO->operation->nameservers)) {
         $t = $ticketVO->operation->nameservers;
         $ticketVO->operation->nameservers = array();
      }
      
      foreach ($ticketVO->operation->nameservers as $x => $value) {
         # name
         $val_n = $edit_ticket_model->nameservers[$x];
         # ip address
         $val_a = $edit_ticket_model->ipAddresses[$x];

         $a = array(
            'name' => array(
                'newValue' => $val_n,
                'failureReasonId' => 0), // 0+$op->name->failureReasonId),
            'ipAddress' => array(
                'newValue' => $val_a,
                'failureReasonId' => 0), // 0+$op->ipAddress->failureReasonId),
         );
         if ($val_a == null or $val_a = '')
            unset($a['ipAddress']);
         $domainOperations['nameservers'][] = $a;
      }
      Yii::log(print_r($domainOperations, true), 'debug', 'TicketsController::create_ticket_operations_from_submitted_data()');
      return $domainOperations;
   }

   /**
    * Fetches Ticket object from backend, for the given Ticket ID
    * 
    * @param  integer    $id Ticket ID
    * @return CRSTicketAppService_ticketVO   Ticket data from CRS-WS-API
    * @access protected
    */
   protected function getTicket($id) {
      Yii::log('getTicket(id=' . $id . ')', 'debug', 'TicketsController::getTicket()');
      $result = null;
      CRSTicketAppService_service::view($result, $this->backend_errors, Yii::app()->user->authenticatedUser, $id);
      if (count($this->backend_errors) == 0) {
         Yii::log(print_r($result, true), 'warning', 'TicketsController::getTicket()' . __LINE__);
      }
      else
         Yii::log(print_r($this->backend_errors, true), 'error', 'TicketsController::getTicket() : ERROR:' . __LINE__);
      return $result;
   }

   public function actionEditticketserialized() {
      // return serialized domain-details form state to ajax requester ; doesn't have to be valid
      $returns = '';
      if (Yii::app()->request->isPostRequest && isset($_POST['EditTicketModel'])) {
         $dd_model = new EditTicketModel();
         $dd_model->attributes = $_POST['EditTicketModel'];
         Yii::app()->request->csrfTokenName = $_POST['YII_CSRF_TOKEN'];
         $returns = AuthOnlyBaseController::safeSerializeObj($dd_model);
      } else {
         Yii::log('got ?!?! :' . print_r($_REQUEST, true), 'debug', 'TicketController::actionTicketdetailsserialized()');
      }
      echo $returns;
   }

   /**
    * returns a pagefull of data for use by the parent GridController
    * 
    * @param  object    $model    model containing jqGrid colmodel-definitions
    * @param  object    $criteria search criteria
    * @param  unknown   $sort     sort criteria (unused)
    * @param  integer   $perpage  record per page
    * @param  integer   $pagenum  pagenumber to show
    * @return array     array of meta-data and data for a page of data for the ticket grid display
    * @access protected
    */
   protected function getData($model, $criteria, $sort, $perpage, $pagenum) {
      Yii::log('..', 'debug', 'TicketsController::getData($model,$criteria,$sort,$perpage,$pagenum)' . __LINE__);
      // paging
      $pagedata = array('records' => 0, 'page' => $pagenum, 'total' => 0, 'rows' => array());
      $offset = $perpage * ($pagenum - 1);
      $limit = $perpage;
      
      if(isset($model->days))
         $model->days = trim($model->days);
      
      if (!is_numeric($model->days)) {
         $model->days = 1000;
      } else {
         if($model->days < 0)
            $model->days = -$model->days;
      }
      
      $tm = time();
      Yii::log("DAYS= ".print_r($model->days, true));
       
      $criteria->from = date('Y-m-d', $tm - (86400 * $model->days)) . 'T00:00:00';
      $criteria->to = date('Y-m-d', $tm) . 'T00:00:00';

      $sc = new CRSTicketAppService_sortCriterion();
      $sc->ascending = true; // type=xs:boolean, (NOT NULL), min=1
      if ($_GET['sord'] == "desc") {
         $sc->ascending = false;
      }

      if (is_null($model->columns[$_GET['sidx']]['criteriafield'])) {
         $sc->sortBy = 'id'; // type=xs:string, (NOT NULL), min=0
      } else {
         $sc->sortBy = $model->columns[$_GET['sidx']]['criteriafield'];
      }
      // API SOAP call
      $result = null;
      CRSTicketAppService_service::find($result, $this->backend_errors, Yii::app()->user->authenticatedUser, $criteria, $offset, $limit, $sc);
      if($result != null) {
         if($result->totalResults) {
            if (count($this->backend_errors) == 0) {
               // records = total records on all pages
               $pagedata['records'] = $result->totalResults;
               // total = total num result pages = (t div p)+1
               $pagedata['total'] = math_div($result->totalResults, $perpage) + 1;
               if (is_array($result->list))
                  foreach ($result->list as $k => $o)
                     $pagedata['rows'][] = $model->addResults($o);
               else
               if (is_object($result->list))
                  $pagedata['rows'][] = $model->addResults($result->list);
               else
                  Yii::log(print_r($result, true), 'warning', 'TicketsController::getData() : WARN : no \'results\':' . __LINE__);
            } else {
               Yii::log(print_r($this->backend_errors, true), 'error', 'TicketsController::getData() : ERROR:' . __LINE__);
            }
         } else {
           Yii::log('NO RESULTS IN FOUND TICKETS!');
         }  
      }
      return $pagedata;
   }

}
