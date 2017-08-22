<?php
/**
 * defines Account (data-model) class
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
 * This is the model class for table "Account".
 * 
 * The followings are the available columns in table 'Account':
 * @property string $A_Number
 * @property string $A_Name
 * @property string $Billing_NH
 * @property string $Address
 * @property string $County
 * @property string $Country
 * @property string $Web_Address
 * @property string $Phone
 * @property string $Fax
 * @property string $A_Status
 * @property string $A_Status_Dt
 * @property string $A_Tariff
 * @property string $Credit_Limit
 * @property string $A_Reg_Dt
 * @property string $A_TStamp
 * @property string $Invoice_Freq
 * @property string $Next_Invoice_Month
 * @property string $A_Remarks
 *           
 * The followings are the available model relations:
 * @property AccountFlags $accountFlags
 * @property INCOMINGDOC[] $iNCOMINGDOCs
 * 
 * @category  NRC
 * @package   IEDR_New_Registrars_Console
 * @author    IEDR <asd@iedr.ie>
 * @copyright 2011 IEDR
 * @license   http://www.iedr.ie/ (C) IEDR 2011
 * @version   Release: @package_version@
 * @link      https://statistics.iedr.ie/
 */

class Account extends CActiveRecord
{
	/**
	 * Returns the static model of the specified AR class.
	 * @return Account the static model class
	 */
	public static function model($className=__CLASS__)
	{
		return parent::model($className);
	}

	/**
	 * @return string the associated database table name
	 */
	public function tableName()
	{
		return 'Account';
	}

	/**
	 * @return array validation rules for model attributes.
	 */
	public function rules()
	{
		// NOTE: you should only define rules for those attributes that
		// will receive user inputs.
		return array(
			array('Address, County, Country, Web_Address, Phone, Fax, A_Tariff, A_TStamp, A_Remarks', 'required'),
			array('A_Name', 'length', 'max'=>30),
			array('Billing_NH', 'length', 'max'=>12),
			array('A_Status, Credit_Limit, Invoice_Freq, Next_Invoice_Month', 'length', 'max'=>10),
			array('A_Status_Dt, A_Reg_Dt', 'safe'),
			// The following rule is used by search().
			// Please remove those attributes that should not be searched.
			array('A_Number, A_Name, Billing_NH, Address, County, Country, Web_Address, Phone, Fax, A_Status, A_Status_Dt, A_Tariff, Credit_Limit, A_Reg_Dt, A_TStamp, Invoice_Freq, Next_Invoice_Month, A_Remarks', 'safe', 'on'=>'search'),
		);
	}

	/**
	 * @return array relational rules.
	 */
	public function relations()
	{
		// NOTE: you may need to adjust the relation name and the related
		// class name for the relations automatically generated below.
		return array(
			'accountFlags' => array(self::HAS_ONE, 'AccountFlags', 'A_Number'),
			'iNCOMINGDOCs' => array(self::HAS_MANY, 'INCOMINGDOC', 'ACCOUNT_NUMBER'),
		);
	}

	/**
	 * @return array customized attribute labels (name=>label)
	 */
	public function attributeLabels()
	{
		return array(
			'A_Number' => 'A Number',
			'A_Name' => 'A Name',
			'Billing_NH' => 'Billing Nh',
			'Address' => 'Address',
			'County' => 'County',
			'Country' => 'Country',
			'Web_Address' => 'Web Address',
			'Phone' => 'Phone',
			'Fax' => 'Fax',
			'A_Status' => 'A Status',
			'A_Status_Dt' => 'A Status Dt',
			'A_Tariff' => 'A Tariff',
			'Credit_Limit' => 'Credit Limit',
			'A_Reg_Dt' => 'A Reg Dt',
			'A_TStamp' => 'A Tstamp',
			'Invoice_Freq' => 'Invoice Freq',
			'Next_Invoice_Month' => 'Next Invoice Month',
			'A_Remarks' => 'A Remarks',
		);
	}

	/**
	 * Retrieves a list of models based on the current search/filter conditions.
	 * @return CActiveDataProvider the data provider that can return the models based on the search/filter conditions.
	 */
	public function search()
	{
		// Warning: Please modify the following code to remove attributes that
		// should not be searched.

		$criteria=new CDbCriteria;

		$criteria->compare('A_Number',$this->A_Number,true);
		$criteria->compare('A_Name',$this->A_Name,true);
		$criteria->compare('Billing_NH',$this->Billing_NH,true);
		$criteria->compare('Address',$this->Address,true);
		$criteria->compare('County',$this->County,true);
		$criteria->compare('Country',$this->Country,true);
		$criteria->compare('Web_Address',$this->Web_Address,true);
		$criteria->compare('Phone',$this->Phone,true);
		$criteria->compare('Fax',$this->Fax,true);
		$criteria->compare('A_Status',$this->A_Status,true);
		$criteria->compare('A_Status_Dt',$this->A_Status_Dt,true);
		$criteria->compare('A_Tariff',$this->A_Tariff,true);
		$criteria->compare('Credit_Limit',$this->Credit_Limit,true);
		$criteria->compare('A_Reg_Dt',$this->A_Reg_Dt,true);
		$criteria->compare('A_TStamp',$this->A_TStamp,true);
		$criteria->compare('Invoice_Freq',$this->Invoice_Freq,true);
		$criteria->compare('Next_Invoice_Month',$this->Next_Invoice_Month,true);
		$criteria->compare('A_Remarks',$this->A_Remarks,true);

		return new CActiveDataProvider(get_class($this), array(
			'criteria'=>$criteria,
		));
	}
}
