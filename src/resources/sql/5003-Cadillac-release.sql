--
-- Insert new email template for email confirmation after changing email disabler settings
--
INSERT INTO `Email` (`E_ID`, `E_Name`, `E_Text`, `E_Subject`, `E_To`, `E_From`, `active`, `Html`, `E_To_Internal`, `E_BCC_Internal`, `E_Suppressible`) VALUES
('170', 'EmailDisablerConfirmationEmail', '\nPLEASE DO NOT REPLY TO THIS EMAIL. THIS IS AN AUTOMATED EMAIL\n\nDear $BILL-C_NAME$,\n\nYou have changed settings for disabling sending emails. Here are emails for which settings were changed:\n\n$EMAILS_LIST$\n\nKind Regards\nRegistration Services Team\nIE Domain Registry Limited\nTel:  +353 (1) 2365400\nFax: +353 (1) 2300365\nWeb: www.iedr.ie\n\n\n-------------------------------------------------------------------------------------------------------\nRegistered Office: Harbour Square, Block 2, 4th Floor, Dun Laoghaire, Co. Dublin.\nRegistered in Ireland. No: 315315.\n-------------------------------------------------------------------------------------------------------\n', 'Email disabling settings changed', '$BILL-C_EMAIL$', 'registrations@iedr.ie', '0', '0', '', 'hostmaster-archive@domainregistry.ie, registrations@iedr.ie', 'Y'),
('171', 'EmailTemplateEditedNotification', 'Notification:\n\nA change has been applied to the system as at $DATE$ by $NIC_NAME$. Please see below for the updated information.\n\n$INFO$\n\nThanks\nIE Domain Registry Limited\n---------------------------------------------------------------------------------------------------------------------------------------\nRegistered Office: Harbour Square, Block 2, 4th Floor, Dun Laoghaire,\nCo. Dublin.\nRegistered in Ireland. No: 315315.\n---------------------------------------------------------------------------------------------------------------------------------------', 'Notification of change to crsweb email disabler', '', 'registrations@iedr.ie', '1', '0', 'registrations@iedr.ie', 'accounts@iedr.ie', 'Y');


--
-- Dumping data for table `EmailGroup`
--

LOCK TABLES `EmailGroup` WRITE;
/*!40000 ALTER TABLE `EmailGroup` DISABLE KEYS */;
INSERT INTO `EmailGroup` VALUES (5,'Deposit Top-Up','2014-05-23 11:54:10'),(6,'DNS','2014-05-23 11:58:38'),(7,'Modifications','2014-05-23 11:59:20'),(8,'New Registrations','2014-05-23 11:59:31'),(9,'NRP','2014-05-23 11:59:46'),(10,'Payments for domains and Invoicing','2014-05-23 12:01:04'),(11,'Transfers (BillC change)','2014-05-23 12:01:37'),(12,'Direct Customers ONLY','2014-05-23 15:38:32'),(13,'Sundry','2014-05-23 12:02:03'),(14,'IEDR internal','2014-05-23 12:02:13');
/*!40000 ALTER TABLE `EmailGroup` ENABLE KEYS */;
UNLOCK TABLES;



