ALTER TABLE `Reservation` ADD COLUMN (`Duration_Months` TINYINT NOT NULL DEFAULT '0' COMMENT 'domain period in months');
ALTER TABLE `Reservation` ADD COLUMN (`Ready_For_Settlement` CHAR(1) NOT NULL DEFAULT 'N' COMMENT 'reservation ready for settlement flag');
ALTER TABLE `Domain` ADD COLUMN (`D_GIBO_Retry_Timeout` timestamp NULL DEFAULT NULL COMMENT 'GIBO domains only: the timeout for the financial check to be performed');
ALTER TABLE `DomainHist` ADD COLUMN (`D_GIBO_Retry_Timeout` timestamp NULL DEFAULT NULL COMMENT 'GIBO domains only: the timeout for the financial check to be performed');
ALTER TABLE `Ticket` ADD COLUMN (`Financial_Status` tinyint(2) unsigned NOT NULL COMMENT 'Status of the Financial Check');
ALTER TABLE `Ticket` ADD COLUMN (`F_Status_Dt` timestamp NOT NULL COMMENT 'Date of the last Financial_Check status change');
ALTER TABLE `TicketHist` ADD COLUMN (`Financial_Status` tinyint(2) unsigned NOT NULL COMMENT 'Status of the Financial Check');
ALTER TABLE `TicketHist` ADD COLUMN (`F_Status_Dt` timestamp NOT NULL COMMENT 'Date of the last Financial_Check status change');
ALTER TABLE `Deposit` ADD COLUMN (`Notification_Sent` CHAR(1) NOT NULL DEFAULT 'N' COMMENT 'notification email sent flag');
ALTER TABLE `DepositHist` ADD COLUMN (`Notification_Sent` CHAR(1) NOT NULL DEFAULT 'N' COMMENT 'notification email sent flag');
CREATE TABLE if not exists `DNS_Check_Notification` (
  `ID` int(11) NOT NULL auto_increment COMMENT 'notification id',
    `Domain_Name` varchar(66) NOT NULL COMMENT 'notification domain name',
      `DNS_Name` varchar(70) NOT NULL COMMENT 'notification nameserver name',
        `Error_Message` TEXT NOT NULL COMMENT 'notification error message',
	  PRIMARY KEY  (`ID`)
	  ) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

	  insert  into `Email`(`E_Name`,`E_Text`,`E_Subject`,`E_To`,`E_CC`,`E_BCC`)
	  values
	  ('insufficientDepositFunds',
	  'Dear $BILL_C,\r\n\r\nYour deposit balance is insufficient to complete registration of domain : $D_NAME.\r\n\r\nKind regards,\r\nIE Hostmaster\r\n\r\ntel: +353-1-2365 400\r\nfax: +353-1-2300 365\r\n\email: hostmaster@iedr.ie\r\nweb: http://www.iedr.ie\r\nRegistered Office: 14 Windsor Terrace, Sandycove, Co. Dublin.\r\nRegistered in Ireland. No: 315315.',
	  'Insufficient deposit funds.',
	  '$MAIL_TO','','');
