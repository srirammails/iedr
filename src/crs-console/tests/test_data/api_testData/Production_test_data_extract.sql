Insert INTO NicHandle VALUES ('ABC1-IEDR', 'Test Registrar Nic', '600', 'Registrar co Limited', '1 The Road, Some Street', 'Dublin', 'Ireland', 'ciara@iedr.ie','Active', NOW(), NOW(), NOW(),'','test data generation','ABC1-IEDR');
Insert into Account VALUES ('600', 'Test Registrar', 'ABC1-IEDR', '1 The Road, Some Street', 'Dublin', 'Ireland', 'www.testregistrar.ie', '012365400', '012365400', 'Active', NOW(), 'TradeAChar', '0.00', NOW(), NOW(), 'Monthly', 'January', 'test data generation script', '3');
Insert into Access VALUES ('ABC1-IEDR', SHA1('password'), '1024', NULL);
Insert into Class_Category_Permission VALUES ('ABC1-IEDR', '2', '3');
REPLACE into CategoryPermission VALUES ('ABC1-IEDR', '3');
Insert into Telecom VALUES('ABC1-IEDR', '+35312345345', 'P');
Insert into Telecom VALUES('ABC1-IEDR', '+35312335345', 'P');
Insert into Telecom VALUES('ABC1-IEDR', '+35312345300', 'F');
Insert into Telecom VALUES('ABC1-IEDR', '+35312355300', 'F');
Insert INTO NicHandle VALUES ('AXXX1-IEDR', 'Test Registrar Admin Nic', '600', 'Registrar co Limited', '1 The Road, Some Street', 'Dublin', 'Ireland', 'ciara@iedr.ie','Active', NOW(), NOW(), NOW(),'','test data generation','ABC1-IEDR');
Insert into Domain values ('example0001.ie', 'Test Holder 0001', 'Sole Trader',  'Personal Trading Name', '00000600', 'Active',  CURDATE(),  CURDATE() - Interval 6 Month,  CURDATE() + Interval 6 Month, NOW(), 'N', 'Y', 'Y', 'Test Remark', 'N');
Insert into Domain values ('example0002.ie', 'Test Holder 0002', 'Sole Trader',  'Registered Business Name', '00000600', 'Active',  CURDATE(),  LAST_DAY(CURDATE() + Interval 10 Month) - Interval 15 Day- Interval 1 Year,  LAST_DAY(CURDATE() + Interval 10 Month) - Interval 15 Day, NOW(), 'N', 'Y', 'Y', 'Test Remark', 'N');
Insert into Domain values ('example0003.ie', 'Test Holder 0003', 'Constitutional Body',  'State Agency Name', '00000600', 'Active',  CURDATE(),  LAST_DAY(CURDATE() + Interval 11 Month) - Interval 2 Day- Interval 1 Year,  LAST_DAY(CURDATE() + Interval 11 Month) - Interval 2 Day, NOW(), 'N', 'Y', 'Y', 'Test Remark', 'N');
Insert into Domain values ('example0004.ie', 'Test Holder 0004', 'Unincorporated Association',  'Discretionary Name', '00000600', 'Active',  CURDATE(),  CURDATE(),  CURDATE(), NOW(), 'N', 'Y', 'Y', 'Test Remark', 'N');
Insert into Domain values ('example0005.ie', 'Test Holder 0005', 'Body Corporate (Ltd,PLC,Company)',  'Discretionary Name', '00000600', 'Active',  CURDATE(),  CURDATE(),  CURDATE(), NOW(), 'N', 'Y', 'Y', 'Test Remark', 'N');
Insert into Domain values ('example0006.ie', 'Test Holder 0006', 'Constitutional Body',  'Discretionary Name', '00000600', 'Active',  CURDATE(),  CURDATE(),  CURDATE(), NOW(), 'N', 'Y', 'Y', 'Test Remark', 'N');
Insert into Domain values ('example0007.ie', 'Test Holder 0007', 'Discretionary Applicant',  'Discretionary Name', '00000600', 'Active',  CURDATE(),  CURDATE(),  CURDATE(), NOW(), 'N', 'Y', 'Y', 'Test Remark', 'N');
Insert into Domain values ('example0008.ie', 'Test Holder 0008', 'School/Educational Institution',  'School/Educational Institution Name', '00000600', 'Active',  CURDATE(),  CURDATE(),  CURDATE(), NOW(), 'N', 'Y', 'Y', 'Test Remark', 'N');
Insert into Domain values ('example0009.ie', 'Test Holder 0009', 'Sole Trader',  'Registered Business Name', '00000600', 'Active',  CURDATE(),  CURDATE(),  CURDATE(), NOW(), 'N', 'Y', 'Y', 'Test Remark', 'N');
Insert into Domain values ('example0010.ie', 'Test Holder 0010', 'Discretionary Applicant',  'Discretionary Name', '00000600', 'Active',  CURDATE(),  CURDATE(),  CURDATE(), NOW(), 'N', 'Y', 'Y', 'Test Remark', 'N');

