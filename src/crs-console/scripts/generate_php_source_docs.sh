#!/bin/bash

function die() { echo $1; exit 1 ; }

SRCDIR=/var/www/trunk/src/www
DOCDIR=/var/www/trunk/doc/phpdoc

[ -f `which phpdoc` ] || die "executable not found: phpdoc"
[ -d ${DOCDIR} ] || die "no src-dir ${SRCDIR}"
cd ${SRCDIR} || die "can't cd to dir ${SRCDIR}"

phpdoc -s -is -pp -ue -po IEDR_New_Registrars_Console -t ${DOCDIR} -d ${SRCDIR}/protected -o HTML:frames:DOM/earthli 2>&1 > $DOCDIR/phpdoc.log
