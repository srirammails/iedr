create or replace view nrc_hist_pay_search AS 
    select Nic_Handle, D_Name, Inv_No, Trans_Dt, Order_ID AS Trans_ID, VAT, Amount, Batch_Total 
    from Receipts 
union
    select Nic_Handle, D_Name, Inv_No, Trans_Dt, Order_ID AS Trans_ID, VAT, Amount, Batch_Total 
    from ReceiptsHist 
order by Trans_ID, D_Name;
