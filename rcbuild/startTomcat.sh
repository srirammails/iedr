echo "Starting tomcat on $1"
ssh root@$1 /opt/crs/tomcat/bin/startup.sh
