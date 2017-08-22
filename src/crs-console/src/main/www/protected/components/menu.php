<?php

/**
 * defines menu data and related functions
 * 
 * @category  NRC
 * @package   IEDR_New_Registrars_Console
 * @author    IEDR <asd@iedr.ie>
 * @copyright 2011 IEDR
 * @license   http://www.iedr.ie/ (C) IEDR 2011
 * @version   CVS: $Id:$
 * @link      https://statistics.iedr.ie/
 */

/**
 * Get menu structure (nested arrays)
 * 
 * passes runtime-determined data to getMenuWithParams()
 * 
 * @return array nested array of menu items
 */

   function getMenuByLevel() {
      switch($_SESSION['lastLevel']) {
         case 0:
            //undefined
            return getMenuForQuest();
         case 1:
            //direct
            return getMenuWithParamsForDirect(true, Yii::app()->user->name);
         case 2:
            //registrar
            return getMenuWithParamsForRegistrar(true, Yii::app()->user->name);
         case 3: 
            //superregistrar
            return getMenuWithParams(true, Yii::app()->user->name);
         case 4:
         	// tech or admin contact.
         	return getMenuWithParamsForTAC(true, Yii::app()->user->name);
         default:
            return getMenuForQuest();
      }
   }

   function setLevel($result) {
   	$_SESSION['lastLevel'] = 0; // undefined
   	if($result != null) {
   		switch($result) {
   			case 1: // Direct
   			case 2: // Registrar
   			case 3: // SuperRegistrar
   			case 4: // Tech or Admin Contact
   				$_SESSION['lastLevel'] = $result;
   				break;
   		}
   	}
   	Yii::log('Permission level set '.$_SESSION['lastLevel']);
   }

   function getMenu() {   
     if(isset(Yii::app()->user->authenticatedUser)) {
         if(!array_key_exists('lastLogged', $_SESSION)) {
            $_SESSION['lastLogged'] = '';
         }
         
         if(!array_key_exists('lastLevel' , $_SESSION)) {
            $_SESSION['lastLevel'] = '';  
         }
         
         $user = Yii::app()->user->authenticatedUser;
         if($_SESSION['lastLogged'] != trim($user->username) ) {
            $_SESSION['lastLogged'] = trim($user->username);
            
            $result = null;
            $errors = array();
            
            CRSPermissionsAppService_service::getUserLevel($result, $errors, $user);
            setLevel($result);
         }
         
         if (Yii::app()->user->authenticatedUser->passwordChangeRequired) {
         	return getMenuForPasswordChange();
         } else {
         	return getMenuByLevel();
         }
     } else {
       return getMenuForQuest();
     }
   }
   
   function getMenuForPasswordChange() {
   	$menu_urls = getMenuUrls();
   	return array(
			   	array('label'=>'Change Password', 'url'=>urlFor($menu_urls, '/site/changePassword'), 'visible'=>true,),
   			);
   }
   
   function getMenuForQuest() {
   	$menu_urls = getMenuUrls();
      return array(
          array('label'=>'Home', 'url'=>urlFor($menu_urls, '/site/index')),
          array('label'=>'Login', 'url'=>urlFor($menu_urls, '/site/login'), 'visible'=>true,),
      );
   }

/**
 * Get menu structure (nested arrays)
 * 
 * There are Three kinds of pages:
 * 
 * - Menu pages, (usually 'something/menu') which render a subset of the menu structure using printMenuHeirarchy()
 * 
 * - Menu-Leaf pages
 * 
 * - Non-Menu pages, listed below as Non-visible; are accessible only by navigating from other menu-leaf page(s)
 * 
 * NB Menu Items MUST use unique LABELS (unique among *all* items).
 * Otherwise, the printMenuHeirarchy() fn gets confused.
 * Check Uniqueness: 
 * <shellscript>
 * grep "'label'=>" protected/components/menu.php | tr '\t' ' ' | tr -s ' ' | cut -d, -f 1 | sort | uniq -c
 * </shellscript>
 * 
 * @return array nested array of menu items
 */
   
function urlFor($menu_urls, $url) {
	if (array_key_exists($url, $menu_urls)) {
		return $menu_urls[$url];
	} else {
		return array($url);
	}
}

function getMenuUrls() {
	return array(
		'/domains/menu' => '#',
			'/domainreports/menu' => '#',
			'/account_management/menu'=>'#',
			'/account_deposit_tx/menu'=>'#',
			'/account_deposit_balance/menu'=>'#',
			'/account_pay_to_renew_domains/menu'=>'#',
			'/account_current_nrp_statuses/main'=>'#',
			'/account_credit_card_tx/menu'=>'#',
			'/account_invoice_and_payment_history/menu'=>'#',
			'/account_accounting_reports/menu'=>'#',
			'/contacts/menu'=>'#',
	);
}
   
