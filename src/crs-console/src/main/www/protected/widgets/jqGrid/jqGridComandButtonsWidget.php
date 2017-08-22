<?php

/**
 * defines jqGridComandButtonsWidget class
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
 * Widget which adds command buttons to a jqGrid, so that clicking on one of the buttons then causes the
 * posting of all the selected row keys to the specified action-url along with additional 'command' arguments.
 * 
 * Example Usage:
 * 
 * $this->widget('application.widgets.jqGrid.jqGridComandButtonsWidget', array(
 * 	'pk_column_index'	=> 1,
 * 	'actionurl'		=> $this->createUrl('mycontroller/transmogrify'),
 * 	'returnurl'		=> $this->createUrl('mycontroller/list_widgets'),
 * 	'commandlist'		=> array(
 * 					'transmog' => 'Transmogrify %n Widgets',
 * 					),
 * 	));
 * 
 * @category  NRC
 * @package   IEDR_New_Registrars_Console
 * @author    IEDR <asd@iedr.ie>
 * @copyright 2011 IEDR
 * @license   http://www.iedr.ie/ (C) IEDR 2011
 * @version   Release: @package_version@
 * @link      https://statistics.iedr.ie/
 * @see       commandButtons.php, jqGridComandButtonsWidget, view.php, jqGridWidget, GridModelBase
 */
class jqGridComandButtonsWidget
	extends CWidget
	{

    /**
     * URL to which the command-button form data is posted
     * 
     * @var    string
     * @access public 
     */
	var $actionurl;

    /**
     * URL of page on which the commandsbuttons are rendered
     * 
     * @var    string
     * @access public 
     */
	var $returnurl;

    /**
     * variable used by backends to select data sub-type
     * 
     * @var    string
     * @access public 
     */

    /**
     * array of command-codes mapping to Command-Button-Labels, where "%n" in the label is replace with the number of selected rows that correspond to that command
     * 
     * @var    array
     * @access public 
     */
	var $commandlist;

    /**
     * an label for this command-button form, required when there are multiple command-button forms rendered in the same view, to facilitate multiple jqGridComandButtonsWidget instances in the same page. defaults to 'gridaction'.
     * 
     * @var    string
     * @access public
     */
	var $formid;


    /**
     * Zero-based column index of column which contains a value used to uniquely indicate a row; used as cellID for jqGrid fn "getCell(rowID, cellID)".
     * 
     * @var    unknown
     * @access public 
     */
	var $pk_column_index;


    /**
     * init function (no-op)
     * 
     * @return void  
     * @access public
     */
	public function init() {
		}

    /**
     * renders the Command-button html form in a view
     * 
     * @return void  
     * @access public
     */
	public function run() {
		if($this->formid == null) $this->formid = 'gridaction';
		$this->render('application.widgets.jqGrid.commandButtons');
		}
	}

