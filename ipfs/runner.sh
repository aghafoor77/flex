#!/bin/bash

ipfs init | tee -a /home/app/data/ipfslog.txt
ipfs config Addresses.API /ip4/0.0.0.0/tcp/5001
ipfs config Addresses.Gateway /ip4/0.0.0.0/tcp/9001
echo "Running IPFS - command"
ipfs daemon
