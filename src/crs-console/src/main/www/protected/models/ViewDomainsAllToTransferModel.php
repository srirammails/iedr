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
class ViewDomainsAllToTransferModel
	extends GridModelBase
	{

    /**
     * Description for public
     * @var    string
     * @access public
     */
	public $defaultSortColumn = 'A';

    /**
     * Description for var
     * @var    array 
     * @access public
     */
	var $columns = array(
		'A' => array(
			'resultfield'=>'name',
			'criteriafield'=>'domainName',
			'label'=>'Domain Name',
			'width'=>60,
			'link'=>'domains/transfer',
			'type'=>'string',
			),
		'B' => array(
			'resultfield'=>'holder',
			'criteriafield'=>'domainHolder',
			'label'=>'Holder',
			'width'=>60,
			'type'=>'string',
			),
		'C' => array(
			'resultfield'=>'holderClass',
			'criteriafield'=>'holderClass',
			'label'=>'Class',
			'width'=>60,
			'type'=>'string',
			),
		'D' => array(
			'resultfield'=>'holderCategory',
			'criteriafield'=>'holderCategory',
			'label'=>'Category',
			'width'=>50,
			'type'=>'string',
			),
		'E' => array(
			'resultfield'=>'dsmState',
			'criteriafield'=>'dsmState',
			'label'=>'Status',
			'width'=>20,
			'type'=>'string',
			),
		'F' => array(
			'resultfield'=>'renewDate',
			'criteriafield'=>'renewDate',
			'label'=>'Renewal Date',
			'width'=>70,
			'type'=>'datefilter',
			),
		);

    /**
     * Short description for function
     * 
     * Long description (if any) ...
     * 
     * @param  array  $searchparams Parameter description (if any) ...
     * @return object Return description (if any) ...
     * @access public
     */
	public function getSearch($searchparams)
		{
		$criteria = parent::getSearch($searchparams);
		// extend base-class to handle encoded class/category select-list option values
		foreach($searchparams as $k => $r)
			{
			if(is_object($r) and isset($r->field))
				{
				$f = $this->columns[$r->field]['criteriafield'];
				Yii::log('criteriafield='.print_r($f,true), 'debug', 'AllDomainsModel::getSearch()');
				if($f != null)
					if($f=='holderClass') {
						$criteria->$f = encode_class_to_wsapi_str($r->data);
               }
					else if($f=='holderCategory') {
							$criteria->$f = encode_category_to_wsapi_str($r->data);
               } else if($f == 'renewDate'){
                  $s = split(" ",$criteria->$f , 2);
                  $criteria->renewFrom = trim($s[0]);
                  $criteria->renewTo = trim($s[1]);
               } else if($f == 'renDate') {
                  $s = split(" ",$criteria->$f , 2);
                  $criteria->renewalDateFrom = trim($s[0]);
                  $criteria->renewalDateTo = trim($s[1]);
               } else if ($f == 'regDate') {
                  $s = split(" ",$criteria->$f , 2);
                  $criteria->registrationDateFrom = trim($s[0]);
                  $criteria->registrationDateTo = trim($s[1]);
               } else if($f == 'msdDate') {
                  $s = split(" ",$criteria->$f , 2);
                  $criteria->msdDateFrom = trim($s[0]);
                  $criteria->msdDateTo = trim($s[1]);
               } else if($f == 'registrationDate') {
                  $s = split(" ",$criteria->$f , 2);
                  $criteria->registrationFrom = trim($s[0]);
                  $criteria->registrationTo = trim($s[1]);
                } else if($f == 'renewalDate') {
                   $s = split(" ",$criteria->$f , 2);
                   $criteria->renewalFrom = trim($s[0]);
                   $criteria->renewalTo = trim($s[1]);
                }
					/* otherwise leave '$criteria->$f' as was set in parent::getSearch() */
				}
			}
      unset($criteria->accountId);
      $criteria->notAccountId = 1;
		Yii::log('returning '.print_r($criteria,true), 'debug', 'AllDomainsModel::getSearch()');
		return $criteria;
		}

    /**
     * Short description for function
     * 
     * Long description (if any) ...
     * 
     * @param  object $o Parameter description (if any) ...
     * @return mixed  Return description (if any) ...
     * @access public
     */
	public function addResults($o)
		{
      ModelUtility::hasProperty($this->columns, $o);
		return array
			(
			'A'=>$o->name,
			'B'=>cleanString($o->holder),
			'C'=>$o->holderClass,
			'D'=>$o->holderCategory,
			'E'=>$o->dsmState->shortNrpStatus,
			'F'=>parseXmlDate($o->renewDate),
			);
		}
	}

