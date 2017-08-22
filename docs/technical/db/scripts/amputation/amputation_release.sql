use phoenixdb;

-- - script removes all amputation procedures and tables

drop table if exists debug;

drop procedure if exists sp_debug;
drop procedure if exists sp_debug_count_abc;
drop procedure if exists sp_amputation_x_letter;
drop procedure if exists sp_amputation_domain_dep;
drop procedure if exists sp_amputation_domain_dep2;
drop procedure if exists sp_amputation_ticket_dep;
