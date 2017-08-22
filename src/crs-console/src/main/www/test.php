<?php

date_default_timezone_set('Europe/Dublin');
session_name("NEW_REGISTRARS_CONSOLE");

require_once('protected/wsapi/wsapi_base.php');
require_once('protected/wsapi/CRSAuthenticationService.php');
require_once('protected/wsapi/CRSDomainAppService.php');
require_once('protected/wsapi/CRSNicHandleAppService.php');
require_once('protected/wsapi/CRSPaymentAppService.php');
require_once('protected/wsapi/CRSPermissionsAppService.php');
require_once('protected/wsapi/CRSResellerAppService.php');
require_once('protected/wsapi/CRSTicketAppService.php');
require_once('protected/wsapi/CRSCommonAppService.php');
require_once('protected/wsapi/CRSInfo.php');
require_once('protected/db/wsapi_version.php');

$libs=array(
	array(
		t('yii','PHP version'),
		true,
		version_compare(PHP_VERSION,"5.1.0",">="),
		'<a href="http://www.yiiframework.com">Yii Framework</a>',
		t('yii','PHP 5.1.0 or higher is required.')),
	array(
		t('yii','$_SERVER variable'),
		true,
		($message=checkServerVar()) === '',
		'<a href="http://www.yiiframework.com">Yii Framework</a>',
		$message),
	array(
		t('yii','Reflection extension'),
		true,
		class_exists('Reflection',false),
		'<a href="http://www.yiiframework.com">Yii Framework</a>',
		''),
	array(
		t('yii','PCRE extension'),
		true,
		extension_loaded("pcre"),
		'<a href="http://www.yiiframework.com">Yii Framework</a>',
		''),
	array(
		t('yii','SPL extension'),
		true,
		extension_loaded("SPL"),
		'<a href="http://www.yiiframework.com">Yii Framework</a>',
		''),
	array(
		t('yii','Mcrypt extension'),
		true,
		extension_loaded("mcrypt"),
		'<a href="http://www.yiiframework.com/doc/api/CSecurityManager">CSecurityManager</a>',
		t('yii','This is required by encrypt and decrypt methods.')),
	array(
		t('yii','JSON extension'),
		true,
		extension_loaded('json'),
		'NRC',
		'This is required for NRC.'),
    array(
		t('yii','GD extension with<br />FreeType support'),
		true,
		($message=checkGD()) === '',
		//extension_loaded('gd'),
		'<a href="http://www.yiiframework.com">Yii Framework</a>',
		$message),
    array(
		t('yii','SOCEKTS extension'),
		true,
		extension_loaded('sockets'),
		t('yii','NRC'),
		t('yii','This is required to check tomcat session.')),
    array(
		t('yii','XMLRPC extension'),
		true,
		extension_loaded('xmlrpc'),
		t('yii','NRC'),
		t('yii','This is required if using SoapClient.')),
     array(
		t('yii','XML extension'),
		true,
		extension_loaded('xml'),
		t('yii','NRC'),
		t('yii','This is required if using xmlrpc.')),
    array(
		t('yii','MBSTRING extension'),
		false,
		extension_loaded('mbstring'),
		t('yii','NRC'),
		t('yii','This is required for NRC.')),
    array(
		t('yii','SOAP extension'),
		true,
		extension_loaded("soap"),
		'NRC',
		'This is required if using SoapClient.'),
    array(
		t('yii','SUHOSIN extension'),
		false,
		!(in_array('suhosin',get_loaded_extensions()) && ini_get('suhosin.get.max_value_length') < 8192),
		'NRC',
		'This is required protect servers and users from known and unknown flaws in PHP core.'),
    array(
		t('yii','ZLIB extension'),
		true,
		extension_loaded('zlib'),
		'NRC',
		'This is required for NRC.'),
    array(
		t('yii','ZIP extension'),
		true,
		extension_loaded('zip'),
		'NRC',
		'This is required for NRC.'),
);

