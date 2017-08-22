<?php include('test_config.php'); test_header('Domain Reports - All Domains'); ?>
<tr><td>setTimeout</td><td><?php echo $timeout; ?></td><td></td></tr>
<tr><td>setSpeed</td><td><?php echo $speed; ?></td><td></td></tr>
<tr><td>open</td><td><?php echo $base; ?>/index.php?r=domainreports/alldomains</td><td></td></tr>
<tr><td>waitForTextPresent</td><td>IE Domain Registry Ltd</td><td></td></tr>
<?php

?>
<!-- OPEN PASSWORD CHANGE FORM -->
<tr>
	<td>open</td>
	<td>/index.php</td>
	<td></td>
</tr>
<tr>
	<td>clickAndWait</td>
	<td>link=Change Password</td>
	<td></td>
</tr>

<!-- PUT IN AN INCORRECT CURRENT PASSWORD AND ENSURE THAT AN ERROR IS THROWN --> 
<tr>
	<td>type</td>
	<td>ChangePasswordForm_old_password</td>
	<td>password123</td>
</tr>
<tr>
	<td>type</td>
	<td>ChangePasswordForm_new_password</td>
	<td>123456asdfG</td>
</tr>
<tr>
	<td>type</td>
	<td>ChangePasswordForm_repeat_new_password</td>
	<td>123456asdfG</td>
</tr>
<tr>
	<td>clickAndWait</td>
	<td>yt0</td>
	<td></td>
</tr>
<tr>
	<td>assertTextPresent</td>
	<td>Old password does not match stored password.</td>
	<td></td>
</tr>


<!-- PUT IN THE CORRECT STORED PASS AND NEW PASS THAT ARE TOO SHORT AND VERIFY ERROR --> 
<tr>
	<td>type</td>
	<td>ChangePasswordForm_old_password</td>
	<td>password</td>
</tr>
<tr>
	<td>type</td>
	<td>ChangePasswordForm_new_password</td>
	<td>1234</td>
</tr>
<tr>
	<td>type</td>
	<td>ChangePasswordForm_repeat_new_password</td>
	<td>1234</td>
</tr>
<tr>
	<td>clickAndWait</td>
	<td>yt0</td>
	<td></td>
</tr>
<tr>
	<td>assertTextPresent</td>
	<td>New Password is too short (minimum is 8 characters).</td>
	<td></td>
</tr>

<!-- PUT IN CORRECT STORED PASS and repeat pass not matching new password and verify error --> i

<tr>
        <td>type</td>
        <td>ChangePasswordForm_old_password</td>
        <td>password</td>
</tr>

<tr>
	<td>type</td>
	<td>ChangePasswordForm_new_password</td>
	<td>Password1234</td>
</tr>
<tr>
	<td>type</td>
	<td>ChangePasswordForm_repeat_new_password</td>
	<td>Passwor</td>
</tr>
<tr>
	<td>clickAndWait</td>
	<td>yt0</td>
	<td></td>
</tr>
<tr>
	<td>assertTextPresent</td>
	<td>New and repeat passwords must match.</td>
	<td></td>
</tr>

<!-- CHANGE PASSWORD SUCCESSFULLY -->

<tr>
        <td>type</td>
        <td>ChangePasswordForm_old_password</td>
        <td>password</td>
</tr>

<tr>
	<td>type</td>
	<td>ChangePasswordForm_new_password</td>
	<td>Password123</td>
</tr>
<tr>
	<td>type</td>
	<td>ChangePasswordForm_repeat_new_password</td>
	<td>Password123</td>
</tr>
<tr>
	<td>clickAndWait</td>
	<td>yt0</td>
	<td></td>
</tr>


<tr>
	<td>assertTextPresent</td>
	<td>Password updated successfully.</td>
	<td></td>
</tr>

<?php test_footer(); ?>
