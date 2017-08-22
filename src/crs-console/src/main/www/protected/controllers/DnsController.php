<?php

/**
 * Short description for file
 * 
 * Long description (if any) ...
 * 
 * PHP version 5
 * 
 * New Registration Console (C) IEDR 2011
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
 * Short description for class
 * 
 * Long description (if any) ...
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
class DnsController extends GridController {

   /**
    * Description for public
    * @var    array 
    * @access public
    */
   public $breadcrumbs = array('DNS');

   /**
    * Short description for function
    * 
    * Long description (if any) ...
    * 
    * @return void  
    * @access public
    */
   public function __construct() {
      
   }

   /**
    * Short description for function
    * 
    * Long description (if any) ...
    * 
    * @return void  
    * @access public
    * @see    dnsupdate.php, DNS_BulkMod
    */
   public function asignValue($i, $name, $ip, &$model) {
      switch ($i) {
         case '0':
            $model->ns_0 = $name;
            break;
         case '1':
            $model->ns_1 = $name;
            break;
         case '2':
            $model->ns_2 = $name;
            break;
         case '3':
            $model->ns_3 = $name;
            break;
         case '4':
            $model->ns_4 = $name;
            break;
         case '5':
            $model->ns_5 = $name;
            break;
         case '6':
            $model->ns_6 = $name;
            break;
         case '7':
            $model->ns_7 = $name;
            break;
         case '8':
            $model->ns_8 = $name;
            break;
         case '9':
            $model->ns_9 = $name;
            break;
      }
   }

//    public function getNameServers(&$model, $domain) {
//       $result = null;
//       $errors = array();
//       CRSDomainAppService_service::view($result, $errors, Yii::app()->user->authenticatedUser, $domain);
//       if ($result != null) {
//          if (count($errors) == 0) {
//             $i = 0;
//             if (is_array($result->domain->nameservers)) {
//                foreach ($result->domain->nameservers as $dns) {
//                   $model->setValues($i, $dns->name, $dns->ipAddress);
//                   $i++;
//                }
//             } else if (is_object($result->domain->nameservers)) {
//                $model->setValues($i, $dns->name, $dns->ipAddress);
//             }
//             $model->count = $i;
//          }
//       }
//    }

//    public function actionGetns() {
//       $domain = $_GET['domain'];
//       if (!isset($domain)) {
//          echo "invalid";
//       }

//       $result = null;
//       $errors = array();
//       CRSDomainAppService_service::view($result, $errors, Yii::app()->user->authenticatedUser, $domain);
//       if ($result != null) {
//          if (count($errors) == 0) {
//             $toSend = array();
//             if (is_array($result->domain->nameservers)) {
//                foreach ($result->domain->nameservers as $dns) {
//                   $toSend [] = "$dns->name=$dns->ipAddress";
//                }
//                echo "" . implode("~", $toSend) . "";
//             } else if (is_object($result->domain->nameservers)) {
//                $name = $result->domain->nameservers->name;
//                $ip = $result->domain->nameservers->ipAddress;
//                $toSend [] = "$name=$ip";
//                echo "" . implode("~", $toSend) . "";
//             }
//          }
//       }
//    }

   public function actionDnsupdate() {
      $model = new DNS_BulkMod();
      $model->clearErrors();

      if (Yii::app()->request->isPostRequest && isset($_POST['DNS_BulkMod'])) {
         $model->attributes = $_POST['DNS_BulkMod'];
         foreach ($_POST['DNS_BulkMod']['nameservers'] as $i => $value) {
            $model->nameservers[$i] = $value;
         }
         $this->performAjaxValidation($model);
         if ($model->validate()) {
            if ($this->saveDNS($model)) {
               Yii::log('SAVE DNS OK');
            } else {
               Yii::log('SAVE DNS NO');
            }
         } else {
            $model->resetDomainListIfInvalid();
            Yii::log('NO VALIDATE');
         }
      } elseif (isset($_REQUEST['gridaction']['domainlist'])) {
         $domains = array_filter(explode(",", trim($_REQUEST['gridaction']['domainlist'])));
         $domains = array_unique(preg_replace('/^[0-7]_/', '', $domains));
         $model->domainlist = implode(",", $domains);
         if (!$model->isDomainListValid()) {
            $this->redirectToServerSearch('Invalid domain list supplied.');
         }
      } else {
         $this->redirectToServerSearch();
      }
      $this->render('dns/dnsupdate', array('model' => $model));
   }

   private function redirectToServerSearch($message = null) {
      $args = array('nsreports/dnsserversearch');
      if ($message) {
         $args['message'] = $message;
      }
      $this->redirect($args);
   }

   /**
    * Short description for function
    * 
    * Long description (if any) ...
    * 
    * @return string Return description (if any) ...
    * @access public
    */
   public function getExportFilenameTag() {
      return 'DomainReports_';
   }

   /**
    * Short description for function
    * 
    * Long description (if any) ...
    * 
    * @param  array     $theModel Parameter description (if any) ...
    * @return boolean   Return description (if any) ...
    * @access protected
    */

   public function saveDNS($model) {
      $nameserver = array();

      $k = 0;
      for($j=0; $j<$model->nameserversCount; $j++) {
            $nameserver[$j] = new CRSDomainAppService_nameserverVO();
            $nameserver[$j]->name = $model->getNsValue($j);
            Yii::log('TO SAVE= '.print_r($nameserver[$j]->name,true) );
      }

      $hostmasterRemark = 'DNS Update via CRS-WS-API';
      $errors = '';

      CRSCommonAppService_service::modifyNameservers($errors, Yii::app()->user->authenticatedUser, $model->getDomainlistAsArray(), $nameserver, $hostmasterRemark);
      if (count($errors) == 0) {
         $model->message = 'Modify DNS successful';
         return true;
      } else {
         Yii::log('MODIFY NAMESERVER NO' . print_r($errors, true));
//          $errors = split(":", $errors);
//          $message = '';
//          for ($i = 2; $i < sizeof($errors); $i++) {
//              $message = $message . ' :' . $errors[$i];
//          }
         $model->error = 'Modify DNS failure <br>' . str_ireplace("\n", "<br/>", str_ireplace('SOAP Exception : NameserversValidationException : ', '', $errors));
         return false;
      }
      return false;
   }

}

?>
