<?php

/**
 * defines Accounts_renewpay_advpayController class
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
 * controller for Advance Payments
 * 
 * @category  NRC
 * @package   IEDR_New_Registrars_Console
 * @author    IEDR <asd@iedr.ie>
 * @copyright 2011 IEDR
 * @license   http://www.iedr.ie/ (C) IEDR 2011
 * @version   Release: @package_version@
 * @link      https://statistics.iedr.ie/
 */
class Account_future_renewals_by_monthController extends AccountsGridController {

    /**
     * renders confirmation page for transfer pay-in-advance
     * 
     * @return void  
     * @access public
     */
	public function actionConfirm_txpayinadvance()
		{
		// grid command buttons POST to here 1) command name 2) selected domain list
		$this->setType_TransfersPIA();
		$this->showConfirmPage();
		}

    /**
     * sets internal parameters for future-renewals
     * 
     * - exportFileName, set to 'Accounts_RenewAndPay_AdvancePayment_CurrentRnR_'
     * 
     * @return void  
     * @access public
     */
	public function setType_CurrRnR() {
		$this->exportFileName = 'Account_future_renewals_by_monthController_';
		}

    /**
     * renders jqGrid page for advance-payment of current registrations and renewals
     * 
     * @return void  
     * @access public
     * @see    current_newandrenewals.php, Acc_RnP_AdvPay_RnR_Model, Accounts_renewpay_advpayController::actionGetgriddatacurrrnr()
     */
	public function actionMenu()
		{
		// action to display grid
		$this->setType_CurrRnR();
		$model = new AccountFutureRenewalByMonthModel($this);
		if(isset($_REQUEST['AccountFutureRenewalByMonthModel']['date'])){
			$model->date = $_REQUEST['AccountFutureRenewalByMonthModel']['date'];
		}
        Utility::writeActionToSession('account_future_renewals_by_month/menu');
		$this->render('menu',array('model'=>$model));
		}

    /**
     * outputs grid data for advance-payment of current registrations and renewals
     * 
     * @return unknown unknown
     * @access public 
     * @see    current_newandrenewals.php, Acc_RnP_AdvPay_RnR_Model, Accounts_renewpay_advpayController::actionCurrent_newandrenewals()
     */
	public function actionGetgriddatacurrrnr()
		{
		// jqGrid calls this action to fill grid
		$this->setType_CurrRnR();
		return $this->actionGetgriddata();
		}

    /**
     * renders confirmation page for advance payment of current registrations and renewals
     * 
     * @return void  
     * @access public
     */
	public function actionConfirm_currnr()
		{
		// grid command buttons POST to here 1) command name 2) selected domain list
		$this->setType_CurrRnR();
		$this->showConfirmPage();
		}
	}

