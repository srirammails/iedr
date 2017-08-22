<!-- START protected/views/grid/results.php -->
<?php
/**
 * View page for Grid Domain-Operation Results
 * 
 * @category  NRC
 * @package   IEDR_New_Registrars_Console
 * @author    IEDR <asd@iedr.ie>
 * @copyright 2011 IEDR
 * @license   http://www.iedr.ie/ (C) IEDR 2011
 * @version   CVS: $Id:$
 * @link      https://statistics.iedr.ie/
 * @see       GridController, GridSelectionModel
 */

$pg = 'Confirmation';
$this->pageTitle=Yii::app()->name . ' - '.$pg;
Yii::log('RESUL IN GRID');
?>
<h2><?php echo printBreadcrumbTrail($pg); ?></h2>
<div class="form">
   
<?php

$errs = $model->getErrors('domainlist');
if($errs != null) {
      echo WSAPIError::getErrorsNotEmpty($errs);
} else {
    $confirmationString = '';
    if($model->command == 'revert') {
         $confirmationString = 'Domain(s) have been reverted to billable and will be included in the next billing cycle.';
         echo "<h4>$confirmationString</h4>";
         echo '<table class="gridtable">';
         switch($model->command) {
            default:
               echo '<tr><td class="gridtablecell"><strong>Domains</strong></td>'.
                  '<td class="gridtablecell"><strong>Result</strong></td></tr><tr><td class="gridtablecell">';
               foreach($model->domainListToArray() as $d) {
                  echo $d.'<br>';
               }
               echo '</td><td class="gridtablecell">OK</td>';
               break;
          }
         echo '</table>';
    } else if($model->command == 'autorenew' || $model->command == 'noautorenew') {
      echo "<table class='gridtable'>";
      echo "<tr><td class='gridtablecell'><strong>Domain</strong></td>";
      echo "<td class='gridtablecell'><strong>Renew mode</strong></td></tr>";

      $value = $model->commandresults[$model->command];
      if(is_array($value)) {
         for($i=0;$i < count($value);$i++) {
            echo "<tr><td class='gridtablecell'>".$value[$i]->name."</td>";
            echo "<td class='gridtablecell'>".$value[$i]->dsmState->renewalMode."</td></tr>";
         }
      } else if(is_object($value)) {
         echo "<tr><td class='gridtablecell'>".$value->name."</td>";
         echo "<td class='gridtablecell'>".$value->dsmState->renewalMode."</td></tr>";
      }
      echo '</table>';
    } else if($model->command == 'authcodedownload') {
      echo "<table class='gridtable'>";
      echo "<tr><td class='gridtablecell'><strong>Domain</strong></td>";
      echo "<td class='gridtablecell'><strong>Result</strong></td></tr>";

      $value = $model->commandresults[$model->command];
      $message = $model->commandresults['result'];
      if(is_array($value)) {
         for($i=0;$i < count($value);$i++) {
            echo "<tr><td class='gridtablecell'>".$value[$i]."</td>";
            echo "<td class='gridtablecell'>".$message[$i]."</td></tr>";
         }
      } else {
         echo "<tr><td class='gridtablecell'>".$value."</td>";
         echo "<td class='gridtablecell'>".$message."</td></tr>";
      }
      echo '</table>';
      $form = $this->beginWidget('CActiveForm', array('id' => 'CSVAction'));
      echo $form->hiddenField($model, 'domainlist');
      for ($i = 0; $i < count($model->commandresults['exportData']); $i++) {
         echo $form->hiddenField($model, "commandresults[exportData]"."[$i]"."[A]");
         echo $form->hiddenField($model, "commandresults[exportData]"."[$i]"."[B]");
         echo $form->hiddenField($model, "commandresults[exportData]"."[$i]"."[C]");
      }
      echo '<div class="row buttons">' . CHtml::submitButton('Download CSV file', array('name' => 'CSVFile')) . '</div><br>';
      $this->endWidget();
    }
}
?>
   
</div>
<br>
<?php echo AuthOnlyBaseController::returnToUrlButton($model->returnurl); ?>

<!-- END protected/views/grid/results.php -->
