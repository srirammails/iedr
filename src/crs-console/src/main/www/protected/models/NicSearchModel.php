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
class NicSearchModel
	extends GridModelBase
	{

    /**
     * Description for var
     * @var    array 
     * @access public
     */
	var $columns;

    /**
     * Description for var
     * @var    unknown
     * @access public 
     */
	var $countryList, $countyList;

    /**
     * Description for public
     * @var    string
     * @access public
     */
	public $defaultSortColumn = 'A';
   public $fromOthers = '0';
   
    /**
     * Short description for function
     * 
     * Long description (if any) ...
     * 
     * @return void  
     * @access public
     */
	public function __construct($from = '0')
		{
      $this->fromOthers = $from;
		parent::__construct();
      
// BUG request too large !!!

		//$this->countryList = getCountryOptions();
		//$this->countyList = getCounties();
  
		$this->columns = array(
			'A' => array(
				'resultfield'=>'nicHandleId',
				'criteriafield'=>'nicHandleId',
				'label'=>'NicHandle',
				'width'=>70,
				'link'=>($from == '0')?('nichandles/viewnichandle'):null,
				'type'=>'string',
				),
			'B' => array(
				'resultfield'=>'name',
				'criteriafield'=>'name',
				'label'=>'Name',
				'width'=>90,
				'type'=>'string',
				),
			'C' => array(
				'resultfield'=>'companyName',
				'criteriafield'=>'companyName',
				'label'=>'Company',
				'width'=>90,
				'type'=>'string',
				),
			'D' => array(
				'resultfield'=>'address',
				'criteriafield'=>'address',
				'label'=>'Address',
				'width'=>160,
				'type'=>'string',
				),
			'E' => array(
				'resultfield'=>'county',
				'criteriafield'=>'county',
				'label'=>'County',
				'width'=>60,
				'type'=>'string',
				),
			'F' => array(
				'resultfield'=>'country',
				'criteriafield'=>'country',
				'label'=>'Country',
				'width'=>40,
				'type'=>'string',
				),
			'G' => array(
				'resultfield'=>'email',
				'criteriafield'=>'email',
				'label'=>'Email',
				'width'=>100,
				'type'=>'email',
				),
			'H' => array(
				'resultfield'=>'phones',
				'criteriafield'=>'phone',
				'label'=>'Phone',
				'width'=>100,
				'type'=>'string',
				)
			);
      
      
		}

    /**
     * Short description for function
     * 
     * Long description (if any) ...
     * 
     * @param  array  $o Parameter description (if any) ...
     * @return mixed  Return description (if any) ...
     * @access public
     */
      public function addResults($o) {
         ModelUtility::hasProperty($this->columns, $o);
         return array
			(
			'A'=>encode($o->nicHandleId),
			'B'=>encode($o->name),
			'C'=>encode($o->companyName),
			'D'=>$this->replaceLineBreaksWithCommas(encode($o->address)),
			'E'=>encode($o->county),
			'F'=>encode($o->country),
			'G'=>encode($o->email),
			'H'=> isset($o->phones) ? encode($o->phones) : ''
			);
		}
      
    /**
     * Short description for function
     * 
     * Long description (if any) ...
     * 
     * @param  unknown $s Parameter description (if any) ...
     * @return mixed   Return description (if any) ...
     * @access public 
     */
	public function replaceLineBreaksWithCommas($s)
		{
		return preg_replace(array("/\r/","/\n/",'/&#10;/','/&#13;/','/[%,]+/','/,[, ]+/'),',',$s);
		}

    /**
     * Short description for function
     * 
     * Long description (if any) ...
     * 
     * @param  array  $searchparams Parameter description (if any) ...
     * @return string Return description (if any) ...
     * @access public
     */
      
      
	public function getSearch($searchparams) {
		$criteria = "";
		foreach($searchparams as $key => $value) {
			$field = $this->columns[$value->field]['criteriafield'];
			if($field != null)
            Yii::log('CRIETRIAS IN NICHANDLE SEARCH= '.print_r($field,true));
            switch($field) {
               case 'nicHandleId':
                  $criteria->$field = $value->data;
                  break;
               case 'name':
                  $criteria->$field = $value->data;
                  break;
               case 'companyName':
                  $criteria->$field = $value->data;
                  break;
               case 'contact':
                  $criteria->$field = $value->data;
                  break;
               case 'domainName':
                  $criteria->$field = $value->data;
                  break;
               case 'email':
                  $criteria->$field = $value->data;
                  break;
               case 'address':
                  $criteria->$field = $value->data;
                  break;
               case 'phone':
                  $criteria->$field = $value->data;
                  break;
               case 'county':
                  $criteria->$field = $value->data;
                  break;
               case 'country':
                  $criteria->$field = $value->data;
                  break;
            }
			}
         Yii::log('RETURN CRITERIA= '.print_r($criteria, true));
         return $criteria;
		}

    /**
     * Short description for function
     * 
     * Long description (if any) ...
     * 
     * @return mixed  Return description (if any) ...
     * @access public
     */
	function getReturningDataNames()
		{
		return array_merge(parent::getReturningDataNames(),array('mode','nictype','nic'));
		}
	}

