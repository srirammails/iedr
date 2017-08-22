<?php

/**
 * defines DomainsController class
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
 * Controller class for domain registration
 * 
 * @category  NRC
 * @package   IEDR_New_Registrars_Console
 * @author    IEDR <asd@iedr.ie>
 * @copyright 2011 IEDR
 * @license   http://www.iedr.ie/ (C) IEDR 2011
 * @version   Release: @package_version@
 * @link      https://statistics.iedr.ie/
 */
class DomainsController extends AuthOnlyBaseController {

   const SESSION_KEY_DOCUMENT_UPLOAD_RESULT = 'documentuploadresult';

   /**
    * renders menu-view
    * 
    * @return void  
    * @access public
    */
   public function actionMenu() {
      $this->render('menu');
   }

   /**
    * Domain Registration Form
    * 
    * Has 4 modes; 
    *
    *  - without request parameters, displays empty domain-registration form
    *
    *  - validates domain-registration form data Ajax requests
    *
    *  - on receipt of invalid form data, redisplays form with error messages
    *
    *  - on receipt of valid form data, redirects to domain-registration-details form
    *
    * Domains are checked for availability as part of validation.
    * Availability-checking includes checking whether the domain already exists, and whether there already exists a registration-ticket for the domain name. See {@link domainIsAvailable}.
    * The redirected-to domainDetails form/action is the one that performs the actual domain creation
    *
    * @return void  
    * @access public
    * @see    regnew.php, Domains_RegNew
    */
   public function actionRegnew() {
      $rn_model = new Domains_RegNew();
      if (Yii::app()->request->isPostRequest) {
         $this->performAjaxValidation($rn_model);
         if (isset($_POST['Domains_RegNew'])) {
            $rn_model->attributes = $_POST['Domains_RegNew'];
            if ($rn_model->validate()) {
               Yii::log('model validates ok', 'debug', 'DomainsController::actionRegnew()');
               $val_avail_domains = array();
               $allDomains = $rn_model->getDomainsAsArray();
               $errorMsg = '';
               foreach ($allDomains as $domain) {
                  // check if this domain is available
                  if (Utility::domainIsAvailable($domain)) {
                     $val_avail_domains[] = $domain;
                  } else {
                     $errorMsg = $errorMsg . $domain . ' is invalid, already registered, or has a pending ticket <br/>';
                  }
               }
               if (count($val_avail_domains) == count($allDomains)) {
                  // go to next step : enter domain details
                  Yii::log('go to next step : enter domain details, with domain names: ' . print_r($val_avail_domains, true), 'debug', 'DomainsController::actionRegnew()');
                  AuthOnlyBaseController::safeSerializeAndStoreObjInSession($val_avail_domains, 'validavailabledomains');
//                   Yii::log('domain names serialized: ' . $vad, 'debug', 'DomainsController::actionRegnew()');
                  $this->redirect(array('domaindetails'), true);
               }
               if (strlen($errorMsg) > 0) {
                  $rn_model->addError('domain_names', $errorMsg);
               }
            }
         }
      }
      // display form (with any errs)
      $this->render('regnew', array('model' => $rn_model));
   }

   /**
    * handles Domain-creation details form, and domain creation
    * 
    * This form has 3 modes;
    * 
    *  - without parameters, renders blank form (note that the form will never validate without domains being passed as parameters)
    * 
    *  - on Ajax request, returns data validation results
    * 
    *  - on invalid data, redisplays the form with errors
    * 
    *  - on valid data, attempts to create domains, and redirects to the domains-created page with creation results per-domain : Ticket No. if created successfully
    * 
    *  - on returning from a Nic-Handles find/create sub-task, redisplays the form, optionall with the relevant Nic-Handle field filled in with the created/found Nic-Handle
    * 
    * @return void  
    * @access public
    * @see    Domains_Creation_Details, Nichandle_Details, domaindetails.php, domainscreated.php, DomainsController::actionDomainsCreated()
    */
   public function actionDomainDetails() {
      $model = new Domains_Creation_Details();
      $uploaderModel = new DocumentsModel();

      if (Yii::app()->request->isPostRequest) {

         if(isset($_POST['Domains_Creation_Details'])) {
            $model->setFromPOST($_POST['Domains_Creation_Details']);
            $model->setupRules($_POST['Domains_Creation_Details']);
         }

         $this->performAjaxValidation($model);

         if (isset($_POST['Domains_Creation_Details'])) {
            $model->setFromPOST($_POST['Domains_Creation_Details']);
            $model->setupRules($_POST['Domains_Creation_Details']);

            $uploaderModel->setFromRequest($model->domainlist);
            // eager check for validity - we want to always check both domain and document models
            $valid = $model->validate();
            $valid = $uploaderModel->validate() && $valid;
            if ($valid) {
               $dc_model = new Domains_Created($this->createDomains($model), $model);
               $uploadResult = $uploaderModel->uploadDocuments(CRSDocumentAppService_uploadPurposeVO::_REGISTRATION);
               AuthOnlyBaseController::safeSerializeAndStoreObjInSession($uploadResult, self::SESSION_KEY_DOCUMENT_UPLOAD_RESULT);
               AuthOnlyBaseController::safeSerializeAndStoreObjInSession($dc_model, 'domaincreateresults');
               $this->redirect(array('domainscreated'), true);
            }
         }

         if (isset($_POST['Nichandle_Details']) and isset($_POST['Nichandle_Details']['returningData'])) {
            // returning from a NIC edit/create
            $returndata = $_POST['Nichandle_Details']['returningData'];
            $model = AuthOnlyBaseController::safeDeserializeObj($returndata['formdata']);
            
            $nicrole = $returndata['nictype'];
            $model->$nicrole = $returndata['nic'];
         }
         if (isset($_POST['NicSearchModel']) and isset($_POST['NicSearchModel']['returningData'])) {
            
            // returning from a NIC find
            $returndata = $_POST['NicSearchModel']['returningData'];
            $model = AuthOnlyBaseController::safeDeserializeObj($returndata['formdata']);
            $nicrole = $returndata['nictype'];
            $model->$nicrole = $returndata['nic'];
         }
      }

      if (isset($_GET['errorReturnData'])) {
          $model = AuthOnlyBaseController::safeDeserializeObj($_GET['errorReturnData']);
      }

      if (AuthOnlyBaseController::isSavedInSession('validavailabledomains') && (($model->domainlist == null) || (count($model->domainlist) < 1))) {
         // got here from post from RegNew (or unexpected direct access)
         $validavailabledomains = AuthOnlyBaseController::safeDeserializeObjFromFromSession('validavailabledomains');
         $model = new Domains_Creation_Details($validavailabledomains);
      }
      
      if (($model->domainlist == null) or (count($model->domainlist) < 1)) {
            Yii::log('reg new action');
            $this->redirect('index.php?r=domains/regnew', true);
      }
      
      // error/fallback : redisplay form with errors
      $this->render('domaindetails', array('model' => $model, 'uploaderModel' => $uploaderModel));
   }

