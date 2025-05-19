#====================== SOURCE MAVEN PROJECTS Path =======================
#!/bin/bash
##################################################
# Building org.acreo.common maven project
##################################################
echo -e "\e[1;31m##################################################\e[0m"
echo " Building org.acreo.common"
proj="org.acreo.common"
cd $proj
mvn clean package
mvn install
cd ..
##################################################
# Building org.acreo.security maven project
##################################################
echo -e "\e[1;31m##################################################\e[0m"
echo " Building org.acreo.security"
proj="org.acreo.security"
cd $proj
mvn clean package
mvn install
cd ..
##################################################
# Building com.ri.se.dap maven project
##################################################
echo -e "\e[1;31m##################################################\e[0m"
echo " Building com.ri.se.dap"
proj="com.ri.se.dap"
cd $proj
mvn clean package
mvn install
cd ..

##################################################
# Building org.ri.se.ipfsj maven project
##################################################
echo -e "\e[1;31m##################################################\e[0m"
echo " Building org.ri.se.ipfsj"
proj="org.ri.se.ipfsj"
cd $proj
mvn clean package
mvn install
cd ..

##################################################
# Building org.ri.se.vt maven project
##################################################
echo -e "\e[1;31m##################################################\e[0m"
echo " Building org.ri.se.vt"
proj="org.ri.se.vt"
cd $proj
mvn clean package
mvn install
cd ..



##################################################
# Building org.ri.se.trace.api maven project
##################################################
echo -e "\e[1;31m##################################################\e[0m"
echo " Building org.ri.se.trace.api"
proj="org.ri.se.trace.api"
cd $proj
mvn clean package
cd ..
