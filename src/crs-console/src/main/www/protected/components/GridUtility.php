<?php

/**
 * defines GridUtility class
 *
 * @category  NRC
 * @package   IEDR_New_Registrars_Console
 * @author    IEDR <asd@iedr.ie>
 * @copyright 2011 IEDR
 * @license   http://www.iedr.ie/ (C) IEDR 2011
 * @version   CVS: $Id:$
 * @link      https://statistics.iedr.ie/
 * @see       References to other sections (if any)...
 */

/**
 * Utility class with useful html-generating functions
 *
 * @category  NRC
 * @package   IEDR_New_Registrars_Console
 * @author    IEDR <asd@iedr.ie>
 * @copyright 2011 IEDR
 * @license   http://www.iedr.ie/ (C) IEDR 2011
 * @version   Release: @package_version@
 * @link      https://statistics.iedr.ie/
 */
class GridUtility
{

    /**
     * outputs a form with a selector for selecting year and month (last 2 years and any month)
     *
     * @param  object  $controller     CController instance handling the current request/action
     * @param  string  $text           label text
     * @param  string  $formId         id for form, will be used to name generate form field names
     * @param  CModel  $model          model containing default data
     * @return void
     * @access public
     * @throws Exception thrown if the passed model does not have attributes needed
     * @static
     */
    static public function makeDateSelectUpdateForm($controller, $text, $formId, $model)
    {
        if (isset($model->year) and isset($model->month)) {
            $form = $controller->beginWidget('CActiveForm', array('id' => $formId));
            echo '<div class="row"><h2>' . $text . ' for ' .
                $form->dropDownList($model, 'month', GridUtility::getMonthSelOpts()) . ' ' .
                $form->dropDownList($model, 'year', GridUtility::getYearSelOpts()) . ' ' .
                CHtml::submitButton('Update') .
                '</h2></div>';
            $controller->endWidget();
        } else
            throw new Exception('makeMonthYearDateSelectUpdateForm : model does not have year/month members ; ' . print_r($model, true));
    }

    static public function getDatesForInvoice($controller, $text, $formId, $model, $modelDateField = 'date')
    {
        $form = $controller->beginWidget('CActiveForm', array('id' => $formId));
        echo '<div class="row"><h2>' . $text . ' for ' .
            $form->dropDownList($model, 'date', GridUtility::getMnYrSelOpts(false)) . ' ' .
            CHtml::submitButton('Update') .
            '</h2></div>';
        $controller->endWidget();
    }

    static public function getCardType($paymentRequest)
    {
                return 'CC';
    }

    /**
     * outputs a form with a selector for selecting future months (in form of "Year-Month")
     *
     * @param  object  $controller     CController instance handling the current request/action
     * @param  string  $text           label text
     * @param  string  $formId         id for form, will be used to name generate form field names
     * @param  CModel  $model          model containing default data
     * @param  string  $modelDateField name of model-attribute containing default data
     * @return void
     * @access public
     * @static
     */

    static public function makeCurrenRenewalsOption($controller, $text, $formId, $model, $modelDateField = 'countDays')
    {
        $form = $controller->beginWidget('CActiveForm', array('id' => $formId));
        echo '<div class="row"><h2>' . $text . ' for ' .
            $form->dropDownList($model, 'countDays', array('OVERDUE' => 'Overdue', 'RENEWAL_TODAY' => 'Due for renewal today', 'RENEWAL_THIS_MONTH' => 'Due for renewal this month')) . ' ' .
            CHtml::submitButton('Update') .
            '</h2></div>';
        $controller->endWidget();
    }

    static public function makeMonthYearDateSelectUpdateForm($controller, $text, $formId, $model, $modelDateField = 'date')
    {
        $form = $controller->beginWidget('CActiveForm', array('id' => $formId));
        echo '<div class="row"><h2>' . $text . ' for ' .
            $form->dropDownList($model, 'date', GridUtility::getNextMnYrSelOpts()) . ' ' .
            CHtml::submitButton('Update') .
            '</h2></div>';
        $controller->endWidget();
    }

    static public function makeNextDays($controller, $text, $formId, $model, $modelDateField = 'countDays')
    {
        $form = $controller->beginWidget('CActiveForm', array('id' => $formId));
        echo '<div class="row"><h2>' . $text . ' ' .
            $form->textField($model, 'countDays', array('maxlength' => 3, 'size' => 3)) . ' days ' .
            CHtml::submitButton('Update') .
            '</h2></div>';
        $controller->endWidget();
    }

