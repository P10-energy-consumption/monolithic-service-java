#!/bin/bash

idle=450
clients=7200
setup=30
setup_teardown=$setup
good_measure=300
end=$((SECONDS+$idle+$setup+$clients+$setup_teardown+$good_measure+$idle))


./TestSystemDockerBuildAndRun.sh
# Stop docker container after benchmark is done.
sleep $end
docker kill $(docker ps -q)