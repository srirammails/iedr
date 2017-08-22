--
-- Changes for CRS-300 - uploader email templates and appconfig setup
--
INSERT INTO `App_Config` (`Cfg_Key`, `Cfg_Type`, `Cfg_Value`) VALUES
('document_size_limit_in_mb', 'INT', '5'),
('document_count_limit', 'INT', '5'),
('document_allowed_types', 'STRING', 'pdf,doc,docx,jpeg,jpg,gif,png,tiff,tif,odt,bmp');

DELETE FROM `App_Config` WHERE `Cfg_Key`='incoming_docs_path_server_dir';
UPDATE App_Config SET Cfg_Value = '/attachment_assigned' WHERE Cfg_Key = 'incoming_docs_path_attachment_assigned' LIMIT 1;
UPDATE App_Config SET Cfg_Value = '/attachment_new' WHERE Cfg_Key = 'incoming_docs_path_attachment_new' LIMIT 1;
UPDATE App_Config SET Cfg_Value = '/fax_assigned' WHERE Cfg_Key = 'incoming_docs_path_fax_assigned' LIMIT 1;
UPDATE App_Config SET Cfg_Value = '/fax_new' WHERE Cfg_Key = 'incoming_docs_path_fax_new' LIMIT 1;
UPDATE App_Config SET Cfg_Value = '/paper_assigned' WHERE Cfg_Key = 'incoming_docs_path_paper_assigned' LIMIT 1;
UPDATE App_Config SET Cfg_Value = '/paper_new' WHERE Cfg_Key = 'incoming_docs_path_paper_new' LIMIT 1;

INSERT INTO `Email` (`E_ID`, `E_Name`, `E_Text`, `E_Subject`, `E_To`, `E_From`, `active`, `Html`, `E_To_Internal`, `E_BCC_Internal`, `E_Suppressible`, `EG_ID`) VALUES
('180', 'DocumentUploadConfirmation', 'PLEASE DO NOT REPLY TO THIS EMAIL. THIS IS AN AUTOMATED EMAIL\n\nDear $CREATOR-C_EMAIL$,\nYou have submitted documents to the IEDR for review.\n$INFO$\n\nFiles marked “file saved” have been received and will be reviewed by our Registrations Services Administrators.\n\nFiles marked “discarded” have not been received and cannot be processed. Please submit alternative documentation instead.\n\nKind Regards\nRegistration Services Team\nIE Domain Registry Limited\nTel:  +353 (1) 2365400\nFax: +353 (1) 2300365\nWeb: www.iedr.ie\n\n\n-------------------------------------------------------------------------------------------------------\nRegistered Office: Harbour Square, Block 2, 4th Floor, Dun Laoghaire, Co. Dublin.\nRegistered in Ireland. No: 315315.\n-------------------------------------------------------------------------------------------------------\n', 'Document Upload Report', '$CREATOR-C_EMAIL$', 'registrations@iedr.ie', '1', '0', 'registrations@iedr.ie', '', 'Y', 13),
('181', 'DocumentUploadFailure', 'PLEASE DO NOT REPLY TO THIS EMAIL. THIS IS AN AUTOMATED EMAIL\n\nDear $CREATOR-C_EMAIL$,\nYou have submitted documents to the IEDR for review, but there was an error processing your email:\n$INFO$\n\nKind Regards\nRegistration Services Team\nIE Domain Registry Limited\nTel:  +353 (1) 2365400\nFax: +353 (1) 2300365\nWeb: www.iedr.ie\n\n\n-------------------------------------------------------------------------------------------------------\nRegistered Office: Harbour Square, Block 2, 4th Floor, Dun Laoghaire, Co. Dublin.\nRegistered in Ireland. No: 315315.\n-------------------------------------------------------------------------------------------------------\n', 'Document Upload Report', '$CREATOR-C_EMAIL$', 'registrations@iedr.ie', '1', '0', 'registrations@iedr.ie', '', 'Y', 13),
('182', 'DocumentMailParseFailure', 'PLEASE DO NOT REPLY TO THIS EMAIL. THIS IS AN AUTOMATED EMAIL\n\nDear $CREATOR-C_EMAIL$,\nYou have submitted documents to the IEDR for review, but no information about domains was detected in your email.\n$INFO$\n\nFiles marked “file saved” have been received and will be reviewed by our Registrations Services Administrators. These will be manually assigned to a domain based on the information they provide.\n\nFiles marked “discarded” have not been received and cannot be processed. Please submit alternative documentation instead.\n\nKind Regards\nRegistration Services Team\nIE Domain Registry Limited\nTel:  +353 (1) 2365400\nFax: +353 (1) 2300365\nWeb: www.iedr.ie\n\n\n-------------------------------------------------------------------------------------------------------\nRegistered Office: Harbour Square, Block 2, 4th Floor, Dun Laoghaire, Co. Dublin.\nRegistered in Ireland. No: 315315.\n-------------------------------------------------------------------------------------------------------\n', 'Document Upload Report', '$CREATOR-C_EMAIL$', 'registrations@iedr.ie', '1', '0', 'registrations@iedr.ie', '', 'Y', 13);