function getMenuWithParamsForRegistrar($isloggedin,$username)
	{
	$menu_urls = getMenuUrls();
		
	return array(
		array('label'=>'Home', 'url'=>urlFor($menu_urls, '/site/index')),
			// TODO : form : Edit Reseller Defaults ( http://localhost/registrar_console/Console/pageCode/RegisterDefaults.php )
		array('label'=>'Confirm', 'url'=>urlFor($menu_urls, '/grid/confirm'), 'visible'=>false,),
		array('label'=>'Confirm Action', 'url'=>urlFor($menu_urls, '/grid/confirmaction'), 'visible'=>false,),
		array('label'=>'Confirmation', 'url'=>urlFor($menu_urls, '/grid/results'), 'visible'=>false,),


		array('label'=>'Domains', 'url'=>urlFor($menu_urls, '/domains/menu'), 'visible'=>$isloggedin,
			'items'=>array(
				array('label'=>'View Domain', 'url'=>urlFor($menu_urls, '/domains/viewdomain'), 'visible'=>false,),
				array('label'=>'Register New', 'url'=>urlFor($menu_urls, '/domains/regnew'), 'visible'=>$isloggedin,),
            array('label'=>'Request Transfer', 'url'=>urlFor($menu_urls, '/domains/requesttransfer'), 'visible'=>$isloggedin,),
            array('label'=>'Request Transfer Details', 'url'=>urlFor($menu_urls, '/domains/requesttransferdetails'), 'visible'=>false,),
            array('label'=>'Request Transfer Result', 'url'=>urlFor($menu_urls, '/domains/requesttransferresult'), 'visible'=>false,),
				array('label'=>'Domain Details', 'url'=>urlFor($menu_urls, '/domains/domaindetails'), 'visible'=>false,),
				array('label'=>'Ticket Confirmation', 'url'=>urlFor($menu_urls, '/domains/domainscreated'), 'visible'=>false,),
				array('label'=>'Reports', 'url'=>urlFor($menu_urls, '/domainreports/menu'), 'visible'=>$isloggedin,
					'items'=>array(
						array('label'=>'All Domains', 'url'=>urlFor($menu_urls, '/domainreports/alldomains'), 'visible'=>$isloggedin,),
						array('label'=>'Newly Registered Domains', 'url'=>urlFor($menu_urls, '/domainreports/newdomains'), 'visible'=>$isloggedin,),
                  array('label'=>'Confirm Action', 'url'=>urlFor($menu_urls, '/domainreports/confirm_action'), 'visible'=>false,),
						),
					),
				),
			),
       
        array('label'=>'Account Management', 'url'=>urlFor($menu_urls, '/account_management/menu'), 'visible'=>$isloggedin,
			'items'=>array(
            array('label'=>'View Registrar Details', 'url'=>urlFor($menu_urls, '/registrar_details/viewregistrar'), 'visible'=>false,),
            array('label'=>'Deposit Account Tx', 'url'=>urlFor($menu_urls, '/account_deposit_tx/menu'), 'visible'=>$isloggedin, 'items'=>array(
                array('label'=>'Deposit Balance', 'url'=>urlFor($menu_urls, '/account_deposit_balance/menu'), 'visible'=>$isloggedin,'items'=>array(
                     array('label'=>'Deposit Balance', 'url'=>urlFor($menu_urls, '/account_deposit_balance/deposit_balance'), 'visible'=>$isloggedin,),
                     array('label'=>'Top up my deposit account', 'url'=>urlFor($menu_urls, '/account_topup_my_deposit_account/menu'), 'visible'=>$isloggedin,),
                     array('label'=>'View Deposit Top-ups', 'url'=>urlFor($menu_urls, '/account_view_deposit_topups/menu'), 'visible'=>$isloggedin,),
                )),
                 array('label'=>'View Today`s Deposit Reservations', 'url'=>urlFor($menu_urls, '/account_today_deposit_reservations/menu'), 'visible'=>$isloggedin,),
                 array('label'=>'Deposit Statement of Account', 'url'=>urlFor($menu_urls, '/account_deposit_statement_of_account/menu'), 'visible'=>$isloggedin,),
                 array('label'=>'Failed Transactions', 'url'=>urlFor($menu_urls, '/account_failed_transactions/menu'), 'visible'=>$isloggedin,),
            )),
            
            array('label'=>'Pay to Renew Domains', 'url'=>urlFor($menu_urls, '/account_pay_to_renew_domains/menu'), 'visible'=>$isloggedin,
					'items'=>array(
						array('label'=>'Current Renewals', 'url'=>urlFor($menu_urls, '/account_current_renewals/menu'), 'visible'=>$isloggedin,),
                  array('label'=>'Confirm Action', 'url'=>urlFor($menu_urls, '/account_current_renewals/confirm_action'), 'visible'=>false,),
                  array('label'=>'Current Renewals Results', 'url'=>urlFor($menu_urls, '/account_current_renewals/result'), 'visible'=>false,),
						array('label'=>'Future Renewals by month', 'url'=>urlFor($menu_urls, '/account_future_renewals_by_month/menu'), 'visible'=>$isloggedin,),
					),                
				),
             
            array('label'=>'Domain Status (NRP)', 'url'=>urlFor($menu_urls, '/account_current_nrp_statuses/main'), 'visible'=>$isloggedin,
                'items'=>array(
                        array('label'=>'Current NRP Statuses', 'url'=>urlFor($menu_urls, '/account_current_nrp_statuses/menu'), 'visible'=>$isloggedin,),
                        array('label'=>'Confirm Action', 'url'=>urlFor($menu_urls, '/account_current_nrp_statuses/confirm_action'), 'visible'=>false,),
                        array('label'=>'Current NRP Statuses Results', 'url'=>urlFor($menu_urls, '/account_current_nrp_statuses/result'), 'visible'=>false,),
                        array('label'=>'Deleted Domain Report', 'url'=>urlFor($menu_urls, '/account_deleted_domain_report/menu'), 'visible'=>$isloggedin,),
                      ),
                ),
             
            array('label'=>'Credit Card Tx', 'url'=>urlFor($menu_urls, '/account_credit_card_tx/menu'), 'visible'=>$isloggedin,
                'items'=>array(
                        array('label'=>'View Today`s CC Reservations', 'url'=>urlFor($menu_urls, '/account_today_cc_reservations/menu'), 'visible'=>$isloggedin,),
                        array('label'=>'Reauthorise CC Transaction', 'url'=>urlFor($menu_urls, '/account_reauthorise_cc_transaction/menu'), 'visible'=>$isloggedin,
                        'items'=>array(
                              array('label'=>'Reauthorise', 'url'=>urlFor($menu_urls, '/account_reauthorise_cc_transaction/reauthorise'), 'visible'=>false,),
                              array('label'=>'Confirm Action', 'url'=>urlFor($menu_urls, '/account_reauthorise_cc_transaction/confirm_action'), 'visible'=>false,),
                              array('label'=>'Reauthorise Results', 'url'=>urlFor($menu_urls, '/account_reauthorise_cc_transaction/result'), 'visible'=>false,),
                            ),
                        ),
                      ),
                ),
             
            array('label'=>'Invoice & Payment History', 'url'=>urlFor($menu_urls, '/account_invoice_and_payment_history/menu'), 'visible'=>$isloggedin,
                'items'=>array(
                        array('label'=>'View Invoice History', 'url'=>urlFor($menu_urls, '/account_view_invoice_history/menu'), 'visible'=>$isloggedin,),
                        array('label'=>'View Invoice', 'url'=>urlFor($menu_urls, '/account_view_invoice_history/invoiceview'), 'visible'=>false,),
                        array('label'=>'View Payment History', 'url'=>urlFor($menu_urls, '/account_view_payment_history/menu'), 'visible'=>$isloggedin,),                        		
                        array('label'=>'View Payment Details', 'url'=>urlFor($menu_urls, '/account_view_payment_history/paymentview'), 'visible'=>false,),                        				                        
                        array('label'=>'Single Payment Search', 'url'=>urlFor($menu_urls, '/account_single_payment_search/menu'), 'visible'=>$isloggedin,),
                      ),
                ),
            
             array('label'=>'Accounting Reports', 'url'=>urlFor($menu_urls, '/account_accounting_reports/menu'), 'visible'=>$isloggedin,
                'items'=>array(
                        array('label'=>'View Autorenew Domains', 'url'=>urlFor($menu_urls, '/account_view_autorenew_domains/menu'), 'visible'=>$isloggedin,),
                        array('label'=>'View Transfers In', 'url'=>urlFor($menu_urls, '/account_view_transfers_in/menu'), 'visible'=>$isloggedin,),
                        array('label'=>'View Transfers Away', 'url'=>urlFor($menu_urls, '/account_view_transfers_away/menu'), 'visible'=>$isloggedin,),
                        array('label'=>'View Charity Domains', 'url'=>urlFor($menu_urls, '/account_view_charity_domains/menu'), 'visible'=>$isloggedin,),
                        array('label'=>'View Non-Billable Domains', 'url'=>urlFor($menu_urls, '/account_view_non_billable_domains/menu'), 'visible'=>$isloggedin,),
                      ),
                ),
            ),
		),
       
       
       
		array('label'=>'Contacts', 'url'=>urlFor($menu_urls, '/contacts/menu'), 'visible'=>$isloggedin,
			'items'=>array(
				array('label'=>'Find', 'url'=>urlFor($menu_urls, '/nicsearch/namesearch'), 'visible'=>$isloggedin,),
				array('label'=>'Create NIC', 'url'=>urlFor($menu_urls, '/nichandles/createnichandle'), 'visible'=>$isloggedin,),
				array('label'=>'View NIC', 'url'=>urlFor($menu_urls, '/nichandles/viewnichandle'), 'visible'=>false,),
				array('label'=>'Edit NIC', 'url'=>urlFor($menu_urls, '/nichandles/editnichandle'), 'visible'=>false,),
				),
			),
		array('label'=>'Tickets', 'url'=>urlFor($menu_urls, '/tickets/main'), 'visible'=>$isloggedin,
			'items'=>array(
				array('label'=>'View Ticket', 'url'=>urlFor($menu_urls, '/tickets/viewticket'), 'visible'=>false,),
				array('label'=>'Edit Ticket', 'url'=>urlFor($menu_urls, '/tickets/editticket'), 'visible'=>false,),
				),
			),
		array('label'=>'DNS', 'url'=>urlFor($menu_urls, '/nsreports/dnsserversearch'), 'visible'=>$isloggedin,
			'items'=>array(
				array('label'=>'DNS Modification', 'url'=>urlFor($menu_urls, '/dns/dnsupdate'), 'visible'=>false,),
				),
			),
		array('label'=>'Email Disabler', 'url'=>urlFor($menu_urls, '/emailDisabler/menu')),
		//array('label'=>'Mailing List', 'url'=>urlFor($menu_urls, '/mlist/main'), 'visible'=>$isloggedin,),
		array('label'=>'Login', 'url'=>urlFor($menu_urls, '/site/login'), 'visible'=>!$isloggedin,),
		);
	}

