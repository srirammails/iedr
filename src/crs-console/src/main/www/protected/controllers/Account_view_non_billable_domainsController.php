<?php
/**
 * Description of Account_view_none_billable_domainsController
 *
 * @author Artur Kielak
 */
class Account_view_non_billable_domainsController extends GridController {
   public function actionMenu() {
      $model = new NoBillableDomainModel();
      Utility::writeActionToSession('account_view_non_billable_domains/menu');
      $this->render('menu', array('model' => $model));
   }
}

?>
