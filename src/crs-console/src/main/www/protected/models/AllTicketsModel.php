<?php

/**
 * defines AllTicketsModel class
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
 * model for jqGrid view of all pending tickets for an account
 *
 * @category  NRC
 * @package   IEDR_New_Registrars_Console
 * @author    IEDR <asd@iedr.ie>
 * @copyright 2011 IEDR
 * @license   http://www.iedr.ie/ (C) IEDR 2011
 * @version   Release: @package_version@
 * @link      https://statistics.iedr.ie/
 */
class AllTicketsModel extends GridModelBase
{

    /**
     * show tickets changed since 'days' ago
     *
     * @var    integer
     * @access public
     */
    var $days;


    /**
     * default sort column-index for grid data
     *
     * @var    string
     * @access public
     */
    public $defaultSortColumn = 'A';

    /**
     * Column model for jqGrid
     *
     * @var    array
     * @access public
     */
    var $columns = array();

    public function __construct()
    {
        $this->columns = array(
            'A' => array(
                'resultfield' => 'id',
                'criteriafield' => null,
                'label' => 'Ticket',
                'width' => 35,
                'type' => 'int',
            ),
            'B' => array(
                'resultfield' => 'domainName',
                'criteriafield' => 'domainName',
                'label' => 'Domain',
                'width' => 90,
                'type' => 'string',
            ),
            'C' => array(
                'resultfield' => 'domainHolder',
                'criteriafield' => 'domainHolder',
                'label' => 'Holder',
                'width' => 90,
                'type' => 'string',
            ),
            'D' => array(
                'resultfield' => 'adminStatus',
                'criteriafield' => 'adminStatus',
                'label' => 'Admin Status',
                'width' => 60,
                'wildcardpadding' => 'NONE',
                'selectlist' => $this->arrayAsColModelSelectOptions(GridUtility::getAdminStatuses()),
            ),
            'E' => array(
                'resultfield' => 'techStatus',
                'criteriafield' => 'techStatus',
                'label' => 'Tech Status',
                'width' => 30,
                'wildcardpadding' => 'NONE',
                'selectlist' => $this->arrayAsColModelSelectOptions(GridUtility::getTechStatuses()),
            ),
            'F' => array(
                'resultfield' => 'financialStatus',
                'criteriafield' => 'financialStatus',
                'label' => 'Financial Status',
                'width' => 40,
                'wildcardpadding' => 'NONE',
                'selectlist' => $this->arrayAsColModelSelectOptions(GridUtility::getFinancialStatuses()),
            ),
            'G' => array(
                'resultfield' => 'type',
                'criteriafield' => 'type',
                'label' => 'Ticket Type',
                'width' => 30,
                'wildcardpadding' => 'NONE',
                'selectlist' => $this->arrayAsColModelSelectOptions(GridUtility::getTicketTypes()),
            ),
            'H' => array(
                'resultfield' => '?',
                'criteriafield' => null,
                'label' => 'Expiry Date',
                'width' => 40,
                'type' => 'date',
            ),
        );
    }

    /**
     * returns default search criteria object
     *
     * @return CRSTicketAppService_ticketSearchCriteria default search criteria object
     * @access public
     */
    public function getSearchBase()
    {
        // override to set the filter
        $criteria = new CRSTicketAppService_ticketSearchCriteria();
        $criteria->customerStatus = CRSTicketAppService_customerStatusEnum::_NEW;
        $criteria->to = date('Ymd');
        return $criteria;
    }

    /**
     * returns fromatted ticket object data as an array, suitable for base class jqGrid-data handling
     *
     * @param  CRSTicketAppService_ticketVO $t Ticket object
     * @return array  array-format fromatted ticket data
     * @access public
     */
    public function addResults($t)
    {
        $d = new DateTime($t->creationDate);
        $d->add(Yii::app()->params['ticketExpiryDays']);
        $ticket_expiry_date = $d->format('Y-m-d');
        $ticket_holder = '?';
        if (property_exists($t->operation, 'domainHolderField') and property_exists($t->operation->domainHolderField, 'newValue'))
            $ticket_holder = $t->operation->domainHolderField->newValue;
        return array
        (
            'A' => $t->id,
            'B' => $this->createLink(encode($t->domainName), $t->id),
            'C' => encode($ticket_holder),
            'D' => $t->adminStatus,
            'E' => $t->techStatus,
            'F' => $t->financialStatus,
            'G' => $t->type,
            'H' => $ticket_expiry_date,
        );
    }

    private function createLink($domainName, $ticketId) {
        return CHtml::link($domainName, Yii::app()->createUrl("tickets/viewticket", array('id' => $ticketId)));
    }

    /**
     * Short description for function
     *
     * Long description (if any) ...
     *
     * @return mixed  Return description (if any) ...
     * @access public
     */
    public function rules()
    {
        return array(
            array('days', 'numerical'),
            array('days', 'length', 'max' => 3),
        );
    }
}

