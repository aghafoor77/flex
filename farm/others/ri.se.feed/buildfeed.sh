#====================== SOURCE MAVEN PROJECTS Path =======================
#!/bin/bash
cd ..
##################################################
# Building com.ri.se.feedpattern maven project
##################################################
echo -e "\e[1;31m##################################################\e[0m"
echo " Building com.ri.se.feedpattern"
proj="com.ri.se.feedpattern"
cd $proj
mvn clean package
mvn install
cd ..
##################################################
# Building com.ri.se.feedtype maven project
##################################################
echo -e "\e[1;31m##################################################\e[0m"
echo " Building com.ri.se.feedtype"
proj="com.ri.se.feedtype"
cd $proj
mvn clean package
mvn install
cd ..
##################################################
# Building com.ri.se.temporarymovement maven project
##################################################
echo -e "\e[1;31m##################################################\e[0m"
echo " Building com.ri.se.temporarymovement"
proj="com.ri.se.temporarymovement"
cd $proj
mvn clean package
mvn install
cd ..
##################################################
# Building ri.se.animal maven project
##################################################
echo -e "\e[1;31m##################################################\e[0m"
echo " Building ri.se.feed"
proj="ri.se.feed"
cd $proj
mvn clean package
cd ..