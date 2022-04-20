#!/bin/bash

idle=450
sleep ${idle}
/home/usr/Downloads/apache-jmeter-5.4.3/bin/jmeter -n -t "LoadTesting.jmx"
sleep ${idle}