Insert into Contact values ('example0001.ie','ALE085-IEDR', 'A'), ('example0001.ie','ABC1-IEDR', 'B'), ('example0001.ie','ABC1-IEDR', 'C'), ('example0001.ie','ABC1-IEDR', 'T');
Insert into Contact values ('example0002.ie','ALE085-IEDR', 'A'), ('example0002.ie','ABC1-IEDR', 'B'), ('example0002.ie','ABC1-IEDR', 'C'), ('example0002.ie','ABC1-IEDR', 'T');
Insert into Contact values ('example0003.ie','ALE085-IEDR', 'A'), ('example0003.ie','ABC1-IEDR', 'B'), ('example0003.ie','ABC1-IEDR', 'C'), ('example0003.ie','ABC1-IEDR', 'T');
Insert into Contact values ('example0004.ie','ALE085-IEDR', 'A'), ('example0004.ie','ABC1-IEDR', 'B'), ('example0004.ie','ABC1-IEDR', 'C'), ('example0004.ie','ABC1-IEDR', 'T');
Insert into Contact values ('example0005.ie','ALE085-IEDR', 'A'), ('example0005.ie','ABC1-IEDR', 'B'), ('example0005.ie','ABC1-IEDR', 'C'), ('example0005.ie','ABC1-IEDR', 'T');
Insert into Contact values ('example0006.ie','ALE085-IEDR', 'A'), ('example0006.ie','ABC1-IEDR', 'B'), ('example0006.ie','ABC1-IEDR', 'C'), ('example0006.ie','ABC1-IEDR', 'T');
Insert into Contact values ('example0007.ie','ALE085-IEDR', 'A'), ('example0007.ie','ABC1-IEDR', 'B'), ('example0007.ie','ABC1-IEDR', 'C'), ('example0007.ie','ABC1-IEDR', 'T');
Insert into Contact values ('example0008.ie','ALE085-IEDR', 'A'), ('example0008.ie','ABC1-IEDR', 'B'), ('example0008.ie','ABC1-IEDR', 'C'), ('example0008.ie','ABC1-IEDR', 'T');
Insert into Contact values ('example0009.ie','ALE085-IEDR', 'A'), ('example0009.ie','ABC1-IEDR', 'B'), ('example0009.ie','ABC1-IEDR', 'C'), ('example0009.ie','ABC1-IEDR', 'T');
Insert into Contact values ('example0010.ie','ALE085-IEDR', 'A'), ('example0010.ie','ABC1-IEDR', 'B'), ('example0010.ie','ABC1-IEDR', 'C'), ('example0010.ie','ABC1-IEDR', 'T');

