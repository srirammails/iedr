<!-- START protected/views/domainreports/menu.php -->
<?php
/**
 * View page for Domain Reports Menu
 * 
 * @category  NRC
 * @package   IEDR_New_Registrars_Console
 * @author    IEDR <asd@iedr.ie>
 * @copyright 2011 IEDR
 * @license   http://www.iedr.ie/ (C) IEDR 2011
 * @version   CVS: $Id:$
 * @link      https://statistics.iedr.ie/
 * @see       DomainreportsController::actionMenu()
 */

$pg = 'Reports';
$this->pageTitle=Yii::app()->name . ' - '.$pg;
?>
<h2><?php echo printBreadcrumbTrail($pg); ?></h2>
<?php printMenuHeirarchy(getMenu(),$pg); ?>

<!-- END protected/views/domainreports/menu.php -->
