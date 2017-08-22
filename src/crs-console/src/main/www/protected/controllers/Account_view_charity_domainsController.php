<?php

/**
 * Description of Account_view_charity_domainsController
 *
 * @author Artur Kielak
 */
class Account_view_charity_domainsController extends GridController {
   public function actionMenu() {
      $model = new CharityDomainModel();
      Utility::writeActionToSession('account_view_charity_domains/menu');
      $this->render('menu', array('model' => $model));
   }
}

?>