UPDATE Email SET EG_ID=5, E_Send_Reason="", E_Non_Suppressible_Reason="", E_Suppressible="Y" WHERE E_ID=1;
UPDATE Email SET EG_ID=10, E_Send_Reason="", E_Non_Suppressible_Reason="", E_Suppressible="Y" WHERE E_ID=2;
UPDATE Email SET EG_ID=10, E_Send_Reason="", E_Non_Suppressible_Reason="", E_Suppressible="Y" WHERE E_ID=3;
UPDATE Email SET EG_ID=12, E_Send_Reason="Customer reassurance that request is actioned", E_Non_Suppressible_Reason="", E_Suppressible="N" WHERE E_ID=4;
UPDATE Email SET EG_ID=10, E_Send_Reason="", E_Non_Suppressible_Reason="", E_Suppressible="Y" WHERE E_ID=5;
UPDATE Email SET EG_ID=10, E_Send_Reason="", E_Non_Suppressible_Reason="", E_Suppressible="Y" WHERE E_ID=7;
UPDATE Email SET EG_ID=10, E_Send_Reason="", E_Non_Suppressible_Reason="", E_Suppressible="Y" WHERE E_ID=8;
UPDATE Email SET EG_ID=12, E_Send_Reason="Customer reassurance that request is actioned", E_Non_Suppressible_Reason="", E_Suppressible="N" WHERE E_ID=9;
UPDATE Email SET EG_ID=10, E_Send_Reason="", E_Non_Suppressible_Reason="", E_Suppressible="Y" WHERE E_ID=10;
UPDATE Email SET EG_ID=9, E_Send_Reason="", E_Non_Suppressible_Reason="Contractual obligation to inform", E_Suppressible="Y" WHERE E_ID=11;
UPDATE Email SET EG_ID=12, E_Send_Reason="Contractual obligation to inform", E_Non_Suppressible_Reason="", E_Suppressible="N" WHERE E_ID=12;
UPDATE Email SET EG_ID=9, E_Send_Reason="", E_Non_Suppressible_Reason="Contractual obligation to inform", E_Suppressible="Y" WHERE E_ID=13;
UPDATE Email SET EG_ID=12, E_Send_Reason="Contractual obligation to inform", E_Non_Suppressible_Reason="", E_Suppressible="N" WHERE E_ID=14;
UPDATE Email SET EG_ID=9, E_Send_Reason="", E_Non_Suppressible_Reason="", E_Suppressible="Y" WHERE E_ID=15;
UPDATE Email SET EG_ID=9, E_Send_Reason="", E_Non_Suppressible_Reason="", E_Suppressible="Y" WHERE E_ID=16;
UPDATE Email SET EG_ID=11, E_Send_Reason="", E_Non_Suppressible_Reason="", E_Suppressible="Y" WHERE E_ID=17;
UPDATE Email SET EG_ID=11, E_Send_Reason="", E_Non_Suppressible_Reason="", E_Suppressible="Y" WHERE E_ID=18;
UPDATE Email SET EG_ID=9, E_Send_Reason="", E_Non_Suppressible_Reason="", E_Suppressible="Y" WHERE E_ID=19;
UPDATE Email SET EG_ID=10, E_Send_Reason="VAT invoice must be sent", E_Non_Suppressible_Reason="", E_Suppressible="N" WHERE E_ID=24;
UPDATE Email SET EG_ID=14, E_Send_Reason="", E_Non_Suppressible_Reason="", E_Suppressible="N" WHERE E_ID=25;
UPDATE Email SET EG_ID=12, E_Send_Reason="Helpful reminder to customers", E_Non_Suppressible_Reason="", E_Suppressible="N" WHERE E_ID=26;
UPDATE Email SET EG_ID=14, E_Send_Reason="", E_Non_Suppressible_Reason="", E_Suppressible="N" WHERE E_ID=27;
UPDATE Email SET EG_ID=7, E_Send_Reason="", E_Non_Suppressible_Reason="", E_Suppressible="Y" WHERE E_ID=28;
UPDATE Email SET EG_ID=\N, E_Send_Reason="", E_Non_Suppressible_Reason=\N, E_Suppressible="Y" WHERE E_ID=29;
UPDATE Email SET EG_ID=\N, E_Send_Reason="", E_Non_Suppressible_Reason=\N, E_Suppressible="Y" WHERE E_ID=31;
UPDATE Email SET EG_ID=\N, E_Send_Reason="", E_Non_Suppressible_Reason=\N, E_Suppressible="Y" WHERE E_ID=32;
UPDATE Email SET EG_ID=11, E_Send_Reason="", E_Non_Suppressible_Reason="", E_Suppressible="Y" WHERE E_ID=33;
UPDATE Email SET EG_ID=\N, E_Send_Reason="", E_Non_Suppressible_Reason=\N, E_Suppressible="Y" WHERE E_ID=34;
UPDATE Email SET EG_ID=11, E_Send_Reason="", E_Non_Suppressible_Reason="", E_Suppressible="Y" WHERE E_ID=35;
UPDATE Email SET EG_ID=11, E_Send_Reason="", E_Non_Suppressible_Reason="", E_Suppressible="Y" WHERE E_ID=36;
UPDATE Email SET EG_ID=\N, E_Send_Reason="", E_Non_Suppressible_Reason=\N, E_Suppressible="Y" WHERE E_ID=37;
UPDATE Email SET EG_ID=6, E_Send_Reason="", E_Non_Suppressible_Reason="", E_Suppressible="Y" WHERE E_ID=38;
UPDATE Email SET EG_ID=11, E_Send_Reason="Requested", E_Non_Suppressible_Reason="", E_Suppressible="N" WHERE E_ID=39;
UPDATE Email SET EG_ID=8, E_Send_Reason="", E_Non_Suppressible_Reason="", E_Suppressible="Y" WHERE E_ID=40;
UPDATE Email SET EG_ID=8, E_Send_Reason="", E_Non_Suppressible_Reason="", E_Suppressible="Y" WHERE E_ID=41;
UPDATE Email SET EG_ID=8, E_Send_Reason="", E_Non_Suppressible_Reason="", E_Suppressible="Y" WHERE E_ID=42;
UPDATE Email SET EG_ID=8, E_Send_Reason="", E_Non_Suppressible_Reason="", E_Suppressible="Y" WHERE E_ID=43;
UPDATE Email SET EG_ID=\N, E_Send_Reason="", E_Non_Suppressible_Reason=\N, E_Suppressible="Y" WHERE E_ID=44;
UPDATE Email SET EG_ID=6, E_Send_Reason="", E_Non_Suppressible_Reason="", E_Suppressible="Y" WHERE E_ID=45;
UPDATE Email SET EG_ID=8, E_Send_Reason="Contractual obligation to inform", E_Non_Suppressible_Reason="", E_Suppressible="N" WHERE E_ID=46;
UPDATE Email SET EG_ID=8, E_Send_Reason="", E_Non_Suppressible_Reason="", E_Suppressible="Y" WHERE E_ID=47;
UPDATE Email SET EG_ID=13, E_Send_Reason="Security - good practice", E_Non_Suppressible_Reason="", E_Suppressible="N" WHERE E_ID=49;
UPDATE Email SET EG_ID=14, E_Send_Reason="", E_Non_Suppressible_Reason="", E_Suppressible="N" WHERE E_ID=50;
UPDATE Email SET EG_ID=13, E_Send_Reason="Customer reassurance that request is actioned", E_Non_Suppressible_Reason="", E_Suppressible="N" WHERE E_ID=51;
UPDATE Email SET EG_ID=\N, E_Send_Reason="", E_Non_Suppressible_Reason=\N, E_Suppressible="Y" WHERE E_ID=55;
UPDATE Email SET EG_ID=14, E_Send_Reason="", E_Non_Suppressible_Reason="", E_Suppressible="N" WHERE E_ID=56;
UPDATE Email SET EG_ID=14, E_Send_Reason="", E_Non_Suppressible_Reason="", E_Suppressible="N" WHERE E_ID=57;
UPDATE Email SET EG_ID=14, E_Send_Reason="", E_Non_Suppressible_Reason="", E_Suppressible="N" WHERE E_ID=58;
UPDATE Email SET EG_ID=5, E_Send_Reason="", E_Non_Suppressible_Reason="", E_Suppressible="Y" WHERE E_ID=59;
UPDATE Email SET EG_ID=14, E_Send_Reason="", E_Non_Suppressible_Reason="", E_Suppressible="N" WHERE E_ID=60;
UPDATE Email SET EG_ID=10, E_Send_Reason="VAT invoice must be sent", E_Non_Suppressible_Reason="", E_Suppressible="N" WHERE E_ID=61;
UPDATE Email SET EG_ID=12, E_Send_Reason="VAT invoice must be sent", E_Non_Suppressible_Reason="", E_Suppressible="N" WHERE E_ID=62;
UPDATE Email SET EG_ID=12, E_Send_Reason="Contractual obligation to inform", E_Non_Suppressible_Reason="", E_Suppressible="N" WHERE E_ID=63;
UPDATE Email SET EG_ID=9, E_Send_Reason="", E_Non_Suppressible_Reason="Contractual obligation to inform", E_Suppressible="Y" WHERE E_ID=64;
UPDATE Email SET EG_ID=10, E_Send_Reason="", E_Non_Suppressible_Reason="", E_Suppressible="Y" WHERE E_ID=65;
UPDATE Email SET EG_ID=10, E_Send_Reason="", E_Non_Suppressible_Reason="", E_Suppressible="Y" WHERE E_ID=66;
UPDATE Email SET EG_ID=10, E_Send_Reason="", E_Non_Suppressible_Reason="", E_Suppressible="Y" WHERE E_ID=67;
UPDATE Email SET EG_ID=10, E_Send_Reason="", E_Non_Suppressible_Reason="", E_Suppressible="Y" WHERE E_ID=68;
UPDATE Email SET EG_ID=12, E_Send_Reason="", E_Non_Suppressible_Reason="", E_Suppressible="N" WHERE E_ID=69;
UPDATE Email SET EG_ID=12, E_Send_Reason="", E_Non_Suppressible_Reason="", E_Suppressible="N" WHERE E_ID=70;
UPDATE Email SET EG_ID=10, E_Send_Reason="", E_Non_Suppressible_Reason="", E_Suppressible="Y" WHERE E_ID=71;
UPDATE Email SET EG_ID=10, E_Send_Reason="", E_Non_Suppressible_Reason="", E_Suppressible="Y" WHERE E_ID=72;
UPDATE Email SET EG_ID=5, E_Send_Reason="", E_Non_Suppressible_Reason="", E_Suppressible="Y" WHERE E_ID=73;
UPDATE Email SET EG_ID=12, E_Send_Reason="Contractual obligation to inform", E_Non_Suppressible_Reason="", E_Suppressible="N" WHERE E_ID=74;
UPDATE Email SET EG_ID=14, E_Send_Reason="", E_Non_Suppressible_Reason="", E_Suppressible="N" WHERE E_ID=75;
UPDATE Email SET EG_ID=\N, E_Send_Reason="", E_Non_Suppressible_Reason=\N, E_Suppressible="Y" WHERE E_ID=76;
UPDATE Email SET EG_ID=\N, E_Send_Reason="", E_Non_Suppressible_Reason=\N, E_Suppressible="Y" WHERE E_ID=77;
UPDATE Email SET EG_ID=13, E_Send_Reason="", E_Non_Suppressible_Reason="", E_Suppressible="Y" WHERE E_ID=79;
UPDATE Email SET EG_ID=\N, E_Send_Reason="", E_Non_Suppressible_Reason=\N, E_Suppressible="Y" WHERE E_ID=80;
UPDATE Email SET EG_ID=\N, E_Send_Reason="", E_Non_Suppressible_Reason=\N, E_Suppressible="Y" WHERE E_ID=81;
UPDATE Email SET EG_ID=13, E_Send_Reason="Customer reassurance that request is actioned", E_Non_Suppressible_Reason="", E_Suppressible="N" WHERE E_ID=82;
UPDATE Email SET EG_ID=11, E_Send_Reason="Helpful reminder to customers", E_Non_Suppressible_Reason="", E_Suppressible="N" WHERE E_ID=83;
UPDATE Email SET EG_ID=11, E_Send_Reason="Customer reassurance that request is actioned", E_Non_Suppressible_Reason="", E_Suppressible="N" WHERE E_ID=84;
UPDATE Email SET EG_ID=7, E_Send_Reason="", E_Non_Suppressible_Reason="", E_Suppressible="Y" WHERE E_ID=85;
UPDATE Email SET EG_ID=8, E_Send_Reason="", E_Non_Suppressible_Reason="", E_Suppressible="Y" WHERE E_ID=86;
UPDATE Email SET EG_ID=\N, E_Send_Reason="", E_Non_Suppressible_Reason=\N, E_Suppressible="Y" WHERE E_ID=87;
UPDATE Email SET EG_ID=\N, E_Send_Reason="", E_Non_Suppressible_Reason=\N, E_Suppressible="Y" WHERE E_ID=88;
UPDATE Email SET EG_ID=\N, E_Send_Reason="", E_Non_Suppressible_Reason=\N, E_Suppressible="Y" WHERE E_ID=89;
UPDATE Email SET EG_ID=\N, E_Send_Reason="", E_Non_Suppressible_Reason=\N, E_Suppressible="Y" WHERE E_ID=90;
UPDATE Email SET EG_ID=7, E_Send_Reason="", E_Non_Suppressible_Reason="", E_Suppressible="Y" WHERE E_ID=91;
UPDATE Email SET EG_ID=13, E_Send_Reason="Contractual obligation to inform", E_Non_Suppressible_Reason="", E_Suppressible="N" WHERE E_ID=92;
UPDATE Email SET EG_ID=\N, E_Send_Reason="", E_Non_Suppressible_Reason=\N, E_Suppressible="Y" WHERE E_ID=93;
UPDATE Email SET EG_ID=10, E_Send_Reason="Customer reassurance that request is actioned", E_Non_Suppressible_Reason="", E_Suppressible="N" WHERE E_ID=101;
UPDATE Email SET EG_ID=6, E_Send_Reason="DNS change is uber-important", E_Non_Suppressible_Reason="", E_Suppressible="N" WHERE E_ID=102;
UPDATE Email SET EG_ID=8, E_Send_Reason="Important to notify re application failure", E_Non_Suppressible_Reason="", E_Suppressible="N" WHERE E_ID=103;
UPDATE Email SET EG_ID=13, E_Send_Reason="", E_Non_Suppressible_Reason="", E_Suppressible="Y" WHERE E_ID=104;
UPDATE Email SET EG_ID=7, E_Send_Reason="Security - good practice", E_Non_Suppressible_Reason="", E_Suppressible="N" WHERE E_ID=105;
UPDATE Email SET EG_ID=7, E_Send_Reason="Important to notify re Mods", E_Non_Suppressible_Reason="", E_Suppressible="N" WHERE E_ID=120;
UPDATE Email SET EG_ID=12, E_Send_Reason="Customer reassurance that request is actioned", E_Non_Suppressible_Reason="", E_Suppressible="N" WHERE E_ID=121;
UPDATE Email SET EG_ID=7, E_Send_Reason="Important to notify re Mods", E_Non_Suppressible_Reason="", E_Suppressible="N" WHERE E_ID=129;
UPDATE Email SET EG_ID=12, E_Send_Reason="Customer reassurance that request is actioned", E_Non_Suppressible_Reason="", E_Suppressible="N" WHERE E_ID=130;
UPDATE Email SET EG_ID=7, E_Send_Reason="Important to notify re Mods", E_Non_Suppressible_Reason="", E_Suppressible="N" WHERE E_ID=131;
UPDATE Email SET EG_ID=12, E_Send_Reason="Customer reassurance that request is actioned", E_Non_Suppressible_Reason="", E_Suppressible="N" WHERE E_ID=132;
UPDATE Email SET EG_ID=7, E_Send_Reason="Important to notify re Mods", E_Non_Suppressible_Reason="", E_Suppressible="N" WHERE E_ID=133;
UPDATE Email SET EG_ID=6, E_Send_Reason="", E_Non_Suppressible_Reason="DNS change is uber-important", E_Suppressible="Y" WHERE E_ID=141;
UPDATE Email SET EG_ID=6, E_Send_Reason="", E_Non_Suppressible_Reason="DNS change is uber-important", E_Suppressible="Y" WHERE E_ID=142;
UPDATE Email SET EG_ID=12, E_Send_Reason="DNS change is uber-important", E_Non_Suppressible_Reason="", E_Suppressible="N" WHERE E_ID=145;
UPDATE Email SET EG_ID=12, E_Send_Reason="DNS change is uber-important", E_Non_Suppressible_Reason="", E_Suppressible="N" WHERE E_ID=146;
UPDATE Email SET EG_ID=6, E_Send_Reason="DNS change is uber-important", E_Non_Suppressible_Reason="", E_Suppressible="N" WHERE E_ID=150;
UPDATE Email SET EG_ID=7, E_Send_Reason="Customer reassurance that request is actioned", E_Non_Suppressible_Reason="", E_Suppressible="N" WHERE E_ID=151;
UPDATE Email SET EG_ID=14, E_Send_Reason="", E_Non_Suppressible_Reason="", E_Suppressible="N" WHERE E_ID=160;
UPDATE Email SET EG_ID=14, E_Send_Reason="", E_Non_Suppressible_Reason="", E_Suppressible="N" WHERE E_ID=161;
UPDATE Email SET EG_ID=14, E_Send_Reason="", E_Non_Suppressible_Reason="", E_Suppressible="N" WHERE E_ID=171;