   /**
    * Returns a string representing a serialized Model instance containing the posted form data.
    * 
    * This is called from Javascript function {@link getDataAndPostForm} when navigating to a Nic-Handle find/create/edit form,
    * so that the target form can return to the originating page with all of the form-data in the state it was in when the
    * user clicked on the relevant button
    * 
    * @return void  
    * @access public
    */
   public function actionDomaindetailsserialized() {
      // return serialized domain-details form state to ajax requester ; doesn't have to be valid
      $retval = '';
      if (Yii::app()->request->isPostRequest and isset($_POST['Domains_Creation_Details'])) {
         Yii::log('Domains_Creation_Details');
         $model = new Domains_Creation_Details();
         $model->setFromPOST($_POST['Domains_Creation_Details']);
         if(isset($_POST['Domains_Creation_Details'])) {
            $model->setupRules($_POST['Domains_Creation_Details']);
         }
         $retval = AuthOnlyBaseController::safeSerializeObj($model);
      } else if (Yii::app()->request->isPostRequest && isset($_POST['DomainsTransferDetails'])) {
         Yii::log('DomainsTransferDetails');
         $model = new DomainsTransferDetails();
         $model->setFromPOST($_POST['DomainsTransferDetails']);
         if(isset($_POST['DomainsTransferDetails'])) {
            $model->setupRules($_POST['DomainsTransferDetails']);
         }
         $retval = AuthOnlyBaseController::safeSerializeObj($model);
      } else if (Yii::app()->request->isPostRequest && isset($_POST['ViewDomainModel'])) {
         Yii::log('ViewDomainModel');
         $model = new ViewDomainModel();
         $model->setFromPOST($_POST['ViewDomainModel']);
         $retval = AuthOnlyBaseController::safeSerializeObj($model);
      }
      
      echo $retval;
   }

   public function setSessionFormPost($post) {
      $_SESSION['adminCompanyName0'] = $post['domain_adminContacts_0_nicHandle'];
      $_SESSION['adminCountry0'] = $post['domain_adminContacts_0_country'];
      $_SESSION['adminEmail0'] = $post['domain_adminContacts_0_email'];
      $_SESSION['adminName0'] = $post['domain_adminContacts_0_name'];
      $_SESSION['adminNicHandleId0'] = $post['domain_adminContacts_0_nicHandle'];

      $_SESSION['adminCompanyName1'] = $post['domain_adminContacts_1_nicHandle'];
      $_SESSION['adminCountry1'] = $post['domain_adminContacts_1_country'];
      $_SESSION['adminEmail1'] = $post['domain_adminContacts_1_email'];
      $_SESSION['adminName1'] = $post['domain_adminContacts_1_name'];
      $_SESSION['adminNicHandleId1'] = $post['domain_adminContacts_1_nicHandle'];

      $_SESSION['techCompanyName'] = $post['domain_techContacts_companyName'];
      $_SESSION['techCountry'] = $post['domain_techContacts_country'];
      $_SESSION['techEmail'] = $post['domain_techContacts_email'];
      $_SESSION['techName'] = $post['domain_techContacts_name'];
      $_SESSION['techNicHandleId'] = $post['domain_techContacts_nicHandle'];
   }

   public function cleanSession() {
      unset($_SESSION['adminCompanyName0']);
      unset($_SESSION['adminCountry0']);
      unset($_SESSION['adminEmail0']);
      unset($_SESSION['adminName0']);
      unset($_SESSION['adminNicHandleId0']);

      unset($_SESSION['adminCompanyName1']);
      unset($_SESSION['adminCountry1']);
      unset($_SESSION['adminEmail1']);
      unset($_SESSION['adminName1']);
      unset($_SESSION['adminNicHandleId1']);

      unset($_SESSION['techCompanyName']);
      unset($_SESSION['techCountry']);
      unset($_SESSION['techEmail']);
      unset($_SESSION['techName']);
      unset($_SESSION['techNicHandleId']);
   }

