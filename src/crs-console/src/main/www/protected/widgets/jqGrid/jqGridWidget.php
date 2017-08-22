<?php

/**
 * defines jqGridWidget class
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
 * class for rendering JqGrid widgets
 * 
 * @category  NRC
 * @package   IEDR_New_Registrars_Console
 * @author    IEDR <asd@iedr.ie>
 * @copyright 2011 IEDR
 * @license   http://www.iedr.ie/ (C) IEDR 2011
 * @version   Release: @package_version@
 * @link      https://statistics.iedr.ie/
 * @see       view.php, jqGridWidget, jqGridComandButtonsWidget, GridModelBase
 */
class jqGridWidget
    extends CWidget
    {

    /**
     * Grid Caption
     *
     * @var    string
     * @access public
     */
    var $caption    = 'grid caption';

    /**
     * URL to navigate to on page error
     *
     * @var    string
     * @access public
     */
    var $thisfile    = '/index.php?r=mycontroller/myaction';

    /**
     * URL which accepts Ajax requests for data, in JSON (paginated), CSV, or Excel format
     *
     * @var    string
     * @access public
     */
    var $datasource    = '/index.php?r=mycontroller/mygriddata';

    var $datatype = 'json';

    var $datastr = '{}';
    /**
     * default number of rows per page
     *
     * @var    integer
     * @access public 
     */
    var $numrows    = 15;

    /**
     * Model instance containing page data
     *
     * @var    unknown
     * @access public 
     */
    var $model    = null;

    /**
     * default grid width in pixels
     *
     * @var    integer
     * @access public 
     */
    var $width    = 1060;

    /**
     * true for provide searching functionality in the grid
     *
     * @var    boolean
     * @access public 
     */
    var $searching    = true;
   var $get_nic_handle = false;
    /**
     * true for provide sorting functionality in the grid
     *
     * @var    boolean
     * @access public 
     */
    var $sorting    = true;

    /**
     * the key, in the model's column-array, of the column which data is by default sorted by
     *
     * @var    string
     * @access public
     */
    var $sortcolumn = 'A';

    /**
     * true if the grid should allow row selections
     *
     * @var    boolean
     * @access public 
     */
    var $selections = false;

    var $tableId = 'thisJqGrid';
    var $pager = true;
    var $navGrid = true;
    var $filterBar = true;
    var $allowAll = false;

    /**
     * widget init (no-op)
     * 
     * @return void  
     * @access public
     */
    public function init() {
        }

    /**
     * performs html rendering
     * 
     * @return void  
     * @access public
     */
    public function run() {
        $this->render('application.widgets.jqGrid.view',
            array(
                'caption' => $this->caption,
                'thisfile' => $this->thisfile,
                'datasource' => $this->datasource,
                'datastr' => $this->datastr,
                'datatype' => $this->datatype,
                'numrows' => $this->numrows,
                'allowAll' => $this->allowAll,
                'tableId' => $this->tableId,
                'navGrid' => $this->navGrid,
                'pager' => $this->pager,
                'filterBar' => $this->filterBar,
                ));
        }
    }

