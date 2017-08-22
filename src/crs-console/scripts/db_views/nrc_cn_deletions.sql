create or replace view nrc_cn_deletions AS 
    Select I_Bill_NH, I_Inv_No AS InvoiceNo, D_Name AS Domain, I_DHolder AS Holder, Date_FORMAT(I_Inv_Dt, '%Y-%m-%d') AS InvoiceDate,  
        Date_FORMAT(I_Ren_Dt, '%Y-%m-%d') AS RenewalDate, Date_FORMAT(I_Reg_Dt, '%Y-%m-%d') AS RegistrationDate, I_Amount AS Amount,  I_Inv_Vat_Amt, I_Amount, Cr_Note_Dt 
    from Cr_Notes_Hist, InvoiceHist, NicHandle 
    where D_Name = I_DName 
        and I_Bill_NH = NicHandle.Nic_Handle 
        and Cr_Note_Dt >= CURDATE() - INTERVAL 14 Day 
        and Type = 'MSD' 
        and I_INV_DT >= '2005-01-01' 
        and Cr_Note_Dt IN (select Last_MSD_Run FROM StaticTable) 
    group by D_Name 
    order by Domain;
