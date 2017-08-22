-- -
-- - prepare variables - ---------------------------------------
-- -
use phoenixdb;
set @domain_amputation_limit = 25;
-- -
-- - run forest run - ------------------------------------------
-- -

-- call sp_amputation_x_letter('ticket', 'd_name', 5);
call sp_amputation_ticket_dep('domain', 'd_name');

-- call sp_amputation_domain(@domain_amputation_limit);
call sp_amputation_domain_dep('domainhist', 'd_name');
call sp_amputation_domain_dep('ticket', 'd_name');
call sp_amputation_domain_dep('tickethist', 'd_name');
call sp_amputation_domain_dep('contact', 'd_name');
call sp_amputation_domain_dep('contacthist', 'd_name');
call sp_amputation_domain_dep('dns', 'd_name');
call sp_amputation_domain_dep('dnshist', 'd_name');
call sp_amputation_domain_dep('grossrecs', 'd_name');
call sp_amputation_domain_dep('invoicehist', 'i_dname');

-- delete from statsaccesslog where Access_TS < '2007-12-01 00:00:00';
-- delete from statsaccesslog where Access_TS > '2008-02-01 23:59:59' and Access_TS < '2008-07-01 00:00:00';
truncate access;
insert  into `access`(`Nic_Handle`,`NH_Password`,`NH_Level`,`Answer`) values ('IH4-IEDR','69bac2841e83eb64',255,'marysia');
truncate accesshist;
truncate statsaccesslog;

call sp_amputation_domain_dep('snapshot_of_dns', 'd_name');

-- call sp_amputation_domain_dep2('nichandle', 'a_number');
create temporary table if not exists amputation_temp like nichandle;
insert into amputation_temp
	select n.* 
	from 
		nichandle n, ticket t
	where
		n.Nic_Handle = t.Admin_NH1
		or n.Nic_Handle = t.Admin_NH2
		or n.Nic_Handle = t.Tech_NH
		or n.Nic_Handle = t.Bill_NH
		or n.Nic_Handle = t.Creator_NH
	group by n.Nic_Handle;
drop table if exists nichandle;
create table if not exists nichandle select * from amputation_temp;
drop table if exists amputation_temp;
insert into nichandle 
	values("IH4-IEDR", "IEDR Hostmaster", "00000002", "IE Domain Registry Limited", "14 Winsor Terrace Sandycove", "Co. Dublin", "Ireland", "alamakota@serwer.kom", "Active", "2003-06-10", "0000-00-00", "2008-03-26 17:31:50", "Y", "test", "IH4-IEDR");

-- delete from nichandlehist where nh_tstamp < '2007-12-01 00:00:00';
-- call sp_amputation_domain_dep2('nichandlehist', 'a_number');
truncate nichandlehist;

call sp_amputation_domain_dep('receipts', 'd_name');
call sp_amputation_domain_dep('receiptshist', 'd_name');


call sp_amputation_domain_dep('pendingpaymenthist', 'd_name');
call sp_amputation_domain_dep('reg_accuracy', 'd_name');
call sp_amputation_domain_dep('allticks', 'd_name');
call sp_amputation_domain_dep('rcinvoicehist', 'i_dname');
call sp_amputation_domain_dep('d_locked', 'd_name');
call sp_amputation_domain_dep('d_locked_hist', 'd_name');

truncate auth_code;

-- delete from telecom where type='F';
-- delete from telecom where phone not like '+%';
-- delete from telecom where phone not regexp '^.44';
truncate telecom;

-- delete from telecomhist where chng_dt < '2007-12-01 00:00:00';
truncate telecomhist;

call sp_amputation_domain_dep('dnssearch_ratelim', 'd_name');
call sp_amputation_domain_dep('temptickethist', 'd_name');
call sp_amputation_domain_dep('rendates', 'd_name');

call sp_amputation_domain_dep('cr_notes_hist', 'd_name');

truncate guestreceiptshist;
truncate deletelisthist;

truncate realvoid;

call sp_amputation_domain_dep('transfershist', 'd_name');

select * from debug;


