#!/bin/bash

function die() {
    echo "FATAL ERR: $1" ; exit 1
    }

TC="/opt/apache-tomcat-6.0.29"
WN="crs-web-services"
V="0.9.6"
SRC="/home/johnm/trunk/src/${WN}/target"
WARFILENAME="${WN}-${V}.war"
WARFILE="${SRC}/${WARFILENAME}"

[ -d  $TC ] || { die "dir not found : $TC" ; }
[ -d  $TC/webapps ] || { die "dir not found : $TC/webapps" ; }
[ -d  $SRC ] || { die "dir not found : $SRC" ; }
[ -f ${WARFILE} ] || { die "file not found : ${WARFILE}" ; }

[ `whoami` = "root" ] || { die "must be ROOT" ; }

[ $WARFILE -nt $TC/webapps/${WARFILENAME} ] && {

    ${TC}/bin/shutdown.sh
    # rm any matching warfile and unpacked dir
    rm -rf ${TC}/webapps/${WN}-${V}*
    rm -f ${TC}/logs/*
    cp -p ${WARFILE} ${TC}/webapps/${WARFILENAME}
    chmod 0644 ${TC}/webapps/${WARFILENAME}
    ${TC}/bin/startup.sh

} || {
    echo "Source War file not updated";
    }

ls -l ${WARFILE} ${TC}/webapps/${WARFILENAME}

