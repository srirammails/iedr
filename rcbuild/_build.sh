#!/bin/bash

export MAVEN_OPTS="-Xmx512M -XX:MaxPermSize=512M" 
mkdir logs
rm -fR artifacts
mkdir artifacts

./copyRemoteFiles.sh &
./buildNrc.sh > logs/buildNrc.log &
./buildAndDeployCrs.sh &

echo `jobs -l` 

for job in `jobs -p`; do  
  wait $job
  echo "$job ended"
done

./installNrc.sh 172.19.243.33