function getMenuWithParams($isloggedin,$username)
	{
	$menu_urls = getMenuUrls();
	return array(
		array('label'=>'Home', 'url'=>urlFor($menu_urls, '/site/index')),
			// TODO : form : Edit Reseller Defaults ( http://localhost/registrar_console/Console/pageCode/RegisterDefaults.php )
		array('label'=>'Confirm', 'url'=>urlFor($menu_urls, '/grid/confirm'), 'visible'=>false,),
		array('label'=>'Confirm Action', 'url'=>urlFor($menu_urls, '/grid/confirmaction'), 'visible'=>false,),
		array('label'=>'Confirmation', 'url'=>urlFor($menu_urls, '/grid/results'), 'visible'=>false,),


		array('label'=>'Domains', 'url'=>urlFor($menu_urls, '/domains/menu'), 'visible'=>$isloggedin,
			'items'=>array(
				array('label'=>'View Domain', 'url'=>urlFor($menu_urls, '/domains/viewdomain'), 'visible'=>false,),
				array('label'=>'Register New', 'url'=>urlFor($menu_urls, '/domains/regnew'), 'visible'=>$isloggedin,),
            array('label'=>'Request Transfer', 'url'=>urlFor($menu_urls, '/domains/requesttransfer'), 'visible'=>$isloggedin,),
            array('label'=>'Request Transfer Details', 'url'=>urlFor($menu_urls, '/domains/requesttransferdetails'), 'visible'=>false,),
            array('label'=>'Request Transfer Result', 'url'=>urlFor($menu_urls, '/domains/requesttransferresult'), 'visible'=>false,),
				array('label'=>'Domain Details', 'url'=>urlFor($menu_urls, '/domains/domaindetails'), 'visible'=>false,),
				array('label'=>'Ticket Confirmation', 'url'=>urlFor($menu_urls, '/domains/domainscreated'), 'visible'=>false,),
				array('label'=>'Reports', 'url'=>urlFor($menu_urls, '/domainreports/menu'), 'visible'=>$isloggedin,
					'items'=>array(
						array('label'=>'All Domains', 'url'=>urlFor($menu_urls, '/domainreports/alldomains'), 'visible'=>$isloggedin,),
						array('label'=>'Newly Registered Domains', 'url'=>urlFor($menu_urls, '/domainreports/newdomains'), 'visible'=>$isloggedin,),
                  array('label'=>'Confirm Action', 'url'=>urlFor($menu_urls, '/domainreports/confirm_action'), 'visible'=>false,),
						),
					),
				),
			),
       
       array('label'=>'Account Management', 'url'=>urlFor($menu_urls, '/account_management/menu'), 'visible'=>$isloggedin,
			'items'=>array(
            array('label'=>'View Registrar Details', 'url'=>urlFor($menu_urls, '/registrar_details/viewregistrar'), 'visible'=>false,),
            array('label'=>'Deposit Account Tx', 'url'=>urlFor($menu_urls, '/account_deposit_tx/menu'), 'visible'=>$isloggedin, 'items'=>array(
                array('label'=>'Deposit Balance', 'url'=>urlFor($menu_urls, '/account_deposit_balance/menu'), 'visible'=>$isloggedin,'items'=>array(
                     array('label'=>'Deposit Balance', 'url'=>urlFor($menu_urls, '/account_deposit_balance/deposit_balance'), 'visible'=>$isloggedin,),
                     array('label'=>'Top up my deposit account', 'url'=>urlFor($menu_urls, '/account_topup_my_deposit_account/menu'), 'visible'=>$isloggedin,),
                     array('label'=>'View Deposit Top-ups', 'url'=>urlFor($menu_urls, '/account_view_deposit_topups/menu'), 'visible'=>$isloggedin,),
                )),
                 array('label'=>'View Today`s Deposit Reservations', 'url'=>urlFor($menu_urls, '/account_today_deposit_reservations/menu'), 'visible'=>$isloggedin,),
                 array('label'=>'Deposit Statement of Account', 'url'=>urlFor($menu_urls, '/account_deposit_statement_of_account/menu'), 'visible'=>$isloggedin,),
                 array('label'=>'Failed Transactions', 'url'=>urlFor($menu_urls, '/account_failed_transactions/menu'), 'visible'=>$isloggedin,),
            )),
            
            array('label'=>'Pay to Renew Domains', 'url'=>urlFor($menu_urls, '/account_pay_to_renew_domains/menu'), 'visible'=>$isloggedin,
					'items'=>array(
						array('label'=>'Current Renewals', 'url'=>urlFor($menu_urls, '/account_current_renewals/menu'), 'visible'=>$isloggedin,),
                  array('label'=>'Confirm Action', 'url'=>urlFor($menu_urls, '/account_current_renewals/confirm_action'), 'visible'=>false,),
                  array('label'=>'Current Renewals Results', 'url'=>urlFor($menu_urls, '/account_current_renewals/result'), 'visible'=>false,),
						array('label'=>'Future Renewals by month', 'url'=>urlFor($menu_urls, '/account_future_renewals_by_month/menu'), 'visible'=>$isloggedin,),
					),                
				),
             
            array('label'=>'Domain Status (NRP)', 'url'=>urlFor($menu_urls, '/account_current_nrp_statuses/main'), 'visible'=>$isloggedin,
                'items'=>array(
                        array('label'=>'Current NRP Statuses', 'url'=>urlFor($menu_urls, '/account_current_nrp_statuses/menu'), 'visible'=>$isloggedin,),
                        array('label'=>'Confirm Action', 'url'=>urlFor($menu_urls, '/account_current_nrp_statuses/confirm_action'), 'visible'=>false,),
                        array('label'=>'Current NRP Statuses Results', 'url'=>urlFor($menu_urls, '/account_current_nrp_statuses/result'), 'visible'=>false,),
                        array('label'=>'Deleted Domain Report', 'url'=>urlFor($menu_urls, '/account_deleted_domain_report/menu'), 'visible'=>$isloggedin,),
                      ),
                ),
             
            array('label'=>'Credit Card Tx', 'url'=>urlFor($menu_urls, '/account_credit_card_tx/menu'), 'visible'=>$isloggedin,
                'items'=>array(
                        array('label'=>'View Today`s CC Reservations', 'url'=>urlFor($menu_urls, '/account_today_cc_reservations/menu'), 'visible'=>$isloggedin,),
                        array('label'=>'Reauthorise CC Transaction', 'url'=>urlFor($menu_urls, '/account_reauthorise_cc_transaction/menu'), 'visible'=>$isloggedin,
                        'items'=>array(
                              array('label'=>'Reauthorise', 'url'=>urlFor($menu_urls, '/account_reauthorise_cc_transaction/reauthorise'), 'visible'=>false,),
                              array('label'=>'Confirm Action', 'url'=>urlFor($menu_urls, '/account_reauthorise_cc_transaction/confirm_action'), 'visible'=>false,),
                              array('label'=>'Reauthorise Results', 'url'=>urlFor($menu_urls, '/account_reauthorise_cc_transaction/result'), 'visible'=>false,),
                            ),
                        ),
                      ),
                ),
             
            array('label'=>'Invoice & Payment History', 'url'=>urlFor($menu_urls, '/account_invoice_and_payment_history/menu'), 'visible'=>$isloggedin,
                'items'=>array(
                		array('label'=>'View Invoice History', 'url'=>urlFor($menu_urls, '/account_view_invoice_history/menu'), 'visible'=>$isloggedin,),
                		array('label'=>'View Invoice', 'url'=>urlFor($menu_urls, '/account_view_invoice_history/invoiceview'), 'visible'=>false,),
                        array('label'=>'View Payment History', 'url'=>urlFor($menu_urls, '/account_view_payment_history/menu'), 'visible'=>$isloggedin,),                        		
                        array('label'=>'View Payment Details', 'url'=>urlFor($menu_urls, '/account_view_payment_history/paymentview'), 'visible'=>false,),
						array('label'=>'Single Payment Search', 'url'=>urlFor($menu_urls, '/account_single_payment_search/menu'), 'visible'=>$isloggedin,),
                      ),
                ),
            
             array('label'=>'Accounting Reports', 'url'=>urlFor($menu_urls, '/account_accounting_reports/menu'), 'visible'=>$isloggedin,
                'items'=>array(
                        array('label'=>'View Autorenew Domains', 'url'=>urlFor($menu_urls, '/account_view_autorenew_domains/menu'), 'visible'=>$isloggedin,),
                        array('label'=>'View Transfers In', 'url'=>urlFor($menu_urls, '/account_view_transfers_in/menu'), 'visible'=>$isloggedin,),
                        array('label'=>'View Transfers Away', 'url'=>urlFor($menu_urls, '/account_view_transfers_away/menu'), 'visible'=>$isloggedin,),
                        array('label'=>'View Charity Domains', 'url'=>urlFor($menu_urls, '/account_view_charity_domains/menu'), 'visible'=>$isloggedin,),
                        array('label'=>'View Non-Billable Domains', 'url'=>urlFor($menu_urls, '/account_view_non_billable_domains/menu'), 'visible'=>$isloggedin,),
                      ),
                ),
            //array('label'=>'Statement of Account - aged balance', 'url'=>urlFor($menu_urls, '/accounts_reports/account_aged_balance'), 'visible'=>$isloggedin,),
            ),
		),
		array('label'=>'Contacts', 'url'=>urlFor($menu_urls, '/contacts/menu'), 'visible'=>$isloggedin,
			'items'=>array(
				array('label'=>'Find', 'url'=>urlFor($menu_urls, '/nicsearch/namesearch'), 'visible'=>$isloggedin,),
				array('label'=>'Create NIC', 'url'=>urlFor($menu_urls, '/nichandles/createnichandle'), 'visible'=>$isloggedin,),
				array('label'=>'View NIC', 'url'=>urlFor($menu_urls, '/nichandles/viewnichandle'), 'visible'=>false,),
				array('label'=>'Edit NIC', 'url'=>urlFor($menu_urls, '/nichandles/editnichandle'), 'visible'=>false,),
				),
			),
		array('label'=>'Tickets', 'url'=>urlFor($menu_urls, '/tickets/main'), 'visible'=>$isloggedin,
			'items'=>array(
				array('label'=>'View Ticket', 'url'=>urlFor($menu_urls, '/tickets/viewticket'), 'visible'=>false,),
				array('label'=>'Edit Ticket', 'url'=>urlFor($menu_urls, '/tickets/editticket'), 'visible'=>false,),
				),
			),
		array('label'=>'DNS', 'url'=>urlFor($menu_urls, '/nsreports/dnsserversearch'), 'visible'=>$isloggedin,
			'items'=>array(
				array('label'=>'DNS Modification', 'url'=>urlFor($menu_urls, '/dns/dnsupdate'), 'visible'=>false,),
				),
			),
		//array('label'=>'Mailing List', 'url'=>urlFor($menu_urls, '/mlist/main'), 'visible'=>$isloggedin,),
		array('label'=>'Login', 'url'=>urlFor($menu_urls, '/site/login'), 'visible'=>!$isloggedin,),
		);
	}

	function getMenuWithParamsForTAC($isloggedin,$username)
	{
		$menu_urls = getMenuUrls();
		$var = true;
		return array(
				array('label'=>'Home', 'url'=>urlFor($menu_urls, '/site/index')),
				array('label'=>'Confirm', 'url'=>urlFor($menu_urls, '/grid/confirm'), 'visible'=>false,),
				array('label'=>'Confirm Action', 'url'=>urlFor($menu_urls, '/grid/confirmaction'), 'visible'=>false,),
				array('label'=>'Confirmation', 'url'=>urlFor($menu_urls, '/grid/results'), 'visible'=>false,),
	
	
				array('label'=>'Domains', 'url'=>urlFor($menu_urls, '/domains/menu'), 'visible'=>$isloggedin,
						'items'=>array(
								array('label'=>'View Domain', 'url'=>urlFor($menu_urls, '/domains/viewdomain'), 'visible'=>false,),
								array('label'=>'Domain Details', 'url'=>urlFor($menu_urls, '/domains/domaindetails'), 'visible'=>false,),
								array('label'=>'Ticket Confirmation', 'url'=>urlFor($menu_urls, '/domains/domainscreated'), 'visible'=>false,),
								array('label'=>'Reports', 'url'=>urlFor($menu_urls, '/domainreports/menu'), 'visible'=>$isloggedin,
										'items'=>array(
												array('label'=>'Confirm Action', 'url'=>urlFor($menu_urls, '/domainreports/confirm_action'), 'visible'=>false,),
												array('label'=>'All Domains', 'url'=>urlFor($menu_urls, '/domainreports/alldomains'), 'visible'=>$isloggedin,),
										),
								),
								 
								array('label'=>'Create NIC', 'url'=>urlFor($menu_urls, '/nichandles/createnichandle'), 'visible'=>false,),
								array('label'=>'View NIC', 'url'=>urlFor($menu_urls, '/nichandles/viewnichandle'), 'visible'=>false,),
								array('label'=>'Edit NIC', 'url'=>urlFor($menu_urls, '/nichandles/editnichandle'), 'visible'=>false,),
								 
						),
				),
				array('label'=>'Tickets', 'url'=>urlFor($menu_urls, '/tickets/main'), 'visible'=>$isloggedin,
						'items'=>array(
								array('label'=>'View Ticket', 'url'=>urlFor($menu_urls, '/tickets/viewticket'), 'visible'=>false,),
								array('label'=>'Edit Ticket', 'url'=>urlFor($menu_urls, '/tickets/editticket'), 'visible'=>false,),
						),
				),
				array('label'=>'DNS', 'url'=>urlFor($menu_urls, '/nsreports/dnsserversearch'), 'visible'=>$isloggedin,
						'items'=>array(
								array('label'=>'DNS Modification', 'url'=>urlFor($menu_urls, '/dns/dnsupdate'), 'visible'=>false,),
						),
				),
				array('label'=>'Change Password', 'url'=>urlFor($menu_urls, '/site/changePassword'), 'visible'=>false,),
				array('label'=>'Login', 'url'=>urlFor($menu_urls, '/site/login'), 'visible'=>!$isloggedin,),
		);
	}
	
   
   function getMenuWithParamsForDirect($isloggedin,$username)
	{
		$menu_urls = getMenuUrls();
      $var = true;
	return array(
		array('label'=>'Home', 'url'=>urlFor($menu_urls, '/site/index')),
			// TODO : form : Edit Reseller Defaults ( http://localhost/registrar_console/Console/pageCode/RegisterDefaults.php )
		array('label'=>'Confirm', 'url'=>urlFor($menu_urls, '/grid/confirm'), 'visible'=>false,),
		array('label'=>'Confirm Action', 'url'=>urlFor($menu_urls, '/grid/confirmaction'), 'visible'=>false,),
		array('label'=>'Confirmation', 'url'=>urlFor($menu_urls, '/grid/results'), 'visible'=>false,),


		array('label'=>'Domains', 'url'=>urlFor($menu_urls, '/domains/menu'), 'visible'=>$isloggedin,
			'items'=>array(
				array('label'=>'View Domain', 'url'=>urlFor($menu_urls, '/domains/viewdomain'), 'visible'=>false,),
				array('label'=>'Register New', 'url'=>urlFor($menu_urls, '/domains/regnew'), 'visible'=>$isloggedin,),
            array('label'=>'Request Transfer', 'url'=>urlFor($menu_urls, '/domains/requesttransfer'), 'visible'=>$isloggedin,),
            array('label'=>'Request Transfer Details', 'url'=>urlFor($menu_urls, '/domains/requesttransferdetails'), 'visible'=>false,),
            array('label'=>'Request Transfer Result', 'url'=>urlFor($menu_urls, '/domains/requesttransferresult'), 'visible'=>false,), 
				array('label'=>'Domain Details', 'url'=>urlFor($menu_urls, '/domains/domaindetails'), 'visible'=>false,),
				array('label'=>'Ticket Confirmation', 'url'=>urlFor($menu_urls, '/domains/domainscreated'), 'visible'=>false,),
				array('label'=>'Reports', 'url'=>urlFor($menu_urls, '/domainreports/menu'), 'visible'=>$isloggedin,
					'items'=>array(
                        array('label'=>'Confirm Action', 'url'=>urlFor($menu_urls, '/domainreports/confirm_action'), 'visible'=>false,),
                        array('label'=>'All Domains', 'url'=>urlFor($menu_urls, '/domainreports/alldomains'), 'visible'=>$isloggedin,),
                        array('label'=>'View Charity Domains', 'url'=>urlFor($menu_urls, '/account_view_charity_domains/menu'), 'visible'=>$isloggedin,),
                    ),
                ),
             
				array('label'=>'Find', 'url'=>urlFor($menu_urls, '/nicsearch/namesearch'), 'visible'=>false,),
				array('label'=>'Create NIC', 'url'=>urlFor($menu_urls, '/nichandles/createnichandle'), 'visible'=>false,),
				array('label'=>'View NIC', 'url'=>urlFor($menu_urls, '/nichandles/viewnichandle'), 'visible'=>false,),
				array('label'=>'Edit NIC', 'url'=>urlFor($menu_urls, '/nichandles/editnichandle'), 'visible'=>false,),
             
				),
			), 
		 array('label'=>'Account Management', 'url'=>urlFor($menu_urls, '/account_management/menu'), 'visible'=>$isloggedin,
			'items'=>array(
             
            array('label'=>'Pay to Renew Domains', 'url'=>urlFor($menu_urls, '/account_pay_to_renew_domains/menu'), 'visible'=>$isloggedin,
					'items'=>array(
						array('label'=>'Current Renewals', 'url'=>urlFor($menu_urls, '/account_current_renewals/menu'), 'visible'=>$isloggedin,),
                  array('label'=>'Confirm Action', 'url'=>urlFor($menu_urls, '/account_current_renewals/confirm_action'), 'visible'=>false,),
                  array('label'=>'Current Renewals Results', 'url'=>urlFor($menu_urls, '/account_current_renewals/result'), 'visible'=>false,),
						array('label'=>'Future Renewals by month', 'url'=>urlFor($menu_urls, '/account_future_renewals_by_month/menu'), 'visible'=>$isloggedin,),
					),                
				),
             
				array('label'=>'Domain Status (NRP)', 'url'=>urlFor($menu_urls, '/account_current_nrp_statuses/main'), 'visible'=>$isloggedin,
                'items'=>array(
                        array('label'=>'Current NRP Statuses', 'url'=>urlFor($menu_urls, '/account_current_nrp_statuses/menu'), 'visible'=>$isloggedin,),
                        array('label'=>'Confirm Action', 'url'=>urlFor($menu_urls, '/account_current_nrp_statuses/confirm_action'), 'visible'=>false,),
                        array('label'=>'Current NRP Statuses Results', 'url'=>urlFor($menu_urls, '/account_current_nrp_statuses/result'), 'visible'=>false,),
                      ),
                ),
             
            array('label'=>'Credit Card Tx', 'url'=>urlFor($menu_urls, '/account_credit_card_tx/menu'), 'visible'=>$isloggedin,
                'items'=>array(
                        array('label'=>'View Today`s CC Reservations', 'url'=>urlFor($menu_urls, '/account_today_cc_reservations/menu'), 'visible'=>$isloggedin,),
                        array('label'=>'Reauthorise CC Transaction', 'url'=>urlFor($menu_urls, '/account_reauthorise_cc_transaction/menu'), 'visible'=>$isloggedin,
                        'items'=>array(
                              array('label'=>'Reauthorise', 'url'=>urlFor($menu_urls, '/account_reauthorise_cc_transaction/reauthorise'), 'visible'=>false,),
                              array('label'=>'Confirm Action', 'url'=>urlFor($menu_urls, '/account_reauthorise_cc_transaction/confirm_action'), 'visible'=>false,),
                              array('label'=>'Reauthorise Results', 'url'=>urlFor($menu_urls, '/account_reauthorise_cc_transaction/result'), 'visible'=>false,),
                            ),
                        ),
                      ),
                ),
             
              array('label'=>'Invoice & Payment History', 'url'=>urlFor($menu_urls, '/account_invoice_and_payment_history/menu'), 'visible'=>$isloggedin,
                'items'=>array(
                        array('label'=>'View Invoice History', 'url'=>urlFor($menu_urls, '/account_view_invoice_history/menu'), 'visible'=>$isloggedin,),
                        array('label'=>'View Invoice', 'url'=>urlFor($menu_urls, '/account_view_invoice_history/invoiceview'), 'visible'=>false,),
						array('label'=>'View Payment History', 'url'=>urlFor($menu_urls, '/account_view_payment_history/menu'), 'visible'=>$isloggedin,),                        		
                        array('label'=>'View Payment Details', 'url'=>urlFor($menu_urls, '/account_view_payment_history/paymentview'), 'visible'=>false,),
                        array('label'=>'Single Payment Search', 'url'=>urlFor($menu_urls, '/account_single_payment_search/menu'), 'visible'=>$isloggedin,),
                      ),
                ),
            ),
		),
		array('label'=>'Tickets', 'url'=>urlFor($menu_urls, '/tickets/main'), 'visible'=>$isloggedin,
			'items'=>array(
				array('label'=>'View Ticket', 'url'=>urlFor($menu_urls, '/tickets/viewticket'), 'visible'=>false,),
				array('label'=>'Edit Ticket', 'url'=>urlFor($menu_urls, '/tickets/editticket'), 'visible'=>false,),
				),
			),
		array('label'=>'DNS', 'url'=>urlFor($menu_urls, '/nsreports/dnsserversearch'), 'visible'=>$isloggedin,
			'items'=>array(
				array('label'=>'DNS Modification', 'url'=>urlFor($menu_urls, '/dns/dnsupdate'), 'visible'=>false,),
				),
			),
      array('label'=>'Change Password', 'url'=>urlFor($menu_urls, '/site/changePassword'), 'visible'=>false,),
		array('label'=>'Login', 'url'=>urlFor($menu_urls, '/site/login'), 'visible'=>!$isloggedin,),
		);
	}
   