INSERT INTO EmailHist SELECT E_ID,E_Name,E_Text,E_Subject,E_To,E_CC,E_BCC,E_To_Internal,E_CC_Internal,E_BCC_Internal,E_From,active,Html,EG_ID,E_TS,E_Suppressible,E_Send_Reason,E_Non_Suppressible_Reason, 'ALTMANN-IEDR', NOW() FROM Email WHERE E_ID IN ('6','20','21','22','23','29','30','31','32','34','37','44','52','53','54','55','76','77','78','80','81','87','88','89','90','93');
DELETE FROM Email  WHERE E_ID IN ('6','20','21','22','23','29','30','31','32','34','37','44','52','53','54','55','76','77','78','80','81','87','88','89','90','93');
UPDATE Email SET E_To = NULL WHERE E_ID IN (27,57,56) LIMIT 3;

ALTER TABLE `EmailGroup`
ADD COLUMN `EG_Visible` CHAR(1) DEFAULT 'Y' AFTER `EG_Name`;

ALTER TABLE `EmailGroupHist`
ADD COLUMN `EG_Visible` CHAR(1) DEFAULT 'Y' AFTER `EG_Name`;

UPDATE Email SET E_Text = '* * This is very important, please read it carefully **\n\nPlease do not reply directly to this email as this email address is not monitored.\n\nDear $ADMIN-C_NAME$\n\nAs Administrative Contact for the domain name $DOMAIN$, we would like to inform you that your domain name has been entered into IEDR Non-Renewal Process (NRP), as requested by your billing agent $BILL-C_NAME$.\n\nIf you wish to keep $DOMAIN$ \nPlease immediately contact $BILL-C_NAME$ on $BILL-C_TEL$ who are currently responsible for managing your domain name.\n\nWhat does this mean for you?\nThis means that if your domain name is not removed from the IEDR non-renewed process by $SUSPENSION_DATE$ it will be suspended at 12.00 mid-day on this date. If your domain name is suspended, you will no longer have access to your website or email which is attached to this address.\nUltimate deletion of your domain name will occur on $DELETION_DATE$, where by your domain will become available for re-registration to the general public on a first come, first served basis.\n\nPlease take note: This is the only notification that you will receive from IE Domain Registry. At the point the domain name is suspended on $SUSPENSION_DATE$, we will be unable to make further contact with you.\n\nIf you do not wish to renew your domain name:\nYou should take no further action, the domain name will ultimately be deleted on $DELETION_DATE$.\n\nWhy should you renew $DOMAIN$?\n- It is your Irish identity on the Internet $DOMAIN$ shows you are located or doing business in Ireland.\n- Increased consumer confidence and credibility - All .ie holders must authenticate their claim to their domain name; hence we have visibility of who is behind an .ie domain name. As a result online fraud is less likely to occur on a website using an .ie address.\n- Greatly improved listing in search engines- .ie addresses receives higher ratings in search engines, such as Google, than Irish based .com addresses.\n- Security: the .ie namespace is consistently ranked amount the top five safest in the world.\n\n\nKind Regards \n\nIE Domain Registry Limited\nWeb: <www.iedr.ie>\n--------------------------------------------------------------------------------------------------------------------------------------\nRegistered Office: Harbour Square, Block 2, 4th Floor, Dun Laoghaire, Co. Dublin.\nRegistered in Ireland. No: 315315.\n--------------------------------------------------------------------------------------------------------------------------------------\n\n\n' where E_ID = 13 limit 1;
UPDATE Email SET E_Subject = 'Your domain name $DOMAIN$ has not been renewed.' where E_ID = 64 limit 1;
UPDATE Email set E_To = '$BILL-C_EMAIL$' where E_ID = 66 limit 1;

UPDATE Email SET EG_ID=11, E_Send_Reason="Requested", E_Non_Suppressible_Reason="", E_Suppressible="N" WHERE E_ID=17;
UPDATE Email SET EG_ID=11, E_Send_Reason="Requested", E_Non_Suppressible_Reason="", E_Suppressible="N" WHERE E_ID=18;
UPDATE Email SET EG_ID=9, E_Send_Reason="Requested", E_Non_Suppressible_Reason="", E_Suppressible="N" WHERE E_ID=19;
UPDATE Email SET EG_ID=11, E_Send_Reason="Requested", E_Non_Suppressible_Reason="", E_Suppressible="N" WHERE E_ID=33;

UPDATE Email set E_To = '$BILL-C_EMAIL$' where E_ID IN (65,68) limit 2;
