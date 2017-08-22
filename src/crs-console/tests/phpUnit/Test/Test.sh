rm -rf *.txt
#phpunit --testdox-text 000_TestTomcatSessionTest.txt ./000_TestTomcatSessionTest.php
phpunit --testdox-text 001_testResult.txt --log-junit 001_TestStats.txt ./001_TestStats.php;
phpunit --testdox-text 002_testResult.txt --log-junit 002_TestDomainReportsAllDomains.txt ./002_TestDomainReportsAllDomains.php
phpunit --testdox-text 003_testResult.txt --log-junit 003_TestDomainReportsAllDomainsGridFilters.txt ./003_TestDomainReportsAllDomainsGridFilters.php
phpunit --testdox-text 004_testResult.txt --log-junit 004_TestDomainReportsAutoRenew.txt ./004_TestDomainReportsAutoRenew.php
phpunit --testdox-text 005_testResult.txt --log-junit 005_TestCurrentRenewals.txt ./005_TestCurrentRenewals.php
phpunit --testdox-text 006_testResult.txt --log-junit 006_TestInternalUsers.txt ./006_TestInternalUsers.php
phpunit --testdox-text 007_testResult.txt --log-junit 007_TestFutureRenewals.txt ./007_TestFutureRenewals.php
phpunit --testdox-text 008_testResult.txt --log-junit 008_TestNrp.txt ./008_TestNrp.php
phpunit --testdox-text 009_testResult.txt --log-junit 009_TestViewTodayCCReservation.txt ./009_TestViewTodayCCReservation.php
phpunit --testdox-text 010_testResult.txt --log-junit 010_TestViewTodayDepositReservation.txt ./010_TestViewTodayDepositReservation.php
phpunit --testdox-text 011_testResult.txt --log-junit 011_TestRegisterNew.txt ./011_TestRegisterNew.php
phpunit --testdox-text 012_testResult.txt --log-junit 012_TestTransferRequest.txt ./012_TestTransferRequest.php
phpunit --testdox-text 013_testResult.txt --log-junit 013_TestTickets.txt ./013_TestTickets.php
phpunit --testdox-text 014_testResult.txt --log-junit 014_TestAutorenew.txt ./014_TestAutorenew.php
phpunit --testdox-text 015_testResult.txt --log-junit 015_TestViewSinglePaymentSearch.txt ./015_TestViewSinglePaymentSearch.php
phpunit --testdox-text 016_testResult.txt --log-junit 016_TestTopUpMyDepositAccount.txt ./016_TestTopUpMyDepositAccount.php

cat 001_testResult.txt 002_testResult.txt 003_testResult.txt 004_testResult.txt 005_testResult.txt 006_testResult.txt 007_testResult.txt 008_testResult.txt 009_testResult.txt 010_testResult.txt 011_testResult.txt 012_testResult.txt 013_testResult.txt 014_testResult.txt 015_testResult.txt 016_testResult.txt > testResult.txt
rm *_testResult.txt