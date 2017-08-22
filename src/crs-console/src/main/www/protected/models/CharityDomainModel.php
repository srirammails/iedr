<?php

/**
 * defines CharityDomainModel class
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
class CharityDomainModel extends AllDomainsModel {

   /**
    * Short description for function
    * 
    * Long description (if any) ...
    * 
    * @return object Return description (if any) ...
    * @access public
    */
   public function getSearchBase() {
      $criteria = parent::getSearchBase();
      $criteria->holderTypes = CRSDomainAppService_domainHolderType::_Charity;
      return $criteria;
   }

}

