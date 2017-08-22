create or replace view nrc_cn_on_invs_msddels AS 
    select I_Bill_Nh, I_Inv_Dt, I_DName, I_Inv_No, Cr_Note_Dt, C_Nt_Ts 
    from InvoiceHist 
        left join ReceiptsHist on I_DName = ReceiptsHist.D_Name and I_Inv_No = Inv_No 
        left join Cr_Notes_Hist on I_DName = Cr_Notes_Hist.D_Name and Cr_Note_Dt >= I_Inv_Dt and Cr_Note_Dt <= DATE_ADD(I_Inv_Dt, INTERVAL 112 DAY)  
    where ReceiptsHist.D_Name IS NULL 
        and Type = 'MSD';