   public function setSessionAdmin($num, $result) {
      if (is_array($result)) {
         $_SESSION["adminCompanyName$num"] = $result[$num]->companyName;
         $_SESSION["adminCountry$num"] = $result[$num]->country;
         $_SESSION["adminEmail$num"] = $result[$num]->email;
         $_SESSION["adminName$num"] = $result[$num]->name;
         if (property_exists($result[$num], 'nicHandle')) {
            $_SESSION["adminNicHandleId$num"] = $result[$num]->nicHandle;
         } else if (property_exists($result[$num], 'nicHandleId')) {
            $_SESSION["adminNicHandleId$num"] = $result[$num]->nicHandleId;
         }
      } else {
         $_SESSION["adminCompanyName$num"] = $result->companyName;
         $_SESSION["adminCountry$num"] = $result->country;
         $_SESSION["adminEmail$num"] = $result->email;
         $_SESSION["adminName$num"] = $result->name;
         if (property_exists($result, 'nicHandle')) {
            $_SESSION["adminNicHandleId$num"] = $result->nicHandle;
         } else if (property_exists($result, 'nicHandleId')) {
            $_SESSION["adminNicHandleId$num"] = $result->nicHandleId;
         }
      }
   }

   public function getAdminFromSession() {
      return array(
          array('companyName' => isset($_SESSION['adminCompanyName0']) ? $_SESSION['adminCompanyName0'] : '',
              'country' => isset($_SESSION['adminCountry0']) ? $_SESSION['adminCountry0'] : '',
              'email' => isset($_SESSION['adminEmail0']) ? $_SESSION['adminEmail0'] : '',
              'name' => isset($_SESSION['adminName0']) ? $_SESSION['adminName0'] : '',
              'nicHandle' => isset($_SESSION['adminNicHandleId0']) ? $_SESSION['adminNicHandleId0'] : '',
          ),
          array('companyName' => isset($_SESSION['adminCompanyName1']) ? $_SESSION['adminCompanyName1'] : '',
              'country' => isset($_SESSION['adminCountry1']) ? $_SESSION['adminCountry1'] : '',
              'email' => isset($_SESSION['adminEmail1']) ? $_SESSION['adminEmail1'] : '',
              'name' => isset($_SESSION['adminName1']) ? $_SESSION['adminName1'] : '',
              'nicHandle' => isset($_SESSION['adminNicHandleId1']) ? $_SESSION['adminNicHandleId1'] : '',
          ),
      );
   }

   public function setSessionTech($result) {
      $_SESSION['techCompanyName'] = $result->companyName;
      $_SESSION['techCountry'] = $result->country;
      $_SESSION['techEmail'] = $result->email;
      $_SESSION['techName'] = $result->name;
      if (property_exists($result, 'nicHandle')) {
         $_SESSION['techNicHandleId'] = $result->nicHandle;
      } else if (property_exists($result, 'nicHandleId')) {
         $_SESSION['techNicHandleId'] = $result->nicHandleId;
      }
   }

   public function setTechFromSession(&$tech) {
      if (isset($_SESSION['techCompanyName'])) {
         $tech->companyName = $_SESSION['techCompanyName'];
         $tech->country = $_SESSION['techCountry'];
         $tech->email = $_SESSION['techEmail'];
         $tech->name = $_SESSION['techName'];
         $tech->nicHandle = $_SESSION['techNicHandleId'];
      }
   }

   public function setSession($nicrole, $result, &$domainVO) {
      switch ($nicrole) {
         case 'domain_techContacts_nicHandle':
            Yii::log('SESSION domain_techContacts_nicHandle = ' . print_r($result, true));
            $this->setSessionTech($result);
            $domainVO->domain->adminContacts = $this->getAdminFromSession();
            $this->setTechFromSession($domainVO->domain->techContacts);
            break;
         case 'domain_adminContacts_0_nicHandle':
            Yii::log('SESSION domain_adminContacts_0_nicHandle = ' . print_r($result, true));
            $this->setSessionAdmin(0, $result);
            $domainVO->domain->adminContacts = $this->getAdminFromSession();
            $this->setTechFromSession($domainVO->domain->techContacts);
            break;
         case 'domain_adminContacts_1_nicHandle':
            Yii::log('SESSION domain_adminContacts_1_nicHandle = ' . print_r($result, true));
            $this->setSessionAdmin(1, $result);
            $domainVO->domain->adminContacts = $this->getAdminFromSession();
            $this->setTechFromSession($domainVO->domain->techContacts);
            break;
      }
   }

   /**
    * Domain view action, also puts domains into the MSD process
    * 
    * @return void  
    * @access public
    * @see    viewdomain.php, ViewDomainModel, DomainsController::actionViewDomain()
    */
   public function getPostAction($post) {
      foreach($post as $key => $value) {
         // keys intended to be matched: yt1, yt017, yt12872, etc.
         if(preg_match('/^yt[0-9]+$/', $key)) {
            return $post[$key];
         }
      }
      return " ";
   }

