<?php

/**
 * Description of ReauthorizePayModel
 *
 * @author Artur Kielak
 */
class ReauthorizeCCTransactionPayModel extends DynamicModelBase {
   public function __construct() {
      parent::__construct();
      $this->addMixinModel(new CreditCardFormSegmentModel());
   }
}

?>
