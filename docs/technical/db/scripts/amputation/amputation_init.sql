use phoenixdb;
-- - PHOENIXDB name is used in sp_debug procedure


-- - create debug procedure - ----------------------

DELIMITER $$

DROP PROCEDURE IF EXISTS `sp_debug`$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_debug`(
	msg varchar(255)
)
BEGIN
	-- check if debug table exists
	if exists(
		select 1 from information_schema.tables 
		where table_schema = "phoenixdb"
		and table_name = "debug")
	then
		insert into debug VALUES(default, default, msg);
	end if;
END$$

DELIMITER ;

-- - create debug table - ----------------------

drop table if exists debug;
create table debug(
	id int AUTO_INCREMENT key, 
	stamp TIMESTAMP default CURRENT_TIMESTAMP, 
	msg varchar(255)
);

-- - create debug counter - ----------------------

DELIMITER $$

DROP PROCEDURE IF EXISTS `sp_debug_count_abc`$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_debug_count_abc`(
	table_name varchar(100),
	column_name varchar(100)
)
begin
		set @magic = "0123456789abcdefghijklmnopqrstuvwxyz";
		set @i = 1;
		set @counter = 0;
		-- -
		while @i<length(@magic) do
			set @letter = substring(@magic, @i, 1);
			-- -
			set @sqltext = concat(
				'select count(*) into @counter from ', table_name, 
				' where lower(', column_name, ') like concat(@letter,\'%\')');
			prepare statement from @sqltext;
			execute statement;# using @table_name, @column_name;
			drop prepare statement;
			-- -
			call sp_debug(concat(@letter, ': ', @counter));
			-- -
			set @i=@i+1;
		end while;
    end$$

DELIMITER ;

-- - create amputation procedures - --------------

DELIMITER $$

DROP PROCEDURE IF EXISTS `sp_amputation_x_letter`$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_amputation_x_letter`(
	table_name varchar(100),
	column_name varchar(100),
	amputation_limit int
)
begin
	set @magic = "0123456789abcdefghijklmnopqrstuvwxyz";
	set @i = 1;
	-- -
	drop table if exists amputation_temp;
	set @sqltext = concat(
		'create table if not exists amputation_temp like ', table_name);
	prepare statement from @sqltext;
	execute statement;
	drop prepare statement;
	-- -
	while @i<length(@magic) do
		set @letter = substring(@magic, @i, 1);
		-- -
		-- perform amputation
		set @sqltext = concat(
			'insert into amputation_temp select * from ', table_name, 
			' where lower(', column_name, ') like ''', @letter, '%'' limit ', amputation_limit);
		prepare statement from @sqltext;
		execute statement;
		drop prepare statement;
		-- -
		set @i=@i+1;
	end while;
	-- -
	set @sqltext = concat(
		'drop table if exists ', table_name);
	prepare statement from @sqltext;
	execute statement;
	drop prepare statement;
 	-- -
 	set @sqltext = concat(
 		'create table if not exists ', table_name, ' select * from amputation_temp');
 	prepare statement from @sqltext;
 	execute statement;
 	drop prepare statement;

	end$$

DELIMITER ;

-- - next - -----------------

DELIMITER $$

DROP PROCEDURE IF EXISTS `sp_amputation_domain_dep`$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_amputation_domain_dep`(
	table_name varchar(100),
	column_name varchar(100)
)
BEGIN
	-- - initial msg
	call sp_debug(concat('start: ', table_name));
	set @sqltext = concat(
		'select \'---> processing: ', table_name, '\' as \' \'');
	prepare statement from @sqltext;
	execute statement;
	drop prepare statement;
	-- -
	-- - amputation begins here
	drop table if exists amputation_temp;
	-- -
	set @sqltext = concat(
		'create temporary table if not exists amputation_temp like ', table_name);
	prepare statement from @sqltext;
	execute statement;
	drop prepare statement;
	-- -
	set @sqltext = concat(
		'insert into amputation_temp select t.* from domain d, ', table_name, ' t where d.d_name = t.', column_name,
		' order by t.', column_name);
	prepare statement from @sqltext;
	execute statement;
	drop prepare statement;
	-- -
	set @sqltext = concat(
		'drop table if exists ', table_name);
	prepare statement from @sqltext;
	execute statement;
	drop prepare statement;
	-- -
	set @sqltext = concat(
		'create table if not exists ', table_name,' select * from amputation_temp');
	prepare statement from @sqltext;
	execute statement;
	drop prepare statement;
	-- -
	drop table if exists amputation_temp;
	-- -
	-- - ending msg
	set @sqltext = concat(
		'select \'---> eof: ', table_name, '\' as \' \'');
	prepare statement from @sqltext;
	execute statement;
	drop prepare statement;
	call sp_debug(concat('end: ', table_name));