$directoriesAreAccessible=array(
    array(
		t('yii','assets'),
		true,
		is_dir('assets'),
		'NRC',
		''),
    array(
		t('yii','css'),
		true,
		is_dir('css'),
		'Framework Yii',
		''),
    array(
		t('yii','files'),
		true,
		is_dir('files'),
		'NRC',
		''),
    array(
		t('yii','files/docs'),
		true,
		is_dir('files/docs'),
		'NRC',
		''),
    array(
		t('yii','files/api'),
		true,
		is_dir('files/api'),
		'NRC',
		''),
    array(
		t('yii','html'),
		true,
		is_dir('html'),
		'Framework Yii',
		''),
    array(
		t('yii','images'),
		true,
		is_dir('images'),
		'NRC',
		''),
    array(
		t('yii','jqGrid4'),
		true,
		is_dir('jqGrid4'),
		'NRC',
		''),
    array(
		t('yii','jqGrid4/css'),
		true,
		is_dir('jqGrid4/css'),
		'NRC',
		''),
    array(
		t('yii','jqGrid4/js'),
		true,
		is_dir('jqGrid4/js'),
		'NRC',
		''),
    array(
		t('yii','protected'),
		true,
		is_dir('protected'),
		'NRC',
		''),
    array(
		t('yii','protected/db'),
		true,
		is_dir('protected/db'),
		'NRC',
		''),
    array(
		t('yii','protected/widgets'),
		true,
		is_dir('protected/widgets'),
		'NRC',
		''),
    array(
		t('yii','protected/components'),
		true,
		is_dir('protected/components'),
		'NRC',
		''),
    array(
		t('yii','protected/wsapi'),
		true,
		is_dir('protected/wsapi'),
		'NRC',
		''),
    array(
		t('yii','protected/views'),
		true,
		is_dir('protected/views'),
		'NRC',
		''),
    array(
		t('yii','protected/tests'),
		true,
		is_dir('protected/tests'),
		'NRC',
		''),
    array(
		t('yii','protected/controllers'),
		true,
		is_dir('protected/controllers'),
		'NRC',
		''),	
    array(
		t('yii','protected/models'),
		true,
		is_dir('protected/models'),
		'NRC',
		''),
    array(
		t('yii','protected/runtime'),
		true,
		is_dir('protected/runtime'),
		'NRC',
		''),
    array(
		t('yii','protected/config'),
		true,
		is_dir('protected/config'),
		'NRC',
		''),
    array(
		t('yii','superfish'),
		true,
		is_dir('superfish'),
		'NRC',
		''),
    array(
		t('yii','themes'),
		true,
		is_dir('themes'),
		'NRC',
		''),
    array(
		t('yii','themes/classic'),
		true,
		is_dir('themes/classic'),
		'NRC',
		''),
    array(
		t('yii','yii'),
		true,
		is_dir('yii'),
		'Framework Yii',
		''),
    array(
		t('yii','yii/framework'),
		true,
		is_dir('yii/framework'),
		'Framework Yii',
		''),
);


$directoriesAreWritable=array(
    array(
		t('yii','assets'),
		true,
		is_dir('assets'),
		'NRC',
		''),
    array(
		t('yii','protected/runtime'),
		true,
		is_dir('protected/runtime'),
		'NRC',
		''),
    array(
		t('yii','protected/runtime/cache'),
		true,
		is_dir('protected/runtime/cache'),
		'NRC',
		''),
);

$wsapiFilesExists=array(
    array(
		t('yii','CRSAuthenticationService.php'),
		true,
		is_file('./../wsapi/wsdl_from_svc/CRSAuthenticationService.php'),
		'NRC',
		''),
    array(
		t('yii','CRSCommonAppService.php'),
		true,
		is_file('./../wsapi/wsdl_from_svc/CRSCommonAppService.php'),
		'NRC',
		''),
    array(
		t('yii','CRSDomainAppService.php'),
		true,
		is_file('./../wsapi/wsdl_from_svc/CRSDomainAppService.php'),
		'NRC',
		''),
   array(
		t('yii','CRSNicHandleAppService.php'),
		true,
		is_file('./../wsapi/wsdl_from_svc/CRSNicHandleAppService.php'),
		'NRC',
		''),
   array(
		t('yii','CRSPaymentAppService.php'),
		true,
		is_file('./../wsapi/wsdl_from_svc/CRSPaymentAppService.php'),
		'NRC',
		''),
   array(
		t('yii','CRSPermissionsAppService.php'),
		true,
		is_file('./../wsapi/wsdl_from_svc/CRSPermissionsAppService.php'),
		'NRC',
		''),
   array(
		t('yii','CRSResellerAppService.php'),
		true,
		is_file('./../wsapi/wsdl_from_svc/CRSResellerAppService.php'),
		'NRC',
		''),
   array(
		t('yii','CRSTicketAppService.php'),
		true,
		is_file('./../wsapi/wsdl_from_svc/CRSTicketAppService.php'),
		'NRC',
		''),
);

