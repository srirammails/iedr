DELIMITER $$

DROP PROCEDURE IF EXISTS `phoenixdb`.`sp_create_debug`$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_create_debug`()
BEGIN
	drop table if exists debug;
	create table debug(
		id int AUTO_INCREMENT key, 
		stamp TIMESTAMP default CURRENT_TIMESTAMP, 
		msg varchar(255)
	);
END$$

DELIMITER ;