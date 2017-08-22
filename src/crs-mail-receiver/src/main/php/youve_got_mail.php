<?php

error_reporting(E_ALL);

require_once('MimeMailParser.class.php');

$VERSION = "1.0";
$HELP = <<<EOH
USAGE:
  $argv[0] <options>

DESCRIPTION:
Script reads a single email message from STDIN, saves attachments to a predefined
folder and messages java web service endpoint with email message and attachment
filenames to let java handle the email

OPTIONS:
  -u=username
    *required* username to log into web service. If none passed script will check
    env for variable \$UPLOADER_USERNAME.
  -p=password
    *required* password to log into web service. If none passed script will check
    env for variable \$UPLOADER_PASSWORD.
  -h=host
    *required* host of webservices. If none passed script will check env for
    variable \$UPLOADER_HOST.
  -d=dir
    *required* directory to which attachments should be saved. If none passed script
    will check env for variable \$UPLOADER_DIR.
  --help
    display this help
  --version
    display version info

EOH;

function print_help() {
  global $HELP;
  fwrite(STDOUT,$HELP);
}

function print_version() {
  global $VERSION;
  fwrite(STDOUT, "Version $VERSION\n");
}

$opts = getopt('u:p:h:d:', array('help', 'version'));

if (isset($opts['help'])) {
  print_help();
  exit(0);
}
if (isset($opts['version'])) {
  print_version();
  exit(0);
}

$DEFAULT_USERNAME = getenv('UPLOADER_USERNAME');
$DEFAULT_PASSWORD = getenv('UPLOADER_PASSWORD');
$DEFAULT_HOST     = getenv('UPLOADER_HOST');
$DEFAULT_DIR      = getenv('UPLOADER_DIR');

$user = isset($opts['u']) ? $opts['u'] : $DEFAULT_USERNAME;
$pass = isset($opts['p']) ? $opts['p'] : $DEFAULT_PASSWORD;
$host = isset($opts['h']) ? $opts['h'] : $DEFAULT_HOST;
$dir  = isset($opts['d']) ? $opts['d'] : $DEFAULT_DIR;

function logError($message) {
  $ext = extension_loaded('posix');
  if (!$ext || posix_isatty(STDERR)) {
    fwrite(STDERR, $message . "\n");
    fflush(STDERR);
  } else if (!$ext) {
    syslog(LOG_ERR, $message);
  }
}

function check($varname) {
  global $$varname;
  if (empty($$varname)) {
    logError("Missing $varname");
    return true;
  }
  return false;
}

function getEmailAddress($string) {
  foreach(preg_split('/\s/', $string) as $part) {
    $part = trim($part, '<>'); // sometime emails are presented e.g. Kuba Bogaczewicz <jb@rdprojekt.pl>
    if (filter_var($part, FILTER_VALIDATE_EMAIL)) {
      return $part;
    }
  }
  return null;
}

$errors_count = 0;
$errors_count += check('user');
$errors_count += check('pass');
$errors_count += check('host');
$errors_count += check('dir');
if ($errors_count) {
  if ($errors_count == 4) {
    print_help();
    exit(0);
  } else {
    exit(1);
  }
}

$authUser = NULL;
try {
  $AuthWS = new SoapClient($host.'/crs-web-services/CRSAuthenticationService?wsdl');
  $userWS = $AuthWS->authenticate(array(
    'username' => $user,
    'password' => $pass,
    'remoteAddress' =>'127.0.0.1',
    'pin' => 0,
  ));
  $authUser = $userWS->return;
} catch (Exception $e) {
  logError("Error while authenticating: " . $e->getMessage() . "\n");
  exit(1);
}

$Parser = new MimeMailParser();
$Parser->setStream(STDIN);

$from = $Parser->getHeader('from');
$subject = $Parser->getHeader('subject');
$replyTo = $Parser->getHeader('reply-to');
$replyTo = empty($replyTo) ? $from : $replyTo;
$text = $Parser->getMessageBody('text');
$attachments = $Parser->getAttachments();
$replyAddress = getEmailAddress($replyTo);
$fromAddress = getEmailAddress($from);

if (empty($replyAddress)) {
  logError("Unknown return address, looked for in reply-to and from headers. Aborting");
  exit(1);
}

$files = array();
foreach ($attachments as $attachment) {
  if ($attachment->getContentDisposition() != 'attachment')
    continue;
  $content = $attachment->getContent();
  $sha = substr(sha1($content), 0, 6);
  $source = str_replace('@', '.', $fromAddress);
  $orig_filename = preg_replace('[^\w\s\d._\(\)-]', '', pathinfo($attachment->getFilename(), PATHINFO_FILENAME));
  $uploadFilename = implode('_', array($source, $orig_filename, $sha));
  $ext = $attachment->getFileExtension();
  $filename = $uploadFilename . '.' . $ext;
  $i = 0;
  while(file_exists($dir . '/' . $filename)) {
    $filename = $uploadFilename . '-' . ++$i . '.' . $ext;
  }
  file_put_contents($dir . '/' . $filename, $content);
  chmod($dir . '/' . $filename, 0644);
  $uploadFilename = new stdClass();
  $uploadFilename->filesystemName = $filename;
  $uploadFilename->userFilename = $attachment->getFilename();
  array_push($files, $uploadFilename);
}

try {
  $DocsWS = new SoapClient($host.'/crs-web-services/CRSDocumentAppService?wsdl');
  $DocsWS->handleMailUpload(array(
    'user'    => $authUser,
    'replyTo' => $replyAddress,
    'content' => $text,
    'attachments' => $files));
} catch (Exception $e) {
  logError("Error while sending documents " . $e->getMessage() . "\n");
  exit(1);
}

