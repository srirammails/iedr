create or replace view nrc_ns_report AS
   select Billing_NH, Domain.D_Name, D_Holder, D_Ren_Dt, D_Reg_Dt, D_Status, DNS_Name, DNS_Order, DNS_IpAddr 
   from DNS, Domain, Account
   where Domain.D_Name = DNS.D_Name 
      and Domain.A_Number = Account.A_Number  
