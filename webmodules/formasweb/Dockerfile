FROM ubuntu:22.04 as base-stage

USER root
ENV TZ=Europe/Stockholm
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

LABEL vnf="kd" description="KD Controller" type="container"
MAINTAINER Abdul Ghafoor abdul.ghafoor@ri.se

RUN \
    apt-get -y update && \
    apt-get -y install curl unzip man wget telnet net-tools iputils-ping vim nodejs npm


FROM base-stage as build-stage

USER root

# Building for running microservice
FROM base-stage as development-stage

USER root

ENV HOME /root
WORKDIR /home

RUN mkdir -p /home/app

WORKDIR /home/app

COPY ./farmdash/tempdash/ /home/app

WORKDIR /home/app/tempdash
COPY ./runner.sh /home/app/tempdash/runner.sh
RUN npm rebuild node-sass
RUN npm install --legacy-peer-deps

EXPOSE 3000

CMD ./runner.sh

