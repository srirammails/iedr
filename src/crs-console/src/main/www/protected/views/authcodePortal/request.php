<?php
/**
 * Authcode portal page
 *
 * @category  NRC
 * @package   IEDR_New_Registrars_Console
 * @copyright 2014 IEDR
 * @link      https://statistics.iedr.ie/
 * @see       AuthcodePortalController
 */

?>
<div class="form">
<?php $form = $this->beginWidget('CActiveForm', array(
        'id'=>'authcodePortal',
        'enableAjaxValidation'=>false,
        ));?>

<center><h2>Authcode Portal</h2></center>

<p>Please fill out the following details to receive you authcode.</p>

<p class="note">Fields with <span class="required">*</span> are required.</p>

    <table>
        <tr>
            <td><?php echo $form->labelEx($model,'domain_name'); ?>
                <?php echo $form->error($model,'domain_name'); ?></td>
            <td><?php echo $form->textField($model,'domain_name', array('size'=>30,'maxlength'=>60)); ?></td>
        </tr>


        <tr>
            <td><?php echo $form->labelEx($model,'email'); ?>
                <?php echo $form->error($model,'email'); ?></td>
            <td><?php echo $form->textField($model,'email', array('size'=>30,'maxlength'=>60)); ?></td>
        </tr>

        <tr>
            <td rowspan="3"><?php echo $form->labelEx($model, 'verifyCode') ?>
                <?php echo $form->error($model, 'verifyCode') ?>
                </td>
            <td><?php $this->widget('CCaptcha'); ?></td>
        </tr>
        <tr>
            <td><?php echo $form->textField($model,'verifyCode'); ?><td>
        </tr>
        <tr>
            <td><div class="hint">Please enter the letters as they are shown in the image above.
                <br/>Letters are not case-sensitive.</div></td>
        </tr>
    </table>

   <div class="row buttons">
      <?php echo CHtml::submitButton('Submit'); ?>
   </div>

<?php $this->endWidget(); ?>

<script>
    <?php if ($model->message) { ?>
        alert("<?php echo $model->message ?>");
    <?php } ?>
</script>