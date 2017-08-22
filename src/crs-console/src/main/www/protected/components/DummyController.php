<?php
/**
 * file which defines DummyController class
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
 * Base class for controllers who only display menus
 * 
 * @category  NRC
 * @package   IEDR_New_Registrars_Console
 * @author    IEDR <asd@iedr.ie>
 * @copyright 2011 IEDR
 * @license   http://www.iedr.ie/ (C) IEDR 2011
 * @version   Release: @package_version@
 * @link      https://statistics.iedr.ie/
 */
class DummyController
	extends AuthOnlyBaseController
	{

    /**
     * returns path to dummy (menu-)view file
     * 
     * @return string Return description (if any) ...
     * @access public
     */
	public function getViewPath() { return 'protected/views/dummy'; }

    /**
     * default action which renders menu view
     * 
     * @return void  
     * @access public
     */
	public function actionMenu() { $this->render('menu'); }
	}