    static public function getByDomain($controller, $text, $formId, $model, $modelDateField = 'domainName')
    {
        $form = $controller->beginWidget('CActiveForm', array('id' => $formId));
        echo '<div class="row"><h2>' . $text . ' ' .
            $form->textField($model, 'domainName', array('maxlength' => 23, 'size' => 23)) . ' ' .
            CHtml::submitButton('Update') .
            '</h2></div>';
        $controller->endWidget();
    }

    static public function readyForSettlement($controller, $text, $formId, $model, $modelDateField = 'list')
    {
        $form = $controller->beginWidget('CActiveForm', array('id' => $formId));
        $list = array('All', 'Ready for settelment', 'Not ready for settelment');
        echo '<div class="row"><h2>' . $text . ' ' .
            $form->dropDownList($model, 'list', $list) . ' ' .
            CHtml::submitButton('Update') .
            '</h2></div>';
        $controller->endWidget();
    }

    /**
     * returns array of year-month values for select-lists
     *
     * @return array     array of 10 strings like '2011-06' => 'June-2011', for next 10 months
     * @access protected
     * @static
     */
    static protected function getNextMnYrSelOpts($asc = true)
    {
        $firstDate = strtotime(date("Y-m-01"));
        $retval = array();
        $sign = '+';
        if ($asc == false) {
            $firstDate = strtotime('11 months', $firstDate);
            $sign = '-';
        }
        for ($i = 0; $i < 12; $i++) {
            $timePeriod = strtotime($sign . $i . ' months', $firstDate);
            $retval[date('Y-m', $timePeriod)] = date('F', $timePeriod);
        }
        return $retval;
    }

    static protected function getMnYrSelOpts($asc = true)
    {
        // start at previous calendar month
        $currentDate = strtotime(date('Y-m-01'));
        $retval = array();

        $period = '+';
        if ($asc == false) {
            $period = '-';
        }

        for ($i = 0; $i < 10; $i++) {
            $timePeriod = strtotime($period . $i . ' month', $currentDate);
            $retval[date('Y-m', $timePeriod)] = date('F-Y', $timePeriod);
        }

        return $retval;
    }

    /**
     * returns a two-element array of last year and this year as integers
     *
     * @return array     two-element array of last year and this year as integers
     * @access protected
     * @static
     */
    static protected function getYearSelOpts()
    {
        // start at previous year
        $selarr = array();
        $Y = date('Y', strtotime('-1 year'));
        $selarr[$Y] = $Y;
        $Y = date('Y');
        $selarr[$Y] = $Y;
        return $selarr;
    }

    /**
     * returns an array of months for select-lists
     *
     * @return array  array of months, key and value are both month-name strings
     * @access public
     * @static
     */
    static public function getMonthSelOpts()
    {
        // start at Jan
        $arr = array();
        foreach (self::getMonthList() as $i => $m)
            $arr[$m] = $m;
        return $arr;
    }

    /**
     * returns an array of month numbers and names for select-lists
     *
     * @return array  array of (int,1-12)month => (string,eg 'January')month-name
     * @access public
     * @static
     */
    static public function getMonthList()
    {
        $l = array();
        for ($i = 1; $i < 13; $i++)
            $l[$i] = date('F', strtotime('2000-' . $i . '-01'));
        return $l;
    }

    static public function getDomainsWithRenewalMode($model)
    {
        $domains = array();
        $domainWithPeriods = split(',', $model->period);
        for ($i = 0; $i < count($domainWithPeriods); $i++) {
            $pair = split(' ', $domainWithPeriods[$i]);
            Yii::log('pair= ' . print_r($pair, true));
            if (count($pair) == 2) {
                $domains[$pair[0]] = $pair[1];
            }
        }
        Yii::log('added= ' . print_r(count($domains), true));
        return $domains;
    }

    static public function getDomainsWithPeriod($model)
    {
        $prices = get_reg_prices();
        $r_codes = $prices['CODE'];

        $domains = array();
        $domainWithPeriods = split(',', $model->period);
        for ($i = 0; $i < count($domainWithPeriods); $i++) {
            $pair = split(' ', $domainWithPeriods[$i]);
            Yii::log('pair= ' . print_r($pair, true));
            if (count($pair) == 2) {
                $domains[$i] = new CRSPaymentAppService_domainWithPeriodVO();
                $domains[$i]->domainName = $pair[0];
                $periodCode = $pair[1];
                $domains[$i]->periodInYears = $r_codes[$periodCode]->duration;
                Yii::log('domain= ' . print_r($pair[0], true));
                Yii::log('period= ' . print_r($pair[1], true));
            }
        }
        Yii::log('added= ' . print_r(count($domains), true));
        return $domains;
    }

