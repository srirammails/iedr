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
class NicsearchController
    extends GridController
{

    /**
     * Description for public
     * @var    array
     * @access public
     */
    public $breadcrumbs = array('Nicsearch');

    /**
     * Short description for function
     *
     * Long description (if any) ...
     *
     * @return void
     * @access public
     * @see    namesearch.php, NicSearchModel
     */
    public function actionNamesearch()
    {
        Yii::log(' actionNamesearch Request:' . print_r($_REQUEST, true), 'debug', 'NicsearchController::actionNamesearch()');
        if (isset($_GET['from']) && $_GET['from'] == '1') {
            $model = new NicSearchModel($_GET['from']);
        } else {
            $model = new NicSearchModel();
        }
        $this->render('namesearch', array('model' => $model));
    }

    // Non-Action functions


    /**
     * Short description for function
     *
     * Long description (if any) ...
     *
     * @return string    Return description (if any) ...
     * @access protected
     */
    protected function getExportFilenameTag()
    {
        return 'NicHandles_';
    }

    /**
     * Short description for function
     *
     * Long description (if any) ...
     *
     * @param  string    $whereclause Parameter description (if any) ...
     * @return string    Return description (if any) ...
     * @access protected
     */


    /**
     * Short description for function
     *
     * Long description (if any) ...
     *
     * @param  object    $model    Parameter description (if any) ...
     * @param  unknown   $criteria Parameter description (if any) ...
     * @param  mixed     $sort     Parameter description (if any) ...
     * @param  number    $perpage  Parameter description (if any) ...
     * @param  number    $pagenum  Parameter description (if any) ...
     * @return array     Return description (if any) ...
     * @access protected
     */
    protected function getData($model, $criteria, $sort, $perpage, $pagenum)
    {
        // paging
        $pagedata = array('records' => 0, 'page' => $pagenum, 'total' => 0, 'rows' => array());
        $offset = $perpage * ($pagenum - 1);
        $limit = $perpage;

        //$criteria = new CRSNicHandleAppService_nicHandleSearchCriteria();
        $criteria->accountNumber = Yii::app()->user->id;
        if (Utility::isDirect()) {
            $criteria->creator = Yii::app()->user->authenticatedUser->username;
        }
        Yii::log('CIRTERIA IN NICHANDLESERACH= ' . print_r($criteria, true));

        $errs = array();
        CRSNicHandleAppService_service::find($result, $errs, Yii::app()->user->authenticatedUser, $criteria, $offset, $limit, $sort);
        if ($result != null) {
            if (count($errs) == 0) {
                $pagedata['records'] = $result->totalResults;
                ;
                $pagedata['total'] = math_div($result->totalResults, $perpage) + 1;
                if (isset($result->results) && is_array($result->results)) {
                    for ($i = 0; $i < count($result->results); $i++) {
                        $row = $model->addResults($result->results[$i]);
                        if ($row != null)
                            $pagedata['rows'][] = $row;
                    }
                } else if (isset($result->results) && is_object($result->results)) {
                    $row = $model->addResults($result->results);
                    $pagedata['rows'][] = $row;
                }
            } else {
                Yii::log('COUNT ERROR= ' . print_r($errs, true));
            }
        } else {
            Yii::log('RETURN NULL= ' . print_r($errs, true));
        }

        return $pagedata;

    }
}

