#====================== SOURCE MAVEN PROJECTS Path =======================
#!/bin/bash
cd ..
##################################################
# Building com.ri.se.animalexamination maven project
##################################################
echo -e "\e[1;31m##################################################\e[0m"
echo " Building com.ri.se.animalexamination"
proj="com.ri.se.animalexamination"
cd $proj
mvn clean package
mvn install
cd ..
##################################################
# Building com.ri.se.drugstreatments maven project
##################################################
echo -e "\e[1;31m##################################################\e[0m"
echo " Building com.ri.se.drugstreatments"
proj="com.ri.se.drugstreatments"
cd $proj
mvn clean package
mvn install
cd ..
##################################################
# Building com.ri.se.generalhealthexamination maven project
##################################################
echo -e "\e[1;31m##################################################\e[0m"
echo " Building com.ri.se.generalhealthexamination"
proj="com.ri.se.generalhealthexamination"
cd $proj
mvn clean package
mvn install
cd ..
##################################################
# Building ri.se.health maven project
##################################################
echo -e "\e[1;31m##################################################\e[0m"
echo " Building ri.se.health"
proj="ri.se.health"
cd $proj
mvn clean package
cd ..