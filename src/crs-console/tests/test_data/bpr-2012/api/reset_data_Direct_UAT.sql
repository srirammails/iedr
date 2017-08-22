Delete from NicHandle where Nic_handle='UAT274-IEDR';
Delete from NicHandleHist where Nic_Handle='UAT274-IEDR';
Delete from Contact where D_Name like 'my-domain-%';
Delete from DNS where D_Name like 'my-domain-%';
Delete from Domain where D_Name like 'my-domain-%';
Delete from DomainHist where D_Name like 'my-domain-%';
Delete from Ticket where Creator_NH='UAT274-IEDR';
Delete from TicketHist where Creator_NH='UAT274-IEDR';
Delete from Ticket where A_Number=600;
Delete from Access where Nic_Handle='UAT274-IEDR';
Delete from AccessHist where Nic_Handle='UAT274-IEDR';
Delete from Telecom where Nic_Handle='UAT274-IEDR';
Delete from TelecomHist where Nic_Handle='UAT274-IEDR';

Delete from Access where Nic_Handle='UAT275-IEDR';
Delete from AccessHist where Nic_Handle='UAT275-IEDR';
Delete from Telecom where Nic_Handle='UAT275-IEDR';
Delete from NicHandle where Nic_handle='UAT275-IEDR';
Delete from TelecomHist where Nic_Handle='UAT275-IEDR';

