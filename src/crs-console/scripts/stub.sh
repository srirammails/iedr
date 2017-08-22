#!/bin/bash

function stub() {
	TXT=$1
	DIR=$2
	FILE=$3
	[ -d protected/views/${DIR} ] || { 
		echo "mkdir -p protected/views/${DIR}" ;
		mkdir -p protected/views/${DIR} ;
		}
	[ -f protected/views/${DIR}/${FILE}.php ] || {
		echo "create file 'protected/views/${DIR}/${FILE}.php'" ;
		cat > protected/views/${DIR}/${FILE}.php <<EOF
<?php
\$pg = '${TXT}';
\$this->pageTitle=Yii::app()->name . ' - '.\$pg;
?>
<h2><?=\$pg ?></h2>
EOF
		}
	}

# just for menu placeholders
stub "Accounts" "accounts" "menu"
stub "Contacts" "contacts" "menu"
stub "Credit Notes" "accounts_payhist_crednotes" "menu"
stub "Domain Reports" "domainreports" "menu"
stub "Domains" "domains" "menu"
stub "Invoices" "accounts_history_invoices" "menu"
stub "Manage MSD" "accounts_msd" "menu"
stub "Payments" "accounts_history_payments" "menu"
stub "Renew and Pay" "accounts_renewpay" "menu"
stub "Reports" "accounts_renewpay_reports" "menu"
stub "Reports" "domainreports" "menu"
stub "Resources" "resources" "menu"
stub "Search Domains" "domainsearch" "menu"
stub "View History" "accounts_history" "menu"
stub "View Reports" "accounts_reports" "menu"

# non-static pages/functions
#stub "Home" "site" "index"
stub "Register New" "domains" "regnew"
stub "All Domains" "domainreports" "alldomains"
stub "Newly Registered Domains" "domainreports" "newdomains"
stub "Full Name" "domainsearch" "fullname"
stub "Partial / Select" "domainsearch" "partname"
stub "DNS Server search" "domainreports" "dnsserversearch"
stub "Bulk DNS update" "domainreports" "bulkdnsupdate"
stub "NS Records" "domainreports" "nsrecords"
stub "Conditionally Accepted Dmains" "domainreports" "condaccepted"
stub "Autorenewed Domains" "domainreports" "autorenewed"
stub "Charity Domains" "domainreports" "charity"
stub "Current Renewals and New Registrations" "accounts_renewpay_reports" "current_newandrenewals"
stub "Future Renewals - Pay now" "accounts_renewpay_reports" "future_paynow"
stub "Transfers - Pay Current Invoice" "accounts_renewpay_reports" "transfers_paycurrent"
stub "Transfers - Pay in Advance of Invoice" "accounts_renewpay" "transfers_pia"
stub "Domains with a renewal date" "accounts_renewpay" "renewals"
stub "Modify or Remove Outstanding Payments" "accounts_renewpay" "outstanding_payments"
stub "Top Up Deposit Account" "accounts_renewpay" "topup"
stub "Mails / Suspensions / Deletions" "accounts_msd" "recent"
stub "Mails / Suspensions / Deletions over Last Year" "accounts_msd" "year"
stub "Domains Transferred" "accounts_reports" "domains_transferred"
stub "Statement of Account - aged balance" "accounts_reports" "account_aged_balance"
stub "Renewals" "accounts_history_invoices" "renewals"
stub "Transfers" "accounts_history_invoices" "transfers"
stub "Advance Payments" "accounts_history_invoices" "payments"
stub "Renewals" "accounts_history_payments" "renewals"
stub "Transfers" "accounts_history_payments" "transfers"
stub "Advance Payments" "accounts_history_payments" "payments"
stub "Historical Payment Search" "accounts_history" "payhist"
stub "MSD Deletions" "accounts_payhist_crednotes" "deletions"
stub "Transfers Away" "accounts_payhist_crednotes" "transfers_away"
stub "On Invoices" "accounts_payhist_crednotes" "invoices"
stub "Admins" "contacts" "admins"
stub "Techs" "contacts" "techs"
stub "Name Search" "contacts" "nameseach"
stub "NIC Search" "contacts" "nicsearch"
stub "Tickets" "tickets" "main"
stub "Mailing List" "mlist" "main"

