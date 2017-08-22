scp artifacts/crsdb.sql root@172.19.247.184:crsdb.sql
ssh root@172.19.247.184 'mysql -Dcrsdb -u root --password=iedr </root/crsdb.sql'
