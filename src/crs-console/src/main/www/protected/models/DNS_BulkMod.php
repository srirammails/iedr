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
class DNS_BulkMod extends FormModelBase {

   /**
    * Description for public
    * @var    array 
    * @access public
    */
   public $maxRows;
   public $minRows;
   public $nameserversCount;
   public $labels = array();
   public $rules = array();
   public $nameservers = array();

   /**
    * Description for public
    * @var    array 
    * @access public
    */
   public $message;

   /**
    * Description for public
    * @var    unknown
    * @access public 
    */
   public $domainlist;
   
   public $glueDomains;

   /**
    * Short description for function
    * 
    * Long description (if any) ...
    * 
    * @return array  Return description (if any) ...
    * @access public
    */
   public function __construct() {
      parent::__construct();
      CRSCommonAppService_service::getDomainSettings($result, $errs, Yii::app()->user->authenticatedUser);
      $this->maxRows = $result->maxCount;
      $this->minRows = $result->minCount;
      $this->nameserversCount = $this->minRows;
      $this->clearErrors();
   }

   public function setValues($i, $name) {
      if ($i < 0 || $i >= $this->maxRows)
         return;
      $this->nameservers[$i] = $name;
   }

   public function rules() {
      $array = array_merge(parent::rules(), array(
         array('domainlist', 'isListOfIeDomains'),
         array('domainlist', 'safe'),
         array('domainlist', 'checkGlue'),
         array('nameserversCount', 'required'),
         array('nameservers', 'NameserverListValidator'),
      ));
      $this->rules = $array;
      return $array;
   }

   public function resetDomainListIfInvalid() {
      $errors = $this->getErrors();
      if(isset($errors['domainlist'])){
         $this->domainlist = null;
      }
   }

   public function getNsValue($i) {
      if ($i < 0 || $i >= $this->maxRows)
         return;
      return $this->nameservers[$i];
   }

   public function setDomainList($domainlist) {
      $this->domainlist = $domainlist;
   }

//    /**
//     * Short description for function
//     * 
//     * Long description (if any) ...
//     * 
//     * @param  unknown $event Parameter description (if any) ...
//     * @return void   
//     * @access public 
//     */
   
//    public function onBeforeValidate($event) {
//       $ns = split(",",$this->toSave);
//       $toSet = array();
//       Yii::log('onBeforeValidate= '.print_r($ns,true));
      
//       $toRewriteIp = array();
//       $toRewriteAddress = array();
//       for($i=0;$i<count($ns);$i++) {
//          $toRewriteAddress[] = $this->getNsValue($ns[$i]);
//          $toRewriteIp[] = $this->getIpValue($ns[$i]);
//       }
      
//       for($i=0;$i<count($toRewriteAddress);$i++) {
//          Yii::log($i.' Set values = '.print_r($toRewriteAddress[$i],true). '  '.$toRewriteIp[$i]);
//          $this->setValues($i, $toRewriteAddress[$i], $toRewriteIp[$i]);
//       }
      
//       for ($i = $this->nameserversCount; $i < $this->maxRows; $i++) {
//          $this->setValues($i, 'empty.domain.com', '');
//       } 
//       return true;
//    }

   #Validate Glue records.

   /**
    * Short description for function
    * 
    * Long description (if any) ...
    * 
    * @return boolean Return description (if any) ...
    * @access public 
    */
   public function checkGlue($attrib, $params) {
      $errCount = 0;

//       for ($i = 0; $i < $this->nameserversCount; $i++) {
//          $ns = $this->getNsValue($i);
//          if (!isset($ns)) {
//             $ip = $this->getIpValue($i);
//             if (isset($ip)) {
//                 $errCount++;
//                $this->addError('ip_' . $i, "Glue not needed.");
//             }
//          }
//       }
      $this->glueDomains = array();
      for ($i = 0; $i < $this->nameserversCount; $i++) {
         $ns = $this->getNsValue($i);
         if (isset($ns) && !empty($ns)) {
            $tree = explode('.', $ns);
            $tree = array_reverse($tree);            
            $t = $tree[1];
            $t .= '.';
            $t .= isset($tree[0]) ? $tree[0] : '';
            foreach ($this->getDomainlistAsArray() as $dname) {
               if($t == $dname) {
                  $errCount++;
                  $this->addError('ns_' . $i, $dname." : glue needed for ".$ns);
                  $this->glueDomains[] = $dname;
                  break;
               }
            }
         }
      }

      if ($errCount) {
         return false;
      }
      return true;
   }

   public function isListOfIeDomains($attrib, $params) {
      $domainList = $this->$attrib;
      $errors = array();
      Utility::parseInput($domainList, $errors);
      if (!empty($errors)) {
         $this->addError($attrib, 'Invalid domain list.');
         return false;
      }
      return true;
   }

   public function isDomainListValid() {
      return $this->isListOfIeDomains('domainlist', null);
   }
   
   public function getDomainlistAsArray() {
   		return split(",", $this->domainlist);
   }
}


