#!/bin/bash

ifconfig
echo "Running centralhostregistration - command"
java -jar com.ri.se.centralhostregistration-1.0.0.jar server config.yml
