/*
SQLyog Enterprise - MySQL GUI v7.11 
MySQL - 5.0.45-Debian_1ubuntu3.3-log : Database - crsdb
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

CREATE DATABASE /*!32312 IF NOT EXISTS*/`crsdb` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `crsdb`;

/*Table structure for table `20MedSizeAccounts` */

DROP TABLE IF EXISTS `20MedSizeAccounts`;

CREATE TABLE `20MedSizeAccounts` (
  `A_Number` int(8) unsigned NOT NULL default '0',
  PRIMARY KEY  (`A_Number`),
  UNIQUE KEY `A_Number` (`A_Number`),
  CONSTRAINT `FK_20MedSizeAccounts` FOREIGN KEY (`A_Number`) REFERENCES `Account` (`A_Number`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `Access` */

DROP TABLE IF EXISTS `Access`;

CREATE TABLE `Access` (
  `Nic_Handle` varchar(12) NOT NULL default '',
  `NH_Password` tinytext,
  `NH_Level` int(4) NOT NULL default '0',
  `Answer` tinytext,
  PRIMARY KEY  (`Nic_Handle`),
  CONSTRAINT `FK_access` FOREIGN KEY (`Nic_Handle`) REFERENCES `NicHandle` (`Nic_Handle`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `AccessHhist` */

DROP TABLE IF EXISTS `AccessHhist`;

CREATE TABLE `AccessHhist` (
  `Nic_Handle` varchar(12) NOT NULL default '',
  `NH_Password` tinytext,
  `NH_Level` int(4) default NULL,
  `Answer` tinytext,
  `Chng_NH` varchar(12) NOT NULL default '',
  `Chng_Dt` datetime NOT NULL default '0000-00-00 00:00:00',
  PRIMARY KEY  (`Chng_Dt`,`Nic_Handle`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `Account` */

DROP TABLE IF EXISTS `Account`;

CREATE TABLE `Account` (
  `A_Number` int(8) unsigned zerofill NOT NULL auto_increment,
  `A_Name` varchar(30) NOT NULL default '',
  `Billing_NH` varchar(12) NOT NULL default '',
  `Address` tinytext NOT NULL,
  `County` tinytext NOT NULL,
  `Country` tinytext NOT NULL,
  `Web_Address` tinytext NOT NULL,
  `Phone` tinytext NOT NULL,
  `Fax` tinytext NOT NULL,
  `A_Status` varchar(10) default NULL,
  `A_Status_Dt` date NOT NULL default '0000-00-00',
  `A_Tariff` tinytext NOT NULL,
  `Credit_Limit` decimal(10,2) NOT NULL default '0.00',
  `A_Reg_Dt` date NOT NULL default '0000-00-00',
  `A_TStamp` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  `Invoice_Freq` varchar(10) NOT NULL default '',
  `Next_Invoice_Month` varchar(10) NOT NULL default '',
  `A_Remarks` tinytext NOT NULL,
  PRIMARY KEY  (`A_Number`),
  KEY `FK_account` (`Billing_NH`),
  CONSTRAINT `FK_account` FOREIGN KEY (`Billing_NH`) REFERENCES `NicHandle` (`Nic_Handle`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `AccountHist` */

DROP TABLE IF EXISTS `AccountHist`;

CREATE TABLE `AccountHist` (
  `A_Number` int(8) unsigned zerofill NOT NULL default '00000000',
  `A_Name` varchar(30) NOT NULL default '',
  `Billing_NH` varchar(12) NOT NULL default '',
  `Address` tinytext NOT NULL,
  `County` tinytext NOT NULL,
  `Country` tinytext NOT NULL,
  `Web_Address` tinytext NOT NULL,
  `Phone` tinytext NOT NULL,
  `Fax` tinytext NOT NULL,
  `A_Status` tinytext NOT NULL,
  `A_Status_Dt` date NOT NULL default '0000-00-00',
  `A_Tariff` tinytext NOT NULL,
  `Credit_Limit` decimal(10,2) NOT NULL default '0.00',
  `A_Reg_Dt` date NOT NULL default '0000-00-00',
  `A_TStamp` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  `Invoice_Freq` varchar(10) NOT NULL default '',
  `Next_Invoice_Month` varchar(10) NOT NULL default '',
  `A_Remarks` tinytext NOT NULL,
  `Chng_Dt` datetime NOT NULL default '0000-00-00 00:00:00',
  `Chng_NH` varchar(12) default NULL,
  PRIMARY KEY  (`A_Number`,`Chng_Dt`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `AdminC_Emails` */

DROP TABLE IF EXISTS `AdminC_Emails`;

CREATE TABLE `AdminC_Emails` (
  `Email_ID` int(10) unsigned NOT NULL auto_increment,
  `Email_Name` text NOT NULL,
  `Email_Content` text NOT NULL,
  PRIMARY KEY  (`Email_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `AllTicks` */

DROP TABLE IF EXISTS `AllTicks`;

CREATE TABLE `AllTicks` (
  `T_Date` datetime NOT NULL default '0000-00-00 00:00:00',
  `T_Number` varchar(10) NOT NULL default '',
  `Bill_NH` varchar(20) NOT NULL default '',
  `D_Name` varchar(66) NOT NULL default '',
  `D_Holder` tinytext NOT NULL,
  `T_Class` tinytext NOT NULL,
  `T_Category` tinytext NOT NULL,
  `Accept` char(1) NOT NULL default '',
  PRIMARY KEY  (`D_Name`,`T_Date`),
  KEY `FK_AllTicks` (`Bill_NH`),
  CONSTRAINT `FK_AllTicks` FOREIGN KEY (`Bill_NH`) REFERENCES `NicHandle` (`Nic_Handle`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `ApiPermissions` */

DROP TABLE IF EXISTS `ApiPermissions`;

CREATE TABLE `ApiPermissions` (
  `id` int(11) NOT NULL,
  `command` varchar(64) NOT NULL,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `command` (`command`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `Auth_Code` */

DROP TABLE IF EXISTS `Auth_Code`;

CREATE TABLE `Auth_Code` (
  `Nic_Handle` varchar(15) NOT NULL,
  `Request_Id` varchar(45) NOT NULL,
  `Auth_Code` char(6) NOT NULL,
  `Status` enum('W','A','E','N') NOT NULL default 'W',
  `Auth_TS` datetime NOT NULL default '0000-00-00 00:00:00',
  PRIMARY KEY  (`Request_Id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `BillStatus` */

DROP TABLE IF EXISTS `BillStatus`;

CREATE TABLE `BillStatus` (
  `Status` tinyint(2) unsigned NOT NULL default '0',
  `Description` char(1) NOT NULL default '',
  `DetailedDescription` tinytext,
  `Colour` varchar(12) default NULL,
  PRIMARY KEY  (`Status`),
  UNIQUE KEY `NewIndex1` (`Description`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `Category` */

DROP TABLE IF EXISTS `Category`;

CREATE TABLE `Category` (
  `id` int(11) NOT NULL,
  `name` varchar(128) NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `CategoryPermission` */

DROP TABLE IF EXISTS `CategoryPermission`;

CREATE TABLE `CategoryPermission` (
  `Nic_Handle` varchar(20) NOT NULL default '',
  `Category_Id` int(11) NOT NULL,
  PRIMARY KEY  (`Nic_Handle`,`Category_Id`),
  KEY `FK_categorypermission` (`Category_Id`),
  CONSTRAINT `FK_categorypermission` FOREIGN KEY (`Category_Id`) REFERENCES `Category` (`id`),
  CONSTRAINT `FK_CategoryPermission_2` FOREIGN KEY (`Nic_Handle`) REFERENCES `NicHandle` (`Nic_Handle`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `Class` */

DROP TABLE IF EXISTS `Class`;

CREATE TABLE `Class` (
  `id` int(11) NOT NULL,
  `name` varchar(128) NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `Class_To_Category` */

DROP TABLE IF EXISTS `Class_To_Category`;

CREATE TABLE `Class_To_Category` (
  `class_id` int(11) NOT NULL,
  `category_id` int(11) NOT NULL,
  PRIMARY KEY  (`class_id`,`category_id`),
  KEY `FK_category_class_to_category` (`category_id`),
  CONSTRAINT `FK_category_class_to_category` FOREIGN KEY (`category_id`) REFERENCES `Category` (`id`),
  CONSTRAINT `FK_class_class_to_category` FOREIGN KEY (`class_id`) REFERENCES `Class` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `ClikPaid` */

DROP TABLE IF EXISTS `ClikPaid`;

CREATE TABLE `ClikPaid` (
  `Status` tinyint(2) unsigned NOT NULL default '0',
  `Description` char(1) NOT NULL default '0',
  PRIMARY KEY  (`Status`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `ClikPayNotification` */

DROP TABLE IF EXISTS `ClikPayNotification`;

CREATE TABLE `ClikPayNotification` (
  `Transaction_Id` varchar(20) NOT NULL default '',
  `Date_Time` varchar(20) default '0000-00-00 00:00:00',
  `Approval_Code` varchar(10) default '0',
  `Description` varchar(100) default '0',
  `Transaction_Amount` decimal(10,0) default '0',
  `Transaction_Currency_Code` varchar(5) default '0',
  `Charged_Amount` decimal(10,0) default '0',
  `Charged_Currency_Code` varchar(5) default '0',
  `Exchange_Rate` decimal(10,0) default '0',
  `CardNumber` varchar(12) default '0',
  `Expiry_Date` varchar(5) default '0',
  PRIMARY KEY  (`Transaction_Id`),
  UNIQUE KEY `Transaction_Id` (`Transaction_Id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `Cond_Accept` */

DROP TABLE IF EXISTS `Cond_Accept`;

CREATE TABLE `Cond_Accept` (
  `D_Name` varchar(66) NOT NULL default '',
  `Add_Dt` date NOT NULL default '0000-00-00',
  `Comment` text,
  `Status` varchar(250) default NULL,
  PRIMARY KEY  (`D_Name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `Contact` */

DROP TABLE IF EXISTS `Contact`;

CREATE TABLE `Contact` (
  `D_Name` varchar(66) NOT NULL default '',
  `Contact_NH` varchar(12) NOT NULL default '',
  `Type` char(1) NOT NULL default '',
  PRIMARY KEY  (`D_Name`,`Contact_NH`,`Type`),
  KEY `idx_contact_contact_nh` (`Contact_NH`),
  CONSTRAINT `FK_contact` FOREIGN KEY (`D_Name`) REFERENCES `Domain` (`D_Name`),
  CONSTRAINT `FK_contact_NH` FOREIGN KEY (`Contact_NH`) REFERENCES `NicHandle` (`Nic_Handle`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `ContactHist` */

DROP TABLE IF EXISTS `ContactHist`;

CREATE TABLE `ContactHist` (
  `D_Name` varchar(66) NOT NULL default '',
  `Contact_NH` varchar(12) NOT NULL default '',
  `Type` char(1) NOT NULL default '',
  `Chng_NH` varchar(12) NOT NULL default '',
  `Chng_Dt` datetime NOT NULL default '0000-00-00 00:00:00',
  PRIMARY KEY  (`Chng_Dt`,`D_Name`,`Contact_NH`,`Type`),
  KEY `D_Name` (`D_Name`),
  KEY `Contact_NH` (`Contact_NH`),
  CONSTRAINT `FK_ContactHist` FOREIGN KEY (`Chng_Dt`, `D_Name`) REFERENCES `DomainHist` (`Chng_Dt`, `D_Name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `Counties` */

DROP TABLE IF EXISTS `Counties`;

CREATE TABLE `Counties` (
  `CountryCode` int(3) unsigned NOT NULL default '0',
  `County` varchar(100) NOT NULL default '',
  PRIMARY KEY  (`CountryCode`,`County`),
  UNIQUE KEY `CountryCode` (`CountryCode`,`County`),
  KEY `CountryCode_2` (`CountryCode`,`County`),
  CONSTRAINT `FK_counties` FOREIGN KEY (`CountryCode`) REFERENCES `Countries` (`CountryCode`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `Countries` */

DROP TABLE IF EXISTS `Countries`;

CREATE TABLE `Countries` (
  `CountryCode` int(2) unsigned NOT NULL auto_increment,
  `Country` char(100) default NULL,
  PRIMARY KEY  (`CountryCode`),
  UNIQUE KEY `CountryCode` (`CountryCode`),
  KEY `CountryCode_2` (`CountryCode`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `Cr_Notes` */

DROP TABLE IF EXISTS `Cr_Notes`;

CREATE TABLE `Cr_Notes` (
  `D_Name` varchar(66) NOT NULL default '',
  `Cr_Note_Dt` date default NULL,
  `Type` char(3) default NULL,
  PRIMARY KEY  (`D_Name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `Cr_Notes_Hist` */

DROP TABLE IF EXISTS `Cr_Notes_Hist`;

CREATE TABLE `Cr_Notes_Hist` (
  `D_Name` varchar(66) NOT NULL default '',
  `Cr_Note_Dt` date NOT NULL default '0000-00-00',
  `Type` char(3) NOT NULL default '',
  `Nic_Handle` varchar(15) NOT NULL default '',
  `C_Nt_Ts` timestamp NOT NULL default '0000-00-00 00:00:00'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `Cr_Notes_Old` */

DROP TABLE IF EXISTS `Cr_Notes_Old`;

CREATE TABLE `Cr_Notes_Old` (
  `D_Name` varchar(66) NOT NULL default '',
  `Cr_Note_Dt` date NOT NULL default '0000-00-00',
  `Type` char(3) NOT NULL default '',
  PRIMARY KEY  (`D_Name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `DNS` */

DROP TABLE IF EXISTS `DNS`;

CREATE TABLE `DNS` (
  `D_Name` char(66) NOT NULL default '',
  `DNS_Name` char(70) NOT NULL default '',
  `DNS_IpAddr` char(20) default NULL,
  `DNS_Order` tinyint(2) NOT NULL default '0',
  PRIMARY KEY  (`D_Name`,`DNS_Name`,`DNS_Order`),
  CONSTRAINT `FK_dns` FOREIGN KEY (`D_Name`) REFERENCES `Domain` (`D_Name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `DNSHist` */

DROP TABLE IF EXISTS `DNSHist`;

CREATE TABLE `DNSHist` (
  `D_Name` varchar(66) NOT NULL default '',
  `DNS_Name` varchar(70) NOT NULL default '',
  `DNS_IpAddr` varchar(20) NOT NULL default '',
  `DNS_Order` tinyint(2) NOT NULL default '0',
  `Chng_NH` varchar(12) NOT NULL default '',
  `Chng_Dt` datetime NOT NULL default '0000-00-00 00:00:00',
  PRIMARY KEY  (`D_Name`,`DNS_Name`,`Chng_Dt`),
  KEY `FK_DNSHist` (`Chng_Dt`,`D_Name`),
  CONSTRAINT `FK_DNSHist` FOREIGN KEY (`Chng_Dt`, `D_Name`) REFERENCES `DomainHist` (`Chng_Dt`, `D_Name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `DNSSearch_RateLim` */

DROP TABLE IF EXISTS `DNSSearch_RateLim`;

CREATE TABLE `DNSSearch_RateLim` (
  `Remote_IP` varchar(16) NOT NULL default '',
  `Query_TS` timestamp NOT NULL default '0000-00-00 00:00:00',
  `D_Name` varchar(66) NOT NULL default ''
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `D_Audit` */

DROP TABLE IF EXISTS `D_Audit`;

CREATE TABLE `D_Audit` (
  `D_Name` varchar(66) NOT NULL default '',
  `Op_Dt` datetime NOT NULL default '0000-00-00 00:00:00',
  `status` int(11) NOT NULL default '0',
  PRIMARY KEY  (`D_Name`,`Op_Dt`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `D_Locked` */

DROP TABLE IF EXISTS `D_Locked`;

CREATE TABLE `D_Locked` (
  `D_Name` varchar(66) NOT NULL default '',
  `Type` varchar(4) NOT NULL default '',
  `L_Date` datetime NOT NULL default '0000-00-00 00:00:00',
  PRIMARY KEY  (`D_Name`),
  CONSTRAINT `FK_d_locked` FOREIGN KEY (`D_Name`) REFERENCES `Domain` (`D_Name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `D_Locked_Hist` */

DROP TABLE IF EXISTS `D_Locked_Hist`;

CREATE TABLE `D_Locked_Hist` (
  `D_Name` varchar(66) NOT NULL default '',
  `Type` varchar(4) NOT NULL default '',
  `L_Date` datetime NOT NULL default '0000-00-00 00:00:00',
  `UL_Date` datetime NOT NULL default '0000-00-00 00:00:00',
  PRIMARY KEY  (`D_Name`,`L_Date`,`UL_Date`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `DavidReportRegByMonth` */

DROP TABLE IF EXISTS `DavidReportRegByMonth`;

CREATE TABLE `DavidReportRegByMonth` (
  `A_Number` int(8) unsigned NOT NULL default '0',
  PRIMARY KEY  (`A_Number`),
  UNIQUE KEY `A_Number` (`A_Number`),
  KEY `A_Number_2` (`A_Number`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `DeleteList` */

DROP TABLE IF EXISTS `DeleteList`;

CREATE TABLE `DeleteList` (
  `D_Name` varchar(66) NOT NULL default '',
  `D_Add_Dt` date default NULL,
  `D_Add_Id` varchar(12) default NULL,
  PRIMARY KEY  (`D_Name`),
  UNIQUE KEY `D_Name` (`D_Name`),
  KEY `D_Name_2` (`D_Name`),
  CONSTRAINT `FK_deletelist` FOREIGN KEY (`D_Name`) REFERENCES `Domain` (`D_Name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `DeleteListHist` */

DROP TABLE IF EXISTS `DeleteListHist`;

CREATE TABLE `DeleteListHist` (
  `D_Name` varchar(66) NOT NULL default '',
  `D_Add_Dt` date NOT NULL default '0000-00-00',
  `D_Add_Id` varchar(12) default NULL,
  PRIMARY KEY  (`D_Name`,`D_Add_Dt`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `DeletePublic` */

DROP TABLE IF EXISTS `DeletePublic`;

CREATE TABLE `DeletePublic` (
  `D_Name` varchar(66) NOT NULL default '',
  `Type` char(1) NOT NULL default '',
  PRIMARY KEY  (`D_Name`),
  KEY `D_Name` (`D_Name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `Deposit` */

DROP TABLE IF EXISTS `Deposit`;

CREATE TABLE `Deposit` (
  `Nic_Handle` varchar(20) NOT NULL default '',
  `Trans_Dt` datetime NOT NULL default '0000-00-00 00:00:00',
  `Open_Bal` decimal(10,2) NOT NULL default '0.00',
  `Close_Bal` decimal(10,2) NOT NULL default '0.00',
  PRIMARY KEY  (`Nic_Handle`),
  KEY `Nic_Handle` (`Nic_Handle`),
  KEY `Nic_Handle_2` (`Nic_Handle`),
  CONSTRAINT `FK_Deposit` FOREIGN KEY (`Nic_Handle`) REFERENCES `NicHandle` (`Nic_Handle`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `DepositHist` */

DROP TABLE IF EXISTS `DepositHist`;

CREATE TABLE `DepositHist` (
  `Nic_Handle` varchar(20) NOT NULL default '',
  `Op_Dt` datetime NOT NULL default '0000-00-00 00:00:00',
  `Trans_Dt` datetime NOT NULL default '0000-00-00 00:00:00',
  `Open_Bal` decimal(10,2) NOT NULL default '0.00',
  `Close_Bal` decimal(10,2) NOT NULL default '0.00'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `Domain` */

DROP TABLE IF EXISTS `Domain`;

CREATE TABLE `Domain` (
  `D_Name` varchar(66) NOT NULL default '',
  `D_Holder` tinytext NOT NULL,
  `D_Class` tinytext NOT NULL,
  `D_Category` tinytext NOT NULL,
  `A_Number` int(8) unsigned zerofill default NULL,
  `D_Status` tinytext NOT NULL,
  `D_Status_Dt` date NOT NULL default '0000-00-00',
  `D_Reg_Dt` date NOT NULL default '0000-00-00',
  `D_Ren_Dt` date NOT NULL default '0000-00-00',
  `D_TStamp` timestamp NOT NULL default '0000-00-00 00:00:00',
  `D_Discount` char(1) NOT NULL default '',
  `D_Bill_Status` char(1) NOT NULL default '',
  `D_Vat_Status` char(1) NOT NULL default '',
  `D_Remark` text,
  `D_ClikPaid` char(1) NOT NULL default 'N',
  `D_Class_Id` int(11) NOT NULL,
  `D_Category_Id` int(11) NOT NULL,
  PRIMARY KEY  (`D_Name`),
  KEY `A_Number` (`A_Number`),
  KEY `FK_domain_CC` (`D_Class_Id`,`D_Category_Id`),
  KEY `FK_domain_bs` (`D_Bill_Status`),
  CONSTRAINT `FK_domain` FOREIGN KEY (`A_Number`) REFERENCES `Account` (`A_Number`),
  CONSTRAINT `FK_domain_bs` FOREIGN KEY (`D_Bill_Status`) REFERENCES `BillStatus` (`Description`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `DomainHist` */

DROP TABLE IF EXISTS `DomainHist`;

CREATE TABLE `DomainHist` (
  `D_Name` varchar(66) NOT NULL default '',
  `D_Holder` tinytext NOT NULL,
  `D_Class` tinytext NOT NULL,
  `D_Category` tinytext NOT NULL,
  `A_Number` int(8) unsigned zerofill default NULL,
  `D_Status` tinytext NOT NULL,
  `D_Status_Dt` date NOT NULL default '0000-00-00',
  `D_Reg_Dt` date NOT NULL default '0000-00-00',
  `D_Ren_Dt` date NOT NULL default '0000-00-00',
  `D_TStamp` timestamp NOT NULL default '0000-00-00 00:00:00',
  `D_Discount` char(1) NOT NULL default '',
  `D_Bill_Status` char(1) NOT NULL default '',
  `D_Vat_Status` char(1) NOT NULL default '',
  `D_Remark` text,
  `Chng_NH` varchar(12) NOT NULL default '',
  `Chng_Dt` datetime NOT NULL default '0000-00-00 00:00:00',
  `D_ClikPaid` char(1) NOT NULL default 'N',
  PRIMARY KEY  (`Chng_Dt`,`D_Name`),
  KEY `D_Holder` USING BTREE (`D_Holder`(128)),
  KEY `D_Name` (`D_Name`),
  KEY `Chng_Dt` (`Chng_Dt`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `DomainStats` */

DROP TABLE IF EXISTS `DomainStats`;

CREATE TABLE `DomainStats` (
  `S_Number` int(6) NOT NULL auto_increment,
  `Month` varchar(14) NOT NULL default '',
  `Year` varchar(4) NOT NULL default '',
  `Delegated_Domains` varchar(8) NOT NULL default '',
  `Mail_Only` varchar(8) NOT NULL default '',
  `Total` varchar(8) NOT NULL default '',
  `Increase_Decrease` varchar(8) NOT NULL default '',
  `Working_Days` varchar(8) NOT NULL default '',
  `Registrations` varchar(8) NOT NULL default '',
  PRIMARY KEY  (`S_Number`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `Email` */

DROP TABLE IF EXISTS `Email`;

CREATE TABLE `Email` (
  `E_ID` int(3) unsigned NOT NULL auto_increment,
  `E_Name` text,
  `E_Text` text,
  `E_Subject` text,
  `E_To` text,
  `E_CC` text,
  `E_BCC` text,
  PRIMARY KEY  (`E_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `Geographical_Names` */

DROP TABLE IF EXISTS `Geographical_Names`;

CREATE TABLE `Geographical_Names` (
  `Place_Name` varchar(100) NOT NULL,
  `Type` varchar(3) NOT NULL,
  PRIMARY KEY  (`Place_Name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `GrossRecs` */

DROP TABLE IF EXISTS `GrossRecs`;

CREATE TABLE `GrossRecs` (
  `Nic_Handle` varchar(16) NOT NULL,
  `Type` varchar(8) NOT NULL,
  `GR_Date` datetime NOT NULL,
  `A_Number` int(8) NOT NULL,
  `D_Name` varchar(66) NOT NULL,
  `Holder` varchar(100) NOT NULL,
  `InvNo` varchar(20) default NULL,
  `RegDate` date NOT NULL,
  `RenDate` date default NULL,
  `Amount` decimal(10,2) default NULL,
  `VAT` decimal(10,2) default NULL,
  `Random` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `GuestReceipts` */

DROP TABLE IF EXISTS `GuestReceipts`;

CREATE TABLE `GuestReceipts` (
  `D_Name` varchar(66) NOT NULL default '',
  `Nic_Handle` varchar(20) NOT NULL default '',
  `Inv_No` varchar(20) NOT NULL default '',
  `Trans_Dt` datetime NOT NULL default '0000-00-00 00:00:00',
  `Order_ID` varchar(50) NOT NULL default '',
  `Remote_IP` varchar(35) NOT NULL default '',
  `VAT` decimal(10,2) NOT NULL default '0.00',
  `Amount` decimal(10,2) NOT NULL default '0.00',
  `Batch_Total` decimal(10,2) NOT NULL default '0.00',
  `Type` varchar(15) NOT NULL default '',
  `GR_Status` char(1) NOT NULL default '',
  PRIMARY KEY  (`D_Name`,`Trans_Dt`),
  KEY `FK_GuestReceipts` (`Nic_Handle`),
  CONSTRAINT `FK_GuestReceipts` FOREIGN KEY (`Nic_Handle`) REFERENCES `NicHandle` (`Nic_Handle`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `GuestReceiptsHist` */

DROP TABLE IF EXISTS `GuestReceiptsHist`;

CREATE TABLE `GuestReceiptsHist` (
  `D_Name` varchar(66) NOT NULL default '',
  `Nic_Handle` varchar(20) NOT NULL default '',
  `Inv_No` varchar(20) NOT NULL default '',
  `Trans_Dt` datetime NOT NULL default '0000-00-00 00:00:00',
  `Order_ID` varchar(50) NOT NULL default '',
  `Remote_IP` varchar(35) NOT NULL default '',
  `VAT` decimal(10,2) NOT NULL default '0.00',
  `Amount` decimal(10,2) NOT NULL default '0.00',
  `Batch_Total` decimal(10,2) NOT NULL default '0.00',
  `Type` varchar(15) NOT NULL default '',
  `GR_Status` char(1) NOT NULL default '',
  `Date` datetime NOT NULL default '0000-00-00 00:00:00',
  `ID` varchar(20) NOT NULL default '',
  PRIMARY KEY  (`D_Name`,`Trans_Dt`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `IE_Zone_DNS` */

DROP TABLE IF EXISTS `IE_Zone_DNS`;

CREATE TABLE `IE_Zone_DNS` (
  `D_Name` varchar(66) NOT NULL default '',
  `DNS_Name` varchar(200) NOT NULL default '',
  `IPv4_Addr` varchar(16) default NULL,
  `IPv6_Addr` varchar(40) default NULL,
  PRIMARY KEY  (`DNS_Name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `IE_Zone_SOA` */

DROP TABLE IF EXISTS `IE_Zone_SOA`;

CREATE TABLE `IE_Zone_SOA` (
  `D_Name` varchar(66) NOT NULL default '',
  `TTL` int(10) NOT NULL default '0',
  `MName` varchar(200) NOT NULL default '',
  `RName` varchar(200) NOT NULL default '',
  `Serial_Dt` date NOT NULL default '0000-00-00',
  `Serial_No` int(2) NOT NULL default '0',
  `Refresh` int(10) NOT NULL default '0',
  `Retry` int(10) NOT NULL default '0',
  `Expire` int(10) NOT NULL default '0',
  `Minim` int(10) NOT NULL default '0',
  PRIMARY KEY  (`MName`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `IPv6_Glue` */

DROP TABLE IF EXISTS `IPv6_Glue`;

CREATE TABLE `IPv6_Glue` (
  `DNS_Name` varchar(70) NOT NULL default '',
  `IPv6_Addr` varchar(39) NOT NULL default '',
  `Remarks` varchar(100) default NULL,
  PRIMARY KEY  (`DNS_Name`,`IPv6_Addr`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `Invoice` */

DROP TABLE IF EXISTS `Invoice`;

CREATE TABLE `Invoice` (
  `I_DName` varchar(66) NOT NULL default '',
  `I_Ren_Dt` date NOT NULL default '0000-00-00',
  `I_Amount` decimal(7,2) NOT NULL default '0.00',
  `I_Status` varchar(10) NOT NULL default '',
  `I_Stat_Dt` date NOT NULL default '0000-00-00',
  `I_NewReg` char(1) NOT NULL default 'N',
  `I_Create_Prc` char(1) NOT NULL default 'A',
  PRIMARY KEY  (`I_DName`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `InvoiceHist` */

DROP TABLE IF EXISTS `InvoiceHist`;

CREATE TABLE `InvoiceHist` (
  `I_Inv_No` varchar(10) NOT NULL default '0000000000',
  `I_DName` varchar(66) NOT NULL default '',
  `I_Ren_Dt` date NOT NULL default '0000-00-00',
  `I_Bill_NH` varchar(12) NOT NULL default '',
  `I_Acc_No` int(10) NOT NULL default '0',
  `I_DHolder` tinytext NOT NULL,
  `I_Discount` char(1) NOT NULL default 'N',
  `I_Amount` decimal(7,2) NOT NULL default '0.00',
  `I_Status` varchar(10) NOT NULL default '',
  `I_Reg_Dt` date NOT NULL default '0000-00-00',
  `I_NewReg` char(1) NOT NULL default 'N',
  `I_Create_prc` varchar(20) NOT NULL default '',
  `I_Inv_Dt` date NOT NULL default '0000-00-00',
  `I_Inv_NH` varchar(12) default NULL,
  `I_Inv_Vat` char(1) default 'Y',
  `I_Inv_ClikPaid` char(1) default 'N',
  `I_Inv_Vat_Amt` decimal(7,2) default '2.00',
  `I_Inv_Type` char(3) NOT NULL default '',
  PRIMARY KEY  (`I_Inv_No`,`I_DName`,`I_Inv_Dt`,`I_Inv_Type`),
  KEY `idx_bill_invoicehist` (`I_Bill_NH`),
  KEY `I_DName` (`I_DName`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `Levels_To_ApiPermissions` */

DROP TABLE IF EXISTS `Levels_To_ApiPermissions`;

CREATE TABLE `Levels_To_ApiPermissions` (
  `NH_Level` int(4) NOT NULL,
  `permission_id` int(11) NOT NULL,
  UNIQUE KEY `NH_Level` (`NH_Level`,`permission_id`),
  KEY `c_lev2perm_perm` (`permission_id`),
  CONSTRAINT `FK_Levels_To_ApiPermissions` FOREIGN KEY (`permission_id`) REFERENCES `ApiPermissions` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `MailList` */

DROP TABLE IF EXISTS `MailList`;

CREATE TABLE `MailList` (
  `D_Name` varchar(66) NOT NULL default '',
  `M_Add_Dt` date default NULL,
  `M_Add_Id` varchar(12) default NULL,
  `M_Type` char(1) NOT NULL default 'I',
  PRIMARY KEY  (`D_Name`),
  UNIQUE KEY `D_Name` (`D_Name`),
  KEY `D_Name_2` (`D_Name`),
  CONSTRAINT `FK_MailList` FOREIGN KEY (`D_Name`) REFERENCES `Domain` (`D_Name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `NicHandle` */

DROP TABLE IF EXISTS `NicHandle`;

CREATE TABLE `NicHandle` (
  `Nic_Handle` varchar(12) NOT NULL default '',
  `NH_Name` tinytext NOT NULL,
  `A_Number` int(8) unsigned zerofill default NULL,
  `Co_Name` tinytext NOT NULL,
  `NH_Address` tinytext NOT NULL,
  `NH_County` tinytext,
  `NH_Country` tinytext,
  `NH_Email` tinytext NOT NULL,
  `NH_Status` tinytext NOT NULL,
  `NH_Status_Dt` date NOT NULL default '0000-00-00',
  `NH_Reg_Dt` date NOT NULL default '0000-00-00',
  `NH_TStamp` timestamp NOT NULL default '0000-00-00 00:00:00',
  `NH_BillC_Ind` char(1) NOT NULL default '',
  `NH_Remark` tinytext,
  `NH_Creator` varchar(12) default NULL,
  PRIMARY KEY  (`Nic_Handle`),
  KEY `FK_nichandle` (`A_Number`),
  CONSTRAINT `FK_nichandle` FOREIGN KEY (`A_Number`) REFERENCES `Account` (`A_Number`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `NicHandleFailures` */

DROP TABLE IF EXISTS `NicHandleFailures`;

CREATE TABLE `NicHandleFailures` (
  `Nic_Handle` varchar(12) NOT NULL default '',
  `NH_Name_Fail_Cd` tinyint(3) default NULL,
  `NH_Address_Fail_Cd` tinyint(3) default NULL,
  `NH_Email_Fail_Cd` tinyint(3) default NULL,
  `Remark` tinytext,
  `Chng_NH` varchar(12) NOT NULL default '',
  `Chng_Dt` datetime NOT NULL default '0000-00-00 00:00:00',
  `NH_Phone_Fail_Cd` tinyint(3) default NULL,
  `NH_Fax_Fail_Cd` tinyint(3) default NULL,
  `NH_Company_Fail_Cd` tinyint(3) unsigned default NULL,
  PRIMARY KEY  (`Nic_Handle`,`Chng_Dt`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `NicHandleHist` */

DROP TABLE IF EXISTS `NicHandleHist`;

CREATE TABLE `NicHandleHist` (
  `Nic_Handle` varchar(12) NOT NULL default '',
  `NH_Name` tinytext NOT NULL,
  `A_Number` int(8) unsigned zerofill default NULL,
  `Co_Name` tinytext NOT NULL,
  `NH_Address` tinytext NOT NULL,
  `NH_County` tinytext,
  `NH_Country` tinytext,
  `NH_Email` tinytext NOT NULL,
  `NH_Status` tinytext NOT NULL,
  `NH_Status_Dt` date NOT NULL default '0000-00-00',
  `NH_Reg_Dt` date NOT NULL default '0000-00-00',
  `NH_TStamp` timestamp NOT NULL default '0000-00-00 00:00:00',
  `NH_BillC_Ind` char(1) NOT NULL default '',
  `NH_Remark` tinytext,
  `Chng_NH` varchar(12) NOT NULL default '',
  `Chng_Dt` timestamp NOT NULL default '0000-00-00 00:00:00',
  `NH_Creator` varchar(12) default NULL,
  PRIMARY KEY  (`Chng_Dt`,`Nic_Handle`),
  KEY `Nic_Handle` (`Nic_Handle`),
  KEY `Chng_Dt` (`Chng_Dt`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `NicHandleSeq` */

DROP TABLE IF EXISTS `NicHandleSeq`;

CREATE TABLE `NicHandleSeq` (
  `id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `Payment` */

DROP TABLE IF EXISTS `Payment`;

CREATE TABLE `Payment` (
  `Billing_Contact` varchar(12) NOT NULL default '',
  `Credit_Card_Type` tinytext NOT NULL,
  `Name_on_Card` tinytext NOT NULL,
  `Card_Number` tinytext NOT NULL,
  `Expiry_Date` varchar(5) NOT NULL default '',
  `Bank_Account_Holder` tinytext NOT NULL,
  `Bank` tinytext NOT NULL,
  `NSC` tinytext NOT NULL,
  `AC_Number` tinytext NOT NULL,
  `Address` tinytext NOT NULL,
  `VAT_Reg_ID` tinytext NOT NULL,
  `VAT_exempt` tinytext NOT NULL,
  PRIMARY KEY  (`Billing_Contact`),
  CONSTRAINT `FK_payment` FOREIGN KEY (`Billing_Contact`) REFERENCES `NicHandle` (`Nic_Handle`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `PaymentHist` */

DROP TABLE IF EXISTS `PaymentHist`;

CREATE TABLE `PaymentHist` (
  `Billing_Contact` varchar(12) NOT NULL default '',
  `Credit_Card_Type` tinytext NOT NULL,
  `Name_on_Card` tinytext NOT NULL,
  `Card_Number` tinytext NOT NULL,
  `Expiry_Date` varchar(5) NOT NULL default '',
  `Bank_Account_Holder` tinytext NOT NULL,
  `Bank` tinytext NOT NULL,
  `NSC` tinytext NOT NULL,
  `AC_Number` tinytext NOT NULL,
  `Address` tinytext NOT NULL,
  `VAT_Reg_ID` tinytext NOT NULL,
  `VAT_exempt` tinytext NOT NULL,
  `Chng_NH` varchar(12) NOT NULL default '',
  `Chng_Dt` datetime NOT NULL default '0000-00-00 00:00:00',
  PRIMARY KEY  (`Chng_Dt`,`Billing_Contact`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `PendingMailList` */

DROP TABLE IF EXISTS `PendingMailList`;

CREATE TABLE `PendingMailList` (
  `D_Name` varchar(66) NOT NULL default '',
  `M_Add_Dt` date NOT NULL default '0000-00-00',
  `M_Add_Id` varchar(12) NOT NULL default '',
  `M_Type` char(1) NOT NULL default '',
  `Type` char(1) NOT NULL default '0',
  PRIMARY KEY  (`D_Name`,`Type`),
  CONSTRAINT `FK_PendingMailList` FOREIGN KEY (`D_Name`) REFERENCES `Domain` (`D_Name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `PendingPayment` */

DROP TABLE IF EXISTS `PendingPayment`;

CREATE TABLE `PendingPayment` (
  `D_Name` varchar(66) NOT NULL default '',
  `Nic_Handle` varchar(20) NOT NULL default '',
  `Inv_No` varchar(20) NOT NULL default '',
  `Trans_Dt` datetime NOT NULL default '0000-00-00 00:00:00',
  `Sess_ID` varchar(50) NOT NULL default '',
  `Remote_IP` varchar(35) NOT NULL default '',
  `VAT` decimal(10,2) NOT NULL default '0.00',
  `Amount` decimal(10,2) NOT NULL default '0.00',
  `Batch_Total` decimal(10,2) NOT NULL default '0.00',
  PRIMARY KEY  (`D_Name`,`Trans_Dt`),
  KEY `DName_Only` (`D_Name`),
  KEY `FK_pendingpayment_2` (`Nic_Handle`),
  CONSTRAINT `FK_pendingpayment` FOREIGN KEY (`D_Name`) REFERENCES `Domain` (`D_Name`),
  CONSTRAINT `FK_pendingpayment_2` FOREIGN KEY (`Nic_Handle`) REFERENCES `NicHandle` (`Nic_Handle`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `PendingPaymentHist` */

DROP TABLE IF EXISTS `PendingPaymentHist`;

CREATE TABLE `PendingPaymentHist` (
  `D_Name` varchar(66) NOT NULL default '',
  `Nic_Handle` varchar(20) NOT NULL default '',
  `Inv_No` varchar(20) NOT NULL default '',
  `Trans_Dt` datetime NOT NULL default '0000-00-00 00:00:00',
  `Sess_ID` varchar(50) NOT NULL default '',
  `Remote_IP` varchar(35) NOT NULL default '',
  `VAT` decimal(10,2) NOT NULL default '0.00',
  `Amount` decimal(10,2) NOT NULL default '0.00',
  `Batch_Total` decimal(10,2) NOT NULL default '0.00',
  `Date` timestamp NOT NULL default '0000-00-00 00:00:00',
  `ID` varchar(20) NOT NULL default ''
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `PendingSuspension` */

DROP TABLE IF EXISTS `PendingSuspension`;

CREATE TABLE `PendingSuspension` (
  `D_Name` varchar(66) NOT NULL default '',
  `Nic_Handle` varchar(15) NOT NULL default '',
  `Date` datetime NOT NULL default '0000-00-00 00:00:00',
  `Sess_Id` varchar(50) NOT NULL default '',
  `Remote_Ip` varchar(35) NOT NULL default '',
  `Type` char(1) NOT NULL default '',
  PRIMARY KEY  (`D_Name`,`Date`,`Type`),
  KEY `FK_pendingsuspension_2` (`Nic_Handle`),
  CONSTRAINT `FK_pendingsuspension` FOREIGN KEY (`D_Name`) REFERENCES `Domain` (`D_Name`),
  CONSTRAINT `FK_pendingsuspension_2` FOREIGN KEY (`Nic_Handle`) REFERENCES `NicHandle` (`Nic_Handle`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `PendingSuspensionHist` */

DROP TABLE IF EXISTS `PendingSuspensionHist`;

CREATE TABLE `PendingSuspensionHist` (
  `D_Name` varchar(66) NOT NULL default '',
  `Nic_Handle` varchar(20) NOT NULL default '',
  `Date` date NOT NULL default '0000-00-00',
  `Sess_ID` varchar(50) NOT NULL default '',
  `Remote_IP` varchar(35) NOT NULL default '',
  `ID` varchar(20) NOT NULL default '',
  PRIMARY KEY  (`D_Name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `PendingTicket` */

DROP TABLE IF EXISTS `PendingTicket`;

CREATE TABLE `PendingTicket` (
  `T_Number` int(10) NOT NULL auto_increment,
  `T_Type` char(1) NOT NULL default '',
  `D_Name` varchar(66) NOT NULL default '',
  `DN_Fail_Cd` tinyint(3) default NULL,
  `D_Holder` tinytext NOT NULL,
  `DH_Fail_Cd` tinyint(3) default NULL,
  `A_Number` int(8) unsigned zerofill NOT NULL default '00000000',
  `AC_Fail_Cd` tinyint(3) default NULL,
  `T_Class` tinytext NOT NULL,
  `T_Category` tinytext NOT NULL,
  `T_Remark` text,
  `Admin_NH1` varchar(12) NOT NULL default '',
  `ANH1_Fail_Cd` tinyint(3) default NULL,
  `Admin_NH2` varchar(12) default NULL,
  `ANH2_Fail_Cd` tinyint(3) default NULL,
  `Tech_NH` varchar(12) NOT NULL default '',
  `TNH_Fail_Cd` tinyint(3) default NULL,
  `Bill_NH` varchar(12) NOT NULL default '',
  `BNH_Fail_Cd` tinyint(3) default NULL,
  `Creator_NH` varchar(12) NOT NULL default '',
  `DNS_Name1` tinytext NOT NULL,
  `DNS1_Fail_Cd` tinyint(3) default NULL,
  `DNS_IP1` tinytext,
  `DNSIP1_Fail_Cd` tinyint(3) default NULL,
  `DNS_Name2` tinytext NOT NULL,
  `DNS2_Fail_Cd` tinyint(3) default NULL,
  `DNS_IP2` tinytext,
  `DNSIP2_Fail_Cd` tinyint(3) default NULL,
  `DNS_Name3` tinytext,
  `DNS3_Fail_Cd` tinyint(3) default NULL,
  `DNS_IP3` tinytext,
  `DNSIP3_Fail_Cd` tinyint(3) default NULL,
  `Admin_Status` tinyint(2) unsigned NOT NULL default '0',
  `Ad_StatusDt` date NOT NULL default '0000-00-00',
  `Tech_Status` tinyint(2) unsigned NOT NULL default '0',
  `T_Status_Dt` date NOT NULL default '0000-00-00',
  `CheckedOut` char(1) NOT NULL default 'N',
  `CheckedOutTo` varchar(12) default NULL,
  `T_Reg_Dt` date default NULL,
  `T_Ren_Dt` date default NULL,
  `T_TStamp` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  `H_Remark` text,
  `T_Class_Fail_Cd` tinyint(3) default NULL,
  `T_Category_Fail_Cd` tinyint(3) default NULL,
  `T_Created_TS` datetime NOT NULL default '0000-00-00 00:00:00',
  `Opt_Cert` char(1) default 'N',
  `T_ClikPaid` char(1) default 'N',
  `Fee` float default '0',
  `VAT` char(1) default 'Y',
  `Session_Id` tinytext,
  `Date_Time` varchar(20) default NULL,
  `Approval_Code` varchar(10) default NULL,
  `Description` tinytext,
  `Transaction_Amount` varchar(10) default NULL,
  `Transaction_Currency_Code` varchar(10) default NULL,
  `Charged_Amount` varchar(10) default NULL,
  `Charged_Currency_Code` varchar(10) default NULL,
  `Exchange_Rate` varchar(10) default NULL,
  `CardNumber` varchar(18) default NULL,
  `Expiry_Date` varchar(4) default NULL,
  `Transaction_Id` varchar(20) default NULL,
  PRIMARY KEY  (`T_Number`),
  UNIQUE KEY `Number` (`T_Number`),
  KEY `D_Name` (`D_Name`),
  KEY `FK_pendingticket_ac_fail` (`AC_Fail_Cd`),
  KEY `FK_pendingticket_ANH1_Fail_Cd` (`ANH1_Fail_Cd`),
  KEY `FK_pendingticket_ANH1` (`Admin_NH1`),
  KEY `FK_pendingticket_A_Number` (`A_Number`),
  KEY `FK_pendingticket_ANH2` (`Admin_NH2`),
  KEY `FK_pendingticket_ANH2_FC` (`ANH2_Fail_Cd`),
  KEY `FK_pendingticket_BNH` (`Bill_NH`),
  KEY `FK_pendingticket_BNH_FC` (`BNH_Fail_Cd`),
  KEY `FK_pendingticket_DH_FC` (`DH_Fail_Cd`),
  KEY `FK_pendingticket_DN_FC` (`DN_Fail_Cd`),
  KEY `FK_pendingticket_DNS1_FC` (`DNS1_Fail_Cd`),
  KEY `FK_pendingticket_DNS2_FC` (`DNS2_Fail_Cd`),
  KEY `FK_pendingticket_DNS3_FC` (`DNS3_Fail_Cd`),
  KEY `FK_pendingticket_DNSIP1_FC` (`DNSIP1_Fail_Cd`),
  KEY `FK_pendingticket_DNSIP2_FC` (`DNSIP2_Fail_Cd`),
  KEY `FK_pendingticket_DNSIP3_FC` (`DNSIP3_Fail_Cd`),
  KEY `FK_pendingticket` (`T_Category_Fail_Cd`),
  KEY `FK_pendingticket_Class_FC` (`T_Class_Fail_Cd`),
  KEY `FK_pendingticket_TNH` (`Tech_NH`),
  KEY `FK_pendingticket_TNH_FC` (`TNH_Fail_Cd`),
  KEY `FK_pendingticket_AS` (`Admin_Status`),
  KEY `FK_pendingticket_TS` (`Tech_Status`),
  CONSTRAINT `FK_pendingticket` FOREIGN KEY (`T_Category_Fail_Cd`) REFERENCES `TicketFailCd` (`FailCd`),
  CONSTRAINT `FK_pendingticket_ac_fail` FOREIGN KEY (`AC_Fail_Cd`) REFERENCES `TicketFailCd` (`FailCd`),
  CONSTRAINT `FK_pendingticket_ANH1` FOREIGN KEY (`Admin_NH1`) REFERENCES `NicHandle` (`Nic_Handle`),
  CONSTRAINT `FK_pendingticket_ANH1_Fail_Cd` FOREIGN KEY (`ANH1_Fail_Cd`) REFERENCES `TicketFailCd` (`FailCd`),
  CONSTRAINT `FK_pendingticket_ANH2` FOREIGN KEY (`Admin_NH2`) REFERENCES `NicHandle` (`Nic_Handle`),
  CONSTRAINT `FK_pendingticket_ANH2_FC` FOREIGN KEY (`ANH2_Fail_Cd`) REFERENCES `TicketFailCd` (`FailCd`),
  CONSTRAINT `FK_pendingticket_AS` FOREIGN KEY (`Admin_Status`) REFERENCES `TicketAdminStatus` (`Status`),
  CONSTRAINT `FK_pendingticket_A_Number` FOREIGN KEY (`A_Number`) REFERENCES `Account` (`A_Number`),
  CONSTRAINT `FK_pendingticket_BNH` FOREIGN KEY (`Bill_NH`) REFERENCES `NicHandle` (`Nic_Handle`),
  CONSTRAINT `FK_pendingticket_BNH_FC` FOREIGN KEY (`BNH_Fail_Cd`) REFERENCES `TicketFailCd` (`FailCd`),
  CONSTRAINT `FK_pendingticket_Class_FC` FOREIGN KEY (`T_Class_Fail_Cd`) REFERENCES `TicketFailCd` (`FailCd`),
  CONSTRAINT `FK_pendingticket_DH_FC` FOREIGN KEY (`DH_Fail_Cd`) REFERENCES `TicketFailCd` (`FailCd`),
  CONSTRAINT `FK_pendingticket_DNS1_FC` FOREIGN KEY (`DNS1_Fail_Cd`) REFERENCES `TicketFailCd` (`FailCd`),
  CONSTRAINT `FK_pendingticket_DNS2_FC` FOREIGN KEY (`DNS2_Fail_Cd`) REFERENCES `TicketFailCd` (`FailCd`),
  CONSTRAINT `FK_pendingticket_DNS3_FC` FOREIGN KEY (`DNS3_Fail_Cd`) REFERENCES `TicketFailCd` (`FailCd`),
  CONSTRAINT `FK_pendingticket_DNSIP1_FC` FOREIGN KEY (`DNSIP1_Fail_Cd`) REFERENCES `TicketFailCd` (`FailCd`),
  CONSTRAINT `FK_pendingticket_DNSIP2_FC` FOREIGN KEY (`DNSIP2_Fail_Cd`) REFERENCES `TicketFailCd` (`FailCd`),
  CONSTRAINT `FK_pendingticket_DNSIP3_FC` FOREIGN KEY (`DNSIP3_Fail_Cd`) REFERENCES `TicketFailCd` (`FailCd`),
  CONSTRAINT `FK_pendingticket_DN_FC` FOREIGN KEY (`DN_Fail_Cd`) REFERENCES `TicketFailCd` (`FailCd`),
  CONSTRAINT `FK_pendingticket_TNH` FOREIGN KEY (`Tech_NH`) REFERENCES `NicHandle` (`Nic_Handle`),
  CONSTRAINT `FK_pendingticket_TNH_FC` FOREIGN KEY (`TNH_Fail_Cd`) REFERENCES `TicketFailCd` (`FailCd`),
  CONSTRAINT `FK_pendingticket_TS` FOREIGN KEY (`Tech_Status`) REFERENCES `TicketTechStatus` (`Status`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `PersistedCommands` */

DROP TABLE IF EXISTS `PersistedCommands`;

CREATE TABLE `PersistedCommands` (
  `cId` bigint(20) NOT NULL auto_increment,
  `P_CreateDate` timestamp NOT NULL default CURRENT_TIMESTAMP,
  `P_NicHandle` varchar(12) NOT NULL,
  `P_RequestHash` varchar(32) NOT NULL,
  `P_Response` text,
  PRIMARY KEY  (`cId`),
  UNIQUE KEY `cId` (`cId`),
  KEY `persistedCommandsIdx` (`P_CreateDate`,`P_NicHandle`,`P_RequestHash`),
  KEY `FK_PersistedCommands` (`P_NicHandle`),
  CONSTRAINT `FK_PersistedCommands` FOREIGN KEY (`P_NicHandle`) REFERENCES `NicHandle` (`Nic_Handle`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `Product` */

DROP TABLE IF EXISTS `Product`;

CREATE TABLE `Product` (
  `P_Code` varchar(16) NOT NULL default '',
  `P_Short_Desc` varchar(50) NOT NULL default '',
  `P_Long_Desc` varchar(255) default NULL,
  `P_Price` decimal(3,0) NOT NULL default '0',
  `P_Duration` float NOT NULL default '0',
  `P_Active` tinyint(3) unsigned NOT NULL default '1',
  `P_Default` tinyint(3) unsigned NOT NULL default '0',
  `P_Valid_From` date default NULL,
  `P_Valid_To` date default NULL,
  `P_Reg` tinyint(3) unsigned NOT NULL default '1',
  `P_Ren` tinyint(3) unsigned NOT NULL default '1',
  `P_Guest` tinyint(3) unsigned NOT NULL default '1',
  `P_Disp_Order` smallint(5) unsigned NOT NULL default '0',
  PRIMARY KEY  (`P_Code`),
  UNIQUE KEY `P_Code` (`P_Code`),
  KEY `P_Code_2` (`P_Code`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `RCInvoiceHist` */

DROP TABLE IF EXISTS `RCInvoiceHist`;

CREATE TABLE `RCInvoiceHist` (
  `I_Inv_No` varchar(10) NOT NULL default '',
  `I_DName` varchar(66) NOT NULL default '',
  `I_Ren_Dt` date NOT NULL default '0000-00-00',
  `I_Bill_NH` varchar(12) NOT NULL default '',
  `I_DHolder` tinytext NOT NULL,
  `I_Type` varchar(10) NOT NULL default '',
  `I_Amount` decimal(7,2) NOT NULL default '0.00',
  `I_Status` char(1) NOT NULL default '',
  `I_Reg_Dt` date NOT NULL default '0000-00-00',
  `I_Inv_Dt` date NOT NULL default '0000-00-00',
  `I_Inv_NH` varchar(12) NOT NULL default '',
  `I_Inv_Vat` char(1) NOT NULL default '',
  `I_Batch_Total` decimal(7,2) NOT NULL default '0.00',
  PRIMARY KEY  (`I_Inv_No`,`I_Inv_Dt`,`I_DName`),
  KEY `I_DName` (`I_DName`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `RcptBatch` */

DROP TABLE IF EXISTS `RcptBatch`;

CREATE TABLE `RcptBatch` (
  `Type` char(10) NOT NULL default '',
  `Start` datetime NOT NULL default '0000-00-00 00:00:00',
  `Finish` datetime NOT NULL default '0000-00-00 00:00:00',
  `Batch_No` char(5) NOT NULL default '',
  PRIMARY KEY  (`Type`,`Start`,`Finish`,`Batch_No`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `RealVoid` */

DROP TABLE IF EXISTS `RealVoid`;

CREATE TABLE `RealVoid` (
  `R_Order_ID` varchar(50) NOT NULL default '',
  `R_AUTHCODE` varchar(15) NOT NULL default '',
  `R_PASREF` varchar(25) NOT NULL default '',
  `Trans_Dt` datetime NOT NULL default '0000-00-00 00:00:00',
  PRIMARY KEY  (`R_Order_ID`,`Trans_Dt`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='Codes for Realex Voids/Rebates';

/*Table structure for table `Receipts` */

DROP TABLE IF EXISTS `Receipts`;

CREATE TABLE `Receipts` (
  `D_Name` varchar(66) NOT NULL default '',
  `Nic_Handle` varchar(20) NOT NULL default '',
  `Inv_No` varchar(20) NOT NULL default '',
  `Trans_Dt` datetime NOT NULL default '0000-00-00 00:00:00',
  `Order_ID` varchar(50) NOT NULL default '',
  `Remote_IP` varchar(35) NOT NULL default '',
  `VAT` decimal(10,2) NOT NULL default '0.00',
  `Amount` decimal(10,2) NOT NULL default '0.00',
  `Batch_Total` decimal(10,2) NOT NULL default '0.00',
  KEY `FK_receipts` (`Nic_Handle`),
  CONSTRAINT `FK_receipts` FOREIGN KEY (`Nic_Handle`) REFERENCES `NicHandle` (`Nic_Handle`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `ReceiptsHist` */

DROP TABLE IF EXISTS `ReceiptsHist`;

CREATE TABLE `ReceiptsHist` (
  `D_Name` varchar(66) NOT NULL default '',
  `Nic_Handle` varchar(20) NOT NULL default '',
  `Inv_No` varchar(20) NOT NULL default '',
  `Trans_Dt` datetime NOT NULL default '0000-00-00 00:00:00',
  `Order_ID` varchar(50) NOT NULL default '',
  `Remote_IP` varchar(35) NOT NULL default '',
  `VAT` decimal(10,2) NOT NULL default '0.00',
  `Amount` decimal(10,2) NOT NULL default '0.00',
  `Batch_Total` decimal(10,2) NOT NULL default '0.00',
  `Date` date NOT NULL default '0000-00-00',
  `ID` varchar(20) NOT NULL default '',
  PRIMARY KEY  (`D_Name`,`Trans_Dt`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `Reg_Accuracy` */

DROP TABLE IF EXISTS `Reg_Accuracy`;

CREATE TABLE `Reg_Accuracy` (
  `T_Date` datetime NOT NULL default '0000-00-00 00:00:00',
  `Bill_NH` varchar(20) NOT NULL default '',
  `D_Name` varchar(66) NOT NULL default '',
  `D_Holder` tinytext NOT NULL,
  `T_Class` tinytext NOT NULL,
  `T_Category` tinytext NOT NULL,
  `Accept` char(1) NOT NULL default '',
  KEY `FK_reg_accuracy` (`Bill_NH`),
  CONSTRAINT `FK_reg_accuracy` FOREIGN KEY (`Bill_NH`) REFERENCES `NicHandle` (`Nic_Handle`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `RenDates` */

DROP TABLE IF EXISTS `RenDates`;

CREATE TABLE `RenDates` (
  `D_Name` varchar(66) NOT NULL,
  `D_Ren_Dt` date NOT NULL,
  KEY `FK_rendates` (`D_Name`),
  CONSTRAINT `FK_rendates` FOREIGN KEY (`D_Name`) REFERENCES `Domain` (`D_Name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `Report` */

DROP TABLE IF EXISTS `Report`;

CREATE TABLE `Report` (
  `R_ID` varchar(10) NOT NULL default '',
  `R_Description` varchar(66) NOT NULL default '',
  `R_Order` tinyint(3) unsigned NOT NULL default '0',
  `R_File_Name` varchar(30) NOT NULL default '',
  `R_Uses_Date` char(1) NOT NULL default 'N',
  `R_Authority` int(4) NOT NULL default '16777215',
  PRIMARY KEY  (`R_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `Reseller_Defaults` */

DROP TABLE IF EXISTS `Reseller_Defaults`;

CREATE TABLE `Reseller_Defaults` (
  `Nic_Handle` varchar(16) NOT NULL default '',
  `Tech_C` varchar(15) NOT NULL default '',
  `N_Server1` varchar(70) NOT NULL default '',
  `N_Server2` varchar(70) NOT NULL default '',
  `N_Server3` varchar(70) NOT NULL default '',
  KEY `FK_reseller_defaults` (`Nic_Handle`),
  KEY `FK_reseller_defaults_2` (`Tech_C`),
  CONSTRAINT `FK_reseller_defaults` FOREIGN KEY (`Nic_Handle`) REFERENCES `NicHandle` (`Nic_Handle`),
  CONSTRAINT `FK_reseller_defaults_2` FOREIGN KEY (`Tech_C`) REFERENCES `NicHandle` (`Nic_Handle`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `ResetPass` */

DROP TABLE IF EXISTS `ResetPass`;

CREATE TABLE `ResetPass` (
  `Nic_Handle` varchar(16) NOT NULL default '',
  `Unique_ID` varchar(30) NOT NULL,
  `Time_Stamp` datetime NOT NULL default '0000-00-00 00:00:00',
  `Remote_IP` varchar(20) NOT NULL default '',
  PRIMARY KEY  (`Nic_Handle`,`Unique_ID`),
  CONSTRAINT `FK_resetpass` FOREIGN KEY (`Nic_Handle`) REFERENCES `NicHandle` (`Nic_Handle`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `SNAPSHOT_OF_DNS` */

DROP TABLE IF EXISTS `SNAPSHOT_OF_DNS`;

CREATE TABLE `SNAPSHOT_OF_DNS` (
  `D_Name` char(66) NOT NULL default '',
  `DNS_Name` char(70) NOT NULL default '',
  `DNS_IpAddr` char(20) default NULL,
  `DNS_Order` tinyint(2) NOT NULL default '0'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `STPAccount` */

DROP TABLE IF EXISTS `STPAccount`;

CREATE TABLE `STPAccount` (
  `Nic_Handle` varchar(12) NOT NULL default '',
  `A_Number` mediumint(8) unsigned zerofill default '00000000',
  PRIMARY KEY  (`Nic_Handle`),
  UNIQUE KEY `Nic_Handle` (`Nic_Handle`),
  KEY `Nic_Handle_2` (`Nic_Handle`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `Soc_Votes` */

DROP TABLE IF EXISTS `Soc_Votes`;

CREATE TABLE `Soc_Votes` (
  `Nic_Handle` varchar(16) NOT NULL default '',
  `Week` varchar(1) NOT NULL default '',
  `Month` varchar(12) NOT NULL default '',
  `Choice` varchar(50) NOT NULL default '',
  `Password` tinytext NOT NULL,
  PRIMARY KEY  (`Nic_Handle`,`Week`,`Month`,`Choice`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `SpecialList` */

DROP TABLE IF EXISTS `SpecialList`;

CREATE TABLE `SpecialList` (
  `D_Name` varchar(66) NOT NULL default '',
  `S_Add_Dt` date NOT NULL default '0000-00-00',
  `S_Add_NH` varchar(12) NOT NULL default '',
  PRIMARY KEY  (`D_Name`),
  UNIQUE KEY `D_Name` (`D_Name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `SpecialListText` */

DROP TABLE IF EXISTS `SpecialListText`;

CREATE TABLE `SpecialListText` (
  `LineNo` tinyint(3) NOT NULL auto_increment,
  `TextLine` varchar(80) NOT NULL default '',
  PRIMARY KEY  (`LineNo`),
  UNIQUE KEY `LineNo` (`LineNo`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `Statement` */

DROP TABLE IF EXISTS `Statement`;

CREATE TABLE `Statement` (
  `Nic_Handle` varchar(15) NOT NULL default '',
  `Pay_Terms` varchar(10) NOT NULL default '',
  `Future_Bal` decimal(10,2) NOT NULL default '0.00',
  `Current_Bal` decimal(10,2) NOT NULL default '0.00',
  `Age_1_Bal` decimal(10,2) NOT NULL default '0.00',
  `Age_2_Bal` decimal(10,2) NOT NULL default '0.00',
  `Age_3_Bal` decimal(10,2) NOT NULL default '0.00',
  `Total_Bal` decimal(10,2) NOT NULL default '0.00',
  `Tr_Date` date NOT NULL default '0000-00-00',
  `Tr_Type` varchar(20) NOT NULL default '',
  `Ext_Ref` varchar(20) NOT NULL default '',
  `Int_Ref` varchar(20) NOT NULL default '',
  KEY `FK_Statement` (`Nic_Handle`),
  CONSTRAINT `FK_Statement` FOREIGN KEY (`Nic_Handle`) REFERENCES `NicHandle` (`Nic_Handle`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `StaticTable` */

DROP TABLE IF EXISTS `StaticTable`;

CREATE TABLE `StaticTable` (
  `Nxt_Zone_Reload` time NOT NULL default '00:00:00',
  `Next_MSD_Run` date NOT NULL default '0000-00-00',
  `Last_MSD_Run` datetime NOT NULL default '0000-00-00 00:00:00',
  `Last_Cr_Nt_Mods` datetime NOT NULL default '0000-00-00 00:00:00',
  `Last_Rcpt_Run` datetime NOT NULL default '0000-00-00 00:00:00',
  `Last_PIA_Run` datetime NOT NULL default '0000-00-00 00:00:00',
  `Last_Xfer_Inv_Run` datetime NOT NULL default '0000-00-00 00:00:00',
  `Vision_Date` date NOT NULL default '0000-00-00',
  `Last_Rcpt_Run_Off` datetime NOT NULL default '0000-00-00 00:00:00',
  `Last_Month_Inv_Run` date NOT NULL default '0000-00-00',
  `Last_Cr_Nt_MSD_Xfers` datetime NOT NULL default '0000-00-00 00:00:00',
  `Last_Guest_Rcp_Run_Off` datetime NOT NULL default '0000-00-00 00:00:00',
  `Last_Guest_Rcp_Run_On` datetime NOT NULL default '0000-00-00 00:00:00',
  `Last_Guest_Rcp_Run_RX_On` datetime NOT NULL default '0000-00-00 00:00:00',
  `Last_Deposit_Run_Adv_Xfer` datetime NOT NULL default '0000-00-00 00:00:00',
  `Last_Deposit_Run_RED` datetime NOT NULL default '0000-00-00 00:00:00',
  `Last_Deposit_Acc_Run` datetime NOT NULL default '0000-00-00 00:00:00'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `StaticTableName` */

DROP TABLE IF EXISTS `StaticTableName`;

CREATE TABLE `StaticTableName` (
  `CompanyName` varchar(255) NOT NULL default '',
  `VatRate` decimal(6,4) default '0.0000',
  `TradeRate` float default NULL,
  `RetailRate` float default '0',
  `HistoricRetailRate` float default '0',
  `BusinessDate` date default NULL,
  `VatNumber` varchar(255) default 'NULL',
  `LastNicHandle` varchar(12) default NULL,
  `CompanyAddress` varchar(255) default NULL,
  `LastInvoiceRun` date NOT NULL default '0000-00-00',
  `LastRemittalRun` date NOT NULL default '0000-00-00',
  `LastInvoiceNo` int(1) unsigned NOT NULL default '0',
  `Phone` varchar(20) NOT NULL default '0',
  `Fax` varchar(20) NOT NULL default '0',
  `Web` varchar(66) NOT NULL default '0',
  `SMTP` varchar(30) default NULL,
  `SMTP_Port` int(11) default NULL,
  `ReplyAddr` varchar(60) default NULL,
  `WebReport` varchar(66) NOT NULL default '',
  `RegistrarRate` float default NULL,
  `RegistrarMSDRate` float default NULL,
  `MinDepositLimit` float default NULL,
  `MaxDepositLimit` float default NULL,
  PRIMARY KEY  (`CompanyName`),
  UNIQUE KEY `CompanyName` (`CompanyName`),
  KEY `CompanyName_2` (`CompanyName`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `StatsAccess` */

DROP TABLE IF EXISTS `StatsAccess`;

CREATE TABLE `StatsAccess` (
  `Nic_Handle` varchar(12) NOT NULL default '',
  `NH_Password` tinytext,
  `NH_Level` int(4) NOT NULL default '0',
  `Answer` tinytext,
  PRIMARY KEY  (`Nic_Handle`),
  CONSTRAINT `FK_StatsAccess` FOREIGN KEY (`Nic_Handle`) REFERENCES `NicHandle` (`Nic_Handle`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `StatsAccessLog` */

DROP TABLE IF EXISTS `StatsAccessLog`;

CREATE TABLE `StatsAccessLog` (
  `NicHandle` varchar(12) NOT NULL default '',
  `Access_TS` datetime NOT NULL default '0000-00-00 00:00:00',
  `Report` varchar(60) NOT NULL default '',
  `Remote_IP` varchar(16) NOT NULL default ''
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `SuspendList` */

DROP TABLE IF EXISTS `SuspendList`;

CREATE TABLE `SuspendList` (
  `D_Name` varchar(66) NOT NULL default '',
  `S_Add_Dt` date default NULL,
  `S_Add_Id` varchar(12) default NULL,
  PRIMARY KEY  (`D_Name`),
  UNIQUE KEY `D_Name` (`D_Name`),
  KEY `D_Name_2` (`D_Name`),
  CONSTRAINT `FK_suspendlist` FOREIGN KEY (`D_Name`) REFERENCES `Domain` (`D_Name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `Tariff` */

DROP TABLE IF EXISTS `Tariff`;

CREATE TABLE `Tariff` (
  `Status` tinyint(2) unsigned NOT NULL default '0',
  `Description` tinytext NOT NULL,
  PRIMARY KEY  (`Status`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `Telecom` */

DROP TABLE IF EXISTS `Telecom`;

CREATE TABLE `Telecom` (
  `Nic_Handle` varchar(12) NOT NULL default '',
  `Phone` varchar(25) NOT NULL default '',
  `Type` char(1) NOT NULL default '',
  PRIMARY KEY  (`Nic_Handle`,`Phone`,`Type`),
  CONSTRAINT `FK_telecom` FOREIGN KEY (`Nic_Handle`) REFERENCES `NicHandle` (`Nic_Handle`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `TelecomHist` */

DROP TABLE IF EXISTS `TelecomHist`;

CREATE TABLE `TelecomHist` (
  `Nic_Handle` varchar(12) NOT NULL default '',
  `Phone` varchar(25) NOT NULL default '',
  `Type` char(1) NOT NULL default '',
  `Chng_NH` varchar(12) NOT NULL default '',
  `Chng_Dt` datetime NOT NULL default '0000-00-00 00:00:00',
  PRIMARY KEY  (`Chng_Dt`,`Nic_Handle`,`Phone`,`Type`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `TempGross` */

DROP TABLE IF EXISTS `TempGross`;

CREATE TABLE `TempGross` (
  `Nic_Handle` varchar(16) NOT NULL,
  `Type` varchar(8) NOT NULL,
  `GR_Date` datetime NOT NULL,
  `A_Number` int(8) NOT NULL,
  `D_Name` varchar(66) NOT NULL,
  `Holder` varchar(100) NOT NULL,
  `InvNo` varchar(20) default NULL,
  `RegDate` date NOT NULL,
  `RenDate` date default NULL,
  `Amount` decimal(10,2) default NULL,
  `VAT` decimal(10,2) default NULL,
  `Random` varchar(100) NOT NULL,
  PRIMARY KEY  (`Nic_Handle`,`GR_Date`,`D_Name`,`Random`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `TempTicketHist` */

DROP TABLE IF EXISTS `TempTicketHist`;

CREATE TABLE `TempTicketHist` (
  `T_Number` int(10) NOT NULL default '0',
  `T_Type` char(1) NOT NULL default '',
  `D_Name` varchar(66) NOT NULL default '',
  `DN_Fail_Cd` tinyint(3) default NULL,
  `D_Holder` tinytext NOT NULL,
  `DH_Fail_Cd` tinyint(3) default NULL,
  `A_Number` int(8) unsigned zerofill NOT NULL default '00000000',
  `AC_Fail_Cd` tinyint(3) default NULL,
  `T_Class` tinytext NOT NULL,
  `T_Category` tinytext NOT NULL,
  `T_Remark` text,
  `Admin_NH1` varchar(12) NOT NULL default '',
  `ANH1_Fail_Cd` tinyint(3) default NULL,
  `Admin_NH2` varchar(12) default NULL,
  `ANH2_Fail_Cd` tinyint(3) default NULL,
  `Tech_NH` varchar(12) NOT NULL default '',
  `TNH_Fail_Cd` tinyint(3) default NULL,
  `Bill_NH` varchar(12) NOT NULL default '',
  `BNH_Fail_Cd` tinyint(3) default NULL,
  `Creator_NH` varchar(12) NOT NULL default '',
  `DNS_Name1` tinytext NOT NULL,
  `DNS1_Fail_Cd` tinyint(3) default NULL,
  `DNS_IP1` tinytext,
  `DNSIP1_Fail_Cd` tinyint(3) default NULL,
  `DNS_Name2` tinytext NOT NULL,
  `DNS2_Fail_Cd` tinyint(3) default NULL,
  `DNS_IP2` tinytext,
  `DNSIP2_Fail_Cd` tinyint(3) default NULL,
  `DNS_Name3` tinytext,
  `DNS3_Fail_Cd` tinyint(3) default NULL,
  `DNS_IP3` tinytext,
  `DNSIP3_Fail_Cd` tinyint(3) default NULL,
  `Admin_Status` tinyint(2) unsigned NOT NULL default '0',
  `Ad_StatusDt` date NOT NULL default '0000-00-00',
  `Tech_Status` tinyint(2) unsigned NOT NULL default '0',
  `T_Status_Dt` date NOT NULL default '0000-00-00',
  `CheckedOut` char(1) NOT NULL default 'N',
  `CheckedOutTo` varchar(12) default NULL,
  `T_Reg_Dt` date default NULL,
  `T_Ren_Dt` date default NULL,
  `T_TStamp` timestamp NOT NULL default '0000-00-00 00:00:00',
  `H_Remark` text,
  `Chng_Dt` datetime NOT NULL default '0000-00-00 00:00:00',
  `Chng_NH` varchar(12) default NULL,
  `T_Class_Fail_Cd` tinyint(3) default NULL,
  `T_Category_Fail_Cd` tinyint(3) default NULL,
  `T_Created_TS` datetime NOT NULL default '0000-00-00 00:00:00',
  `Opt_Cert` char(1) default 'N',
  `T_ClikPaid` char(1) default 'N',
  `Fee` float default '0',
  `VAT` char(1) default 'Y',
  PRIMARY KEY  (`Chng_Dt`,`T_Number`),
  KEY `idx_TicketHist_D_Name` (`D_Name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `Ticket` */

DROP TABLE IF EXISTS `Ticket`;

CREATE TABLE `Ticket` (
  `T_Number` int(10) NOT NULL auto_increment,
  `T_Type` char(1) NOT NULL default '',
  `D_Name` varchar(66) NOT NULL default '',
  `DN_Fail_Cd` tinyint(3) default NULL,
  `D_Holder` tinytext NOT NULL,
  `DH_Fail_Cd` tinyint(3) default NULL,
  `A_Number` int(8) unsigned zerofill NOT NULL default '00000000',
  `AC_Fail_Cd` tinyint(3) default NULL,
  `T_Class` tinytext NOT NULL,
  `T_Category` tinytext NOT NULL,
  `T_Remark` text,
  `Admin_NH1` varchar(12) NOT NULL default '',
  `ANH1_Fail_Cd` tinyint(3) default NULL,
  `Admin_NH2` varchar(12) default NULL,
  `ANH2_Fail_Cd` tinyint(3) default NULL,
  `Tech_NH` varchar(12) NOT NULL default '',
  `TNH_Fail_Cd` tinyint(3) default NULL,
  `Bill_NH` varchar(12) NOT NULL default '',
  `BNH_Fail_Cd` tinyint(3) default NULL,
  `Creator_NH` varchar(12) NOT NULL default '',
  `DNS_Name1` tinytext NOT NULL,
  `DNS1_Fail_Cd` tinyint(3) default NULL,
  `DNS_IP1` tinytext,
  `DNSIP1_Fail_Cd` tinyint(3) default NULL,
  `DNS_Name2` tinytext NOT NULL,
  `DNS2_Fail_Cd` tinyint(3) default NULL,
  `DNS_IP2` tinytext,
  `DNSIP2_Fail_Cd` tinyint(3) default NULL,
  `DNS_Name3` tinytext,
  `DNS3_Fail_Cd` tinyint(3) default NULL,
  `DNS_IP3` tinytext,
  `DNSIP3_Fail_Cd` tinyint(3) default NULL,
  `Admin_Status` tinyint(2) unsigned NOT NULL default '0',
  `Ad_StatusDt` date NOT NULL default '0000-00-00',
  `Tech_Status` tinyint(2) unsigned NOT NULL default '0',
  `T_Status_Dt` date NOT NULL default '0000-00-00',
  `CheckedOut` char(1) NOT NULL default 'N',
  `CheckedOutTo` varchar(12) default NULL,
  `T_Reg_Dt` date default NULL,
  `T_Ren_Dt` date default NULL,
  `T_TStamp` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  `H_Remark` text,
  `T_Class_Fail_Cd` tinyint(3) default NULL,
  `T_Category_Fail_Cd` tinyint(3) default NULL,
  `T_Created_TS` datetime NOT NULL default '0000-00-00 00:00:00',
  `Opt_Cert` char(1) default 'N',
  `T_ClikPaid` char(1) default 'N',
  `Fee` float default '0',
  `VAT` char(1) default 'Y',
  PRIMARY KEY  (`T_Number`),
  UNIQUE KEY `Number` (`T_Number`),
  KEY `Tech_Status` (`Tech_Status`),
  KEY `D_Name` (`D_Name`),
  KEY `Admin_Status` USING HASH (`Admin_Status`),
  KEY `A_Number` USING HASH (`A_Number`),
  KEY `D_Holder` USING BTREE (`D_Holder`(128)),
  KEY `FK_ticket_AC_FC` (`AC_Fail_Cd`),
  KEY `FK_ticket_ANH1_FC` (`ANH1_Fail_Cd`),
  KEY `FK_ticket_ANH2_FC` (`ANH2_Fail_Cd`),
  KEY `FK_ticket_BH_FC` (`BNH_Fail_Cd`),
  KEY `FK_ticket_ANH1` (`Admin_NH1`),
  KEY `FK_ticket_ANH2` (`Admin_NH2`),
  KEY `FK_ticket_BH` (`Bill_NH`),
  KEY `FK_ticket_TNH` (`Tech_NH`),
  KEY `FK_ticket_TNH_FC` (`TNH_Fail_Cd`),
  KEY `FK_ticket_DH_FC` (`DH_Fail_Cd`),
  KEY `FK_ticket_DN_FC` (`DN_Fail_Cd`),
  KEY `FK_ticket_DNS1_FC` (`DNS1_Fail_Cd`),
  KEY `FK_ticket_DNS2_FC` (`DNS2_Fail_Cd`),
  KEY `FK_ticket_DNS3_FC` (`DNS3_Fail_Cd`),
  KEY `FK_ticket_DNSIP1_FC` (`DNSIP1_Fail_Cd`),
  KEY `FK_ticket_DNSIP2_FC` (`DNSIP2_Fail_Cd`),
  KEY `FK_ticket_DNSIP3_FC` (`DNSIP3_Fail_Cd`),
  KEY `FK_ticket_Class_FC` (`T_Class_Fail_Cd`),
  KEY `FK_ticket_Cat_FC` (`T_Category_Fail_Cd`),
  CONSTRAINT `FK_ticket` FOREIGN KEY (`A_Number`) REFERENCES `Account` (`A_Number`),
  CONSTRAINT `FK_ticket_AC_FC` FOREIGN KEY (`AC_Fail_Cd`) REFERENCES `TicketFailCd` (`FailCd`),
  CONSTRAINT `FK_ticket_ANH1` FOREIGN KEY (`Admin_NH1`) REFERENCES `NicHandle` (`Nic_Handle`),
  CONSTRAINT `FK_ticket_ANH1_FC` FOREIGN KEY (`ANH1_Fail_Cd`) REFERENCES `TicketFailCd` (`FailCd`),
  CONSTRAINT `FK_ticket_ANH2` FOREIGN KEY (`Admin_NH2`) REFERENCES `NicHandle` (`Nic_Handle`),
  CONSTRAINT `FK_ticket_ANH2_FC` FOREIGN KEY (`ANH2_Fail_Cd`) REFERENCES `TicketFailCd` (`FailCd`),
  CONSTRAINT `FK_ticket_AS` FOREIGN KEY (`Admin_Status`) REFERENCES `TicketAdminStatus` (`Status`),
  CONSTRAINT `FK_ticket_BH` FOREIGN KEY (`Bill_NH`) REFERENCES `NicHandle` (`Nic_Handle`),
  CONSTRAINT `FK_ticket_BH_FC` FOREIGN KEY (`BNH_Fail_Cd`) REFERENCES `TicketFailCd` (`FailCd`),
  CONSTRAINT `FK_ticket_Cat_FC` FOREIGN KEY (`T_Category_Fail_Cd`) REFERENCES `TicketFailCd` (`FailCd`),
  CONSTRAINT `FK_ticket_Class_FC` FOREIGN KEY (`T_Class_Fail_Cd`) REFERENCES `TicketFailCd` (`FailCd`),
  CONSTRAINT `FK_ticket_DH_FC` FOREIGN KEY (`DH_Fail_Cd`) REFERENCES `TicketFailCd` (`FailCd`),
  CONSTRAINT `FK_ticket_DNS1_FC` FOREIGN KEY (`DNS1_Fail_Cd`) REFERENCES `TicketFailCd` (`FailCd`),
  CONSTRAINT `FK_ticket_DNS2_FC` FOREIGN KEY (`DNS2_Fail_Cd`) REFERENCES `TicketFailCd` (`FailCd`),
  CONSTRAINT `FK_ticket_DNS3_FC` FOREIGN KEY (`DNS3_Fail_Cd`) REFERENCES `TicketFailCd` (`FailCd`),
  CONSTRAINT `FK_ticket_DNSIP1_FC` FOREIGN KEY (`DNSIP1_Fail_Cd`) REFERENCES `TicketFailCd` (`FailCd`),
  CONSTRAINT `FK_ticket_DNSIP2_FC` FOREIGN KEY (`DNSIP2_Fail_Cd`) REFERENCES `TicketFailCd` (`FailCd`),
  CONSTRAINT `FK_ticket_DNSIP3_FC` FOREIGN KEY (`DNSIP3_Fail_Cd`) REFERENCES `TicketFailCd` (`FailCd`),
  CONSTRAINT `FK_ticket_DN_FC` FOREIGN KEY (`DN_Fail_Cd`) REFERENCES `TicketFailCd` (`FailCd`),
  CONSTRAINT `FK_ticket_TNH` FOREIGN KEY (`Tech_NH`) REFERENCES `NicHandle` (`Nic_Handle`),
  CONSTRAINT `FK_ticket_TNH_FC` FOREIGN KEY (`TNH_Fail_Cd`) REFERENCES `TicketFailCd` (`FailCd`),
  CONSTRAINT `FK_ticket_TS` FOREIGN KEY (`Tech_Status`) REFERENCES `TicketTechStatus` (`Status`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `TicketAdminStatus` */

DROP TABLE IF EXISTS `TicketAdminStatus`;

CREATE TABLE `TicketAdminStatus` (
  `Status` tinyint(2) unsigned NOT NULL default '0',
  `Description` tinytext NOT NULL,
  PRIMARY KEY  (`Status`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `TicketFailCd` */

DROP TABLE IF EXISTS `TicketFailCd`;

CREATE TABLE `TicketFailCd` (
  `FailCd` tinyint(3) NOT NULL default '0',
  `Description` tinytext NOT NULL,
  `Data_Field` tinyint(2) unsigned zerofill default '00',
  PRIMARY KEY  (`FailCd`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `TicketHist` */

DROP TABLE IF EXISTS `TicketHist`;

CREATE TABLE `TicketHist` (
  `T_Number` int(10) NOT NULL default '0',
  `T_Type` char(1) NOT NULL default '',
  `D_Name` varchar(66) NOT NULL default '',
  `DN_Fail_Cd` tinyint(3) default NULL,
  `D_Holder` tinytext NOT NULL,
  `DH_Fail_Cd` tinyint(3) default NULL,
  `A_Number` int(8) unsigned zerofill NOT NULL default '00000000',
  `AC_Fail_Cd` tinyint(3) default NULL,
  `T_Class` tinytext NOT NULL,
  `T_Category` tinytext NOT NULL,
  `T_Remark` text,
  `Admin_NH1` varchar(12) NOT NULL default '',
  `ANH1_Fail_Cd` tinyint(3) default NULL,
  `Admin_NH2` varchar(12) default NULL,
  `ANH2_Fail_Cd` tinyint(3) default NULL,
  `Tech_NH` varchar(12) NOT NULL default '',
  `TNH_Fail_Cd` tinyint(3) default NULL,
  `Bill_NH` varchar(12) NOT NULL default '',
  `BNH_Fail_Cd` tinyint(3) default NULL,
  `Creator_NH` varchar(12) NOT NULL default '',
  `DNS_Name1` tinytext NOT NULL,
  `DNS1_Fail_Cd` tinyint(3) default NULL,
  `DNS_IP1` tinytext,
  `DNSIP1_Fail_Cd` tinyint(3) default NULL,
  `DNS_Name2` tinytext NOT NULL,
  `DNS2_Fail_Cd` tinyint(3) default NULL,
  `DNS_IP2` tinytext,
  `DNSIP2_Fail_Cd` tinyint(3) default NULL,
  `DNS_Name3` tinytext,
  `DNS3_Fail_Cd` tinyint(3) default NULL,
  `DNS_IP3` tinytext,
  `DNSIP3_Fail_Cd` tinyint(3) default NULL,
  `Admin_Status` tinyint(2) unsigned NOT NULL default '0',
  `Ad_StatusDt` date NOT NULL default '0000-00-00',
  `Tech_Status` tinyint(2) unsigned NOT NULL default '0',
  `T_Status_Dt` date NOT NULL default '0000-00-00',
  `CheckedOut` char(1) NOT NULL default 'N',
  `CheckedOutTo` varchar(12) default NULL,
  `T_Reg_Dt` date default NULL,
  `T_Ren_Dt` date default NULL,
  `T_TStamp` timestamp NOT NULL default '0000-00-00 00:00:00',
  `H_Remark` text,
  `Chng_Dt` datetime NOT NULL default '0000-00-00 00:00:00',
  `Chng_NH` varchar(12) default NULL,
  `T_Class_Fail_Cd` tinyint(3) default NULL,
  `T_Category_Fail_Cd` tinyint(3) default NULL,
  `T_Created_TS` datetime NOT NULL default '0000-00-00 00:00:00',
  `Opt_Cert` char(1) default 'N',
  `T_ClikPaid` char(1) default 'N',
  `Fee` float default '0',
  `VAT` char(1) default 'Y',
  PRIMARY KEY  (`Chng_Dt`,`T_Number`),
  KEY `T_Number` USING BTREE (`T_Number`),
  KEY `D_Name` USING BTREE (`D_Name`),
  KEY `D_Holder` USING BTREE (`D_Holder`(128)),
  KEY `A_Number` USING HASH (`A_Number`),
  KEY `Chng_Dt` USING BTREE (`Chng_Dt`),
  KEY `idx_TicketHist_D_Name` (`D_Name`),
  KEY `FK_TicketHist_ac` (`AC_Fail_Cd`),
  KEY `FK_TicketHist_anh1` (`ANH1_Fail_Cd`),
  KEY `FK_TicketHist_nh2` (`ANH2_Fail_Cd`),
  KEY `FK_TicketHist_bnh` (`BNH_Fail_Cd`),
  KEY `FK_TicketHist_dh` (`DH_Fail_Cd`),
  KEY `FK_TicketHist_dn` (`DN_Fail_Cd`),
  KEY `FK_TicketHist_dns1` (`DNS1_Fail_Cd`),
  KEY `FK_TicketHist_dns2` (`DNS2_Fail_Cd`),
  KEY `FK_TicketHist_dns3` (`DNS3_Fail_Cd`),
  KEY `FK_TicketHist_ip1` (`DNSIP1_Fail_Cd`),
  KEY `FK_TicketHist_ip2` (`DNSIP2_Fail_Cd`),
  KEY `FK_TicketHist_ip3` (`DNSIP3_Fail_Cd`),
  KEY `FK_TicketHist_cat` (`T_Category_Fail_Cd`),
  KEY `FK_TicketHist_class` (`T_Class_Fail_Cd`),
  KEY `FK_TicketHist_tnh` (`TNH_Fail_Cd`),
  KEY `FK_TicketHist_TS` (`Tech_Status`),
  KEY `FK_TicketHist_AS` (`Admin_Status`),
  CONSTRAINT `FK_TicketHist_AS` FOREIGN KEY (`Admin_Status`) REFERENCES `TicketAdminStatus` (`Status`),
  CONSTRAINT `FK_TicketHist_ac` FOREIGN KEY (`AC_Fail_Cd`) REFERENCES `TicketFailCd` (`FailCd`),
  CONSTRAINT `FK_TicketHist_anh1` FOREIGN KEY (`ANH1_Fail_Cd`) REFERENCES `TicketFailCd` (`FailCd`),
  CONSTRAINT `FK_TicketHist_bnh` FOREIGN KEY (`BNH_Fail_Cd`) REFERENCES `TicketFailCd` (`FailCd`),
  CONSTRAINT `FK_TicketHist_cat` FOREIGN KEY (`T_Category_Fail_Cd`) REFERENCES `TicketFailCd` (`FailCd`),
  CONSTRAINT `FK_TicketHist_class` FOREIGN KEY (`T_Class_Fail_Cd`) REFERENCES `TicketFailCd` (`FailCd`),
  CONSTRAINT `FK_TicketHist_dh` FOREIGN KEY (`DH_Fail_Cd`) REFERENCES `TicketFailCd` (`FailCd`),
  CONSTRAINT `FK_TicketHist_dn` FOREIGN KEY (`DN_Fail_Cd`) REFERENCES `TicketFailCd` (`FailCd`),
  CONSTRAINT `FK_TicketHist_dns1` FOREIGN KEY (`DNS1_Fail_Cd`) REFERENCES `TicketFailCd` (`FailCd`),
  CONSTRAINT `FK_TicketHist_dns2` FOREIGN KEY (`DNS2_Fail_Cd`) REFERENCES `TicketFailCd` (`FailCd`),
  CONSTRAINT `FK_TicketHist_dns3` FOREIGN KEY (`DNS3_Fail_Cd`) REFERENCES `TicketFailCd` (`FailCd`),
  CONSTRAINT `FK_TicketHist_ip1` FOREIGN KEY (`DNSIP1_Fail_Cd`) REFERENCES `TicketFailCd` (`FailCd`),
  CONSTRAINT `FK_TicketHist_ip2` FOREIGN KEY (`DNSIP2_Fail_Cd`) REFERENCES `TicketFailCd` (`FailCd`),
  CONSTRAINT `FK_TicketHist_ip3` FOREIGN KEY (`DNSIP3_Fail_Cd`) REFERENCES `TicketFailCd` (`FailCd`),
  CONSTRAINT `FK_TicketHist_nh2` FOREIGN KEY (`ANH2_Fail_Cd`) REFERENCES `TicketFailCd` (`FailCd`),
  CONSTRAINT `FK_TicketHist_tnh` FOREIGN KEY (`TNH_Fail_Cd`) REFERENCES `TicketFailCd` (`FailCd`),
  CONSTRAINT `FK_TicketHist_TS` FOREIGN KEY (`Tech_Status`) REFERENCES `TicketTechStatus` (`Status`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `TicketResponse` */

DROP TABLE IF EXISTS `TicketResponse`;

CREATE TABLE `TicketResponse` (
  `Response_ID` int(3) unsigned NOT NULL auto_increment,
  `Response_Title` text,
  `Response_Text` text,
  PRIMARY KEY  (`Response_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `TicketTechStatus` */

DROP TABLE IF EXISTS `TicketTechStatus`;

CREATE TABLE `TicketTechStatus` (
  `Status` tinyint(2) unsigned NOT NULL default '0',
  `Description` tinytext NOT NULL,
  PRIMARY KEY  (`Status`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `Transfers` */

DROP TABLE IF EXISTS `Transfers`;

CREATE TABLE `Transfers` (
  `D_Name` varchar(66) NOT NULL default '',
  `Tr_Date` date NOT NULL default '0000-00-00',
  `Old_Nic_Handle` varchar(15) NOT NULL default '',
  `New_Nic_Handle` varchar(15) NOT NULL default '',
  PRIMARY KEY  (`D_Name`,`Tr_Date`,`Old_Nic_Handle`,`New_Nic_Handle`),
  KEY `FK_transfers_1` (`New_Nic_Handle`),
  KEY `FK_transfers_2` (`Old_Nic_Handle`),
  CONSTRAINT `FK_transfers` FOREIGN KEY (`D_Name`) REFERENCES `Domain` (`D_Name`),
  CONSTRAINT `FK_transfers_1` FOREIGN KEY (`New_Nic_Handle`) REFERENCES `NicHandle` (`Nic_Handle`),
  CONSTRAINT `FK_transfers_2` FOREIGN KEY (`Old_Nic_Handle`) REFERENCES `NicHandle` (`Nic_Handle`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `TransfersHist` */

DROP TABLE IF EXISTS `TransfersHist`;

CREATE TABLE `TransfersHist` (
  `D_Name` varchar(66) NOT NULL default '',
  `Tr_Date` date NOT NULL default '0000-00-00',
  `Old_Nic_Handle` varchar(15) NOT NULL default '',
  `New_Nic_Handle` varchar(15) NOT NULL default '',
  PRIMARY KEY  (`D_Name`,`Tr_Date`,`Old_Nic_Handle`,`New_Nic_Handle`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `VatCountries` */

DROP TABLE IF EXISTS `VatCountries`;

CREATE TABLE `VatCountries` (
  `CountryCode` smallint(3) unsigned NOT NULL default '0',
  `VatType` char(1) NOT NULL default '',
  `ISOCode` char(2) NOT NULL default '',
  PRIMARY KEY  (`CountryCode`),
  UNIQUE KEY `CountryCode` (`CountryCode`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `VatCountry` */

DROP TABLE IF EXISTS `VatCountry`;

CREATE TABLE `VatCountry` (
  `id` int(11) NOT NULL auto_increment,
  `name` varchar(128) NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `Zone` */

DROP TABLE IF EXISTS `Zone`;

CREATE TABLE `Zone` (
  `Head` text NOT NULL,
  `Serial` int(2) NOT NULL default '0'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
