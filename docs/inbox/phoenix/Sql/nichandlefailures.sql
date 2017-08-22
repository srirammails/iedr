# MySQL-Front Dump 1.16 beta
#
# Host: 136.205.1.26 Database: Phoenix
#--------------------------------------------------------
# Server version 3.23.47-nt
#
# Table structure for table 'nichandlefailures'
#

CREATE TABLE nichandlefailures (
  Nic_Handle varchar(12) NOT NULL DEFAULT '' ,
  NH_Name_Fail_Cd tinyint(3) ,
  NH_Address_Fail_Cd tinyint(3) ,
  NH_Email_Fail_Cd tinyint(3) ,
  Remark tinytext ,
  Chng_NH varchar(12) NOT NULL DEFAULT '' ,
  Chng_Dt datetime NOT NULL DEFAULT '0000-00-00 00:00:00' ,
  NH_Phone_Fail_Cd tinyint(3) ,
  NH_Fax_Fail_Cd tinyint(3) ,
  NH_Company_Fail_Cd tinyint(3) unsigned ,
  PRIMARY KEY (Nic_Handle,Chng_Dt)
);


