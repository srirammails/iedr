<?php

/**
 * defines Utility class
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
 * container for various unrelated miscellaneous static functions
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
class Utility
{

    /**
     * round a floating-point number to two decimal places according to IEDR business rules 'half even'
     *
     * Dermot : "below half a penny, you round down.  Above half a penny, you round up.  At exactly half a penny, you round to the nearest even amount so 76.5p becomes 76p and 77.5p becomes 78, etc."
     *
     * @param  unknown $f decimal value
     * @return integer rounded number
     * @access public
     * @static
     */
    static public function roundAccountingAmount($f)
    {
        return round($f, 2, PHP_ROUND_HALF_EVEN);
    }

    /**
     * render a currency amount in html format
     *
     * prepends currency symbol, adds thousands separator
     *
     * @param  unknown $m    currency amount
     * @param  string  $curr currency, defaults to 'EUR'
     * @return mixed   html representation of amount
     * @access public
     * @static
     */
    static public function currencyAmount($m, $curr = 'EUR')
    {
        if ($m == null) return 0;
        // use : Utility::currencyAmount($x);
        $csym = '&euro;';
        switch ($curr) {
            case 'USD':
                $csym = '$';
                break;
            case 'GBP':
                $csym = '£';
                break;
            default:
            case 'EUR':
                break;
        }
        return $csym . '&nbsp;' . number_format($m, /*decimals*/
            2, /*dec_point*/
            '.', /*thousands*/
            ',');
    }

    /**
     * returns string representing a date, in YYYY-MM-DD format, being the last calendar day of the current month
     *
     * @return string last day of current month
     * @access public
     * @static
     */
    static public function lastDayOfCurrentMonth()
    {
        return date('Y-m-t', time());
    }

    /**
     * returns a string being the payment-due-date of the current invoice period
     *
     * currently defined as the last calendar day of the present month
     *
     * @return string payment-due-date...
     * @access public
     * @static
     */
    static public function paymentDueByDate()
    {
        return Utility::lastDayOfCurrentMonth();
    }

    static public function isTransferPossible($domainName)
    {
        $user = Yii::app()->user->authenticatedUser;
        $result = null;
        $errs = '';
        CRSCommonAppService_service::isTransferPossible($result, $errs, $user, $domainName);
        if ($result == null || count($errs)) {
            Yii::log('Transfer not possible');
            return false;
        }
        return true;
    }


    /**
     * find whether a domain is available for registration, checking if it exists and has no pending creation ticket
     *
     * @param  string $domainName domain name
     * @return boolean true if the domain can be created
     * @access public
     * @static
     */
    static public function domainIsAvailable($domainName)
    {
        $errors = '';
        $result = new CRSDomainAppService_domainAvailabilityVO();
        CRSDomainAppService_service::checkAvailability($result, $errors, Yii::app()->user->authenticatedUser, $domainName);
        if ($result != null && count($errors) == 0) {
            return $result->available;
        } else {
            Yii::log('check domain availability failed ' . print_r($errors, true));
            return false;
        }
    }

    /**
     * split a delimited string into an array, then validate the strings with {@link IEDomainValidator}
     *
     * @param  unknown $in       string containing one or more domains delimited by newline, comma, colon, semicolon, or space
     * @param  array   &$errDoms array will have added to it domains from the input which are not valid .IE domains
     * @return array   array of valid .IE domain names
     * @access public
     * @static
     */
    static public function parseInput($in, &$errDoms = null)
    {
        // parse input string into array and return an array of valid domain-names
        if ($errDoms == null) $errDoms = array();
        $validIEDomains = array();
        $tokens = preg_split('/[\s,;:]+/', strtolower($in));
        Yii::log('TOKENS= ' . print_r($tokens, true));
        Yii::log('str "' . $in . '" into array: ' . print_r($tokens, true), 'debug', 'Utility::parseInput()1');
        foreach ($tokens as $d) {

            Yii::log('D= ' . print_r($d, true));

            if (Utility::isValidIEDomain($d)) {
                $validIEDomains[$d] = 1;
            } else {
                $errDoms[$d] = 1;
            }
        }
        $validIEDomains = array_keys($validIEDomains);
        Yii::log('VALID DOMAINS "' . $in . '": VALID = ' . print_r($validIEDomains, true));
        $errDoms = array_keys($errDoms);

        Yii::log('VALID DOMAINS "' . $in . '": ERRORS = ' . print_r($errDoms, true));
        return $validIEDomains;
    }

    private function isValidIEDomain($domainName)
    {
        $ieProperDomainName = '/^(([a-z0-9]+[-]+)*[a-z0-9]+)+(\.ie){1,1}$/i';
        $ieInvalidTwoLetterDomain = '/^[a-zA-Z]{2}\.ie$/i';
        $ieInvalidOneLetterDomain = '/^[a-zA-Z0-9]\.ie$/i';
        if (preg_match($ieProperDomainName, $domainName) == 1
            && preg_match($ieInvalidOneLetterDomain, $domainName) == 0
            && preg_match($ieInvalidTwoLetterDomain, $domainName) == 0
            && strlen($domainName) <= 66
        ) {
            Yii::log('RETUN TRU FROM VALIDATOR');
            return true;
        }

        Yii::log('RETUN FALSE FROM VALIDATOR');
        return false;
    }

    /**
     * replaces newline characters with unix newline, slash-n
     *
     * @param  string $string string to use
     * @return string copy of input with newlines replaced
     * @access public
     * @static
     */
    static public function replace_newline($string)
    {
        return (string)str_replace(array("\r", "\r\n", "\n"), '', $string);
    }

    /**
     * returns array of IEDR Usernames, for local login widget
     *
     * @return array  array of login usernames
     * @access public
     * @static
     */
    static public function getIEDRUsers()
    {
        $arrayResult = array();
        $users = array();
        CRSPermissionsAppService_service::getInternalUsers($result, $errs);
        if ($result != null) {
            if (count($errs) == 0) {
                if (is_array($result)) {
                    foreach ($result as $key) {
                        if (isset($key->nicHandleId)) {
                            $users[] = $key->nicHandleId;
                        }
                    }
                } else if (is_object($result)) {
                    if (isset($result->nicHandleId)) {
                        $users[] = $result->nicHandleId;
                    }
                }
            }
        }

        if (count($users) == 0) {
            Yii::log('IEDR USERS ARE EMPTY');
        }

        $arrayResult = array(NicHandleIdentity::$NOT_INTERNAL => '');
        foreach ($users as $u) {
            $arrayResult[$u] = $u;
        }
        Yii::log('IEDR USERS ' . print_r($arrayResult, true));
        return $arrayResult;
    }

    /**
     * returns boolean whether current request matches rules for determining of the access is from an internal network
     *
     * @param  unknown $ipAddr IP address
     * @return boolean whether current request matches rules for determining of the access is from an internal network
     * @access public
     * @static
     */
    static public function isInternalNetwork($ipAddr = null)
    {
        if ($ipAddr == null)
            $ipAddr = $_SERVER['REMOTE_ADDR'];
        $internalNet = Yii::app()->params['internal_network'];
        foreach ($internalNet['exact_matches'] as $exactIp)
            if ($ipAddr == $exactIp)
                return true;
        foreach ($internalNet['regex_matches'] as $IpRegex)
            if (preg_match($IpRegex, $ipAddr) == 1)
                return true;
        return false;
    }

    #Prints the Invoice Summary table for the View History->Invoices reports.


    /**
     * Prints to the output stream the Invoice Summary table for the View History->Invoices reports.
     *
     * @param  mixed  $model data model
     * @return void
     * @access public
     * @static
     */

    static public function hasProperty($object, $property)
    {
        if (property_exists($object, $property)) {
            Yii::log('Has property ' . $property . '.');
            return true;
        } else {
            Yii::log('Has not property ' . $property . '.');
            return false;
        }
    }

    static public function renderResult($model)
    {
        Yii::log('RENDER RESULTS');
        $orderid = '';
        $action = '';

        $totals = 0.00;
        $fee = 0.00;
        $vat = 0.00;

        switch ($model->command) {
            case 'payonline' :
                $action = 'by Payment Online';

                echo '<table class="gridtable"><tr>';
                echo '<td class="gridtablecell"><strong>Domains<br>' . $action . '</strong></td>';
                echo '<td class="gridtablecell"><strong>Transaction ID</strong></td>';
                echo '<td class="gridtablecell"><strong>Reg Date</strong></td>';
                echo '<td class="gridtablecell"><strong>Renew Date</strong></td>';
                echo '<td class="gridtablecell"><strong>Payment Period</strong></td>';
                echo '<td class="gridtablecell"><strong>Fee</strong></td>';
                echo '<td class="gridtablecell"><strong>VAT</strong></td>';
                echo '<td class="gridtablecell"><strong>Total</strong></td></tr>';

                $value = $model->commandresults['payonline']->paymentDomains;

                if (is_object($value)) {
                    echo '<tr><td class="gridtablecell">' . $value->domainName . '</td>';
                    echo '<td class="gridtablecell" colspan="1"> ' . $model->commandresults['payonline']->orderId . ' </td>';
                    echo '<td class="gridtablecell" colspan="1"> ' . parseXmlDate($value->registrationDate) . ' </td>';
                    echo '<td class="gridtablecell" colspan="1"> ' . parseXmlDate($value->renewalDate) . ' </td>';
                    echo '<td class="gridtablecell" colspan="1"> ' . $value->periodInYears . ' </td>';
                    echo '<td class="gridtablecell cash">' . Utility::currencyAmount($value->fee) . '</td>';
                    $fee += $value->fee;
                    echo '<td class="gridtablecell cash">' . Utility::currencyAmount($value->vat) . '</td>';
                    $vat += $value->vat;
                    echo '<td class="gridtablecell cash">' . Utility::currencyAmount(Utility::roundAccountingAmount($value->total)) . '</td></tr>';
                    $totals += $value->total;
                } else if (is_array($value)) {
                    for ($i = 0; $i < count($value); $i++) {
                        echo '<tr><td class="gridtablecell">' . $value[$i]->domainName . '</td>';
                        echo '<td class="gridtablecell" colspan="1"> ' . $model->commandresults['payonline']->orderId . ' </td>';
                        echo '<td class="gridtablecell" colspan="1"> ' . parseXmlDate($value[$i]->registrationDate) . ' </td>';
                        echo '<td class="gridtablecell" colspan="1"> ' . parseXmlDate($value[$i]->renewalDate) . ' </td>';
                        echo '<td class="gridtablecell" colspan="1"> ' . $value[$i]->periodInYears . ' </td>';
                        echo '<td class="gridtablecell cash">' . Utility::currencyAmount($value[$i]->fee) . '</td>';
                        $fee += $value[$i]->fee;
                        echo '<td class="gridtablecell cash">' . Utility::currencyAmount($value[$i]->vat) . '</td>';
                        $vat += $value[$i]->vat;
                        echo '<td class="gridtablecell cash">' . Utility::currencyAmount(Utility::roundAccountingAmount($value[$i]->total)) . '</td></tr>';
                        $totals += $value[$i]->total;
                    }
                }

                echo '<tr><td class="gridtablecell"><strong>Totals</strong></td>';
                echo '<td class="gridtablecell" colspan="4"></td>';
                echo '<td class="gridtablecell cash">' . Utility::currencyAmount($fee) . '</td>';
                echo '<td class="gridtablecell cash">' . Utility::currencyAmount($vat) . '</td>';
                echo '<td class="gridtablecell cash">' . Utility::currencyAmount(Utility::roundAccountingAmount($totals)) . '</td></tr>';
                echo '</table>';
                break;


            case 'paydeposit':

                $action = 'by Payment From Deposit';
                echo '<table class="gridtable"><tr>';
                echo '<td class="gridtablecell"><strong>Domains<br>' . $action . '</strong></td>';
                echo '<td class="gridtablecell"><strong>Transaction ID</strong></td>';
                echo '<td class="gridtablecell"><strong>Reg Date</strong></td>';
                echo '<td class="gridtablecell"><strong>Renew Date</strong></td>';
                echo '<td class="gridtablecell"><strong>Payment Period</strong></td>';
                echo '<td class="gridtablecell"><strong>Fee</strong></td>';
                echo '<td class="gridtablecell"><strong>VAT</strong></td>';
                echo '<td class="gridtablecell"><strong>Total</strong></td></tr>';

                $value = $model->commandresults['paydeposit']->paymentDomains;
                Yii::log('value= ' . print_r($model->commandresults, true));

                if (is_array($value)) {
                    for ($i = 0; $i < count($value); $i++) {
                        echo '<tr><td class="gridtablecell">' . $value[$i]->domainName . '</td>';
                        echo '<td class="gridtablecell" colspan="1"> ' . $model->commandresults['paydeposit']->orderId . ' </td>';
                        echo '<td class="gridtablecell" colspan="1"> ' . parseXmlDate($value[$i]->registrationDate) . ' </td>';
                        echo '<td class="gridtablecell" colspan="1"> ' . parseXmlDate($value[$i]->renewalDate) . ' </td>';
                        echo '<td class="gridtablecell" colspan="1"> ' . $value[$i]->periodInYears . ' </td>';
                        echo '<td class="gridtablecell cash">' . Utility::currencyAmount($value[$i]->fee) . '</td>';
                        $fee += $value[$i]->fee;
                        echo '<td class="gridtablecell cash">' . Utility::currencyAmount($value[$i]->vat) . '</td>';
                        $vat += $value[$i]->vat;
                        echo '<td class="gridtablecell cash">' . Utility::currencyAmount(Utility::roundAccountingAmount($value[$i]->total)) . '</td></tr>';
                        $totals += $value[$i]->total;
                    }
                } else if (is_object($value)) {
                    echo '<tr><td class="gridtablecell">' . $value->domainName . '</td>';
                    echo '<td class="gridtablecell" colspan="1"> ' . $model->commandresults['paydeposit']->orderId . ' </td>';
                    echo '<td class="gridtablecell" colspan="1"> ' . parseXmlDate($value->registrationDate) . ' </td>';
                    echo '<td class="gridtablecell" colspan="1"> ' . parseXmlDate($value->renewalDate) . ' </td>';
                    echo '<td class="gridtablecell" colspan="1"> ' . $value->periodInYears . ' </td>';
                    echo '<td class="gridtablecell cash">' . Utility::currencyAmount($value->fee) . '</td>';
                    $fee += $value->fee;
                    echo '<td class="gridtablecell cash">' . Utility::currencyAmount($value->vat) . '</td>';
                    $vat += $value->vat;
                    echo '<td class="gridtablecell cash">' . Utility::currencyAmount(Utility::roundAccountingAmount($value->total)) . '</td></tr>';
                    $totals += $value->total;
                }

                echo '<tr><td class="gridtablecell"><strong>Totals</strong></td>';
                echo '<td class="gridtablecell" colspan="4"></td>';
                echo '<td class="gridtablecell cash">' . Utility::currencyAmount($fee) . '</td>';
                echo '<td class="gridtablecell cash">' . Utility::currencyAmount($vat) . '</td>';
                echo '<td class="gridtablecell cash">' . Utility::currencyAmount(Utility::roundAccountingAmount($totals)) . '</td></tr>';
                echo '</table>';

                break;

            case 'removefromvoluntary':
                $action = '';
                if (empty($model->commandresults['removefromvoluntary'])) {
                    echo "<div> No domain removed from NRP.</div><br><br>";
                } else {
                    echo "<div> The following domain(s) have been successfully removed from the non-renewal process: " . implode(", ", $model->commandresults['removefromvoluntary']) . "</div><br><br>";
                }

                if (!empty($model->commandresults['removefromvoluntaryfailed'])) {
                    echo "<div> The following domain(s) require payment to be reactivated: " . implode(", ", $model->commandresults['removefromvoluntaryfailed']) . "</div><br><br>";
                }
                break;

            case 'voluntary':
                $action = '';
                echo "<table class='gridtable'>";
                echo "<tr><td class='gridtablecell'><strong>Domain $action</strong></td>";
                echo "<td class='gridtablecell'><strong>Registration Date</strong></td>";
                echo "<td class='gridtablecell'><strong>Renewal Date</strong></td>";
                echo "<td class='gridtablecell'><strong>Status</strong></td></tr>";

                $value = $model->commandresults['voluntary'];
                if (is_array($value)) {
                    for ($i = 0; $i < count($value); $i++) {
                        echo "<tr><td class='gridtablecell'>" . $value[$i]->name . "</td>";
                        echo "<td class='gridtablecell'>" . parseXmlDate($value[$i]->registrationDate) . "</td>";
                        echo "<td class='gridtablecell'>" . parseXmlDate($value[$i]->renewDate) . "</td>";
                        echo "<td class='gridtablecell'>" . $value[$i]->dsmState->shortNrpStatus . "</td></tr>";
                    }
                } else if (is_object($value)) {
                    echo "<tr><td class='gridtablecell'>" . $value->name . "</td>";
                    echo "<td class='gridtablecell'>" . parseXmlDate($value->registrationDate) . "</td>";
                    echo "<td class='gridtablecell'>" . parseXmlDate($value->renewDate) . "</td>";
                    echo "<td class='gridtablecell'>" . $value->dsmState->shortNrpStatus . "</td></tr>";
                }

                echo '</table>';
                break;

            case 'autorenew':
                $action = '';
                echo "<table class='gridtable'>";
                echo "<tr><td class='gridtablecell'><strong>Domain $action</strong></td>";
                echo "<td class='gridtablecell'><strong>Registration Date</strong></td>";
                echo "<td class='gridtablecell'><strong>Renewal Date</strong></td>";
                echo "<td class='gridtablecell'><strong>Renew mode</strong></td></tr>";

                $value = $model->commandresults['autorenew'];
                if (is_array($value)) {
                    for ($i = 0; $i < count($value); $i++) {
                        echo "<tr><td class='gridtablecell'>" . $value[$i]->name . "</td>";
                        echo "<td class='gridtablecell'>" . parseXmlDate($value[$i]->registrationDate) . "</td>";
                        echo "<td class='gridtablecell'>" . parseXmlDate($value[$i]->renewDate) . "</td>";
                        echo "<td class='gridtablecell'>" . $value[$i]->dsmState->renewalMode . "</td></tr>";
                    }
                } else if (is_object($value)) {
                    echo "<tr><td class='gridtablecell'>" . $value->name . "</td>";
                    echo "<td class='gridtablecell'>" . parseXmlDate($value->registrationDate) . "</td>";
                    echo "<td class='gridtablecell'>" . parseXmlDate($value->renewDate) . "</td>";
                    echo "<td class='gridtablecell'>" . $value->dsmState->renewalMode . "</td></tr>";
                }

                echo '</table>';
                break;
                break;
        }
    }

    static public function isRegistrar()
    {
        $permission = Utility::getPermission();
        return ($permission == 2);
    }

    static public function isSuperRegistrar()
    {
        $permission = Utility::getPermission();
        return ($permission == 3);
    }

    static public function isDirect()
    {
        $permission = Utility::getPermission();
        return ($permission == 1);
    }

    static public function isLoggedIn() {
        return isset(Yii::app()->user->authenticatedUser);
    }

    static public function isLoggedInAs($name) {
    	return strcasecmp(Yii::app()->user->authenticatedUser->username, $name) === 0;
    }

    /**
     * @param ViewDomainModel $domain
     */
    static public function isTechContact($domain)
    {
    	return Utility::isLoggedInAs($domain->domain_techContacts_nicHandle_orig);
    }

    /**
     * @param ViewDomainModel $domain
     */
    static public function isAdminContact($domain)
    {
    	return Utility::isLoggedInAs($domain->domain_adminContacts_0_nicHandle_orig) 
    	 || Utility::isLoggedInAs($domain->domain_adminContacts_1_nicHandle_orig);
    }
    
    /**
     * @param ViewDomainModel $domain
     */
    static public function isBillingContact($domain)
    {
    	return Utility::isLoggedInAs($domain->domain_billingContacts_nicHandle);
    }

    static public function isAdminOrTech() {
    	$permission = Utility::getPermission();
    	return $permission == 4;
    }
    
    static public function getPermission()
    {
        $result = null;
        $errs = array();
        if (isset(Yii::app()->user->authenticatedUser)) {
            $user = Yii::app()->user->authenticatedUser;
            CRSPermissionsAppService_service::getUserLevel($result, $errs, $user);
            if ($result != null) {
                return $result;
            }
        }
        return 0;
    }

    static public function getPeriodsWithPrices($type)
    {
        $duration = array();
        $pricelist = get_reg_prices();
        foreach ($pricelist[$type] as $code => $prod) {
            $priceWithVat = round($prod->price + $prod->vatValue, 2, PHP_ROUND_HALF_EVEN);
            $duration[$code] = $prod->duration . ' Year €' . $priceWithVat;
            if ($prod->vatValue > 0) {
            	$duration[$code] = $duration[$code] . ' Inc Vat.';
            }
        }
        return $duration;
    }
    
    static public function getPeriodsWithNumericFees($type)
    {
    	$duration = array();
    	$pricelist = get_reg_prices();
        if (!is_array($pricelist) || sizeof($pricelist) == 0) {
            return $duration;
        }
    	foreach ($pricelist[$type] as $code => $prod) {
    		$priceWithVat = round($prod->price + $prod->vatValue, 2, PHP_ROUND_HALF_EVEN);
    		$duration[$code] = array($priceWithVat, $prod->vatValue);
    	}
    	return $duration;
    }

    static public function createSelect($htmlId, $array)
    {
        if (count($array)) {
            $select = '<select id="period_' . $htmlId . '" disabled="disabled" onchange="periodChanged(\''.$htmlId.'\');" >';
            foreach ($array as $key => $value) {
                $select .= '<option value=' . $key . '>' . $value . '</option>';
            }
            $select .= '</select>';
            return $select;
        }
        return ' ';
    }

    static private function extensionIsLoaded()
    {
        if (!extension_loaded('sockets')) {
            Yii::log('Extension sockets not loaded!');
            return false;
        }
        return true;
    }

    static private function isValidPort($port)
    {
        if (is_numeric($port))
            return true;
        Yii::log('Port is not numeric.');
        return false;
    }

    static private function isValidWsapiSoapUrl()
    {
        // Valid wsapi url string ('wsapi_soap_url'=>'http://localhost:8080/crs-web-services-0.10.0-Sprint16/')
        $url = Yii::app()->params['wsapi_soap_url'];
        if (!isset($url)) {
            Yii::log('Wsapi soap url:' . $url . ' is not set.');
            return false;
        }

        $whitoutProtocol = split('//', $url);
        if (count($whitoutProtocol) != 2) {
            Yii::log('Could not get address and service name form wsapi soap url:' . print_r($url, true));
            return false;
        }

        $whitoutSubdirs = split('/', $whitoutProtocol[1]);
        if (count($whitoutSubdirs) == 0) {
            Yii::log('Could not get address and service name form wsapi soap url:' . print_r($url, true));
            return false;
        }

        $addressAndPort = split(':', $whitoutSubdirs[0]);
        if (count($addressAndPort) != 2) {
            Yii::log('Could not get address and port form wsapi soap url:' . print_r($url, true));
            return false;
        }
        return true;
    }

    static private function getAddressAndPortFromWsapiSoapUrl(&$address, &$port)
    {
        $url = Yii::app()->params['wsapi_soap_url'];
        $whitoutProtocol = split('//', $url);

        if (count($whitoutProtocol) != 2) {
            Yii::log('Could not get address without protocol from wsapi soap url : ' . print_r($url, true));
            return false;
        }

        $whitoutSubdirs = split('/', $whitoutProtocol[1]);
        if (count($whitoutSubdirs) != 3) {
            Yii::log('Could not get subdirs from : ' . print_r($whitoutProtocol[1], true));
            return false;
        }

        $addressAndPort = split(':', $whitoutSubdirs[0]);
        if (count($addressAndPort) != 2) {
            Yii::log('Could not get address and port from : ' . print_r($whitoutSubdirs[0], true));
            return false;
        }

        $address = $addressAndPort[0];
        $port = $addressAndPort[1];

        return true;
    }

    static public function isTomcatExist()
    {
        if (!Utility::extensionIsLoaded())
            return false;

        if (!Utility::isValidWsapiSoapUrl())
            return false;

        if (!Utility::getAddressAndPortFromWsapiSoapUrl($address, $port))
            return false;

        if (!Utility::isValidPort($port))
            return false;

        $socket = @socket_create(AF_INET, SOCK_STREAM, 0);
        if ($socket) {
            $isConnect = @socket_connect($socket, $address, $port);
            if ($isConnect === false) {
                Yii::log('Could not connect to Tomcat Server on address ' . $address . ':' . $port . '');
                return false;
            } else {
                @socket_close($socket);
                Yii::log('Connect to Tomcat successful on address ' . $address . ':' . $port . '.');
                return true;
            }
        }

        Yii::log('Could not create socket.');
        return false;
    }

    static public function getPaymentTypes()
    {
        $paymentType = array();
        $paymentType['CC'] = 'Credit Card';
        if (!Utility::isDirect())
            $paymentType['ADP'] = 'Deposit Account';
        $paymentType['CH'] = 'Charity';
        return $paymentType;
    }

    static public function getTransferPaymentTypes()
    {
        $paymentType = array();
        $paymentType['CC'] = 'Credit Card';
        if (!Utility::isDirect())
            $paymentType['ADP'] = 'Deposit Account';
        return $paymentType;
    }

    static public function isDebitPaymentAllowed($model)
    {
//       	Yii::log(print_r($model, true), 'DEBUG', "Utility::isDebitPaymentAllowed");
        return method_exists($model, 'isDebitCardAllowed') && $model->isDebitCardAllowed() && Utility::getPermission() == 1;
    }

    static public function getEmailInvoiceTypes()
    {
        return array(
            CRSNicHandleAppService_emailInvoiceFormat::_XML => 'XML',
            CRSNicHandleAppService_emailInvoiceFormat::_PDF => 'PDF',
            CRSNicHandleAppService_emailInvoiceFormat::_BOTH => 'BOTH',
//            CRSNicHandleAppService_emailInvoiceFormat::_NONE => 'NONE',
        );
    }

    static public  function writeActionToSession($returnAction) {
        Yii::app()->session['returnAction'] = $returnAction;
    }
    
    static public function printFlashNotice(&$message) {
    	Utility::printDiv($message, "flash-notice");
    }
    
    static public function printFlashError(&$message) {
    	Utility::printDiv($message, "flash-error");
    }
    
    static private function printDiv(&$message, $divClass) {
    	if($message && strlen($message) > 0){
    		echo "<div class =\"$divClass\">";
    		if(is_array($message)) {
    			foreach($message as $aMsg){
    				echo $aMsg . "<br>";
    			}
    		} else {
    			echo $message . "<br>";
    		}
    		echo "</div><br>";
    		$message = "";
    	}
    }

}
   

