CREATE TABLE `Transaction` (`ID` INT UNSIGNED NOT NULL AUTO_INCREMENT,
`Invoice_Number` INT UNSIGNED NULL DEFAULT NULL COMMENT 'invoice number',
`Settlement_Started` CHAR NOT NULL DEFAULT 'N' COMMENT 'settlement start flag',
`Settlement_Ended` CHAR NOT NULL DEFAULT 'N' COMMENT 'settlement end flag',
`Total_Cost` INT UNSIGNED NULL DEFAULT NULL COMMENT 'total cost',
PRIMARY KEY) ENGINE=InnoDB DEFAULT CHARSET=utf8;

ALTER TABLE `Reservation` ADD COLUMN (`Transaction_ID` INT UNSIGNED NULL DEFAULT NULL COMMENT 'transaction id');
ALTER TABLE `Reservation` ADD CONSTRAINT `FK_Transaction` FOREIGN KEY (`Transaction_ID`) REFERENCES `Transaction` (`ID`);
ALTER TABLE `Reservation` DROP COLUMN `Order_Id`;

ALTER TABLE `Product` ENGINE = InnoDB;
ALTER TABLE `Product` CONVERT TO CHARACTER SET utf8;
ALTER TABLE `Reservation` ADD COLUMN (`Product_Code` VARCHAR NULL DEFAULT NULL COMMENT 'product code');
ALTER TABLE `Reservation` ADD CONSTRAINT `FK_Product` FOREIGN KEY (`Product_Code`) REFERENCES `Product` (`P_Code`);
ALTER TABLE `Reservation` ADD COLUMN (`Operation_Type` varchar(30) NOT NULL DEFAULT '' COMMENT 'operation type');
