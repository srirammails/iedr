create or replace view nrc_xfers_list AS 
    select Domain.D_Name As Domain, D_Holder, D_Reg_Dt, D_Ren_Dt, Tr_Date As Moved_Dt, Old_Nic_Handle, New_Nic_Handle 
        from Transfers, Domain WHERE Domain.D_Name = Transfers.D_Name 
    union  
    select Domain.D_Name AS Domain, D_Holder, D_Reg_Dt, D_Ren_Dt, Tr_Date AS Moved_Dt, Old_Nic_Handle, New_Nic_Handle 
        from TransfersHist, Domain WHERE Domain.D_Name = TransfersHist.D_Name;
