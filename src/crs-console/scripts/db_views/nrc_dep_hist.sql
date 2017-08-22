create or replace view nrc_dep_hist AS 
    select Nic_Handle, Op_Dt AS Trans_Dt, Close_Bal 
    from DepositHist 
union 
    select Nic_Handle, Now() AS Trans_Dt, Close_Bal 
    from Deposit 
order by Trans_Dt;
