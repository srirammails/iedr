<?php

/**
 * defines ViewGridModelBase class
 * 
 * @category  NRC
 * @package   IEDR_New_Registrars_Console
 * @author    IEDR <asd@iedr.ie>
 * @copyright 2011 IEDR
 * @license   http://www.iedr.ie/ (C) IEDR 2011
 * @version   CVS: $Id:$
 * @link      https://statistics.iedr.ie/
 */

/**
 * Base class for Data Models for actions using SQL Queries
 * 
 * @category  NRC
 * @package   IEDR_New_Registrars_Console
 * @author    IEDR <asd@iedr.ie>
 * @copyright 2011 IEDR
 * @license   http://www.iedr.ie/ (C) IEDR 2011
 * @version   Release: @package_version@
 * @link      https://statistics.iedr.ie/
 */
class ViewGridModelBase extends GridModelBase {
   /**
    * returns base search criteria shared by derived-class queries
    * 
    * @return CDbCriteria search criteria
    * @access public
    */
   /**
    * return current request's search-filter parameters as a SQL Where-clause fragment
    * 
    * @param  array  $searchparams array of table-column => data-value
    * @return string SQL Where-clause fragment
    * @access public
    */

   /**
    * returns validation rules
    * 
    * @return array  validation rules array
    * @access public
    */
   public function rules() {
      return array(
          array('days', 'required'),
          array('days', 'length', 'min' => 1, 'max' => 4),
          array('days', 'numerical', 'min' => 1, 'max' => 9999, 'integerOnly' => true),
          array('month', 'required'),
          array('year', 'required'),
      );
   }

   /**
    * labels for view fields
    * 
    * @return array  array for field-name => field-label string values
    * @access public
    */
   public function attributeLabels() {
      return array(
          'days' => 'Days',
      );
   }

   /**
    * convert an object into an array, with attributes renamed
    * 
    * @param  array  $o result object returned by backend system by controller function, to be added as a row to controller's getData return array
    * @return array  result object converted into array with keys being result-obj-members renamed as per $this->column[]->resultfield
    * @access public
    */
   public function addResults($o) {
      $a = array();
      foreach ($this->columns as $k => $data)
         $a[$k] = $o[$data['resultfield']];
      return $a;
   }

}

