#!/bin/bash
echo "Building RPM"
rm -fR rpm
tar -zxf rpm.tar.gz
chown root:root -R rpm

echo "rpmbuild using nrc.spec"
cat ./rpm/SPECS/nrc.spec
rpmbuild -v -bb ./rpm/SPECS/nrc.spec
