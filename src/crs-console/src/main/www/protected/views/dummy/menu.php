<!-- START protected/views/dummy/menu.php -->
<?php
/**
 * View page for Generic Menu
 * 
 * @category  NRC
 * @package   IEDR_New_Registrars_Console
 * @author    IEDR <asd@iedr.ie>
 * @copyright 2011 IEDR
 * @license   http://www.iedr.ie/ (C) IEDR 2011
 * @version   CVS: $Id:$
 * @link      https://statistics.iedr.ie/
 */

$pg = $this->breadcrumbs[0];
$this->pageTitle=Yii::app()->name . ' - '.$pg;
?>
<h2><?php echo printBreadcrumbTrail($pg); ?></h2>
<?php printMenuHeirarchy(getMenu(),$pg); ?>
<!-- END protected/views/dummy/menu.php -->
