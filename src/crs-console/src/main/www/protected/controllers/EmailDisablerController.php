<?php

/**
 * defines EmailDisablerController class
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
 * This controller class handles general web app actions, including index, error, login, logout
 * 
 * @category  NRC
 * @package   IEDR_New_Registrars_Console
 * @author    IEDR <asd@iedr.ie>
 * @copyright 2011 IEDR
 * @license   http://www.iedr.ie/ (C) IEDR 2011
 * @version   Release: @package_version@
 * @link      https://statistics.iedr.ie/
 */
class EmailDisablerController extends GridController
{

	/**
	 * Short description for function
	 *
	 * Long description (if any) ...
	 *
	 * @return void
	 * @access public
	 * @see    dnsserversearch.php, DnsserversearchModel, NsreportsController::actionDnsserversearch()
	 */
	public function actionMenu()
	{
		$model = new EmailDisablerModel();
		Utility::writeActionToSession('emailDisabler/menu');

		if (Yii::app()->request->isPostRequest && isset($_POST['EmailDisablerModel'])) { 
			$model->disabledValuesByEmailId = CJSON::decode($_POST['EmailDisablerModel']['disabledValuesByEmailId']);
			if (count($model->disabledValuesByEmailId) > 0) {
				$result = '';
				$errors = '';
				$suppressInfos =  $this->generateSuppressInfoData($model);
				CRSEmailDisablerAppService_service::modifySuppressionMode($result, $errors,  $suppressInfos, Yii::app()->user->authenticatedUser);
				$this->setupModelWithSubmitMessages($result, $errors, $model);
			} else {
				$model->errorSubmitMessage = 'No changes entered - nothing to submit.';
				Yii::log('EmailDisablerController: $errors = ' . CVarDumper::dumpAsString($errors));
			}
		} else {
			Yii::log('Yii::app()->request->isPostRequest == false');
		}
		$this->setupModelWithViewData($model);
		$this->render('menu', array('model' => $model));
	}

	private function generateSuppressInfoData($model)
	{
		$suppressInfos = array();
		foreach ($model->disabledValuesByEmailId as $emailId => $disabled) {
			$suppressInfo = new CRSEmailDisablerAppService_emailDisablerSuppressInfo();
			$suppressInfo->emailId = $emailId;
			$suppressInfo->disabled = $disabled;
			$suppressInfo->nicHandle = Yii::app()->user->authenticatedUser->username;
	
			array_push($suppressInfos, $suppressInfo);
		}
		return $suppressInfos;
	}

	private function setupModelWithSubmitMessages($result, $errors, $model){
		if (count($errors) == 0) {
			$numberOfPersisted = count($result->persistedEmailDisablerSuppressInfo);
			if ($numberOfPersisted > 0) {
				$persistedPluralitySuffix = $numberOfPersisted == 1 ? '' : 's';
				$model->successSubmitMessage = 'Successfully updated status of ' . $numberOfPersisted . ' email' . $persistedPluralitySuffix . '.';
			}
			$numberOfRejected = count($result->rejectedEmailDisablerSuppressInfo);
			if ($numberOfRejected > 0) {
				$rejectedIdsString = '';
				$rejectedPluralitySuffix = '';
				if ($numberOfRejected > 1){
					foreach($result->rejectedEmailDisablerSuppressInfo as $index => $rejectedSuppressInfo) {
						if ($rejectedIdsString <> '') {
							$rejectedIdsString = $rejectedIdsString . ', ';
						}
						$rejectedIdsString = $rejectedIdsString . $rejectedSuppressInfo->emailId;
					}
					$rejectedPluralitySuffix = $numberOfRejected == 1 ? '' : 's';
				}
				else{
			
					$rejectedIdsString = $result->rejectedEmailDisablerSuppressInfo->emailId;
				}
				$model->errorSubmitMessage = 'Could not update status of email' . $rejectedPluralitySuffix
				. ' with following ID' . $rejectedPluralitySuffix
				. ': ' . $rejectedIdsString . '.';
			}
		} else {
			$model->errorSubmitMessage = 'Update could not be performed due to an internal server error. Please contact system administrator for help.';
			Yii::log('EmailDisablerController: $errors = ' . CVarDumper::dumpAsString($errors));
		}
	}

	private function setupModelWithViewData($model)
	{
		$result = '';
		$errs = '';
		CRSEmailDisablerAppService_service::getAll($result, $errs, Yii::app()->user->authenticatedUser);
		if (count($errs) == 0) {
			$model->viewDataGrouped = $this->generateViewDataGrouped($result, $model);
		}
		else {
			$model->errorDisplayMessage = 'Internal server error. Please contact system administrator for help.';
			Yii::log('EmailDisablerController: $errors = ' . CVarDumper::dumpAsString($errs));
		}
	}

	private function generateViewDataGrouped($result, $model) {
		$viewDataGrouped = array();
		$groups = $this->divideIntoGroups($result);
		foreach ($groups as $groupName => $groupData) {
			$grouppagedata = array('records' => 0, 'page' => 1, 'total' => 0, 'rows' => array());
			$grouppagedata['total'] = count($groupData->emailDisablers);
			$grouppagedata['page'] = 1;
			$grouppagedata['records'] = count($groupData->emailDisablers);
			if (is_array($groupData->emailDisablers)) {
				foreach ($groupData->emailDisablers as $k => $o) {
					$grouppagedata['rows'][] = $model->addResults($o);
				}
			} else if (is_object($groupData->emailDisablers)) {
				$grouppagedata['rows'][] = $model->addResults($groupData->emailDisablers);
			}
			$modelColIDs = array_keys($model->columns);
			if (is_array($grouppagedata) && array_key_exists('rows', $grouppagedata)) {
				if (count($grouppagedata['rows'])) {
					foreach ($grouppagedata['rows'] as $k => $r) {
						$rowArray = array();
						$rowArray['id'] = $r[$model->defaultSortColumn];
						$rowArray['cell'] = array();
						foreach ($modelColIDs as $colID)
							array_push($rowArray['cell'], $r[$colID]);
						$grouppagedata['rows'][$k] = $rowArray;
					}
				}
			}
			$viewDataGrouped[$groupName] = $grouppagedata;
		}

		return $viewDataGrouped;
	}

	private function divideIntoGroups($result){
		$groupsByIds = array();
		$groupsByNames = array();
		$groupNames = array();
		foreach ($result->emailDisablers as $k => $emailDisabler) {
			$emailGroupId = $emailDisabler->emailTemplate->group->id;
			if ($emailGroupId  == null) { $emailGroupId = PHP_INT_MAX;}
			if (!array_key_exists($emailGroupId, $groupsByIds)) {
				$groupNames[$emailGroupId] = $emailDisabler->emailTemplate->group->name;
				if ($groupNames[$emailGroupId] == '') {
					$groupNames[$emailGroupId] = 'Other';
				}
				$groupsByIds[$emailGroupId] = new CRSEmailDisablerAppService_emailDisablerSearchResultVO;
				$groupsByIds[$emailGroupId]->emailDisablers = array();
			}
			array_push($groupsByIds[$emailGroupId]->emailDisablers, $emailDisabler);
		}
 		ksort($groupsByIds);
 		foreach ($groupsByIds as $k => $group) {
 			$groupsByNames[$groupNames[$k]] = $group;
 		}

		return $groupsByNames;
	}

	protected function getSelectionModel()
	{
		// called from actionConfirmaction()
		return new EmailDisablerModel();
	}

	protected function getSelectionModelName()
	{
		// called from actionConfirmaction()
		return 'EmailDisablerModel';
	}
}