function getFileSize($path) {
   $size = 0;
   $size = @filesize($path);
   return $size;
}

$wsapiFilesSizes=array(
    array(
		t('yii','CRSAuthenticationService.php'),
		true,
		getFileSize('./../wsapi/wsdl_from_svc/CRSAuthenticationService.php'),
		'NRC',
		''),
    array(
		t('yii','CRSCommonAppService.php'),
		true,
		getFileSize('./../wsapi/wsdl_from_svc/CRSCommonAppService.php'),
		'NRC',
		''),
    array(
		t('yii','CRSDomainAppService.php'),
		true,
		getFileSize('./../wsapi/wsdl_from_svc/CRSDomainAppService.php'),
		'NRC',
		''),
   array(
		t('yii','CRSNicHandleAppService.php'),
		true,
		getFileSize('./../wsapi/wsdl_from_svc/CRSNicHandleAppService.php'),
		'NRC',
		''),
   array(
		t('yii','CRSPaymentAppService.php'),
		true,
		getFileSize('./../wsapi/wsdl_from_svc/CRSPaymentAppService.php'),
		'NRC',
		''),
   array(
		t('yii','CRSPermissionsAppService.php'),
		true,
		getFileSize('./../wsapi/wsdl_from_svc/CRSPermissionsAppService.php'),
		'NRC',
		''),
   array(
		t('yii','CRSResellerAppService.php'),
		true,
		getFileSize('./../wsapi/wsdl_from_svc/CRSResellerAppService.php'),
		'NRC',
		''),
   array(
		t('yii','CRSTicketAppService.php'),
		true,
		getFileSize('./../wsapi/wsdl_from_svc/CRSTicketAppService.php'),
		'NRC',
		''),
);


$yiiCoreFile=array(
   array(
    t('yii','yii.php'),
	 true,
	 is_file('yii/framework/yii.php'),
	 'NRC',
	 ''),
);

function getUrl() {
   $url = array();
   $fileContent = explode("\n",file_get_contents('./protected/config/main.php'));
   for($i=0;$i<count($fileContent);$i++) {
      if(preg_match('/wsapi_soap_url/', $fileContent[$i])) {
         $url = split('=>', $fileContent[$i]);
         break;
      }
   }

   $url[1] = str_replace(',', '', $url[1]);
   $url[1] = str_replace('\'', '', $url[1]);
   
   return $url[1];
}

function getExampleWsdl($url) {
   $handle = curl_init($url);
   curl_setopt($handle,  CURLOPT_RETURNTRANSFER, TRUE);
   $response = curl_exec($handle);
   $httpCode = curl_getinfo($handle, CURLINFO_HTTP_CODE);
   curl_close($handle);
   return $httpCode;
}


function getAddressAndPortFromUrl($url, &$address, &$port , &$backMessage) {
   $whitoutProtocol = split('//', $url);
   if(count($whitoutProtocol) != 2) {
      $backMessage = 'Could`t get address without protocol from wsapi soap url : '.$url;
      return false;
   }

   $whitoutSubdirs = split('/', $whitoutProtocol[1]);
   if(count($whitoutSubdirs) != 3) {
      $backMessage = 'Could`t get subdirs from : '.$whitoutProtocol[1];
      return false;
   }

   $addressAndPort = split(':', $whitoutSubdirs[0]);
   if(count($addressAndPort) != 2) {
      $backMessage = 'Could`t get address and port from : '.$whitoutSubdirs[0];
      return false;
   }

   $address = $addressAndPort[0];
   $port = $addressAndPort[1];

   return true;
}

function connectToTomcat($url, &$backMessage) {
   $address = '';
   $port = '';
   if(!getAddressAndPortFromUrl($url,$address ,$port, $backMessage)) 
        return false;
   
   $socket = @socket_create(AF_INET, SOCK_STREAM, 0);
   if ($socket) {
      $isConnect = @socket_connect($socket, $address, $port);
      if($isConnect === false) {
          $backMessage='Could`t connect to Tomcat Server on address '.$address.':'.$port.'';
          return false;
      } else {
          @socket_close($socket);
          $backMessage='Connect to Tomcat successful on address '.$address.':'.$port.'';
          return true;
      }
   }
   $backMessage='Connect to Tomcat not successful on address '.$address.':'.$port.'';
   return false;
}

