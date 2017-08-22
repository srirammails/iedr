<?php
$pg = 'Pay to Renew Domains';
$this->pageTitle=Yii::app()->name . ' - '.$pg;
?>
<h2><?php echo printBreadcrumbTrail($pg); ?></h2>
<?php printMenuHeirarchy(getMenu(),$pg); ?>