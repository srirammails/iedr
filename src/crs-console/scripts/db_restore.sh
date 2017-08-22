#!/bin/bash

#SRCF=/home/billyg/mysql-backup.20110303.tgz
#SRCF=/home/billyg/mysql-hot-backups.tgz
SRCF=/home/billyg/mysql-backup.20110510.tgz

INNODIR=/var/lib/innodb-tables
EXPDIR=/export/mysql-hot-backups
HOTBACKDIR=/var/ie-domain/Innodb-Hot-Backup
MYSQLDIR=/var/lib/mysql
LOGDIR=/var/log/mysql

function heading() { echo -e "#######################\n## `date` # $1" ; }
function die() { echo "Err: $1" ; exit 1 ; }

###########################
heading "check files"

mkdir -p ${EXPDIR}

[ -f ${SRCF}		] || die "file not found : ${SRCF}"
[ -x ${HOTBACKDIR}/ibbackup ] || die "file not found executable : ${HOTBACKDIR}/ibbackup"
[ -d ${INNODIR} 	] || die "dir not found : ${INNODIR}"
[ -d ${MYSQLDIR} 	] || die "dir not found : ${MYSQLDIR}"
[ -d ${EXPDIR}		] || die "dir not found : ${EXPDIR}"
[ -d ${HOTBACKDIR}	] || die "dir not found : ${HOTBACKDIR}"

grep "^innodb_data_home_dir = ${EXPDIR}$" ${HOTBACKDIR}/mg01-hotbackups.cnf || die "check config !"
grep "^innodb_log_group_home_dir = ${EXPDIR}$" ${HOTBACKDIR}/mg01-hotbackups.cnf || die "check config !"
grep "^innodb_log_arch_dir = ${EXPDIR}$" ${HOTBACKDIR}/mg01-hotbackups.cnf || die "check config !"

###########################
heading "stop db"

/etc/init.d/mysql stop
sleep 5
kill -9 `pidof mysql` `pidof mysqld`

###########################
heading "clean up"

[ "${MYSQLDIR}/" = "/" ] && die "can't delete '/'" ; rm -rf ${MYSQLDIR}/*
[ "${EXPDIR}/"   = "/" ] && die "can't delete '/'" ; rm -rf ${EXPDIR}/*
[ "${INNODIR}/"  = "/" ] && die "can't delete '/'" ; rm -rf ${INNODIR}/*

mkdir -p ${EXPDIR}/data || die "dir not created ${EXPDIR}/data"
: > ${LOGDIR}/query.log

###########################
heading "unpack backup"

rm -rf $EXPDIR/*
cd /export/ ; tar -xvzf ${SRCF} || die "failed untarring ${SRCF}"

[ -d /var/export/export/mysql-hot-backups ] && mv /var/export/export/mysql-hot-backups /var/export/mysql-hot-backups

[ -d ${EXPDIR} ] || die "not created : ${EXPDIR}"

###########################
#heading "check output exists"

#[ -f ${EXPDIR}/ibbackup_logfile ] || die "file not found : ${EXPDIR}/ibbackup_logfile"
#[ -f ${EXPDIR}/non-innodb/mysql-backups.tar ] || die "file not found : ${EXPDIR}/non-innodb/mysql-backups.tar"
[ -f ${EXPDIR}/phoenixdb.ibz ] || die "file not found : ${EXPDIR}/phoenixdb.ibz"

###########################
heading "prepare hot backup"

cd ${HOTBACKDIR}/
./ibbackup --apply-log --uncompress mg01-hotbackups.cnf || die "failed preparing hot backup"
## License A10107 is granted to David Curtin
## Licensed for use in a computer whose hostname is 'db-deg.domainregistry.ie'

###########################
heading "check new files exists"

[ -f ${EXPDIR}/ib_logfile0 ] || die "file not found : ${EXPDIR}/ib_logfile0"
[ -f ${EXPDIR}/phoenixdb   ] || die "file not found : ${EXPDIR}/phoenixdb"

###########################
heading "copy processed files"

cp ${EXPDIR}/phoenixdb ${INNODIR}/   || die "failed copying ${EXPDIR}/phoenixdb to ${INNODIR}/"
cp ${EXPDIR}/ib_log* ${MYSQLDIR}/ || die "failed copying ${EXPDIR}/ib_log* to ${MYSQLDIR}/"

###########################
heading "copy other files"

cd / ; tar -xvf ${EXPDIR}/non-innodb/mysql-backups.tar || die "failed untarring ${EXPDIR}/non-innodb/mysql-backups.tar"

###########################
heading "change perms"

chown -R mysql:mysql ${MYSQLDIR} ${INNODIR} ${EXPDIR} ${LOGDIR}
chmod -R o=rwX,g=rwX,o= ${MYSQLDIR} ${INNODIR} ${EXPDIR} ${LOGDIR}

###########################
heading "start server"

/etc/init.d/mysql start

