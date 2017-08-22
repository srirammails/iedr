ALTER TABLE `Reservation` ADD COLUMN (`Ready_For_Settlement` CHAR(1) NOT NULL DEFAULT 'N' COMMENT 'reservation ready for settlement flag');
