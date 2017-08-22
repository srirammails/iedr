create or replace view nrc_rr_inv_hist AS 
    select I_Inv_No, I_Bill_NH, I_Inv_Dt, I_DName AS Domain, I_DHolder AS Holder, I_Reg_Dt AS RegDate, I_Ren_Dt AS RenDate, I_Amount AS Amount, I_Inv_Vat_Amt AS VAT 
    from InvoiceHist; 
