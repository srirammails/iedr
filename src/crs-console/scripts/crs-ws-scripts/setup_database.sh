#!/bin/bash

cat resources/db/development/crsdb_mini_camelcase.sql | mysql -h devj-db -u root --password='rootpass'
echo "grant all on crsdb.* to crsuser@'%' identified by 'crspassword';"  | mysql -h devj-db -u root --password='rootpass'