Insert into DNS values ('example0001.ie','ns1.example0001.ie','10.10.121.23',  '1'), ('example0001.ie','ns2.example0001.ie','1.3.4.2', '2'), ('example0001.ie','ns3.example0001.ie','10.12.13.14', '3');
Insert into DNS values ('example0002.ie','ns1.abc1.ie','',  '1'), ('example0002.ie','ns2.abc1.ie','', '2'), ('example0002.ie','ns3.abc1.ie','', '3');
Insert into DNS values ('example0003.ie','ns1.abc1.ie','',  '1'), ('example0003.ie','ns2.abc1.ie','', '2'), ('example0003.ie','ns3.abc1.ie','', '3');
Insert into DNS values ('example0004.ie','ns1.abc1.ie','',  '1'), ('example0004.ie','ns2.abc1.ie','', '2'), ('example0004.ie','ns3.abc1.ie','', '3');
Insert into DNS values ('example0005.ie','ns1.abc1.ie','',  '1'), ('example0005.ie','ns2.abc1.ie','', '2'), ('example0005.ie','ns3.abc1.ie','', '3');
Insert into DNS values ('example0006.ie','ns1.abc1.ie','',  '1'), ('example0006.ie','ns2.abc1.ie','', '2'), ('example0006.ie','ns3.abc1.ie','', '3');
Insert into DNS values ('example0007.ie','ns1.abc1.ie','',  '1'), ('example0007.ie','ns2.abc1.ie','', '2'), ('example0007.ie','ns3.abc1.ie','', '3');
Insert into DNS values ('example0008.ie','ns1.abc1.ie','',  '1'), ('example0008.ie','ns2.abc1.ie','', '2'), ('example0008.ie','ns3.abc1.ie','', '3');
Insert into DNS values ('example0009.ie','ns1.abc1.ie','',  '1'), ('example0009.ie','ns2.abc1.ie','', '2'), ('example0009.ie','ns3.abc1.ie','', '3');
Insert into DNS values ('example0010.ie','ns1.abc1.ie','',  '1'), ('example0010.ie','ns2.abc1.ie','', '2'), ('example0010.ie','ns3.abc1.ie','', '3');

Insert into D_Locked values ('example0004.ie', 'NREG', NOW());
Insert into D_Locked values ('example0005.ie', 'NREG', NOW());
Insert into D_Locked values ('example0006.ie', 'NREG', NOW());
Insert into D_Locked values ('example0007.ie', 'NREG', NOW());
Insert into D_Locked values ('example0008.ie', 'NREG', NOW());
Insert into D_Locked values ('example0009.ie', 'NREG', NOW());
Insert into D_Locked values ('example0010.ie', 'NREG', NOW());

Insert into Transfers values ('example0014.ie', concat(date_format(LAST_DAY(now()),'%Y-%m-'),'01'), 'AAE553-IEDR', 'ABC1-IEDR');
Insert into Transfers values ('example0015.ie', concat(date_format(LAST_DAY(now()),'%Y-%m-'),'01'), 'IDL2-IEDR', 'ABC1-IEDR');

Insert into TransfersHist values ('example0128.ie', CURDATE() - Interval 1 Month, 'END1-IEDR', 'ABC1-IEDR');

Insert into RCInvoiceHist values ('INV990982', 'example0128.ie',  CURDATE() - Interval 22 Month,  'ABC1-IEDR', 'Test Holder 0128', 'Xfer', '14.00', 'Y',  CURDATE() + Interval 2 Month, LAST_DAY(CURDATE() - Interval 1 month), 'Console', '1','16.94' );

Insert into InvoiceHist values ('INV990992', 'example0022.ie',  DATE_FORMAT(CURDATE() - Interval 1 Month, '%Y%-%m%-23'),  'ABC1-IEDR', '600', 'Test Holder 0022', 'N', '14.00', 'Y',  DATE_FORMAT(CURDATE() - Interval 1 Month, '%Y%-%m%-23'), 'Y', 'InvoiceRun', LAST_DAY(CURDATE() - Interval 1 month), 'Invoice', '1','N','2.94','INV' );

