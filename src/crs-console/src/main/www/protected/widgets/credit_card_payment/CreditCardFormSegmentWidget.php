<?php
/**
 * defines CreditCardFormSegmentWidget class
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
 * Yii Widget class for credit-card form, which can be added into another view
 * 
 * 
 * To use this widget in an action:
 * 
 * (1) the Model being used by the action must extend DynamicModelBase (or any of it's descendants), and add CreditCardFormSegmentModel's attributes on construction. Example:
 * 
 * 	class AnotherFormModel
 * 		extends DynamicModelBase
 * 		{
 * 		public function __construct()
 * 			{
 * 			parent::__construct();
 * 			// add members from CreditCardFormSegmentModel
 * 			$this->addMixinModel(new CreditCardFormSegmentModel());
 * 			}
 * 
 * (2) The widget is invoked in the action's view. Example:
 * 
 * 	$form = $this->beginWidget('CActiveForm', array('id'=>'AnotherForm'));
 * 	$this->widget('application.widgets.credit_card_payment.CreditCardFormSegmentWidget', array('form'=> $form, 'model'=> $model));
 * 	$this->endWidget();
 * 
 * (3) The action, on receiving POSTed form data, fills the model (including the added CreditCardFormSegmentModel attributes), like this:
 * 
 * 	public function actionAnother()
 * 		{
 * 		$model = new AnotherModel();
 * 		$this->performAjaxValidation($atu_model);
 * 		if(isset($_POST['AnotherModel']))
 * 			{
 * 			$model->setFromPOST($_POST['AnotherModel']);
 * 
 * @category  NRC
 * @package   IEDR_New_Registrars_Console
 * @author    IEDR <asd@iedr.ie>
 * @copyright 2011 IEDR
 * @license   http://www.iedr.ie/ (C) IEDR 2011
 * @version   Release: @package_version@
 * @link      https://statistics.iedr.ie/
 * @see       CreditCardFormSegmentModel, DynamicModelBase
 */
class CreditCardFormSegmentWidget
	extends CWidget
	{

    /**
     * associated form object
     *
     * @var    object
     * @access public 
     */
	public $form;

    /**
     * associated model object
     *
     * @var    object
     * @access public 
     */
	public $model;

    /**
     * init function (no-op)
     * 
     * @return void  
     * @access public
     */
	public function init() {
		}

    /**
     * renders credit card form html in a view
     * 
     * @return void  
     * @access public
     */
	public function run() {
		$this->render('application.widgets.credit_card_payment.CCform');
		}
	}

