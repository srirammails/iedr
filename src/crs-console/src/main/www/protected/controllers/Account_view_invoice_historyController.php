<?php
class Account_view_invoice_historyController extends AccountsGridController {
   public function actionMenu() {
      $model = new AllInvoiceModel();
      if (isset($_REQUEST['AllInvoiceModel']['date'])) {
         $model->date = $_REQUEST['AllInvoiceModel']['date'];         
      }
      Utility::writeActionToSession('account_view_invoice_history/menu');
      $this->render("menu", array('model'=>$model));
   }
   
   public function actionInvoiceview() {
      $result = null;
      if(array_key_exists('id', $_GET) && isset($_GET['id'])) {
         $errors = array();
         $user = Yii::app()->user->authenticatedUser;
         CRSPaymentAppService_service::getInvoiceInfo($result, $errors, $user, $_GET['id']);
         if($result != null) {
            if(count($errors) == 0) {
               Yii::log('GET INVOICE = '.print_r($result, true));
            } else {
               Yii::log('GET INVOICE ERROS ');
            }
         } else {
            Yii::log('GET INVOICE '.$_GET['id'].' RESULT IS NULL '.print_r($errors,true));
         }
      }
      $this->render("invoiceview", array('result'=>$result));
   }
   
   public function actionConfirm() {
      $this->showConfirmPage();
   }
   
   public function actionGetgriddata_invoices() {
      $this->exportFileName = 'Invoice_';
      $this->actionGetgriddata();
   }
   
   public function actionSendemail() {
      $number = $_GET['number'];
      $errs = array();
      $user = Yii::app()->user->authenticatedUser;
      CRSPaymentAppService_service::sendEmailWithInvoices($errs, $user, $number);
      if(count($errs) == 0){
          Yii::log('Send email successful.');
          echo "sendemail";
      } else {
          Yii::log('Send email failure '.print_r($errs,true));
          echo "sendemailfailure";
      }
   }
   
   function actionDownload() {
   	  $type = $_GET['type'];
   	  $number = $_GET['number'];   	    	  

   	  $wsapi_soap_url = Yii::app()->params['wsapi_soap_url'];
   	  $username = Yii::app()->user->authenticatedUser->username;
      $token = Yii::app()->user->authenticatedUser->authenticationToken;
      
	  $url = $wsapi_soap_url . "view-invoice/invoice?userName=" . $username . "&token=" . $token . "&invoiceNumber=" . $number . "&fileType=" . $type;
   	  
   	  $ch = curl_init($url);
   	  curl_setopt($ch,CURLOPT_HEADER,true);
   	  curl_setopt($ch, CURLOPT_RETURNTRANSFER, 1);
   	  curl_setopt($ch, CURLOPT_BINARYTRANSFER, 1);
   	  $result = curl_exec($ch);
   	  list($headers,$content) = explode("\r\n\r\n",$result,2);   	  
   	  Yii::log($headers, 'DEBUG', 'curl headers');
   	  foreach (explode("\r\n",$headers) as $hdr) {
   	  	if (stristr($hdr, 'content'))
   	  		header($hdr);
   	  }
   	  echo $content;
   	  curl_close($ch);
   }
   
   public function actionGetgriddata_menu() {
      return $this->actionGetgriddata_invoices();
   }
   
   protected function getData($model, $criteria, $sort, $perpage, $pagenum) {
//      CRSCommonAppService_service::runInvoicing($errs, Yii::app()->user->authenticatedUser);
//      if(count($errs) == 0) {
//         Yii::log('runInvoicing ok '.print_r(Yii::app()->user->authenticatedUser,true));
//      }
      $errs = '';
      $pagedata = array('records' => 0, 'page' => $pagenum, 'total' => 0, 'rows' => array());
      $offset = $perpage * $pagenum - $perpage;
      $limit = $perpage;
      $count = 0;

      $result = null;
      
      $user = Yii::app()->user->authenticatedUser; 
      $criteria->billingNhId = $user->username;
      $date = $model->date;
      $criteria->settledFrom = date("Y-m-01", strtotime($date));
      $criteria->settledTo = date("Y-m-01", strtotime($date."+1 months"));
      Yii::log('crap= ' . print_r($model->date, true));
      Yii::log('citeria= ' . print_r($criteria, true));
      Yii::log('$offset= ' . print_r($offset, true));
      Yii::log('$limit= ' . print_r($limit, true));
      Yii::log('$sort= ' . print_r($sort, true));
      Yii::log('date= ' . print_r($model->date, true));
      
      CRSPaymentAppService_service::findInvoices($result, $this->backend_errors, $user, $criteria, $offset, $limit, $sort);
      if (count($this->backend_errors) == 0 && $result != null) {
         Yii::log('Results= '.print_r($result,true));
         $count = $result->totalResults;
         $total = 0;
         if ($count > 0) {
            $total = ceil($count / $perpage);
         } else {
            $total = 0;
         }

         if ($pagenum > $total)
            $pagenum = $total;

         $pagedata['total'] = $total;
         $pagedata['page'] = $pagenum;
         $pagedata['records'] = $count;

         if($result->totalResults) {
            if ( is_array($result->results) && is_object($result->results[0])) {
               foreach ($result->results as $k => $o) {
                  $r = $this->addResult($model, $o);
                  if ($r != null) {
                     $pagedata['rows'][] = $r;
                  }
               }
            } else if (is_object($result->results)) {
               $r = $this->addResult($model, $result->results);
               if ($r != null)
                  $pagedata['rows'][] = $r;
            } else {
               Yii::log('no results : ' . print_r($result, true), 'warning', 'Accounts_msdController::getInvoicesData()' . __LINE__);
            }
         }
      }
      else {
         Yii::log(print_r($this->backend_errors, true), 'error', 'Accounts_msdController::getInvoicesData()' . __LINE__);
      }

      return $pagedata;
   }
}
?>
