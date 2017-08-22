create or replace view nrc_cn_on_invs_xfer_away_pp AS 
    select I_Bill_Nh, I_Inv_Dt, I_DName, I_Inv_No, Tr_Date, Type, C_Nt_Ts 
    from InvoiceHist 
        left join TransfersHist ON I_DName = TransfersHist.D_Name 
        left join Cr_Notes_Hist ON I_DName = Cr_Notes_Hist.D_Name and Tr_Date = Cr_Note_Dt 
    where Old_Nic_Handle = I_Bill_NH 
        and Tr_Date >= I_Inv_Dt 
        and Tr_Date <= DATE_ADD(I_Inv_Dt, INTERVAL 112 DAY) 
        and Type != 'MM' 
        and Type IS NOT NULL;
