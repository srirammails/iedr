<?php

/**
 * file which defines Controller class
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
 * Application base Controller class.
 * 
 * All controller classes for this application should extend from this base class.
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
 * @see       http://www.yiiframework.com/doc/api/1.1/CController
 */
class Controller extends CController {

   /**
    * @var string the default layout for the controller view. Defaults to '//layouts/column1',
    * meaning using a single column layout. See 'protected/views/layouts/column1.php'.
    */
   public $layout = '//layouts/column1';

   /**
    * @var array context menu items. This property will be assigned to {@link CMenu::items}.
    */
   public $menu = array();

   /**
    * used by views for page title
    * @var    array
    * @access public
    */
   public $breadcrumbs = array('');
   protected $backend_errors;

   /**
    * a simple log wrapper, calls {@link http://www.yiiframework.com/doc/api/1.1/YiiBase#log-detail Yii::log}
    * 
    * See : {@link http://www.yiiframework.com/doc/api/1.1/YiiBase#log-detail}
    * 
    * @param  string $m message
    * @param  string $s severity : debug. warn, error
    * @param  string $c context, eg 'Function()' or 'Class::Function'
    * @return void
    * @access public
    * @static
    */
   public static function log($m, $s, $c) {
      //#print($c.','.$s.','.$m."\n");
      Yii::log($m, $s, $c);
   }

   /**
    * A helper method for derived classes that validates any ajax requests seen by action* member
    * functions, echoes http response to ajax client, and terminates the response processing
    * (i.e. does not return from this function call)
    * See : {@link http://www.yiiframework.com/doc/api/1.1/CActiveForm#validate-detail }, {@link http://www.yiiframework.com/doc/api/1.1/CModel#validators-detail }
    * 
    * @param  CModel   $model Model to be validated (needs not be populated with data)
    * @return void     
    * @access protected
    */
   protected function performAjaxValidation($model) {
      if (isset($_POST['ajax'])) {
         $retval = CActiveForm::validate($model);
         Yii::log($retval, 'debug', 'Controller::performAjaxValidation()');
         echo $retval;
         Yii::app()->end();
      }
   }

   /**
    * Action Method that switches between users, used by IEDR Staff.
    * 
    * The {@link NicHandleIdentity} class handles the operation.
    * The SwitchUser Action is used by the form created by {@link UserSwitchWidget}
    * 
    * @return void  
    * @access public
    */
   public function actionSwitchuser() {
      Yii::log(print_r($_REQUEST, true), 'debug', 'SiteController::actionSwitchuser()');
      $userSwitchModel = new UserSwitchModel();
      $userSwitchModel->attributes = $_POST['UserSwitchModel'];
      if ($userSwitchModel->validate()) {
         // perform user switch
         $nicHandleIdentity = new NicHandleIdentity(null, $userSwitchModel->switchUser, $userSwitchModel->switchUser);
         $nicHandleIdentity->switchToAccount();
         // tel Yii that new user is authenticated
         Yii::app()->user->login($nicHandleIdentity, 0/* prevent "cookie-based authentication" */);
      }
      $this->redirect('index.php?r=site/index');
   }

}