   public function actionViewDomain() {
      $vd_model = new ViewDomainModel();
      $vd_model->message = array();
      $uploaderModel = new DocumentsModel();

      $saveContacts = false;
      $domainName = "";
      $stringMessage = "";
      $results = null;
      $user = Yii::app()->user->authenticatedUser;
      $temporaryModel = new ViewDomainModel();
      $modifiedNicHandle = '';
      $nicrole = '';
      $validationPassed = true;
      $dd_model = null;
      $domainVO = null;

      if (Yii::app()->request->isPostRequest) {

         if (array_key_exists('ViewDomainModel', $_POST) && isset($_POST['ViewDomainModel'])) {
            $temporaryModel->setFromArray($_POST['ViewDomainModel']);
            $domainName = $_POST['ViewDomainModel']['domain_name'];
            $this->performAjaxValidation($temporaryModel);
         }

         switch ($this->getPostAction($_POST)) {
            case 'Enter Domain to Voluntary NRP':
               $domainName = $_POST['ViewDomainModel']['domain_name'];
               CRSDomainAppService_service::enterVoluntaryNRP($vd_model->errors, $user, $domainName);
               if (strlen($vd_model->errors)) {
                  $stringMessage = WSAPIError::getErrorsNotEmpty($vd_model->errors);
               } else {
                  $stringMessage = "Domain $domainName added to Voluntary NRP";
               }
               break;

            case 'Remove from Voluntary NRP':
               $domainName = $_POST['ViewDomainModel']['domain_name'];
               CRSDomainAppService_service::removeFromVoluntaryNRP($this->backend_errors, $user, $domainName);
               if (count($this->backend_errors) == 0) {
                  $stringMessage = "Domain ".$domainName." has been successfully removed from NRP.";
               } else {
                  $stringMessage = "Payment is required to complete this action. Please return to the selection and select a valid payment method to proceed";
               }
               break;
            case 'Show authcode':
               $result = null;
               CRSCommonAppService_service::generateOrProlongAuthCode($result, $this->backend_errors, $user, $domainName);
               if (count($this->backend_errors) == 0) {
                  $vd_model->authCodeMessage = true;
               } else {
                  $stringMessage = "Authcode generation failed. " . WSAPIError::getErrorsNotEmpty($this->backend_errors);
               }
               break;
            case 'Send authcode by email':
               CRSCommonAppService_service::sendAuthCodeByEmail($this->backend_errors, $user, $domainName);
               if (count($this->backend_errors) == 0) {
                  $stringMessage = "Email sent successfully";
               } else {
                  $stringMessage = "Email sending failed. " . WSAPIError::getErrorsNotEmpty($this->backend_errors);
               }
               break;
               break;
            case 'Submit changes':
            case 'Modify Domain':
               $result = null;
               $domainName = $_POST['ViewDomainModel']['domain_name'];
               
               $adminContacts = array();
               $adminContacts[] = $_POST['ViewDomainModel']['domain_adminContacts_0_nicHandle'];
               $adminContacts[] = $_POST['ViewDomainModel']['domain_adminContacts_1_nicHandle'];
               
               $techContacts = array();
               $techContacts[] = $_POST['ViewDomainModel']['domain_techContacts_nicHandle'];
               $domainHolder = $_POST['ViewDomainModel']['domain_holder'];
               $domainClass = $_POST['ViewDomainModel']['domain_holderClass'];
               $domainCategory = $_POST['ViewDomainModel']['domain_holderCategory'];
               $nsc = $_POST['ViewDomainModel']['domain_nameservers'];
               $nameservers = array();
               
               for ($i = 0; $i < $_POST['ViewDomainModel']['domain_nameserver_count']; $i++) {
                  if (!empty($_POST['ViewDomainModel']['domain_nameservers'][$i]) && isset($_POST['ViewDomainModel']['domain_nameservers'][$i])) {
                     $nameservers[$i] = new CRSCommonAppService_nameserverVO();
                     $nameservers[$i]->name = $_POST['ViewDomainModel']['domain_nameservers'][$i];
                     $nameservers[$i]->ipAddress = $_POST['ViewDomainModel']['domain_ipAddresses'][$i];
                  }
               }

			// Directs cannot change the renewal mode
			   $renewalMode = null;
			   if (!Utility::isDirect()) { 
               		$renewalMode = $_POST['ViewDomainModel']['domain_dsmState_renewalMode'];
			   }
               $hostmasterRemark = $_POST['ViewDomainModel']['domain_remark'];

               if (array_key_exists('ViewDomainModel', $_POST) && isset($_POST['ViewDomainModel'])) {
                   $temporaryModel->setFromArray($_POST['ViewDomainModel']);

                   $purpose = CRSDocumentAppService_uploadPurposeVO::_MODIFICATION;
                   $domainVO = $this->getDomainDetails($domainName, $error);
                   $validationPassed = $temporaryModel->validate();
                   if (Utility::isLoggedInAs($domainVO->domain->billingContacts->nicHandle)) {
                       $uploaderModel->setFromRequest($domainName);
                       $validationPassed = $uploaderModel->validate() && $validationPassed;
                   }
                   if ($validationPassed) {
                       $ticketId = CRSCommonAppService_service::modifyDomain($result, $this->backend_errors, $user, $domainName, $domainHolder, $domainClass, $domainCategory, $adminContacts, $techContacts, $nameservers, $renewalMode, $hostmasterRemark);
                       if ($result != null) {
                           if (count($this->backend_errors) == 0) {
                               $stringMessage = "Your modification request for ".$domainName." has been submitted and will be processed by our Registrations team shortly.";
                               if ($uploaderModel->isSetup()) {
                                   $stringMessage .= $this->summarizeResult($uploaderModel->uploadDocuments($purpose));
                               }
                           } else {
                               $stringMessage = "Error: " . WSAPIError::getErrorsNotEmpty($this->backend_errors);
                           }
                       } else {
                           if(count($this->backend_errors) == 0) {
                               $stringMessage = "Modify domain is successful.";
                               if ($uploaderModel->isSetup()) {
                                   $stringMessage .= $this->summarizeResult($uploaderModel->uploadDocuments($purpose));
                               }
                           } else {
                               $stringMessage = "Change domain details failed. " . WSAPIError::getErrorsNotEmpty($this->backend_errors);
                           }
                       }
                   }
               }
               break;

            default:
               //$stringMessage = "Undefined post action";
               break;
         }

         /* push message to flash */
         if (!empty($stringMessage)) {
            array_push($vd_model->message, $stringMessage);
         }

         if (isset($_POST['NicSearchModel']) and isset($_POST['NicSearchModel']['returningData'])) {
            $returndata = $_POST['NicSearchModel']['returningData'];
            $dd_model = AuthOnlyBaseController::safeDeserializeObj($returndata['formdata']);
            $nicrole = $returndata['nictype'];
            $dd_model->$nicrole = $returndata['nic'];
            $modifiedNicHandle = $returndata['nic'];
            $domain = split("id=", $returndata['returnurl']);
            $domainName = $domain[1];
            $vd_model->isActive = true;
         }

         if (isset($_POST['Nichandle_Details']) and isset($_POST['Nichandle_Details']['returningData'])) {
            $returndata = $_POST['Nichandle_Details']['returningData'];
            $dd_model = AuthOnlyBaseController::safeDeserializeObj($returndata['formdata']);
            $nicrole = $returndata['nictype'];
            $dd_model->$nicrole = $returndata['nic'];
            $modifiedNicHandle = $returndata['nic'];
            $domain = split("id=", $returndata['returnurl']);
            $domainName = $domain[1];
            $vd_model->isActive = true;
         }
      } else {
         if (isset($_GET['id'])) {
            $domainName = $_GET['id'];
         }
      }

      Yii::log('domain name = ' . $domainName);
      $error = "";
      if (is_null($domainVO)) {
         $domainVO = $this->getDomainDetails($domainName, $error);
      }
      if ($domainVO == null) {
         Yii::log('domain error= ' . print_r($error, true));
      }

      if (!isset($domainVO)) {
         Yii::log('Domain object not set!');
         $this->redirect('/index.php?r=domainreports/alldomains');
      }
      // save original admin and tech nh
      $techOrig = $this->getNhIdFor($domainVO->domain, 'techContacts', 0);
      $admin0Orig = $this->getNhIdFor($domainVO->domain, 'adminContacts', 0);
      $admin1Orig = $this->getNhIdFor($domainVO->domain, 'adminContacts', 1);

      $result = null;
      if ($modifiedNicHandle != '') {
         CRSNicHandleAppService_service::get($result, $this->backend_errors, Yii::app()->user->authenticatedUser, $modifiedNicHandle);
         if ($result == null) {
            Yii::log('Can not get nichandle !!!');
         } else {
            $this->setSession($nicrole, $result, $domainVO);
         }
      } else if ($saveContacts == true) {
         $this->setSessionFormPost($_POST['ViewDomainModel']);
      } else {
         $this->cleanSession();
         if (property_exists($domainVO->domain, 'techContacts')) {
            if (isset($domainVO->domain->techContacts)) {
               $this->setSessionTech($domainVO->domain->techContacts);
            }
         }

         if (property_exists($domainVO->domain, 'adminContacts')) {
            if (is_array($domainVO->domain->adminContacts)) {
               $this->setSessionAdmin(0, $domainVO->domain->adminContacts);
               $this->setSessionAdmin(1, $domainVO->domain->adminContacts);
               $domainVO->domain->adminContacts = $this->getAdminFromSession();
            } else if (is_object($domainVO->domain->adminContacts)) {
               $this->setSessionAdmin(0, $domainVO->domain->adminContacts);
               $domainVO->domain->adminContacts = $this->getAdminFromSession();
            }
         }
      }

       $vd_model->fillFromObject($domainVO, $this->backend_errors);
       $vd_model->setOriginalContacts($techOrig, $admin0Orig, $admin1Orig);
       $vd_model->overrideModifiedFields($dd_model, Utility::isDirect());
       if (!$validationPassed) {
           Yii::log('Validation failed');
           $vd_model->setFromArray($_POST['ViewDomainModel']);
           $vd_model->addErrors($temporaryModel->getErrors());
           $vd_model->isActive = true;
       }
       $this->render('viewdomain', array('model' => $vd_model, 'uploaderModel' => $uploaderModel));
   }
   
