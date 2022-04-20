#!/bin/bash

idle=450
clients=7200
setup=30
setup_teardown=$setup
good_measure=300
end=$((SECONDS+$idle+$setup+$clients+$setup_teardown+$good_measure+$idle))

echo "Starting benchmark"
./TestSystemBuildAndKillDocker.sh
# Wait for Docker containers to start
sleep 60

echo "Begin collecting Docker stats now for $end seconds."
while [ $SECONDS -lt $end ]; do docker stats --no-stream | tee -a StatsDockerSingle.txt; sleep 1; done &

echo "Starting rapl.rs"
raplrs benchmark bash TestSystemJMeterRunTestPlan.sh

echo "Done"