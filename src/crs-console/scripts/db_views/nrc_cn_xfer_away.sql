create or replace view nrc_cn_xfer_away AS 
    Select I_Bill_Nh, I_Inv_No AS InvoiceNo, D_Name AS Domain, I_DHolder AS Holder, Date_FORMAT(I_Inv_Dt, '%Y-%m-%d') AS InvoiceDate,  Date_FORMAT(I_Ren_Dt, '%Y-%m -%d') AS RenewalDate, 
    Date_FORMAT(I_Reg_Dt, '%Y-%m-%d') AS RegistrationDate, I_Amount AS Amount,  I_Inv_Vat_Amt, I_Amount, Cr_Note_Dt AS XferDate 
    from Cr_Notes , InvoiceHist, NicHandle 
    where D_Name = I_DName 
        and I_Bill_NH = NicHandle.Nic_Handle 
        and Cr_Note_Dt >= LAST_DAY(CURDATE() - INTERVAL 1 MONTH) 
        and Type = 'MOD' 
        and I_INV_DT = LAST_DAY(CURDATE() - INTERVAL 1 MONTH)
    group by D_Name 
    order by Domain;
