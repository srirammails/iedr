--
-- Changes related to Email Scheduler (CRS-9)
--

-- Schema changes

DROP TABLE IF EXISTS `EmailDisabler`;
CREATE TABLE `EmailDisabler` (
  `ED_Email_E_ID` int(3) unsigned NOT NULL,
  `ED_Nic_Handle` varchar(12) NOT NULL,
  `ED_Disabled` char(1) NOT NULL DEFAULT 'Y' COMMENT 'flag indicating if given email template is disabled',
  `ED_TS` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`ED_Email_E_ID`,`ED_Nic_Handle`),
  CONSTRAINT FOREIGN KEY (`ED_Email_E_ID`) REFERENCES `Email` (`E_ID`) ON UPDATE CASCADE,
  CONSTRAINT FOREIGN KEY (`ED_Nic_Handle`) REFERENCES `NicHandle` (`Nic_Handle`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `EmailDisablerHist`;
CREATE TABLE `EmailDisablerHist` (
  `ED_Email_E_ID` int(3) unsigned NOT NULL,
  `ED_Nic_Handle` varchar(12) NOT NULL,
  `ED_Disabled` char(1) NOT NULL DEFAULT 'Y' COMMENT 'flag indicating if given email template is disabled',
  `ED_TS` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `Chng_NH` varchar(12) NOT NULL DEFAULT '',
  `Chng_Dt` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`ED_Email_E_ID`,`ED_Nic_Handle`,`Chng_Dt`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `EmailGroup`;
CREATE TABLE `EmailGroup` (
  `EG_ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `EG_Name` varchar(255) DEFAULT NULL,
  `EG_TS` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`EG_ID`),
  UNIQUE KEY `EG_Name` (`EG_Name`)
) ENGINE=InnoDB AUTO_INCREMENT=179 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `EmailGroupHist`;
CREATE TABLE `EmailGroupHist` (
  `EG_ID` int(10) unsigned NOT NULL,
  `EG_Name` varchar(255) DEFAULT NULL,
  `EG_TS` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `Chng_NH` varchar(12) NOT NULL DEFAULT '',
  `Chng_Dt` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`EG_ID`,`Chng_Dt`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

ALTER TABLE `Email`
  ADD COLUMN (`E_To_Internal` text COMMENT 'internal to addresses'),
  ADD COLUMN (`E_CC_Internal` text COMMENT 'internal CC addresses'),
  ADD COLUMN (`E_BCC_Internal` text COMMENT 'internal BCC addresses'),
  ADD COLUMN (`EG_ID` int(10) unsigned DEFAULT NULL COMMENT 'group ID'),
  ADD COLUMN (`E_TS` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'change timestamp'),
  ADD COLUMN (`E_Suppressible` char(1) NOT NULL DEFAULT 'Y' COMMENT 'flag indicating if this email template can be disabled by registrars'),
  ADD COLUMN (`E_Send_Reason` text COMMENT 'reason for sending this email template'),
  ADD COLUMN (`E_Non_Suppressible_Reason` text COMMENT 'reason for disallowing suppression of this email template'),
  ADD CONSTRAINT FOREIGN KEY (`EG_ID`) REFERENCES `EmailGroup` (`EG_ID`) ON DELETE SET NULL ON UPDATE CASCADE;

DROP TABLE IF EXISTS `EmailHist`;
CREATE TABLE `EmailHist` (
  `E_ID` int(3) unsigned NOT NULL,
  `E_Name` text,
  `E_Text` text,
  `E_Subject` text,
  `E_To` text,
  `E_CC` text,
  `E_BCC` text,
  `E_To_Internal` text,
  `E_CC_Internal` text,
  `E_BCC_Internal` text,
  `E_From` text,
  `active` char(1) NOT NULL DEFAULT '1',
  `Html` char(1) DEFAULT '0',
  `EG_ID` int(10) unsigned DEFAULT NULL,
  `E_TS` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `E_Suppressible` char(1) NOT NULL DEFAULT 'Y',
  `E_Send_Reason` text,
  `E_Non_Suppressible_Reason` text,
  `Chng_NH` varchar(12) NOT NULL DEFAULT '',
  `Chng_Dt` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`E_ID`,`Chng_Dt`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


-- Data migration

UPDATE Email SET E_To='$BILL-C_EMAIL$', E_To_Internal=NULL, E_CC=NULL, E_CC_Internal='receipts@iedr.ie', E_BCC=NULL, E_BCC_Internal='receipts@iedr.ie' WHERE E_ID=1;
UPDATE Email SET E_To='$BILL-C_EMAIL$', E_To_Internal=NULL, E_CC=NULL, E_CC_Internal='receipts@iedr.ie', E_BCC=NULL, E_BCC_Internal='receipts@iedr.ie' WHERE E_ID=2;
UPDATE Email SET E_To='$BILL-C_EMAIL$', E_To_Internal=NULL, E_CC=NULL, E_CC_Internal='receipts@iedr.ie', E_BCC=NULL, E_BCC_Internal='receipts@iedr.ie' WHERE E_ID=3;
UPDATE Email SET E_To='$BILL-C_EMAIL$', E_To_Internal=NULL, E_CC=NULL, E_CC_Internal='receipts@iedr.ie', E_BCC=NULL, E_BCC_Internal='receipts@iedr.ie' WHERE E_ID=4;
UPDATE Email SET E_To='$BILL-C_EMAIL$', E_To_Internal=NULL, E_CC=NULL, E_CC_Internal='receipts@iedr.ie', E_BCC=NULL, E_BCC_Internal='receipts@iedr.ie' WHERE E_ID=5;
UPDATE Email SET E_To='$BILL-C_EMAIL$', E_To_Internal=NULL, E_CC=NULL, E_CC_Internal='receipts@iedr.ie', E_BCC=NULL, E_BCC_Internal='receipts@iedr.ie' WHERE E_ID=7;
UPDATE Email SET E_To='$GAINING_BILL-C_EMAIL$', E_To_Internal=NULL, E_CC=NULL, E_CC_Internal='receipts@iedr.ie, registrations@iedr.ie', E_BCC=NULL, E_BCC_Internal='receipts@iedr.ie' WHERE E_ID=8;
UPDATE Email SET E_To='$GAINING_BILL-C_EMAIL$', E_To_Internal=NULL, E_CC=NULL, E_CC_Internal='receipts@iedr.ie, registrations@iedr.ie', E_BCC=NULL, E_BCC_Internal='receipts@iedr.ie' WHERE E_ID=9;
UPDATE Email SET E_To='$GAINING_BILL-C_EMAIL$', E_To_Internal=NULL, E_CC=NULL, E_CC_Internal='receipts@iedr.ie', E_BCC=NULL, E_BCC_Internal=NULL WHERE E_ID=10;
UPDATE Email SET E_To='$ADMIN-C_EMAIL$', E_To_Internal=NULL, E_CC='$BILL-C_EMAIL$, $WEBMASTER$, $INFO$, $POSTMASTER$', E_CC_Internal=NULL, E_BCC=NULL, E_BCC_Internal='nrp@iedr.ie, donotreply@iedr.ie' WHERE E_ID=11;
UPDATE Email SET E_To='$ADMIN-C_EMAIL$', E_To_Internal=NULL, E_CC='$BILL-C_EMAIL$, $POSTMASTER$, $WEBMASTER$, $INFO$', E_CC_Internal='nrp@iedr.ie', E_BCC=NULL, E_BCC_Internal='nrp@iedr.ie' WHERE E_ID=12;
UPDATE Email SET E_To='$ADMIN-C_EMAIL$', E_To_Internal=NULL, E_CC='$BILL-C_EMAIL$, $POSTMASTER$, $WEBMASTER$, $INFO$', E_CC_Internal='nrp@iedr.ie', E_BCC=NULL, E_BCC_Internal='donotreply@iedr.ie' WHERE E_ID=13;
UPDATE Email SET E_To='$ADMIN-C_EMAIL$', E_To_Internal=NULL, E_CC='$BILL-C_EMAIL$, $POSTMASTER$, $WEBMASTER$, $INFO$', E_CC_Internal=NULL, E_BCC=NULL, E_BCC_Internal='nrp@iedr.ie' WHERE E_ID=14;
UPDATE Email SET E_To='$BILL-C_EMAIL$', E_To_Internal=NULL, E_CC='$ADMIN-C_EMAIL$', E_CC_Internal='nrp@iedr.ie', E_BCC=NULL, E_BCC_Internal='nrp@iedr.ie' WHERE E_ID=15;
UPDATE Email SET E_To='$BILL-C_EMAIL$', E_To_Internal=NULL, E_CC=NULL, E_CC_Internal='nrp@iedr.ie', E_BCC=NULL, E_BCC_Internal='nrp@iedr.ie' WHERE E_ID=16;
UPDATE Email SET E_To='$ADMIN-C_EMAIL$', E_To_Internal=NULL, E_CC='$BILL-C_EMAIL$, $GAINING_BILL-C_EMAIL$', E_CC_Internal=' registrations@iedr.ie', E_BCC=NULL, E_BCC_Internal='hostmaster-archive@domainregistry.ie, registrations@iedr.ie' WHERE E_ID=17;
UPDATE Email SET E_To='$ADMIN-C_EMAIL$', E_To_Internal=NULL, E_CC='$BILL-C_EMAIL$, $GAINING_BILL-C_EMAIL$,', E_CC_Internal='registrations@iedr.ie', E_BCC=NULL, E_BCC_Internal='hostmaster-archive@domainregistry.ie, registrations@iedr.ie' WHERE E_ID=18;
UPDATE Email SET E_To='$ADMIN-C_EMAIL$', E_To_Internal=NULL, E_CC='$BILL-C_EMAIL$, $GAINING_BILL-C_EMAIL$,', E_CC_Internal='registrations@iedr.ie', E_BCC=NULL, E_BCC_Internal='hostmaster-archive@domainregistry.ie, registrations@iedr.ie' WHERE E_ID=19;
UPDATE Email SET E_To='$BILL-C_EMAIL$', E_To_Internal=NULL, E_CC=NULL, E_CC_Internal='invoicing@iedr.ie', E_BCC=NULL, E_BCC_Internal='invoicing@iedr.ie' WHERE E_ID=24;
UPDATE Email SET E_To=NULL, E_To_Internal='invoicing@iedr.ie', E_CC=NULL, E_CC_Internal='abutler@iedr.ie', E_BCC=NULL, E_BCC_Internal=NULL WHERE E_ID=25;
UPDATE Email SET E_To='$BILL-C_EMAIL$', E_To_Internal=NULL, E_CC=NULL, E_CC_Internal='accounts@iedr.ie', E_BCC=NULL, E_BCC_Internal=NULL WHERE E_ID=26;
UPDATE Email SET E_To='invoicing@iedr.ie', E_To_Internal='invoicing@iedr.ie', E_CC=NULL, E_CC_Internal='abutler@iedr.ie', E_BCC=NULL, E_BCC_Internal=NULL WHERE E_ID=27;
UPDATE Email SET E_To='$BILL-C_EMAIL$', E_To_Internal=NULL, E_CC=NULL, E_CC_Internal='registrations@iedr.ie', E_BCC=NULL, E_BCC_Internal='hostmaster-archive@domainregistry.ie, registrations@iedr.ie' WHERE E_ID=28;
UPDATE Email SET E_To='$BILL-C_EMAIL$', E_To_Internal=NULL, E_CC=NULL, E_CC_Internal='registrations@iedr.ie', E_BCC=NULL, E_BCC_Internal='hostmaster-archive@domainregistry.ie, registrations@iedr.ie' WHERE E_ID=29;
UPDATE Email SET E_To='$BILL-C_EMAIL$', E_To_Internal=NULL, E_CC=NULL, E_CC_Internal='registrations@iedr.ie', E_BCC=NULL, E_BCC_Internal='hostmaster-archive@domainregistry.ie, registrations@iedr.ie' WHERE E_ID=31;
UPDATE Email SET E_To='$BILL-C_EMAIL$', E_To_Internal=NULL, E_CC=NULL, E_CC_Internal='registrations@iedr.ie', E_BCC=NULL, E_BCC_Internal='hostmaster-archive@domainregistry.ie, registrations@iedr.ie' WHERE E_ID=32;
UPDATE Email SET E_To='$GAINING_BILL-C_EMAIL$', E_To_Internal=NULL, E_CC='$BILL-C_EMAIL$, $ADMIN-C_EMAIL$', E_CC_Internal='registrations@iedr.ie', E_BCC=NULL, E_BCC_Internal='hostmaster-archive@domainregistry.ie, registrations@iedr.ie' WHERE E_ID=33;
UPDATE Email SET E_To='$GAINING_BILL-C_EMAIL$', E_To_Internal=NULL, E_CC='$ADMIN-C_EMAIL$,', E_CC_Internal='registrations@iedr.ie', E_BCC=NULL, E_BCC_Internal='hostmaster-archive@domainregistry.ie, registrations@iedr.ie' WHERE E_ID=34;
UPDATE Email SET E_To='$ADMIN-C_EMAIL$', E_To_Internal=NULL, E_CC='$BILL-C_EMAIL$,', E_CC_Internal='registrations@iedr.ie', E_BCC=NULL, E_BCC_Internal='hostmaster-archive@domainregistry.ie, registrations@iedr.ie' WHERE E_ID=35;
UPDATE Email SET E_To='$GAINING_BILL-C_EMAIL$', E_To_Internal=NULL, E_CC='$ADMIN-C_EMAIL$,', E_CC_Internal='registrations@iedr.ie', E_BCC=NULL, E_BCC_Internal='hostmaster-archive@domainregistry.ie, registrations@iedr.ie' WHERE E_ID=36;
UPDATE Email SET E_To='$TECH-C_EMAIL$', E_To_Internal=NULL, E_CC='$GAINING_BILL-C_EMAIL$', E_CC_Internal='registrations@iedr.ie', E_BCC=NULL, E_BCC_Internal='hostmaster-archive@domainregistry.ie, registrations@iedr.ie' WHERE E_ID=37;
UPDATE Email SET E_To='$TECH-C_EMAIL$', E_To_Internal=NULL, E_CC='$GAINING_BILL-C_EMAIL$', E_CC_Internal='registrations@iedr.ie', E_BCC=NULL, E_BCC_Internal='hostmaster-archive@domainregistry.ie, registrations@iedr.ie' WHERE E_ID=38;
UPDATE Email SET E_To='$GAINING_BILL-C_EMAIL$', E_To_Internal=NULL, E_CC='$ADMIN-C_EMAIL$, $LOSING_BILL-C_EMAIL$', E_CC_Internal='registrations@iedr.ie', E_BCC=NULL, E_BCC_Internal='hostmaster-archive@domainregistry.ie, registrations@iedr.ie' WHERE E_ID=39;
UPDATE Email SET E_To='$BILL-C_EMAIL$', E_To_Internal=NULL, E_CC='$ADMIN-C_EMAIL$,', E_CC_Internal='registrations@iedr.ie', E_BCC=NULL, E_BCC_Internal='hostmaster-archive@domainregistry.ie, registrations@iedr.ie' WHERE E_ID=40;
UPDATE Email SET E_To='$ADMIN-C_EMAIL$', E_To_Internal=NULL, E_CC='$BILL-C_EMAIL$,', E_CC_Internal='registrations@iedr.ie', E_BCC=NULL, E_BCC_Internal='hostmaster-archive@domainregistry.ie, registrations@iedr.ie' WHERE E_ID=41;
UPDATE Email SET E_To='$ADMIN-C_EMAIL$', E_To_Internal=NULL, E_CC='$BILL-C_EMAIL$,', E_CC_Internal='registrations@iedr.ie', E_BCC=NULL, E_BCC_Internal='hostmaster-archive@domainregistry.ie, registrations@iedr.ie' WHERE E_ID=42;
UPDATE Email SET E_To='$BILL-C_EMAIL$', E_To_Internal=NULL, E_CC='$ADMIN-C_EMAIL$,', E_CC_Internal='registrations@iedr.ie', E_BCC=NULL, E_BCC_Internal='hostmaster-archive@domainregistry.ie, registrations@iedr.ie' WHERE E_ID=43;
UPDATE Email SET E_To='$TECH-C_EMAIL$', E_To_Internal=NULL, E_CC='$BILL-C_EMAIL$,', E_CC_Internal='registrations@iedr.ie', E_BCC=NULL, E_BCC_Internal='hostmaster-archive@domainregistry.ie, registrations@iedr.ie' WHERE E_ID=44;
UPDATE Email SET E_To='$TECH-C_EMAIL$', E_To_Internal=NULL, E_CC='$BILL-C_EMAIL$,', E_CC_Internal='registrations@iedr.ie', E_BCC=NULL, E_BCC_Internal='hostmaster-archive@domainregistry.ie, registrations@iedr.ie' WHERE E_ID=45;
UPDATE Email SET E_To='$ADMIN-C_EMAIL$', E_To_Internal=NULL, E_CC='$BILL-C_EMAIL$, $TECH-C_EMAIL$', E_CC_Internal='registrations@iedr.ie', E_BCC=NULL, E_BCC_Internal='hostmaster-archive@domainregistry.ie, registrations@iedr.ie' WHERE E_ID=46;
UPDATE Email SET E_To='$BILL-C_EMAIL$', E_To_Internal=NULL, E_CC=NULL, E_CC_Internal='registrations@iedr.ie', E_BCC=NULL, E_BCC_Internal='hostmaster-archive@domainregistry.ie, registrations@iedr.ie' WHERE E_ID=47;
UPDATE Email SET E_To='$NIC_EMAIL$', E_To_Internal=NULL, E_CC=NULL, E_CC_Internal=NULL, E_BCC=NULL, E_BCC_Internal=NULL WHERE E_ID=49;
UPDATE Email SET E_To=NULL, E_To_Internal='receipts@iedr.ie', E_CC=NULL, E_CC_Internal='abutler@iedr.ie', E_BCC=NULL, E_BCC_Internal='receipts@iedr.ie' WHERE E_ID=50;
UPDATE Email SET E_To='$NIC_EMAIL$', E_To_Internal=NULL, E_CC=NULL, E_CC_Internal='registrations@iedr.ie', E_BCC=NULL, E_BCC_Internal='hostmaster-archive@domainregistry.ie, registrations@iedr.ie' WHERE E_ID=51;
UPDATE Email SET E_To='$ADMIN-C_EMAIL$', E_To_Internal=NULL, E_CC='$BILL-C_EMAIL$, $POSTMASTER$, $WEBMASTER$, $INFO$', E_CC_Internal=NULL, E_BCC=NULL, E_BCC_Internal='nrp@iedr.ie, donotreply@iedr.ie' WHERE E_ID=55;
UPDATE Email SET E_To='asd@iedr.ie', E_To_Internal='asd@iedr.ie', E_CC=NULL, E_CC_Internal=NULL, E_BCC=NULL, E_BCC_Internal=NULL WHERE E_ID=56;
UPDATE Email SET E_To='mkral@iedr.ie', E_To_Internal='mkral@iedr.ie', E_CC=NULL, E_CC_Internal=NULL, E_BCC=NULL, E_BCC_Internal=NULL WHERE E_ID=57;
UPDATE Email SET E_To=NULL, E_To_Internal='accounts@iedr.ie', E_CC=NULL, E_CC_Internal='abutler@iedr.ie', E_BCC=NULL, E_BCC_Internal=NULL WHERE E_ID=58;
UPDATE Email SET E_To='$BILL-C_EMAIL$', E_To_Internal=NULL, E_CC=NULL, E_CC_Internal='receipts@iedr.ie', E_BCC=NULL, E_BCC_Internal='receipts@iedr.ie' WHERE E_ID=59;
UPDATE Email SET E_To=NULL, E_To_Internal='invoicing@iedr.ie', E_CC=NULL, E_CC_Internal='abutler@iedr.ie', E_BCC=NULL, E_BCC_Internal='hostmaster-archive@domainregistry.ie' WHERE E_ID=60;
UPDATE Email SET E_To='$BILL-C_EMAIL$', E_To_Internal=NULL, E_CC=NULL, E_CC_Internal='invoicing@iedr.ie', E_BCC=NULL, E_BCC_Internal='invoicing@iedr.ie' WHERE E_ID=61;
UPDATE Email SET E_To='$BILL-C_EMAIL$', E_To_Internal=NULL, E_CC=NULL, E_CC_Internal='invoicing@iedr.ie', E_BCC=NULL, E_BCC_Internal='invoicing@iedr.ie' WHERE E_ID=62;
UPDATE Email SET E_To='$ADMIN-C_EMAIL$', E_To_Internal=NULL, E_CC='$BILL-C_EMAIL$, $POSTMASTER$, $WEBMASTER$, $INFO$', E_CC_Internal=NULL, E_BCC=NULL, E_BCC_Internal='nrp@iedr.ie' WHERE E_ID=63;
UPDATE Email SET E_To='$ADMIN-C_EMAIL$', E_To_Internal=NULL, E_CC='$BILL-C_EMAIL$, $POSTMASTER$, $WEBMASTER$, $INFO$', E_CC_Internal=NULL, E_BCC=NULL, E_BCC_Internal='nrp@iedr.ie, donotreply@iedr.ie' WHERE E_ID=64;
UPDATE Email SET E_To=NULL, E_To_Internal=NULL, E_CC=NULL, E_CC_Internal='receipts@iedr.ie', E_BCC=NULL, E_BCC_Internal='receipts@iedr.ie' WHERE E_ID=65;
UPDATE Email SET E_To=NULL, E_To_Internal=NULL, E_CC=NULL, E_CC_Internal='receipts@iedr.ie', E_BCC=NULL, E_BCC_Internal='receipts@iedr.ie' WHERE E_ID=66;
UPDATE Email SET E_To=NULL, E_To_Internal=NULL, E_CC=NULL, E_CC_Internal='receipts@iedr.ie', E_BCC=NULL, E_BCC_Internal='receipts@iedr.ie' WHERE E_ID=67;
UPDATE Email SET E_To=NULL, E_To_Internal=NULL, E_CC=NULL, E_CC_Internal='receipts@iedr.ie', E_BCC=NULL, E_BCC_Internal='receipts@iedr.ie' WHERE E_ID=68;
UPDATE Email SET E_To=NULL, E_To_Internal=NULL, E_CC=NULL, E_CC_Internal='receipts@iedr.ie', E_BCC=NULL, E_BCC_Internal='receipts@iedr.ie' WHERE E_ID=69;
UPDATE Email SET E_To=NULL, E_To_Internal=NULL, E_CC=NULL, E_CC_Internal='receipts@iedr.ie', E_BCC=NULL, E_BCC_Internal='receipts@iedr.ie' WHERE E_ID=70;
UPDATE Email SET E_To='$BILL-C_EMAIL$', E_To_Internal=NULL, E_CC=NULL, E_CC_Internal='receipts@iedr.ie', E_BCC=NULL, E_BCC_Internal='receipts@iedr.ie' WHERE E_ID=71;
UPDATE Email SET E_To='$BILL-C_EMAIL$', E_To_Internal=NULL, E_CC=NULL, E_CC_Internal='receipts@iedr.ie', E_BCC=NULL, E_BCC_Internal='receipts@iedr.ie' WHERE E_ID=72;
UPDATE Email SET E_To='$BILL-C_EMAIL$', E_To_Internal=NULL, E_CC=NULL, E_CC_Internal='receipts@iedr.ie', E_BCC=NULL, E_BCC_Internal='receipts@iedr.ie' WHERE E_ID=73;
UPDATE Email SET E_To='$BILL-C_EMAIL$', E_To_Internal=NULL, E_CC=NULL, E_CC_Internal='accounts@iedr.ie', E_BCC=NULL, E_BCC_Internal=NULL WHERE E_ID=74;
UPDATE Email SET E_To=NULL, E_To_Internal='accounts@iedr.ie', E_CC=NULL, E_CC_Internal='abutler@iedr.ie', E_BCC=NULL, E_BCC_Internal=NULL WHERE E_ID=75;
UPDATE Email SET E_To='$BILL-C_EMAIL$', E_To_Internal=NULL, E_CC=NULL, E_CC_Internal='registrations@iedr.ie', E_BCC=NULL, E_BCC_Internal='hostmaster-archive@domainregistry.ie, registrations@iedr.ie' WHERE E_ID=76;
UPDATE Email SET E_To='$BILL-C_EMAIL$', E_To_Internal=NULL, E_CC=NULL, E_CC_Internal='registrations@iedr.ie', E_BCC=NULL, E_BCC_Internal='hostmaster-archive@domainregistry.ie, registrations@iedr.ie' WHERE E_ID=77;
UPDATE Email SET E_To='$BILL-C_EMAIL$', E_To_Internal=NULL, E_CC=NULL, E_CC_Internal='registrations@iedr.ie, accounts@iedr.ie', E_BCC=NULL, E_BCC_Internal='hostmaster-archive@domainregistry.ie, registrations@iedr.ie' WHERE E_ID=79;
UPDATE Email SET E_To='$BILL-C_EMAIL$', E_To_Internal=NULL, E_CC=NULL, E_CC_Internal='registrations@iedr.ie', E_BCC=NULL, E_BCC_Internal='hostmaster-archive@domainregistry.ie, registrations@iedr.ie' WHERE E_ID=80;
UPDATE Email SET E_To='$NIC_EMAIL$', E_To_Internal=NULL, E_CC=NULL, E_CC_Internal='registrations@iedr.ie', E_BCC=NULL, E_BCC_Internal='hostmaster-archive@domainregistry.ie, registrations@iedr.ie' WHERE E_ID=81;
UPDATE Email SET E_To='$BILL-C_EMAIL$', E_To_Internal=NULL, E_CC=NULL, E_CC_Internal='registrations@iedr.ie', E_BCC=NULL, E_BCC_Internal='hostmaster-archive@domainregistry.ie, registrations@iedr.ie' WHERE E_ID=82;
UPDATE Email SET E_To='$ADMIN-C_EMAIL$', E_To_Internal=NULL, E_CC='$BILL-C_EMAIL$, $GAINING_BILL-C_EMAIL$', E_CC_Internal='registrations@iedr.ie', E_BCC=NULL, E_BCC_Internal='hostmaster-archive@domainregistry.ie, registrations@iedr.ie' WHERE E_ID=83;
UPDATE Email SET E_To='$BILL-C_EMAIL$', E_To_Internal=NULL, E_CC=NULL, E_CC_Internal='registrations@iedr.ie', E_BCC=NULL, E_BCC_Internal='hostmaster-archive@domainregistry.ie, registrations@iedr.ie' WHERE E_ID=84;
UPDATE Email SET E_To='$ADMIN-C_EMAIL$', E_To_Internal=NULL, E_CC='$BILL-C_EMAIL$,', E_CC_Internal='registrations@iedr.ie', E_BCC=NULL, E_BCC_Internal='hostmaster-archive@domainregistry.ie, registrations@iedr.ie' WHERE E_ID=85;
UPDATE Email SET E_To='$ADMIN-C_EMAIL$', E_To_Internal=NULL, E_CC='$BILL-C_EMAIL$,', E_CC_Internal='registrations@iedr.ie', E_BCC=NULL, E_BCC_Internal='hostmaster-archive@domainregistry.ie, registrations@iedr.ie' WHERE E_ID=86;
UPDATE Email SET E_To='$BILL-C_EMAIL$', E_To_Internal=NULL, E_CC=NULL, E_CC_Internal='registrations@iedr.ie', E_BCC=NULL, E_BCC_Internal='hostmaster-archive@domainregistry.ie, registrations@iedr.ie' WHERE E_ID=87;
UPDATE Email SET E_To='$BILL-C_EMAIL$', E_To_Internal=NULL, E_CC=NULL, E_CC_Internal='registrations@iedr.ie', E_BCC=NULL, E_BCC_Internal='hostmaster-archive@domainregistry.ie, registrations@iedr.ie' WHERE E_ID=88;
UPDATE Email SET E_To='$BILL-C_EMAIL$', E_To_Internal=NULL, E_CC=NULL, E_CC_Internal='registrations@iedr.ie', E_BCC=NULL, E_BCC_Internal='hostmaster-archive@domainregistry.ie, registrations@iedr.ie' WHERE E_ID=89;
UPDATE Email SET E_To='$BILL-C_EMAIL$', E_To_Internal=NULL, E_CC=NULL, E_CC_Internal='registrations@iedr.ie', E_BCC=NULL, E_BCC_Internal='hostmaster-archive@domainregistry.ie, registrations@iedr.ie' WHERE E_ID=90;
UPDATE Email SET E_To='$BILL-C_EMAIL$', E_To_Internal=NULL, E_CC=NULL, E_CC_Internal='registrations@iedr.ie', E_BCC=NULL, E_BCC_Internal='hostmaster-archive@domainregistry.ie, registrations@iedr.ie' WHERE E_ID=91;
UPDATE Email SET E_To='$BILL-C_EMAIL$', E_To_Internal=NULL, E_CC=NULL, E_CC_Internal='registrations@iedr.ie', E_BCC=NULL, E_BCC_Internal='hostmaster-archive@domainregistry.ie, registrations@iedr.ie' WHERE E_ID=92;
UPDATE Email SET E_To='$BILL-C_EMAIL$', E_To_Internal=NULL, E_CC='$ADMIN-C_EMAIL$,', E_CC_Internal='registrations@iedr.ie', E_BCC=NULL, E_BCC_Internal='hostmaster-archive@domainregistry.ie, registrations@iedr.ie' WHERE E_ID=93;
UPDATE Email SET E_To='$BILL-C_EMAIL$', E_To_Internal=NULL, E_CC=NULL, E_CC_Internal='invoicing@iedr.ie', E_BCC=NULL, E_BCC_Internal='invoicing@iedr.ie' WHERE E_ID=101;
UPDATE Email SET E_To='$TECH-C_EMAIL$', E_To_Internal=NULL, E_CC=NULL, E_CC_Internal=NULL, E_BCC=NULL, E_BCC_Internal='hostmaster-archive@domainregistry.ie, registrations@iedr.ie' WHERE E_ID=102;
UPDATE Email SET E_To='$CREATOR-C_EMAIL$', E_To_Internal='registrations@iedr.ie', E_CC=NULL, E_CC_Internal=NULL, E_BCC=NULL, E_BCC_Internal='hostmaster-archive@domainregistry.ie, registrations@iedr.ie' WHERE E_ID=103;
UPDATE Email SET E_To='$BILL-C_EMAIL$', E_To_Internal=NULL, E_CC=NULL, E_CC_Internal=NULL, E_BCC=NULL, E_BCC_Internal='hostmaster-archive@domainregistry.ie, registrations@iedr.ie' WHERE E_ID=104;
UPDATE Email SET E_To='$NIC_EMAIL$', E_To_Internal=NULL, E_CC=NULL, E_CC_Internal=NULL, E_BCC=NULL, E_BCC_Internal=NULL WHERE E_ID=105;
UPDATE Email SET E_To='$CREATOR-C_EMAIL$', E_To_Internal=NULL, E_CC='$BILL-C_EMAIL$,', E_CC_Internal='registrations@iedr.ie', E_BCC=NULL, E_BCC_Internal='registrations@iedr.ie, hostmaster-archive@domainregistry.ie' WHERE E_ID=120;
UPDATE Email SET E_To='$CREATOR-C_EMAIL$', E_To_Internal=NULL, E_CC='$BILL-C_EMAIL$,', E_CC_Internal='registrations@iedr.ie', E_BCC=NULL, E_BCC_Internal='registrations@iedr.ie, hostmaster-archive@domainregistry.ie' WHERE E_ID=121;
UPDATE Email SET E_To='$ADMIN-C_EMAIL$', E_To_Internal=NULL, E_CC=NULL, E_CC_Internal='registrations@iedr.ie', E_BCC=NULL, E_BCC_Internal='hostmaster-archive@domainregistry.ie, registrations@iedr.ie' WHERE E_ID=129;
UPDATE Email SET E_To='$CREATOR-C_EMAIL$', E_To_Internal=NULL, E_CC='$ADMIN-C_EMAIL$, $BILL-C_EMAIL$,', E_CC_Internal='registrations@iedr.ie', E_BCC=NULL, E_BCC_Internal='hostmaster-archive@domainregistry.ie, registrations@iedr.ie' WHERE E_ID=130;
UPDATE Email SET E_To='$CREATOR-C_EMAIL$', E_To_Internal=NULL, E_CC='$ADMIN-C_EMAIL$, $BILL-C_EMAIL$,', E_CC_Internal='registrations@iedr.ie', E_BCC=NULL, E_BCC_Internal='hostmaster-archive@domainregistry.ie, registrations@iedr.ie' WHERE E_ID=131;
UPDATE Email SET E_To='$CREATOR-C_EMAIL$', E_To_Internal=NULL, E_CC='$ADMIN-C_EMAIL$, $BILL-C_EMAIL$,', E_CC_Internal='registrations@iedr.ie', E_BCC=NULL, E_BCC_Internal='hostmaster-archive@domainregistry.ie, registrations@iedr.ie' WHERE E_ID=132;
UPDATE Email SET E_To='$CREATOR-C_EMAIL$', E_To_Internal=NULL, E_CC='$ADMIN-C_EMAIL$, $BILL-C_EMAIL$,', E_CC_Internal='registrations@iedr.ie', E_BCC=NULL, E_BCC_Internal='hostmaster-archive@domainregistry.ie, registrations@iedr.ie' WHERE E_ID=133;
UPDATE Email SET E_To='$CREATOR-C_EMAIL$', E_To_Internal=NULL, E_CC='$BILL-C_EMAIL$, $TECH-C_EMAIL$,', E_CC_Internal='registrations@iedr.ie', E_BCC=NULL, E_BCC_Internal='hostmaster-archive@domainregistry.ie, registrations@iedr.ie' WHERE E_ID=141;
UPDATE Email SET E_To='$CREATOR-C_EMAIL$', E_To_Internal=NULL, E_CC='$BILL-C_EMAIL$, $TECH-C_EMAIL$,', E_CC_Internal='registrations@iedr.ie', E_BCC=NULL, E_BCC_Internal='hostmaster-archive@domainregistry.ie, registrations@iedr.ie' WHERE E_ID=142;
UPDATE Email SET E_To='$CREATOR-C_EMAIL$', E_To_Internal=NULL, E_CC='$BILL-C_EMAIL$, $TECH-C_EMAIL$, $ADMIN-C_EMAIL$,', E_CC_Internal='registrations@iedr.ie', E_BCC=NULL, E_BCC_Internal='hostmaster-archive@domainregistry.ie, registrations@iedr.ie' WHERE E_ID=145;
UPDATE Email SET E_To='$CREATOR-C_EMAIL$', E_To_Internal=NULL, E_CC='$BILL-C_EMAIL$, $TECH-C_EMAIL$, $ADMIN-C_EMAIL$,', E_CC_Internal='registrations@iedr.ie', E_BCC=NULL, E_BCC_Internal='hostmaster-archive@domainregistry.ie, registrations@iedr.ie' WHERE E_ID=146;
UPDATE Email SET E_To=NULL, E_To_Internal='asd@iedr.ie', E_CC=NULL, E_CC_Internal=NULL, E_BCC=NULL, E_BCC_Internal=NULL WHERE E_ID=150;
UPDATE Email SET E_To='$NIC_EMAIL$', E_To_Internal=NULL, E_CC=NULL, E_CC_Internal='registrations@iedr.ie', E_BCC=NULL, E_BCC_Internal='hostmaster-archive@domainregistry.ie, registrations@iedr.ie' WHERE E_ID=151;
UPDATE Email SET E_To=NULL, E_To_Internal='asd@iedr.ie, kavanaghciara@gmail.com, altmannmarcelo@gmail.com, robin.spiteri@gmail.com, Mickarooney@gmail.com', E_CC=NULL, E_CC_Internal=NULL, E_BCC=NULL, E_BCC_Internal=NULL WHERE E_ID=160;
UPDATE Email SET E_To=NULL, E_To_Internal='asd@iedr.ie', E_CC=NULL, E_CC_Internal=NULL, E_BCC=NULL, E_BCC_Internal=NULL WHERE E_ID=161;
