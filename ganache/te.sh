#!/bin/bash

ip=$(ifconfig | grep -oP "(?<=inet addr:).*?(?=Bcast)")
ping $ip