$url = getUrl();
$isConnectMessage='';
$isConnect = connectToTomcat($url,$isConnectMessage);
$returnHttp = (getExampleWsdl($url.='CRSAuthenticationService?wsdl')=='200') ? true : false;
if($returnHttp) {
   $msg = 'Get '.($url.='CRSAuthenticationService?wsdl').'';
} else {
   $msg = 'Could`t get '.($url.='CRSAuthenticationService?wsdl').'';
}

$connectToTomcat=array(
   array(
    t('yii','Connection'),
	 true,
	 $isConnect,
	 'NRC',
	 ''.$isConnectMessage),
   array(
    t('yii','Get example wsdl'),
	 true,
	 $returnHttp,
	 'NRC',
	 $msg),
);


function checkServerVar()
{
	$vars=array('HTTP_HOST','SERVER_NAME','SERVER_PORT','SCRIPT_NAME','SCRIPT_FILENAME','PHP_SELF','HTTP_ACCEPT','HTTP_USER_AGENT');
	$missing=array();
	foreach($vars as $var)
	{
		if(!isset($_SERVER[$var]))
			$missing[]=$var;
	}
	if(!empty($missing))
		return t('yii','$_SERVER does not have {vars}.',array('{vars}'=>implode(', ',$missing)));

	if(realpath($_SERVER["SCRIPT_FILENAME"]) !== realpath(__FILE__))
		return t('yii','$_SERVER["SCRIPT_FILENAME"] must be the same as the entry script file path.');

	if(!isset($_SERVER["REQUEST_URI"]) && isset($_SERVER["QUERY_STRING"]))
		return t('yii','Either $_SERVER["REQUEST_URI"] or $_SERVER["QUERY_STRING"] must exist.');

	if(!isset($_SERVER["PATH_INFO"]) && strpos($_SERVER["PHP_SELF"],$_SERVER["SCRIPT_NAME"]) !== 0)
		return t('yii','Unable to determine URL path info. Please make sure $_SERVER["PATH_INFO"] (or $_SERVER["PHP_SELF"] and $_SERVER["SCRIPT_NAME"]) contains proper value.');

	return '';
}

function checkGD()
{
	if(extension_loaded('gd'))
	{
		$gdinfo=gd_info();
		if($gdinfo['FreeType Support'])
			return '';
		return t('yii','GD installed<br />FreeType support not installed');
	}
	return t('yii','GD not installed');
}

function getYiiVersion()
{
	$coreFile=dirname(__FILE__).'/../framework/YiiBase.php';
	if(is_file($coreFile))
	{
		$contents=file_get_contents($coreFile);
		$matches=array();
		if(preg_match('/public static function getVersion.*?return \'(.*?)\'/ms',$contents,$matches) > 0)
			return $matches[1];
	}
	return '';
}

function t($category,$message,$params=array())
{
	static $messages;

	if($messages === null)
	{
		$messages=array();
		if(($lang=getPreferredLanguage()) !== false)
		{
			$file=dirname(__FILE__)."/messages/$lang/yii.php";
			if(is_file($file))
				$messages=include($file);
		}
	}

	if(empty($message))
		return $message;

	if(isset($messages[$message]) && $messages[$message] !== '')
		$message=$messages[$message];

	return $params !== array() ? strtr($message,$params) : $message;
}

function getPreferredLanguage()
{
	if(isset($_SERVER['HTTP_ACCEPT_LANGUAGE']) && ($n=preg_match_all('/([\w\-]+)\s*(;\s*q\s*=\s*(\d*\.\d*))?/',$_SERVER['HTTP_ACCEPT_LANGUAGE'],$matches)) > 0)
	{
		$languages=array();
		for($i=0; $i < $n; ++$i)
			$languages[$matches[1][$i]]=empty($matches[3][$i]) ? 1.0 : floatval($matches[3][$i]);
		arsort($languages);
		foreach($languages as $language=>$pref)
			return strtolower(str_replace('-','_',$language));
	}
	return false;
}

$result=1;  // 1: all pass, 0: fail, -1: pass with warnings
$resultArray=array();
for($i=0;$i<6;$i++) {
   $resultArray[$i] = 1;
}

