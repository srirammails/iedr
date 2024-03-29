Delete from Domain where A_Number='600';
Delete from DomainHist where A_Number='600';
Delete from Contact  where D_Name like 'example%';
Delete from Contact  where D_Name like 'new-example%';
Delete from DNS where D_Name like 'example%';
Delete from DNS where D_Name like 'new-example%%';
Delete from Receipts where Nic_Handle='ABC1-IEDR';
Delete from RCInvoiceHist where I_Bill_Nh='ABC1-IEDR';
Delete from InvoiceHist where I_Bill_Nh='ABC1-IEDR';
Delete from DeleteList where D_Name like 'example%';
Delete from SuspendList where D_Name like 'example%';
Delete from MailList where D_Name like 'example%';
Delete from Ticket where D_Name like 'example%';
Delete from Ticket where A_Number='600';
Delete from Transfers where D_Name like 'example%';
Delete from Transfers where New_Nic_Handle='ABC1-IEDR';
Delete from Transfers where Old_Nic_Handle='ABC1-IEDR';
Delete from TransfersHist where Old_Nic_Handle='ABC1-IEDR';
Delete from TransfersHist where New_Nic_Handle='ABC1-IEDR';
Delete from PendingPayment where Nic_Handle='ABC1-IEDR';
Delete from TransfersHist where D_Name like 'example%';
Delete from ReceiptsHist where Nic_Handle='ABC1-IEDR';
Delete from Receipts where Nic_Handle='ABC1-IEDR';
Delete from Statement where Nic_Handle='ABC1-IEDR';
Delete from Telecom where Nic_Handle='ABC1-IEDR';
Delete from D_Locked where D_Name like 'new-example%';
Delete from D_Locked where D_Name like 'example%';
Replace INTO NicHandle VALUES ('ABC1-IEDR', 'Test Registrar Nic', '600', 'Registrar co Limited', '1 The Road, Some Street', 'Dublin', 'Ireland', 'ciara@iedr.ie','Active', NOW(), NOW(), NOW(),'',         'test data generation','ABC1-IEDR');
REPLACE into Account VALUES ('600', 'Test Registrar', 'ABC1-IEDR', '1 The Road, Some Street', 'Dublin', 'Ireland', 'www.testregistrar.ie', '012365400', '012365400', 'Active', NOW(), 'TradeAChar'        ,         '0.00', NOW(), NOW(), 'Monthly', 'January', 'test data generation script');
REPLACE into Deposit VALUES( 'ABC1-IEDR', NOW(), '1000.0', '1.0', '201102130918230912-13123');
DROP table IF EXISTS example_data;
