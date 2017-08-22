create or replace view nrc_pending_payments AS 
	select D_Name, Nic_Handle, Inv_No, Trans_Dt, Sess_ID, VAT, Amount, Batch_Total from PendingPayment
