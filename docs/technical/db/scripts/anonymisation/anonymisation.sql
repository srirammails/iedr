-- script used to anonymize data in phoenixdb
-- please refer to each section to see if there are unsolved issues

-- already anonymized tables
/*
domain
ticket
allticks
tickethist
pendingticket
temptickethist
nichandle
nichandlehist
payment
paymenthist
account
telecom
telecomhist
*/

-- still to anonymize
/*
accounthist
grossrecs
invoicehist
rcinvoicehist
reg_accuracy
statictablename

*/
-- -----------------------------------



use crsdb;

-- set autocommit=0;

-- -----------------------------------
-- change domain holder
-- domain.d_holder
-- problem: many domains have one owner... written differently :(

-- alter table domain
-- add column anonymous_holder tinytext;

-- DONE

-- start transaction;

set @rownum=0;
update domain d, (select @rownum:=@rownum+1 rownum, domain.* from domain) r
set d.anonymous_holder = concat('DHolder', lpad(cast(r.rownum as char), 6, '0'))
where d.d_name = r.d_name;

-- select * from domain;

-- rollback;
-- commit;

-- -----------------------------------
-- change ticket holder
-- ticket.d_holder
-- problem: there is just a few tickets for existing domains (most of them are tickettechstatus=0 (new))

-- DONE

-- start transaction;

update ticket t, domain d
set t.d_holder = d.anonymous_holder
where d.d_name = t.d_name;

set @rownum=0;
update ticket t, (select @rownum:=@rownum+1 rownum, ticket.* from ticket) r
set t.d_holder = concat('NewDHolder', lpad(cast(r.rownum as char), 6, '0'))
where t.d_name = r.d_name and t.d_holder not like 'DHolder%';
-- rollback;
-- commit;

-- -----------------------------------
-- change allticks holder
-- allticks.d_holder
-- problem: there are records with d_holders without connection to domain.d_holder

-- DONE (there are still 2000+ unanonymoised records)

-- start transaction;

update allticks t, domain d
set t.d_holder = d.anonymous_holder
where d.d_name = t.d_name;

-- select * from allticks where d_holder not like 'DHolder%';

-- rollback;
-- commit;

-- -----------------------------------
-- change tickethist holder
-- tickethist.d_holder
-- problem: there is still 175000+ records not matched to domain table

-- DONE

-- start transaction;
-- alter table tickethist
-- add column anonymous_holder tinytext;

update tickethist t, domain d
set t.anonymous_holder = d.anonymous_holder
where d.d_name = t.d_name;

-- select * from tickethist where anonymous_holder is NULL;

-- rollback;
-- commit;

-- -----------------------------------
-- change pendingticket holder
-- pendingticket.d_holder
-- problem: 

-- DONE

-- start transaction;

update pendingticket p, domain d
set p.D_Holder = d.anonymous_holder
where p.D_Name = d.D_Name;
-- rollback;
-- commit;

-- -----------------------------------
-- change tamptickethist holder
-- temptickethist.d_holder
-- problem: as same as in tickethist (still 605 records not matched)

-- DONE

-- start transaction;

-- alter table temptickethist
-- add column anonymous_holder tinytext;

update temptickethist t, domain d
set t.anonymous_holder = d.anonymous_holder
where d.d_name = t.d_name;
-- rollback;
-- commit;

-- -----------------------------------
-- change nichandle address and schema
-- nichandle.nh_address, nichandle.NH_Email
-- problem: NONE

-- DONE

-- start transaction;

set @rownum=0;
update nichandle n, (select @rownum:=@rownum+1 rownum, nichandle.* from nichandle) r
set 
	n.nh_address = concat('NHAddress', lpad(cast(r.rownum as char), 6, '0')),
	n.NH_Email = concat(concat('NHEmail', lpad(cast(r.rownum as char), 6, '0')), '@server.kom')
where n.Nic_Handle = r.nic_handle;
-- rollback;
-- commit;

-- -----------------------------------
-- change nichandlehist address and schema
-- nichandlehist.nh_address, nichandlehist.NH_Email
-- problem: NONE

-- DONE

-- start transaction;

update nichandlehist h, nichandle n
set 
	h.NH_Address = n.NH_Address,
	h.NH_Email = n.NH_Email
where
	h.Nic_Handle = n.Nic_Handle;
-- rollback;
-- commit;

-- -----------------------------------
-- change payment VAT_Reg_ID
-- payment.VAT_Reg_ID
-- problem: NONE

-- DONE

-- start transaction;

set @rownum=0;
update payment p, (select @rownum:=@rownum+1 rownum, nichandle.* from nichandle) r
set p.VAT_Reg_ID = concat('GB', SUBSTRING(r.nh_email,8, 6))
where p.Billing_Contact = r.Nic_Handle;
-- rollback;
-- commit;

-- -----------------------------------
-- change paymenthist VAT_Reg_ID
-- payment.VAT_Reg_ID
-- problem: 24 rows stayed intacted

-- DONE

-- start transaction;
update paymenthist h, payment p
set h.VAT_Reg_ID = p.VAT_Reg_ID
where h.Billing_Contact = p.Billing_Contact;
-- rollback;
-- commit;

-- -----------------------------------
-- change account
-- .address, .web_address, .phone, .fax
-- problem: 1 row (deleted account) not changed

-- DONE

-- start transaction;
set @rownum=0;
update account a, (select @rownum:=@rownum+1 rownum, nichandle.* from nichandle) r
set 
	a.address = concat('ACAddress', lpad(cast(r.rownum as char), 6, '0')),
	a.web_address = concat(concat('www.www', lpad(cast(r.rownum as char), 6, '0')),'.kom'),
	a.phone = concat('+111 ', lpad(cast(r.rownum as char), 6, '0')),
	a.fax = concat('+222 ', lpad(cast(r.rownum as char), 6, '0'))
where a.a_number = r.a_number;
-- commit;
-- rollback;

-- -----------------------------------
-- change telecom
-- .phone
-- problem: NONE

-- DONE

-- start transaction;
set @rn=0;
update telecom t
set t.Phone	= concat('+000 ', lpad(cast((@rn:=@rn+1) as char), 7, '0'));
-- commit;
-- rollback;

-- -----------------------------------
-- change telecomhist
-- .phone
-- problem: NONE

-- DONE

set @rn=0;
-- start transaction;
update telecomhist t
set t.Phone	= concat('+000 ', lpad(cast((@rn:=@rn+1) as char), 7, '0'));
-- commit;
-- rollback;