    static public function getAllShortStatuses()
    {
        return array(
            CRSDomainAppService_shortNRPStatus::_Active => 'Active',
            CRSDomainAppService_shortNRPStatus::_InvoluntaryMailed => CRSDomainAppService_shortNRPStatus::_InvoluntaryMailed,
            CRSDomainAppService_shortNRPStatus::_InvoluntarySuspended => CRSDomainAppService_shortNRPStatus::_InvoluntarySuspended,
            CRSDomainAppService_shortNRPStatus::_VoluntaryMailed => CRSDomainAppService_shortNRPStatus::_VoluntaryMailed,
            CRSDomainAppService_shortNRPStatus::_VoluntarySuspended => CRSDomainAppService_shortNRPStatus::_VoluntarySuspended,
            //CRSDomainAppService_shortNRPStatus::_NA => 'N/A',
        );
    }

    static public function getNrpShortStatuses()
    {
        return array(
            CRSDomainAppService_shortNRPStatus::_InvoluntaryMailed => CRSDomainAppService_shortNRPStatus::_InvoluntaryMailed,
            CRSDomainAppService_shortNRPStatus::_InvoluntarySuspended => CRSDomainAppService_shortNRPStatus::_InvoluntarySuspended,
            CRSDomainAppService_shortNRPStatus::_VoluntaryMailed => CRSDomainAppService_shortNRPStatus::_VoluntaryMailed,
            CRSDomainAppService_shortNRPStatus::_VoluntarySuspended => CRSDomainAppService_shortNRPStatus::_VoluntarySuspended,
        );
    }

    static public function getTicketTypes()
    {
        return array(
            CRSTicketAppService_domainOperationType::_REG => 'Registration',
            CRSTicketAppService_domainOperationType::_MOD => 'Modification',
            CRSTicketAppService_domainOperationType::_XFER => 'Transfer',
            CRSTicketAppService_domainOperationType::_DEL => 'Deletion',
        );
    }

    static public function getReservationTypes()
    {
        return array(
            'REGISTRATION' => 'New Reg',
            'TRANSFER' => 'Xfer',
            'RENEWAL' => 'Renewal'
        );
    }

    static public function getRenewalsMode()
    {
        return array(
            'Autorenew' => 'Autorenew',
            'RenewOnce' => 'RenewOnce'
        );
    }

    static public function getAdminStatuses() {
        return array(
            0 => CRSTicketAppService_adminStatusEnum::_NEW,
            1 => CRSTicketAppService_adminStatusEnum::_PASSED,
            2 => CRSTicketAppService_adminStatusEnum::_HOLD_UPDATE,
            3 => CRSTicketAppService_adminStatusEnum::_HOLD_PAPERWORK,
            4 => CRSTicketAppService_adminStatusEnum::_STALLED,
            5 => CRSTicketAppService_adminStatusEnum::_RENEW,
            6 => CRSTicketAppService_adminStatusEnum::_FINANCE_HOLDUP,
            9 => CRSTicketAppService_adminStatusEnum::_CANCELLED,
            10 => CRSTicketAppService_adminStatusEnum::_HOLD_REGISTRAR_APPROVAL,
            11 => CRSTicketAppService_adminStatusEnum::_DOCUMENTS_SUBMITTED,
        );
    }

    static public function getTechStatuses() {
        return array(
            0 => CRSTicketAppService_techStatusEnum::_NEW,
            1 => CRSTicketAppService_techStatusEnum::_PASSED,
            2 => CRSTicketAppService_techStatusEnum::_STALLED,
        );
    }

    static public function getFinancialStatuses() {
        return array(
            CRSTicketAppService_financialStatusEnum::_NEW => CRSTicketAppService_financialStatusEnum::_NEW,
            CRSTicketAppService_financialStatusEnum::_PASSED => CRSTicketAppService_financialStatusEnum::_PASSED,
            CRSTicketAppService_financialStatusEnum::_STALLED => CRSTicketAppService_financialStatusEnum::_STALLED,
        );
    }
}

