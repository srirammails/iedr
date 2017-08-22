#!/bin/bash -x

DATEHR=`date "+%Y-%m-%d_%H"`
LOGFILE="logs/NRC_win_IE9_${DATEHR}.log"
LOGFILE2="logs/NRC_win_IE9_OUTPUT_${DATEHR}.log"

phpunit --log-tap ${LOGFILE} win_IE9 > ${LOGFILE2}
