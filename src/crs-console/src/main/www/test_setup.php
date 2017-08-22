<html>
<body>
<ul>
<li>This is HTML : Apache httpd works.</li>
<li>PHP works ? = '<?php echo "YES" ; ?>' (should say 'YES')</li>
<?php
define('BR',"\n");
date_default_timezone_set('Europe/Dublin');
session_name("NEW_REGISTRARS_CONSOLE");
function reallyflush() {
	echo(str_repeat(' ',256))."\n";
	// check that buffer is actually set before flushing
	@flush();
	if (ob_get_length()){            
		@ob_flush();
		@flush();
		@ob_end_flush();
		}    
	@ob_start();
	@flush();
	}

$config='protected/config/main.php';

###################################

echo '<li>PHP Version = '.phpversion().'</li>';
echo '<li>PHP Server Date/Time = '.date('Y-m-d H:i:s',time()).'</li>';

reallyflush();

###################################

echo '<li>Check if suhosin lets enough data through ? ...</li>'."\n";
reallyflush();

# ensure suhosin lets enough data through
$maxgetsize=8192;
if(in_array('suhosin',get_loaded_extensions()) and ini_get('suhosin.get.max_value_length') < $maxgetsize)
	{
	echo '<li><h1>Please update the server "php.ini" config file, add a line "suhosin.get.max_value_length='.$maxgetsize.'"</h1></li>';
	}
echo '<li>.. OK</li>';

###################################

echo '<li>Check if directories are accessible ? ...</li>'."\n";
reallyflush();

$dirlist = array(
	'yii','yii/framework','css','html','protected','protected/db','protected/widgets','protected/components',
	'protected/wsapi','protected/views','protected/tests','protected/controllers','protected/models',
	'protected/runtime','protected/config','superfish','jqGrid','jqGrid/themes','jqGrid/js','themes',
	'themes/classic','files','files/docs','files/api','images','assets'
	);
$okdirs = 0;
foreach($dirlist as $d)
	{
	if(!is_dir($d))
		echo '<li>Is Directory "'.$d.'" NOT Accessible : ERROR!!</li>'."\n";
	else
		++$okdirs;
	}
echo '<li> .. '.$okdirs.' Directories out of '.count($dirlist).' Accessible OK</li>'."\n";

reallyflush();

###################################

$write_dirlist = array('protected/runtime','protected/runtime/cache','assets');
foreach($write_dirlist as $d)
	{
	if(!is_dir($d))
		echo '<li>Directory "'.$d.'" NOT Writeable : ERROR!!</li>'."\n";
	else
		echo '<li>Directory "'.$d.'" Is Writeable : OK</li>'."\n";
	}

reallyflush();

###################################

echo '<li>Try including various files ..</li>'."\n";
reallyflush();
	require_once('protected/wsapi/wsapi_base.php');
	require_once('protected/wsapi/CRSAuthenticationService.php');
	require_once('protected/wsapi/CRSDomainAppService.php');
	require_once('protected/wsapi/CRSNicHandleAppService.php');
	require_once('protected/wsapi/CRSPaymentAppService.php');
	require_once('protected/wsapi/CRSPermissionsAppService.php');
	require_once('protected/wsapi/CRSResellerAppService.php');
	require_once('protected/wsapi/CRSTicketAppService.php');
	require_once('protected/db/class_category.php');
	require_once('protected/db/domain_prices.php');
	require_once('protected/db/short_account_stats.php');
	require_once('protected/db/country_county.php');
	require_once('protected/components/menu.php');
	require_once('yii/framework/yii.php');
echo '<li>.. OK</li>'."\n";

reallyflush();

###################################

echo '<li>Try Creating YII Application Object ..</li>'."\n";
reallyflush();
	if(!is_file($config))
		echo '<li>Cant open yii config file "'.$config.'" ! ERROR!!</li>'."\n";
	else
		{
		$webapp = Yii::createWebApplication($config);
		#$webapp->run(); // this will display main web app page
		}

echo '<li>.. OK</li>'."\n";

reallyflush();

###################################

echo '<li>Checking PHP Extensions ..</li>'."\n";
reallyflush();

$needed_exts = array('curl','gd','json','libxml','mysql','mysqli','pcre','shmop',
	'soap','sysvmsg','sysvsem','sysvshm','xmlrpc','xsl','zip','zlib');

$loaded_exts = array_flip(get_loaded_extensions());

$exts_ok = 0;
foreach($needed_exts as $e)
	{
	if(isset($loaded_exts[$e]))
		++$exts_ok;
	else
		echo '<li>Extension "'.$e.'" NOT IN PHP ! ERROR!!</li>'."\n";
	}

echo '<li>.. '.$exts_ok.' out of '.count($needed_exts).' are loaded OK</li>'."\n";

reallyflush();

###################################

$_CDbConnection = Yii::app()->getComponent('db');
$connstr = $_CDbConnection->connectionString;

echo '<li>Try Opening Database Connection  = "'.$connstr.'"</li>'."\n";
reallyflush();

	$d = DB::get_db();
	if($d == null)
		echo '<li>DB connection FAILED! ERROR!!</li>'."\n";
	else
		{
		$d->query('select now() as u;',__FILE__,__LINE__);
		if($r = $d->fetch())
			echo '<li> .. OK ; Date/Time on Database "'.$r->u.'"</li>'."\n";
		else
			echo '<li> .. Fetch FAILED! ERROR!!</li>'."\n";
		}
reallyflush();

###################################

// e.g. http://127.0.0.1:8080/crs-web-services-0.9.7/
$url = Yii::app()->params['wsapi_soap_url'];
$url .= 'CRSAuthenticationService?wsdl';
echo '<li>Try Connection to CRS-WS-API SOAP Server : URL = <a href="'.$url.'">'.$url.'</a>..</li>'."\n";
reallyflush();
$handle = curl_init($url);
curl_setopt($handle,  CURLOPT_RETURNTRANSFER, TRUE);
$response = curl_exec($handle);
$httpCode = curl_getinfo($handle, CURLINFO_HTTP_CODE);
curl_close($handle);
echo '<li> .. HTTP return code = '.($httpCode=='200' ? 'OK' : $httpCode).'</li>'."\n";
reallyflush();

###################################

#check if the ckdns exists and is executable
reallyflush();
$ckdnsURL = Yii::app()->params['ckdnsPath'];
echo "<li> Check if CKDNS exists in $ckdnsURL.</li>\n";
if(! file_exists ( $ckdnsURL )){ echo '<li> .. ERROR : Ckdns does not exist please contact asd@iedr.ie </li>'."\n"; }else{ echo '<li> .. OK.</li>'."\n"; }
echo '<li> Check if CKDNS is executable.</li>'."\n";
if(! is_executable ( $ckdnsURL )){ echo '<li> .. ERROR : Ckdns is not executable please contact asd@iedr.ie </li>'."\n"; }else{ echo '<li> .. OK.</li>'."\n"; }
reallyflush();

?>
<ul>
</body>
</html>

