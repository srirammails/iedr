#!/bin/bash

XML=`mktemp`

function die() {
	echo $1
	exit 1
	}

function do_request() {
	./test-crs-ws-request.sh $1 $2 > $XML
	grep -i "exception" $XML && die "$3"
	}


## (1) checkout
do_request CRSTicketAppService crs-ws-test-xml/test_ticket_update_p1.xml "failed to checkout"

## (2) update
sleep 1
do_request CRSTicketAppService crs-ws-test-xml/test_ticket_update_p2.xml "failed to update"

## (3) checkin
sleep 1
do_request CRSTicketAppService crs-ws-test-xml/test_ticket_update_p3.xml "failed to checkin"

## (4) view
do_request CRSTicketAppService crs-ws-test-xml/test_ticket_update_p4.xml "failed to view"

# check that IP addresses are blank or empty
echo "nameservers/ipAddress/newValue should be enpty:"
echo "cat //nameservers/ipAddress/newValue" | xmllint --shell $XML

echo "output in file : " $XML
