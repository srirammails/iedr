<?php

/**
 * defines functions for classes and categories, which are not handled by the NASK CRS-WS-API
 * 
 * @category  NRC
 * @package   IEDR_New_Registrars_Console
 * @author    IEDR <asd@iedr.ie>
 * @copyright 2011 IEDR
 * @license   http://www.iedr.ie/ (C) IEDR 2011
 * @version   CVS: $Id:$
 * @link      https://statistics.iedr.ie/
 */



function getClasses() {
   $result = null;
   $errors = array();
   $returnArray = array();
   CRSTicketAppService_service::getClasses($result, $errors, Yii::app()->user->authenticatedUser);
   if($result != null) {
      if(count($errors) == 0) {
         for($i = 0 ;$i<count($result);$i++) {
            //To remove when internal users will be done
            if($result[$i]->name != 'Charity')
               $returnArray[$result[$i]->name] = $result[$i]->name;
         }
         return $returnArray;
      } else {
         Yii::log('getClasses() errors '.print_r($errors,true));
      }
   } else {
      Yii::log('getClasses() result is null');
   }
}


function getCategoryForClass($classId) {
   $result = null;
   $returnArray = array();
   $errors = array();
   CRSTicketAppService_service::getCategoriesForClass($result, $errors, Yii::app()->user->authenticatedUser, $classId);
   if($result != null) {
      if(count($errors) == 0) {
         for($i = 0 ;$i<count($result);$i++) {
         Yii::log('results class = '.print_r($result[$i]->name,true));
            $returnArray[$result[$i]->name] = $result[$i]->name;
         }
         return $returnArray;
      } else {
         Yii::log('getCategoryForClass() errors '.print_r($errors,true));
      }
   } else {
      Yii::log('getCategoryForClass() result is null');
   }
}

/**
 * gets class and category array, or gets data and fills cache if uncached
 * 
 * returns just the 'classcat' part of the data returned by {@link get_full_class_category}.
 * 
 * @return array Keys are Class names, values are arrays with key strings like 'Class:Category' and values of category strings
 */
function get_class_category()
	{
	$s = get_full_class_category();
	return $s['classcat'];
	}

/**
 * gets class and category array, or gets data and fills cache if uncached
 * 
 * @return array as returned by {@link _get_full_class_category}
 */
   function get_full_class_category() {
      return _get_full_class_category();
	}
/**
 * gets class and category arrays
 * 
 * returned array contains three subarrays:
 * 
 * - 'classcat' : keys are Class names, values are arrays with key strings like 'Class:Category' and values of category strings
 * 
 * - 'class' : keys are class-ID, values are class names
 * 
 * - 'category' : keys are category-ID, values are category names
 * 
 * 
 * @return array (for array structure, see above)
 */
function _get_full_class_category() {
	$class_category = array();
   CRSDomainAppService_service::getClassCategory($result, $errs,  Yii::app()->user->authenticatedUser);
   if($result != null) {
      if(count($errs) == 0){
         if(is_array($result)) {
            for($i=0;$i<count($result);$i++) {
               $class_category['classcat'][$result[$i]->className][$result[$i]->className .':'. $result[$i]->categoryName] = $result[$i]->categoryName;
               $class_category['class'][$result[$i]->classId] = $result[$i]->className;
               $class_category['category'][$result[$i]->classId] = $result[$i]->categoryName;
            }
         } else if(is_object($result)) {
            $class_category['classcat'][$result->className][$result->className .':'. $result->categoryName] = $result->categoryName;
            $class_category['class'][$result->classId] = $result->className;
            $class_category['category'][$result->classId] = $result->categoryName;
         }
      } else {
         Yii::log('Get class category errors = '.print_r($errs,true));
      }
   } else {
      Yii::log('Get class category return is null');
   }
   return $class_category;
	}

/**
 * returns a class-name string as found in the database, matching the passed parameter case-insensitively
 * 
 * @param  string $c class name
 * @return string database version of class name
 */
function encode_class_to_wsapi_str($c)
	{
	// convert console display /class/ to XML-API string
	$s = null;
	$d = get_full_class_category();
	$dd = $d['class'];
	if(is_array($dd))
		{
		foreach($dd as $id => $cls)
			if(preg_match('/.*'.$c.'.*/i',$cls)!==0)
				{ $s = $dd[$id]; break; }
		Yii::log('encode_class_to_wsapi_str('.$c.')=>'.$s, 'info', '');
         }
	else
		{
		Yii::log('encode_class_to_wsapi_str() no array in'.print_r($d,true), 'ERROR', '');
	}
	return $s;
	}

/**
 * returns a category-name string as found in the database, matching the passed parameter case-insensitively
 * 
 * @param  string $c category name
 * @return string database version of category name
 */
function encode_category_to_wsapi_str($c)
	{
	// convert console display /category/ to XML-API string
	$s = null;
	$d = get_full_class_category();
	$dd = $d['category'];
	if(is_array($dd))
		{
		foreach($dd as $id => $cat)
			if(preg_match('/.*'.$c.'.*/i',$cat)!==0)
				{ $s = $dd[$id]; break; }
		Yii::log('encode_category_to_wsapi_str('.$c.')=>'.$s, 'info', '');
		}
	else
		{
		Yii::log('encode_category_to_wsapi_str() no array in'.print_r($d,true), 'ERROR', '');
		}

	return $s;
	}

