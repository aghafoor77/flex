#!/bin/bash

echo "Running ./ganache-cli - command"
ganache-cli -e 10000000000 -h 0.0.0.0 -g 2000 -a 25 | tee -a /home/app/data/ganachelog.txt
