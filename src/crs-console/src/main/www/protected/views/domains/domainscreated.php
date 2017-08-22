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

$pg = 'Ticket Confirmation';
$this->pageTitle = Yii::app()->name . ' - ' . $pg;
?>
<h2><?php echo printBreadcrumbTrail($pg); ?></h2>
<table>
    <tr>
        <td>Domain</td>
        <td colspan="2">Result</td>
    </tr>
    <?php
    $model = AuthOnlyBaseController::safeDeserializeObjFromFromSession('domaincreateresults');
    foreach ($model->domainsprocessed as $domain => $result) {
        echo '<tr>';
        if (isset($result['error'])) {
            $modelSerialized = AuthOnlyBaseController::safeSerializeObj($model->returnData);
            $returnUrl = 'Card declined.' . CHtml::link("Click here to try again/another card.", Yii::app()->createUrl('domains/domaindetails', array('errorReturnData' => $modelSerialized)));
            echo '<td>'.$domain.'</td><td>' . WSAPIError::getErrorsNotEmpty($result['error'], $returnUrl) . '</td>';
        } else {
            echo '<td>Your ticket for ' . $domain . ' has been received and will be processed by our hostmasters in due course.</td>';
            $link = $this->createUrl('tickets/viewticket', array('id' => $result['ticket']));
            echo "<td>Click here <a href=" . $link . ">" . $result['ticket'] . "</a> to view/modify the ticket.</td>";
        }
        echo '</tr>';
    }
    ?>
    <?php if (!$uploaderResult->isEmpty()): ?>
        <tr style="height: 20px;"></tr>
        <tr>
            <td>Documents</td>
        </tr>

        <?php
        if ($uploaderResult->hasErrors()) {
            if (isset($uploaderResult->model['error'])) {
                echo '<tr><td colspan="3">' . WSAPIError::getErrorsNotEmpty($uploaderResult->model['error']) . '</td></tr>';
            } else foreach ($uploaderResult->getHumanReadableResults() as $docResult) {
                echo '<tr><td>'.$docResult['documentName'].'</td><td>'.$docResult['status'].'</td></tr>';
            }
        } else {
            echo '<tr><td colspan="3">All documents uploaded successfully</td></tr>';
        } ?>
    <?php endif; ?>
</table>
</div><!-- form -->

<!-- END protected/views/domains/domainscreated.php -->
