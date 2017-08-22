--
-- Schedule_Id column has been added 
--

ALTER TABLE `SchedulerConfig`
 ADD COLUMN (`schedule_id` VARCHAR(60) NULL DEFAULT NULL 
 COMMENT 'internal scheduler id');

--
-- Version field has been extended to support Git hash 
--
ALTER TABLE `Invoice`
 CHANGE `Crs_Version` `Crs_Version` varchar(50) NOT NULL COMMENT 'Version tag of the software which produced this invoice (git commit hash)';

