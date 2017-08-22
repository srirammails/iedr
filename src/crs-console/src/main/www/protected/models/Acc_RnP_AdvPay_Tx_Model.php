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
class Acc_RnP_AdvPay_Tx_Model
	extends AccountAdvanceGridModel
	{

          public $columns = array(
             'PK' => array(
                 'label'=>'Domain',        
                 'width'=>60,
                 'link'=>'domains/viewdomain',
                 'resultfield' => 'domain',
                 'criteriafield' => 'domainName',
               ),
             'H' => array(
                 'label' => 'Holder',
                 'resultfield' => 'holder',
                 'criteriafield' => null,
                 'width' => 20,
                 'type' => null,
              ),
             'I' => array(
                 'label' => 'Renewal',
                 'resultfield' => 'renewDate',
                 'criteriafield' => null,
                 'width' => 23,
                 'type' => null,
              ),
             'J' => array(
                 'label' => 'Amount',
                 'resultfield' => 'amount',
                 'criteriafield' => null,
                 'width' => 23,
                 'type' => 'currency',
             ),
              'K' => array(
                 'label' => 'Vat',
                 'resultfield' => 'vat',
                 'criteriafield' => null,
                 'width' => 23,
                 'type' => 'currency',
              ),
             'L' => array(
                 'label'=>'Select',
                 'width'=>20,
                 'resultfield' => 'renewDate',
                 'criteriafield' => null,
                 'type'=>'checkbox',
                 ),
             );
   
       /**
        * Short description for function
        * 
        * Long description (if any) ...
        * 
        * @param  unknown $obj Parameter description (if any) ...
        * @return void   
        * @access public 
        */
      public function __construct($obj) { 
         parent::__construct($obj);
      }
      
      
      public function addResults($o)
		{
         ModelUtility::hasProperty($this->columns, $o);
         return array
            (
            'PK'=>$o->domainName,
            'H'=>cleanString($o->holderId),
            'I'=>parseXmlDate($o->renDate),
            'J'=>number_format($o->amount,2),
            'K'=>number_format($o->vat,2),
            'L'=>0,
            );
		}
      
	}

