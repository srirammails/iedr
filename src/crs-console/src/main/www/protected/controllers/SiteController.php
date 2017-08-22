<?php

/**
 * defines SiteController class
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
 * This controller class handles general web app actions, including index, error, login, logout
 * 
 * @category  NRC
 * @package   IEDR_New_Registrars_Console
 * @author    IEDR <asd@iedr.ie>
 * @copyright 2011 IEDR
 * @license   http://www.iedr.ie/ (C) IEDR 2011
 * @version   Release: @package_version@
 * @link      https://statistics.iedr.ie/
 */
class SiteController extends Controller {
   public $message;
   /**
    * defines actions to use CViewAction 
    *
    * @access public
    */
   public function actions() {
       if ($this->checkTimeout()) {
           $this->redirect('index.php?r=site/logout');
       }
       if(!isset(Yii::app()->user->authenticatedUser)) {
           Yii::log('WITHOUT LOGIN');
           $this->redirect('index.php?r=site/login');
       }
       return array(
          // page action renders "static" pages stored under 'protected/views/site/pages'
          // They can be accessed via: index.php?r=site/page&view=FileName
          'page' => array(
              'class' => 'CViewAction',
              'basePath' => 'application.views.site.pages',
          ),
      );
   }

   private function checkTimeout() {
   	try {
   		CRSAuthenticationService_service::isUserSwitched($result, $this->backend_errors, Yii::app()->user->authenticatedUser);
   		if (isset($this->backend_errors)) {
   			return true;
   		}
   		return false;
   	} catch (Exception $e) {
		return true;
   	}
   }
   /**
    * This is the default 'index' action that is invoked
    * when an action is not explicitly requested by users.
    *
    * Renders the view file 'protected/views/site/index.php'
    * using the default layout 'protected/views/layouts/main.php'
    * 
    * @access public
    * @see    views/site/index.php
    */
   public function actionIndex() {
      $this->render('index');
   }

   public function actionGetcategory() {
      $result = null;
      $retval = array();
      $errors = array();
      CRSTicketAppService_service::getCategoriesForClass($result, $errors, Yii::app()->user->authenticatedUser, number_format($_GET['index']) );
      if($result != null) {
         if(count($errors) == 0) {
            if(is_array($result)) {
               for($i = 0 ;$i < count($result);$i++) {
                   $retval[$i] = $result[$i]->name;
               }
            } else if(is_object($result)) {
               $retval[0] = $result->name;
            }
            
            if(count($retval) != 0) {
               echo implode('~', $retval);
            } 
            
            
         } else {
            Yii::log('getCategoryForClass() errors '.print_r($errors,true));
            echo implode('~', $errors);
         }
      } else {
         Yii::log('getCategoryForClass() result is null');
      }
   }
   
      
   public function actionValiddomains() {
      if(isset($_GET) && isset($_GET['list'])) {
         $domains = split(",", $_GET['list']);
         $user = Yii::app()->user->authenticatedUser;
         $errors = '';
         $result = null;
         $invalidDomains = "";
         CRSDomainAppService_service::checkPayAvailable($result, $errors, $user, $domains);
         if($result != null) {
            if(count($errors) == 0) {
               if(is_array($result)) {
                  $invalidDomains = implode("~", $result);
                  echo $invalidDomains;
               } else if(is_string($result)) {
                  echo $result;
               }
            }
         } else {
            if(count($errors) == 0) {
               echo "ok";
            } else {
               if (strstr($errors, "Permission denied")) {
               	 echo "Permission denied while pre-validating payment for the domains. One of the selected domains is not owned by the user.";
               } else {
                 echo "Error while pre-validating payment for the domains";
               }
            }
         }
      } else {
         echo "invalid";
      }
   }
   
   public function actionIssesion() {
      $result = null;
      if (!isset(Yii::app()->user->authenticatedUser)) {
          //just skip
      } else {
          //TODO used dedicated method instead!!!
          CRSAuthenticationService_service::isUserSwitched($result, $this->backend_errors, Yii::app()->user->authenticatedUser);
          if (isset($this->backend_errors)) {
              echo "sessiontimeout";
          } else {
              $permission = Utility::getPermission();
              if($permission == 0) {
                  echo "nopermission";
              }
          }
      }
   }

