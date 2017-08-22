create or replace view nrc_xfer_inv_hist AS 
    Select I_Bill_Nh, I_Inv_Dt, I_DName AS Domain, I_DHolder AS Holder, I_Reg_Dt AS RegDate, I_Ren_Dt AS RenDate, I_Amount AS Amount,  
        case when I_Inv_Vat = 1 THEN (I_Amount * (select VatRate FROM StaticTableName))  End AS VAT, 
        I_Batch_Total AS Total, I_Inv_No, Tr_Date  
    from RCInvoiceHist, TransfersHist 
    where I_Type = 'Xfer' 
        and TransfersHist.D_Name = RCInvoiceHist.I_DName;