   function getNhIdFor($domainVO, $listName, $index) {
   	if (property_exists($domainVO, $listName)) {
   		$contacts = $domainVO->$listName;
   		if (is_array($contacts)) {
   			$contact = $contacts[$index];
   		} else {
   			// ignore index
   			$contact = $contacts;
   		}
   		if (property_exists($contact, 'nicHandle')) {
   			return $contact->nicHandle;
   		} else if (property_exists($contact, 'nicHandleId')) {
   			return $contact->nicHandleId;
   		}
   	}
   }
   

   public function setRegRequestObject($model, &$registrationRequest) {
      if (isset($model->domain_name)) {
         $registrationRequest->domainName = $model->domain_name;
      }

      if (isset($model->authcode)) {
         $registrationRequest->authCode = $model->authcode;
      }

      if (isset($model->admin_contact_nic_1)) {
         $registrationRequest->adminContact1NicHandle = $model->admin_contact_nic_1;
      }

      if (isset($model->admin_contact_nic_2) && !empty($model->admin_contact_nic_2)) {
         $registrationRequest->adminContact2NicHandle = $model->admin_contact_nic_2;
      } else {
         $registrationRequest->adminContact2NicHandle = null;
      }

      if (isset($model->tech_contact)) {
         $registrationRequest->techContactNicHandle = $model->tech_contact;
      }

      if (isset($model->nameserversCount) && isset($model->nameservers) && isset($model->ipAddresses)) {
         for ($i = 0; $i < $model->nameserversCount; $i++) {
             $nameserver = new CRSCommonAppService_nameserverVO();
             $nameserver->name = $model->nameservers[$i];
             $nameserver->ipAddress = $model->ipAddresses[$i];
             $registrationRequest->nameservers[$i] = $nameserver;
         }
      }

      if (isset($model->charitycode)) {
         $registrationRequest->charityCode = $model->charitycode;
      }

      if (isset($model->renewalPeriod)) {
         $registrationRequest->period = $model->renewalPeriod;
      }

      if (isset($model->periodType)) {
         $registrationRequest->periodType = $model->periodType;
      }

      if (isset($model->paymentType)) {
         $registrationRequest->paymentType = $model->paymentType;
      }
   }

