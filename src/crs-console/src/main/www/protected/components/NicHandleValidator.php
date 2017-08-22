<?php

/**
 * defines NicHandleValidator class
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
 * Validates a NicHandle, that it matches syntax and has an account number
 * 
 * @category  NRC
 * @package   IEDR_New_Registrars_Console
 * @author    IEDR <asd@iedr.ie>
 * @copyright 2011 IEDR
 * @license   http://www.iedr.ie/ (C) IEDR 2011
 * @version   Release: @package_version@
 * @link      https://statistics.iedr.ie/
 * @see       NicHandleSyntaxValidator
 */
class NicHandleValidator
	extends NicHandleSyntaxValidator
	{

    /**
     * Validator that returns true if the model-attribute both (a) passes the parent NicHandle Syntax
     * check (see {@link NicHandleSyntaxValidator}), and (b) has an Account-ID returned by the CRS-WS API
     * call CRSNicHandleAppService:get().
     * 
     * @param  CModel    $model     model containing data to validate
     * @param  string   $attribute name of model attribute containing NicHandle to validate
     * @return boolean   true if NicHandle has valid syntax and has an AccountID from CRS-WS API
     * @access protected
     */
	protected function validateAttribute($model,$attribute)
		{
		if(parent::validateAttribute($model,$attribute))
			{
			$value=$model->$attribute;
			// additionally, see if the Nic-Handle exists
			if(!$this->NICExists($value))
				{
				$lbls = $model->attributeLabels();
				$lbl = $lbls[$attribute];
				$model->addError($attribute,Yii::t('yii',$lbl.' is not an existing NIC-Handle'));
				}
			else
				return true;
			}
		else
			return false;
		}

    /**
     * Gets an account ID for a given NicHandle, from the CRS-WS Backend
     * 
     * @param  string    $nic Nic Handle to search for
     * @return integer   Account ID, if found, otherwise -1
     * @access protected
     */
    protected function _NICExists($nic)
		{
		$result = null; // CRSNicHandleAppService_nicHandleVO
		$errs   = null;
		try
			{
			CRSNicHandleAppService_service::get($result,$errs,Yii::app()->user->authenticatedUser,$nic);
			if(count($errs)!=0)
				Yii::log('ERRS='.$errs, 'error', 'NicHandleValidator::_NICExists('.$nic.') [err]');
			else
				Yii::log('ACCID='.$result->accountId, 'debug', 'NicHandleValidator::_NICExists('.$nic.') [res]');
			}
		catch(Exception $e)
			{
			Yii::log(print_r($e,true), 'error', 'NicHandleValidator::_NICExists('.$nic.') [EX]');
			}
		return count($errs)==0 ? $result->accountId : -1;
		}

    /**
     * Gets an account ID for a given NicHandle, returning a cached value of found, otherwise calling {@link _NICExists}
     * 
     * @param  string    $nic Nic Handle to search for
     * @return integer   Account ID, if found, otherwise -1
     * @access protected
     */
    protected function NICExists($nic)
		{
		$accno = -1;
		// is cached ?
		$c = Yii::app()->cache;
		// cache is global to application (all session/users), so use a cache key that contains the logged-in user,
		// to avoid returning a cached value to another user
		$ckey = 'NICExistsFor_'.Yii::app()->user->authenticatedUser->username.'_'.$nic;
		if($c!=null)
			{
			$cached_accno = null;
			$cached_accno = Yii::app()->cache->get($ckey);
			if(is_integer($cached_accno))
				{
				Yii::log('cache hit for '.$ckey, 'debug', 'NicHandleValidator::NICExists('.$nic.')');
				$accno = $cached_accno;
				}
			else
				{
				Yii::log('cache miss for '.$ckey, 'debug', 'NicHandleValidator::NICExists('.$nic.')');
				$accno = $this->_NICExists($nic);
				Yii::app()->cache->set($ckey, $accno, 60);
				}
			}
		else
			{
			Yii::log('no caching', 'debug', 'NicHandleValidator::NICExists('.$nic.')');
			$ret = $this->_NICExists($nic);
			}
		return $accno > 0 ? true : false;
		}

	}
