create or replace view nrc_mngoff_transfers as
Select PendingPayment.D_Name, Old_Nic_Handle, New_Nic_Handle
From Transfers
LEFT JOIN PendingPayment ON PendingPayment.D_Name = Transfers.D_Name AND Transfers.Old_Nic_Handle = Nic_Handle
Where PendingPayment.D_Name != '';