--
-- CRS-355
--

DELETE FROM `ResellerDefaultNameservers`
 WHERE `RDN_Nameserver` IS NULL
    OR `RDN_Nameserver` = '';

ALTER TABLE `ResellerDefaultNameservers`
  MODIFY `RDN_Nameserver` VARCHAR(255) NOT NULL;

ALTER TABLE `ResellerDefaultNameservers`
  ADD UNIQUE KEY  (`Nic_Handle`, `RDN_Nameserver`);


--
-- Changes related to Authorization Code (CRS-304)
--

ALTER TABLE Domain
  ADD COLUMN (`D_Authcode` char(12)),
  ADD COLUMN (`D_Authc_Exp_Dt` date),
  ADD COLUMN (`D_Authc_Portal_Cnt` tinyint DEFAULT 0);

ALTER TABLE DomainHist
  ADD COLUMN (`D_Authcode` char(12)),
  ADD COLUMN (`D_Authc_Exp_Dt` date),
  ADD COLUMN (`D_Authc_Portal_Cnt` tinyint DEFAULT 0);

-- New emails templates sending authcode

DELETE FROM `Email` WHERE `E_ID` IN (174, 175, 176, 177);

INSERT INTO `Email` (`E_ID`, `E_Name`, `E_Text`, `E_Subject`, `E_To`, `E_CC`, `E_BCC_Internal`, `E_From`, `EG_ID`) VALUES
(174, 'AuthcodeOnDemand', '\nPLEASE DO NOT REPLY TO THIS EMAIL. THIS IS AN AUTOMATED EMAIL\n\nYou are receiving this email following a request for the Authcode required to transfer the domain $DOMAIN$ to another account.\n\nThe Authcode for the domain $DOMAIN$ is: $AUTHCODE$. This is valid until $AUTHCODE_EXP_DATE$.\n\nKind Regards\nRegistration Services Team\nIE Domain Registry Limited\nTel:  +353 (1) 2365400\nFax: +353 (1) 2300365\nWeb: www.iedr.ie\n\n\n----------------------------------------------------------------------------\n---------------------------\nRegistered Office: Harbour Square, Block 2, 4th Floor, Dun Laoghaire, Co.\nDublin.\nRegistered in Ireland. No: 315315.\n----------------------------------------------------------------------------\n---------------------------\n', 'Authcode for $DOMAIN$', '$BILL-C_EMAIL$', '$ADMIN-C_EMAIL$', NULL, 'registrations@iedr.ie', 11),
(175, 'AuthcodeVerificationFailure', '\nPLEASE DO NOT REPLY TO THIS EMAIL. THIS IS AN AUTOMATED EMAIL\n\nAlert: You have unsuccessfully entered the Authcode three times. Please re-submit your request using the correct Authcode associated with the Domain name: $DOMAIN$. Hint: Authcode is case sensitive.\n\nIf you are unsure why you are receiving this email, please contact the IEDR for more information.\n\nKind Regards\nRegistration Services Team\nIE Domain Registry Limited\nTel:  +353 (1) 2365400\nFax: +353 (1) 2300365\nWeb: www.iedr.ie\n\n\n----------------------------------------------------------------------------\n---------------------------\nRegistered Office: Harbour Square, Block 2, 4th Floor, Dun Laoghaire, Co.\nDublin.\nRegistered in Ireland. No: 315315.\n----------------------------------------------------------------------------\n---------------------------\n', 'Authcode violation for $DOMAIN$', '$BILL-C_EMAIL$', NULL, 'asd@iedr.ie', NULL, 11),
(176, 'AuthcodeBulkExport', '\nPLEASE DO NOT REPLY TO THIS EMAIL. THIS IS AN AUTOMATED EMAIL\n\nYou are receiving this email following a request for the Authcodes required to transfer the domains: $DOMAIN_LIST$ to another account. The Authcodes you have requested are as follows:\n\n$DOMAIN_TABLE_WITH_AUTHCODES$\n\nThese are valid until $AUTHCODE_EXP_DATE$.\n\nKind Regards\nRegistration Services Team\nIE Domain Registry Limited\nTel:  +353 (1) 2365400\nFax: +353 (1) 2300365\nWeb: www.iedr.ie\n\n\n----------------------------------------------------------------------------\n---------------------------\nRegistered Office: Harbour Square, Block 2, 4th Floor, Dun Laoghaire, Co.\nDublin.\nRegistered in Ireland. No: 315315.\n----------------------------------------------------------------------------\n---------------------------\n', 'Bulk export of authcodes', '$BILL-C_EMAIL$', '$ADMIN-C_EMAIL$', NULL, 'registrations@iedr.ie', 11),
(177, 'AuthcodeFromPortal', '\nPLEASE DO NOT REPLY TO THIS EMAIL. THIS IS AN AUTOMATED EMAIL\n\nYou are receiving this email following a request for the Authcode required\nto transfer the domain $DOMAIN$ to another account.\n\nThe Authcode for the domain $DOMAIN$ is: $AUTHCODE$. This is valid until\n$AUTHCODE_EXP_DATE$.\n\nKind Regards\nRegistration Services Team\nIE Domain Registry Limited\nTel:  +353 (1) 2365400\nFax: +353 (1) 2300365\nWeb: www.iedr.ie\n\n\n----------------------------------------------------------------------------\n---------------------------\nRegistered Office: Harbour Square, Block 2, 4th Floor, Dun Laoghaire, Co.\nDublin.\nRegistered in Ireland. No: 315315.\n----------------------------------------------------------------------------\n---------------------------\n', 'Authcode for $DOMAIN$', '$NIC_EMAIL$', NULL, NULL, 'registrations@iedr.ie', 11);

