#!/bin/bash

cd com.ri.se.animalderegister
mvn package 
mvn install 
cd ..
cd com.ri.se.animalreg
mvn package 
mvn install 

cd ..
cd com.ri.se.assignanimal
mvn package 
mvn install 


cd ..
cd com.ri.se.assignanimalstatus
mvn package 
mvn install

cd ..
cd com.ri.se.breed
mvn package 
mvn install


cd ..
cd ri.se.animal
mvn package
