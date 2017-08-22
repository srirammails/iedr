<?php
include_once "functions.php";

$myDate = date('Y-m-d');
$yest = date("Y-m-d", strtotime("yesterday"));

$newreg = getNewRegCount();

echo "$newreg is count";


?>
