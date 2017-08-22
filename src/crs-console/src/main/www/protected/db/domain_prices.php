<?php

/**
 * defines domain-pricing information functions
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
 * returns cached domain pricing info array, getting data and filling cache if necessary
 * 
 * @return array pricing data, see {@link _get_reg_prices}
 */
function get_reg_prices() {
   $regprice = array();
   // get data with code
   $regprice['CODE'] = array();
   // get Registration list
   $regprice['REG'] = array();
   // get Renewal list
   $regprice['REN'] = array();
   // defaults
   $regprice['DEFAULT'] = array();

   $result = null;
   $errors = null;
   CRSPaymentAppService_service::getDomainPricing($result, $errors, Yii::app()->user->authenticatedUser);
   if(count($errors) != 0 || $result == null) {
      Yii::log('getDomainPricing errors '.print_r($errors,true));
      return array();
   }
   
   if (!is_array($result)) {
      $result = array(0 => $result);
   }
   
   $vatRate = 0;
   CRSPaymentAppService_service::getVatRate($vatRate, $errors, Yii::app()->user->authenticatedUser);
   if (count($errors) == 0) {
      foreach ($result as $item) {
         $item->vatValue = round($item->price * $vatRate, 2, PHP_ROUND_HALF_EVEN);
         $regprice['CODE'][$item->id] = $item;
         if ($item->forRegistration) {
            $regprice['REG'][$item->id] = $item;
            if ($item->defaultPrice)
               $regprice['DEFAULT']['REG'] = $item->id;
         }
         
         if ($item->forRenewal) {
            $regprice['REN'][$item->id] = $item;
            if ($item->defaultPrice)
               $regprice['DEFAULT']['REN'] = $item->id;
         }
      }
      
      Yii::log('Regprice array ' . print_r($regprice, true));
      return $regprice;
   }
   
   Yii::log('Regprice error = ' . print_r($errors, true));
   return array();
}
