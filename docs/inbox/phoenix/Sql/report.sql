# MySQL-Front Dump 1.16 beta
#
# Host: 136.205.1.26 Database: Phoenix
#--------------------------------------------------------
# Server version 3.23.47-nt
#
# Table structure for table 'report'
#

CREATE TABLE report (
  R_ID varchar(10) NOT NULL DEFAULT '' ,
  R_Description varchar(66) NOT NULL DEFAULT '' ,
  R_Order tinyint(3) unsigned NOT NULL DEFAULT '0' ,
  R_File_Name varchar(30) NOT NULL DEFAULT '' ,
  R_Uses_Date char(1) NOT NULL DEFAULT 'N' ,
  R_Authority int(4) NOT NULL DEFAULT '16777215' ,
  PRIMARY KEY (R_ID)
);


#
# Dumping data for table 'report'
#

INSERT INTO report VALUES("R001","New/Modify Domains","1","R001.rpt","Y","255");
INSERT INTO report VALUES("R002","List of Domains Alpha Order","2","R002.rpt","Y","255");
INSERT INTO report VALUES("R003","Total Number of Registered Reseller","3","R003.rpt","N","255");
INSERT INTO report VALUES("R004","Total No Of Domains Discount","4","R004.rpt","Y","255");
INSERT INTO report VALUES("R005","Total No Of Registrations Per Month","5","R005.rpt","Y","255");