/**
 * for the given menu label, locate the label in the menu tree,
 * then print a row of '/'-separated links for each parent-or-self menu item,
 * each one being a hyperlink to the relevant menu-page
 * 
 * @param  string $item label of menu item to print breadcrumb trail for
 * @return void   
 */
function printBreadcrumbTrail($item)
	{
	$m = array('items'=>getMenu(),'label'=>'Root');
	checkLabelsAreUnique($m);
	// 1. find the item in the nesting arrays
	$treepath = menuFindLabelRecurse($m,$item);
	if($treepath==null) throw new Exception('The Menu label "'.$item.'" was NOT FOUND : either change the search-string, or update the menu\'s label (case sensitive)');
	// 2. convert to array
	$tpa = explode(',',$treepath);
	// 3. iterate over found nodes and print text+link
	$dc = new DummyController('');
	foreach($tpa as $n => $l)
		{
		if($l != 'X') {
			$m = $m['items'][$l];
			if($n != 0) echo ' / ';
			$tgt = $dc->createUrl($m['url'][0]);
            if($m['label'] == 'Confirm Action') {
               echo $m['label'];
            } else {
               //echo '<a href="'.$tgt.'">'.$m['label'].'</a>';
               echo $m['label'];
            }
			}
		}
	}

/**
 * Recursively locate a label in a menu tree, returning a comma-separated list of parent item labels
 * 
 * @param  array   $m menu array structure
 * @param  string  $l menu label being sought
 * @param  integer $i recursion depth gauge
 * @return string  comma-separated list of parent item labels
 */
