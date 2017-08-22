<!-- START protected/views/site/twoFactorAuth.php -->
<?php
/**
 * Change settings for the Two Factor Authentication
 * 
 * @category  NRC
 * @package   IEDR_New_Registrars_Console
 * @author    IEDR <asd@iedr.ie>
 * @copyright 2011 IEDR
 * @license   http://www.iedr.ie/ (C) IEDR 2011
 * @version   CVS: $Id:$
 * @link      https://statistics.iedr.ie/
 * @see      ChangePasswordForm, SiteController::actionChangePassword()
 */

?>
<div class="form">
<?php $form=$this->beginWidget('CActiveForm', array(
        'id'=>'changeTFASettings',
        'enableAjaxValidation'=>false,
        ));

if($this->backend_errors != ""){
	echo "<div class=\"error\">" . $this->backend_errors . "</div>";
} else {
}

?>


<center><h2>Change Two Factor Authentication settings</h2></center>
The use of Google Authenticator is now <?php echo ($tfaUsed ? 'enabled' : 'disabled')?>.
   
   <div class="row buttons">
      <?php if($tfaUsed) {
      	echo CHtml::submitButton('Disable'); 
      } else {
		echo CHtml::submitButton('Enable'); 
	  }
       ?>
   </div>
<?php
    if(isset($newSecret)) {
        echo ('New secret: '.$newSecret);
    }
    $this->endWidget();
?>
<!-- END protected/views/site/twoFactorAuth.php -->

