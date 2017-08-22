<!-- START protected/views/nsreports/dnsserversearch.php -->
<?php
/**
 * View page for DNS Server Search
 * 
 * @category  NRC
 * @package   IEDR_New_Registrars_Console
 * @author    IEDR <asd@iedr.ie>
 * @copyright 2011 IEDR
 * @license   http://www.iedr.ie/ (C) IEDR 2011
 * @version   CVS: $Id:$
 * @link      https://statistics.iedr.ie/
 * @see       DnsserversearchModel, NsreportsController::actionDnsserversearch(), jqGridComandButtonsWidget, jqGridWidget
 */

$pg = 'DNS';
$this->pageTitle=Yii::app()->name . ' - '.$pg;
?>
<h2><?php echo printBreadcrumbTrail($pg); ?></h2>
<?php

if (isset($_REQUEST['message'])) {
   Utility::printFlashError(htmlentities($_REQUEST['message']));
}

echo "<p>To modify a domain's DNS details use the grid to search and select domain(s) and then click on the DNS modification button.</p>";

$model_serialised = AuthOnlyBaseController::safeSerializeObj($model);

$this->widget('application.widgets.jqGrid.jqGridComandButtonsWidget', array(
	'pk_column_index'	=> 1,
	'actionurl'		=> $this->createUrl('dns/dnsupdate'),
	'returnurl'		=> $this->createUrl('nsreports/dnsserversearch'),
	'commandlist'		=> array(
					'dnsMod'  => 'Modify DNS for selected Domains',
					),
	));

echo '<br/>';


$w = $this->widget('application.widgets.jqGrid.jqGridWidget', array(
	'caption'	=> 'All Nameservers registered with \''.Yii::app()->user->name.'\'',
	'thisfile'	=> $this->createUrl('nsreports/dnsserversearch',array('model'=>$model_serialised)),
	'datasource'	=> $this->createUrl('nsreports/getgriddataNrc_ns_report',array('model'=>$model_serialised)),
	'model'		=> $model,
	'selections'	=> true,
	));
?>
<!-- END protected/views/nsreports/dnsserversearch.php -->