function menuFindLabelRecurse($m,$l,$i=0)
	{
	if($m['label']==$l)
		{
		#echo BR.'found';
		return 'X';
		}
	if(isset($m['items']))
		{
		#echo BR.'iterating..';
		foreach($m['items'] as $idx => $ia)
			{
			#echo BR.'trying in '.$idx;
			$chld = menuFindLabelRecurse($ia,$l,$i+1);
			if($chld!=null)
				{
				#echo BR.'found in '.$idx.' in child '.$chld.' at depth '.$i;
				return $idx.','.$chld;
				}
			}
		}
	return null;
	}

/**
 * Recursively print a HTML tree for a menu item; itself and all it's children, each label a hyperlink to the relevant URL
 * 
 * @param  array   $m   menu structure
 * @param  unknown $sel label to start from, or null for all
 * @param  integer $lvl recursion depth gauge
 * @return void   
 */
function printMenuHeirarchy($menu, $pattern=null, $labelvl=0) {
   if (is_array($menu)) {
      $sp = str_repeat('  ', $labelvl);
      print($sp . '<ul>' . BR);
      $dumyController = new DummyController('');

      foreach ($menu as $key => $value) {
         if ($pattern != $value['label']) {
            $url = $dumyController->createUrl($value['url'][0]);
         } else {
            $url = $value['url'][0];
         }

         $label = $value['label'];
         if (($pattern == null or $pattern == $label) && ( isset($value['visible']) && $value['visible'])) {
            if ($pattern != $value['label']) {
               print($sp . '<li><a href="' . $url . '">' . $label . '</a>' . BR);
            } else {
               print($sp . '<li>' . $label . '' . BR);
            }

            if (isset($value['items']) and $value['visible']) {
               printMenuHeirarchy($value['items'], null, $labelvl + 1);
            } else {
               echo ' ...';
            }

            print($sp . '</li>' . BR);
         } else {
            if (isset($value['items']) and $value['visible']) {
               printMenuHeirarchy($value['items'], $pattern, $labelvl + 1);
            }
         }
      }
      print($sp . '</ul>' . BR);
   }
}

/**
 * Recursively counts menu labels; used in checking for duplicate labels
 * 
 * @param  array   $arr array to append labels and counts to
 * @param  array   $m   menu array structure
 * @return void   
 */
function labelFinder(&$arr ,$m)
	{
	if(is_array($m))
		foreach($m as $k => $v)
			{
			if(isset($v['label']))
				{
				$lbl = $v['label'];
				if(!isset($arr[$lbl]))
					$arr[$lbl] = 0;
				$arr[$lbl] += 1;
				}
			if(isset($v['items']))
				labelFinder($arr,$v['items']);
			}
	}

/**
 * check for duplicate menu labels
 * 
 * @param  array   $m   menu array structure
 * @return void   
 */
function checkLabelsAreUnique($m)
	{
	$a = array();
	labelFinder($a, $m);
	$errs = array();
	foreach($a as $k => $v) if($v > 1) $errs[] = $k;
	if(count($errs)>0)
		{
		throw new Exception('ERROR: Menu Labels are Duplicated : "'.implode('","',$errs).'"');
		}
	}

