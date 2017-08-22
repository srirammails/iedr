<?php

/**
 * Description of Registrar_DetailsControler
 *
 * @author Artur Kielak
 */
class Registrar_detailsController extends Controller {

   private function setModel(&$model) {
      $result = null;
      $errs = array();
      $user = Yii::app()->user->authenticatedUser;
      $userName = Yii::app()->user->name;
      CRSNicHandleAppService_service::getDefaults($result, $errs, $user, $userName);
      if ($result != null) {
         if (count($errs) == 0) {
            Yii::log('$result = ' . print_r($result, true));
            if(property_exists($result, 'techContactId')) {
               $model->setTechContact($result->techContactId);
            }
            if(property_exists($result, 'nameservers')) {
               $model->setNameservers($result->nameservers);
               $model->setNameserversCount(count($result->nameservers));
            }
            if(property_exists($result, 'dnsNotificationPeriod')) {
               $model->setDnsNotificationPeriod($result->dnsNotificationPeriod);
            }
            if(property_exists($result, 'emailInvoiceFormat')) {
               $model->setEmailInvoiceFormat($result->emailInvoiceFormat);
            }
         } else {
            Yii::log("Errors in Registrar_detailsController" . print_r($errs, true));
         }
      } else {
         Yii::log("NULL IN Registrar_detailsController " . print_r($errs, true));
      }
   }

   public function actionDomaindetailsserialized() {
      $retval = '';
      if (Yii::app()->request->isPostRequest) {
         if (isset($_POST['RegistrarDetailsModel'])) {
            $model = new RegistrarDetailsModel();
            $model->attributes = $_POST['RegistrarDetailsModel'];
            $retval = AuthOnlyBaseController::safeSerializeObj($model);
         }
      }
      echo $retval;
   }

   public function actionViewregistrar() {
      Yii::log('POSTTT= ' . print_r($_POST, true));
      $model = new RegistrarDetailsModel();
      $user = Yii::app()->user->authenticatedUser;
      $message = '';
      $nicHandle = '';
      $this->setModel($model);
      if (Yii::app()->request->isPostRequest) {
         if (isset($_POST['NicSearchModel']) and isset($_POST['NicSearchModel']['returningData'])) {
            $returndata = $_POST['NicSearchModel']['returningData'];
            $nicHandle = $returndata['nic'];
            $model->setTechContact($nicHandle);
         }

         if (isset($_POST['Nichandle_Details']) and isset($_POST['Nichandle_Details']['returningData'])) {
            $returndata = $_POST['Nichandle_Details']['returningData'];
            $nicHandle = $returndata['nic'];
            $model->setTechContact($nicHandle);
         }

         $this->performAjaxValidation($model);

         if (isset($_POST['RegistrarDetailsModel'])) {
            $model->attributes = $_POST['RegistrarDetailsModel'];
            $model->setFromPOST($_POST['RegistrarDetailsModel']);
            if ($model->validate()) {
               CRSNicHandleAppService_service::saveDefaults($errs, $user, $model->getTechContact(), $model->getNameserversToSubmit(), $model->getDnsNotificationPeriod(), $model->getEmailInvoiceFormat());
               if (count($errs) == 0) {
                  $message = "Modify data is successful. These changes will take effect after relogin.";
               } else {
                  $message = "Modify data is not successful.";
               }
            }
         }
      } else {
         Yii::log('NOT POST REQUEST');
      }
      $model->setMessage($message);
      $this->render('viewregistrar', array('model' => $model));
   }

}

?>
