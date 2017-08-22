/*
SQLyog Enterprise Trial - MySQL GUI v7.1 Beta2
MySQL - 5.0.67-community-nt : Database - phoenixdb
*********************************************************************
*/
/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

CREATE DATABASE /*!32312 IF NOT EXISTS*/`phoenixdb_small` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `phoenixdb_small`;

/*Table structure for table `20medsizeaccounts` */

DROP TABLE IF EXISTS `20medsizeaccounts`;

CREATE TABLE `20medsizeaccounts` (
  `A_Number` int(8) unsigned NOT NULL default '0',
  PRIMARY KEY  (`A_Number`),
  UNIQUE KEY `A_Number` (`A_Number`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `access` */

DROP TABLE IF EXISTS `access`;

CREATE TABLE `access` (
  `Nic_Handle` varchar(12) NOT NULL default '',
  `NH_Password` tinytext,
  `NH_Level` int(4) NOT NULL default '0',
  `Answer` tinytext,
  PRIMARY KEY  (`Nic_Handle`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `accesshist` */

DROP TABLE IF EXISTS `accesshist`;

CREATE TABLE `accesshist` (
  `Nic_Handle` varchar(12) NOT NULL default '',
  `NH_Password` tinytext,
  `NH_Level` int(4) default NULL,
  `Answer` tinytext,
  `Chng_NH` varchar(12) NOT NULL default '',
  `Chng_Dt` datetime NOT NULL default '0000-00-00 00:00:00',
  PRIMARY KEY  (`Chng_Dt`,`Nic_Handle`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `account` */

DROP TABLE IF EXISTS `account`;

CREATE TABLE `account` (
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
  PRIMARY KEY  (`A_Number`)
) ENGINE=InnoDB AUTO_INCREMENT=801 DEFAULT CHARSET=latin1;

/*Table structure for table `accounthist` */

DROP TABLE IF EXISTS `accounthist`;

CREATE TABLE `accounthist` (
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

/*Table structure for table `adminc_emails` */

DROP TABLE IF EXISTS `adminc_emails`;

CREATE TABLE `adminc_emails` (
  `Email_ID` int(10) unsigned NOT NULL auto_increment,
  `Email_Name` text NOT NULL,
  `Email_Content` text NOT NULL,
  PRIMARY KEY  (`Email_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

/*Table structure for table `allticks` */

DROP TABLE IF EXISTS `allticks`;

CREATE TABLE `allticks` (
  `T_Date` datetime NOT NULL default '0000-00-00 00:00:00',
  `T_Number` varchar(10) NOT NULL default '',
  `Bill_NH` varchar(20) NOT NULL default '',
  `D_Name` varchar(66) NOT NULL default '',
  `D_Holder` tinytext NOT NULL,
  `T_Class` tinytext NOT NULL,
  `T_Category` tinytext NOT NULL,
  `Accept` char(1) NOT NULL default '',
  PRIMARY KEY  (`D_Name`,`T_Date`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `apipermissions` */

DROP TABLE IF EXISTS `apipermissions`;

CREATE TABLE `apipermissions` (
  `id` int(11) NOT NULL,
  `command` varchar(64) NOT NULL,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `command` (`command`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

/*Table structure for table `auth_code` */

DROP TABLE IF EXISTS `auth_code`;

CREATE TABLE `auth_code` (
  `Nic_Handle` varchar(15) NOT NULL,
  `Request_Id` varchar(45) NOT NULL,
  `Auth_Code` char(6) NOT NULL,
  `Status` enum('W','A','E','N') NOT NULL default 'W',
  `Auth_TS` datetime NOT NULL default '0000-00-00 00:00:00',
  PRIMARY KEY  (`Request_Id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

/*Table structure for table `billstatus` */

DROP TABLE IF EXISTS `billstatus`;

CREATE TABLE `billstatus` (
  `Status` tinyint(2) unsigned NOT NULL default '0',
  `Description` char(1) NOT NULL default '',
  `DetailedDescription` tinytext,
  `Colour` varchar(12) default NULL,
  PRIMARY KEY  (`Status`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `category` */

DROP TABLE IF EXISTS `category`;

CREATE TABLE `category` (
  `id` int(11) NOT NULL,
  `name` varchar(128) NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `categorypermission` */

DROP TABLE IF EXISTS `categorypermission`;

CREATE TABLE `categorypermission` (
  `Nic_Handle` varchar(20) NOT NULL default '',
  `Category_Id` int(11) NOT NULL,
  PRIMARY KEY  (`Nic_Handle`,`Category_Id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `class` */

DROP TABLE IF EXISTS `class`;

CREATE TABLE `class` (
  `id` int(11) NOT NULL,
  `name` varchar(128) NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `class_to_category` */

DROP TABLE IF EXISTS `class_to_category`;

CREATE TABLE `class_to_category` (
  `class_id` int(11) NOT NULL,
  `category_id` int(11) NOT NULL,
  PRIMARY KEY  (`class_id`,`category_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `clikpaid` */

DROP TABLE IF EXISTS `clikpaid`;

CREATE TABLE `clikpaid` (
  `Status` tinyint(2) unsigned NOT NULL default '0',
  `Description` char(1) NOT NULL default '0',
  PRIMARY KEY  (`Status`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `clikpaynotification` */

DROP TABLE IF EXISTS `clikpaynotification`;

CREATE TABLE `clikpaynotification` (
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

/*Table structure for table `cond_accept` */

DROP TABLE IF EXISTS `cond_accept`;

CREATE TABLE `cond_accept` (
  `D_Name` varchar(66) NOT NULL default '',
  `Add_Dt` date NOT NULL default '0000-00-00',
  `Comment` text,
  `Status` varchar(250) default NULL,
  PRIMARY KEY  (`D_Name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `contact` */

DROP TABLE IF EXISTS `contact`;

CREATE TABLE `contact` (
  `D_Name` varchar(66) NOT NULL default '',
  `Contact_NH` varchar(12) NOT NULL default '',
  `Type` char(1) NOT NULL default '',
  PRIMARY KEY  (`D_Name`,`Contact_NH`,`Type`),
  KEY `idx_contact_contact_nh` (`Contact_NH`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `contacthist` */

DROP TABLE IF EXISTS `contacthist`;

CREATE TABLE `contacthist` (
  `D_Name` varchar(66) NOT NULL default '',
  `Contact_NH` varchar(12) NOT NULL default '',
  `Type` char(1) NOT NULL default '',
  `Chng_NH` varchar(12) NOT NULL default '',
  `Chng_Dt` datetime NOT NULL default '0000-00-00 00:00:00',
  PRIMARY KEY  (`Chng_Dt`,`D_Name`,`Contact_NH`,`Type`),
  KEY `D_Name` (`D_Name`),
  KEY `Contact_NH` (`Contact_NH`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `counties` */

DROP TABLE IF EXISTS `counties`;

CREATE TABLE `counties` (
  `CountryCode` int(3) unsigned NOT NULL default '0',
  `County` varchar(100) NOT NULL default '',
  PRIMARY KEY  (`CountryCode`,`County`),
  UNIQUE KEY `CountryCode` (`CountryCode`,`County`),
  KEY `CountryCode_2` (`CountryCode`,`County`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `countries` */

DROP TABLE IF EXISTS `countries`;

CREATE TABLE `countries` (
  `CountryCode` int(2) unsigned NOT NULL auto_increment,
  `Country` char(100) default NULL,
  PRIMARY KEY  (`CountryCode`),
  UNIQUE KEY `CountryCode` (`CountryCode`),
  KEY `CountryCode_2` (`CountryCode`)
) ENGINE=InnoDB AUTO_INCREMENT=282 DEFAULT CHARSET=latin1;

/*Table structure for table `cr_notes` */

DROP TABLE IF EXISTS `cr_notes`;

CREATE TABLE `cr_notes` (
  `D_Name` varchar(66) NOT NULL default '',
  `Cr_Note_Dt` date default NULL,
  `Type` char(3) default NULL,
  PRIMARY KEY  (`D_Name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `cr_notes_hist` */

DROP TABLE IF EXISTS `cr_notes_hist`;

CREATE TABLE `cr_notes_hist` (
  `D_Name` varchar(66) NOT NULL default '',
  `Cr_Note_Dt` date NOT NULL default '0000-00-00',
  `Type` char(3) NOT NULL default '',
  `Nic_Handle` varchar(15) NOT NULL default '',
  `C_Nt_Ts` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  PRIMARY KEY  (`D_Name`,`Cr_Note_Dt`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `cr_notes_old` */

DROP TABLE IF EXISTS `cr_notes_old`;

CREATE TABLE `cr_notes_old` (
  `D_Name` varchar(66) NOT NULL default '',
  `Cr_Note_Dt` date NOT NULL default '0000-00-00',
  `Type` char(3) NOT NULL default '',
  PRIMARY KEY  (`D_Name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `d_audit` */

DROP TABLE IF EXISTS `d_audit`;

CREATE TABLE `d_audit` (
  `D_Name` varchar(66) NOT NULL default '',
  `Op_Dt` datetime NOT NULL default '0000-00-00 00:00:00',
  `status` int(11) NOT NULL default '0',
  PRIMARY KEY  (`D_Name`,`Op_Dt`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `d_locked` */

DROP TABLE IF EXISTS `d_locked`;

CREATE TABLE `d_locked` (
  `D_Name` varchar(66) NOT NULL default '',
  `Type` varchar(4) NOT NULL default '',
  `L_Date` datetime NOT NULL default '0000-00-00 00:00:00',
  PRIMARY KEY  (`D_Name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `d_locked_hist` */

DROP TABLE IF EXISTS `d_locked_hist`;

CREATE TABLE `d_locked_hist` (
  `D_Name` varchar(66) NOT NULL default '',
  `Type` varchar(4) NOT NULL default '',
  `L_Date` datetime NOT NULL default '0000-00-00 00:00:00',
  `UL_Date` datetime NOT NULL default '0000-00-00 00:00:00',
  PRIMARY KEY  (`D_Name`,`L_Date`,`UL_Date`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `davidreportregbymonth` */

DROP TABLE IF EXISTS `davidreportregbymonth`;

CREATE TABLE `davidreportregbymonth` (
  `A_Number` int(8) unsigned NOT NULL default '0',
  PRIMARY KEY  (`A_Number`),
  UNIQUE KEY `A_Number` (`A_Number`),
  KEY `A_Number_2` (`A_Number`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `deletelist` */

DROP TABLE IF EXISTS `deletelist`;

CREATE TABLE `deletelist` (
  `D_Name` varchar(66) NOT NULL default '',
  `D_Add_Dt` date default NULL,
  `D_Add_Id` varchar(12) default NULL,
  PRIMARY KEY  (`D_Name`),
  UNIQUE KEY `D_Name` (`D_Name`),
  KEY `D_Name_2` (`D_Name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `deletelisthist` */

DROP TABLE IF EXISTS `deletelisthist`;

CREATE TABLE `deletelisthist` (
  `D_Name` varchar(66) NOT NULL default '',
  `D_Add_Dt` date NOT NULL default '0000-00-00',
  `D_Add_Id` varchar(12) default NULL,
  PRIMARY KEY  (`D_Name`,`D_Add_Dt`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `deletepublic` */

DROP TABLE IF EXISTS `deletepublic`;

CREATE TABLE `deletepublic` (
  `D_Name` varchar(66) NOT NULL default '',
  `Type` char(1) NOT NULL default '',
  PRIMARY KEY  (`D_Name`),
  KEY `D_Name` (`D_Name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `deposit` */

DROP TABLE IF EXISTS `deposit`;

CREATE TABLE `deposit` (
  `Nic_Handle` varchar(20) NOT NULL default '',
  `Trans_Dt` datetime NOT NULL default '0000-00-00 00:00:00',
  `Open_Bal` decimal(10,2) NOT NULL default '0.00',
  `Close_Bal` decimal(10,2) NOT NULL default '0.00',
  PRIMARY KEY  (`Nic_Handle`),
  KEY `Nic_Handle` (`Nic_Handle`),
  KEY `Nic_Handle_2` (`Nic_Handle`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `deposithist` */

DROP TABLE IF EXISTS `deposithist`;

CREATE TABLE `deposithist` (
  `Nic_Handle` varchar(20) NOT NULL default '',
  `Op_Dt` datetime NOT NULL default '0000-00-00 00:00:00',
  `Trans_Dt` datetime NOT NULL default '0000-00-00 00:00:00',
  `Open_Bal` decimal(10,2) NOT NULL default '0.00',
  `Close_Bal` decimal(10,2) NOT NULL default '0.00'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `dns` */

DROP TABLE IF EXISTS `dns`;

CREATE TABLE `dns` (
  `D_Name` char(66) NOT NULL default '',
  `DNS_Name` char(70) NOT NULL default '',
  `DNS_IpAddr` char(20) default NULL,
  `DNS_Order` tinyint(2) NOT NULL default '0',
  PRIMARY KEY  (`D_Name`,`DNS_Name`,`DNS_Order`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=FIXED;

/*Table structure for table `dnshist` */

DROP TABLE IF EXISTS `dnshist`;

CREATE TABLE `dnshist` (
  `D_Name` varchar(66) NOT NULL default '',
  `DNS_Name` varchar(70) NOT NULL default '',
  `DNS_IpAddr` varchar(20) NOT NULL default '',
  `DNS_Order` tinyint(2) NOT NULL default '0',
  `Chng_NH` varchar(12) NOT NULL default '',
  `Chng_Dt` datetime NOT NULL default '0000-00-00 00:00:00',
  PRIMARY KEY  (`D_Name`,`DNS_Name`,`Chng_Dt`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `dnssearch_ratelim` */

DROP TABLE IF EXISTS `dnssearch_ratelim`;

CREATE TABLE `dnssearch_ratelim` (
  `Remote_IP` varchar(16) NOT NULL default '',
  `Query_TS` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  `D_Name` varchar(66) NOT NULL default ''
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `domain` */

DROP TABLE IF EXISTS `domain`;

CREATE TABLE `domain` (
  `D_Name` varchar(66) NOT NULL default '',
  `D_Holder` tinytext NOT NULL,
  `D_Class` tinytext NOT NULL,
  `D_Category` tinytext NOT NULL,
  `A_Number` int(8) unsigned zerofill default NULL,
  `D_Status` tinytext NOT NULL,
  `D_Status_Dt` date NOT NULL default '0000-00-00',
  `D_Reg_Dt` date NOT NULL default '0000-00-00',
  `D_Ren_Dt` date NOT NULL default '0000-00-00',
  `D_TStamp` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  `D_Discount` char(1) NOT NULL default '',
  `D_Bill_Status` char(1) NOT NULL default '',
  `D_Vat_Status` char(1) NOT NULL default '',
  `D_Remark` text,
  `D_ClikPaid` char(1) NOT NULL default 'N',
  `anonymous_holder` tinytext,
  PRIMARY KEY  (`D_Name`),
  KEY `A_Number` (`A_Number`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `domainhist` */

DROP TABLE IF EXISTS `domainhist`;

CREATE TABLE `domainhist` (
  `D_Name` varchar(66) NOT NULL default '',
  `D_Holder` tinytext NOT NULL,
  `D_Class` tinytext NOT NULL,
  `D_Category` tinytext NOT NULL,
  `A_Number` int(8) unsigned zerofill default NULL,
  `D_Status` tinytext NOT NULL,
  `D_Status_Dt` date NOT NULL default '0000-00-00',
  `D_Reg_Dt` date NOT NULL default '0000-00-00',
  `D_Ren_Dt` date NOT NULL default '0000-00-00',
  `D_TStamp` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  `D_Discount` char(1) NOT NULL default '',
  `D_Bill_Status` char(1) NOT NULL default '',
  `D_Vat_Status` char(1) NOT NULL default '',
  `D_Remark` text,
  `Chng_NH` varchar(12) NOT NULL default '',
  `Chng_Dt` datetime NOT NULL default '0000-00-00 00:00:00',
  `D_ClikPaid` char(1) NOT NULL default 'N',
  PRIMARY KEY  (`Chng_Dt`,`D_Name`),
  KEY `D_Name` (`D_Name`),
  KEY `Chng_Dt` (`Chng_Dt`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `domainstats` */

DROP TABLE IF EXISTS `domainstats`;

CREATE TABLE `domainstats` (
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
) ENGINE=MyISAM AUTO_INCREMENT=168 DEFAULT CHARSET=latin1;

/*Table structure for table `email` */

DROP TABLE IF EXISTS `email`;

CREATE TABLE `email` (
  `E_ID` int(3) unsigned NOT NULL auto_increment,
  `E_Name` text,
  `E_Text` text,
  `E_Subject` text,
  `E_To` text,
  `E_CC` text,
  `E_BCC` text,
  PRIMARY KEY  (`E_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=latin1;

/*Table structure for table `geographical_names` */

DROP TABLE IF EXISTS `geographical_names`;

CREATE TABLE `geographical_names` (
  `Place_Name` varchar(100) NOT NULL,
  `Type` varchar(3) NOT NULL,
  PRIMARY KEY  (`Place_Name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `grossrecs` */

DROP TABLE IF EXISTS `grossrecs`;

CREATE TABLE `grossrecs` (
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
  PRIMARY KEY  (`Nic_Handle`,`GR_Date`,`D_Name`,`Random`),
  KEY `Nic_Handle` (`Nic_Handle`),
  KEY `A_Number` (`A_Number`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `guestreceipts` */

DROP TABLE IF EXISTS `guestreceipts`;

CREATE TABLE `guestreceipts` (
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
  PRIMARY KEY  (`D_Name`,`Trans_Dt`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `guestreceiptshist` */

DROP TABLE IF EXISTS `guestreceiptshist`;

CREATE TABLE `guestreceiptshist` (
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

/*Table structure for table `ie_zone_dns` */

DROP TABLE IF EXISTS `ie_zone_dns`;

CREATE TABLE `ie_zone_dns` (
  `D_Name` varchar(66) NOT NULL default '',
  `DNS_Name` varchar(200) NOT NULL default '',
  `IPv4_Addr` varchar(16) default NULL,
  `IPv6_Addr` varchar(40) default NULL,
  PRIMARY KEY  (`DNS_Name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `ie_zone_soa` */

DROP TABLE IF EXISTS `ie_zone_soa`;

CREATE TABLE `ie_zone_soa` (
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

/*Table structure for table `invoice` */

DROP TABLE IF EXISTS `invoice`;

CREATE TABLE `invoice` (
  `I_DName` varchar(66) NOT NULL default '',
  `I_Ren_Dt` date NOT NULL default '0000-00-00',
  `I_Amount` decimal(7,2) NOT NULL default '0.00',
  `I_Status` varchar(10) NOT NULL default '',
  `I_Stat_Dt` date NOT NULL default '0000-00-00',
  `I_NewReg` char(1) NOT NULL default 'N',
  `I_Create_Prc` char(1) NOT NULL default 'A',
  PRIMARY KEY  (`I_DName`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `invoicehist` */

DROP TABLE IF EXISTS `invoicehist`;

CREATE TABLE `invoicehist` (
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

/*Table structure for table `ipv6_glue` */

DROP TABLE IF EXISTS `ipv6_glue`;

CREATE TABLE `ipv6_glue` (
  `DNS_Name` varchar(70) NOT NULL default '',
  `IPv6_Addr` varchar(39) NOT NULL default '',
  `Remarks` varchar(100) default NULL,
  PRIMARY KEY  (`DNS_Name`,`IPv6_Addr`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `levels_to_apipermissions` */

DROP TABLE IF EXISTS `levels_to_apipermissions`;

CREATE TABLE `levels_to_apipermissions` (
  `NH_Level` int(4) NOT NULL,
  `permission_id` int(11) NOT NULL,
  UNIQUE KEY `NH_Level` (`NH_Level`,`permission_id`),
  KEY `c_lev2perm_perm` (`permission_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

/*Table structure for table `maillist` */

DROP TABLE IF EXISTS `maillist`;

CREATE TABLE `maillist` (
  `D_Name` varchar(66) NOT NULL default '',
  `M_Add_Dt` date default NULL,
  `M_Add_Id` varchar(12) default NULL,
  `M_Type` char(1) NOT NULL default 'I',
  PRIMARY KEY  (`D_Name`),
  UNIQUE KEY `D_Name` (`D_Name`),
  KEY `D_Name_2` (`D_Name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `nichandle` */

DROP TABLE IF EXISTS `nichandle`;

CREATE TABLE `nichandle` (
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
  `NH_TStamp` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  `NH_BillC_Ind` char(1) NOT NULL default '',
  `NH_Remark` tinytext,
  `NH_Creator` varchar(12) default NULL,
  PRIMARY KEY  (`Nic_Handle`),
  KEY `A_Number` (`A_Number`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `nichandlefailures` */

DROP TABLE IF EXISTS `nichandlefailures`;

CREATE TABLE `nichandlefailures` (
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

/*Table structure for table `nichandlehist` */

DROP TABLE IF EXISTS `nichandlehist`;

CREATE TABLE `nichandlehist` (
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

/*Table structure for table `nichandleseq` */

DROP TABLE IF EXISTS `nichandleseq`;

CREATE TABLE `nichandleseq` (
  `id` int(11) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

/*Table structure for table `payment` */

DROP TABLE IF EXISTS `payment`;

CREATE TABLE `payment` (
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
  PRIMARY KEY  (`Billing_Contact`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `paymenthist` */

DROP TABLE IF EXISTS `paymenthist`;

CREATE TABLE `paymenthist` (
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

/*Table structure for table `pendingmaillist` */

DROP TABLE IF EXISTS `pendingmaillist`;

CREATE TABLE `pendingmaillist` (
  `D_Name` varchar(66) NOT NULL default '',
  `M_Add_Dt` date NOT NULL default '0000-00-00',
  `M_Add_Id` varchar(12) NOT NULL default '',
  `M_Type` char(1) NOT NULL default '',
  `Type` char(1) NOT NULL default '0',
  PRIMARY KEY  (`D_Name`,`Type`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `pendingpayment` */

DROP TABLE IF EXISTS `pendingpayment`;

CREATE TABLE `pendingpayment` (
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
  KEY `DName_Only` (`D_Name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `pendingpaymenthist` */

DROP TABLE IF EXISTS `pendingpaymenthist`;

CREATE TABLE `pendingpaymenthist` (
  `D_Name` varchar(66) NOT NULL default '',
  `Nic_Handle` varchar(20) NOT NULL default '',
  `Inv_No` varchar(20) NOT NULL default '',
  `Trans_Dt` datetime NOT NULL default '0000-00-00 00:00:00',
  `Sess_ID` varchar(50) NOT NULL default '',
  `Remote_IP` varchar(35) NOT NULL default '',
  `VAT` decimal(10,2) NOT NULL default '0.00',
  `Amount` decimal(10,2) NOT NULL default '0.00',
  `Batch_Total` decimal(10,2) NOT NULL default '0.00',
  `Date` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  `ID` varchar(20) NOT NULL default ''
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `pendingsuspension` */

DROP TABLE IF EXISTS `pendingsuspension`;

CREATE TABLE `pendingsuspension` (
  `D_Name` varchar(66) NOT NULL default '',
  `Nic_Handle` varchar(15) NOT NULL default '',
  `Date` datetime NOT NULL default '0000-00-00 00:00:00',
  `Sess_Id` varchar(50) NOT NULL default '',
  `Remote_Ip` varchar(35) NOT NULL default '',
  `Type` char(1) NOT NULL default '',
  PRIMARY KEY  (`D_Name`,`Date`,`Type`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `pendingsuspensionhist` */

DROP TABLE IF EXISTS `pendingsuspensionhist`;

CREATE TABLE `pendingsuspensionhist` (
  `D_Name` varchar(66) NOT NULL default '',
  `Nic_Handle` varchar(20) NOT NULL default '',
  `Date` date NOT NULL default '0000-00-00',
  `Sess_ID` varchar(50) NOT NULL default '',
  `Remote_IP` varchar(35) NOT NULL default '',
  `ID` varchar(20) NOT NULL default '',
  PRIMARY KEY  (`D_Name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `pendingticket` */

DROP TABLE IF EXISTS `pendingticket`;

CREATE TABLE `pendingticket` (
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
  KEY `D_Name` (`D_Name`)
) ENGINE=InnoDB AUTO_INCREMENT=20732 DEFAULT CHARSET=latin1;

/*Table structure for table `persistedcommands` */

DROP TABLE IF EXISTS `persistedcommands`;

CREATE TABLE `persistedcommands` (
  `cId` bigint(20) NOT NULL auto_increment,
  `P_CreateDate` timestamp NOT NULL default CURRENT_TIMESTAMP,
  `P_NicHandle` varchar(12) NOT NULL,
  `P_RequestHash` varchar(32) NOT NULL,
  `P_Response` text,
  PRIMARY KEY  (`cId`),
  UNIQUE KEY `cId` (`cId`),
  KEY `persistedCommandsIdx` (`P_CreateDate`,`P_NicHandle`,`P_RequestHash`)
) ENGINE=MyISAM AUTO_INCREMENT=188751 DEFAULT CHARSET=latin1;

/*Table structure for table `product` */

DROP TABLE IF EXISTS `product`;

CREATE TABLE `product` (
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
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

/*Table structure for table `rcinvoicehist` */

DROP TABLE IF EXISTS `rcinvoicehist`;

CREATE TABLE `rcinvoicehist` (
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

/*Table structure for table `rcptbatch` */

DROP TABLE IF EXISTS `rcptbatch`;

CREATE TABLE `rcptbatch` (
  `Type` char(10) NOT NULL default '',
  `Start` datetime NOT NULL default '0000-00-00 00:00:00',
  `Finish` datetime NOT NULL default '0000-00-00 00:00:00',
  `Batch_No` char(5) NOT NULL default '',
  PRIMARY KEY  (`Type`,`Start`,`Finish`,`Batch_No`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `realvoid` */

DROP TABLE IF EXISTS `realvoid`;

CREATE TABLE `realvoid` (
  `R_Order_ID` varchar(50) NOT NULL default '',
  `R_AUTHCODE` varchar(15) NOT NULL default '',
  `R_PASREF` varchar(25) NOT NULL default '',
  `Trans_Dt` datetime NOT NULL default '0000-00-00 00:00:00',
  PRIMARY KEY  (`R_Order_ID`,`Trans_Dt`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='Codes for Realex Voids/Rebates';

/*Table structure for table `receipts` */

DROP TABLE IF EXISTS `receipts`;

CREATE TABLE `receipts` (
  `D_Name` varchar(66) NOT NULL default '',
  `Nic_Handle` varchar(20) NOT NULL default '',
  `Inv_No` varchar(20) NOT NULL default '',
  `Trans_Dt` datetime NOT NULL default '0000-00-00 00:00:00',
  `Order_ID` varchar(50) NOT NULL default '',
  `Remote_IP` varchar(35) NOT NULL default '',
  `VAT` decimal(10,2) NOT NULL default '0.00',
  `Amount` decimal(10,2) NOT NULL default '0.00',
  `Batch_Total` decimal(10,2) NOT NULL default '0.00',
  PRIMARY KEY  (`D_Name`,`Trans_Dt`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='Should be money in the realex';

/*Table structure for table `receiptshist` */

DROP TABLE IF EXISTS `receiptshist`;

CREATE TABLE `receiptshist` (
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

/*Table structure for table `reg_accuracy` */

DROP TABLE IF EXISTS `reg_accuracy`;

CREATE TABLE `reg_accuracy` (
  `T_Date` datetime NOT NULL default '0000-00-00 00:00:00',
  `Bill_NH` varchar(20) NOT NULL default '',
  `D_Name` varchar(66) NOT NULL default '',
  `D_Holder` tinytext NOT NULL,
  `T_Class` tinytext NOT NULL,
  `T_Category` tinytext NOT NULL,
  `Accept` char(1) NOT NULL default '',
  PRIMARY KEY  (`D_Name`,`T_Date`),
  KEY `idx_reg_accuracy_d_name` (`D_Name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `rendates` */

DROP TABLE IF EXISTS `rendates`;

CREATE TABLE `rendates` (
  `D_Name` varchar(66) NOT NULL,
  `D_Ren_Dt` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `report` */

DROP TABLE IF EXISTS `report`;

CREATE TABLE `report` (
  `R_ID` varchar(10) NOT NULL default '',
  `R_Description` varchar(66) NOT NULL default '',
  `R_Order` tinyint(3) unsigned NOT NULL default '0',
  `R_File_Name` varchar(30) NOT NULL default '',
  `R_Uses_Date` char(1) NOT NULL default 'N',
  `R_Authority` int(4) NOT NULL default '16777215',
  PRIMARY KEY  (`R_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `reseller_defaults` */

DROP TABLE IF EXISTS `reseller_defaults`;

CREATE TABLE `reseller_defaults` (
  `Nic_Handle` varchar(16) NOT NULL default '',
  `Tech_C` varchar(15) NOT NULL default '',
  `N_Server1` varchar(70) NOT NULL default '',
  `N_Server2` varchar(70) NOT NULL default '',
  `N_Server3` varchar(70) NOT NULL default ''
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `resetpass` */

DROP TABLE IF EXISTS `resetpass`;

CREATE TABLE `resetpass` (
  `Nic_Handle` varchar(16) NOT NULL default '',
  `Unique_ID` varchar(30) NOT NULL,
  `Time_Stamp` datetime NOT NULL default '0000-00-00 00:00:00',
  `Remote_IP` varchar(20) NOT NULL default '',
  PRIMARY KEY  (`Nic_Handle`,`Unique_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `snapshot_of_dns` */

DROP TABLE IF EXISTS `snapshot_of_dns`;

CREATE TABLE `snapshot_of_dns` (
  `D_Name` char(66) NOT NULL default '',
  `DNS_Name` char(70) NOT NULL default '',
  `DNS_IpAddr` char(20) default NULL,
  `DNS_Order` tinyint(2) NOT NULL default '0',
  PRIMARY KEY  (`D_Name`,`DNS_Name`,`DNS_Order`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `soc_votes` */

DROP TABLE IF EXISTS `soc_votes`;

CREATE TABLE `soc_votes` (
  `Nic_Handle` varchar(16) NOT NULL default '',
  `Week` varchar(1) NOT NULL default '',
  `Month` varchar(12) NOT NULL default '',
  `Choice` varchar(50) NOT NULL default '',
  `Password` tinytext NOT NULL,
  PRIMARY KEY  (`Nic_Handle`,`Week`,`Month`,`Choice`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `speciallist` */

DROP TABLE IF EXISTS `speciallist`;

CREATE TABLE `speciallist` (
  `D_Name` varchar(66) NOT NULL default '',
  `S_Add_Dt` date NOT NULL default '0000-00-00',
  `S_Add_NH` varchar(12) NOT NULL default '',
  PRIMARY KEY  (`D_Name`),
  UNIQUE KEY `D_Name` (`D_Name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `speciallisttext` */

DROP TABLE IF EXISTS `speciallisttext`;

CREATE TABLE `speciallisttext` (
  `LineNo` tinyint(3) NOT NULL auto_increment,
  `TextLine` varchar(80) NOT NULL default '',
  PRIMARY KEY  (`LineNo`),
  UNIQUE KEY `LineNo` (`LineNo`)
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=latin1;

/*Table structure for table `statement` */

DROP TABLE IF EXISTS `statement`;

CREATE TABLE `statement` (
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
  `Int_Ref` varchar(20) NOT NULL default ''
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `statictable` */

DROP TABLE IF EXISTS `statictable`;

CREATE TABLE `statictable` (
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

/*Table structure for table `statictablename` */

DROP TABLE IF EXISTS `statictablename`;

CREATE TABLE `statictablename` (
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

/*Table structure for table `statsaccess` */

DROP TABLE IF EXISTS `statsaccess`;

CREATE TABLE `statsaccess` (
  `Nic_Handle` varchar(12) NOT NULL default '',
  `NH_Password` tinytext,
  `NH_Level` int(4) NOT NULL default '0',
  `Answer` tinytext,
  PRIMARY KEY  (`Nic_Handle`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `statsaccesslog` */

DROP TABLE IF EXISTS `statsaccesslog`;

CREATE TABLE `statsaccesslog` (
  `NicHandle` varchar(12) NOT NULL default '',
  `Access_TS` datetime NOT NULL default '0000-00-00 00:00:00',
  `Report` varchar(60) NOT NULL default '',
  `Remote_IP` varchar(16) NOT NULL default ''
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `stpaccount` */

DROP TABLE IF EXISTS `stpaccount`;

CREATE TABLE `stpaccount` (
  `Nic_Handle` varchar(12) NOT NULL default '',
  `A_Number` mediumint(8) unsigned zerofill default '00000000',
  PRIMARY KEY  (`Nic_Handle`),
  UNIQUE KEY `Nic_Handle` (`Nic_Handle`),
  KEY `Nic_Handle_2` (`Nic_Handle`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `suspendlist` */

DROP TABLE IF EXISTS `suspendlist`;

CREATE TABLE `suspendlist` (
  `D_Name` varchar(66) NOT NULL default '',
  `S_Add_Dt` date default NULL,
  `S_Add_Id` varchar(12) default NULL,
  PRIMARY KEY  (`D_Name`),
  UNIQUE KEY `D_Name` (`D_Name`),
  KEY `D_Name_2` (`D_Name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `tariff` */

DROP TABLE IF EXISTS `tariff`;

CREATE TABLE `tariff` (
  `Status` tinyint(2) unsigned NOT NULL default '0',
  `Description` tinytext NOT NULL,
  PRIMARY KEY  (`Status`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `telecom` */

DROP TABLE IF EXISTS `telecom`;

CREATE TABLE `telecom` (
  `Nic_Handle` varchar(12) NOT NULL default '',
  `Phone` varchar(25) NOT NULL default '',
  `Type` char(1) NOT NULL default '',
  PRIMARY KEY  (`Nic_Handle`,`Phone`,`Type`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `telecomhist` */

DROP TABLE IF EXISTS `telecomhist`;

CREATE TABLE `telecomhist` (
  `Nic_Handle` varchar(12) NOT NULL default '',
  `Phone` varchar(25) NOT NULL default '',
  `Type` char(1) NOT NULL default '',
  `Chng_NH` varchar(12) NOT NULL default '',
  `Chng_Dt` datetime NOT NULL default '0000-00-00 00:00:00',
  PRIMARY KEY  (`Chng_Dt`,`Nic_Handle`,`Phone`,`Type`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `tempgross` */

DROP TABLE IF EXISTS `tempgross`;

CREATE TABLE `tempgross` (
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

/*Table structure for table `temptickethist` */

DROP TABLE IF EXISTS `temptickethist`;

CREATE TABLE `temptickethist` (
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
  `T_TStamp` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
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
  `anonymous_holder` tinytext,
  PRIMARY KEY  (`Chng_Dt`,`T_Number`),
  KEY `idx_TicketHist_D_Name` (`D_Name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `ticket` */

DROP TABLE IF EXISTS `ticket`;

CREATE TABLE `ticket` (
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
  KEY `D_Name` (`D_Name`)
) ENGINE=InnoDB AUTO_INCREMENT=259921 DEFAULT CHARSET=latin1;

/*Table structure for table `ticketadminstatus` */

DROP TABLE IF EXISTS `ticketadminstatus`;

CREATE TABLE `ticketadminstatus` (
  `Status` tinyint(2) unsigned NOT NULL default '0',
  `Description` tinytext NOT NULL,
  PRIMARY KEY  (`Status`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `ticketfailcd` */

DROP TABLE IF EXISTS `ticketfailcd`;

CREATE TABLE `ticketfailcd` (
  `FailCd` tinyint(3) NOT NULL default '0',
  `Description` tinytext NOT NULL,
  `Data_Field` tinyint(2) unsigned zerofill default '00',
  PRIMARY KEY  (`FailCd`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `tickethist` */

DROP TABLE IF EXISTS `tickethist`;

CREATE TABLE `tickethist` (
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
  `T_TStamp` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
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
  `anonymous_holder` tinytext,
  PRIMARY KEY  (`Chng_Dt`,`T_Number`),
  KEY `idx_TicketHist_D_Name` (`D_Name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `ticketresponse` */

DROP TABLE IF EXISTS `ticketresponse`;

CREATE TABLE `ticketresponse` (
  `Response_ID` int(3) unsigned NOT NULL auto_increment,
  `Response_Title` text,
  `Response_Text` text,
  PRIMARY KEY  (`Response_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=79 DEFAULT CHARSET=latin1;

/*Table structure for table `tickettechstatus` */

DROP TABLE IF EXISTS `tickettechstatus`;

CREATE TABLE `tickettechstatus` (
  `Status` tinyint(2) unsigned NOT NULL default '0',
  `Description` tinytext NOT NULL,
  PRIMARY KEY  (`Status`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `transfers` */

DROP TABLE IF EXISTS `transfers`;

CREATE TABLE `transfers` (
  `D_Name` varchar(66) NOT NULL default '',
  `Tr_Date` date NOT NULL default '0000-00-00',
  `Old_Nic_Handle` varchar(15) NOT NULL default '',
  `New_Nic_Handle` varchar(15) NOT NULL default '',
  PRIMARY KEY  (`D_Name`,`Tr_Date`,`Old_Nic_Handle`,`New_Nic_Handle`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `transfershist` */

DROP TABLE IF EXISTS `transfershist`;

CREATE TABLE `transfershist` (
  `D_Name` varchar(66) NOT NULL default '',
  `Tr_Date` date NOT NULL default '0000-00-00',
  `Old_Nic_Handle` varchar(15) NOT NULL default '',
  `New_Nic_Handle` varchar(15) NOT NULL default '',
  PRIMARY KEY  (`D_Name`,`Tr_Date`,`Old_Nic_Handle`,`New_Nic_Handle`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `vatcountries` */

DROP TABLE IF EXISTS `vatcountries`;

CREATE TABLE `vatcountries` (
  `CountryCode` smallint(3) unsigned NOT NULL default '0',
  `VatType` char(1) NOT NULL default '',
  `ISOCode` char(2) NOT NULL default '',
  PRIMARY KEY  (`CountryCode`),
  UNIQUE KEY `CountryCode` (`CountryCode`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `vatcountry` */

DROP TABLE IF EXISTS `vatcountry`;

CREATE TABLE `vatcountry` (
  `id` int(11) NOT NULL auto_increment,
  `name` varchar(128) NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=latin1;

/*Table structure for table `zone` */

DROP TABLE IF EXISTS `zone`;

CREATE TABLE `zone` (
  `Head` text NOT NULL,
  `Serial` int(2) NOT NULL default '0'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
