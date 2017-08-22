<?php

/**
 * Main configuration for for Yii
 *
 * This is the main Web application configuration. Any writable CWebApplication properties can be configured here.
 * Configures Yii application parameters, including logging, database access, NASK CRS-WS Url, caching, admin email, etc.
 *
 * @category  NRC
 * @package   IEDR_New_Registrars_Console
 * @author    IEDR <asd@iedr.ie>
 * @copyright 2011 IEDR
 * @license   http://www.iedr.ie/ (C) IEDR 2011
 * @version   CVS: $Id:$
 * @link      https://statistics.iedr.ie/
 * @see       http://www.yiiframework.com/doc/api/1.1/CWebApplication
 */

// uncomment the following to define a path alias:
//Yii::setPathOfAlias('local','path/to/local-folder');
return array(
    'basePath'=>'/opt/crs/console/www/protected',
    'runtimePath'=>'/var/iedr/console',
    'name'=>'IEDR Registrar\'s Console',
    'preload'=>array('log'),		// preloading 'log' component
    'import'=>array(				// autoloading model and component classes
        'application.models.*',
        'application.components.*',
    ),
    'modules'=>array(),
    // application components
    'components'=>array(
        'user'=>array(
            'authTimeout' => (3600*6),
            'allowAutoLogin'=>false,
            'loginUrl'=>array('site/login'),
        ), // true => "data stored in session" => session serialized into cookie data, stored on client
        'errorHandler'=>array('errorAction'=>'site/error',), // use 'site/error' action to display errors
        'log'=>array(
            'class'=>'CLogRouter',
            'routes'=>array(
                array(
                    'class'=>'CFileLogRoute',
                    'levels'=>'error, warning, info, debug',
                ),
                //array('class'=>'CWebLogRoute',), // uncomment to show log messages on web pages
            ),
        ),
        'request'=>array(
            'enableCsrfValidation'=>true,
            'csrfCookie'=>array(
                'httpOnly'=>true,
            ),
            'enableCookieValidation'=>true,
            'hostInfo'=>(isset($_SERVER['HTTP_X_FORWARDED_HOST']) ? 'https://'.$_SERVER['HTTP_X_FORWARDED_HOST'] : (isset($_SERVER['HTTPS']) ? 'https://' : 'http://').$_SERVER['HTTP_HOST']),
        ),
        'session' => array(
            'cookieParams' => array(
                'httponly' => true,// lower case required to work!!!
            ),
        ),
        // not installed in RHEL5 (no memcache; no apc)
        'cache'=>array('class'=>'CFileCache', ),
        'clientScript' => array(
            'scriptMap' => array(
                'jquery.js' => 'jqGrid4/js/jquery-1.6.1.src.js',
                'jquery.yii.js' => 'jqGrid4/js/jquery-1.6.1.src.js',
            )
        ),
    ),
    // application-level parameters that can be accessed using Yii::app()->params['paramName']
    'params'=>array(
        'adminEmail'=>'webmaster@domainregistry.ie', // this is used in contact page
        //'wsapi_soap_url'=>'http://ws:8080/crs-web-services/',
        'wsapi_soap_url'=>'http://localhost:8080/crs-web-services/',
        //'wsapi_soap_url'=>'http://193.59.205.170:8080/crs-web-services-0.10.0-Sprint10/',
        'superuser_nic'=>'BILLY-IEDR',
        'superuser_pass'=>'password',
        'ticketExpiryDays'=>new DateInterval('P27D'),
        'ckdnsPath'=>'/opt/crs/tools/ckdns',
        'uploader_share_dir' => '/tmp',
        'internal_network'=>array(
            'exact_matches'=>array(
                #'127.0.0.1',		// local
                '83.71.193.115',	// proxy
                #'193.59.205.170',
                #	'213.190.149.194',	// firewall ?!?!
            ),
            'regex_matches'=>array(
                // internal non-routable networks
                '/^10.10./',
                '/^192.168./',
            ),
        ),
        'iedr_users'=>array(
            'ALICE-IEDR',
            'ANGELA-IEDR',
            'BILLY-IEDR',
            'BRIAN-IEDR',
            'CIARA-IEDR',
            'DAVID-IEDR',
            'DELPHIN-IEDR',
            'EBALM-IEDR',
            'LDEAN-IEDR',
            'LORIN-IEDR',
            'PAUL-IEDR',
            'ROBIN-IEDR',
            'SAM-IEDR',
            'SARAL-IEDR',
        ),
        'bulk_operation_limit' => 250,
        'testCertDir' => '/var/iedr/certs/test/',
        'prodCertDir' => '/var/iedr/certs/prod/',
        'certNotificationEmail' => 'api@iedr.ie'
    ),
);

