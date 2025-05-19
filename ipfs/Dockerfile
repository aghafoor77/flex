FROM ubuntu:18.04

LABEL vnf="fic" description="FORMAS IPFS Controler" type="container"
MAINTAINER Abdul Ghafoor abdul.ghafoor@ri.se

RUN \
    apt-get -y update && \
    apt-get -y install golang-go

RUN apt-get install openjdk-8-jdk openjdk-8-jre -y

#RUN wget https://dist.ipfs.io/go-ipfs/v0.8.0/go-ipfs_v0.8.0_linux-amd64.tar.gz
COPY go-ipfs_v0.5.1_linux-amd64.tar.gz go-ipfs_v0.5.1_linux-amd64.tar.gz

RUN tar xvfz go-ipfs_v0.5.1_linux-amd64.tar.gz
RUN mv go-ipfs/ipfs /usr/local/bin/ipfs

ENV HOME /root
WORKDIR /home

RUN mkdir -p /home/ipfs
COPY runner.sh /home/ipfs/runner.sh
#COPY org.ri.se.icc-0.0.1-SNAPSHOT.jar /home/ipfs/org.ri.se.icc-0.0.1-SNAPSHOT.jar
#COPY hostconfig.json /home/ipfs/hostconfig.json
#COPY myip.cfg /home/app/myip.cfg

WORKDIR /home/ipfs
#RUN ifconfig


EXPOSE 4001
EXPOSE 5001
EXPOSE 8080

#RUN ./runner.sh



