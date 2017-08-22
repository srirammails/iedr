#!/bin/bash

# http://dev.mysql.com/doc/refman/5.0/en/resetting-permissions.html#resetting-permissions-unix

UPD=/tmp/upd_root_pwd.sql

function die() {
echo $1
exit 1
}

[ -x /usr/bin/mysqld_safe ] || die "cant find/exec /usr/bin/mysqld_safe"
[ -x /etc/init.d/mysql ] || die "cant find/exec /etc/init.d/mysql"

/etc/init.d/mysql stop
cat > ${UPD}<<EOF

UPDATE mysql.user SET Password=PASSWORD('rootpass') WHERE User='root';

INSERT INTO mysql.user VALUES ('%','root',password('rootpass'),'Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','','','','',0,0,0,0);
INSERT INTO mysql.user VALUES ('%','crsuser',password('crspassword'),'Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','','','','',0,0,0,0);

INSERT INTO mysql.user VALUES ('127.0.0.1','root',password('rootpass'),'Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','','','','',0,0,0,0);
INSERT INTO mysql.user VALUES ('127.0.0.1','crsuser',password('crspassword'),'Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','','','','',0,0,0,0);

INSERT INTO mysql.user VALUES ('localhost','root',password('rootpass'),'Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','','','','',0,0,0,0);
INSERT INTO mysql.user VALUES ('localhost','crsuser',password('crspassword'),'Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','','','','',0,0,0,0);

FLUSH PRIVILEGES;
EOF

cat ${UPD} 
mysqld_safe --init-file=${UPD} &
sleep 10
/etc/init.d/mysql restart
rm ${UPD}

