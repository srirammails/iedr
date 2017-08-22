<!-- START protected/views/domains/domainscreated.php -->
<?php
/**
 * View page for Domains - Domains Created
 * 
 * @category  NRC
 * @package   IEDR_New_Registrars_Console
 * @author    IEDR <asd@iedr.ie>
 * @copyright 2011 IEDR
 * @license   http://www.iedr.ie/ (C) IEDR 2011
 * @version   CVS: $Id:$
 * @link      https://statistics.iedr.ie/
 * @see       Domains_Created, DomainsController::actionDomainsCreated()
 */

$pg = 'Request Transfer Result';
$this->pageTitle=Yii::app()->name . ' - '.$pg;
?>
<h2><?php echo printBreadcrumbTrail($pg); ?></h2>
<table>
<?php

		$model = AuthOnlyBaseController::safeDeserializeObjFromFromSession('requesttransferresult');
		$returnUrl = 'Card declined.' . CHtml::link("Click here to try again/another card.", Yii::app()->createUrl('domains/requesttransferdetails',  array('errorReturnData' => true)));
		if (isset($model->errors)) {
			echo '<td>'.$domain.'</td><td>' . WSAPIError::getErrorsNotEmpty($model->errors, $returnUrl) . '</td>';
		} else {
			$link = $this->createUrl('tickets/viewticket',array('id'=>$model->ticket));
         echo '<tr><td> Your ticket for '.$model->domainName.' has been received and will be processed by our hostmasters in due course.</td>';
         echo "<td>Click here <a href=".$link.">".$model->ticket."</a> to view/modify the ticket.</td></tr>";
		}
?>
</table>
</div><!-- form -->

<!-- END protected/views/domains/domainscreated.php -->