<!-- START protected/views/site/footer.php -->
<?php
/**
 * View page for shared page footer
 * 
 * @category  NRC
 * @package   IEDR_New_Registrars_Console
 * @author    IEDR <asd@iedr.ie>
 * @copyright 2011 IEDR
 * @license   http://www.iedr.ie/ (C) IEDR 2011
 * @version   CVS: $Id:$
 * @link      https://statistics.iedr.ie/
 * @see       view/layouts/main.php
 */

?>
<div id="footer">
	IE&nbsp;Domain&nbsp;Registry&nbsp;Ltd.
		Registered&nbsp;Office:
		2&nbsp;Harbour&nbsp;Square,
		Crofton&nbsp;Road,
		D&uacute;n Laoghaire,
		Co.&nbsp;Dublin.
	<br/>
	Registered&nbsp;No:&nbsp;315315.
	VAT&nbsp;No:&nbsp;IE&nbsp;6335315V
	Phone:&nbsp;+353&nbsp;1&nbsp;2365400 ::
	Fax:&nbsp;+353&nbsp;1&nbsp;2300365 ::
	IE&nbsp;Domain&nbsp;Registry&nbsp;Ltd&nbsp;&copy;&nbsp;<?php echo date('Y'); ?>
	<br/>
</div>

<?php
if(isset($_GET['permission'])) {
if($_GET['permission'] == 'true') {
?>
   <script>
      alert('You don`t have permissions to use this module or functionality');
   </script>   
<?php
}}
?>
<!-- END protected/views/site/footer.php -->
