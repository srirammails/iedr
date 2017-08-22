#!/bin/bash

SERVICE="$1"
REQUESTXML="$2"

SOAPREQ="/home/johnm/tmp/data.xml"
SOAPURL="http://127.0.0.1:8080/crs-web-services-0.9.6/${SERVICE}"
CONTYPE="Content-Type: text/xml"

SL=" CRSAuthenticationService CRSDomainAppService CRSNicHandleAppService CRSPaymentAppService CRSPermissionsAppService CRSResellerAppService CRSTicketAppService "

SVCOK=`echo "$SL" | tr ' ' '\n' | grep -c "^${SERVICE}$"`
[ $SVCOK -eq 0 ] && { echo -e "service '$SERVICE' not recognised\n valid services : $SL" ; exit ; }

[ -f "$REQUESTXML" ] || { echo "input file not found : '${REQUESTXML}'" ;  }

OUT=`mktemp`
curl -s --noproxy \* -d @${REQUESTXML} --compressed -H "$CONTYPE" $SOAPURL > $OUT
[ -x `which xmllint` ] && { cat $OUT | xmllint --format - ; } || { cat $OUT ; }
rm $OUT

