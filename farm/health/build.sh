#!/bin/bash

cd com.ri.se.animalexamination
mvn package 
mvn install 
cd ..
cd com.ri.se.drugstreatments
mvn package 
mvn install 

cd ..
cd com.ri.se.generalhealthexamination
mvn package 
mvn install 

cd ..
cd ri.se.health
mvn package
