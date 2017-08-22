#!/bin/bash
set -e
set -u

ORIGINAL=target/crs-web-services/WEB-INF/classes
WORKING=target/stage/wsapi/wsdl_from_svc
TARGET=target/stage/www/protected/wsapi

# Copy XSD and WSDL from dependency web services war
cp $ORIGINAL/*.xsd $ORIGINAL/*.wsdl $WORKING/

# Generate 
pushd "$WORKING"
/bin/bash wsdl2php.sh
popd

# Clean target dir
find $TARGET -type l -exec rm -f "{}" \;
# Place generated PHP classes into target
cp $WORKING/php/*.php $TARGET/

# do a LINT check on all PHP code
find target/stage -name "*.php" | while read i
do
	php -l "$i"
done
