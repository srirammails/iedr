SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
DROP TABLE IF EXISTS `DSM_State`;
CREATE TABLE `DSM_State` ( `State` int(11) NOT NULL COMMENT 'DSM State', `Published` char(1) NOT NULL DEFAULT 'N' COMMENT 'Published flag', `D_Holder_Type` char(3) COMMENT 'Domain Holder Type (B=Billable, C=Charity, I=IEDR, N=Non-Billable)', `Renewal_Mode` char(3) COMMENT 'Renewal mode (N=No auto renew, R=Renew Once, A=Autorenew)', `WIPO` char(1) COMMENT 'WIPO dispute flag',  `Cust_Type` char(3) COMMENT 'Customer type (R=Registrar, D=Direct)', `NRP_Status` char(3) COMMENT 'A = Active, IM = Involuntary Mailed, IS = Involuntary Suspended, VM = Voluntary Mailed, VS = Voluntary Suspended, D = Deleted, P = Post-Transaction Audit, T = Transaction Failed,  XPA = Transfer Pending - Active, XPI = Transfer Pending - Inv. NRP, XPV = Transfer Pending - Voluntary NRP', `Comment` text COMMENT 'State description', PRIMARY KEY  (`State`) ) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Defines valid DSM states';
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
ALTER TABLE `Domain` ADD COLUMN (`D_Susp_Dt` timestamp NULL DEFAULT NULL COMMENT 'Domain suspension date');
ALTER TABLE `Domain` ADD COLUMN (`D_Del_Dt` timestamp NULL DEFAULT NULL COMMENT 'Domain deletion date');
ALTER TABLE `DomainHist` ADD COLUMN (`D_Susp_Dt` timestamp NULL DEFAULT NULL COMMENT 'Domain suspension date');
ALTER TABLE `DomainHist` ADD COLUMN (`D_Del_Dt` timestamp NULL DEFAULT NULL COMMENT 'Domain deletion date');
DROP TABLE IF EXISTS `D_Locked`;
DROP TABLE IF EXISTS `D_Locked_Hist`;
