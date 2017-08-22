<?php

/**
 * Description of Account_deleted_domain_reportController
 *
 * @author artur
 */
class Account_deleted_domain_reportController  extends GridController {
   
   public $records = 0;
   
    public function actionMenu() {
      $model = new DeletedDomainReportModel();
      Yii::log('ACTION get menu');
      $this->render('menu',array('model'=>$model));
   }
   
   public function actionGetgriddatadeleted() {
      Yii::log('ACTION GET DAta');
      return $this->actionGetgriddata();
   }
   
   public function getData($model, $criteria, $sort, $perpage, $pagenum) {
      Yii::log('GET DATA = '.print_r($model,true));
      $pagedata = array('records' => 0, 'page' => $pagenum, 'total' => 0, 'rows' => array());
      $offset = $perpage * $pagenum - $perpage;
      $limit = $perpage;
      $result = null;

      CRSDomainAppService_service::findDeletedDomains($result, $this->backend_errors, Yii::app()->user->authenticatedUser, $criteria, $offset, $limit, $sort);
      Yii::log('CRITERIA= '.print_r($criteria,true). '  SORT '.print_r($sort,true) );
      if (count($this->backend_errors) == 0) {
         Yii::log('RESULTT= '.print_r($result,true));
         $this->records = $result->total;
         Yii::log('COUNT OK ' . print_r($result->total, true));
         if ($this->records && $result->deletedDomains != null) {
            Yii::log('RESULT OK ' . print_r($result->deletedDomains, true));
            $total = 0;
            if ($this->records > 0) {
               $total = ceil($this->records / $perpage);
            } else {
               $total = 0;
            }

            if ($pagenum > $total)
               $pagenum = $total;

            $pagedata['total'] = $total;
            $pagedata['page'] = $pagenum;
            $pagedata['records'] = $this->records;

            if (is_array($result->deletedDomains) && is_object($result->deletedDomains[0])) {
               foreach ($result->deletedDomains as $key => $o) {
                  $row = $model->addResults($o);
                  $pagedata['rows'][] = $row;
               }
            } else if (is_object($result->deletedDomains)) {
               $row = $model->addResults($result->deletedDomains);
               $pagedata['rows'][] = $row;
            } else {
               Yii::log('Undefined result');
            }
         } else {
            Yii::log('Result is null.');
         }
      } else {
         Yii::log('Some errors ' . print_r($this->backend_errors, true));
      }
      return $pagedata;
   }
}

?>
