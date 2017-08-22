drop table telexomx;

create table telecomx
as
select Nic_Handle, count(*) c from telecom group by Nic_Handle;
-- --------------------

@rn=0;
start transaction;
update
	(select 
		t1.*,
		@rn:=tx.c-1 rn
	from 
		telecom t1, 
		telecomx tx
	where
		t1.Nic_Handle = tx.nic_handle
	) tread,
	telecom twrite,
	telecomx tcount
set
	twrite.phone = tread.rn,
	tcount.c = @rn
where
	twrite.Nic_Handle = tread.nic_handle and 
	tcount.Nic_Handle = tread.nic_handle;


select * from telecomx tx limit 100;

select * from telecomx tx where Nic_Handle='ADQ537-IEDR';
select * from telecomhist where Nic_Handle='AAA001-IEDR';

select tx.c, t1.* from telecomx tx, telecom t1 where tx.Nic_Handle = t1.Nic_Handle and t1.Nic_Handle='ADQ537-IEDR';

set @rn=0;
start transaction;
update telecomhist t
set t.Phone	= concat('+000 ', lpad(cast((@rn:=@rn+1) as char), 7, '0'));


rollback;
commit;