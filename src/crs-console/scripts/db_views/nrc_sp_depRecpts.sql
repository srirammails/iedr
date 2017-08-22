-- Name:	depositRecpts
-- Parameters: 	nic = nichandle to search DepositHist for their set of deposit transactions.
--             	fromDate = date to search from
--             	toDate = date to search to
--		the to and from dates are inclusive.
-- example : 	call depositRecpts('IDL2-IEDR', '2011-02-01 09:38:48', '2011-02-18 09:26:27');


DELIMITER //

DROP PROCEDURE IF EXISTS `depositRecpts`; 

CREATE PROCEDURE depositRecpts(nic varchar(20), fromDate datetime, toDate datetime )
BEGIN
	set @a=1; set @b=1; 
	select dh1.rownum1, dh2.rownum2,  dh1.Nic_Handle, dh1.Op_Dt, dh1.Open_Bal, dh1.Close_Bal, dh2.Open_Bal, dh2.Close_Bal, dh1.Close_Bal - dh2.Close_Bal AS price, dh2.Order_ID 
	from (
		select  if(@a, @a:=@a+1, @a:=1)-1 as rownum1, Nic_Handle, Op_Dt, Trans_Dt, Open_Bal, Close_Bal, Order_ID 
			from DepositHist 
			WHERE Nic_Handle = nic
		UNION
                select if(@a, @a:=@a+1, @a:=1)-1 as rownum1, Nic_Handle, Now() AS Op_Dt, Trans_Dt, Open_Bal, Close_Bal, Order_ID
                        from Deposit
                        where Nic_Handle = nic) AS dh1  
	INNER JOIN (
		select  if(@b, @b:=@b+1, @b:=1)-1 as rownum2, Nic_Handle, Op_Dt, Trans_Dt, Open_Bal, Close_Bal, Order_ID 
			from DepositHist 
			WHERE Nic_Handle = nic
		UNION
                select if(@b, @b:=@b+1, @b:=1)-1 as rownum1, Nic_Handle, Now() AS Op_Dt, Trans_Dt, Open_Bal, Close_Bal, Order_ID
                	from Deposit
                	where Nic_Handle = nic) AS dh2 
	ON dh1.Nic_Handle = dh2.Nic_Handle 
	where dh2.rownum2 = dh1.rownum1 + 1 
		AND dh1.Nic_Handle = nic
		AND dh1.Op_Dt BETWEEN fromDate AND toDate;

END //
DELIMITER ;
