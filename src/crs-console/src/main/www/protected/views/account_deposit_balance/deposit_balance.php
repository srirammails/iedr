<?php

$pg = 'Deposit Balance';
$this->pageTitle=Yii::app()->name . ' - '.$pg;
?>
<h2><?php echo printBreadcrumbTrail($pg); ?></h2>

<?php
   echo "Deposit Balance: € " . number_format($model->depositBalance, 2, '.', '') . "<br>";
   echo "Deposit Reservation: € " . number_format($model->depositReservation, 2, '.', '') . "<br>";
   echo "Available Balance: € " . number_format($model->availableBalance, 2, '.', '') . "<br><br><br><br>";
   echo "<a href='index.php?r=account_today_deposit_reservations/menu'>View Todays Deposit Reservations</a>";
?>
