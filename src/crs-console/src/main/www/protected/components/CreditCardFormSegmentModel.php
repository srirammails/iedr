<?php

/**
 * defines CreditCardFormSegmentModel class
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
 * a form model for use with credit-card input
 * 
 * To used used as a 'mixin' class in other form-models (based on @DynamicModelBase) whose views contain the widget @CreditCardFormSegmentWidget
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
class CreditCardFormSegmentModel
    extends FormModelBase
    {
   
   public $rules = array();

   public function __construct() {
      $year = date('y',time());
      $userMin = isset(Yii::app()->user->depositAccMin) ? Yii::app()->user->depositAccMin : 0;
      $userMax = isset(Yii::app()->user->depositAccMax) ? Yii::app()->user->depositAccMax : 9999999;
      $this->rules = array(
            array('creditcardno,euros_amount,cardholder, exp_date, exp_year,exp_month,cardtype,cvn',
                'required',
                'on'=>'topup,'.CreditCardFormSegmentModel::$without_amount_scenario),
//            array('creditcardno,euros_amount,exp_year,exp_month',
//                'numerical',
//                'on'=>'topup,'.CreditCardFormSegmentModel::$without_amount_scenario),
            array('exp_year',
                'numerical', 'min'=>$year, 'max'=>($year+15),
                'on'=>'topup,'.CreditCardFormSegmentModel::$without_amount_scenario),
            array('cardtype', 'length', 'min'=>2, 'max'=>15,
                'on'=>'topup,'.CreditCardFormSegmentModel::$without_amount_scenario),
            array('exp_month',
                'numerical', 'min'=>1, 'max'=>12,
                'on'=>'topup,'.CreditCardFormSegmentModel::$without_amount_scenario),
            array('cvn',
                'numerical',
                'numberPattern'=>'/^[0-9]{3}$/i',
                'message'=>'{attribute} must be a three digit number',
                'on'=>'topup,'.CreditCardFormSegmentModel::$without_amount_scenario),
            array('creditcardno',
                'numerical', 'min'=>15,
                'on'=>'topup,'.CreditCardFormSegmentModel::$without_amount_scenario),
            array('euros_amount',
                'numerical',
                'min'=>$userMin,
                'tooSmall'=>'Minimum Transaction is '.Utility::currencyAmount($userMin),
                'max'=>$userMax,
                'tooBig'=>'Maximum Transaction is '.Utility::currencyAmount($userMax),
                'numberPattern'=>'/^[0-9]+(\.[0-9]{1,2}){0,1}$/i', 'message'=>'{attribute} can have only two decimal places',
                'on'=>'topup'),
        );
   }

   public function setupRules() {
      $this->rules = array_merge($this->rules, array(
         array(
                  'creditcardno', 
                  'CreditCardValidator' ,
              ), 
      ));
   }
    /**
     * yii scenario where the amount is not entered by the client
     * @var    string
     * @access public
     * @static
     * @see http://www.yiiframework.com/doc/api/1.1/CModel#scenario-detail
     */
	static public $without_amount_scenario = 'without_amount_scenario';

    /**
     * entered credit card number
     * @var    string
     * @access public 
     */
	public $creditcardno;

    /**
     * entered euro amount
     * @var    string
     * @access public 
     */
	public $euros_amount;

    /**
     * entered cardholder full name
     * @var    string
     * @access public 
     */
	public $cardholder;

    /**
     * entered card expiry year
     * @var    string
     * @access public 
     */
	public $exp_year;

    /**
     * Description for public
     * @var    string
     * @access public 
     */
	public $exp_month;

    /**
     * entered card validation number
     * @var    string
     * @access public 
     */
	public $cvn;

   public $cardtype;

   public $exp_date = 'just a label';
    /**
     * returns yii validation rules for form validation
     * 
     * Some of the rules are only active if the form scenario is the @without_amount_scenario
     * 
     * @return array  
     * @access public
     */
	public function rules()
		{
         $this->setupRules();
         return $this->rules;
		}
      
    /**
     * returns yii attribute labels for form rendering
     * 
     * @return array
     * @access public
     */
	public function attributeLabels()
		{
		return array(
         'cardtype'   => 'Card Type',
			'creditcardno'   => 'Card Number',
			'euros_amount'   => 'Top-up Amount',
			'cardholder'         => 'Card Holder',
			'exp_year'       => 'Card Expiration Year',
			'exp_month'      => 'Card Expiration Month',
			// no field named 'exp_date', this is just a label to put beside the pair of Mon/Yr inputs
			'exp_date'       => 'Card Expiration Month / Year',
			'cvn'            => 'Card Verification Number',
			);
		}

    /**
     * returns an object instance of class CRSPaymentAppService_paymentRequestVO, filled with the model's credit-card data
     * 
     * @param  DynamicModelBase $mdl model whose data will be copied
     * @return CRSPaymentAppService_paymentRequestVO object filled with the model's credit-card data
     * @access public
     * @static
     */
	static public function asWSAPIPaymentObject($mdl)
		{
		// call with a parameter being either an instance of this class or a class that has it's values mixed in
		// e.g. $p = CreditCardFormSegmentModel::asWSAPIPaymentObject($m);
		$payreq = new CRSPaymentAppService_paymentRequestVO();
		$payreq->currency       = 'EUR';
		$payreq->amount		   = $mdl->euros_amount;
		$payreq->cardNumber     = $mdl->creditcardno;
		$payreq->cardExpDate    = sprintf('%02u%02u', $mdl->exp_month, $mdl->exp_year);
		$payreq->cardHolderName	= $mdl->cardholder;
      // determine the card type
		//CreditCardValidator::CCPrefixLengthTypeCheck($mdl->creditcardno,$payreq->cardtype);
      
      switch($mdl->cardtype) {
         case "MC":
            $payreq->cardType = 'MC';
           $payreq->cvnPresenceIndicator = 1;
            break;
         case "VISA":
            $payreq->cardType = 'VISA';
            $payreq->cvnPresenceIndicator = 1;
            break;
      }

         $payreq->cvnNumber = $mdl->cvn;
         Yii::log('PAYREQ= '.print_r($payreq, true));
         return $payreq;
		}

    /**
     * test data function
     * 
     * fills the model with test data, only for use during development
     * 
     * @param  DynamicModelBase $obj      model to fill with test data
     * @param  string $testcase 1-letter index into test-data array, for test selection
     * @return void  
     * @access public
     * @static
     */
	public static function setTestVals($obj,$testcase=null)
		{
		// pick a random test case. when testing, can be called from controller-action before displaying new form
		// these cards (except invalid card 73045897360) are approved for test use with RealEx by IEDR.

		// usage :
		// $mymodel->addMixinModel(new CreditCardFormSegmentModel());
		// CreditCardFormSegmentModel::setTestVals($mymodel,'A');

		$paytests = array(
			'A'=>array('cardno'=>'4263971921001307','cpi'=>CVN_PRESENT_OK,'cvn'=>'123','cardtype'=>'VISA','expected_code'=>'00','expected_msg'=>'AUTH CODE xxxxxx (CVN 123)'),
			'B'=>array('cardno'=>'5425232820001308','cpi'=>CVN_PRESENT_OK,'cvn'=>'123','cardtype'=>'MC','expected_code'=>'00','expected_msg'=>'AUTH CODE xxxxxx'),
			'C'=>array('cardno'=>'6304939304310009610','cpi'=>CVN_PRESENT_NOT_ON_CARD,'cvn'=>null,'cardtype'=>'LASER','expected_code'=>'00','expected_msg'=>'AUTH CODE xxxxxx'),
			'D'=>array('cardno'=>'4000126842489127','cpi'=>CVN_PRESENT_OK,'cvn'=>'123','cardtype'=>'VISA','expected_code'=>'101','expected_msg'=>'DECLINED'),
			'E'=>array('cardno'=>'5114617896541284','cpi'=>CVN_PRESENT_OK,'cvn'=>'123','cardtype'=>'MC','expected_code'=>'101','expected_msg'=>'DECLINED'),
			'F'=>array('cardno'=>'4000136842489878','cpi'=>CVN_PRESENT_OK,'cvn'=>'123','cardtype'=>'VISA','expected_code'=>'102','expected_msg'=>'REFERRAL B'),
			'G'=>array('cardno'=>'5114634523652350','cpi'=>CVN_PRESENT_OK,'cvn'=>'123','cardtype'=>'MC','expected_code'=>'102','expected_msg'=>'REFERRAL B'),
			'H'=>array('cardno'=>'4000166842489115','cpi'=>CVN_PRESENT_OK,'cvn'=>'123','cardtype'=>'VISA','expected_code'=>'103','expected_msg'=>'REFERRAL A'),
			'I'=>array('cardno'=>'5121229875643585','cpi'=>CVN_PRESENT_OK,'cvn'=>'123','cardtype'=>'MC','expected_code'=>'103','expected_msg'=>'REFERRAL A'),
			'J'=>array('cardno'=>'4015955467892139','cpi'=>CVN_PRESENT_OK,'cvn'=>'123','cardtype'=>'VISA','expected_code'=>'200','expected_msg'=>'Too Many Simultaneous Transactions'),
			'K'=>array('cardno'=>'5135024346548487','cpi'=>CVN_PRESENT_OK,'cvn'=>'123','cardtype'=>'MC','expected_code'=>'200','expected_msg'=>'Too Many Simultaneous Transactions'),
			'L'=>array('cardno'=>'4009837983422344','cpi'=>CVN_PRESENT_OK,'cvn'=>'123','cardtype'=>'VISA','expected_code'=>'205','expected_msg'=>'Comms Error'),
			'M'=>array('cardno'=>'5135024345352238','cpi'=>CVN_PRESENT_OK,'cvn'=>'123','cardtype'=>'MC','expected_code'=>'205','expected_msg'=>'Comms Error'),
			'N'=>array('cardno'=>'73045897360','cpi'=>CVN_PRESENT_OK,'cvn'=>'123','cardtype'=>'MC','expected_code'=>'99','expected_msg'=>'Luhn Check Fail'),
			);
		$testcase = $testcase==null ? chr(rand(ord('A'),ord('N'))) : $testcase;
		$randtest = $paytests[$testcase];

		Yii::log('testcase '.$testcase.' = '.print_r($randtest,true), 'debug', 'CreditCardFormSegmentModel::setTestVals()');

		$obj->creditcardno	= $randtest['cardno'];
		$obj->euros_amount	= rand(1,1500)/100;
		$obj->carHolder		= 'Test Transaction '.rand(1000,9999);
		$obj->exp_year		= rand(12,31);
		$obj->exp_month		= rand(1,12);
		$obj->cvn		= $randtest['cvn'];;
		}
	}

