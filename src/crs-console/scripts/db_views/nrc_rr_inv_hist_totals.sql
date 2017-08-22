create or replace view nrc_rr_inv_hist_totals AS 
select I_Bill_Nh, Year(I_Inv_Dt) AS Year, Month(I_Ren_Dt) AS Month,  I_Inv_No AS InvoiceNo, Sum(I_Amount) AS Amount, Sum(I_Inv_Vat_Amt) AS VAT, (Sum(I_Amount) + Sum(I_Inv_Vat_Amt)) AS Total  
from InvoiceHist 
where I_Acc_No  > 1 
group by I_Bill_Nh, InvoiceNo, Year(I_Ren_Dt), Month(I_Ren_Dt);
