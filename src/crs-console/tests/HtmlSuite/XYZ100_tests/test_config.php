<?php

$host = 'http://newregcon.iedr.ie/';
#$host = 'http://localhost/';

$base = '';
$timeout = 10000;
$speed = 100;

$db_host='db';
$db_user='root';
$db_pass='vinooigo';
$db_schema='phoenixdb';

// test parameters

$test_user_accountid = 601;
$test_user_nichandle = 'XYZ100-IEDR';

// db test-data functions
require_once('db.php');

function getRandHostName()
	{
	$alph='abcdefghijklmnopqrstuvwxyz1234567890';
	$nalph = strlen($alph);
	$randhostname = '';
	$l=rand(8,12);
	for($x=0;$i<$l;$i++)
		$randhostname .= substr($alph,rand(0,$nalph),1);
	return $randhostname;
	}

function test_header($testName)
	{
?><!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head profile="http://selenium-ide.openqa.org/profiles/test-case">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<link rel="selenium.base" href="<?php echo $host; ?>" />
	<title><?php echo $testName; ?></title>
</head>
<body>
	<table cellpadding="1" cellspacing="1" border="1">
		<thead><tr><td rowspan="1" colspan="3"><?php echo $testName; ?></td></tr></thead>
		<tbody>
<?php
	}

function test_footer()
	{
	?>
	<tr><td>pause</td><td>1000</td><td></td></tr>
	</tbody></table></body></html><?php
	}

