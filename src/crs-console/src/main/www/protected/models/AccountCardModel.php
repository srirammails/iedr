<?php
/**
 * Description of AccountCardModel
 *
 * @author Artur Kielak
 */

class AccountCardModel extends GridModelBase {
    public $columns = array(
       'A' => array(
           'label' => 'Date',
           'width' => 30,
           'resultfield' => 'settlementDate',
           'criteriafield' => 'settlementDate',
       ),
       'PK' => array(
           'label' => 'Trans id',
           'resultfield' => 'id',
           'criteriafield' => 'id',
           'width' => 20,
       ),
       'B' => array(
           'label' => 'Trans operation',
           'resultfield' => 'operationType',
           'criteriafield' => 'operationType',
           'width' => 20,
           'type' => null,
       ),
       'C' => array(
           'label' => 'Trans totalCost',
           'resultfield' => 'totalCost',
           'criteriafield' => 'totalCost',
           'width' => 20,
           'type' => null,
       ),
   );
   
   public function addResults($o) {
      ModelUtility::hasProperty($this->columns, $o);
		return array(
          'A'=> isset($o->settlementDate) ? parseXmlDate($o->settlementDate) : ' ',
          'PK'=>isset($o->id) ? $o->id : ' ', 
          'B'=>isset($o->operationType) ? $o->operationType : ' ',
          'C'=>isset($o->totalCost) ? $o->totalCost : ' ',
      );
	}
}

?>
