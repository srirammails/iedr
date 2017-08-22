<?php

/**
 * Short description for file
 *
 * Long description (if any) ...
 *
 * PHP version 5
 *
 * New Registration Console (C) IEDR 2011
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
 * Short description for class
 *
 * Long description (if any) ...
 *
 * @category  NRC
 * @package   IEDR_New_Registrars_Console
 * @author    IEDR <asd@iedr.ie>
 * @copyright 2011 IEDR
 * @license   http://www.iedr.ie/ (C) IEDR 2011
 * @version   Release: @package_version@
 * @link      https://statistics.iedr.ie/
 * @see       References to other sections (if any)...
 */
class NsreportsController extends GridController
{

    /**
     * Short description for function
     *
     * Long description (if any) ...
     *
     * @return void
     * @access public
     * @see    dnsserversearch.php, DnsserversearchModel, NsreportsController::actionDnsserversearch()
     */
    public function actionDnsserversearch()
    {
        $model = new DnsserversearchModel();
        Utility::writeActionToSession('nsreports/dnsserversearch');
        $this->render('dnsserversearch', array('model' => $model));
    }

    /**
     * Short description for function
     *
     * Long description (if any) ...
     *
     * @return unknown Return description (if any) ...
     * @access public
     */
    public function actionGetgriddataNrc_ns_report()
    {
        return $this->actionGetgriddata();
    }

    protected function getData($model, $criteria, $sort, $perpage, $pagenum)
    {
        $pagedata = array('records' => 0, 'page' => $pagenum, 'total' => 0, 'rows' => array());
        $offset = $perpage * $pagenum - $perpage;
        $limit = $perpage;
        $result = null;
        $errs = null;
        $user = Yii::app()->user->authenticatedUser;

        CRSDomainAppService_service::getNsReports($result, $errs, $user, $criteria, $offset, $limit, $sort);
        if (count($errs) == 0) {
            Yii::log('TOTAL_RESULTS= ' . print_r($result, true));
            if (isset($result) && $result != null && property_exists($result, 'results')) {
                $count = $result->totalResults;
                $total = 0;
                if ($count > 0) {
                    $total = ceil($count / $perpage);
                }

                if ($pagenum > $total)
                    $pagenum = $total;

                $pagedata['total'] = $total;
                $pagedata['page'] = $pagenum;
                $pagedata['records'] = $count;

                if (is_array($result->results)) {
                    foreach ($result->results as $k => $o)
                        $pagedata['rows'][] = $model->addResults($o);
                } else if (is_object($result->results)) {
                    $pagedata['rows'][] = $model->addResults($result->results);
                }
            }
        } else {
            Yii::log('Some errors ' . print_r($errs, true));
        }
        return $pagedata;
    }

    /**
     * Short description for function
     *
     * Long description (if any) ...
     *
     * @param  unknown    $model    Parameter description (if any) ...
     * @param  unknown    $criteria Parameter description (if any) ...
     * @return string     Return description (if any) ...
     * @access public
     * @throws CException Exception description (if any) ...
     */

    /**
     * Short description for function
     *
     * Long description (if any) ...
     *
     * @return object    Return description (if any) ...
     * @access protected
     */
    protected function getSelectionModel()
    {
        // called from actionConfirmaction()
        return new DnsserversearchModel();
    }

    /**
     * Short description for function
     *
     * Long description (if any) ...
     *
     * @return string    Return description (if any) ...
     * @access protected
     */
    protected function getSelectionModelName()
    {
        // called from actionConfirmaction()
        return 'DnsserversearchModel';
    }
}