   /**
    * This is the action to handle external exceptions.
    *
    * @access public
    */
   public function actionError() {
      $error = Yii::app()->errorHandler->error;
      if ($error) {
         if (Yii::app()->request->isAjaxRequest)
            echo 'Error during ajax request. Check logs for details';
         else
            $this->render('error', $error);
      }
   }

   /**
    * Displays account summary iframe, 'protected/views/site/summaryframe.php'
    *
    * @access public
    * @see summaryframe.php
    */
   public function actionSummaryFrame() {
      $this->renderpartial('summaryframe');
   }

   /**
    * Account summary IFrame fetches several numbers via Ajax calls, which are all handled by this action function.
    *
    * This function outputs formatted numbers for the account summary display
    *
    * @access public
    * @see summaryframe.php
    */
   public function actionSummary() {
      if(!isset(Yii::app()->user->name)) {
         Yii::log('actionSummary Yii::app()->user->name no set');
      }
      
      if(!isset($_GET['id'])) {
         Yii::log('actionSummary $_GET[id] no set');
      }
      
      if(Utility::isRegistrar() || Utility::isSuperRegistrar()) {
         switch ($_GET['id']) {
             case 'DepositBalance': echo number_format(getDepositBalance(Yii::app()->user->name), 2);
               break;
         }
      }
   }

   /**
    * Displays the contact page (unused)
    * $user = Yii::app()->user->authenticatedUser;
    * @access public
    * @see    contact.php, ContactForm
    */
   public function actionContact() {
      $model = new ContactForm();
      if (isset($_POST['ContactForm'])) {
         $model->attributes = $_POST['ContactForm'];
         if ($model->validate()) {
            $headers = "From: {$model->email}\r\nReply-To: {$model->email}";
            mail(Yii::app()->params['adminEmail'], $model->subject, $model->body, $headers);
            Yii::app()->user->setFlash('contact', 'Thank you for contacting us. We will respond to you as soon as possible.');
            $this->refresh();
         }
      }
      $this->render('contact', array('model' => $model));
   }

   /**
    * Displays the login page, or handles a login request - on success, redirecting to originating url, if any
    *
    * @access public
    * @see    LoginForm, login.php, NicHandleIdentity
    */
   public function actionLogin() {
      if(!Utility::isTomcatExist()) {
         $returnMessage = new stdclass();
         $returnMessage->message =  'Could not connect to the backend server, please try again later.';
         $serializedMessage = AuthOnlyBaseController::safeSerializeObj($returnMessage);
         $this->redirect(array('index', 'message' => $serializedMessage));
      }
      
      $model = new LoginForm();
      if (isset($_POST['ajax']) && $_POST['ajax'] === 'login-form') {
         echo CActiveForm::validate($model);
         Yii::app()->end();
      }     
      
      // collect user input data
      if (isset($_POST['LoginForm'])) {
         $model->attributes = $_POST['LoginForm'];
         // validate user input and redirect to the previous page if valid
         if ($model->validate() && $model->login()) {
         	if (Yii::app()->user->authenticatedUser->passwordChangeRequired) { 
				$this->redirect('/index.php?r=site/changePassword');
         	} else {
             	$this->redirect(Yii::app()->user->returnUrl);
         	}
         } 
      } 

      CRSInfo_service::getServerTime($model->serverTime, $errs);
      
      $this->render('login', array('model' => $model));
   }

   /**
    * Changes the password of the logged in user.
    *
    * @access public
    * @see    changePassword.php, ChangePasswordForm
    */
   public function actionChangePassword() {
      $model = new ChangePasswordForm();
      #Yii::log('POST CHANGES '.print_r($_POST,true));
      if (array_key_exists('yt0', $_POST) && $_POST['yt0'] == "Change Password?") {
         $model->attributes = $_POST['ChangePasswordForm'];
         if ($model->validate()) {
            $model->unsetAttributes();
            $model->msg = "Password updated successfully.";
         }
      }
      $this->render('changePassword', array('model' => $model));
   }