function setRequires(&$currentArray,&$resultArray,$index) {
   foreach($currentArray as $i=>$requirement) {
      if($requirement[1] && !$requirement[2]) {
         $result=0;
         $resultArray[$index] = 0;
      } else if($resultArray[$index] > 0 && !$requirement[1] && !$requirement[2]) {
         $result=-1;
         $resultArray[$index]=-1;
      }
      if($requirement[4] === '')
         $currentArray[$i][4]='&nbsp;';
   }
}

function getWarningAndErrors($resultArray) {
   for($i=0;$i<6;$i++) {
      if($resultArray[$i] == 0){
         return 0; /*max requires*/
      } else if($resultArray[$i] == -1) {
         return -1; /*min requires*/
      }
   }
   return 1; /*all requires*/ 
}

setRequires($libs, $resultArray , 0);
setRequires($directoriesAreAccessible, $resultArray , 1);
setRequires($wsapiFilesExists, $resultArray , 2);
setRequires($yiiCoreFile, $resultArray , 3);
setRequires($connectToTomcat, $resultArray , 4);
setRequires($wsapiFilesSizes, $resultArray , 5);

?>


<?php
      class ClassMethod {
         private $class;
         private $method;
         private $commented;
         
         public function __construct($class, $method, $commented) {
            $this->class = $class;
            $this->method = $method;
            $this->commented = $commented;
         }
         public function setClass($class) { 
            $this->class = $class;
         }
         public function setMethod($method) {
            $this->method = $method;
         }
         public function setCommented($commented) {
            $this->commented = $commented;
         }
         public function getClass() { 
            return $this->class;
         }
         public function getMethod() {
            return $this->method;
         }
         public function getCommented() {
            return $this->commented;
         }
      }

      function isExist($classname, $method, $commented) {
         if(strlen($classname)  && strlen($method) && $commented==true) {
            if(method_exists($classname, $method)) 
               return true;
         }
         return false;
      }
      
      function getClassMethod($content, &$class, &$method, &$commented) { 
           $begin = strpos($content, 'CRS', 0); 
           if($begin === false) {
               return false;
           }
           
           $yiiLog = strpos($content, 'Yii', 0);
           if($yiiLog !== false) {
              if($yiiLog < $begin)
               return false;
           }
          
           /*If comment is before function*/
           $comment = strpos($content, '//', 0); 
           if($comment !== false) {
              if($comment < $begin) {
                 $commented = false;
              }
           }
           
           $end = strpos($content, '(', 0); 
           if($end === false) {
               return false;
           }
           $result = substr($content, $begin, $end - $begin);
           if($result === false) {
               return false;
           }
           
           $tmp = array();
           
           $tmp = split('::',$result);
           if(count($tmp) == 1) {
            $class = trim($tmp[0]);
           }
           if(count($tmp) == 2) {
            $class = trim($tmp[0]);
            $method = trim($tmp[1]);
           }
           return true;
      }
     
      function cmp($one, $two) {
         return strcmp($one->getClass(), $two->getClass());
      }
      
      function find($a, $class, $method) {
         foreach($a as $o) {
            if($o->getClass() == $class)
               if($o->getMethod() == $method)
                  return true;
         }
         return false;
      }
      
      function getClassAndMethods(&$wsapiMethods, &$container) {
         $path = '/opt/crs/console/www/protected';
         $logFile = '/var/log/iedr/console/methods.log';
         $methods = array();
         
         system('find '.$path.'/models -name "*.php" -exec grep \'CRS.*::[a-z]\' {} \; > '.$logFile.' ');
         system('find '.$path.'/components -name "*.php" -exec grep \'CRS.*::[a-z]\' {} \; >> '.$logFile.' ');
         system('find '.$path.'/controllers -name "*.php" -exec grep \'CRS.*::[a-z]\' {} \; >> '.$logFile.' ');
         system('find '.$path.'/db -name "*.php" -exec grep \'CRS.*::[a-z]\' {} \; >> '.$logFile.' ');
         system('find '.$path.'/views -name "*.php" -exec grep \'CRS.*::[a-z]\' {} \; >> '.$logFile.' ');
       
         $handle = @fopen($logFile, "r");
         if ($handle) {
             while (($buffer = fgets($handle)) !== false) {
                 if(!in_array($buffer, $methods)) {
                     $tmpBuffer = split(";",$buffer);
                     if(count($tmpBuffer) == 1) {
                        array_push($methods, $tmpBuffer[0]);
                     } else if(count($tmpBuffer) == 2) {
                        array_push($methods, $tmpBuffer[0]);
                        array_push($methods, $tmpBuffer[1]);
                     } else {
                        array_push($methods, $buffer);
                     }
                  }
             }
             if (!feof($handle))
                 echo "Error: unexpected fgets() fail\n";
             fclose($handle);
         }
         
         system('rm -rf '.$logFile.' ');    
         
         /*Check all method from NRC*/
         foreach($methods as $classMethod) {
            $class = '';
            $method = '';
            $commented = true;
            if(getClassMethod($classMethod, $class, $method, $commented))
               if(strlen($class)  && strlen($method)) {
                  if(!find($container, $class, $method)) {
                     array_push($container, new ClassMethod($class, $method, $commented));
                  }
               }
         } 
      }
      
      $container = array();
      $wsapiMethods = array();
      
      getClassAndMethods($wsapiMethods, $container);
      usort($container, "cmp");
      
      foreach ($container as $c) {
       array_push($wsapiMethods, array(
             t('nrc', $c->getClass()."::".$c->getMethod()),
             $c->getCommented(),
             isExist($c->getClass(), $c->getMethod(), $c->getCommented()),
             '',
             ''
        ));
      }
      
      function getSoap() {
         $url = getUrl();
         $url.='CRSInfo?wsdl';
         return $url;
      }
      
      function getWsapiVersion(&$result) {
         try {
            $client = new SoapClient(getUrl() . 'CRSInfo?wsdl', array(
                        'soap_version' => SOAP_1_1,
                        'exceptions' => true,
                        'trace' => 1,
                        'cache_wsdl' => WSDL_CACHE_MEMORY
                    ));
            
            $response = $client->getVersionInfo(array());
            
            if (is_object($response))
               if(property_exists($response, 'return'))
                  $result = $response->return;
            
         } catch (Exception $e) {
         }
         return $response == null ? false : true;
      }
      
