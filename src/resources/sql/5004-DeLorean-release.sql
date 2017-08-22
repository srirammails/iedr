-- dnsnotification-email

UPDATE `Email` SET `E_Text`='\nPLEASE DO NOT REPLY TO THIS EMAIL. THIS IS AN AUTOMATED EMAIL\n\nTo whom it concerns,\n\nThe following DNS Failures have been detected for $DOMAIN$:\n\n$DNS_FAILURES$\n\n\nKind Regards\nRegistration Services Team\nIE Domain Registry Limited\nTel:  +353 (1) 2365400\nFax: +353 (1) 2300365\nWeb: www.iedr.ie\n\n-------------------------------------------------------------------------------------------------------\nRegistered Office: Harbour Square, Block 2, 4th Floor, Dun Laoghaire, Co. Dublin.\nRegistered in Ireland. No: 315315.\n-------------------------------------------------------------------------------------------------------\n\n\n\n' WHERE `E_ID`='102';

-- CRS-132 changes

DROP TABLE IF EXISTS `TicketNameserver`;
CREATE TABLE `TicketNameserver` (
  `T_Number` int(10) unsigned NOT NULL,
  `TN_Name` VARCHAR(70) NOT NULL,
  `TN_Name_Fail_Cd` tinyint(3) default NULL,
  `TN_IP` VARCHAR(20),
  `TN_IP_Fail_Cd` tinyint(3) default NULL,
  `TN_Order` tinyint NOT NULL,
  `TN_TS` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  PRIMARY KEY (`T_Number`, `TN_Order`),
  UNIQUE KEY  (`T_Number`, `TN_Name`), 
  CONSTRAINT `TicketNameserver_fk_ticket` FOREIGN KEY (`T_Number`) REFERENCES `Ticket` (`T_Number`) ON UPDATE CASCADE ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `TicketNameserverHist`;
CREATE TABLE `TicketNameserverHist` (
  `T_Number` int(10) unsigned NOT NULL,
  `TN_Name` VARCHAR(70) NOT NULL,
  `TN_Name_Fail_Cd` tinyint(3) default NULL,
  `TN_IP` VARCHAR(20),
  `TN_IP_Fail_Cd` tinyint(3) default NULL,
  `TN_Order` tinyint NOT NULL,
  `TN_TS` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  `Chng_NH` varchar(12) NOT NULL default '',
  `Chng_Dt` datetime NOT NULL default '0000-00-00 00:00:00',
  PRIMARY KEY  (`T_Number`, `TN_Name`, `Chng_Dt`) 
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

INSERT INTO `TicketNameserver`
(`T_Number`,
`TN_Name`,
`TN_Name_Fail_Cd`,
`TN_IP`,
`TN_IP_Fail_Cd`,
`TN_Order`,
`TN_TS`)
SELECT `T_Number`,
    `DNS_Name1`,
    `DNS1_Fail_Cd`,
    `DNS_IP1`,
    `DNSIP1_Fail_Cd`,
    0,
    `T_TStamp`
FROM `Ticket`
WHERE NULLIF(DNS_Name1, '') IS NOT NULL;

INSERT INTO `TicketNameserver`
(`T_Number`,
`TN_Name`,
`TN_Name_Fail_Cd`,
`TN_IP`,
`TN_IP_Fail_Cd`,
`TN_Order`,
`TN_TS`)
SELECT `T_Number`,
    `DNS_Name2`,
    `DNS2_Fail_Cd`,
    `DNS_IP2`,
    `DNSIP2_Fail_Cd`,
    1,
    `T_TStamp`
FROM `Ticket`
WHERE NULLIF(DNS_Name2, '') IS NOT NULL;

INSERT INTO `TicketNameserver`
(`T_Number`,
`TN_Name`,
`TN_Name_Fail_Cd`,
`TN_IP`,
`TN_IP_Fail_Cd`,
`TN_Order`,
`TN_TS`)
SELECT `T_Number`,
    `DNS_Name3`,
    `DNS3_Fail_Cd`,
    `DNS_IP3`,
    `DNSIP3_Fail_Cd`,
    2,
    `T_TStamp`
FROM `Ticket`
WHERE NULLIF(DNS_Name3, '') IS NOT NULL;

INSERT INTO `TicketNameserverHist`
(`T_Number`,
`TN_Name`,
`TN_Name_Fail_Cd`,
`TN_IP`,
`TN_IP_Fail_Cd`,
`TN_Order`,
`TN_TS`,
`Chng_NH`,
`Chng_Dt`)
SELECT `T_Number`,
    `DNS_Name1`,
    `DNS1_Fail_Cd`,
    `DNS_IP1`,
    `DNSIP1_Fail_Cd`,
    0,
    `T_TStamp`,
    `Chng_NH`,
    `Chng_Dt`
FROM `TicketHist`
WHERE NULLIF(DNS_Name1, '') IS NOT NULL;

INSERT INTO `TicketNameserverHist`
(`T_Number`,
`TN_Name`,
`TN_Name_Fail_Cd`,
`TN_IP`,
`TN_IP_Fail_Cd`,
`TN_Order`,
`TN_TS`,
`Chng_NH`,
`Chng_Dt`)
SELECT `T_Number`,
    `DNS_Name2`,
    `DNS2_Fail_Cd`,
    `DNS_IP2`,
    `DNSIP2_Fail_Cd`,
    1,
    `T_TStamp`,
    `Chng_NH`,
    `Chng_Dt`
FROM `TicketHist`
WHERE NULLIF(DNS_Name2, '') IS NOT NULL
ON DUPLICATE KEY UPDATE `TicketNameserverHist`.`Chng_Dt` = DATE_ADD(`TicketNameserverHist`.`Chng_Dt`, INTERVAL -1 second);

INSERT INTO `TicketNameserverHist`
(`T_Number`,
`TN_Name`,
`TN_Name_Fail_Cd`,
`TN_IP`,
`TN_IP_Fail_Cd`,
`TN_Order`,
`TN_TS`,
`Chng_NH`,
`Chng_Dt`)
SELECT `T_Number`,
    `DNS_Name3`,
    `DNS3_Fail_Cd`,
    `DNS_IP3`,
    `DNSIP3_Fail_Cd`,
    2,
    `T_TStamp`,
    `Chng_NH`,
    `Chng_Dt`
FROM `TicketHist`
WHERE NULLIF(`DNS_Name3`, '') IS NOT NULL 
ON DUPLICATE KEY UPDATE `TicketNameserverHist`.`Chng_Dt` = DATE_ADD(`TicketNameserverHist`.`Chng_Dt`, INTERVAL -1 second);

ALTER TABLE `Ticket`
DROP COLUMN `DNSIP3_Fail_Cd`,
DROP COLUMN `DNS_IP3`,
DROP COLUMN `DNS3_Fail_Cd`,
DROP COLUMN `DNS_Name3`,
DROP COLUMN `DNSIP2_Fail_Cd`,
DROP COLUMN `DNS_IP2`,
DROP COLUMN `DNS2_Fail_Cd`,
DROP COLUMN `DNS_Name2`,
DROP COLUMN `DNSIP1_Fail_Cd`,
DROP COLUMN `DNS_IP1`,
DROP COLUMN `DNS1_Fail_Cd`,
DROP COLUMN `DNS_Name1`;

ALTER TABLE `TicketHist`
DROP COLUMN `DNSIP3_Fail_Cd`,
DROP COLUMN `DNS_IP3`,
DROP COLUMN `DNS3_Fail_Cd`,
DROP COLUMN `DNS_Name3`,
DROP COLUMN `DNSIP2_Fail_Cd`,
DROP COLUMN `DNS_IP2`,
DROP COLUMN `DNS2_Fail_Cd`,
DROP COLUMN `DNS_Name2`,
DROP COLUMN `DNSIP1_Fail_Cd`,
DROP COLUMN `DNS_IP1`,
DROP COLUMN `DNS1_Fail_Cd`,
DROP COLUMN `DNS_Name1`;

INSERT INTO `App_Config` (`Cfg_Key`, `Cfg_Type`, `Cfg_Value`) VALUES
('nameserver_min_count', 'INT', '2'),
('nameserver_max_count', 'INT', '7');


DROP TABLE IF EXISTS `ResellerDefaultNameservers`;

ALTER TABLE Reseller_Defaults MODIFY Nic_Handle varchar(12);

CREATE TABLE `ResellerDefaultNameservers` (
  `Nic_Handle` varchar(12) NOT NULL,
  `RDN_Nameserver` VARCHAR(70) NOT NULL,
  `RDN_Order` tinyint NOT NULL,
  `RDN_TS` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  PRIMARY KEY (`Nic_Handle`, `RDN_Order`),
  UNIQUE KEY  (`Nic_Handle`, `RDN_Nameserver`),
  CONSTRAINT `ResellerDefaultNameservers_fk_ResellerDefault` FOREIGN KEY (`Nic_Handle`) REFERENCES `Reseller_Defaults` (`Nic_Handle`) ON UPDATE CASCADE ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

INSERT INTO `ResellerDefaultNameservers`
(`Nic_Handle`,
`RDN_Nameserver`,
`RDN_Order`)
SELECT `Nic_Handle`,
    `N_Server1`,
    0
FROM `Reseller_Defaults`
WHERE NULLIF(`N_Server1`, '') IS NOT NULL;

INSERT INTO `ResellerDefaultNameservers`
(`Nic_Handle`,
`RDN_Nameserver`,
`RDN_Order`)
SELECT `Nic_Handle`,
    `N_Server2`,
    1
FROM `Reseller_Defaults`
WHERE NULLIF(`N_Server2`, '') IS NOT NULL;

INSERT INTO `ResellerDefaultNameservers`
(`Nic_Handle`,
`RDN_Nameserver`,
`RDN_Order`)
SELECT `Nic_Handle`,
    `N_Server3`,
    2
FROM `Reseller_Defaults`
WHERE NULLIF(`N_Server3`, '') IS NOT NULL;

ALTER TABLE `Reseller_Defaults`
DROP COLUMN `N_Server1`,
DROP COLUMN `N_Server2`,
DROP COLUMN `N_Server3`;

-- CRS-319
ALTER TABLE EmailHist CHANGE E_To_Internal E_To_Internal text AFTER Html;
ALTER TABLE EmailHist CHANGE E_CC_Internal E_CC_Internal text AFTER E_To_Internal;
ALTER TABLE EmailHist CHANGE E_BCC_Internal E_BCC_Internal text AFTER E_CC_Internal;

-- CRS-174
INSERT INTO EmailHist SELECT *, 'ALTMANN-IEDR', NOW() FROM Email WHERE E_ID IN (4,27,62) LIMIT 3;
DELETE FROM Email WHERE E_ID IN (4,27,62) LIMIT 3;

-- Changes asked by Mario on CRS-318
INSERT INTO EmailHist SELECT *, 'ALTMANN-IEDR', NOW() FROM Email WHERE E_ID IN (69, 70) LIMIT 2;
DELETE FROM Email WHERE E_ID IN (69, 70) LIMIT 2;