Insert into DomainHist values ('example0001.ie', 'Test Holder 0001', 'Sole Trader',  'Personal Trading Name', 600, 'Active',  CURDATE(),  CURDATE() - Interval 6 Month,  CURDATE() + Interval 6 Month, NOW(), 'N', 'Y', 'Y', 'Test Remark Hist', 'CIARA-IEDR', CURDATE(), 'N');
Insert into DomainHist values ('example0002.ie', 'Test Holder 0002', 'Sole Trader',  'Registered Business Name', 600, 'Active',  CURDATE(),  CURDATE() - Interval 26 Month,  CURDATE() - Interval 2 Month, NOW(), 'N', 'Y', 'Y', 'Test Remark Hist', 'CIARA-IEDR', CURDATE(), 'N');
Insert into DomainHist values ('example0003.ie', 'Test Holder 0003', 'Constitutional Body',  'State Agency Name', 600, 'Active',  CURDATE(),  CURDATE() - Interval 26 Month,  CURDATE() - Interval 2 Month, NOW(), 'N', 'Y', 'Y', 'Test Remark Hist', 'CIARA-IEDR', CURDATE(), 'N');
Insert into DomainHist values ('example0004.ie', 'Test Holder 0004', 'Unincorporated Association',  'Discretionary Name', 600, 'Active',  CURDATE(),  CURDATE(),  CURDATE(), NOW(), 'N', 'Y', 'Y', 'Test Remark Hist', 'CIARA-IEDR', CURDATE(), 'N');
Insert into DomainHist values ('example0005.ie', 'Test Holder 0005', 'Body Corporate (Ltd,PLC,Company)',  'Discretionary Name', 600, 'Active',  CURDATE(),  CURDATE(),  CURDATE(), NOW(), 'N', 'Y', 'Y', 'Test Remark Hist', 'CIARA-IEDR', CURDATE(), 'N');
Insert into DomainHist values ('example0006.ie', 'Test Holder 0006', 'Constitutional Body',  'Discretionary Name', 600, 'Active',  CURDATE(),  CURDATE(),  CURDATE(), NOW(), 'N', 'Y', 'Y', 'Test Remark Hist', 'CIARA-IEDR', CURDATE(), 'N');
Insert into DomainHist values ('example0007.ie', 'Test Holder 0007', 'Discretionary Applicant',  'Discretionary Name', 600, 'Active',  CURDATE(),  CURDATE(),  CURDATE(), NOW(), 'N', 'Y', 'Y', 'Test Remark Hist', 'CIARA-IEDR', CURDATE(), 'N');
Insert into DomainHist values ('example0008.ie', 'Test Holder 0008', 'School/Educational Institution',  'School/Educational Institution Name', 600, 'Active',  CURDATE(),  CURDATE(),  CURDATE(), NOW(), 'N', 'Y', 'Y', 'Test Remark Hist', 'CIARA-IEDR', CURDATE(), 'N');
Insert into DomainHist values ('example0009.ie', 'Test Holder 0009', 'Sole Trader',  'Registered Business Name', 600, 'Active',  CURDATE(),  CURDATE(),  CURDATE(), NOW(), 'N', 'Y', 'Y', 'Test Remark Hist', 'CIARA-IEDR', CURDATE(), 'N');
Insert into DomainHist values ('example0010.ie', 'Test Holder 0010', 'Discretionary Applicant',  'Publication Name', 600, 'Active',  CURDATE(),  CURDATE(),  CURDATE(), NOW(), 'N', 'Y', 'Y', 'Test Remark Hist', 'CIARA-IEDR', CURDATE(), 'N');

Insert into ReceiptsHist values ('example0811.ie', 'ABC1-IEDR', 'INV990973', NOW() - Interval 111 day, 'ABC1-IEDR-200000000000000000',  '10.10.128.154', '2.94', '14.00', '16.94',  CURDATE() - interval 111 day, 'CONSOLE');
Insert into ReceiptsHist values ('example0908.ie', 'ABC1-IEDR', 'INV990970', NOW() - Interval 68 day, 'ABC1-IEDR-200000000000000000',  '10.10.128.154', '2.94', '14.00', '16.94',  CURDATE() - interval 111 day, 'CONSOLE');

Insert into Transfers VALUES ('iedr.ie', concat(date_format(LAST_DAY(now()),'%Y-%m-'),'01'), 'ABC1-IEDR', 'AAE553-IEDR');
Insert into Transfers VALUES ('google.ie', concat(date_format(LAST_DAY(now()),'%Y-%m-'),'01'), 'ABC1-IEDR', 'AAE553-IEDR');
Insert into Transfers VALUES ('blacknight.ie', concat(date_format(LAST_DAY(now()),'%Y-%m-'),'02'), 'ABC1-IEDR', 'AAE553-IEDR');
