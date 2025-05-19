#====================== SOURCE MAVEN PROJECTS Path =======================
#!/bin/bash
cd ..
##################################################
# Building com.ri.se.animalderegister maven project
##################################################
echo -e "\e[1;31m##################################################\e[0m"
echo " Building com.ri.se.animalderegister"
proj="com.ri.se.animalderegister"
cd $proj
mvn clean package
mvn install
cd ..
##################################################
# Building com.ri.se.animalreg maven project
##################################################
echo -e "\e[1;31m##################################################\e[0m"
echo " Building com.ri.se.animalreg"
proj="com.ri.se.animalreg"
cd $proj
mvn clean package
mvn install
cd ..
##################################################
# Building com.ri.se.breed maven project
##################################################
echo -e "\e[1;31m##################################################\e[0m"
echo " Building com.ri.se.breed"
proj="com.ri.se.breed"
cd $proj
mvn clean package
mvn install
cd ..
##################################################
# Building ri.se.animal maven project
##################################################
echo -e "\e[1;31m##################################################\e[0m"
echo " Building ri.se.animal"
proj="ri.se.animal"
cd $proj
mvn clean package
cd ..