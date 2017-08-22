<?php

/**
 * defines class DomainreportsController
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
class DomainreportsController
	extends GridController
	{

    /**
     * Description for public
     * @var    array 
     * @access public
     */
	public $breadcrumbs = array('Domainreports');

    /**
     * Short description for function
     * 
     * Long description (if any) ...
     * 
     * @return void  
     * @access public
     * @see    domainreports/menu.php
     */
	public function actionMenu() { $this->render('menu'); }

    /**
     * Short description for function
     * 
     * Long description (if any) ...
     * 
     * @return void  
     * @access public
     * @see    alldomains.php
     */
	public function actionAlldomains() {
		$model = new AllDomainsModel();
        Utility::writeActionToSession('domainreports/alldomains');
		$this->render('alldomains',array('model'=>$model));
		}

    /**
     * Short description for function
     * 
     * Long description (if any) ...
     * 
     * @return void  
     * @access public
     * @see    newdomains.php
     */
   public function actionConfirm() {
      $this->showConfirmPage();
   }
      
      
	public function showConfirmPage() {
      if(isset($_POST['gridaction'])) {
         $model = $this->getSelectionModel();
         $model->setFromPOST($_POST['gridaction']);
         $this->processGridActionCommand($model);         
         $this->redirectAfterPost('confirmaction', $model );
//          $this->redirect(array('confirmaction', 'id'=>self::safeSerializeObj($model) ));
      } else {
         $model = $this->getSelectionModel();
         $this->redirect($model->returnurl,true);
      }
   }
   
   public function actionConfirm_billable() {
      $this->showConfirmPage();
   }
      
	public function actionNewdomains() {
		$nd_model = new NewDomainsModel();
		$this->performAjaxValidation($nd_model);
		if(Yii::app()->request->isPostRequest and isset($_POST['NewDomainsModel'])) {
			$nd_model->attributes = $_POST['NewDomainsModel'];
		}
		Yii::log(print_r($nd_model,true), 'debug', 'DomainreportsController::actionNewdomains()');
        Utility::writeActionToSession('domainreports/newdomains');
		$this->render('newdomains',array('model'=>$nd_model));
		}

    /**
     * Short description for function
     * 
     * Long description (if any) ...
     * 
     * @return void  
     * @access public
     * @see    charity.php, CharityDomainModel
     */
      
    /**
     * Short description for function
     * 
     * Long description (if any) ...
     * 
     * @return void  
     * @access public
     * @see    autorenewed.php, ARDomainModel
     */


    /**
     * Short description for function
     * 
     * Long description (if any) ...
     * 
     * @return string Return description (if any) ...
     * @access public
     */
	public function getExportFilenameTag()
		{
		return 'DomainReports_';
		}
	}