?>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<meta http-equiv="content-language" content="en"/>
<link rel="stylesheet" type="text/css" href="/yii/requirements/css/main.css" />
<title>NRC Requirement</title>
</head>

<body>
<div id="page">

<div id="header">
<h1>NRC Requirement Checker</h1>
</div><!-- header-->
<div id="content">
   
<h2>Conclusion</h2>
<p>
<?php $result = getWarningAndErrors($resultArray); ?>
<?php if($result>0): ?>
Congratulations! Your server configuration satisfies all requirements.
<?php elseif($result<0): ?>
Your server configuration satisfies the minimum requirements.
<?php else: ?>
Unfortunately your server configuration does not satisfy.
<?php endif; ?>
</p>

<h2>Details</h2>

<table class="result">
<tr><th>Name</th><th>Result</th><th>Required By</th><th>Memo</th></tr>
<tr><th colspan="4">Libraries</th></tr>
<?php foreach($libs as $requirement): ?>
<tr>
	<td>
	<?php echo $requirement[0]; ?>
	</td>
	<td class="<?php echo $requirement[2] ? 'passed' : ($requirement[1] ? 'failed' : 'warning'); ?>">
	<?php echo $requirement[2] ? 'Passed' : ($requirement[1] ? 'Failed' : 'Warning'); ?>
	</td>
	<td>
	<?php echo $requirement[3]; ?>
	</td>
	<td>
	<?php echo $requirement[4]; ?>
	</td>
</tr>
<?php endforeach; ?>

<tr><th colspan="4">Directories accessible</th></tr>
<?php foreach($directoriesAreAccessible as $requirement): ?>
<tr>
	<td>
	<?php echo $requirement[0]; ?>
	</td>
	<td class="<?php echo $requirement[2] ? 'passed' : ($requirement[1] ? 'failed' : 'warning'); ?>">
	<?php echo $requirement[2] ? 'Passed' : ($requirement[1] ? 'Failed' : 'Warning'); ?>
	</td>
	<td>
	<?php echo $requirement[3]; ?>
	</td>
	<td>
	<?php echo $requirement[4]; ?>
	</td>
</tr>
<?php endforeach; ?>


