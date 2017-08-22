# MySQL-Front Dump 1.16 beta
#
# Host: 136.205.1.26 Database: Phoenix
#--------------------------------------------------------
# Server version 3.23.47-nt
#
# Table structure for table 'tariff'
#

CREATE TABLE tariff (
  Status tinyint(2) unsigned NOT NULL DEFAULT '0' ,
  Description tinytext NOT NULL DEFAULT '' ,
  PRIMARY KEY (Status)
);


#
# Dumping data for table 'tariff'
#

INSERT INTO tariff VALUES("0","Silver");
INSERT INTO tariff VALUES("1","Gold");
INSERT INTO tariff VALUES("2","Platinum");
