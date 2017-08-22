<?php

/**
 * defines functions for counties and countries, which are not handled by the NASK CRS-WS-API
 *
 * @category  NRC
 * @package   IEDR New Registrars Console
 * @author    IEDR <asd@iedr.ie>
 * @copyright 2011 IEDR
 * @license   http://www.iedr.ie/ (C) IEDR 2011
 * @version   CVS: $Id:$
 * @link      http://pear.php.net/package/IEDR New Registrars Console
 */


/**
 * Returns array of data for counties and countries, getting data and filling cache if necessary
 *
 * @return array county and country data (see {@link _get_country_county})
 */
function get_country_county()
{
    return _get_country_county();
}

/**
 * Returns array of data for counties and countries
 *
 * Returned array contains three sub-arrays;
 *
 * - 'country_names' : maps country-name to country-code
 *
 * - 'country_codes' : maps country code to country name
 *
 * - 'countrycode_counties' : maps country-code to county
 *
 * @return array county and country data
 */
function _get_country_county()
{
    $data = array();
    $data['country_names'] = array();
    $data['country_codes'] = array();
    $data['countrycode_counties'] = array();

    // user may be a null value here
	$user = null;
    $errs = '';
    $result = null;

    CRSDomainAppService_service::getCountries($result, $errs, $user);
    if ($result != null && count($errs) == 0) {
        //Yii::log('GET COUNTRIES ' . print_r($result, true));
        if (is_array($result)) {
            for ($i = 0; $i < count($result); $i++) {
                $data['country_names'][$result[$i]->name] = $result[$i]->code;
                $data['country_codes'][$result[$i]->code] = $result[$i]->name;
                if (isset($result[$i]->counties)) {
                    $data['countrycode_counties'][$result[$i]->code] = $result[$i]->counties;
                } else {
                    $data['countrycode_counties'][$result[$i]->code] = null;
                }
            }
        } else if (is_object($result)) {
            $data['country_names'][$result->name] = $result->code;
            $data['country_codes'][$result->code] = $result->name;
            if (isset($result->counties)) {
                $data['countrycode_counties'][$result->code] = $result->counties;
            }
        }
    } else {
        Yii::log('GET COUNTRIES ERROR ' . print_r($errs, true));
    }
    //Yii::log('COUNTRIES DATA' . print_r($data, true));
    return $data;
}

/**
 * get Country options in a format suitable for a Yii $form->dropDownList
 *
 * @return array select-list options array
 */
function getCountryOptions()
{
    $countries = array();
    $countries[''] = '';
    $country_county_data = get_country_county();
    if (is_array($country_county_data['country_codes']))
        foreach ($country_county_data['country_codes'] as $ccode => $cname)
            $countries[$cname] = $cname;
    return $countries;
}

/**
 * get County options in a format suitable for a Yii $form->dropDownList
 *
 * @param  string $country country name
 * @return array   select-list options array
 */
function getCountyOptions($country)
{
    $country_county_data = get_country_county();
    $counties = array();
    $counties[''] = '';
    // find country code
    $ccode = $country_county_data['country_names'][$country];
    if ($ccode != null and is_array($country_county_data['countrycode_counties'][$ccode]))
        foreach ($country_county_data['countrycode_counties'][$ccode] as $county)
            if ($county != null and strlen($county) > 1)
                $counties[$county] = $county;
    if (count($counties) <= 1) {
        $counties = array();
        $counties['N/A'] = 'N/A';
    }
    return $counties;
}

