#!/bin/bash 

#URL="http://193.59.205.170:8080/crs-web-services-0.10.0-Sprint19/"
URL="http://127.0.0.1:8080/crs-web-services-0.10.0-Sprint21/"

S="CRSAuthenticationService"
S="$S CRSDomainAppService"
S="$S CRSDocumentAppService"
S="$S CRSNicHandleAppService"
S="$S CRSPaymentAppService"
S="$S CRSPermissionsAppService"
S="$S CRSResellerAppService"
S="$S CRSTicketAppService"
S="$S CRSCommonAppService"
S="$S CRSInfo"

WSDLDIR="/var/www/trunk/src/wsapi/wsdl_from_svc"

cd $WSDLDIR || { echo "ERROR cant cd to '$WSDLDIR'" ; exit 1 ; }
rm -f ./CRS*

function xmltidy() {
	X=$1
	[ -s $X ] && {
		T=`mktemp`
		cat $X > $T && xmllint --format $T > $X
		rm $T		
		}
	}

for F in $S ; do

	[ -f ${F}.1.wsdl ] || { wget --no-proxy -SO ${F}.1.wsdl "${URL}${F}?wsdl=1" 	&& xmltidy ${F}.1.wsdl ; }
	[ -f ${F}.xsd    ] || { wget --no-proxy -SO ${F}.xsd 	"${URL}${F}?xsd=1" 	&& xmltidy ${F}.xsd ; }

	[ -s ${F}.xsd ] && { xsltproc --stringparam xsd_file ${F}.xsd wsdl_to_api.xslt ${F}.1.wsdl > ${F}_api.xml && xmltidy ${F}_api.xml ; }
	[ -s ${F}_api.xml ] && { xsltproc --stringparam namespace ${F} make_php_api_calls.xslt ${F}_api.xml | grep -v "xml version" > ${F}.php ; }
done

#svn diff CRS*.php