<tr><th colspan="4">Directories writable</th></tr>
<?php foreach($directoriesAreWritable as $requirement): ?>
<tr>
	<td>
	<?php echo $requirement[0]; ?>
	</td>
	<td class="<?php echo $requirement[2] ? 'passed' : ($requirement[1] ? 'failed' : 'warning'); ?>">
	<?php echo $requirement[2] ? 'Passed' : ($requirement[1] ? 'Failed' : 'Warning'); ?>
	</td>
	<td>
	<?php echo $requirement[3]; ?>
	</td>
	<td>
	<?php echo $requirement[4]; ?>
	</td>
</tr>
<?php endforeach; ?>

<tr>
<th colspan="4">Wsapi version :
<?php 
   $result = null;
   if(getWsapiVersion($result)) {
      echo "$result->formattedVersion<br>" ;
   } else {
      echo "Undefined version";
   }
?>   
</th>
</tr>

<tr><th colspan="4">WSAPI files exist</th></tr>
<?php foreach($wsapiFilesExists as $requirement): ?>
<tr>
	<td>
	<?php echo $requirement[0]; ?>
	</td>
	<td class="<?php echo $requirement[2] ? 'passed' : ($requirement[1] ? 'failed' : 'warning'); ?>">
	<?php echo $requirement[2] ? 'Passed' : ($requirement[1] ? 'Failed' : 'Warning'); ?>
	</td>
	<td>
	<?php echo $requirement[3]; ?>
	</td>
	<td>
	<?php echo $requirement[4]; ?>
	</td>
</tr>
<?php endforeach; ?>


<tr><th colspan="4">WSAPI files size</th></tr>
<?php foreach($wsapiFilesSizes as $requirement): ?>
<tr>
	<td>
	<?php echo $requirement[0]; ?>
	</td>
	<td class="<?php echo $requirement[2] ? 'passed' : ($requirement[1] ? 'failed' : 'warning'); ?>">
	<?php echo $requirement[2] ? 'Passed' : ($requirement[1] ? 'Failed' : 'Warning'); ?>
	</td>
	<td>
	<?php echo $requirement[3]; ?>
	</td>
	<td>
	<?php echo $requirement[4]; ?>
	</td>
</tr>
<?php endforeach; ?>


<tr><th colspan="4">Yii Core file</th></tr>
<?php foreach($yiiCoreFile as $requirement): ?>
<tr>
	<td>
	<?php echo $requirement[0]; ?>
	</td>
	<td class="<?php echo $requirement[2] ? 'passed' : ($requirement[1] ? 'failed' : 'warning'); ?>">
	<?php echo $requirement[2] ? 'Passed' : ($requirement[1] ? 'Failed' : 'Warning'); ?>
	</td>
	<td>
	<?php echo $requirement[3]; ?>
	</td>
	<td>
	<?php echo $requirement[4]; ?>
	</td>
</tr>
<?php endforeach; ?>


<tr><th colspan="4">Tomcat setup</th></tr>
<?php foreach($connectToTomcat as $requirement): ?>
<tr>
	<td>
	<?php echo $requirement[0]; ?>
	</td>
	<td class="<?php echo $requirement[2] ? 'passed' : ($requirement[1] ? 'failed' : 'warning'); ?>">
	<?php echo $requirement[2] ? 'Passed' : ($requirement[1] ? 'Failed' : 'Warning'); ?>
	</td>
	<td>
	<?php echo $requirement[3]; ?>
	</td>
	<td>
	<?php echo $requirement[4]; ?>
	</td>
</tr>
<?php endforeach; ?>


<tr><th colspan="4">Compatible wsapi method (Failed when used in NRC but not generated from wsdl)</th></tr>
<?php foreach($wsapiMethods as $requirement): ?>
<tr>
	<td>
	<?php echo $requirement[0]; ?>
	</td>
	<td class="<?php echo $requirement[2] ? 'passed' : ($requirement[1] ? 'failed' : 'warning'); ?>">
	<?php echo $requirement[2] ? 'Passed' : ($requirement[1] ? 'Failed' : 'Warning'); ?>
	</td>
	<td>
	<?php echo $requirement[3]; ?>
	</td>
	<td>
	<?php echo $requirement[4]; ?>
	</td>
</tr>
<?php endforeach; ?>

</table>


<table>
<tr>
<td class="passed">&nbsp;</td><td>passed</td>
<td class="failed">&nbsp;</td><td>failed</td>
<td class="warning">&nbsp;</td><td>warning</td>
</tr>
</table>

</div><!-- content -->
</div><!-- page -->
</body>
</html>
