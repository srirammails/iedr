# MySQL-Front Dump 1.16 beta
#
# Host: 136.205.1.26 Database: Phoenix
#--------------------------------------------------------
# Server version 3.23.47-nt
#
# Table structure for table 'clikpay'
#

CREATE TABLE clikpay (
  Status tinyint(2) unsigned NOT NULL DEFAULT '0' ,
  Description char(1) NOT NULL DEFAULT '0' ,
  PRIMARY KEY (Status)
);


#
# Dumping data for table 'clikpay'
#

INSERT INTO clikpay VALUES("0","Y");
INSERT INTO clikpay VALUES("1","N");
