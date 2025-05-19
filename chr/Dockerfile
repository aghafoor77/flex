FROM ubuntu:20.04

LABEL vnf="fic" description="FORMAS centralhostregistration Controler" type="container"
MAINTAINER Abdul Ghafoor abdul.ghafoor@ri.se

RUN \
    apt-get -y update && \
    apt-get -y install curl unzip man wget telnet net-tools iputils-ping vim

RUN apt-get -y update 
RUN apt-get install openjdk-8-jre -y


ENV HOME /root
WORKDIR /home

RUN mkdir -p /home/app

COPY com.ri.se.centralhostregistration-1.0.0.jar /home/app/com.ri.se.centralhostregistration-1.0.0.jar
COPY config.yml /home/app/config.yml
COPY runner.sh /home/app/runner.sh

WORKDIR /home/app

EXPOSE 9090




