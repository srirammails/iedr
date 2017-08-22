create or replace view nrc_adv_pay_inv_hist AS 
    select I_Bill_Nh, I_DName AS Domain, I_DHolder AS Holder, I_Reg_Dt AS RegDate, I_Ren_Dt AS RenDate, I_Amount AS Amount,  
        CASE WHEN I_Inv_Vat = 1 Then (I_Amount * (SELECT VatRate FROM StaticTableName)) End AS VAT, 
        I_Batch_Total AS Total, I_Inv_No, I_Inv_Dt  
    from RCInvoiceHist 
    where I_Type = 'Advance';
