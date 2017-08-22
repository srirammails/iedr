<?php

/**
 * defined ReturningModelBase class
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
 * base class for models which need to store data relating to a page which can be returned to.
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
class ReturningModelBase
	extends FormModelBase
	{

    /**
     * array of names of data-fields to be returned
     * @var    array
     * @access public 
     */
	public $returningDataList;


    /**
     * array of data to be returned
     * @var    array 
     * @access public
     */
	public $returningData;


    /**
     * constructor, sets up data structures for returning
     * 
     * calls {@link getReturningDataNames} to get a list of field names to set up in the {@link returningData} array, then updates the model data from the POST data with {@link updateFromPOST}
     * 
     * @return void  
     * @access public
     */
	function __construct() {
		$this->returningData = array();
		$na = $this->getReturningDataNames();
		if($na and is_array($na))
			foreach($na as $n)
				$this->returningData[$n] = null;
		$this->updateFromPOST();
		}

    /**
     * updates {@link returningData} array with data found in the passed array
     * 
     * @param  array     $arr array to copy data from, having the same keys as {@link returningData}
     * @return boolean   whether any data was found in the passed array
     * @access protected
     */
	protected function updateFromArray($arr)
		{
		$found = false;
		foreach($this->getReturningDataNames() as $k => $v)
			if(isset($arr[$v])) {
				$this->returningData[$v] = $arr[$v];
				$found = true;
				}
		return $found;
		}

    /**
     * calls {@link updateFromArray} with data from the passed array, using the array's data member named 'returningData' if it exists
     * 
     * @param  array     $arr array containing an array whose key is 'returningData'
     * @return mixed     whether data was found
     * @access protected
     */
	protected function updateFromBaseArray($arr)
		{
		if(isset($arr['returningData']) and is_array($arr['returningData']))
			return $this->updateFromArray($arr['returningData']);
		return false;
		}

    /**
     * tries three ways to fill model data with data from POST variables
     * 
     * calls {@link updateFromArray}; if no data found, calls {@link updateFromBaseArray}; if no data foundm then
     * iterates over POST data and tries each data member as a parameter to {@link updateFromBaseArray}, stopping
     * if any data are found.
     * 
     * @return void     
     * @access protected
     */
	protected function updateFromPOST()
		{
		// add data to model for return to originating form
		if(!$this->updateFromArray($_POST)) {
			if(!$this->updateFromBaseArray($_POST))
				foreach($_POST as $pk => $pv)
					if($this->updateFromBaseArray($pv))
						break;
			}
		#Yii::log('POST="'.print_r($_POST,true), 'error', 'ReturningModelBase::updateFromPOST()');
		#Yii::log('ReturningModelBase="'.print_r($this,true), 'debug', 'ReturningModelBase::updateFromPOST()');
		}

    /**
     * returns an array of data member names to be copied from inout POST data
     * 
     * override in a child class to return a list of data items top be saved for return
     * 
     * @return array     array of data member names
     * @access protected
     */
	protected function getReturningDataNames()
		{
		return array('formdata','returnurl');
		}
	}

