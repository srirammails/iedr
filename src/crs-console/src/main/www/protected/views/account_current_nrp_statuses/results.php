<?php
$pg = 'Current NRP Statuses Results';
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
    }
    if ($model->command != 'removefromvoluntary') {
        echo '<form><input type=\'button\' value=\'Print This Page\' onClick=\'window.print()\' /></form>';
    }
    ?>
</div>
<br>
   <?php echo AuthOnlyBaseController::returnToUrlButton($model->returnurl); ?>