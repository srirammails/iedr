<?php
/**
 * Description of NoBillableDomainModel
 *
 * @author Artur Kielak
 */

class NoBillableDomainModel extends AllDomainsModel {
   public function getSearchBase() {
      $criteria = parent::getSearchBase();
      $criteria->holderTypes = CRSDomainAppService_domainHolderType::_NonBillable;
      return $criteria;
   }

}


?>
