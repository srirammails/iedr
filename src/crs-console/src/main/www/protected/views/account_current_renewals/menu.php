<?php

$pg = 'Current Renewals';
$this->pageTitle = Yii::app()->name . ' - ' . $pg;
?>
<h2><?php printBreadcrumbTrail($pg); ?></h2>
<?php

$model_serialised = AuthOnlyBaseController::safeSerializeObj($model);

$commandlist = array();
if (!Utility::isDirect()) {
    if ($model->countDays == 'OVERDUE') {
        $commandlist = array(
            'payonline' => 'Pay %n Domains Online (€ %fee)',
            'paydeposit' => 'Pay %n Domains From Deposit (€ %fee)',
            'voluntary' => 'Put %n Domains into Voluntary NRP',
        );
    } else {
        $commandlist = array(
            'payonline' => 'Pay %n Domains Online (€ %fee)',
            'paydeposit' => 'Pay %n Domains From Deposit (€ %fee)',
            'voluntary' => 'Put %n Domains into Voluntary NRP',
            'autorenew' => 'Set %n Domains Autorenew',
        );
    }

} else {
    $commandlist = array(
        'payonline' => 'Pay %n Domains Online (€ %fee)',
        'voluntary' => 'Put %n Domains into Voluntary NRP',
    );
}

GridUtility::makeCurrenRenewalsOption($this, $pg, 'account_current_renewals', $model, 'date');


$this->widget('application.widgets.jqGrid.jqGridComandButtonsWidget', array(
    'pk_column_index' => 0,
    'actionurl' => $this->createUrl('account_current_renewals/confirm'),
    'returnurl' => $this->createUrl('account_current_renewals/menu'),
    'formid' => 'gridactionpay',
    'commandlist' => $commandlist,
));

echo '<br/>';

$this->widget('application.widgets.jqGrid.jqGridWidget', array(
    'caption' => $pg . ' for \'' . Yii::app()->user->name . '\'',
    'thisfile' => $this->createUrl('account_current_renewals/menu', array('model' => $model_serialised)),
    'datasource' => $this->createUrl('account_current_renewals/getgriddatacurrrnr', array('model' => $model_serialised)),
    'model' => $model,
    'searching' => true,
    'sorting' => true,
    'selections' => true,
));
?>
<!-- END protected/views/accounts_renewpay_advpay/current_newandrenewals.php -->
