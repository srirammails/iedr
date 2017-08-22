DELIMITER $$

DROP PROCEDURE IF EXISTS `phoenixdb`.`sp_debug`$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_debug`(
	msg varchar(255)
)
BEGIN
	-- check if debug table exists
	if 
		exists(select 1 from information_schema.tables 
		where table_schema = "phoenixdb"
		and table_name = "debug")
	then
		insert into debug VALUES(default, default, msg);
	end if;
END$$

DELIMITER ;