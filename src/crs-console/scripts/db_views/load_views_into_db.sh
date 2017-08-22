#!/bin/bash

# db conection parameters
db_host="10.10.128.102"
db_root_user="root"
db_root_pass="rootpass"
db_schema="phoenixdb"

MYSQL="/usr/bin/mysql -h ${db_host} -u ${db_root_user} --password=${db_root_pass} -D ${db_schema}"

echo $MYSQL
echo "select curdate()" | $MYSQL || exit 1

# iterate over view sql files :

svn update

ls nrc_*.sql | while read F ; do
    echo "running svn-update for $F .."
	echo "loading $F .."
	echo "source $F; show errors;" | $MYSQL && { echo -e " .. OK" ; } || { echo " ERR" ; }
    done