UPDATE `Email`
   SET `E_Text` = '\nPLEASE DO NOT REPLY TO THIS EMAIL. THIS IS AN AUTOMATED EMAIL\n\nDear $GAINING_BILL-C_NAME$\n\nAn application (Ticket Number $TICKET_ID$) has been received by the IE Domain Registry Limited (IEDR) requesting the transfer of the domain name $DOMAIN$ to you for billing purposes.\n\nYou will receive confirmation once this transfer is complete.\n\nKind Regards\nRegistration Services Team\nIE Domain Registry Limited\nTel:  +353 (1) 2365400\nFax: +353 (1) 2300365\nWeb: www.iedr.ie\n\n\n----------------------------------------------------------------------------\n---------------------------\nRegistered Office: Harbour Square, Block 2, 4th Floor, Dun Laoghaire, Co.\nDublin.\nRegistered in Ireland. No: 315315.\n----------------------------------------------------------------------------\n---------------------------\n'
 WHERE `E_ID` = 33;

-- Validity period

DELETE FROM `App_Config` WHERE `Cfg_Key` IN ('authcode_expiration_period', 'authcode_failure_limit', 'authcode_portal_limit');

INSERT INTO `App_Config` VALUES
('authcode_expiration_period', 'INT', 14),
('authcode_failure_limit', 'INT', 3),
('authcode_portal_limit', 'INT', 2);

-- Scheduler job

DELETE FROM `SchedulerConfig` WHERE `name` = 'AuthCodeCleanup';

INSERT INTO `SchedulerConfig` (`name`, `schedule`, `Active`) VALUES
('AuthCodeCleanup', '10 00 * * *', 'Y');

--
-- CRS-316 Changes to historical tables to use Chng_ID as primary historical id
--   instead of Chng_Dt
--

ALTER TABLE `AccountHist`
ADD COLUMN `Chng_ID` INT UNSIGNED NOT NULL AUTO_INCREMENT FIRST,
DROP PRIMARY KEY,
ADD PRIMARY KEY (`Chng_ID`),
ADD INDEX `idx_AccountHist_A_Number_ChngDt` (`A_Number` ASC, `Chng_Dt` ASC);

ALTER TABLE `AccountFlagsHist`
DROP FOREIGN KEY `FK_accountflagshist`;
ALTER TABLE `AccountFlagsHist`
ADD COLUMN `Chng_ID` INT UNSIGNED NOT NULL FIRST,
DROP PRIMARY KEY,
ADD INDEX `idx_AccountFlagsHist_A_Number` (`A_Number` ASC);

UPDATE `AccountFlagsHist` afh LEFT JOIN `AccountHist` ah USING (`A_Number`, `Chng_Dt`)
SET afh.Chng_ID = ah.Chng_ID;

ALTER TABLE `AccountFlagsHist`
DROP COLUMN `Chng_Dt`,
ADD PRIMARY KEY (`Chng_ID`);

