find artifacts -name $1*war -exec scp {} root@$2:/opt/crs/tomcat/webapps/$1.war \;
