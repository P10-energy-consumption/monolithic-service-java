#!/bin/bash

idle=450
clients=7200
end=$((SECONDS+$idle+$clients+$idle))

echo "Starting benchmark"
./TestSystemBuildAndKillDocker.sh &
# Wait for Docker containers to start
sleep 60

echo "Begin collecting Docker stats now for $end seconds."
while [ $SECONDS -lt $end ]; do docker stats --no-stream | tee -a StatsDockerSingle.txt; done &

echo "Starting rapl.rs"
raplrs benchmark bash TestSystemJMeterRunTestPlan.sh

echo "Done"