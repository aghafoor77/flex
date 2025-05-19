#!/bin/bash
image="iganache"
container="cganache"
host="ganachectrl"
echo "========================================================"
echo "Executing commands for Building and Running ganache image and container . . . "
echo "1.  Building Image"
echo "2.  Deploying container"
echo "3.  Running Terminal"
echo "========================================================"
echo ""
echo "________________________________________________________"
echo "1. Building ganache image !"
sudo docker build -t $image .
echo ""
echo "________________________________________________________"
echo "2. Deploying container !"
sudo docker run -p 8545:8545 -d --name $container -h $host -i -t $image /bin/bash
echo ""
echo "________________________________________________________"
echo "3. Running terminal !"
echo "Done"
echo "========================================================"
echo ""
echo "" 
sudo docker attach $container

