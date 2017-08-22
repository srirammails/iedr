-- MySQL dump 10.11
--
-- Host: localhost    Database: mysql
-- ------------------------------------------------------
-- Server version	5.0.87sp1-enterprise-gpl-log

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
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `Host` char(60) collate utf8_bin NOT NULL default '',
  `User` char(16) collate utf8_bin NOT NULL default '',
  `Password` char(41) character set latin1 collate latin1_bin NOT NULL default '',
  `Select_priv` enum('N','Y') character set utf8 NOT NULL default 'N',
  `Insert_priv` enum('N','Y') character set utf8 NOT NULL default 'N',
  `Update_priv` enum('N','Y') character set utf8 NOT NULL default 'N',
  `Delete_priv` enum('N','Y') character set utf8 NOT NULL default 'N',
  `Create_priv` enum('N','Y') character set utf8 NOT NULL default 'N',
  `Drop_priv` enum('N','Y') character set utf8 NOT NULL default 'N',
  `Reload_priv` enum('N','Y') character set utf8 NOT NULL default 'N',
  `Shutdown_priv` enum('N','Y') character set utf8 NOT NULL default 'N',
  `Process_priv` enum('N','Y') character set utf8 NOT NULL default 'N',
  `File_priv` enum('N','Y') character set utf8 NOT NULL default 'N',
  `Grant_priv` enum('N','Y') character set utf8 NOT NULL default 'N',
  `References_priv` enum('N','Y') character set utf8 NOT NULL default 'N',
  `Index_priv` enum('N','Y') character set utf8 NOT NULL default 'N',
  `Alter_priv` enum('N','Y') character set utf8 NOT NULL default 'N',
  `Show_db_priv` enum('N','Y') character set utf8 NOT NULL default 'N',
  `Super_priv` enum('N','Y') character set utf8 NOT NULL default 'N',
  `Create_tmp_table_priv` enum('N','Y') character set utf8 NOT NULL default 'N',
  `Lock_tables_priv` enum('N','Y') character set utf8 NOT NULL default 'N',
  `Execute_priv` enum('N','Y') character set utf8 NOT NULL default 'N',
  `Repl_slave_priv` enum('N','Y') character set utf8 NOT NULL default 'N',
  `Repl_client_priv` enum('N','Y') character set utf8 NOT NULL default 'N',
  `Create_view_priv` enum('N','Y') collate utf8_bin NOT NULL default 'N',
  `Show_view_priv` enum('N','Y') collate utf8_bin NOT NULL default 'N',
  `Create_routine_priv` enum('N','Y') collate utf8_bin NOT NULL default 'N',
  `Alter_routine_priv` enum('N','Y') collate utf8_bin NOT NULL default 'N',
  `Create_user_priv` enum('N','Y') collate utf8_bin NOT NULL default 'N',
  `ssl_type` enum('','ANY','X509','SPECIFIED') character set utf8 NOT NULL default '',
  `ssl_cipher` blob NOT NULL,
  `x509_issuer` blob NOT NULL,
  `x509_subject` blob NOT NULL,
  `max_questions` int(11) unsigned NOT NULL default '0',
  `max_updates` int(11) unsigned NOT NULL default '0',
  `max_connections` int(11) unsigned NOT NULL default '0',
  `max_user_connections` int(11) unsigned NOT NULL default '0',
  PRIMARY KEY  (`Host`,`User`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='Users and global privileges';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES ('localhost','root','02a0628c14bbf838','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','','','','',0,0,0,0),('10.11.2.15','root','02a0628c14bbf838','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','','','','',0,0,0,0),('213.190.149.196','phoenix01','70fc58292f94a2a9','Y','Y','Y','Y','N','N','N','N','N','N','N','N','N','N','N','N','Y','Y','N','N','N','N','N','N','N','N','','','','',0,0,0,0),('localhost','phoenix01','70fc58292f94a2a9','Y','Y','Y','Y','N','N','N','N','N','N','N','N','N','N','N','N','Y','Y','N','N','N','N','N','N','N','N','','','','',0,0,0,0),('213.190.149.194','mysqlem','*F2C361561173017FE73A095D2B1A4A6FBB714E0A','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','Y','Y','N','N','N','N','Y','N','N','N','N','N','','','','',0,0,0,0),('213.190.149.194','ciarak','4d1e834e1fd187fa','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','Y','N','N','N','N','N','N','N','N','','','','',0,0,0,0),('213.190.149.196','replphx','39b0357c147af800','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','Y','Y','N','Y','Y','N','N','N','N','N','','','','',0,0,0,0),('213.190.149.206','phoenix01','70fc58292f94a2a9','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','','','','',0,0,0,0),('193.1.32.50','joomlaWhy','3098537026e32465','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','','','','',0,0,0,0),('213.190.149.194','flysprayuser','2a191c3456311d01','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','','','','',0,0,0,0),('10.10.2.%','faxwriter','5d2e19393cc5ef67','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','Y','N','N','N','N','N','N','N','N','','','','',0,0,0,0),('10.11.6.4','Cr5uS3r','*63AC02642319CCE7BD4EDC640246EB2E58F9CA88','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','','','','',0,0,0,0),('193.1.32.50','PhoenixWA','29f6c2292fe6eafb','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','Y','N','N','N','N','N','N','N','N','','','','',0,0,0,0),('193.1.32.50','joomlaWeb','22c6f0302021d737','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','','','','',0,0,0,0),('localhost','newbinlog','41c6411e2fdc6b8c','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','Y','Y','N','N','N','N','N','N','N','N','','','','',0,0,0,0),('10.10.2.%','phoenix01','70fc58292f94a2a9','Y','Y','Y','Y','Y','N','N','N','N','N','N','N','N','N','N','N','Y','Y','N','N','N','N','N','N','N','N','','','','',0,0,0,0),('193.1.32.50','WhoisLogger','006a264320ed9871','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','Y','N','N','N','N','N','N','N','N','','','','',0,0,0,0),('217.114.166.213','phoenix01','70fc58292f94a2a9','Y','Y','Y','Y','N','N','N','N','N','N','N','N','N','N','N','N','Y','Y','N','N','N','N','N','N','N','N','','','','',0,0,0,0),('213.190.149.205','PhoenixWA','29f6c2292fe6eafb','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','Y','N','N','N','N','N','N','N','N','','','','',0,0,0,0),('193.1.32.50','joomlaTech','7e2d460e5e80d00b','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','','','','',0,0,0,0),('213.190.149.194','Webfaxwriter','5d2e19393cc5ef67','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','Y','N','N','N','N','N','N','N','N','','','','',0,0,0,0),('213.190.149.194','faxwriter','5d2e19393cc5ef67','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','Y','N','N','N','N','N','N','N','N','','','','',0,0,0,0),('193.1.32.51','phoenix01','70fc58292f94a2a9','Y','Y','Y','Y','N','N','N','N','N','N','N','N','N','N','N','N','Y','Y','N','N','N','N','N','N','N','N','','','','',0,0,0,0),('10.11.2.15','phoenix01','70fc58292f94a2a9','Y','Y','Y','Y','N','N','N','N','N','N','N','N','N','N','N','N','Y','Y','N','N','N','N','N','N','N','N','','','','',0,0,0,0),('193.1.32.50','PhoenixStats','1fdddfae65155def','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','Y','N','N','N','N','N','N','N','N','','','','',0,0,0,0),('213.190.149.194','phoenix01','70fc58292f94a2a9','Y','Y','Y','Y','N','N','N','N','N','N','N','N','N','N','N','N','Y','Y','N','N','N','N','N','N','N','N','','','','',0,0,0,0),('10.12.2.14','replphx','39b0357c147af800','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','Y','Y','N','Y','Y','N','N','N','N','N','','','','',0,0,0,0),('dopey.domainregistry.ie','mysqlem','*F2C361561173017FE73A095D2B1A4A6FBB714E0A','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','Y','Y','N','N','N','N','Y','N','N','N','N','N','','','','',0,0,0,0),('213.190.149.194','faxreader','5d2e19393cc5ef67','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','Y','N','N','N','N','N','N','N','N','','','','',0,0,0,0),('193.1.32.50','WhoisReader','1d68f065485708cb','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','','','','',0,0,0,0),('193.1.32.50','consult_iedr','5daa6ff34089a54b','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','','','','',0,0,0,0),('193.1.32.50','dnsSearch','50a2863976cfd638','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Y','Y','N','N','N','N','N','N','N','N','','','','',0,0,0,0),('10.10.128.%','phoenix01','70fc58292f94a2a9','Y','Y','Y','Y','N','N','N','N','N','N','N','N','N','N','N','N','Y','Y','N','N','N','N','N','N','N','N','','','','',0,0,0,0),('213.190.149.194','replphx','39b0357c147af800','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','Y','Y','N','Y','Y','N','N','N','N','N','','','','',0,0,0,0),('193.1.32.37','phoenix01','70fc58292f94a2a9','Y','Y','Y','Y','N','N','N','N','N','N','N','N','N','N','N','N','Y','Y','N','N','N','N','N','N','N','N','','','','',0,0,0,0),('10.10.%','crsuser','*AE61255AAC8EE41D576AAE31A532A34EE1EEB019','Y','Y','Y','Y','N','N','N','N','N','N','N','N','N','N','N','N','Y','Y','Y','N','N','N','Y','N','N','N','','','','',0,0,0,0),('10.10.128.%','root','02a0628c14bbf838','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','','','','',0,0,0,0);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2011-11-08 16:02:10
