Delete from Reservation where Nic_Handle='XNV498-IEDR';
Delete from NicHandle where A_Number=603;
Delete from NicHandleHist where A_Number=603;
Delete from Account where A_Number=603;
Delete from AccountHist where A_Number=603;
Delete from Contact where D_Name in (Select D_Name from Domain where A_Number=603);
Delete from DNS where D_Name in (Select D_Name from Domain where A_Number=603);
Delete from Domain where A_Number=603;
Delete from DomainHist where A_Number=603;
Delete from TicketHist where A_Number=603;
Delete from Ticket where A_Number=603;
Delete from Access where Nic_Handle='XNV498-IEDR';
Delete from AccessHist where Nic_Handle='XNV498-IEDR';
Delete from Telecom where Nic_Handle='XNV498-IEDR';
Delete from Telecom where Nic_Handle='XOE551-IEDR';
Delete from TelecomHist where Nic_Handle='XNV498-IEDR';
Delete from TelecomHist where Nic_Handle='XOE551-IEDR';
Delete from Deposit where Nic_Handle='XNV498-IEDR';
Delete from DepositHist where Nic_Handle='XNV498-IEDR';
Delete from Class_Category_Permission where Nic_handle='XNV498-IEDR';
Delete from CategoryPermission where Nic_handle='XNV498-IEDR';
Update Domain set DSM_State=17 where D_Name in ('love.ie', 'holistictherapy.ie', 'ireland.ie');
Delete from Reseller_Defaults where Nic_handle='XNV498-IEDR';