   public function setPaymentRequestObject($model, &$paymentRequest, &$registrationRequest) {
      switch ($model->paymentType) {
         case 'CC' :
            $paymentRequest = CreditCardFormSegmentModel::asWSAPIPaymentObject($model);
            break;
         case 'CH' :
            $registrationRequest->charityCode = $model->charitycode;
            break;
         case 'ADP' :
            // do nothing
            break;
      }
      Yii::log('!payment request = ' . print_r($paymentRequest, true));
   }

   public function actionRequestTransfer() {
      $model = new DomainsTransferRegNew();
      if (Yii::app()->request->isPostRequest) {
         Yii::log('POST REQUEST IN Request Transfer');
         $this->performAjaxValidation($model);

         if (isset($_POST['DomainsTransferRegNew'])) {
            $model->attributes = $_POST['DomainsTransferRegNew'];

            if ($model->validate()) {
               $availableDomains = array();
               $domain = $model->domain_name;

               if(!Utility::isTransferPossible($domain)) {
                  $model->addError('domain_name', $domain . ' is invalid, already registered, or has a pending ticket, or has incorrect state');
               } else {
                  Yii::log('add domain = '.print_r($domain,true));
                  AuthOnlyBaseController::safeSerializeAndStoreObjInSession(array('domain' => $domain), 'transferdata');
                  $this->redirect(array('requesttransferdetails'), true);
               }
            }
         }
      }
      $this->render('requesttransfer', array('model' => $model));
   }

