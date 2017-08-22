<?php
/**
 * defines UserSwitchWidget class
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
 * View-Page Widget for switching users
 * 
 * @category  NRC
 * @package   IEDR_New_Registrars_Console
 * @author    IEDR <asd@iedr.ie>
 * @copyright 2011 IEDR
 * @license   http://www.iedr.ie/ (C) IEDR 2011
 * @version   CVS: $Id:$
 * @link      https://statistics.iedr.ie/
 * @see       UserSwitchWidget, userSwitchView, UserSwitchModel
 */
class UserSwitchWidget
	extends CWidget
	{

    /**
     * performs view rendering
     * 
     * @return void  
     * @access public
     */
	public function run() {
		$this->render('application.widgets.userSwitch.userSwitchView');
		}
	}

