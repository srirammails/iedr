#!/bin/bash -x

DATEHR=`date "+%Y-%m-%d_%H"`
LOGFILE="logs/NRC_readOnly_${DATEHR}.log"
LOGFILE2="logs/NRC_readOnly_OUTPUT_${DATEHR}.log"

phpunit --log-tap ${LOGFILE} read_only_Tests > ${LOGFILE2}
