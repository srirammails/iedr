<!-- START protected/views/accountsgrid/results.php -->
<?php
/**
 * View page for Accounts operation results
 * 
 * @category  NRC
 * @package   IEDR_New_Registrars_Console
 * @author    IEDR <asd@iedr.ie>
 * @copyright 2011 IEDR
 * @license   http://www.iedr.ie/ (C) IEDR 2011
 * @version   CVS: $Id:$
 * @link      https://statistics.iedr.ie/
 * @see       GridController, AccountsGridController, GridSelectionModel
 */
$pg = 'Confirmation';
$this->pageTitle = Yii::app()->name . ' - ' . $pg;
Yii::log('RESUL IN ACCCOUNT GRID');
?>

<h2><?php printBreadcrumbTrail($pg); ?></h2>
<div class="form">
   <?php
   
   $errors = $model->getErrors('domainlist');
   $returnUrl = 'Card declined. ' . CHtml::link("Click here to try again/another card.", $model->returnurl);
   if ($errors != null) {
      echo WSAPIError::getErrorsNotEmpty($errors, $returnUrl);
   }
   else {
      Utility::renderResult($model);
      echo '<form><input type=\'button\' value=\'Print This Page\' onClick=\'window.print()\' /></form>';
   }

   ?>
</div>
<br>
   <?php echo AuthOnlyBaseController::returnToUrlButton($model->returnurl); ?>
<!-- END protected/views/accountsgrid/results.php -->
