create or replace view nrc_rr_payment_hist AS
select Nic_Handle, YEAR(Trans_Dt) AS Year, Month(Trans_Dt) AS Month, D_Name, Inv_No, Trans_Dt, Order_ID AS Trans_ID, VAT, Amount, Batch_Total
from Receipts 
where Inv_No != 'Xfer' 
        and Inv_No != 'Advance' 
UNION 
select Nic_Handle, YEAR(Trans_Dt) AS Year, Month(Trans_Dt) AS Month, D_Name, Inv_No, Trans_Dt, Order_ID AS Trans_ID, VAT, Amount, Batch_Total 
from ReceiptsHist 
where Inv_No != 'Xfer' 
        and Inv_No != 'Advance'
order by Trans_ID, D_Name;
