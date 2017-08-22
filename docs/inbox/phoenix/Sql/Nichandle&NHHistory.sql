# MySQL-Front Dump 1.16 beta
#
# Host: 136.205.1.26 Database: Phoenix
#--------------------------------------------------------
# Server version 3.23.47-nt
#
# Table structure for table 'nichandle'
#

CREATE TABLE nichandle (
  Nic_Handle varchar(12) NOT NULL DEFAULT '' ,
  NH_Name tinytext NOT NULL DEFAULT '' ,
  A_Number int(8) unsigned zerofill ,
  Co_Name tinytext NOT NULL DEFAULT '' ,
  NH_Address tinytext NOT NULL DEFAULT '' ,
  NH_County tinytext ,
  NH_Country tinytext ,
  NH_Email tinytext NOT NULL DEFAULT '' ,
  NH_Status tinytext NOT NULL DEFAULT '' ,
  NH_Status_Dt date NOT NULL DEFAULT '0000-00-00' ,
  NH_Reg_Dt date NOT NULL DEFAULT '0000-00-00' ,
  NH_TStamp timestamp(14) ,
  NH_BillC_Ind char(1) NOT NULL DEFAULT '' ,
  NH_Remark text ,
  NH_Creator varchar(12) ,
  PRIMARY KEY (Nic_Handle),
  KEY A_Number (A_Number)
);


#
# Table structure for table 'nichandlehist'
#

CREATE TABLE nichandlehist (
  Nic_Handle varchar(12) NOT NULL DEFAULT '' ,
  NH_Name tinytext NOT NULL DEFAULT '' ,
  A_Number int(8) unsigned zerofill ,
  Co_Name tinytext NOT NULL DEFAULT '' ,
  NH_Address tinytext NOT NULL DEFAULT '' ,
  NH_County tinytext ,
  NH_Country tinytext ,
  NH_Email tinytext NOT NULL DEFAULT '' ,
  NH_Status tinytext NOT NULL DEFAULT '' ,
  NH_Status_Dt date NOT NULL DEFAULT '0000-00-00' ,
  NH_Reg_Dt date NOT NULL DEFAULT '0000-00-00' ,
  NH_TStamp timestamp(14) ,
  NH_BillC_Ind char(1) NOT NULL DEFAULT '' ,
  NH_Remark text ,
  Chng_NH varchar(12) NOT NULL DEFAULT '' ,
  Chng_Dt datetime NOT NULL DEFAULT '0000-00-00 00:00:00' ,
  NH_Creator varchar(12) ,
  PRIMARY KEY (Chng_Dt,Nic_Handle)
);


