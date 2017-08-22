-- MySQL dump 10.13  Distrib 5.5.35, for debian-linux-gnu (x86_64)
--
-- Host: localhost    Database: crssync
-- ------------------------------------------------------
-- Server version	5.6.16-enterprise-commercial-advanced

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `20MedSizeAccounts`
--

DROP TABLE IF EXISTS `20MedSizeAccounts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `20MedSizeAccounts` (
  `A_Number` int(8) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`A_Number`),
  UNIQUE KEY `A_Number` (`A_Number`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Access`
--

DROP TABLE IF EXISTS `Access`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Access` (
  `Nic_Handle` varchar(12) NOT NULL DEFAULT '',
  `NH_Password` tinytext,
  `NH_Level` int(4) NOT NULL DEFAULT '0',
  `Answer` tinytext,
  `IP_Address` varchar(20) DEFAULT NULL,
  `Salt` varchar(40) DEFAULT NULL,
  `password_changed` date DEFAULT NULL COMMENT 'last password change date',
  `use_two_factor_authentication` char(1) CHARACTER SET utf8 NOT NULL DEFAULT 'Y' COMMENT 'flag indicating, if Two Factor Authentication should be used',
  `secret` varchar(50) DEFAULT NULL COMMENT 'google authentication secret',
  PRIMARY KEY (`Nic_Handle`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `AccessHist`
--

DROP TABLE IF EXISTS `AccessHist`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `AccessHist` (
  `Nic_Handle` varchar(12) NOT NULL DEFAULT '',
  `NH_Password` tinytext,
  `NH_Level` int(4) DEFAULT NULL,
  `Answer` tinytext,
  `IP_Address` varchar(20) DEFAULT NULL,
  `Chng_NH` varchar(12) NOT NULL DEFAULT '',
  `Chng_Dt` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `Salt` varchar(25) DEFAULT NULL,
  `Operation_Type` varchar(255) DEFAULT NULL COMMENT 'operation performed on the user premissions',
  `use_two_factor_authentication` char(1) CHARACTER SET utf8 NOT NULL DEFAULT 'Y' COMMENT 'flag indicating, if Two Factor Authentication should be used',
  `secret` varchar(50) DEFAULT NULL COMMENT 'google authentication secret',
  PRIMARY KEY (`Chng_Dt`,`Nic_Handle`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Account`
--

