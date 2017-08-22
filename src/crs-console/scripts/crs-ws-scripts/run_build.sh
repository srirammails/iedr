#!/bin/bash -x

DATEHR=`date "+%Y-%m-%d_%H"`
SRCDIR="/home/johnm/trunk/src"
WEBAPPS="/opt/apache-tomcat-6.0.29/webapps"
TMPSRC="/home/johnm/tmp/tmpsrc"
DIFFFILE="${TMPSRC}/../svn_diffs_crsws_${DATEHR}.patch"

[ -d ${WEBAPPS} ] || { echo "not a directory : webapps='${WEBAPPS}'" ; exit 1 ; }
[ -d ${SRCDIR} ] || { echo "not a directory : srcdir='${SRCDIR}'" ; exit 1 ; }
cd ${SRCDIR} || { echo "cant cd to dir='${SRCDIR}'" ; exit 1 ; }

find ${SRCDIR} -type d -name target -exec rm -rf {} \; 2>/dev/null

echo "** backup sources"
mkdir -p ${TMPSRC}
rm -rf ${TMPSRC}/*
cp -a ${SRCDIR}/* ${TMPSRC}/

echo "** update svn"
svn update
INFODIR="/var/www/trunk/src/wsapi"
INFO="${INFODIR}/crs_ws_version.php"
echo "<?php \$crs_ws_version_info = array(" > $INFO
svn info | sed "s|\([^:]*\): *\(.*\)|'\1'=>'\2',|" >> $INFO
echo ");" >> $INFO

diff -x .svn -x target -rwdbBPU 3 ${TMPSRC}/ ${SRCDIR}/ > ${DIFFFILE}

# run build if any svn updates found
grep "^+" ${DIFFFILE} >/dev/null && {
    echo "** build project"
    mvn -P iedr_lab_crs_ii_g_config -Dmaven.test.skip=true package
    }