END$$

DELIMITER ;

-- - next - -----------------

DELIMITER $$

DROP PROCEDURE IF EXISTS `sp_amputation_ticket_dep`$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_amputation_ticket_dep`(
	table_name varchar(100),
	column_name varchar(100)
)
BEGIN
	-- - initial msg
	call sp_debug(concat('start: ', table_name));
	set @sqltext = concat(
		'select \'---> processing: ', table_name, '\' as \' \'');
	prepare statement from @sqltext;
	execute statement;
	drop prepare statement;
	-- -
	-- - amputation begins here
	drop table if exists amputation_temp;
	-- -
	set @sqltext = concat(
		'create temporary table if not exists amputation_temp like ', table_name);
	prepare statement from @sqltext;
	execute statement;
	drop prepare statement;
	-- -
	set @sqltext = concat(
		'insert into amputation_temp select t.* from ticket d, ', table_name, ' t where d.d_name = t.', column_name,
		' order by t.', column_name);
	prepare statement from @sqltext;
	execute statement;
	drop prepare statement;
	-- -
	set @sqltext = concat(
		'drop table if exists ', table_name);
	prepare statement from @sqltext;
	execute statement;
	drop prepare statement;
	-- -
	set @sqltext = concat(
		'create table if not exists ', table_name,' select * from amputation_temp');
	prepare statement from @sqltext;
	execute statement;
	drop prepare statement;
	-- -
	drop table if exists amputation_temp;
	-- -
	-- - ending msg
	set @sqltext = concat(
		'select \'---> eof: ', table_name, '\' as \' \'');
	prepare statement from @sqltext;
	execute statement;
	drop prepare statement;
	call sp_debug(concat('end: ', table_name));
END$$

DELIMITER ;

-- - next - -----------------

DELIMITER $$

DROP PROCEDURE IF EXISTS `sp_amputation_domain_dep2`$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_amputation_domain_dep2`(
	table_name varchar(100),
	column_name varchar(100)
)
BEGIN
	-- - initial msg
	call sp_debug(concat('start: ', table_name));
	set @sqltext = concat(
		'select \'---> processing: ', table_name, '\' as \' \'');
	prepare statement from @sqltext;
	execute statement;
	drop prepare statement;
	-- -
	-- - amputation begins here
	drop table if exists amputation_temp;
	-- -
	set @sqltext = concat(
		'create temporary table if not exists amputation_temp like ', table_name);
	prepare statement from @sqltext;
	execute statement;
	drop prepare statement;
	-- -
	set @sqltext = concat(
		'insert into amputation_temp select t.* from domain d, ', table_name, ' t where d.', column_name, ' = t.', column_name,
		' group by t.', column_name);
	prepare statement from @sqltext;
	execute statement;
	drop prepare statement;
	-- -
	set @sqltext = concat(
		'drop table if exists ', table_name);
	prepare statement from @sqltext;
	execute statement;
	drop prepare statement;
	-- -
	set @sqltext = concat(
		'create table if not exists ', table_name,' select * from amputation_temp');
	prepare statement from @sqltext;
	execute statement;
	drop prepare statement;
	-- -
	drop table if exists amputation_temp;
	-- -
	-- - ending msg
	set @sqltext = concat(
		'select \'---> eof: ', table_name, '\' as \' \'');
	prepare statement from @sqltext;
	execute statement;
	drop prepare statement;
	call sp_debug(concat('end: ', table_name));
END$$

DELIMITER ;