DROP TABLE IF EXISTS `Account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Account` (
  `A_Number` int(8) unsigned zerofill NOT NULL AUTO_INCREMENT,
  `A_Name` varchar(30) NOT NULL DEFAULT '',
  `Billing_NH` varchar(12) NOT NULL DEFAULT '',
  `Address` tinytext NOT NULL,
  `County` tinytext NOT NULL,
  `Country` tinytext NOT NULL,
  `Web_Address` tinytext NOT NULL,
  `Phone` tinytext NOT NULL,
  `Fax` tinytext NOT NULL,
  `A_Status` varchar(10) DEFAULT NULL,
  `A_Status_Dt` date NOT NULL DEFAULT '0000-00-00',
  `A_Tariff` tinytext NOT NULL,
  `Credit_Limit` decimal(10,2) NOT NULL DEFAULT '0.00',
  `A_Reg_Dt` date NOT NULL DEFAULT '0000-00-00',
  `A_TStamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `Invoice_Freq` varchar(10) NOT NULL DEFAULT '',
  `Next_Invoice_Month` varchar(10) NOT NULL DEFAULT '',
  `A_Remarks` tinytext NOT NULL,
  `Vat_Category` char(1) DEFAULT NULL COMMENT 'vat category',
  PRIMARY KEY (`A_Number`)
) ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `AccountFlags`
--

DROP TABLE IF EXISTS `AccountFlags`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `AccountFlags` (
  `A_Number` int(8) unsigned zerofill NOT NULL,
  `Agreement_Signed` tinyint(1) NOT NULL DEFAULT '0',
  `Ticket_Edit` tinyint(1) NOT NULL DEFAULT '0',
  `TStamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`A_Number`),
  CONSTRAINT `FK_accountflags` FOREIGN KEY (`A_Number`) REFERENCES `Account` (`A_Number`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `AccountHist`
--

DROP TABLE IF EXISTS `AccountFlagsHist`;
DROP TABLE IF EXISTS `AccountHist`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `AccountHist` (
  `A_Number` int(8) unsigned zerofill NOT NULL DEFAULT '00000000',
  `A_Name` varchar(30) NOT NULL DEFAULT '',
  `Billing_NH` varchar(12) NOT NULL DEFAULT '',
  `Address` tinytext NOT NULL,
  `County` tinytext NOT NULL,
  `Country` tinytext NOT NULL,
  `Web_Address` tinytext NOT NULL,
  `Phone` tinytext NOT NULL,
  `Fax` tinytext NOT NULL,
  `A_Status` tinytext NOT NULL,
  `A_Status_Dt` date NOT NULL DEFAULT '0000-00-00',
  `A_Tariff` tinytext NOT NULL,
  `Credit_Limit` decimal(10,2) NOT NULL DEFAULT '0.00',
  `A_Reg_Dt` date NOT NULL DEFAULT '0000-00-00',
  `A_TStamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `Invoice_Freq` varchar(10) NOT NULL DEFAULT '',
  `Next_Invoice_Month` varchar(10) NOT NULL DEFAULT '',
  `A_Remarks` tinytext NOT NULL,
  `Chng_Dt` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `Chng_NH` varchar(12) DEFAULT NULL,
  PRIMARY KEY (`A_Number`,`Chng_Dt`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `AccountFlagsHist`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `AccountFlagsHist` (
  `A_Number` int(8) unsigned zerofill NOT NULL,
  `Agreement_Signed` tinyint(1) NOT NULL DEFAULT '0',
  `Ticket_Edit` tinyint(1) NOT NULL DEFAULT '0',
  `TStamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `Chng_Dt` datetime NOT NULL,
  PRIMARY KEY (`A_Number`,`Chng_Dt`),
  CONSTRAINT `FK_accountflagshist` FOREIGN KEY (`A_Number`, `Chng_Dt`) REFERENCES `AccountHist` (`A_Number`, `Chng_Dt`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `AccountVersionIndex`
--

DROP TABLE IF EXISTS `AccountVersionIndex`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `AccountVersionIndex` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=402 DEFAULT CHARSET=latin1 COMMENT='for generating AccountVersion numbers';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `AllTicks`
--

DROP TABLE IF EXISTS `AllTicks`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `AllTicks` (
  `T_Date` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `T_Number` varchar(10) NOT NULL DEFAULT '',
  `Bill_NH` varchar(20) NOT NULL DEFAULT '',
  `D_Name` varchar(66) NOT NULL DEFAULT '',
  `D_Holder` tinytext NOT NULL,
  `T_Class` tinytext NOT NULL,
  `T_Category` tinytext NOT NULL,
  `Accept` char(1) NOT NULL DEFAULT '',
  PRIMARY KEY (`D_Name`,`T_Date`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ApiPermissions`
--

DROP TABLE IF EXISTS `ApiPermissions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ApiPermissions` (
  `id` int(11) NOT NULL,
  `command` varchar(64) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `command` (`command`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `App_Config`
--

DROP TABLE IF EXISTS `App_Config`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `App_Config` (
  `Cfg_Key` varchar(255) NOT NULL COMMENT 'config entry key',
  `Cfg_Type` varchar(255) NOT NULL COMMENT 'config entry type',
  `Cfg_Value` varchar(255) DEFAULT NULL COMMENT 'config entry value',
  UNIQUE KEY `Cfg_Key` (`Cfg_Key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Auth_Code`
--

DROP TABLE IF EXISTS `Auth_Code`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Auth_Code` (
  `Nic_Handle` varchar(15) NOT NULL,
  `Request_Id` varchar(45) NOT NULL,
  `Auth_Code` char(6) NOT NULL,
  `Status` enum('W','A','E','N') NOT NULL DEFAULT 'W',
  `Auth_TS` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`Request_Id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Bulk_Transfer`
--

DROP TABLE IF EXISTS `Bulk_Transfer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Bulk_Transfer` (
  `ID` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `Losing_Account` int(10) unsigned NOT NULL COMMENT 'ID of the losing account',
  `Gaining_Account` int(10) unsigned NOT NULL COMMENT 'ID of the gaining account',
  `Remarks` varchar(255) NOT NULL COMMENT 'remarks',
  `Completed` datetime DEFAULT NULL COMMENT 'time when the transfer was completed',
  `Hostmaster_NH` varchar(12) DEFAULT NULL COMMENT 'nh of the hostmaster who closed the transfer',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Bulk_Transfer_Domain`
--

DROP TABLE IF EXISTS `Bulk_Transfer_Domain`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Bulk_Transfer_Domain` (
  `Bulk_Transfer_ID` int(10) unsigned NOT NULL COMMENT 'ID',
  `Domain_Name` varchar(66) NOT NULL COMMENT 'Name of the domain to be transferred',
  `Transfer_Dt` datetime DEFAULT NULL COMMENT 'time when the domain was transferred',
  `Transfer_NH` varchar(12) DEFAULT NULL COMMENT 'nh of the hostmaster, who transferred the domain',
  PRIMARY KEY (`Bulk_Transfer_ID`,`Domain_Name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Category`
--

DROP TABLE IF EXISTS `Category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Category` (
  `id` int(11) NOT NULL,
  `name` varchar(128) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `CategoryPermission`
--

DROP TABLE IF EXISTS `CategoryPermission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `CategoryPermission` (
  `Nic_Handle` varchar(20) NOT NULL DEFAULT '',
  `Category_Id` int(11) NOT NULL,
  PRIMARY KEY (`Nic_Handle`,`Category_Id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Class`
--

DROP TABLE IF EXISTS `Class`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Class` (
  `id` int(11) NOT NULL,
  `name` varchar(128) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Class_Category_Permission`
--

DROP TABLE IF EXISTS `Class_Category_Permission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Class_Category_Permission` (
  `Nic_Handle` varchar(20) NOT NULL,
  `Class_id` int(11) NOT NULL,
  `Category_id` int(11) NOT NULL,
  PRIMARY KEY (`Nic_Handle`,`Class_id`,`Category_id`),
  KEY `FK_class_to_category` (`Class_id`,`Category_id`),
  CONSTRAINT `FK_class_to_category` FOREIGN KEY (`Class_id`, `Category_id`) REFERENCES `Class_to_Category` (`class_id`, `category_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Class_to_Category`
--

DROP TABLE IF EXISTS `Class_to_Category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Class_to_Category` (
  `class_id` int(11) NOT NULL,
  `category_id` int(11) NOT NULL,
  PRIMARY KEY (`class_id`,`category_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Contact`
--

DROP TABLE IF EXISTS `Contact`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Contact` (
  `D_Name` varchar(66) NOT NULL DEFAULT '',
  `Contact_NH` varchar(12) NOT NULL DEFAULT '',
  `Type` char(1) NOT NULL DEFAULT '',
  PRIMARY KEY (`D_Name`,`Contact_NH`,`Type`),
  KEY `idx_contact_contact_nh` (`Contact_NH`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ContactHist`
--

DROP TABLE IF EXISTS `ContactHist`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ContactHist` (
  `D_Name` varchar(66) NOT NULL DEFAULT '',
  `Contact_NH` varchar(12) NOT NULL DEFAULT '',
  `Type` char(1) NOT NULL DEFAULT '',
  `Chng_NH` varchar(12) NOT NULL DEFAULT '',
  `Chng_Dt` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`Chng_Dt`,`D_Name`,`Contact_NH`,`Type`),
  KEY `D_Name` (`D_Name`),
  KEY `Contact_NH` (`Contact_NH`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Counties`
--

DROP TABLE IF EXISTS `Counties`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Counties` (
  `CountryCode` int(3) unsigned NOT NULL DEFAULT '0',
  `County` varchar(100) NOT NULL DEFAULT '',
  PRIMARY KEY (`CountryCode`,`County`),
  UNIQUE KEY `CountryCode` (`CountryCode`,`County`),
  KEY `CountryCode_2` (`CountryCode`,`County`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Countries`
--

DROP TABLE IF EXISTS `Countries`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Countries` (
  `CountryCode` int(2) unsigned NOT NULL AUTO_INCREMENT,
  `Country` char(100) DEFAULT NULL,
  `vat_category` char(1) CHARACTER SET utf8 NOT NULL DEFAULT 'A' COMMENT 'vat category code',
  PRIMARY KEY (`CountryCode`),
  UNIQUE KEY `CountryCode` (`CountryCode`),
  KEY `CountryCode_2` (`CountryCode`),
  KEY `fk_vat_category` (`vat_category`),
  CONSTRAINT `fk_vat_category` FOREIGN KEY (`vat_category`) REFERENCES `vat_category` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=282 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `DNS`
--

DROP TABLE IF EXISTS `DNS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `DNS` (
  `D_Name` char(66) NOT NULL DEFAULT '',
  `DNS_Name` char(70) NOT NULL DEFAULT '',
  `DNS_IpAddr` char(20) DEFAULT NULL,
  `DNS_Order` tinyint(2) NOT NULL DEFAULT '0',
  PRIMARY KEY (`D_Name`,`DNS_Name`,`DNS_Order`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=FIXED;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `DNSHist`
--

DROP TABLE IF EXISTS `DNSHist`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `DNSHist` (
  `D_Name` varchar(66) NOT NULL DEFAULT '',
  `DNS_Name` varchar(70) NOT NULL DEFAULT '',
  `DNS_IpAddr` varchar(20) NOT NULL DEFAULT '',
  `DNS_Order` tinyint(2) NOT NULL DEFAULT '0',
  `Chng_NH` varchar(12) NOT NULL DEFAULT '',
  `Chng_Dt` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`D_Name`,`DNS_Name`,`Chng_Dt`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `DNSSEC_DS`
--

DROP TABLE IF EXISTS `DNSSEC_DS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `DNSSEC_DS` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `D_Name` char(66) NOT NULL DEFAULT '',
  `Key_Tag` int(6) NOT NULL DEFAULT '0',
  `Alg` int(1) NOT NULL DEFAULT '0',
  `Digest_Type` int(1) NOT NULL DEFAULT '1',
  `Digest` varchar(255) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`,`D_Name`,`Digest`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 ROW_FORMAT=FIXED;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `DNSSearch_RateLim`
--

DROP TABLE IF EXISTS `DNSSearch_RateLim`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `DNSSearch_RateLim` (
  `Remote_IP` varchar(39) NOT NULL DEFAULT '',
  `Query_TS` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `D_Name` varchar(66) NOT NULL DEFAULT '',
  KEY `Remote_IP` (`Remote_IP`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `DNS_Check_Notification`
--

DROP TABLE IF EXISTS `DNS_Check_Notification`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `DNS_Check_Notification` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT 'notification id',
  `Domain_Name` varchar(66) NOT NULL COMMENT 'notification domain name',
  `DNS_Name` varchar(70) NOT NULL COMMENT 'notification nameserver name',
  `Error_Message` text NOT NULL COMMENT 'notification error message',
  `Nic_Handle` varchar(12) NOT NULL DEFAULT '' COMMENT 'notification recipient',
  `Email` text NOT NULL COMMENT 'notification recipient email',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=85854 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `DNS_Check_Notification_Date`
--

DROP TABLE IF EXISTS `DNS_Check_Notification_Date`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `DNS_Check_Notification_Date` (
  `Nic_Handle` varchar(12) NOT NULL COMMENT 'nic handle',
  `Next_Notification_Date` datetime NOT NULL COMMENT 'next notification date',
  PRIMARY KEY (`Nic_Handle`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `DSM_Action`
--

DROP TABLE IF EXISTS `DSM_Action`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `DSM_Action` (
  `Id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Id of the action',
  `Action_Name` varchar(255) NOT NULL COMMENT 'The name of the action to be fired',
  `Comment` text COMMENT 'Action description',
  PRIMARY KEY (`Id`),
  UNIQUE KEY `Action_Name` (`Action_Name`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 COMMENT='Holds valid names of DSM actions';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `DSM_Event`
--

DROP TABLE IF EXISTS `DSM_Event`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `DSM_Event` (
  `Id` int(11) NOT NULL COMMENT 'ID of the event',
  `Name` varchar(255) NOT NULL COMMENT 'Name of the event',
  `Comment` text COMMENT 'Event description',
  PRIMARY KEY (`Id`),
  UNIQUE KEY `Dsm_Event_Name` (`Name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Holds valid names of DSM Events';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `DSM_State`
--

DROP TABLE IF EXISTS `DSM_State`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `DSM_State` (
  `State` int(11) NOT NULL COMMENT 'DSM State',
  `Published` char(1) NOT NULL DEFAULT 'N' COMMENT 'Published flag',
  `D_Holder_Type` char(3) DEFAULT NULL COMMENT 'Domain Holder Type (B=Billable, C=Charity, I=IEDR, =Non-Billable)',
  `Renewal_Mode` char(3) DEFAULT NULL COMMENT 'Renewal mode (N=No auto renew, R=Renew Once, A=Autorenew)',
  `WIPO` char(1) DEFAULT NULL COMMENT 'WIPO dispute flag',
  `Cust_Type` char(3) DEFAULT NULL COMMENT 'Customer type (R=Registrar, D=Direct)',
  `NRP_Status` char(4) DEFAULT NULL,
  `Comment` text COMMENT 'State description',
  PRIMARY KEY (`State`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Defines valid DSM states';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `DSM_Transition`
--

DROP TABLE IF EXISTS `DSM_Transition`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `DSM_Transition` (
  `Id` int(11) NOT NULL COMMENT 'ID of the transition',
  `Event_Id` int(11) NOT NULL COMMENT 'ID of the event which causes this transition to be used when changing the tate',
  `Begin_State` int(11) NOT NULL COMMENT 'Starting state for the transition',
  `End_State` int(11) NOT NULL COMMENT 'End state for th transition',
  `Comment` text COMMENT 'Description for the transition',
  PRIMARY KEY (`Id`),
  UNIQUE KEY `Dsm_Transition_From_State` (`Event_Id`,`Begin_State`),
  KEY `FK_dsm_transition_start_state` (`Begin_State`),
  KEY `FK_dsm_transition_end_state` (`End_State`),
  KEY `FK_dsm_transition_event` (`Event_Id`),
  CONSTRAINT `FK_dsm_transition_end_state` FOREIGN KEY (`End_State`) REFERENCES `DSM_State` (`State`),
  CONSTRAINT `FK_dsm_transition_event` FOREIGN KEY (`Event_Id`) REFERENCES `DSM_Event` (`Id`),
  CONSTRAINT `FK_dsm_transition_start_state` FOREIGN KEY (`Begin_State`) REFERENCES `DSM_State` (`State`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Defines valid DSM transitions between domain states';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `DSM_Transition_Action`
--

DROP TABLE IF EXISTS `DSM_Transition_Action`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `DSM_Transition_Action` (
  `Id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Artificial primary key',
  `Transition_Id` int(11) NOT NULL COMMENT 'ID of the transition',
  `Order` int(11) NOT NULL COMMENT 'Defines the order in which the actions will be fired for the given transition',
  `Action_Id` int(11) NOT NULL COMMENT 'The ID of the action to be fired',
  `Comment` text COMMENT 'Action description',
  `Action_Param` varchar(255) DEFAULT NULL COMMENT 'optional action parameter',
  PRIMARY KEY (`Id`),
  UNIQUE KEY `Dsm_Transition_Action_NK` (`Transition_Id`,`Order`),
  KEY `Dsm_Transition_Action_action` (`Action_Id`),
  CONSTRAINT `FK_dsm_transition_action_actionId` FOREIGN KEY (`Action_Id`) REFERENCES `DSM_Action` (`Id`),
  CONSTRAINT `FK_dsm_transition_action_transitionId` FOREIGN KEY (`Transition_Id`) REFERENCES `DSM_Transition` (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=934 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `DeleteListHist`
--

DROP TABLE IF EXISTS `DeleteListHist`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `DeleteListHist` (
  `D_Name` varchar(66) NOT NULL DEFAULT '',
  `D_Add_Dt` date NOT NULL DEFAULT '0000-00-00',
  `D_Add_Id` varchar(12) DEFAULT NULL,
  PRIMARY KEY (`D_Name`,`D_Add_Dt`),
  KEY `D_Name` (`D_Name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Deposit`
--

DROP TABLE IF EXISTS `Deposit`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Deposit` (
  `Nic_Handle` varchar(20) NOT NULL DEFAULT '',
  `Trans_Dt` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `Open_Bal` decimal(10,2) NOT NULL DEFAULT '0.00',
  `Close_Bal` decimal(10,2) NOT NULL DEFAULT '0.00',
  `Order_ID` varchar(50) NOT NULL DEFAULT '',
  `Notification_Sent` char(1) NOT NULL DEFAULT 'N' COMMENT 'notification email sent flag',
  `Trans_Amount` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT 'transaction amount',
  `Trans_Type` varchar(20) NOT NULL DEFAULT '' COMMENT 'transaction type',
  `Corrector_NH` varchar(20) DEFAULT NULL COMMENT 'nic handle of deposit corrector',
  `Remark` text COMMENT 'remark',
  PRIMARY KEY (`Nic_Handle`),
  KEY `Nic_Handle` (`Nic_Handle`),
  KEY `Nic_Handle_2` (`Nic_Handle`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `DepositHist`
--

DROP TABLE IF EXISTS `DepositHist`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `DepositHist` (
  `Nic_Handle` varchar(20) NOT NULL DEFAULT '',
  `Op_Dt` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `Trans_Dt` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `Open_Bal` decimal(10,2) NOT NULL DEFAULT '0.00',
  `Close_Bal` decimal(10,2) NOT NULL DEFAULT '0.00',
  `Order_ID` varchar(50) NOT NULL DEFAULT '',
  `Notification_Sent` char(1) NOT NULL DEFAULT 'N' COMMENT 'notification email sent flag',
  `Trans_Amount` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT 'transaction amount',
  `Trans_Type` varchar(20) NOT NULL DEFAULT '' COMMENT 'transaction type',
  `Corrector_NH` varchar(20) DEFAULT NULL COMMENT 'nic handle of deposit corrector',
  `Remark` text COMMENT 'remark',
  `id` int(10) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`),
  KEY `Order_ID` (`Order_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=42126 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `DepositHist_Old`
--

DROP TABLE IF EXISTS `DepositHist_Old`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `DepositHist_Old` (
  `Nic_Handle` varchar(20) NOT NULL DEFAULT '',
  `Op_Dt` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `Trans_Dt` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `Open_Bal` decimal(10,2) NOT NULL DEFAULT '0.00',
  `Close_Bal` decimal(10,2) NOT NULL DEFAULT '0.00',
  `Order_ID` varchar(50) NOT NULL DEFAULT '',
  KEY `Order_ID` (`Order_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `DoaVersionIndex`
--

DROP TABLE IF EXISTS `DoaVersionIndex`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `DoaVersionIndex` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=606 DEFAULT CHARSET=latin1 COMMENT='for generating DoaVersionIndex';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Domain`
--

DROP TABLE IF EXISTS `Domain`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Domain` (
  `D_Name` varchar(66) NOT NULL DEFAULT '',
  `D_Holder` tinytext NOT NULL,
  `D_Class` tinytext NOT NULL,
  `D_Category` tinytext NOT NULL,
  `A_Number` int(8) unsigned zerofill DEFAULT NULL,
  `_D_Status_DECOM` tinytext,
  `_D_Status_Dt_DECOM` date DEFAULT NULL,
  `D_Reg_Dt` date NOT NULL DEFAULT '0000-00-00',
  `D_Ren_Dt` date NOT NULL DEFAULT '0000-00-00',
  `D_TStamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `D_Discount` char(1) NOT NULL DEFAULT '',
  `D_Bill_Status` char(1) NOT NULL DEFAULT '',
  `D_Vat_Status` char(1) NOT NULL DEFAULT '',
  `D_Remark` text,
  `D_ClikPaid` char(1) NOT NULL DEFAULT 'N',
  `DSM_State` int(11) NOT NULL DEFAULT '0',
  `D_GIBO_Retry_Timeout` timestamp NULL DEFAULT NULL COMMENT 'GIBO domains only: the timeout for the financial check to be performed',
  `D_Susp_Dt` date DEFAULT NULL COMMENT 'Domain suspension date',
  `D_Del_Dt` date DEFAULT NULL COMMENT 'Domain deletion date',
  `D_Transfer_Dt` timestamp NULL DEFAULT NULL COMMENT 'Last transfer date',
  PRIMARY KEY (`D_Name`),
  KEY `A_Number` (`A_Number`),
  KEY `Domain.D_Holder` (`D_Holder`(255)) USING BTREE,
  KEY `D_Reg_Dt` (`D_Reg_Dt`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `DomainHist`
--

DROP TABLE IF EXISTS `DomainHist`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `DomainHist` (
  `D_Name` varchar(66) NOT NULL DEFAULT '',
  `D_Holder` varchar(255) NOT NULL,
  `D_Class` tinytext NOT NULL,
  `D_Category` tinytext NOT NULL,
  `A_Number` int(8) unsigned zerofill DEFAULT NULL,
  `D_Status` tinytext,
  `D_Status_Dt` date NOT NULL DEFAULT '0000-00-00',
  `D_Reg_Dt` date NOT NULL DEFAULT '0000-00-00',
  `D_Ren_Dt` date NOT NULL DEFAULT '0000-00-00',
  `D_TStamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `D_Discount` char(1) NOT NULL DEFAULT '',
  `D_Bill_Status` char(1) NOT NULL DEFAULT '',
  `D_Vat_Status` char(1) NOT NULL DEFAULT '',
  `D_Remark` text,
  `Chng_NH` varchar(12) NOT NULL DEFAULT '',
  `Chng_Dt` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `D_ClikPaid` char(1) NOT NULL DEFAULT 'N',
  `DSM_State` int(11) NOT NULL DEFAULT '0',
  `D_GIBO_Retry_Timeout` timestamp NULL DEFAULT NULL COMMENT 'GIBO domains only: the timeout for the financial check to be performed',
  `D_Susp_Dt` date DEFAULT NULL COMMENT 'Domain suspension date',
  `D_Del_Dt` date DEFAULT NULL COMMENT 'Domain deletion date',
  `D_Transfer_Dt` timestamp NULL DEFAULT NULL COMMENT 'Last transfer date',
  PRIMARY KEY (`Chng_Dt`,`D_Name`),
  KEY `D_Name` (`D_Name`),
  KEY `Chng_Dt` (`Chng_Dt`),
  KEY `D_Holder` (`D_Holder`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `DomainStats`
--

DROP TABLE IF EXISTS `DomainStats`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `DomainStats` (
  `S_Number` int(6) NOT NULL AUTO_INCREMENT,
  `Month` varchar(14) NOT NULL DEFAULT '',
  `Year` varchar(4) NOT NULL DEFAULT '',
  `Delegated_Domains` varchar(8) NOT NULL DEFAULT '',
  `Mail_Only` varchar(8) NOT NULL DEFAULT '',
  `Total` varchar(8) NOT NULL DEFAULT '',
  `Increase_Decrease` varchar(8) NOT NULL DEFAULT '',
  `Working_Days` varchar(8) NOT NULL DEFAULT '',
  `Registrations` varchar(8) NOT NULL DEFAULT '',
  PRIMARY KEY (`S_Number`),
  KEY `year_index` (`Year`) USING BTREE,
  KEY `month_index` (`Month`) USING BTREE,
  KEY `dd_index` (`Delegated_Domains`) USING BTREE
) ENGINE=MyISAM AUTO_INCREMENT=399 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Domain_Notifications`
--

DROP TABLE IF EXISTS `Domain_Notifications`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Domain_Notifications` (
  `D_Name` varchar(66) NOT NULL DEFAULT '',
  `Type` varchar(30) NOT NULL DEFAULT '' COMMENT 'notification type',
  `Period` int(11) NOT NULL COMMENT 'notification period',
  `Exp_Date` date NOT NULL DEFAULT '0000-00-00' COMMENT 'notification expiration date'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Email`
--

DROP TABLE IF EXISTS `Email`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Email` (
  `E_ID` int(3) unsigned NOT NULL AUTO_INCREMENT,
  `E_Name` text,
  `E_Text` text,
  `E_Subject` text,
  `E_To` text,
  `E_CC` text,
  `E_BCC` text,
  `E_From` text COMMENT 'from field',
  `active` char(1) NOT NULL DEFAULT '1' COMMENT 'email active flag',
  `Html` char(1) DEFAULT '0' COMMENT 'text in html format',
  PRIMARY KEY (`E_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=162 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `GIBO_Payment`
--

DROP TABLE IF EXISTS `GIBO_Payment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `GIBO_Payment` (
  `D_Name` varchar(66) NOT NULL DEFAULT '',
  `Op_Dt` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `status` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`D_Name`,`Op_Dt`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Geographical_Names`
--

DROP TABLE IF EXISTS `Geographical_Names`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Geographical_Names` (
  `Place_Name` varchar(100) NOT NULL,
  `Type` varchar(3) NOT NULL,
  PRIMARY KEY (`Place_Name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `IE_Zone_DNS`
--

DROP TABLE IF EXISTS `IE_Zone_DNS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `IE_Zone_DNS` (
  `D_Name` varchar(66) NOT NULL DEFAULT '',
  `DNS_Name` varchar(200) NOT NULL DEFAULT '',
  `IPv4_Addr` varchar(16) DEFAULT NULL,
  `IPv6_Addr` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`DNS_Name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `IE_Zone_SOA`
--

DROP TABLE IF EXISTS `IE_Zone_SOA`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `IE_Zone_SOA` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `D_Name` varchar(66) NOT NULL DEFAULT '',
  `TTL` int(10) NOT NULL DEFAULT '0',
  `MName` varchar(200) NOT NULL DEFAULT '',
  `RName` varchar(200) NOT NULL DEFAULT '',
  `Serial_Dt` date NOT NULL DEFAULT '0000-00-00',
  `Serial_No` int(2) NOT NULL DEFAULT '0',
  `Refresh` int(10) NOT NULL DEFAULT '0',
  `Retry` int(10) NOT NULL DEFAULT '0',
  `Expire` int(10) NOT NULL DEFAULT '0',
  `Minim` int(10) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `INCOMING_DOC`
--

DROP TABLE IF EXISTS `INCOMING_DOC`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `INCOMING_DOC` (
  `DOC_ID` int(11) NOT NULL AUTO_INCREMENT,
  `CR_DATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `DOC_TYPE` varchar(30) DEFAULT NULL,
  `FILENAME` varchar(100) DEFAULT NULL,
  `DOC_PURPOSE` varchar(30) DEFAULT NULL,
  `DOC_SOURCE` varchar(80) DEFAULT NULL,
  `ACCOUNT_NUMBER` int(8) unsigned zerofill DEFAULT NULL,
  `DOC_CREATOR` varchar(12) DEFAULT NULL,
  PRIMARY KEY (`DOC_ID`),
  KEY `FK_DOC` (`ACCOUNT_NUMBER`),
  CONSTRAINT `FK_DOC` FOREIGN KEY (`ACCOUNT_NUMBER`) REFERENCES `Account` (`A_Number`)
) ENGINE=InnoDB AUTO_INCREMENT=295368 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `INCOMING_DOC_DOMAINS`
--

DROP TABLE IF EXISTS `INCOMING_DOC_DOMAINS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `INCOMING_DOC_DOMAINS` (
  `DOC_ID` int(11) NOT NULL,
  `DOMAIN_NAME` varchar(66) NOT NULL,
  KEY `FK_DOC_DOMAINS` (`DOC_ID`),
  KEY `INCOMING_DOC_DOMAINS.DOMAIN_NAME` (`DOMAIN_NAME`) USING BTREE,
  CONSTRAINT `FK_DOC_DOMAINS` FOREIGN KEY (`DOC_ID`) REFERENCES `INCOMING_DOC` (`DOC_ID`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `IPv6_Glue`
--

DROP TABLE IF EXISTS `IPv6_Glue`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `IPv6_Glue` (
  `DNS_Name` varchar(70) NOT NULL DEFAULT '',
  `IPv6_Addr` varchar(39) NOT NULL DEFAULT '',
  `Remarks` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`DNS_Name`,`IPv6_Addr`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Invoice`
--

DROP TABLE IF EXISTS `Invoice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Invoice` (
  `ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `Invoice_Number` varchar(20) DEFAULT NULL COMMENT 'invoice number',
  `Account_Name` varchar(30) NOT NULL DEFAULT '' COMMENT 'account name',
  `Account_Number` int(8) NOT NULL,
  `Address1` text COMMENT 'addres field',
  `Address2` text COMMENT 'addres field',
  `Address3` text COMMENT 'addres field',
  `Billing_Nic_Handle` varchar(12) NOT NULL COMMENT 'billing nic handle id',
  `Completed` char(1) NOT NULL DEFAULT 'N' COMMENT 'completed flag',
  `Country` varchar(100) DEFAULT NULL COMMENT 'country field',
  `County` varchar(100) DEFAULT NULL COMMENT 'county field',
  `Crs_Version` varchar(20) NOT NULL COMMENT 'crs version',
  `Invoice_Date` datetime DEFAULT NULL COMMENT 'invoice creation date',
  `MD5` varchar(100) NOT NULL DEFAULT '' COMMENT 'MD5 checksum',
  `Total_Cost` int(10) unsigned DEFAULT NULL COMMENT 'total cost',
  `Total_Net_Amount` int(10) unsigned DEFAULT NULL COMMENT 'total net amount',
  `Total_Vat_Amount` int(10) unsigned DEFAULT NULL COMMENT 'total vat amount',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=3867 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `InvoiceHist`
--

DROP TABLE IF EXISTS `InvoiceHist`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `InvoiceHist` (
  `I_Inv_No` varchar(10) NOT NULL DEFAULT '0000000000',
  `I_DName` varchar(66) NOT NULL DEFAULT '',
  `I_Ren_Dt` date NOT NULL DEFAULT '0000-00-00',
  `I_Bill_NH` varchar(12) NOT NULL DEFAULT '',
  `I_Acc_No` int(10) NOT NULL DEFAULT '0',
  `I_DHolder` tinytext NOT NULL,
  `I_Discount` char(1) NOT NULL DEFAULT 'N',
  `I_Amount` decimal(7,2) NOT NULL DEFAULT '0.00',
  `I_Status` varchar(10) NOT NULL DEFAULT '',
  `I_Reg_Dt` date NOT NULL DEFAULT '0000-00-00',
  `I_NewReg` char(1) NOT NULL DEFAULT 'N',
  `I_Create_prc` varchar(20) NOT NULL DEFAULT '',
  `I_Inv_Dt` date NOT NULL DEFAULT '0000-00-00',
  `I_Inv_NH` varchar(12) DEFAULT NULL,
  `I_Inv_Vat` char(1) DEFAULT 'Y',
  `I_Inv_ClikPaid` char(1) DEFAULT 'N',
  `I_Inv_Vat_Amt` decimal(7,2) DEFAULT '2.00',
  `I_Inv_Type` char(3) NOT NULL DEFAULT '',
  PRIMARY KEY (`I_Inv_No`,`I_DName`,`I_Inv_Dt`,`I_Inv_Type`),
  KEY `idx_bill_invoicehist` (`I_Bill_NH`),
  KEY `I_DName` (`I_DName`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `LastInvoiceNumber`
--

DROP TABLE IF EXISTS `LastInvoiceNumber`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `LastInvoiceNumber` (
  `Year` year(4) NOT NULL COMMENT 'invoice year',
  `Last_Number` int(10) unsigned NOT NULL COMMENT 'last invoice number',
  PRIMARY KEY (`Year`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Levels_To_ApiPermissions`
--

DROP TABLE IF EXISTS `Levels_To_ApiPermissions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Levels_To_ApiPermissions` (
  `NH_Level` int(4) NOT NULL,
  `permission_id` int(11) NOT NULL,
  UNIQUE KEY `NH_Level` (`NH_Level`,`permission_id`),
  KEY `c_lev2perm_perm` (`permission_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `LoginAttemptFailure`
--

DROP TABLE IF EXISTS `LoginAttemptFailure`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `LoginAttemptFailure` (
  `Nic_Handle` varchar(12) NOT NULL COMMENT 'Customer id',
  `Time_Stamp` datetime NOT NULL COMMENT 'last failed login',
  `Remote_IP` varchar(20) DEFAULT NULL COMMENT 'IP Address of Customer',
  `Duration` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `NicHandle`
--

DROP TABLE IF EXISTS `NicHandle`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `NicHandle` (
  `Nic_Handle` varchar(12) NOT NULL DEFAULT '',
  `NH_Name` tinytext NOT NULL,
  `A_Number` int(8) unsigned zerofill DEFAULT NULL,
  `Co_Name` tinytext NOT NULL,
  `NH_Address` tinytext NOT NULL,
  `NH_County` tinytext,
  `NH_Country` tinytext,
  `NH_Email` tinytext NOT NULL,
  `NH_Status` tinytext NOT NULL,
  `NH_Status_Dt` date NOT NULL DEFAULT '0000-00-00',
  `NH_Reg_Dt` date NOT NULL DEFAULT '0000-00-00',
  `NH_TStamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `NH_BillC_Ind` char(1) NOT NULL DEFAULT '',
  `NH_Remark` tinytext,
  `NH_Creator` varchar(12) DEFAULT NULL,
  `Vat_Category` char(1) DEFAULT NULL COMMENT 'vat category',
  PRIMARY KEY (`Nic_Handle`),
  KEY `A_Number` (`A_Number`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Payment`
--

DROP TABLE IF EXISTS `Payment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Payment` (
  `Billing_Contact` varchar(12) NOT NULL DEFAULT '',
  `Credit_Card_Type` tinytext NOT NULL,
  `Name_on_Card` tinytext NOT NULL,
  `Card_Number` tinytext NOT NULL,
  `Expiry_Date` varchar(5) NOT NULL DEFAULT '',
  `Bank_Account_Holder` tinytext NOT NULL,
  `Bank` tinytext NOT NULL,
  `NSC` tinytext NOT NULL,
  `AC_Number` tinytext NOT NULL,
  `Address` tinytext NOT NULL,
  `VAT_Reg_ID` tinytext NOT NULL,
  `VAT_exempt` tinytext NOT NULL,
  PRIMARY KEY (`Billing_Contact`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `PaymentHist`
--

DROP TABLE IF EXISTS `PaymentHist`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `PaymentHist` (
  `Billing_Contact` varchar(12) NOT NULL DEFAULT '',
  `Credit_Card_Type` tinytext NOT NULL,
  `Name_on_Card` tinytext NOT NULL,
  `Card_Number` tinytext NOT NULL,
  `Expiry_Date` varchar(5) NOT NULL DEFAULT '',
  `Bank_Account_Holder` tinytext NOT NULL,
  `Bank` tinytext NOT NULL,
  `NSC` tinytext NOT NULL,
  `AC_Number` tinytext NOT NULL,
  `Address` tinytext NOT NULL,
  `VAT_Reg_ID` tinytext NOT NULL,
  `VAT_exempt` tinytext NOT NULL,
  `Chng_NH` varchar(12) NOT NULL DEFAULT '',
  `Chng_Dt` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`Chng_Dt`,`Billing_Contact`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `PendingTicket`
--

DROP TABLE IF EXISTS `PendingTicket`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `PendingTicket` (
  `T_Number` int(10) NOT NULL AUTO_INCREMENT,
  `T_Type` char(1) NOT NULL DEFAULT '',
  `D_Name` varchar(66) NOT NULL DEFAULT '',
  `DN_Fail_Cd` tinyint(3) DEFAULT NULL,
  `D_Holder` tinytext NOT NULL,
  `DH_Fail_Cd` tinyint(3) DEFAULT NULL,
  `A_Number` int(8) unsigned zerofill NOT NULL DEFAULT '00000000',
  `AC_Fail_Cd` tinyint(3) DEFAULT NULL,
  `T_Class` tinytext NOT NULL,
  `T_Category` tinytext NOT NULL,
  `T_Remark` text,
  `Admin_NH1` varchar(12) NOT NULL DEFAULT '',
  `ANH1_Fail_Cd` tinyint(3) DEFAULT NULL,
  `Admin_NH2` varchar(12) DEFAULT NULL,
  `ANH2_Fail_Cd` tinyint(3) DEFAULT NULL,
  `Tech_NH` varchar(12) NOT NULL DEFAULT '',
  `TNH_Fail_Cd` tinyint(3) DEFAULT NULL,
  `Bill_NH` varchar(12) NOT NULL DEFAULT '',
  `BNH_Fail_Cd` tinyint(3) DEFAULT NULL,
  `Creator_NH` varchar(12) NOT NULL DEFAULT '',
  `DNS_Name1` tinytext NOT NULL,
  `DNS1_Fail_Cd` tinyint(3) DEFAULT NULL,
  `DNS_IP1` tinytext,
  `DNSIP1_Fail_Cd` tinyint(3) DEFAULT NULL,
  `DNS_Name2` tinytext NOT NULL,
  `DNS2_Fail_Cd` tinyint(3) DEFAULT NULL,
  `DNS_IP2` tinytext,
  `DNSIP2_Fail_Cd` tinyint(3) DEFAULT NULL,
  `DNS_Name3` tinytext,
  `DNS3_Fail_Cd` tinyint(3) DEFAULT NULL,
  `DNS_IP3` tinytext,
  `DNSIP3_Fail_Cd` tinyint(3) DEFAULT NULL,
  `Admin_Status` tinyint(2) unsigned NOT NULL DEFAULT '0',
  `Ad_StatusDt` date NOT NULL DEFAULT '0000-00-00',
  `Tech_Status` tinyint(2) unsigned NOT NULL DEFAULT '0',
  `T_Status_Dt` date NOT NULL DEFAULT '0000-00-00',
  `CheckedOut` char(1) NOT NULL DEFAULT 'N',
  `CheckedOutTo` varchar(12) DEFAULT NULL,
  `T_Reg_Dt` date DEFAULT NULL,
  `T_Ren_Dt` date DEFAULT NULL,
  `T_TStamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `H_Remark` text,
  `T_Class_Fail_Cd` tinyint(3) DEFAULT NULL,
  `T_Category_Fail_Cd` tinyint(3) DEFAULT NULL,
  `T_Created_TS` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `Opt_Cert` char(1) DEFAULT 'N',
  `T_ClikPaid` char(1) DEFAULT 'N',
  `Fee` float DEFAULT '0',
  `VAT` char(1) DEFAULT 'Y',
  `Session_Id` tinytext,
  `Date_Time` varchar(20) DEFAULT NULL,
  `Approval_Code` varchar(10) DEFAULT NULL,
  `Description` tinytext,
  `Transaction_Amount` varchar(10) DEFAULT NULL,
  `Transaction_Currency_Code` varchar(10) DEFAULT NULL,
  `Charged_Amount` varchar(10) DEFAULT NULL,
  `Charged_Currency_Code` varchar(10) DEFAULT NULL,
  `Exchange_Rate` varchar(10) DEFAULT NULL,
  `CardNumber` varchar(18) DEFAULT NULL,
  `Expiry_Date` varchar(4) DEFAULT NULL,
  `Transaction_Id` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`T_Number`),
  UNIQUE KEY `Number` (`T_Number`),
  KEY `D_Name` (`D_Name`)
) ENGINE=InnoDB AUTO_INCREMENT=39457 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `PersistedCommands`
--

DROP TABLE IF EXISTS `PersistedCommands`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `PersistedCommands` (
  `cId` bigint(20) NOT NULL AUTO_INCREMENT,
  `P_CreateDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `P_NicHandle` varchar(12) NOT NULL,
  `P_RequestHash` varchar(32) NOT NULL,
  `P_Response` text,
  PRIMARY KEY (`cId`),
  UNIQUE KEY `cId` (`cId`),
  KEY `persistedCommandsIdx` (`P_CreateDate`,`P_NicHandle`,`P_RequestHash`),
  KEY `P_NicHandle` (`P_NicHandle`) USING BTREE
) ENGINE=MyISAM AUTO_INCREMENT=20461535 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Product`
--

DROP TABLE IF EXISTS `Product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Product` (
  `P_Code` varchar(16) NOT NULL DEFAULT '',
  `P_Short_Desc` varchar(50) NOT NULL DEFAULT '',
  `P_Long_Desc` varchar(255) DEFAULT NULL,
  `P_Price` decimal(5,2) NOT NULL DEFAULT '0.00',
  `P_Duration` float NOT NULL DEFAULT '0',
  `P_Active` tinyint(3) unsigned NOT NULL DEFAULT '1',
  `P_Default` tinyint(3) unsigned NOT NULL DEFAULT '0',
  `P_Valid_From` date DEFAULT NULL,
  `P_Valid_To` date DEFAULT NULL,
  `P_Reg` tinyint(3) unsigned NOT NULL DEFAULT '1',
  `P_Ren` tinyint(3) unsigned NOT NULL DEFAULT '1',
  `P_Guest` tinyint(3) unsigned NOT NULL DEFAULT '1',
  `P_Disp_Order` smallint(5) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`P_Code`),
  UNIQUE KEY `P_Code` (`P_Code`),
  KEY `P_Code_2` (`P_Code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `RealVoid`
--

DROP TABLE IF EXISTS `RealVoid`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `RealVoid` (
  `R_Order_ID` varchar(50) NOT NULL DEFAULT '',
  `R_AUTHCODE` varchar(15) NOT NULL DEFAULT '',
  `R_PASREF` varchar(25) NOT NULL DEFAULT '',
  `Trans_Dt` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`R_Order_ID`,`Trans_Dt`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='Codes for Realex Voids/Rebates';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Receipts`
--

DROP TABLE IF EXISTS `Receipts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Receipts` (
  `D_Name` varchar(66) NOT NULL DEFAULT '',
  `Nic_Handle` varchar(20) NOT NULL DEFAULT '',
  `Inv_No` varchar(20) NOT NULL DEFAULT '',
  `Trans_Dt` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `Order_ID` varchar(50) NOT NULL DEFAULT '',
  `Remote_IP` varchar(35) NOT NULL DEFAULT '',
  `VAT` decimal(10,2) NOT NULL DEFAULT '0.00',
  `Amount` decimal(10,2) NOT NULL DEFAULT '0.00',
  `Batch_Total` decimal(10,2) NOT NULL DEFAULT '0.00',
  PRIMARY KEY (`D_Name`,`Trans_Dt`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='Should be money in the realex';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ReceiptsHist`
--

DROP TABLE IF EXISTS `ReceiptsHist`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ReceiptsHist` (
  `D_Name` varchar(66) NOT NULL DEFAULT '',
  `Nic_Handle` varchar(20) NOT NULL DEFAULT '',
  `Inv_No` varchar(20) NOT NULL DEFAULT '',
  `Trans_Dt` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `Order_ID` varchar(50) NOT NULL DEFAULT '',
  `Remote_IP` varchar(35) NOT NULL DEFAULT '',
  `VAT` decimal(10,2) NOT NULL DEFAULT '0.00',
  `Amount` decimal(10,2) NOT NULL DEFAULT '0.00',
  `Batch_Total` decimal(10,2) NOT NULL DEFAULT '0.00',
  `Date` date NOT NULL DEFAULT '0000-00-00',
  `ID` varchar(20) NOT NULL DEFAULT '',
  PRIMARY KEY (`D_Name`,`Trans_Dt`),
  KEY `Order_ID` (`Order_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Reg_Accuracy`
--

DROP TABLE IF EXISTS `Reg_Accuracy`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Reg_Accuracy` (
  `T_Date` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `Bill_NH` varchar(20) NOT NULL DEFAULT '',
  `D_Name` varchar(66) NOT NULL DEFAULT '',
  `D_Holder` tinytext NOT NULL,
  `T_Class` tinytext NOT NULL,
  `T_Category` tinytext NOT NULL,
  `Accept` char(1) NOT NULL DEFAULT '',
  PRIMARY KEY (`D_Name`,`T_Date`),
  KEY `idx_reg_accuracy_d_name` (`D_Name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `RenDates`
--

DROP TABLE IF EXISTS `RenDates`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `RenDates` (
  `D_Name` varchar(66) NOT NULL,
  `D_Ren_Dt` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Reseller_Defaults`
--

DROP TABLE IF EXISTS `Reseller_Defaults`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Reseller_Defaults` (
  `Nic_Handle` varchar(16) NOT NULL DEFAULT '',
  `Tech_C` varchar(15) NOT NULL DEFAULT '',
  `N_Server1` varchar(70) NOT NULL DEFAULT '',
  `N_Server2` varchar(70) NOT NULL DEFAULT '',
  `N_Server3` varchar(70) NOT NULL DEFAULT '',
  `Dns_Notification_Period` int(11) DEFAULT NULL COMMENT 'notification period in days',
  `email_invoice_format` varchar(10) NOT NULL DEFAULT 'pdf' COMMENT 'email invoice format (xml,pdf,both,none)',
  PRIMARY KEY (`Nic_Handle`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Reservation`
--

DROP TABLE IF EXISTS `Reservation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Reservation` (
  `ID` int(10) unsigned NOT NULL,
  `Nic_Handle` varchar(20) NOT NULL DEFAULT '' COMMENT 'deposit account billing nichandle',
  `Domain_Name` varchar(66) NOT NULL DEFAULT '' COMMENT 'domain name',
  `Creation_Date` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT 'reservation date',
  `Amount` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT 'reservation amount',
  `Vat_ID` smallint(5) unsigned NOT NULL COMMENT 'reservation vat id',
  `Vat_Amount` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT 'reservation vat amount',
  `Settled` char(1) NOT NULL DEFAULT 'N' COMMENT 'reservation settlement flag',
  `Settled_Date` datetime DEFAULT NULL COMMENT 'reservation settlement date',
  `Ticket_ID` int(10) DEFAULT NULL COMMENT 'ticekt id connected with reservation',
  `Duration_Months` tinyint(4) NOT NULL DEFAULT '0' COMMENT 'domain period in months',
  `Ready_For_Settlement` char(1) NOT NULL DEFAULT 'N' COMMENT 'reservation ready for settlement flag',
  `Transaction_ID` int(10) unsigned DEFAULT NULL COMMENT 'transaction id',
  `Operation_Type` varchar(30) NOT NULL DEFAULT '' COMMENT 'operation type',
  `Product_Code` varchar(16) DEFAULT NULL COMMENT 'product code',
  `Period_Start` date DEFAULT NULL COMMENT 'renewal period start',
  `Period_End` date DEFAULT NULL COMMENT 'renewal period end',
  `Payment_Method` varchar(30) NOT NULL DEFAULT '' COMMENT 'payment method',
  PRIMARY KEY (`ID`),
  KEY `FK_Vat` (`Vat_ID`),
  KEY `FK_Product` (`Product_Code`),
  CONSTRAINT `FK_Product` FOREIGN KEY (`Product_Code`) REFERENCES `Product` (`P_Code`),
  CONSTRAINT `FK_Vat` FOREIGN KEY (`Vat_ID`) REFERENCES `Vat` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ReservationHist`
--

DROP TABLE IF EXISTS `ReservationHist`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ReservationHist` (
  `ID` int(10) unsigned NOT NULL,
  `Nic_Handle` varchar(20) NOT NULL DEFAULT '' COMMENT 'deposit account billing nichandle',
  `Domain_Name` varchar(66) NOT NULL DEFAULT '' COMMENT 'domain name',
  `Duration_Months` tinyint(4) NOT NULL DEFAULT '0' COMMENT 'domain period in months',
  `Creation_Date` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT 'reservation date',
  `Amount` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT 'reservation amount',
  `Vat_ID` smallint(5) unsigned NOT NULL COMMENT 'reservation vat id',
  `Vat_Amount` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT 'reservation vat amount',
  `Ready_For_Settlement` char(1) NOT NULL DEFAULT 'N' COMMENT 'reservation ready for settlement flag',
  `Settled` char(1) NOT NULL DEFAULT 'N' COMMENT 'reservation settlement flag',
  `Settled_Date` datetime DEFAULT NULL COMMENT 'reservation settlement date',
  `Ticket_ID` int(10) DEFAULT NULL COMMENT 'ticekt id connected with reservation',
  `Transaction_ID` int(10) unsigned DEFAULT NULL COMMENT 'transaction id',
  `Product_Code` varchar(16) DEFAULT NULL COMMENT 'product code',
  `Operation_Type` varchar(30) NOT NULL DEFAULT '' COMMENT 'operation type',
  `Period_Start` date DEFAULT NULL COMMENT 'renewal period start',
  `Period_End` date DEFAULT NULL COMMENT 'renewal period end',
  `Payment_Method` varchar(30) NOT NULL DEFAULT '' COMMENT 'payment method',
  PRIMARY KEY (`ID`),
  KEY `FK_RH_Product` (`Product_Code`),
  KEY `FK_RH_Transaction` (`Transaction_ID`),
  KEY `FK_RH_Vat` (`Vat_ID`),
  KEY `Domain_Name` (`Domain_Name`),
  KEY `Settled_Date` (`Settled_Date`),
  CONSTRAINT `FK_RH_Product` FOREIGN KEY (`Product_Code`) REFERENCES `Product` (`P_Code`),
  CONSTRAINT `FK_RH_Transaction` FOREIGN KEY (`Transaction_ID`) REFERENCES `TransactionHist` (`ID`),
  CONSTRAINT `FK_RH_Vat` FOREIGN KEY (`Vat_ID`) REFERENCES `Vat` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ReservationIndex`
--

DROP TABLE IF EXISTS `ReservationIndex`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ReservationIndex` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=53983 DEFAULT CHARSET=latin1 COMMENT='replaces auto_increment for Reservation';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ResetPass`
--

DROP TABLE IF EXISTS `ResetPass`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ResetPass` (
  `Nic_Handle` varchar(16) NOT NULL DEFAULT '',
  `Unique_ID` varchar(80) NOT NULL DEFAULT '',
  `Time_Stamp` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `Remote_IP` varchar(20) NOT NULL DEFAULT '',
  `valid` char(1) NOT NULL DEFAULT 'Y' COMMENT 'indicates if the token was not invalidated',
  PRIMARY KEY (`Nic_Handle`,`Unique_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `SNAPSHOT_OF_DNS`
--

DROP TABLE IF EXISTS `SNAPSHOT_OF_DNS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `SNAPSHOT_OF_DNS` (
  `D_Name` char(66) NOT NULL DEFAULT '',
  `DNS_Name` char(70) NOT NULL DEFAULT '',
  `DNS_IpAddr` char(20) DEFAULT NULL,
  `DNS_Order` tinyint(2) NOT NULL DEFAULT '0',
  PRIMARY KEY (`D_Name`,`DNS_Name`,`DNS_Order`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=FIXED;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `SchedulerConfig`
--

DROP TABLE IF EXISTS `SchedulerConfig`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `SchedulerConfig` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(128) NOT NULL COMMENT 'Process name',
  `schedule` varchar(512) NOT NULL COMMENT 'Schedule configuration',
  `Active` char(1) NOT NULL DEFAULT 'N' COMMENT 'activation flag',
  `ErrorMsg` varchar(512) DEFAULT NULL COMMENT 'error message',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `SchedulerConfigPopulated`
--

DROP TABLE IF EXISTS `SchedulerConfigPopulated`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `SchedulerConfigPopulated` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(128) NOT NULL COMMENT 'Process name',
  `schedule` varchar(512) NOT NULL COMMENT 'Schedule configuration',
  `Active` char(1) NOT NULL DEFAULT 'N' COMMENT 'activation flag',
  `ErrorMsg` varchar(512) DEFAULT NULL COMMENT 'error message',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `SchedulerJob`
--

DROP TABLE IF EXISTS `SchedulerJob`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `SchedulerJob` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `start_date` timestamp NULL DEFAULT NULL,
  `end_date` timestamp NULL DEFAULT NULL,
  `job_status` char(1) DEFAULT NULL COMMENT 'P, F, S, R, V,N',
  `failure_reason` text,
  `job_name` varchar(128) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `job_name` (`job_name`)
) ENGINE=InnoDB AUTO_INCREMENT=21322 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `SchedulerJobHist`
--

DROP TABLE IF EXISTS `SchedulerJobHist`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `SchedulerJobHist` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `start_date` timestamp NULL DEFAULT NULL,
  `end_date` timestamp NULL DEFAULT NULL,
  `job_status` char(1) DEFAULT NULL COMMENT 'P, F, S, R, V,N',
  `failure_reason` text,
  `job_name` varchar(128) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21312 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Statement`
--

DROP TABLE IF EXISTS `Statement`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Statement` (
  `Nic_Handle` varchar(15) NOT NULL DEFAULT '',
  `Pay_Terms` varchar(10) NOT NULL DEFAULT '',
  `Future_Bal` decimal(10,2) NOT NULL DEFAULT '0.00',
  `Current_Bal` decimal(10,2) NOT NULL DEFAULT '0.00',
  `Age_1_Bal` decimal(10,2) NOT NULL DEFAULT '0.00',
  `Age_2_Bal` decimal(10,2) NOT NULL DEFAULT '0.00',
  `Age_3_Bal` decimal(10,2) NOT NULL DEFAULT '0.00',
  `Total_Bal` decimal(10,2) NOT NULL DEFAULT '0.00',
  `Tr_Date` date NOT NULL DEFAULT '0000-00-00',
  `Tr_Type` varchar(20) NOT NULL DEFAULT '',
  `Ext_Ref` varchar(20) NOT NULL DEFAULT '',
  `Int_Ref` varchar(20) NOT NULL DEFAULT ''
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `StaticTableName`
--

DROP TABLE IF EXISTS `StaticTableName`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `StaticTableName` (
  `CompanyName` varchar(255) NOT NULL DEFAULT '',
  `VatRate` decimal(6,4) DEFAULT '0.0000',
  `TradeRate` float DEFAULT NULL,
  `RetailRate` float DEFAULT '0',
  `HistoricRetailRate` float DEFAULT '0',
  `BusinessDate` date DEFAULT NULL,
  `VatNumber` varchar(255) DEFAULT 'NULL',
  `LastNicHandle` varchar(12) DEFAULT NULL,
  `CompanyAddress` varchar(255) DEFAULT NULL,
  `LastInvoiceRun` date NOT NULL DEFAULT '0000-00-00',
  `LastRemittalRun` date NOT NULL DEFAULT '0000-00-00',
  `LastInvoiceNo` int(1) unsigned NOT NULL DEFAULT '0',
  `Phone` varchar(20) NOT NULL DEFAULT '0',
  `Fax` varchar(20) NOT NULL DEFAULT '0',
  `Web` varchar(66) NOT NULL DEFAULT '0',
  `SMTP` varchar(30) DEFAULT NULL,
  `SMTP_Port` int(11) DEFAULT NULL,
  `ReplyAddr` varchar(60) DEFAULT NULL,
  `WebReport` varchar(66) NOT NULL DEFAULT '',
  `RegistrarRate` float DEFAULT NULL,
  `RegistrarMSDRate` float DEFAULT NULL,
  `MinDepositLimit` float DEFAULT NULL,
  `MaxDepositLimit` float DEFAULT NULL,
  `CRS_Password_Expiry` int(1) NOT NULL DEFAULT '30',
  PRIMARY KEY (`CompanyName`),
  UNIQUE KEY `CompanyName` (`CompanyName`),
  KEY `CompanyName_2` (`CompanyName`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Tariff`
--

DROP TABLE IF EXISTS `Tariff`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Tariff` (
  `Status` tinyint(2) unsigned NOT NULL DEFAULT '0',
  `Description` tinytext NOT NULL,
  PRIMARY KEY (`Status`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Telecom`
--

DROP TABLE IF EXISTS `Telecom`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Telecom` (
  `Nic_Handle` varchar(12) NOT NULL DEFAULT '',
  `Phone` varchar(25) NOT NULL DEFAULT '',
  `Type` char(1) NOT NULL DEFAULT '',
  PRIMARY KEY (`Nic_Handle`,`Phone`,`Type`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `TelecomHist`
--

DROP TABLE IF EXISTS `TelecomHist`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `TelecomHist` (
  `Nic_Handle` varchar(12) NOT NULL DEFAULT '',
  `Phone` varchar(25) NOT NULL DEFAULT '',
  `Type` char(1) NOT NULL DEFAULT '',
  `Chng_NH` varchar(12) NOT NULL DEFAULT '',
  `Chng_Dt` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`Chng_Dt`,`Nic_Handle`,`Phone`,`Type`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `NicHandleHist`
--

DROP TABLE IF EXISTS `NicHandleHist`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `NicHandleHist` (
  `Nic_Handle` varchar(12) NOT NULL DEFAULT '',
  `NH_Name` tinytext NOT NULL,
  `A_Number` int(8) unsigned zerofill DEFAULT NULL,
  `Co_Name` tinytext NOT NULL,
  `NH_Address` tinytext NOT NULL,
  `NH_County` tinytext,
  `NH_Country` tinytext,
  `NH_Email` tinytext NOT NULL,
  `NH_Status` tinytext NOT NULL,
  `NH_Status_Dt` date NOT NULL DEFAULT '0000-00-00',
  `NH_Reg_Dt` date NOT NULL DEFAULT '0000-00-00',
  `NH_TStamp` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `NH_BillC_Ind` char(1) NOT NULL DEFAULT '',
  `NH_Remark` tinytext,
  `Chng_NH` varchar(12) NOT NULL DEFAULT '',
  `Chng_Dt` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `NH_Creator` varchar(12) DEFAULT NULL,
  `Vat_Category` char(1) DEFAULT NULL COMMENT 'vat category',
  PRIMARY KEY (`Chng_Dt`,`Nic_Handle`),
  KEY `Nic_Handle` (`Nic_Handle`),
  KEY `Chng_Dt` (`Chng_Dt`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Ticket`
--

DROP TABLE IF EXISTS `Ticket`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Ticket` (
  `T_Number` int(10) unsigned NOT NULL,
  `T_Type` char(1) NOT NULL DEFAULT '',
  `D_Name` varchar(66) NOT NULL DEFAULT '',
  `DN_Fail_Cd` tinyint(3) DEFAULT NULL,
  `D_Holder` tinytext NOT NULL,
  `DH_Fail_Cd` tinyint(3) DEFAULT NULL,
  `A_Number` int(8) unsigned zerofill NOT NULL DEFAULT '00000000',
  `AC_Fail_Cd` tinyint(3) DEFAULT NULL,
  `T_Class` tinytext NOT NULL,
  `T_Category` tinytext NOT NULL,
  `T_Remark` text,
  `Admin_NH1` varchar(12) NOT NULL DEFAULT '',
  `ANH1_Fail_Cd` tinyint(3) DEFAULT NULL,
  `Admin_NH2` varchar(12) DEFAULT NULL,
  `ANH2_Fail_Cd` tinyint(3) DEFAULT NULL,
  `Tech_NH` varchar(12) NOT NULL DEFAULT '',
  `TNH_Fail_Cd` tinyint(3) DEFAULT NULL,
  `Bill_NH` varchar(12) NOT NULL DEFAULT '',
  `BNH_Fail_Cd` tinyint(3) DEFAULT NULL,
  `Creator_NH` varchar(12) NOT NULL DEFAULT '',
  `DNS_Name1` tinytext NOT NULL,
  `DNS1_Fail_Cd` tinyint(3) DEFAULT NULL,
  `DNS_IP1` tinytext,
  `DNSIP1_Fail_Cd` tinyint(3) DEFAULT NULL,
  `DNS_Name2` tinytext NOT NULL,
  `DNS2_Fail_Cd` tinyint(3) DEFAULT NULL,
  `DNS_IP2` tinytext,
  `DNSIP2_Fail_Cd` tinyint(3) DEFAULT NULL,
  `DNS_Name3` tinytext,
  `DNS3_Fail_Cd` tinyint(3) DEFAULT NULL,
  `DNS_IP3` tinytext,
  `DNSIP3_Fail_Cd` tinyint(3) DEFAULT NULL,
  `Admin_Status` tinyint(2) unsigned NOT NULL DEFAULT '0',
  `Ad_StatusDt` date NOT NULL DEFAULT '0000-00-00',
  `Tech_Status` tinyint(2) unsigned NOT NULL DEFAULT '0',
  `T_Status_Dt` date NOT NULL DEFAULT '0000-00-00',
  `CheckedOut` char(1) NOT NULL DEFAULT 'N',
  `CheckedOutTo` varchar(12) DEFAULT NULL,
  `T_Reg_Dt` date DEFAULT NULL,
  `T_Ren_Dt` date DEFAULT NULL,
  `T_TStamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `H_Remark` text,
  `T_Class_Fail_Cd` tinyint(3) DEFAULT NULL,
  `T_Category_Fail_Cd` tinyint(3) DEFAULT NULL,
  `T_Created_TS` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `Opt_Cert` char(1) DEFAULT 'N',
  `T_ClikPaid` char(1) DEFAULT 'N',
  `_Fee_DECOM` float DEFAULT NULL,
  `_VAT_DECOM` char(1) DEFAULT NULL,
  `Period` tinyint(2) DEFAULT NULL COMMENT 'domain registration period',
  `CharityCode` varchar(20) DEFAULT NULL COMMENT 'charity code',
  `Financial_Status` tinyint(2) unsigned NOT NULL COMMENT 'Status of the Financial Check',
  `F_Status_Dt` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT 'Date of the last Financial_Check status change',
  `Customer_Status` tinyint(2) unsigned NOT NULL COMMENT 'Customer status',
  `C_Status_Dt` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT 'Date of the last Customer_Status status change',
  PRIMARY KEY (`T_Number`),
  UNIQUE KEY `Number` (`T_Number`),
  KEY `Tech_Status` (`Tech_Status`),
  KEY `D_Name` (`D_Name`),
  KEY `Admin_Status` (`Admin_Status`) USING HASH,
  KEY `A_Number` (`A_Number`) USING HASH,
  KEY `D_Holder` (`D_Holder`(128)) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Table structure for table `TicketAdminStatus`
--

DROP TABLE IF EXISTS `TicketAdminStatus`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `TicketAdminStatus` (
  `Status` tinyint(2) unsigned NOT NULL DEFAULT '0',
  `Description` tinytext NOT NULL,
  PRIMARY KEY (`Status`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `TicketFailCd`
--

DROP TABLE IF EXISTS `TicketFailCd`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `TicketFailCd` (
  `FailCd` tinyint(3) NOT NULL DEFAULT '0',
  `Description` tinytext NOT NULL,
  `Data_Field` tinyint(2) unsigned zerofill DEFAULT '00',
  PRIMARY KEY (`FailCd`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `TicketHist`
--

DROP TABLE IF EXISTS `TicketNameserverHist`;
DROP TABLE IF EXISTS `TicketHist`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `TicketHist` (
  `T_Number` int(10) NOT NULL DEFAULT '0',
  `T_Type` char(1) NOT NULL DEFAULT '',
  `D_Name` varchar(66) NOT NULL DEFAULT '',
  `DN_Fail_Cd` tinyint(3) DEFAULT NULL,
  `D_Holder` varchar(255) NOT NULL,
  `DH_Fail_Cd` tinyint(3) DEFAULT NULL,
  `A_Number` int(8) unsigned zerofill NOT NULL DEFAULT '00000000',
  `AC_Fail_Cd` tinyint(3) DEFAULT NULL,
  `T_Class` tinytext NOT NULL,
  `T_Category` tinytext NOT NULL,
  `T_Remark` text,
  `Admin_NH1` varchar(12) NOT NULL DEFAULT '',
  `ANH1_Fail_Cd` tinyint(3) DEFAULT NULL,
  `Admin_NH2` varchar(12) DEFAULT NULL,
  `ANH2_Fail_Cd` tinyint(3) DEFAULT NULL,
  `Tech_NH` varchar(12) NOT NULL DEFAULT '',
  `TNH_Fail_Cd` tinyint(3) DEFAULT NULL,
  `Bill_NH` varchar(12) NOT NULL DEFAULT '',
  `BNH_Fail_Cd` tinyint(3) DEFAULT NULL,
  `Creator_NH` varchar(12) NOT NULL DEFAULT '',
  `DNS_Name1` tinytext NOT NULL,
  `DNS1_Fail_Cd` tinyint(3) DEFAULT NULL,
  `DNS_IP1` tinytext,
  `DNSIP1_Fail_Cd` tinyint(3) DEFAULT NULL,
  `DNS_Name2` tinytext NOT NULL,
  `DNS2_Fail_Cd` tinyint(3) DEFAULT NULL,
  `DNS_IP2` tinytext,
  `DNSIP2_Fail_Cd` tinyint(3) DEFAULT NULL,
  `DNS_Name3` tinytext,
  `DNS3_Fail_Cd` tinyint(3) DEFAULT NULL,
  `DNS_IP3` tinytext,
  `DNSIP3_Fail_Cd` tinyint(3) DEFAULT NULL,
  `Admin_Status` tinyint(2) unsigned NOT NULL DEFAULT '0',
  `Ad_StatusDt` date NOT NULL DEFAULT '0000-00-00',
  `Tech_Status` tinyint(2) unsigned NOT NULL DEFAULT '0',
  `T_Status_Dt` date NOT NULL DEFAULT '0000-00-00',
  `CheckedOut` char(1) NOT NULL DEFAULT 'N',
  `CheckedOutTo` varchar(12) DEFAULT NULL,
  `T_Reg_Dt` date DEFAULT NULL,
  `T_Ren_Dt` date DEFAULT NULL,
  `T_TStamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `H_Remark` text,
  `Chng_Dt` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `Chng_NH` varchar(12) DEFAULT NULL,
  `T_Class_Fail_Cd` tinyint(3) DEFAULT NULL,
  `T_Category_Fail_Cd` tinyint(3) DEFAULT NULL,
  `T_Created_TS` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `Opt_Cert` char(1) DEFAULT 'N',
  `T_ClikPaid` char(1) DEFAULT 'N',
  `Fee` float DEFAULT '0',
  `VAT` char(1) DEFAULT 'Y',
  `Period` tinyint(2) DEFAULT NULL COMMENT 'domain registration period',
  `CharityCode` varchar(20) DEFAULT NULL COMMENT 'charity code',
  `Financial_Status` tinyint(2) unsigned NOT NULL COMMENT 'Status of the Financial Check',
  `F_Status_Dt` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT 'Date of the last Financial_Check status change',
  `Customer_Status` tinyint(2) unsigned NOT NULL COMMENT 'Customer status',
  `C_Status_Dt` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT 'Date of the last Customer_Status status change',
  PRIMARY KEY (`Chng_Dt`,`T_Number`),
  KEY `idx_TicketHistNew_D_Name` (`D_Name`),
  KEY `T_Number` (`T_Number`) USING BTREE,
  KEY `D_Name` (`D_Name`) USING BTREE,
  KEY `A_Number` (`A_Number`) USING HASH,
  KEY `Chng_Dt` (`Chng_Dt`) USING BTREE,
  KEY `D_Holder` (`D_Holder`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `TicketIndex`
--

DROP TABLE IF EXISTS `TicketIndex`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `TicketIndex` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=529846 DEFAULT CHARSET=latin1 COMMENT='replaces auto_increment for Ticket';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `TicketResponse`
--

DROP TABLE IF EXISTS `TicketResponse`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `TicketResponse` (
  `Response_ID` int(3) unsigned NOT NULL AUTO_INCREMENT,
  `Response_Title` text,
  `Response_Text` text,
  PRIMARY KEY (`Response_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=241 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `TicketTechStatus`
--

DROP TABLE IF EXISTS `TicketTechStatus`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `TicketTechStatus` (
  `Status` tinyint(2) unsigned NOT NULL DEFAULT '0',
  `Description` tinytext NOT NULL,
  PRIMARY KEY (`Status`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `TokenBucket`
--

DROP TABLE IF EXISTS `TokenBucket`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `TokenBucket` (
  `ID` int(10) NOT NULL AUTO_INCREMENT,
  `QTime` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `BucketCount` int(6) NOT NULL,
  `MaxCount` int(6) NOT NULL,
  `StdCount` int(6) NOT NULL,
  `BurstCount` int(6) NOT NULL,
  `ResultCode` char(1) NOT NULL,
  `Remote_IP` varchar(39) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM AUTO_INCREMENT=28822462 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Transaction`
--

DROP TABLE IF EXISTS `Transaction`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Transaction` (
  `ID` int(10) unsigned NOT NULL,
  `Settlement_Started` char(1) NOT NULL DEFAULT 'N' COMMENT 'settlement start flag',
  `Settlement_Ended` char(1) NOT NULL DEFAULT 'N' COMMENT 'settlement end flag',
  `Total_Cost` int(10) unsigned DEFAULT NULL COMMENT 'total cost',
  `Cancelled` char(1) NOT NULL DEFAULT 'N' COMMENT 'cancelled flag',
  `Cancelled_Date` timestamp NULL DEFAULT NULL COMMENT 'Date of the Cancelled flag change',
  `Financially_Passed_Date` timestamp NULL DEFAULT NULL COMMENT 'Date when domain financially passed',
  `Invoice_ID` int(10) unsigned DEFAULT NULL COMMENT 'invoice id',
  `Total_Net_Amount` int(10) unsigned DEFAULT NULL COMMENT 'total net amount',
  `Total_Vat_Amount` int(10) unsigned DEFAULT NULL COMMENT 'total vat amount',
  `Order_ID` varchar(50) NOT NULL DEFAULT '' COMMENT 'operation order id',
  `Invalidated` char(1) NOT NULL DEFAULT 'N' COMMENT 'invalidated flag',
  `Invalidated_Date` timestamp NULL DEFAULT NULL COMMENT 'Date of the invalidated flag change',
  `Reauthorised_ID` int(11) DEFAULT NULL COMMENT 're-authorised transaction id',
  `Realex_Authcode` varchar(50) DEFAULT NULL COMMENT 'reservation authcode from realex',
  `Realex_Passref` varchar(50) DEFAULT NULL COMMENT 'reservation passref from realex',
  PRIMARY KEY (`ID`),
  KEY `FK_Invoice_ID` (`Invoice_ID`),
  CONSTRAINT `FK_Invoice_ID` FOREIGN KEY (`Invoice_ID`) REFERENCES `Invoice` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `TransactionHist`
--

DROP TABLE IF EXISTS `TransactionHist`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `TransactionHist` (
  `ID` int(10) unsigned NOT NULL,
  `Settlement_Started` char(1) NOT NULL DEFAULT 'N' COMMENT 'settlement start flag',
  `Settlement_Ended` char(1) NOT NULL DEFAULT 'N' COMMENT 'settlement end flag',
  `Total_Cost` int(10) unsigned DEFAULT NULL COMMENT 'total cost',
  `Cancelled` char(1) NOT NULL DEFAULT 'N' COMMENT 'cancelled flag',
  `Cancelled_Date` timestamp NULL DEFAULT NULL COMMENT 'Date of the Cancelled flag change',
  `Financially_Passed_Date` timestamp NULL DEFAULT NULL COMMENT 'Date when domain financially passed',
  `Invoice_ID` int(10) unsigned DEFAULT NULL COMMENT 'invoice id',
  `Total_Net_Amount` int(10) unsigned DEFAULT NULL COMMENT 'total net amount',
  `Total_Vat_Amount` int(10) unsigned DEFAULT NULL COMMENT 'total vat amount',
  `Order_ID` varchar(50) NOT NULL DEFAULT '' COMMENT 'operation order id',
  `Invalidated` char(1) NOT NULL DEFAULT 'N' COMMENT 'invalidated flag',
  `Invalidated_Date` timestamp NULL DEFAULT NULL COMMENT 'Date of the invalidated flag change',
  `Reauthorised_ID` int(11) DEFAULT NULL COMMENT 're-authorised transaction id',
  PRIMARY KEY (`ID`),
  KEY `FK_TH_Invoice_ID` (`Invoice_ID`),
  CONSTRAINT `FK_TH_Invoice_ID` FOREIGN KEY (`Invoice_ID`) REFERENCES `Invoice` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `TransactionIndex`
--

DROP TABLE IF EXISTS `TransactionIndex`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `TransactionIndex` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=43972 DEFAULT CHARSET=latin1 COMMENT='replaces auto_increment for Transaction';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `TransfersHist`
--

DROP TABLE IF EXISTS `TransfersHist`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `TransfersHist` (
  `D_Name` varchar(66) NOT NULL DEFAULT '',
  `Tr_Date` date NOT NULL DEFAULT '0000-00-00',
  `Old_Nic_Handle` varchar(15) NOT NULL DEFAULT '',
  `New_Nic_Handle` varchar(15) NOT NULL DEFAULT '',
  PRIMARY KEY (`D_Name`,`Tr_Date`,`Old_Nic_Handle`,`New_Nic_Handle`),
  KEY `New_Nic_Handle_index` (`New_Nic_Handle`,`Tr_Date`),
  KEY `Old_Nic_Handle_index` (`Old_Nic_Handle`,`Tr_Date`),
  KEY `Tr_Date` (`Tr_Date`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `UserPermissions`
--

DROP TABLE IF EXISTS `UserPermissions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `UserPermissions` (
  `Nic_Handle` varchar(12) NOT NULL COMMENT 'nh of the user who owns the permission',
  `Permission_Name` varchar(255) NOT NULL COMMENT 'name of the granted permission',
  PRIMARY KEY (`Nic_Handle`,`Permission_Name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Vat`
--

DROP TABLE IF EXISTS `Vat`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Vat` (
  `ID` smallint(5) unsigned NOT NULL AUTO_INCREMENT,
  `Category` char(1) NOT NULL DEFAULT '' COMMENT 'vat category',
  `From_Date` date NOT NULL DEFAULT '0000-00-00' COMMENT 'date from which the vat rate applies to the category',
  `Rate` float unsigned NOT NULL DEFAULT '0' COMMENT 'vat rate',
  `Valid` char(1) NOT NULL DEFAULT 'N' COMMENT 'valid flag',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `CategoryIndex` (`Category`,`From_Date`),
  KEY `fk_vat_category_1` (`Category`),
  CONSTRAINT `fk_vat_category_1` FOREIGN KEY (`Category`) REFERENCES `vat_category` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `VatCountries`
--

DROP TABLE IF EXISTS `VatCountries`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `VatCountries` (
  `CountryCode` smallint(3) unsigned NOT NULL DEFAULT '0',
  `VatType` char(1) NOT NULL DEFAULT '',
  `ISOCode` char(2) NOT NULL DEFAULT '',
  PRIMARY KEY (`CountryCode`),
  UNIQUE KEY `CountryCode` (`CountryCode`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `VatCountry`
--

DROP TABLE IF EXISTS `VatCountry`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `VatCountry` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(128) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Whois_Whitelist`
--

DROP TABLE IF EXISTS `Whois_Whitelist`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Whois_Whitelist` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `IP_Addr` varchar(30) NOT NULL,
  `MaxQLim` int(10) unsigned NOT NULL DEFAULT '5000',
  `StdQLim` int(10) unsigned NOT NULL DEFAULT '1000',
  `PermitDeny` int(1) unsigned NOT NULL DEFAULT '0',
  `Remarks` varchar(100) NOT NULL DEFAULT 'added by billyg',
  `Source` varchar(20) NOT NULL DEFAULT 'NON-IEDR',
  PRIMARY KEY (`id`),
  KEY `BTREE` (`IP_Addr`)
) ENGINE=InnoDB AUTO_INCREMENT=875 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Zone`
--

DROP TABLE IF EXISTS `Zone`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Zone` (
  `Head` text NOT NULL,
  `Serial` int(2) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Zone_Published`
--

DROP TABLE IF EXISTS `Zone_Published`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Zone_Published` (
  `D_Name` varchar(66) NOT NULL COMMENT 'domain name',
  `Committed` char(1) DEFAULT NULL COMMENT 'Y means, that the domain is published',
  UNIQUE KEY `D_Name` (`D_Name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `_AdminC_Emails_DECOM`
--

DROP TABLE IF EXISTS `_AdminC_Emails_DECOM`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `_AdminC_Emails_DECOM` (
  `Email_ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `Email_Name` text NOT NULL,
  `Email_Content` text NOT NULL,
  PRIMARY KEY (`Email_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `_BillStatus_DECOM`
--

DROP TABLE IF EXISTS `_BillStatus_DECOM`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `_BillStatus_DECOM` (
  `Status` tinyint(2) unsigned NOT NULL DEFAULT '0',
  `Description` char(1) NOT NULL DEFAULT '',
  `DetailedDescription` tinytext,
  `Colour` varchar(12) DEFAULT NULL,
  PRIMARY KEY (`Status`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `_ClikPaid_DECOM`
--

DROP TABLE IF EXISTS `_ClikPaid_DECOM`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `_ClikPaid_DECOM` (
  `Status` tinyint(2) unsigned NOT NULL DEFAULT '0',
  `Description` char(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`Status`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `_ClikPayNotification_DECOM`
--

DROP TABLE IF EXISTS `_ClikPayNotification_DECOM`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `_ClikPayNotification_DECOM` (
  `Transaction_Id` varchar(20) NOT NULL DEFAULT '',
  `Date_Time` varchar(20) DEFAULT '0000-00-00 00:00:00',
  `Approval_Code` varchar(10) DEFAULT '0',
  `Description` varchar(100) DEFAULT '0',
  `Transaction_Amount` decimal(10,0) DEFAULT '0',
  `Transaction_Currency_Code` varchar(5) DEFAULT '0',
  `Charged_Amount` decimal(10,0) DEFAULT '0',
  `Charged_Currency_Code` varchar(5) DEFAULT '0',
  `Exchange_Rate` decimal(10,0) DEFAULT '0',
  `CardNumber` varchar(12) DEFAULT '0',
  `Expiry_Date` varchar(5) DEFAULT '0',
  PRIMARY KEY (`Transaction_Id`),
  UNIQUE KEY `Transaction_Id` (`Transaction_Id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `_Cond_Accept_DECOM`
--

DROP TABLE IF EXISTS `_Cond_Accept_DECOM`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `_Cond_Accept_DECOM` (
  `D_Name` varchar(66) NOT NULL DEFAULT '',
  `Add_Dt` date NOT NULL DEFAULT '0000-00-00',
  `Comment` text,
  `Status` varchar(250) DEFAULT NULL,
  PRIMARY KEY (`D_Name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `_Cr_Notes_DECOM`
--

DROP TABLE IF EXISTS `_Cr_Notes_DECOM`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `_Cr_Notes_DECOM` (
  `D_Name` varchar(66) NOT NULL DEFAULT '',
  `Cr_Note_Dt` date DEFAULT NULL,
  `Type` char(3) DEFAULT NULL,
  PRIMARY KEY (`D_Name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `_Cr_Notes_Hist_DECOM`
--

DROP TABLE IF EXISTS `_Cr_Notes_Hist_DECOM`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `_Cr_Notes_Hist_DECOM` (
  `D_Name` varchar(66) NOT NULL DEFAULT '',
  `Cr_Note_Dt` date NOT NULL DEFAULT '0000-00-00',
  `Type` char(3) NOT NULL DEFAULT '',
  `Nic_Handle` varchar(15) NOT NULL DEFAULT '',
  `C_Nt_Ts` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`D_Name`,`Cr_Note_Dt`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `_DDOS_IPs_DECOM`
--

DROP TABLE IF EXISTS `_DDOS_IPs_DECOM`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `_DDOS_IPs_DECOM` (
  `IP` varchar(30) NOT NULL,
  PRIMARY KEY (`IP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `_D_Audit_DECOM`
--

DROP TABLE IF EXISTS `_D_Audit_DECOM`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `_D_Audit_DECOM` (
  `D_Name` varchar(66) NOT NULL DEFAULT '',
  `Op_Dt` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `status` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`D_Name`,`Op_Dt`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `_D_Audit_Hist_DECOM`
--

DROP TABLE IF EXISTS `_D_Audit_Hist_DECOM`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `_D_Audit_Hist_DECOM` (
  `D_Name` varchar(66) NOT NULL DEFAULT '',
  `Op_Dt` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `status` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`D_Name`,`Op_Dt`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `_D_Locked_DECOM`
--

DROP TABLE IF EXISTS `_D_Locked_DECOM`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `_D_Locked_DECOM` (
  `D_Name` varchar(66) NOT NULL DEFAULT '',
  `Type` varchar(4) NOT NULL DEFAULT '',
  `L_Date` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`D_Name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `_D_Locked_Hist_DECOM`
--

DROP TABLE IF EXISTS `_D_Locked_Hist_DECOM`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `_D_Locked_Hist_DECOM` (
  `D_Name` varchar(66) NOT NULL DEFAULT '',
  `Type` varchar(4) NOT NULL DEFAULT '',
  `L_Date` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `UL_Date` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`D_Name`,`L_Date`,`UL_Date`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `_DeleteList_DECOM`
--

DROP TABLE IF EXISTS `_DeleteList_DECOM`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `_DeleteList_DECOM` (
  `D_Name` varchar(66) NOT NULL DEFAULT '',
  `D_Add_Dt` date DEFAULT NULL,
  `D_Add_Id` varchar(12) DEFAULT NULL,
  PRIMARY KEY (`D_Name`),
  UNIQUE KEY `D_Name` (`D_Name`),
  KEY `D_Name_2` (`D_Name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `_DeletePublic_DECOM`
--

DROP TABLE IF EXISTS `_DeletePublic_DECOM`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `_DeletePublic_DECOM` (
  `D_Name` varchar(66) NOT NULL DEFAULT '',
  `Type` char(1) NOT NULL DEFAULT '',
  PRIMARY KEY (`D_Name`),
  KEY `D_Name` (`D_Name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `_GuestReceiptsHist_DECOM`
--

DROP TABLE IF EXISTS `_GuestReceiptsHist_DECOM`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `_GuestReceiptsHist_DECOM` (
  `D_Name` varchar(66) NOT NULL DEFAULT '',
  `Nic_Handle` varchar(20) NOT NULL DEFAULT '',
  `Inv_No` varchar(20) NOT NULL DEFAULT '',
  `Trans_Dt` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `Order_ID` varchar(50) NOT NULL DEFAULT '',
  `Remote_IP` varchar(35) NOT NULL DEFAULT '',
  `VAT` decimal(10,2) NOT NULL DEFAULT '0.00',
  `Amount` decimal(10,2) NOT NULL DEFAULT '0.00',
  `Batch_Total` decimal(10,2) NOT NULL DEFAULT '0.00',
  `Type` varchar(15) NOT NULL DEFAULT '',
  `GR_Status` char(1) NOT NULL DEFAULT '',
  `Date` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `ID` varchar(20) NOT NULL DEFAULT '',
  PRIMARY KEY (`D_Name`,`Trans_Dt`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `_GuestReceipts_DECOM`
--

DROP TABLE IF EXISTS `_GuestReceipts_DECOM`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `_GuestReceipts_DECOM` (
  `D_Name` varchar(66) NOT NULL DEFAULT '',
  `Nic_Handle` varchar(20) NOT NULL DEFAULT '',
  `Inv_No` varchar(20) NOT NULL DEFAULT '',
  `Trans_Dt` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `Order_ID` varchar(50) NOT NULL DEFAULT '',
  `Remote_IP` varchar(35) NOT NULL DEFAULT '',
  `VAT` decimal(10,2) NOT NULL DEFAULT '0.00',
  `Amount` decimal(10,2) NOT NULL DEFAULT '0.00',
  `Batch_Total` decimal(10,2) NOT NULL DEFAULT '0.00',
  `Type` varchar(15) NOT NULL DEFAULT '',
  `GR_Status` char(1) NOT NULL DEFAULT '',
  PRIMARY KEY (`D_Name`,`Trans_Dt`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `_MailList_DECOM`
--

DROP TABLE IF EXISTS `_MailList_DECOM`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `_MailList_DECOM` (
  `D_Name` varchar(66) NOT NULL DEFAULT '',
  `M_Add_Dt` date DEFAULT NULL,
  `M_Add_Id` varchar(12) DEFAULT NULL,
  `M_Type` char(1) NOT NULL DEFAULT 'I',
  PRIMARY KEY (`D_Name`),
  UNIQUE KEY `D_Name` (`D_Name`),
  KEY `D_Name_2` (`D_Name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `_MonthEnd_DECOM`
--

DROP TABLE IF EXISTS `_MonthEnd_DECOM`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `_MonthEnd_DECOM` (
  `D_Name` varchar(66) NOT NULL DEFAULT '',
  `D_Bill_Status` char(1) NOT NULL DEFAULT '',
  `D_Status` tinytext NOT NULL,
  `Nic_Handle` varchar(12) NOT NULL DEFAULT '',
  `A_Name` varchar(30) NOT NULL DEFAULT '',
  PRIMARY KEY (`D_Name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `_NicHandleFailures_DECOM`
--

DROP TABLE IF EXISTS `_NicHandleFailures_DECOM`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `_NicHandleFailures_DECOM` (
  `Nic_Handle` varchar(12) NOT NULL DEFAULT '',
  `NH_Name_Fail_Cd` tinyint(3) DEFAULT NULL,
  `NH_Address_Fail_Cd` tinyint(3) DEFAULT NULL,
  `NH_Email_Fail_Cd` tinyint(3) DEFAULT NULL,
  `Remark` tinytext,
  `Chng_NH` varchar(12) NOT NULL DEFAULT '',
  `Chng_Dt` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `NH_Phone_Fail_Cd` tinyint(3) DEFAULT NULL,
  `NH_Fax_Fail_Cd` tinyint(3) DEFAULT NULL,
  `NH_Company_Fail_Cd` tinyint(3) unsigned DEFAULT NULL,
  PRIMARY KEY (`Nic_Handle`,`Chng_Dt`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `_PendingMailList_DECOM`
--

DROP TABLE IF EXISTS `_PendingMailList_DECOM`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `_PendingMailList_DECOM` (
  `D_Name` varchar(66) NOT NULL DEFAULT '',
  `M_Add_Dt` date NOT NULL DEFAULT '0000-00-00',
  `M_Add_Id` varchar(12) NOT NULL DEFAULT '',
  `M_Type` char(1) NOT NULL DEFAULT '',
  `Type` char(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`D_Name`,`Type`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `_PendingPaymentHist_DECOM`
--

DROP TABLE IF EXISTS `_PendingPaymentHist_DECOM`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `_PendingPaymentHist_DECOM` (
  `D_Name` varchar(66) NOT NULL DEFAULT '',
  `Nic_Handle` varchar(20) NOT NULL DEFAULT '',
  `Inv_No` varchar(20) NOT NULL DEFAULT '',
  `Trans_Dt` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `Sess_ID` varchar(50) NOT NULL DEFAULT '',
  `Remote_IP` varchar(35) NOT NULL DEFAULT '',
  `VAT` decimal(10,2) NOT NULL DEFAULT '0.00',
  `Amount` decimal(10,2) NOT NULL DEFAULT '0.00',
  `Batch_Total` decimal(10,2) NOT NULL DEFAULT '0.00',
  `Date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `ID` varchar(20) NOT NULL DEFAULT '',
  KEY `dname_index` (`D_Name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `_PendingPayment_DECOM`
--

DROP TABLE IF EXISTS `_PendingPayment_DECOM`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `_PendingPayment_DECOM` (
  `D_Name` varchar(66) NOT NULL DEFAULT '',
  `Nic_Handle` varchar(20) NOT NULL DEFAULT '',
  `Inv_No` varchar(20) NOT NULL DEFAULT '',
  `Trans_Dt` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `Sess_ID` varchar(50) NOT NULL DEFAULT '',
  `Remote_IP` varchar(35) NOT NULL DEFAULT '',
  `VAT` decimal(10,2) NOT NULL DEFAULT '0.00',
  `Amount` decimal(10,2) NOT NULL DEFAULT '0.00',
  `Batch_Total` decimal(10,2) NOT NULL DEFAULT '0.00',
  PRIMARY KEY (`D_Name`,`Trans_Dt`),
  KEY `DName_Only` (`D_Name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `_PendingSuspensionHist_DECOM`
--

DROP TABLE IF EXISTS `_PendingSuspensionHist_DECOM`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `_PendingSuspensionHist_DECOM` (
  `D_Name` varchar(66) NOT NULL DEFAULT '',
  `Nic_Handle` varchar(20) NOT NULL DEFAULT '',
  `Date` date NOT NULL DEFAULT '0000-00-00',
  `Sess_ID` varchar(50) NOT NULL DEFAULT '',
  `Remote_IP` varchar(35) NOT NULL DEFAULT '',
  `ID` varchar(20) NOT NULL DEFAULT '',
  PRIMARY KEY (`D_Name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `_PendingSuspension_DECOM`
--

DROP TABLE IF EXISTS `_PendingSuspension_DECOM`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `_PendingSuspension_DECOM` (
  `D_Name` varchar(66) NOT NULL DEFAULT '',
  `Nic_Handle` varchar(15) NOT NULL DEFAULT '',
  `Date` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `Sess_Id` varchar(50) NOT NULL DEFAULT '',
  `Remote_Ip` varchar(35) NOT NULL DEFAULT '',
  `Type` char(1) NOT NULL DEFAULT '',
  PRIMARY KEY (`D_Name`,`Date`,`Type`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `_RCInvoiceHist_DECOM`
--

DROP TABLE IF EXISTS `_RCInvoiceHist_DECOM`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `_RCInvoiceHist_DECOM` (
  `I_Inv_No` varchar(10) NOT NULL DEFAULT '',
  `I_DName` varchar(66) NOT NULL DEFAULT '',
  `I_Ren_Dt` date NOT NULL DEFAULT '0000-00-00',
  `I_Bill_NH` varchar(12) NOT NULL DEFAULT '',
  `I_DHolder` tinytext NOT NULL,
  `I_Type` varchar(10) NOT NULL DEFAULT '',
  `I_Amount` decimal(7,2) NOT NULL DEFAULT '0.00',
  `I_Status` char(1) NOT NULL DEFAULT '',
  `I_Reg_Dt` date NOT NULL DEFAULT '0000-00-00',
  `I_Inv_Dt` date NOT NULL DEFAULT '0000-00-00',
  `I_Inv_NH` varchar(12) NOT NULL DEFAULT '',
  `I_Inv_Vat` char(1) NOT NULL DEFAULT '',
  `I_Batch_Total` decimal(7,2) NOT NULL DEFAULT '0.00',
  PRIMARY KEY (`I_Inv_No`,`I_Inv_Dt`,`I_DName`),
  KEY `I_DName` (`I_DName`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `_RS_MailListHist_DECOM`
--

DROP TABLE IF EXISTS `_RS_MailListHist_DECOM`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `_RS_MailListHist_DECOM` (
  `Name` tinytext NOT NULL,
  `Surname` tinytext NOT NULL,
  `Role` tinytext NOT NULL,
  `Nic_Handle` varchar(12) NOT NULL DEFAULT '',
  `Email` tinytext NOT NULL,
  `Type` varchar(12) NOT NULL DEFAULT '',
  `Chng_NH` varchar(12) NOT NULL DEFAULT '',
  `Chng_Dt` datetime NOT NULL DEFAULT '0000-00-00 00:00:00'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `_RS_MailList_DECOM`
--

DROP TABLE IF EXISTS `_RS_MailList_DECOM`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `_RS_MailList_DECOM` (
  `Name` tinytext NOT NULL,
  `Surname` tinytext NOT NULL,
  `Role` tinytext NOT NULL,
  `Nic_Handle` varchar(12) NOT NULL DEFAULT '',
  `Email` tinytext NOT NULL,
  `Type` varchar(12) NOT NULL DEFAULT ''
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `_RcptBatch_DECOM`
--

DROP TABLE IF EXISTS `_RcptBatch_DECOM`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `_RcptBatch_DECOM` (
  `Type` char(10) NOT NULL DEFAULT '',
  `Start` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `Finish` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `Batch_No` char(5) NOT NULL DEFAULT '',
  PRIMARY KEY (`Type`,`Start`,`Finish`,`Batch_No`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `_Report_DECOM`
--

DROP TABLE IF EXISTS `_Report_DECOM`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `_Report_DECOM` (
  `R_ID` varchar(10) NOT NULL DEFAULT '',
  `R_Description` varchar(66) NOT NULL DEFAULT '',
  `R_Order` tinyint(3) unsigned NOT NULL DEFAULT '0',
  `R_File_Name` varchar(30) NOT NULL DEFAULT '',
  `R_Uses_Date` char(1) NOT NULL DEFAULT 'N',
  `R_Authority` int(4) NOT NULL DEFAULT '16777215',
  PRIMARY KEY (`R_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `_STPAccount_DECOM`
--

DROP TABLE IF EXISTS `_STPAccount_DECOM`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `_STPAccount_DECOM` (
  `Nic_Handle` varchar(12) NOT NULL DEFAULT '',
  `A_Number` mediumint(8) unsigned zerofill DEFAULT '00000000',
  PRIMARY KEY (`Nic_Handle`),
  UNIQUE KEY `Nic_Handle` (`Nic_Handle`),
  KEY `Nic_Handle_2` (`Nic_Handle`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `_SpecialListText_DECOM`
--

DROP TABLE IF EXISTS `_SpecialListText_DECOM`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `_SpecialListText_DECOM` (
  `LineNo` tinyint(3) NOT NULL AUTO_INCREMENT,
  `TextLine` varchar(80) NOT NULL DEFAULT '',
  PRIMARY KEY (`LineNo`),
  UNIQUE KEY `LineNo` (`LineNo`)
) ENGINE=InnoDB AUTO_INCREMENT=48 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `_SpecialList_DECOM`
--

DROP TABLE IF EXISTS `_SpecialList_DECOM`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `_SpecialList_DECOM` (
  `D_Name` varchar(66) NOT NULL DEFAULT '',
  `S_Add_Dt` date NOT NULL DEFAULT '0000-00-00',
  `S_Add_NH` varchar(12) NOT NULL DEFAULT '',
  PRIMARY KEY (`D_Name`),
  UNIQUE KEY `D_Name` (`D_Name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `_StaticTable_DECOM`
--

DROP TABLE IF EXISTS `_StaticTable_DECOM`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `_StaticTable_DECOM` (
  `Nxt_Zone_Reload` time NOT NULL DEFAULT '00:00:00',
  `Next_MSD_Run` date NOT NULL DEFAULT '0000-00-00',
  `Last_MSD_Run` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `Last_Cr_Nt_Mods` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `Last_Rcpt_Run` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `Last_PIA_Run` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `Last_Xfer_Inv_Run` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `Vision_Date` date NOT NULL DEFAULT '0000-00-00',
  `Last_Rcpt_Run_Off` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `Last_Month_Inv_Run` date NOT NULL DEFAULT '0000-00-00',
  `Last_Cr_Nt_MSD_Xfers` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `Last_Guest_Rcp_Run_Off` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `Last_Guest_Rcp_Run_On` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `Last_Guest_Rcp_Run_RX_On` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `Last_Deposit_Run_Adv_Xfer` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `Last_Deposit_Run_RED` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `Last_Deposit_Acc_Run` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `Last_Guest_Adv_Rcp_Run_On` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `Last_Guest_Adv_Rcp_Run_Off` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `Last_Guest_Rcp_Run_RBDX_On` datetime NOT NULL DEFAULT '0000-00-00 00:00:00'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `_StatsAccessLog_DECOM`
--

DROP TABLE IF EXISTS `_StatsAccessLog_DECOM`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `_StatsAccessLog_DECOM` (
  `NicHandle` varchar(12) NOT NULL DEFAULT '',
  `Access_TS` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `Report` varchar(60) NOT NULL DEFAULT '',
  `Remote_IP` varchar(16) NOT NULL DEFAULT '',
  KEY `NicHandle` (`NicHandle`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `_StatsAccess_DECOM`
--

DROP TABLE IF EXISTS `_StatsAccess_DECOM`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `_StatsAccess_DECOM` (
  `Nic_Handle` varchar(12) NOT NULL DEFAULT '',
  `NH_Password` tinytext,
  `NH_Level` int(4) NOT NULL DEFAULT '0',
  `Answer` tinytext,
  PRIMARY KEY (`Nic_Handle`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `_SuspendList_DECOM`
--

DROP TABLE IF EXISTS `_SuspendList_DECOM`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `_SuspendList_DECOM` (
  `D_Name` varchar(66) NOT NULL DEFAULT '',
  `S_Add_Dt` date DEFAULT NULL,
  `S_Add_Id` varchar(12) DEFAULT NULL,
  PRIMARY KEY (`D_Name`),
  UNIQUE KEY `D_Name` (`D_Name`),
  KEY `D_Name_2` (`D_Name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `_Transfers_DECOM`
--

DROP TABLE IF EXISTS `_Transfers_DECOM`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `_Transfers_DECOM` (
  `D_Name` varchar(66) NOT NULL DEFAULT '',
  `Tr_Date` date NOT NULL DEFAULT '0000-00-00',
  `Old_Nic_Handle` varchar(15) NOT NULL DEFAULT '',
  `New_Nic_Handle` varchar(15) NOT NULL DEFAULT '',
  PRIMARY KEY (`D_Name`,`Tr_Date`,`Old_Nic_Handle`,`New_Nic_Handle`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `innodb_monitor`
--

DROP TABLE IF EXISTS `innodb_monitor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `innodb_monitor` (
  `a` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `login_attempts`
--

DROP TABLE IF EXISTS `login_attempts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `login_attempts` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `nic` varchar(255) NOT NULL COMMENT 'attempting nic handle',
  `attempted` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'attempt date',
  `client_ip` varchar(255) NOT NULL COMMENT 'attempting client IP',
  `failure_cause` varchar(255) DEFAULT NULL COMMENT 'cause of failure',
  `subsequent_failure_count` int(11) NOT NULL DEFAULT '0' COMMENT 'subsequent failed login attempts count',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1276317 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `nicHandleSeq`
--

DROP TABLE IF EXISTS `nicHandleSeq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `nicHandleSeq` (
  `id` int(11) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `vat_category`
--

DROP TABLE IF EXISTS `vat_category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `vat_category` (
  `code` char(1) NOT NULL COMMENT 'vat category code',
  PRIMARY KEY (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2014-03-13 15:32:19
