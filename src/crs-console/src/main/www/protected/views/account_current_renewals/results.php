<!-- START protected/views/accounts_msd/results.php -->
<?php
/**
 * View page for Accounts MSD action results
 * 
 * @category  NRC
 * @package   IEDR_New_Registrars_Console
 * @author    IEDR <asd@iedr.ie>
 * @copyright 2011 IEDR
 * @license   http://www.iedr.ie/ (C) IEDR 2011
 * @version   CVS: $Id:$
 * @link      https://statistics.iedr.ie/
 * @see       Accounts_msdController::showConfirmPage(), Accounts_msdController::actionConfirm(), MSDSelectionModel
 */
$pg = 'Current Renewals';
$this->pageTitle = Yii::app()->name . ' - ' . $pg;
?>

<h2><?php printBreadcrumbTrail($pg); ?></h2>
<div class="form">
   <?php
      $errors = $model->getErrors('domainlist');
      $returnUrl = 'Card declined. ' . CHtml::link("Click here to try again/another card.", $model->returnurl);
      if ($errors != null) {
         echo WSAPIError::getErrorsNotEmpty($errors, $returnUrl);
      } else {
         Utility::renderResult($model);
         echo '<form><input type=\'button\' value=\'Print This Page\' onClick=\'window.print()\' /></form>';
      }
   ?>
</div>
<br>
   <?php echo AuthOnlyBaseController::returnToUrlButton($model->returnurl); ?>
