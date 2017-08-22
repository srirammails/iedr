# no need to prepare a data dump - database needs to be feeded manually!
rm -Rf crs
svn checkout https://drotest4.nask.net.pl/svn/iedr-crs/branches/bpr-sprint36fixes/src crs/src

pushd crs/src/
mvn -Pjndi-config,-enunciate-docs -Dmaven.test.skip=true clean package
popd
find crs/src -name *war -exec cp --parents {} -t artifacts \;

#mysqldump crsdb -u crsuser --password=crspassword > artifacts/crsdb.sql
