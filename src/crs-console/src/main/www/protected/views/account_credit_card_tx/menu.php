<?php
$pg = 'Credit Card Tx';
$this->pageTitle=Yii::app()->name . ' - '.$pg;
?>
<h2><?php echo printBreadcrumbTrail($pg); ?></h2>
<?php printMenuHeirarchy(getMenu(),$pg); ?>