#!/bin/sh

DIRNAME=`dirname $0`

pushd $DIRNAME
pwd
bin/shutdown.sh
rm -fR webapps/crs*
rm -fR logs/catalina.*
popd
