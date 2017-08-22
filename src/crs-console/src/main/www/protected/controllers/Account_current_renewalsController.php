<?php

class Account_current_renewalsController extends AccountsGridController {
   
   public function actionMenu() {
      $model = new CurrentRenewalsModel($this);
      $model->countDays = 'OVERDUE';
      if (isset($_REQUEST['CurrentRenewalsModel']['countDays'])) {
         $model->countDays = $_REQUEST['CurrentRenewalsModel']['countDays'];
      }
      Utility::writeActionToSession('account_current_renewals/menu');
      $this->render('menu', array('model' => $model));
   }
   
   public function getSelectionModel($isVoluntary = false) {
      return new CurrentRenewalsSelectionModel($isVoluntary);
   }

   public function getSelectionModelName() {
      return 'CurrentRenewalsSelectionModel';
   }

   public function setType_CurrRnR() {
      $this->exportFileName = 'CurrentRenewalsModel_';
   }

   public function actionConfirm_currnr() {
      $this->setType_CurrRnR();
      $this->showConfirmPage();
   }

   public function actionConfirm() {
      $this->showConfirmPage();
   }
   
   protected function showConfirmPage() {
      Yii::log('SHOW CONFIGRM PAGE= '.print_r($_POST,true) .   '   '. print_r($_GET,true));
      $rows = array();
      $domains = array();
      if(isset($_POST['gridactionpay']['domainlist'])) {
         $rows = split(",",$_POST['gridactionpay']['domainlist']);
         for($i=0;$i<count($rows);$i++) {
            $tmp = split("~",$rows[$i]);
            $domains [] = $tmp[0];
         }
         $_POST['gridactionpay']['domainlist'] = implode(",", $domains);
      }
      
      Yii::log('domainlist= '.print_r($rows,true));
      
      
      $postedForm = isset($_POST['gridactionpay']) ? $_POST['gridactionpay'] : ( isset($_POST['gridactionnopay']) ? $_POST['gridactionnopay'] : null );
      if ($postedForm != null) {
         
         if($postedForm['command'] == 'payonline') {
            $model = $this->getSelectionModel();
         } else {
            $model = $this->getSelectionModel(true);
         }
         
         $model->setFromPOST($postedForm);
         $model->needCreditCard = ($model->command == 'payonline') ? 1 : 0;
         $model->defaultPeriods = implode(",",$rows);
         $this->redirectAfterPost('account_current_renewals/confirmaction', $model);
      } else {
         $this->redirect($model->returnurl, true);
      }
   }
   
   public function getViewBase() {
      return '/account_current_renewals/';
   }

   public function actionGetgriddatacurrrnr() {
      $this->setType_CurrRnR();
      return $this->actionGetgriddata();
   }
   
   function lastDateOfMonth($Month, $Year=-1) {
       if ($Year < 0) $Year = 0+date("Y");
       $aMonth         = mktime(0, 0, 0, $Month, 1, $Year);
       $NumOfDay       = 0+date("t", $aMonth);
       $LastDayOfMonth = mktime(0, 0, 0, $Month, $NumOfDay, $Year);
       return $LastDayOfMonth;
   }  

   
   public function getData($model, $criteria, $sort, $perpage, $pagenum) {
      $pagedata = array('records' => 0, 'page' => $pagenum, 'total' => 0, 'rows' => array());
      $offset = $perpage * ($pagenum - 1);
      $limit = $perpage;
      $result = null;
      
      CRSDomainAppService_service::getDomainForRenewalPayment($result, $this->backend_errors, Yii::app()->user->authenticatedUser, $model->countDays, $criteria, $offset, $limit, $sort);
      if (count($this->backend_errors) == 0 && $result != null) {
         if($result->totalResults != 0) {
            $pagedata['records'] = $result->totalResults;
            $pagedata['total'] = math_div($result->totalResults, $perpage) + 1;
            if (is_array($result->results)) {
               for($i=0;$i<count($result->results);$i++) {
                  $r = $this->addResult($model, $result->results[$i]);
                  if ($r != null)
                     $pagedata['rows'][] = $r;
               }
            } else {
               if (is_object($result->results)) {
                   $row = $model->addResults($result->results);
                     $pagedata['rows'][] = $row;
               } else {
                  Yii::log('no results : ' . print_r($result, true), 'warning', 'AccountsGridController::getData()' . __LINE__);
               }
            }
         } else {
            Yii::log('No results');
         }
      } else {
         Yii::log('Null result '.print_r($this->backend_errors, true));
      }
      return $pagedata;
   }

}

?>
