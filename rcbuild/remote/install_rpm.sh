#!/bin/bash
export apache_user=apache
export apache_group=apache
#export soap_url=http://172.19.243.31:8080/crs-web-services/
export soap_url=http://172.19.243.42:8080/crs-web-services/

yum -y remove nrc
if [ -z "$1" ]; then
    RPM_PATH=rpm/RPMS/noarch/nrc*rpm
else 
    RPM_PATH=$1
fi

yum -y --nogpgcheck localinstall $RPM_PATH

/var/www/trunk/src/wsapi/wsdl_from_svc/get_wsdl.sh

service httpd restart
