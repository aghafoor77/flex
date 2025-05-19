#====================== SOURCE MAVEN PROJECTS Path =======================
#!/bin/bash
cd ..
##################################################
# Building com.ri.se.movebullforherd maven project
##################################################
echo -e "\e[1;31m##################################################\e[0m"
echo " Building com.ri.se.movebullforherd"
proj="com.ri.se.movebullforherd"
cd $proj
mvn clean package
mvn install
cd ..
##################################################
# Building com.ri.se.ordersemen maven project
##################################################
echo -e "\e[1;31m##################################################\e[0m"
echo " Building com.ri.se.ordersemen"
proj="com.ri.se.ordersemen"
cd $proj
mvn clean package
mvn install
cd ..
##################################################
# Building com.ri.se.semenutilization maven project
##################################################
echo -e "\e[1;31m##################################################\e[0m"
echo " Building com.ri.se.semenutilization"
proj="com.ri.se.semenutilization"
cd $proj
mvn clean package
mvn install
cd ..
##################################################
# Building ri.se.prebirth maven project
##################################################
echo -e "\e[1;31m##################################################\e[0m"
echo " Building ri.se.prebirth"
proj="ri.se.prebirth"
cd $proj
mvn clean package
cd ..