#!/bin/bash

n=1
jmxname=monotest

echo "Starting $testName..."

#echo "Starting performance monitor..."
#logman start 'Petstore Monitor'

#echo "Starting Intel Power Gadget logging..."
#IntelPowerGadget.exe -start

echo "Sleeping for $n seconds before starting JMeter test..."
sleep "${n}"

#echo "Starting JMeter $jmxname test..."
#jmeter -n -t "Monolith Load Testing.jmx"

echo "Starting rapl.rs"
raplrs benchmark bash jmeter_start.sh

echo "Sleeping for $n seconds before ending test..."
sleep "${n}"

#echo "Stopping performance monitor..."
#logman stop 'Petstore Monitor'

#echo "Stopping Intel Power Gadget..."
#IntelPowerGadget.exe -stop
