FROM ubuntu:22.04 as base-stage

USER root
ENV TZ=Europe/Stockholm
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

LABEL vnf="trapi" description="FORMAS TrAPI Controler" type="container"
MAINTAINER Abdul Ghafoor abdul.ghafoor@ri.se

RUN \
    apt-get -y update && \
    apt-get -y install curl unzip man wget telnet net-tools iputils-ping vim 

RUN apt-get install openjdk-8-jdk openjdk-8-jre -y

FROM base-stage as build-stage
COPY ./ /app/build

USER root
RUN apt-get -y install maven 

# Buidling all packages and then installing in maven repo
WORKDIR /app/build/com.ri.se.animalderegister
RUN mvn package && mvn install

WORKDIR /app/build/com.ri.se.animalreg
RUN mvn package && mvn install

WORKDIR /app/build/com.ri.se.breed
RUN mvn package && mvn install

WORKDIR /app/build/ri.se.animal
RUN mvn package
# End building all packages

# Building for running microservice
FROM base-stage as production-stage

USER root

ENV HOME /root
WORKDIR /home

RUN mkdir -p /home/app


WORKDIR /home/app

COPY --from=build-stage /app/build/ri.se.animal/target/ri.se.animal-1.0.0.jar /home/app
COPY --from=build-stage /app/build/ri.se.animal/config.yml /home/app
COPY runner.sh /home/app/runner.sh

EXPOSE 9002

CMD ./runner.sh
