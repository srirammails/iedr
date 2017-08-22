<!-- START protected/views/domainreports/alldomains.php -->
<?php
/**
 * View page for Domain Reports - All Domains
 * 
 * @category  NRC
 * @package   IEDR_New_Registrars_Console
 * @author    IEDR <asd@iedr.ie>
 * @copyright 2011 IEDR
 * @license   http://www.iedr.ie/ (C) IEDR 2011
 * @version   CVS: $Id:$
 * @link      https://statistics.iedr.ie/
 * @see       AllDomainsModel, DomainreportsController::actionAlldomains(), jqGridWidget
 */

$pg = 'All Domains';
$this->pageTitle=Yii::app()->name . ' - '.$pg;
?>
<h2><?php echo printBreadcrumbTrail($pg); ?></h2>
<?php
$model_serialised = AuthOnlyBaseController::safeSerializeObj($model);
$level = $_SESSION['lastLevel'];
Yii::log('USER LEVEL: '.print_r($level,true));

if ($level == 2 || $level == 3) {
    $this->widget('application.widgets.jqGrid.jqGridComandButtonsWidget', array(
        'pk_column_index'	=> 0,
        'actionurl'		=> $this->createUrl('domainreports/confirm'),
        'returnurl'		=> $this->createUrl('domainreports/alldomains'),
        'formid'		=> 'gridaction',
        'commandlist'		=> array(
            'autorenew' => 'Set %n Domains Autorenew',
            'authcodedownload' => 'Download Authcodes for %n Domains',
        ),
    ));

    echo '<br/>';
}

$w = $this->widget('application.widgets.jqGrid.jqGridWidget', array(
	'caption'	=> 'All Domains registered with \''.Yii::app()->user->name.'\'',
	'thisfile'	=> $this->createUrl('domainreports/alldomains',array('model'=>$model_serialised)),
	'datasource'	=> $this->createUrl('domainreports/getgriddata',array('model'=>$model_serialised)),
	'model'		=> $model,
    'selections'	=> ($level == 2 || $level == 3) ? true : false,
    'searching'	=> true,
	 'sorting'	=> true,
	));
?>
<!-- END protected/views/domainreports/alldomains.php -->
