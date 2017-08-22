create or replace view nrc_cn_on_invs_msd_xfer AS 
    select Old_Nic_Handle, I_DName, I_Inv_Dt, I_Inv_No, Tr_Date, Type, C_Nt_Ts, Cr_Note_Dt 
    from Cr_Notes_Hist, TransfersHist, InvoiceHist 
    where Cr_Notes_Hist.D_Name = TransfersHist.D_Name 
        and Cr_Note_Dt = Tr_Date 
        and Type = 'MM' 
        and I_DName = Cr_Notes_Hist.D_Name;
