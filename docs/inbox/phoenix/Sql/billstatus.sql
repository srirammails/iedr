# MySQL-Front Dump 1.16 beta
#
# Host: 136.205.1.26 Database: Phoenix
#--------------------------------------------------------
# Server version 3.23.47-nt
#
# Table structure for table 'billstatus'
#

CREATE TABLE billstatus (
  Status tinyint(2) unsigned NOT NULL DEFAULT '0' ,
  Description char(1) NOT NULL DEFAULT '' ,
  PRIMARY KEY (Status)
);


#
# Dumping data for table 'billstatus'
#

INSERT INTO billstatus VALUES("0","Y");
INSERT INTO billstatus VALUES("1","N");
INSERT INTO billstatus VALUES("2","C");
INSERT INTO billstatus VALUES("3","I");
