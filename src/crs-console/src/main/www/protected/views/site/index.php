<!-- START protected/views/site/index.php -->

<script>
    function checkSession(){
       $.ajax({
		    url:'<?php $host = Yii::app()->request->hostInfo; $host .= '/index.php?r=site/issesion'; echo $host;?>',
		    success: function(message) { 
             if(message == 'sessiontimeout') {  
                alert('Service session timeout.');
                window.location = '<?php $host = Yii::app()->request->hostInfo; $host .= '/index.php?r=site/logout'; echo $host;?>'; 
             } else if(message == 'nopermission') {
                alert('You do not have permission to access this site.');
                window.location = '<?php $host = Yii::app()->request->hostInfo; $host .= '/index.php?r=site/logout'; echo $host;?>'; 
             }
          }
		});
    }
    checkSession();     
    $.ajaxSetup ({ cache: true });
</script>

<?php
/**
 * View page for Main-Page body content
 * 
 * @category  NRC
 * @package   IEDR_New_Registrars_Console
 * @author    IEDR <asd@iedr.ie>
 * @copyright 2011 IEDR
 * @license   http://www.iedr.ie/ (C) IEDR 2011
 * @version   CVS: $Id:$
 * @link      https://statistics.iedr.ie/
 * @see       Sitecontroller::actionIndex()
 */

if(isset($_REQUEST) && array_key_exists('message', $_REQUEST)) {
   $request = AuthOnlyBaseController::safeDeserializeObj($_REQUEST['message']);
   if(property_exists($request, 'message') && isset($request->message)) {
         echo "<div class =\"flash-notice\">";
         echo $request->message . "<br>";
         echo "</div>";
   }
}

?>

<?php
if (Utility::isRegistrar() || Utility::isSuperRegistrar()) {
    include('registrarHome.php');
} else if (Utility::isDirect()) {
    include('directHome.php');
} else if (Utility::isAdminOrTech()) {
    include('adminTechHome.php');
} else {
    include('allHome.php');
}
?>
<!-- END protected/views/site/index.php -->