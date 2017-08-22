Delete from NicHandle where Nic_handle='XDD274-IEDR';
Delete from NicHandleHist where Nic_Handle='XDD274-IEDR';
Delete from Contact where D_Name like 'direct-account-%';
Delete from DNS where D_Name like 'direct-account-%';
Delete from Domain where D_Name like 'direct-account-%';
Delete from DomainHist where D_Name like 'direct-account-%';
Delete from Ticket where Creator_NH='XDD274-IEDR';
Delete from TicketHist where Creator_NH='XDD274-IEDR';
Delete from Ticket where A_Number=600;
Delete from Access where Nic_Handle='XDD274-IEDR';
Delete from AccessHist where Nic_Handle='XDD274-IEDR';
Delete from Telecom where Nic_Handle='XDD274-IEDR';
Delete from TelecomHist where Nic_Handle='XDD274-IEDR';

Delete from Access where Nic_Handle='XDD275-IEDR';
Delete from AccessHist where Nic_Handle='XDD275-IEDR';
Delete from Telecom where Nic_Handle='XDD275-IEDR';
Delete from NicHandle where Nic_handle='XDD275-IEDR';
Delete from TelecomHist where Nic_Handle='XDD275-IEDR';