ALTER TABLE `AccountFlagsHist`
ADD CONSTRAINT `fk_AccountFlagsHist_AccountHist`
  FOREIGN KEY (`Chng_ID`)
  REFERENCES `AccountHist` (`Chng_ID`)
  ON DELETE CASCADE
  ON UPDATE RESTRICT;



ALTER TABLE `NicHandleHist`
ADD COLUMN `Chng_ID` INT UNSIGNED NOT NULL AUTO_INCREMENT FIRST,
DROP PRIMARY KEY,
ADD PRIMARY KEY (`Chng_ID`);

ALTER TABLE `TelecomHist`
ADD COLUMN `Chng_ID` INT UNSIGNED NOT NULL FIRST,
DROP PRIMARY KEY;

ALTER TABLE `PaymentHist`
ADD COLUMN `Chng_ID` INT UNSIGNED NOT NULL FIRST,
DROP PRIMARY KEY,
ADD INDEX `idx_PaymentHist_Billing_Contact` (`Billing_Contact` ASC);

DELETE th
FROM `TelecomHist` th LEFT JOIN `NicHandleHist` nhh USING (`Nic_Handle`, `Chng_Dt`)
WHERE nhh.Chng_ID IS NULL;

UPDATE `TelecomHist` th LEFT JOIN `NicHandleHist` nhh USING (`Nic_Handle`, `Chng_Dt`)
SET th.Chng_ID = nhh.Chng_ID;

DELETE ph
FROM `PaymentHist` ph LEFT JOIN `NicHandleHist` nhh ON  ph.`Billing_Contact` = nhh.`Nic_Handle` AND ph.`Chng_Dt` = nhh.`Chng_Dt`
WHERE nhh.Chng_ID IS NULL;

UPDATE `PaymentHist` ph LEFT JOIN `NicHandleHist` nhh ON  ph.`Billing_Contact` = nhh.`Nic_Handle` AND ph.`Chng_Dt` = nhh.`Chng_Dt`
SET ph.Chng_ID = nhh.Chng_ID;

ALTER TABLE `TelecomHist`
DROP COLUMN `Chng_Dt`,
DROP COLUMN `Chng_NH`,
ADD PRIMARY KEY (`Chng_ID`, `Phone`, `Type`);
ALTER TABLE `TelecomHist`
ADD CONSTRAINT `fk_TelecomHist_NicHandleHist`
  FOREIGN KEY (`Chng_ID`)
  REFERENCES `NicHandleHist` (`Chng_ID`)
  ON DELETE CASCADE
  ON UPDATE RESTRICT;


ALTER TABLE `PaymentHist`
DROP COLUMN `Chng_Dt`,
DROP COLUMN `Chng_NH`,
ADD PRIMARY KEY (`Chng_ID`);
ALTER TABLE `PaymentHist`
ADD CONSTRAINT `fk_PaymentHist_NicHandleHist`
  FOREIGN KEY (`Chng_ID`)
  REFERENCES `NicHandleHist` (`Chng_ID`)
  ON DELETE CASCADE
  ON UPDATE RESTRICT;


ALTER TABLE `TicketHist`
ADD COLUMN `Chng_ID` INT UNSIGNED NOT NULL AUTO_INCREMENT FIRST,
DROP PRIMARY KEY,
ADD PRIMARY KEY (`Chng_ID`);

ALTER TABLE `TicketNameserverHist`
ADD COLUMN `Chng_ID` INT UNSIGNED NOT NULL FIRST,
DROP PRIMARY KEY,
ADD INDEX `idx_TicketNameserverHist_T_Number` (`T_Number` ASC);

UPDATE `TicketNameserverHist` tnh LEFT JOIN `TicketHist` th USING (`T_Number`, `Chng_Dt`)
SET tnh.Chng_ID = th.Chng_ID;

ALTER TABLE `TicketNameserverHist`
DROP COLUMN `Chng_Dt`,
DROP COLUMN `Chng_NH`,
ADD PRIMARY KEY (`TN_Name`, `Chng_ID`);
ALTER TABLE `TicketNameserverHist`
ADD CONSTRAINT `fk_TicketNameserverHist_TicketHist`
  FOREIGN KEY (`Chng_ID`)
  REFERENCES `TicketHist` (`Chng_ID`)
  ON DELETE CASCADE
  ON UPDATE RESTRICT;


ALTER TABLE `EmailDisablerHist`
ADD COLUMN `Chng_ID` INT UNSIGNED NOT NULL AUTO_INCREMENT FIRST,
DROP PRIMARY KEY,
ADD PRIMARY KEY (`Chng_ID`),
ADD INDEX `idx_EmailDisablerHist_E_ID_Nic_Handle` (`ED_Email_E_ID` ASC, `ED_Nic_Handle` ASC);

