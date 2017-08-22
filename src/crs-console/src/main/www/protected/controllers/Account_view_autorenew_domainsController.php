<?php

/**
 * Description of Accounts_autorenewController
 *
 * @author Artur Kielak
 */
class Account_view_autorenew_domainsController extends GridController {

   public function showConfirmPage() {
      if (isset($_POST['gridaction'])) {
         switch($_POST['gridaction']['command']) {
            case 'noautorenew':
               $gs_model = $this->getSelectionModel(true);
               break;
            default:
               $gs_model = $this->getSelectionModel();
               break;
         }
         
         $gs_model->setFromPOST($_POST['gridaction']);
         $this->processGridActionCommand($gs_model);
         $this->redirectAfterPost('account_view_autorenew_domains/confirmaction', $gs_model);
      } else {
         $this->redirect($gs_model->returnurl, true);
      }
   }

   public function actionConfirm_billable() {
      $this->showConfirmPage();
   }

   public function actionMenu() {
      $model = new ARDomainModel();
      Utility::writeActionToSession('account_view_autorenew_domains/menu');
      $this->render('menu', array('model' => $model));
   }

   protected function getData($model, $criteria, $sort, $perpage, $pagenum) {
      $pagedata = array('records' => 0, 'page' => $pagenum, 'total' => 0, 'rows' => array());
      $offset = $perpage * $pagenum - $perpage;
      $limit = $perpage;
      $result = null;
      $errs = null;
      if ($criteria->contactType == null)
         unset($criteria->contactType);
      
      $user = Yii::app()->user->authenticatedUser;
      if(!isset($criteria->renewalModes)) {
         $criteria->renewalModes[] = 'Autorenew';
         $criteria->renewalModes[] = 'RenewOnce';
      }

      CRSDomainAppService_service::findDomains($result, $errs, $user, $criteria, $offset, $limit, $sort);
      if (count($errs) == 0 && $result != null) {
         Yii::log('RESULT IN AR = '.print_r($result,true));
         $count = 0;
         if (isset($result->totalResults) && is_integer($result->totalResults)) {
            $count = $result->totalResults;
         } else {
            Yii::log('ERROR RELATED WITH TOTAL_RESULTS');
         }

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


         if (isset($result->results)) {
            if (is_array($result->results)) {
               foreach ($result->results as $k => $o)
                  $pagedata['rows'][] = $model->addResults($o);
            } else if (is_object($result->results)) {
               $pagedata['rows'][] = $model->addResults($result->results);
            } else {
               Yii::log(print_r($result, true), 'warning', 'GridController::getData() : results format?' . __LINE__);
            }
         } else {
            Yii::log(print_r($result, true), 'warning', 'GridController::getData() : WARN : no \'results\':' . __LINE__);
         }
      } else {
         Yii::log(print_r($errs, true), 'error', 'GridController::getData() : ERROR:' . __LINE__);
      }
      return $pagedata;
   }

}

?>
