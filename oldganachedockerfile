FROM ubuntu:18.04

LABEL vnf="fgc" description="FORMAS Ganache Controler" type="container"
MAINTAINER Abdul Ghafoor abdul.ghafoor@ri.se

RUN \
    apt-get -y update && \
    apt-get -y install curl man wget telnet net-tools iputils-ping vim

RUN apt-get -y update 
RUN apt-get install openjdk-8-jre -y

RUN curl -fsSL https://deb.nodesource.com/setup_12.x |  bash -
RUN apt-get install -y nodejs
RUN npm install -g ganache-cli

ENV HOME /root
WORKDIR /home


RUN mkdir -p /home/ganashe
COPY instalganashe.sh /home/ganashe/instalganashe.sh
COPY runner.sh /home/ganashe/runner.sh
COPY org.ri.se.icc-0.0.1-SNAPSHOT.jar /home/ganashe/org.ri.se.icc-0.0.1-SNAPSHOT.jar
COPY hostconfig.json /home/ganashe/hostconfig.json
COPY myip.cfg /home/app/myip.cfg
COPY keyconfig.json /home/ganashe/keyconfig.json
COPY te.sh /home/ganashe/te.sh

WORKDIR /home/ganashe


RUN ifconfig
EXPOSE 8545
RUN echo "===COMMAND to OPEN SHELL ===>           sudo docker exec -it cganache bash" 



