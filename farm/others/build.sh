#====================== SOURCE MAVEN PROJECTS Path =======================
#!/bin/bash
home="/home/ag/Desktop/RISE/development/formas/farmms/"
# Building com.ri.se.temporarymovement maven project
##################################################
echo -e "\e[1;31m##################################################\e[0m"
echo " Building com.ri.se.temporarymovement"
proj=$home"com.ri.se.temporarymovement"
cd $proj
mvn package
##################################################
# Building com.ri.se.animalexamination maven project
##################################################
echo -e "\e[1;31m##################################################\e[0m"
echo " Building com.ri.se.animalexamination"
proj=$home"com.ri.se.animalexamination"
cd $proj
mvn package
##################################################
# Building com.ri.se.movebullforherd maven project
##################################################
echo -e "\e[1;31m##################################################\e[0m"
echo " Building com.ri.se.movebullforherd"
proj=$home"com.ri.se.movebullforherd"
cd $proj
mvn package
##################################################
# Building com.ri.se.drugstreatments maven project
##################################################
echo -e "\e[1;31m##################################################\e[0m"
echo " Building com.ri.se.drugstreatments"
proj=$home"com.ri.se.drugstreatments"
cd $proj
mvn package
##################################################
# Building com.ri.se.feedpattern maven project
##################################################
echo -e "\e[1;31m##################################################\e[0m"
echo " Building com.ri.se.feedpattern"
proj=$home"com.ri.se.feedpattern"
cd $proj
mvn package
##################################################
# Building com.ri.se.animalderegister maven project
##################################################
echo -e "\e[1;31m##################################################\e[0m"
echo " Building com.ri.se.animalderegister"
proj=$home"com.ri.se.animalderegister"
cd $proj
mvn package
##################################################
# Building com.ri.se.generaldata maven project
##################################################
echo -e "\e[1;31m##################################################\e[0m"
echo " Building com.ri.se.generaldata"
proj=$home"com.ri.se.generaldata"
cd $proj
mvn package
##################################################
# Building com.ri.se.generalhealthexamination maven project
##################################################
echo -e "\e[1;31m##################################################\e[0m"
echo " Building com.ri.se.generalhealthexamination"
proj=$home"com.ri.se.generalhealthexamination"
cd $proj
mvn package
##################################################
# Building com.ri.se.ordersemen maven project
##################################################
echo -e "\e[1;31m##################################################\e[0m"
echo " Building com.ri.se.ordersemen"
proj=$home"com.ri.se.ordersemen"
cd $proj
mvn package
##################################################
# Building com.ri.se.semenutilization maven project
##################################################
echo -e "\e[1;31m##################################################\e[0m"
echo " Building com.ri.se.semenutilization"
proj=$home"com.ri.se.semenutilization"
cd $proj
mvn package
##################################################
# Building com.ri.se.animalreg maven project
##################################################
echo -e "\e[1;31m##################################################\e[0m"
echo " Building com.ri.se.animalreg"
proj=$home"com.ri.se.animalreg"
cd $proj
mvn package
##################################################
# Building com.ri.se.reportslaughterhouse maven project
##################################################
echo -e "\e[1;31m##################################################\e[0m"
echo " Building com.ri.se.reportslaughterhouse"
proj=$home"com.ri.se.reportslaughterhouse"
cd $proj
mvn package
##################################################

