#!/bin/bash

G=apache
BP=/var/www/trunk/src
WP=${BP}/www
RP=${WP}/protected/runtime
AD=${WP}/assets

function die() {
    echo "Err: $1" ; exit 1
    }

[ -d ${BP} ] || die "dir not found : $BP"
[ -d ${WP} ] || die "dir not found : $WP"
[ -d ${RP} ] || die "dir not found : $RP"

echo "sudo for chown:"
sudo chown -R :${G} . ${WP}
find ${BP} -type d -exec chmod 0770 {} \;
find ${BP} -type f -exec chmod 0660 {} \;
find ${BP} -type f -name \*\.sh -exec chmod 0750 {} \;

find ${RP} -type d -exec chmod 0770 {} \;
find ${RP} -type f -exec chmod 0660 {} \;

find ${AD} -type d -exec chmod 0770 {} \;
find ${AD} -type f -exec chmod 0660 {} \;
