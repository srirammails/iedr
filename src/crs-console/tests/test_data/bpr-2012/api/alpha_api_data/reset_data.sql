Delete from DNS where DNS_Name='ns1.abc1.ie';
Delete from DNS where D_Name='domain0001.ie';
Delete from DNS where DNS_Name='ns2.abc1.ie';
Delete from DNS where DNS_Name='ns3.abc1.ie';
Delete from Contact where Contact_NH='SOE626-IEDR';
Delete from Contact where Contact_NH='SOE550-iedr';
Delete from Contact where Contact_NH='abc1-iedr';
Delete from NicHandle where Nic_Handle='SOE550-iedr';
Delete from NicHandleHist where Nic_Handle='SOE550-iedr';
Delete from Reseller_Defaults where Nic_Handle='ABC1-IEDR';
Delete from Account where Billing_NH='ABC1-IEDR';
Delete from Domain where A_Number='600';
Delete from DomainHist where A_Number='600';
Delete from Statement where Nic_Handle='ABC1-IEDR';
Delete from Access where Nic_handle='ABC1-IEDR';

Delete from NicHandle where Nic_Handle='RDP1-iedr';
Delete from NicHandleHist where Nic_Handle='RDP1-iedr';
Delete from NicHandle where Nic_Handle='RAI452-iedr';
Delete from NicHandleHist where Nic_Handle='RAI452-iedr';
Delete from Reseller_Defaults where Nic_Handle='RDP1-IEDR';
Delete from Account where Billing_NH='RDP1-IEDR';
Delete from Domain where A_Number='678';


Delete from NicHandle where A_Number='600' or A_Number='603' or A_Number='605';