   public function actionRequestTransferDetails() {
      Yii::log('actionRequestTransferDetails!');
      $dd_model = new DomainsTransferDetails();

      if (Yii::app()->request->isPostRequest) {
         if(isset($_POST['DomainsTransferDetails'])) {
           $dd_model->setupRules($_POST['DomainsTransferDetails']);
           $dd_model->setFromPOST($_POST['DomainsTransferDetails']);
         }

         $this->performAjaxValidation($dd_model);

         if (isset($_POST['DomainsTransferDetails'])) {
            if ($dd_model->validate()) {
               $transferRequest = new CRSCommonAppService_transferRequestVO();
               $this->setRegRequestObject($dd_model, $transferRequest);
               $paymentRequest = new CRSCommonAppService_paymentRequestVO();
               $this->setPaymentRequestObject($dd_model, $paymentRequest, $transferRequest);

               Yii::log('payment request = ' . print_r($paymentRequest, true));
               Yii::log('registration request = ' . print_r($transferRequest, true));

               if ($dd_model->paymentType != 'CC')
                  $paymentRequest = null;

               if ($dd_model->charityPaymentForced) {
                  $paymentRequest = null;
                  $transferRequest->period = null;
                  $transferRequest->periodType = null;
               } else {
                  $regarr = get_reg_prices();
                  $r_codes = $regarr['CODE'];
                  $r_obj = $r_codes[$dd_model->renewalPeriod];
                  $transferRequest->period = $r_obj->duration;
                  $transferRequest->periodType = 'Y';
               }

               $authorization = true;
               CRSCommonAppService_service::verifyAuthCode($result, $this->backend_errors, Yii::app()->user->authenticatedUser, $dd_model->domain_name, $dd_model->authcode, $dd_model->authcode_failures + 1);
               if ($result == null || $result == false) {
                  $authorization = false;
                  $dd_model->authcode_failures = $dd_model->authcode_failures + 1;
                  if ($dd_model->authcode_failures < $dd_model->retries) {
                     $dd_model->authcode = null;
                     $dd_model->message_displayed = true;
                  } else {
                     Yii::app()->user->setFlash('error', 'The AuthCode you provided does not correspond with our records. Please check your information.');
                     $this->redirect(array('domains/requesttransfer'), true);
                     return;
                  }
               }
               if ($authorization) {
                  $result = null;
                  CRSCommonAppService_service::transferDomain($result, $this->backend_errors, Yii::app()->user->authenticatedUser, $transferRequest, $paymentRequest);
                  $model = new stdClass();
                  $model->ticket = $result;
                  $msg = WSAPIError::getErrors($this->backend_errors);
                  $model->errors = isset($msg) ? $msg : $this->backend_errors;
                  $model->domainName = $dd_model->domain_name;
                  $model->returningData = $dd_model;
                  AuthOnlyBaseController::safeSerializeAndStoreObjInSession($model, 'requesttransferresult');
                  $this->redirect(array('domains/requesttransferresult'), true);
               }
            } else {
               Yii::log('MODEL NOT VALIDATE!!!');
            }
         }

         if (isset($_POST['Nichandle_Details']) and isset($_POST['Nichandle_Details']['returningData'])) {
            // returning from a NIC edit/create
            $returndata = $_POST['Nichandle_Details']['returningData'];
            $dd_model = AuthOnlyBaseController::safeDeserializeObj($returndata['formdata']);
            $nicrole = $returndata['nictype'];
            $dd_model->$nicrole = $returndata['nic'];
         }
         if (isset($_POST['NicSearchModel']) and isset($_POST['NicSearchModel']['returningData'])) {
            // returning from a NIC find
            $returndata = $_POST['NicSearchModel']['returningData'];
            $dd_model = AuthOnlyBaseController::safeDeserializeObj($returndata['formdata']);
            $nicrole = $returndata['nictype'];
            $dd_model->$nicrole = $returndata['nic'];
         }
      } else {
         Yii::log('post not setup! ' . print_r($dd_model, true));
         if (isset($_GET['errorReturnData']) && AuthOnlyBaseController::isSavedInSession('requesttransferresult')) {
         	$dd_model = AuthOnlyBaseController::safeDeserializeObjFromFromSession('requesttransferresult')->returningData;
         }
      }

      if ($dd_model->domain_name == null && AuthOnlyBaseController::isSavedInSession('transferdata')) {
         $transferdata = AuthOnlyBaseController::safeDeserializeObjFromFromSession('transferdata');
         $dd_model = new DomainsTransferDetails($transferdata);
      }

      if ($dd_model->domain_name == null) {
         $this->redirect('index.php?r=domains/requesttransfer', true);
      }
      $this->render('requesttransferdetails', array('model' => $dd_model));
   }

   public function actionRequesttransferresult() {
      //$model = new Domains_Created(AuthOnlyBaseController::safeDeserializeObj($_GET['domaincreateresults']));
      if(AuthOnlyBaseController::isSavedInSession('requesttransferresult')) {
         $model = new stdclass();
         $this->render('requesttransferresult', array('model' => $model));
      } else {
         $this->redirect('index.php?r=domains/requesttransfer', true);
      }
   }

   /**
    * renders the landing-page for the domain-create flow
    * 
    * @return void  
    * @access public
    * @see    domainscreated.php, Domains_Created
    */
   public function actionDomainsCreated() {
   	  if (AuthOnlyBaseController::isSavedInSession('domaincreateresults')) {
         $dd_model = new Domains_Created(AuthOnlyBaseController::safeDeserializeObjFromFromSession('domaincreateresults'));
         $docs_model = null;
         if (AuthOnlyBaseController::isSavedInSession(self::SESSION_KEY_DOCUMENT_UPLOAD_RESULT)) {
            $docs_model = AuthOnlyBaseController::safeDeserializeObjFromFromSession(self::SESSION_KEY_DOCUMENT_UPLOAD_RESULT);
         }
         $this->render('domainscreated', array('model' => $dd_model, 'uploaderResult' => new UploaderResultModelWrapper($docs_model)));
      } else {
         $this->redirect('index.php?r=domains/regnew', true);
      }
   }

   // ########## non-action functions ##########

   /**
    * fetches from the CRS-WS-API backend, a domain-details object for a given domain name
    * 
    * @param  unknown   $domainName Parameter description (if any) ...
    * @return unknown   Return description (if any) ...
    * @access protected
    */
   protected function getDomainDetails($domainName, &$error) {
      $result = null;
      CRSDomainAppService_service::view($result, $error, Yii::app()->user->authenticatedUser, $domainName);
      return $result;
   }

