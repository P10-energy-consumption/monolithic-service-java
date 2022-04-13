#!/bin/bash

clients=7200
setup_teardown=30
idle=300
end=$((SECONDS+$idle+$clients+$idle+$setup_teardown))
echo "Begin collecting Docker stats now for $end seconds."
while [ $SECONDS -lt $end ]; do docker stats --no-stream | tee -a stats_docker_monolith.txt; sleep 1; done &

echo "Sleeping for $idle seconds before starting JMeter test..."
sleep "${idle}"
echo "Starting JMeter test plan"
/home/usr/Downloads/apache-jmeter-5.4.3/bin/jmeter -n -t "LoadTesting.jmx"
echo "Sleeping for $idle seconds before ending benchmark..."
sleep "${idle}"
echo "jmeter_start.sh done"
