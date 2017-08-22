<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head profile="http://selenium-ide.openqa.org/profiles/test-case">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="selenium.base" href="http://newregcon.iedr.ie/" />
<title>Accounts_Hist_TopUpHist</title>
</head>
<body>
<table cellpadding="1" cellspacing="1" border="1">
<thead>
<tr><td rowspan="1" colspan="3">Accounts_Hist_TopUpHist</td></tr>
</thead><tbody>
<tr>
	<td>open</td>
	<td>/index.php/accounts_history/topuphist</td>
	<td></td>
</tr>
<tr>
	<td>clickAndWait</td>
	<td>link=Deposit TopUp History</td>
	<td></td>
</tr>
<tr>
	<td>type</td>
	<td>days</td>
	<td>365</td>
</tr>
<tr>
	<td>clickAndWait</td>
	<td>yt0</td>
	<td></td>
</tr>
<tr>
	<td>waitForText</td>
	<td>//div[@id='yw0']/table/tbody/tr/td[5]</td>
	<td>2000.00</td>
</tr>
<tr>
	<td>assertText</td>
	<td>//div[@id='yw0']/table/tbody/tr/td[5]</td>
	<td>2000.00</td>
</tr>
<tr>
	<td>type</td>
	<td>days</td>
	<td>600</td>
</tr>
<tr>
	<td>clickAndWait</td>
	<td>yt0</td>
	<td></td>
</tr>
<tr>
	<td>type</td>
	<td>days</td>
	<td>999</td>
</tr>
<tr>
	<td>clickAndWait</td>
	<td>yt0</td>
	<td></td>
</tr>
<tr>
	<td>clickAndWait</td>
	<td>yt0</td>
	<td></td>
</tr>
<tr>
	<td>clickAndWait</td>
	<td>yt0</td>
	<td></td>
</tr>
<tr>
	<td>clickAndWait</td>
	<td>link=Logout (ABC1-IEDR)</td>
	<td></td>
</tr>
<tr>
	<td>clickAndWait</td>
	<td>link=Login</td>
	<td></td>
</tr>
<tr>
	<td>type</td>
	<td>LoginForm_username</td>
	<td>aae553-iedr</td>
</tr>
<tr>
	<td>type</td>
	<td>LoginForm_password</td>
	<td>password</td>
</tr>
<tr>
	<td>type</td>
	<td>LoginForm_verifyCode</td>
	<td>5d2fc08a-4975-11e0-808f-080027a1156a</td>
</tr>
<tr>
	<td>clickAndWait</td>
	<td>yt1</td>
	<td></td>
</tr>
<tr>
	<td>clickAndWait</td>
	<td>link=Deposit TopUp History</td>
	<td></td>
</tr>
<tr>
	<td>clickAndWait</td>
	<td>link=Logout (aae553-iedr)</td>
	<td></td>
</tr>
<tr>
	<td>clickAndWait</td>
	<td>link=Login</td>
	<td></td>
</tr>
<tr>
	<td>type</td>
	<td>LoginForm_username</td>
	<td>ABC1-IEDR</td>
</tr>
<tr>
	<td>type</td>
	<td>LoginForm_verifyCode</td>
	<td>5d2fc08a-4975-11e0-808f-080027a1156a</td>
</tr>
<tr>
	<td>clickAndWait</td>
	<td>yt1</td>
	<td></td>
</tr>

</tbody></table>
</body>
</html>
