CREATE TABLE `Vat` (
  `ID` SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT,
    `Category` CHAR(1) NOT NULL DEFAULT '' COMMENT 'vat category',
      `From_Date` DATE NOT NULL DEFAULT '0000-00-00' COMMENT 'date from which the vat rate applies to the category',
        `Rate` FLOAT UNSIGNED NOT NULL DEFAULT '0.0' COMMENT 'vat rate',
	  PRIMARY KEY(`ID`),
	    UNIQUE KEY `CategoryIndex` (`Category`, `From_Date`)
	    ) ENGINE=InnoDB DEFAULT CHARSET=utf8;

	    /*Table structure for table `Reservation` */

	    CREATE TABLE `Reservation` (
	      `ID` INT UNSIGNED NOT NULL AUTO_INCREMENT,
	        `Nic_Handle` VARCHAR(20) NOT NULL DEFAULT '' COMMENT 'deposit account billing nichandle',
		  `Domain_Name` varchar(66) NOT NULL DEFAULT '' COMMENT 'domain name',
		    `Creation_Date` DATETIME NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT 'reservation date',
		      `Order_Id` VARCHAR(50) NOT NULL DEFAULT '' COMMENT 'operation order id',
		        `Amount` DECIMAL(10,2) NOT NULL DEFAULT '0.00' COMMENT 'reservation amount',
			  `Vat_ID` SMALLINT UNSIGNED NOT NULL COMMENT 'reservation vat id',
			    `Vat_Amount` DECIMAL(10,2) NOT NULL DEFAULT '0.00' COMMENT 'reservation vat amount',
			      `Settled` CHAR(1) NOT NULL DEFAULT 'N' COMMENT 'reservation settlement flag',
			        `Settled_Date` DATETIME NULL DEFAULT NULL COMMENT 'reservation settlement date',
				  `Ticket_ID` INT(10) NULL DEFAULT NULL COMMENT 'ticekt id connected with reservation',
				    `Realex_Authcode` VARCHAR(50) NULL DEFAULT NULL COMMENT 'reservation authcode from realex',
				      `Realex_Passref` VARCHAR(50) NULL DEFAULT NULL COMMENT 'reservation passref from realex',
				        PRIMARY KEY(`ID`),
					  KEY `FK_Vat` (`Vat_ID`),
					    CONSTRAINT `FK_Vat` FOREIGN KEY (`Vat_ID`) REFERENCES `Vat`(`ID`)
					    ) ENGINE=InnoDB DEFAULT CHARSET=utf8;

					    ---
					    --- `Ticket` table modification
					    ---
					    ALTER TABLE `Ticket` DROP COLUMN `Fee`;
					    ALTER TABLE `Ticket` DROP COLUMN `VAT`;
					    ALTER TABLE `Ticket` ADD COLUMN (`Period` TINYINT(2) NULL DEFAULT NULL COMMENT 'domain registration period');
					    ALTER TABLE `Ticket` ADD COLUMN (`CharityCode` VARCHAR(20) NULL DEFAULT NULL COMMENT 'charity code');
					    ---
					    --- `TicketHist` table modification
					    ---
					    ALTER TABLE `TicketHist` DROP COLUMN `Fee`;
					    ALTER TABLE `TicketHist` DROP COLUMN `VAT`;
					    ALTER TABLE `TicketHist` ADD COLUMN (`Period` TINYINT(2) NULL DEFAULT NULL COMMENT 'domain registration period');
					    ALTER TABLE `TicketHist` ADD COLUMN (`CharityCode` VARCHAR(20) NULL DEFAULT NULL COMMENT 'charity code');
					    ---
					    --- DSM tables
					    ---

					    CREATE TABLE `DSM_State` (                                                                                                                                                                                                     
					                 `State` int(11) NOT NULL COMMENT 'DSM State',                                                                                                                                                                                
							              `D_Holder_Type` char(1) COMMENT 'Domain Holder Type (B=Billable, C=Charity, I=IEDR, N=Non-Billable)',                                                                                                                
								                   `Renewal_Mode` char(1) COMMENT 'Renewal mode (N=No auto renew, R=Renew Once, A=Autorenew)',                                                                                                                          
										                `WIPO` char(1) COMMENT 'WIPO dispute flag',                                                                                                                                                                      
												             `Cust_Type` char(1) COMMENT 'Customer type (R=Registrar, D=Direct)',                                                                                                                                                 
													                  `NRP_Status` char(2) COMMENT 'A = Active, IM = Involuntary Mailed, IS = Involuntary Suspended, VM = Voluntary Mailed, VS = Voluntary Suspended, D = Deleted, P = Post-Transaction Audit, T = Transaction Failed.',  
															               `Comment` text COMMENT 'State description',                                                                                                                                                                                  
																                    PRIMARY KEY  (`State`)                                                                                                                                                                                                       
																		               ) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Defines valid DSM states';
																			                  
																					  CREATE TABLE `DSM_Event` (
																					  	`Id` int(11) NOT NULL COMMENT 'ID of the event',
																							`Name` varchar(255) NOT NULL COMMENT 'Name of the event',
																								`Comment` text COMMENT 'Event description',
																									PRIMARY KEY (`Id`),
																										UNIQUE KEY `Dsm_Event_Name` (`Name`) 
																										) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Holds valid names of DSM Events';



																										CREATE TABLE `DSM_Transition` (                                                                                           
																											`Id` int(11) NOT NULL COMMENT 'ID of the transition',                                                                   
																											    `Event_Id` int(11) NOT NULL COMMENT 'ID of the event which causes this transition to be used when changing the state',  
																											        `Begin_State` int(11) NOT NULL COMMENT 'Starting state for the transition',                                             
																												    `End_State` int(11) NOT NULL COMMENT 'End state for th transition',                                                     
																												        `Comment` text COMMENT 'Description for the transition',                                                                
																													    PRIMARY KEY  (`id`),                                                                                                    
																													        UNIQUE KEY `Dsm_Transition_From_State` (`Event_Id`,`Begin_State`),                                                            
																														    KEY `FK_dsm_transition_start_state` (`Begin_State`),                                                                                
																														        KEY `FK_dsm_transition_end_state` (`End_State`),          
																															    KEY `FK_dsm_transition_event` (`Event_Id`),
																															    	CONSTRAINT `FK_dsm_transition_start_state` FOREIGN KEY (`Begin_State`) REFERENCES `DSM_State` (`State`),                            
																																	CONSTRAINT `FK_dsm_transition_end_state` FOREIGN KEY (`End_State`) REFERENCES `DSM_State` (`State`),
																																		CONSTRAINT `FK_dsm_transition_event` FOREIGN KEY (`Event_Id`) REFERENCES `DSM_Event` (`Id`)
																																		) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Defines valid DSM transitions between domain states';

																																		CREATE TABLE `DSM_Action` (                                                                                          
																																		              `Id` int(11) NOT NULL auto_increment COMMENT 'Id of the action',                                                                               
																																			                    `Action_Name` varchar(255) NOT NULL COMMENT 'The name of the action to be fired',                                  
																																					                  `Comment` text COMMENT 'Action description',                                                                       
																																							                PRIMARY KEY  (`Id`),                                                                                                             
																																									              UNIQUE KEY (`Action_Name`)
																																										                  ) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Holds valid names of DSM actions';


																																												  CREATE TABLE `DSM_Transition_Action` (                                                                                          
																																												                `Id` int(11) NOT NULL auto_increment COMMENT 'Artificial primary key',                                                   
																																														              `Transition_Id` int(11) NOT NULL COMMENT 'ID of the transition',                            
																																															                    `Order` int(11) NOT NULL COMMENT 'Defines the order in which the actions will be fired for the given transition',  
																																																	                  `Action_Id` int(11) NOT NULL COMMENT 'The ID of the action to be fired',                                  
																																																			                `Comment` text COMMENT 'Action description',                                                                       
																																																					              PRIMARY KEY  (`Id`),                                                                                               
																																																						                    UNIQUE KEY `Dsm_Transition_Action_NK` (`Transition_Id`,`Order`),
																																																								                  KEY `Dsm_Transition_Action_action` (`Action_Id`),
																																																										                CONSTRAINT `FK_dsm_transition_action_transitionId` FOREIGN KEY (`Transition_Id`) REFERENCES `DSM_Transition` (`Id`),
																																																												              CONSTRAINT `FK_dsm_transition_action_actionId` FOREIGN KEY (`Action_Id`) REFERENCES `DSM_Action` (`Id`)
																																																													                  ) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Specifies which actions are to be run when using a transition.';
																																																															              
																																																																      alter table `Domain` add COLUMN `DSM_State` int(11) NOT NULL default 0;
																																																																      alter table `Domain` add FOREIGN KEY (`DSM_State`) references `DSM_State` (`State`);
																																																																      alter table `DomainHist` add COLUMN `DSM_State` int(11) NOT NULL default 0;
																																																																      alter table `DomainHist` add FOREIGN KEY (`DSM_State`) references `DSM_State` (`State`);
																																																																                  
																																																																		  ---
																																																																		  --- `Account` table modification
																																																																		  ---

																																																																		  ALTER TABLE `Account` ADD COLUMN (`Vat_Category` CHAR(1) NULL DEFAULT NULL COMMENT 'vat category');   
