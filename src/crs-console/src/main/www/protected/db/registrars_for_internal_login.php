<?php

/**
 * defines functions for getting ilst of registrars to login-as, which are not handled by the NASK CRS-WS-API
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
 * gets cached list of registrars, if uncached, gets data and fills cache, see {@link _get_registrars_for_login}
 * 
 * @return array registrars
 */
function getRegistrarsForLogin() {
   $registrars = array();
   $result = null;
   $errs = array();
   $user = Yii::app()->user->authenticatedUser;
   CRSResellerAppService_service::getRegistrarsForLogin($result, $errs, $user);
   if ($result != null) {
      if (count($errs) == 0) {
         if (is_array($result)) {
            foreach ($result as $k) {
               $registrars[$k->billingNH] = $k->accountName;
            }
         } else if (is_object($result)) {
               $registrars[$result->billingNH] = $result->accountName;
         }
      } else {
         Yii::log('GET REGISTRARS ERRORS ' . print_r($errs, true));
      }
   } else {
      Yii::log('GET REGISTRARS NULL RESULT ' . print_r($errs, true));
   }

   Yii::log('GET REGISTRARS '.print_r($registrars, true));
   return $registrars;
}

