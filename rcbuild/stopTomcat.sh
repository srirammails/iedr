echo "Stopping tomcat on $1"
ssh root@$1 /opt/crs/tomcat/shutdown_tomcat.sh
