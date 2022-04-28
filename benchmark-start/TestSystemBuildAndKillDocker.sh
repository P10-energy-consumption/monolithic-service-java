#!/bin/bash

idle=450
clients=7200
# Add extra time to make sure benchmark is truely done.
# TestSystemRunAllBenchmarks gives 10 minute leeway.
extra_time=300
end=$((SECONDS+$idle+$clients+$idle+$extra_time))

# Build and run Docker container.
# NOTE: This shell script starts a subprocess.
# Immediately goes to the following sleep command.
./TestSystemDockerBuildAndRun.sh

# Stop docker container after benchmark is done.
sleep $end
docker kill $(docker ps -q)