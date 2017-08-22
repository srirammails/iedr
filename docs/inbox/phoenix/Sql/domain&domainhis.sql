# MySQL-Front Dump 1.16 beta
#
# Host: 136.205.1.26 Database: Phoenix
#--------------------------------------------------------
# Server version 3.23.47-nt
#
# Table structure for table 'domain'
#

CREATE TABLE domain (
  D_Name varchar(66) NOT NULL DEFAULT '' ,
  D_Holder tinytext NOT NULL DEFAULT '' ,
  D_Class tinytext NOT NULL DEFAULT '' ,
  D_Category tinytext NOT NULL DEFAULT '' ,
  A_Number int(8) unsigned zerofill ,
  D_Status tinytext NOT NULL DEFAULT '' ,
  D_Status_Dt date NOT NULL DEFAULT '0000-00-00' ,
  D_Reg_Dt date NOT NULL DEFAULT '0000-00-00' ,
  D_Ren_Dt date NOT NULL DEFAULT '0000-00-00' ,
  D_TStamp timestamp(14) ,
  D_Discount char(1) NOT NULL DEFAULT '' ,
  D_Bill_Status char(1) NOT NULL DEFAULT '' ,
  D_Vat_Status char(1) NOT NULL DEFAULT '' ,
  D_Remark text ,
  D_ClikPaid char(1) NOT NULL DEFAULT 'N' ,
  PRIMARY KEY (D_Name),
  KEY A_Number (A_Number)
);


#
# Table structure for table 'domainhist'
#

CREATE TABLE domainhist (
  D_Name varchar(66) NOT NULL DEFAULT '' ,
  D_Holder tinytext NOT NULL DEFAULT '' ,
  D_Class tinytext NOT NULL DEFAULT '' ,
  D_Category tinytext NOT NULL DEFAULT '' ,
  A_Number int(8) unsigned zerofill ,
  D_Status tinytext NOT NULL DEFAULT '' ,
  D_Status_Dt date NOT NULL DEFAULT '0000-00-00' ,
  D_Reg_Dt date NOT NULL DEFAULT '0000-00-00' ,
  D_Ren_Dt date NOT NULL DEFAULT '0000-00-00' ,
  D_TStamp timestamp(14) ,
  D_Discount char(1) NOT NULL DEFAULT '' ,
  D_Bill_Status char(1) NOT NULL DEFAULT '' ,
  D_Vat_Status char(1) NOT NULL DEFAULT '' ,
  D_Remark text ,
  Chng_NH varchar(12) NOT NULL DEFAULT '' ,
  Chng_Dt datetime NOT NULL DEFAULT '0000-00-00 00:00:00' ,
  D_ClikPaid char(1) NOT NULL DEFAULT 'N' ,
  PRIMARY KEY (Chng_Dt,D_Name)
);