   /**
    * creates one or more domains, using the domain list and details of the supplied model
    * 
    * @param  Domains_Creation_Details     $model Domains_Creation_Details model, including domain list
    * @return array     map of domain-names, whose creation was attempted, to results array, containing either 'ticket' => ticket-number (on success) or 'error' => error string, on error
    * @access protected
    */
   protected function createDomains($model) {

      $domain_creation_results = array();
      // Yii::log('CREATE DOMAINS MODEL:' . print_r($model, true));
      $regarr = get_reg_prices();
      #Yii::log('regarr:'.print_r($regarr,true), 'debug', 'DomainsController::createDomains()');
      $r_codes = $regarr['CODE'];
      #Yii::log('r_codes:'.print_r($r_codes,true), 'debug', 'DomainsController::createDomains()');
      $r_obj = $r_codes[$model->registration_period];
      #Yii::log('r_obj:'.print_r($r_obj,true), 'debug', 'DomainsController::createDomains()');
      $reg_period_years = $r_obj->duration;
      #Yii::log('reg_period_years:'.$reg_period_years, 'debug', 'DomainsController::createDomains()');
      $registrationRequest = new CRSCommonAppService_registrationRequestVO();
      $class_cat_vals = explode(':', $model->applicant);
      $registrationRequest->domainHolderClass = $class_cat_vals[0];  // type=xs:string, (NOT NULL), min=0
      $registrationRequest->domainHolderCategory = $class_cat_vals[1]; // type=xs:string, (NOT NULL), min=0
      $registrationRequest->domainHolder = $model->holder;   // type=xs:string, (NOT NULL), min=0
      $registrationRequest->adminContact1NicHandle = $model->admin_contact_nic_1; // type=xs:string, (NOT NULL), min=0
      $registrationRequest->adminContact2NicHandle = $model->admin_contact_nic_2; // type=xs:string, (NOT NULL), min=0
      if ($registrationRequest->adminContact2NicHandle == null or $registrationRequest->adminContact2NicHandle == '')
         unset($registrationRequest->adminContact2NicHandle);
      $registrationRequest->techContactNicHandle = $model->tech_contact; // type=xs:string, (NOT NULL), min=0
      for ($i = 0; $i < $model->nameserversCount; $i++) {
         $nameserver = new CRSCommonAppService_nameserverVO();
         $nameserver->name = $model->nameservers[$i];
         $nameserver->ipAddress = $model->ipAddresses[$i];
         $registrationRequest->nameservers[$i] = $nameserver;
      }
      $registrationRequest->requestersRemark = $model->remarks;  // type=xs:string, (NOT NULL), min=0
      $registrationRequest->period = $reg_period_years;

      $paymentRequest = null;
      switch ($model->paymentType) {
         case 'CC' :
            $paymentRequest = CreditCardFormSegmentModel::asWSAPIPaymentObject($model);
            break;
         case 'CH' :
            $registrationRequest->charityCode = $model->charitycode;
            break;
         case 'ADP' :
            // do nothing
            break;
      }

      Yii::log('paymentRequest= ' . print_r($paymentRequest, true), 'debug', ' $paymentRequest ');
      Yii::log('registrationRequest= ' . print_r($registrationRequest, true), 'debug', ' $paymentRequest ');

      foreach ($model->domainlist as $k => $domain) {
         $results = null;
         $registrationRequest->domainName = $domain;
         $this->fillIpAddresses($model, $registrationRequest, $domain);

         CRSCommonAppService_service::registerDomain($result, $this->backend_errors, Yii::app()->user->authenticatedUser, $registrationRequest, $paymentRequest);
         if (count($this->backend_errors) == 0) 
            $domain_creation_results[$domain]['ticket'] = $result;
         else
            $domain_creation_results[$domain]['error'] = WSAPIError::getErrorsNotEmpty($this->backend_errors);
      }
      return $domain_creation_results;
   }

   /**
    * removing IP addresses for nameservers unrelated to the current domain
    */
   protected function fillIpAddresses($model, $registrationRequest, $domain) {
       $nsNameGlueRecValidator = new NSNameGlueRecValidator();
       foreach ($registrationRequest->nameservers as $i => $nameserver) {
           if (isset($model->ipAddresses[$i]) && $nsNameGlueRecValidator->domainname_matches($domain, $nameserver->name) == 1) {
               $nameserver->ipAddress = $model->ipAddresses[$i];
           } else {
               $nameserver->ipAddress = "";
           }
       }
   }

   public function actionVerifyDns() {
       if (Yii::app()->request->isPostRequest && isset($_POST['nameservers']) && isset($_POST['domains'])) {
           $nameservers = array();
           $req_ns = is_array($_POST['nameservers']) ? $_POST['nameservers'] : array($_POST['nameservers']);
           foreach($req_ns as $ns) {
               $nameserver = new CRSDomainAppService_nameserverVO();
               $nameserver->name = $ns['name'];
               $nameserver->ipAddress = $ns['ipAddress'];
               $nameservers[] = $nameserver;
           }

           $domains = array();
           $req_domains = $req_ns = is_array($_POST['domains']) ? $_POST['domains'] : array($_POST['domains']);
           foreach($req_domains as $dom) {
              $domains[] = is_string($dom) ? $dom : '';
           }

           $response = null;
           $errors = '';

           $validated = CRSCommonAppService_service::validateNameservers($response, $errors, Yii::app()->user->authenticatedUser, $domains, $nameservers);
           if ($validated) {
               echo $response->message;
           } else {
               echo "Sorry, there were errors while trying to validate nameservers.";
           }
       } else {
           echo "Sorry, request is malformed and cannot be processed.";
       }
       Yii::app()->end();
   }

    private function summarizeResult($uploadResult) {
        $docResult = new UploaderResultModelWrapper($uploadResult);
        $result = "";
        if (!$docResult->isEmpty()) {
            if ($docResult->hasErrors()) {
                $result .= "<br/>Some documents failed to be uploaded:<ul>";
                foreach ($docResult->getHumanReadableResults() as $ur) {
                    $result .= "<li>".$ur['documentName'].": ".$ur['status']."</li>";
                }
            } else {
                $result .= "<br/>All documents uploaded successfully";
            }
        }
        return $result;
    }
}

