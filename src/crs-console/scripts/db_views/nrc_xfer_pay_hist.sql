create or replace view nrc_xfer_pay_hist AS 
    select Nic_Handle, YEAR(Trans_Dt) AS Year, Month(Trans_Dt) AS Month, D_Name, Inv_No, Trans_Dt, Order_ID AS Trans_ID, VAT, Amount, Batch_Total
    from Receipts 
    where Inv_No = 'Xfer'
    UNION ALL
    select Nic_Handle, YEAR(Trans_Dt) AS Year, Month(Trans_Dt) AS Month, D_Name, Inv_No, Trans_Dt, Order_ID AS Trans_ID, VAT, Amount, Batch_Total 
    from ReceiptsHist, RCInvoiceHist 
    where D_Name = I_DName 
        and Inv_No != 'Xfer' 
    order by Trans_ID, D_Name;
