scp remote/build_rpm.sh root@172.19.243.39:.
scp remote/install_rpm.sh root@172.19.243.39:.

#scp remote/api_web.xml root@172.19.243.29:/usr/src/tomcat/conf/web.xml
scp remote/ws_web.xml root@172.19.243.42:/opt/crs/tomcat/conf/web.xml
scp remote/crs_web.xml root@172.19.243.41:/opt/crs/tomcat/conf/web.xml
scp remote/crs_web.xml root@172.19.243.40:/opt/crs/tomcat/conf/web.xml


