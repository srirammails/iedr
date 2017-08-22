<?php
/**
 * Main switchboard-style application code
 * 
 * PHP versions 5
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
 */

define('BR',"\n");
date_default_timezone_set('Europe/Dublin');
session_name("NEW_REGISTRARS_CONSOLE");

// auto-generated classes specifying CRS-WS Soap API
require_once('protected/wsapi/wsapi_base.php');
require_once('protected/wsapi/CRSAuthenticationService.php');
require_once('protected/wsapi/CRSDomainAppService.php');
require_once('protected/wsapi/CRSDocumentAppService.php');
require_once('protected/wsapi/CRSEmailDisablerAppService.php');
require_once('protected/wsapi/CRSNicHandleAppService.php');
require_once('protected/wsapi/CRSPaymentAppService.php');
require_once('protected/wsapi/CRSPermissionsAppService.php');
require_once('protected/wsapi/CRSResellerAppService.php');
require_once('protected/wsapi/CRSTicketAppService.php');
require_once('protected/wsapi/CRSCommonAppService.php');
require_once('protected/wsapi/CRSInfo.php');

// database access classes, intended as temporary until similar functionality is available via CRS-WS Soap API
require_once('protected/db/class_category.php');
require_once('protected/db/domain_prices.php');
require_once('protected/db/short_account_stats.php');
require_once('protected/db/country_county.php');
require_once('protected/db/registrars_for_internal_login.php');
require_once('protected/db/wsapi_version.php');

// application menu definition and functions
require_once('protected/components/menu.php');

define('YII_ENABLE_EXCEPTION_HANDLER',true);
define('YII_ENABLE_ERROR_HANDLER',false);//php errors, notices only loged in apache logs

// change the following paths if necessary
$yii='yii/framework/yii.php';
$config='/etc/iedr/console/main.php';

define ('MESSAGE_WSAPIERRORS','protected/messages/wsapi_errors');

// remove the following lines when in production mode
//defined('YII_DEBUG') or define('YII_DEBUG',true);
// specify how many levels of call stack should be shown in each log message
//defined('YII_TRACE_LEVEL') or define('YII_TRACE_LEVEL',10);

require_once($yii);

Yii::createWebApplication($config)->run();

