export NRC_RELEASE=27.3

mkdir nrc
pushd nrc
mkdir trunk
pushd trunk
rm -Rf doc tests
if [ -z "$1" ]; then
rm -Rf src
svn export http://83.71.193.106:81/svn/CRS-PII-G/branches/sprint36fixes/src
fi
# svn export http://83.71.193.106:81/svn/CRS-PII-G/trunk/doc
# svn export http://83.71.193.106:81/svn/CRS-PII-G/trunk/tests
popd
# prepare RPM package?
rm -fR rpm
cp ../rpm.tar.gz .

tar -zxf rpm.tar.gz
mkdir rpm/SOURCES/nrc-1
rm -fR rpm/SOURCES/nrc-1/trunk/* rpm/SOURCES/nrc-1.tar.gz
cp -R trunk rpm/SOURCES/nrc-1/.
sed 's|\(wsapi_soap_url'"'"'=>'"'"'\)\(.*\)|\1SOAP_URL_VAR'"'"',|' rpm/SOURCES/nrc-1/trunk/src/www/protected/config/main.php > rpm/SOURCES/nrc-1/trunk/src/www/protected/config/main.php.new
sed 's|URL=.*|URL="SOAP_URL_VAR"|' rpm/SOURCES/nrc-1/trunk/src/wsapi/wsdl_from_svc/get_wsdl.sh > rpm/SOURCES/nrc-1/trunk/src/wsapi/wsdl_from_svc/get_wsdl.sh.new
sed "s|Release:.*|Release: $NRC_RELEASE|" rpm/SPECS/nrc.spec > rpm/SPECS/nrc.spec.new
mv rpm/SOURCES/nrc-1/trunk/src/www/protected/config/main.php.new rpm/SOURCES/nrc-1/trunk/src/www/protected/config/main.php
mv rpm/SOURCES/nrc-1/trunk/src/wsapi/wsdl_from_svc/get_wsdl.sh.new rpm/SOURCES/nrc-1/trunk/src/wsapi/wsdl_from_svc/get_wsdl.sh
mv rpm/SPECS/nrc.spec.new rpm/SPECS/nrc.spec
chmod 755 rpm/SOURCES/nrc-1/trunk/src/wsapi/wsdl_from_svc/get_wsdl.sh
pushd rpm/SOURCES
tar --exclude-vcs -zvcf nrc-1.tar.gz nrc-1
popd
tar --exclude-vcs -zvcf rpm.tar.gz rpm
popd
