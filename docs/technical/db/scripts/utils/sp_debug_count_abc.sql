DELIMITER $$

DROP PROCEDURE IF EXISTS `phoenixdb`.`sp_debug_count_abc`$$

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