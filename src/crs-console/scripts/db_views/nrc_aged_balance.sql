create or replace view nrc_aged_balance AS 
    select Nic_Handle, Case When Tr_Date = '0000-00-00' Then 'Totals' Else Tr_Date End AS Tr_Date, 
        Tr_Type, Ext_Ref, Int_Ref, Future_Bal, Current_Bal,  Age_1_Bal, Age_2_Bal, Age_3_Bal, 
        Case When Total_Bal = '0.00' Then '' Else Total_Bal End AS Total_Bal 
    from `Statement`  Limit 1,1000;
