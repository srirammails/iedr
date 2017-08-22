#!/bin/bash

# variables
WEBAPPS=/opt/apache-tomcat-6.0.29/webapps
APP=crs-web-services-0.9.7
JARNAME=crs-config-1.5.3

# prep dir
cd ~
mkdir -p crs/conf
cd crs
cp ${WEBAPPS}/${APP}/WEB-INF/lib/${JARNAME}.jar ./
cd conf
jar xf ../${JARNAME}.jar

### edit 

echo
echo "EDIT dataSource.xml to use correct Database Access:"
ls -l dataSource.xml
grep -ni "<property name=.[up]" dataSource.xml
echo
echo "EDIT emails.xml to use correct SMTP Email Parameters:"
ls -l emails.xml
echo "emailSender params are: (1) mailer, (2) mailhost, (3) port, (4) userName, (5) userPassword, (6) from"
grep -niC 2 "<constructor-arg" emails.xml

echo
echo "press CTRL-Z to suspend this script, edit the files, then 'fg', and then press any key to continue .."
read Z

# create new Jar
cd ~/crs
jar cf ${JARNAME}_NEW.jar -C conf .

echo "created new Jar file :"
ls -l ${JARNAME}_NEW.jar

# update deployed jar file in webapps
cat ${JARNAME}_NEW.jar > ${WEBAPPS}/${APP}/WEB-INF/lib/${JARNAME}.jar

echo "deployed new jar file."
ls -l ${JARNAME}_NEW.jar ${WEBAPPS}/${APP}/WEB-INF/lib/${JARNAME}.jar
md5sum ${JARNAME}_NEW.jar ${WEBAPPS}/${APP}/WEB-INF/lib/${JARNAME}.jar

