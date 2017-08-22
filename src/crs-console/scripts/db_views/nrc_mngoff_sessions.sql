create or replace view nrc_mngoff_sessions as
SELECT Distinct p.Sess_Id, p.Nic_Handle, a.A_Name, p.Trans_Dt, p.Batch_Total, (Sum(p.VAT) + Sum(p.Amount)) As Outstanding, count(*) as NumDomains
FROM PendingPayment p, Account a
WHERE p.Trans_Dt <= NOW()
AND p.Nic_Handle = a.Billing_NH
Group By p.Nic_Handle, p.Sess_Id;