   /**
    * Logs out the current user and redirect to homepage.
    *
    * @access public
    */
   public function actionLogout() {
   	  if (isset(Yii::app()->user->authenticatedUser)) {
      	CRSAuthenticationService_service::logout($this->backend_errors, Yii::app()->user->authenticatedUser);
      }
      Yii::app()->user->logout();
      $this->redirect('/index.php?r=site/login');
   }

   public function actionNewDirectAccount() {
   	 $actionName = 'SiteController::actionNewDirectAccount()';
  	 Yii::log('enter ; ' . print_r($_REQUEST, true), 'debug', $actionName);
     $nhd_model = new Nichandle_Details();
     $passwordModel = new NewPassword();
     $this->performAjaxValidation(array($passwordModel, $nhd_model));
     if (Yii::app()->request->isPostRequest and isset($_POST['Nichandle_Details']) and isset($_POST['NewPassword'])) {
         Yii::log('Is POST', 'debug', $actionName);
         $nhd_model->attributes = $_POST['Nichandle_Details'];
         $passwordModel->attributes = $_POST['NewPassword'];
         
         if (array_key_exists('yt0', $_POST)) {
         	$button = $_POST['yt0'];
         }
         if (array_key_exists('yt1', $_POST)) {
         	$button = $_POST['yt1'];
         } 
         
         $confirmationNeeded = ($button == 'Create');         
         $editData = ($button == 'Back');
         if (!$editData and $nhd_model->validate() and $passwordModel->validate()) {
         	if ($confirmationNeeded) {
         		$this->render('newDirectAccount', array('model' => $nhd_model, 'passwordModel' => $passwordModel, 'confirmation' => $confirmationNeeded));
         		return;
         	} else {
         		Yii::log('Validates', 'debug', $actionName);
         		$aNic = $this->createAccount($nhd_model, $passwordModel);
         		if ($this->backend_errors == null) {
         			$nhd_model->nicHandleId = $aNic->nicHandleId;
         			if (property_exists($aNic, 'secret'))
         				$secret = $aNic->secret;
         			Yii::log('Created NIC ' . $nhd_model->nicHandleId, 'debug', $actionName);
         			$nhd_model->message = 'DIRECT_ACCOUNT_CREATED';
         		}
         		else
         			$nhd_model->error = $this->backend_errors;
         	}
         }
         else {
            Yii::log('Validation Errors', 'debug', $actionName);
         }
      }
      $this->render('newDirectAccount', array('model' => $nhd_model, 'passwordModel' => $passwordModel, 'confirmation' => false, 'secret'=>isset($secret) ? $secret : null));
   }
   
   public function actionTwoFactorAuth() {
      $newSecret = null;
   	  if (Yii::app()->request->isPostRequest and array_key_exists('yt0', $_POST)) {
   	  	CRSNicHandleAppService_service::changeTfaFlag($newSecret, $this->backend_errors, Yii::app()->user->authenticatedUser, $_POST['yt0'] == 'Enable');
   	  } 
   	  
   	  CRSNicHandleAppService_service::isTfaUsed($tfaUsed, $this->backend_errors, Yii::app()->user->authenticatedUser);
   	  $this->render('twoFactorAuthSetup', array('tfaUsed' => $tfaUsed, 'newSecret' => $newSecret));
   }
   
   protected function createAccount($nhd, $passwordModel) {
	       Yii::log('createAccount', 'debug', 'SiteController::createAccount()');
      $result = null;
      // prepare data
      $data = $nhd->getAsObject();
      // attempt create
      CRSResellerAppService_service::createDirectAccount($result, $this->backend_errors, $data, $passwordModel->new_password, $passwordModel->useTwoFactorAuthentication);
      Yii::log('result=' . print_r($result, true), 'debug', 'SiteController::createAccount()');
      Yii::log('errors=' . print_r($this->backend_errors, true), 'debug', 'SiteController::createAccount()');
      return $result;
   }

    public function actionDynamiccounties() {
        $returnval = '';
        $country = trim($_POST['Nichandle_Details']['country']);
        foreach (getCountyOptions($country) as $county)
            $returnval .= '<option value="' . $county . '">' . $county . '</option>';
        echo $returnval;
    }

}
