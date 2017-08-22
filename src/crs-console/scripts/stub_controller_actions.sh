#!/bin/bash

CONTLIST="accounts_history accounts_history_invoices accounts_history_payments accounts_msd accounts_payhist_crednotes accounts_renewpay accounts_renewpay_reports accounts_reports contacts domainreports domains domainsearch mlist tickets"

for DIR in $CONTLIST ; do
	I=`echo ${DIR} | cut -c -1 | tr '[a-z]' '[A-Z]'`
	J=`echo ${DIR} | cut -c 2-`
	CONT="$I$J"
	CONTF=${CONT}Controller.php
	cat > protected/controllers/${CONTF}<<EOF
<?php
class ${CONT}Controller
	extends Controller
	{
	public \$breadcrumbs = array('${CONT}');
EOF
	# list actions
	find protected/views/${DIR} -type f -name \*\.php | \
		while read V ; do
			VF=`basename $V | sed "s|.php||"`
			VF1=`echo $VF | cut -c -1 | tr '[a-z]' '[A-Z]'`
			VF2=`echo $VF | cut -c 2-`
			echo "    public function action${VF1}${VF2}() { \$this->render('${VF}'); }" >> protected/controllers/${CONTF}
		done
	echo "    }" >> protected/controllers/${CONTF}
done

