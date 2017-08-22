./buildWars.sh > logs/buildWars.log &

./stopTomcat.sh 172.19.243.29 &
./stopTomcat.sh 172.19.243.30 &
./stopTomcat.sh 172.19.243.31 &

echo `jobs -l` 

for job in `jobs -p`; do  
  wait $job
  echo "$job ended"
done

sleep 5

echo "Copy war files"
# database needs to be feeded manually!
#./feedDb.sh 172.19.247.184 &
./copyWar.sh crs-main-web 172.19.243.30 &
./copyWar.sh crs-scheduler 172.19.243.30 &
./copyWar.sh crs-web-services 172.19.243.31 &
./copyWar.sh crs-iedr-api 172.19.243.29 &
./copyWar.sh crs-realex-mock 172.19.247.182 &

echo `jobs -l` 

for job in `jobs -p`; do  
  wait $job
  echo "$job ended"
done

./startTomcat.sh 172.19.243.29 &
./startTomcat.sh 172.19.243.30 &
./startTomcat.sh 172.19.243.31 &

echo `jobs -l` 

for job in `jobs -p`; do  
  wait $job
  echo "$job ended"
done