ALTER TABLE `EmailGroupHist`
ADD COLUMN `Chng_ID` INT UNSIGNED NOT NULL AUTO_INCREMENT FIRST,
DROP PRIMARY KEY,
ADD PRIMARY KEY (`Chng_ID`),
ADD INDEX `idx_EmailGroupHist_EG_ID` (`EG_ID` ASC);

ALTER TABLE `EmailHist`
ADD COLUMN `Chng_ID` INT UNSIGNED NOT NULL AUTO_INCREMENT FIRST,
DROP PRIMARY KEY,
ADD PRIMARY KEY (`Chng_ID`),
ADD INDEX `idx_EmailHist_E_ID` (`E_ID` ASC);



ALTER TABLE `DomainHist`
ADD COLUMN `Chng_ID` INT UNSIGNED NOT NULL AUTO_INCREMENT FIRST,
DROP PRIMARY KEY,
ADD PRIMARY KEY (`Chng_ID`);

ALTER TABLE `DNSHist`
ADD COLUMN `Chng_ID` INT UNSIGNED NOT NULL FIRST,
DROP PRIMARY KEY;

ALTER TABLE `ContactHist`
ADD COLUMN `Chng_ID` INT UNSIGNED NOT NULL FIRST,
DROP PRIMARY KEY;

DELETE dnsh
FROM `DNSHist` dnsh LEFT JOIN `DomainHist` dh USING (`D_Name`, `Chng_Dt`)
WHERE dh.Chng_ID IS NULL;

UPDATE `DNSHist` dnsh LEFT JOIN `DomainHist` dh USING (`D_Name`, `Chng_Dt`)
SET dnsh.Chng_ID = dh.Chng_ID;

DELETE ch
FROM `ContactHist` ch LEFT JOIN `DomainHist` dh USING (`D_Name`, `Chng_Dt`)
WHERE dh.Chng_ID IS NULL;

UPDATE `ContactHist` ch LEFT JOIN `DomainHist` dh USING (`D_Name`, `Chng_Dt`)
SET ch.Chng_ID = dh.Chng_ID;


ALTER TABLE `DNSHist`
DROP COLUMN `Chng_Dt`,
DROP COLUMN `Chng_NH`,
ADD PRIMARY KEY (`Chng_ID`, `D_Name`, `DNS_Name`);
ALTER TABLE `DNSHist`
ADD CONSTRAINT `fk_DNSHist_DomainHist`
  FOREIGN KEY (`Chng_ID`)
  REFERENCES `DomainHist` (`Chng_ID`)
  ON DELETE CASCADE
  ON UPDATE RESTRICT;

ALTER TABLE `ContactHist`
DROP COLUMN `Chng_Dt`,
DROP COLUMN `Chng_NH`,
ADD PRIMARY KEY (`Chng_ID`, `D_Name`, `Contact_NH`, `Type`);
ALTER TABLE `ContactHist`
ADD CONSTRAINT `fk_ContactHist_DomainHist`
  FOREIGN KEY (`Chng_ID`)
  REFERENCES `DomainHist` (`Chng_ID`)
  ON DELETE CASCADE
  ON UPDATE RESTRICT;

ALTER TABLE `DeleteListHist`
DROP COLUMN `D_Add_Id`,
ADD COLUMN `Chng_ID` INT UNSIGNED NOT NULL AUTO_INCREMENT FIRST,
DROP INDEX `D_Name` ,
ADD INDEX `Idx_DeleteListHist_D_Name_D_Add_Dt` USING BTREE (`D_Name` ASC, `D_Add_Dt` ASC),
DROP PRIMARY KEY,
ADD PRIMARY KEY (`Chng_ID`);

ALTER TABLE `TransfersHist`
ADD COLUMN `Chng_ID` INT UNSIGNED NOT NULL AUTO_INCREMENT FIRST,
DROP PRIMARY KEY,
ADD PRIMARY KEY (`Chng_ID`),
ADD INDEX `Idx_TransfersHist_All` (`D_Name` ASC, `Tr_Date` ASC, `Old_Nic_Handle` ASC, `New_Nic_Handle` ASC);

ALTER TABLE `AccessHist`
DROP COLUMN `Operation_Type`,
ADD COLUMN `Chng_ID` INT UNSIGNED NOT NULL AUTO_INCREMENT FIRST,
DROP PRIMARY KEY,
ADD PRIMARY KEY (`Chng_ID`),
ADD INDEX `idx_AccessHist_Nic_Handle_Chng_Dt` (`Nic_Handle` ASC, `Chng_Dt` ASC);
