#====================== SOURCE MAVEN PROJECTS Path =======================
#!/bin/bash
##################################################
# Building ri.se.farm maven project
##################################################
echo -e "\e[1;31m##################################################\e[0m"
echo " Building ri.se.farm"
proj="ri.se.farm"
cd $proj
mvn clean package
cd ..