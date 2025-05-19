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
# Building com.ri.se.aggregation maven project
##################################################
echo -e "\e[1;31m##################################################\e[0m"
echo " Building com.ri.se.aggregation"
proj=$home"com.ri.se.aggregation"
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
# Building com.ri.se.commonentities maven project
##################################################
echo -e "\e[1;31m##################################################\e[0m"
echo " Building com.ri.se.commonentities"
proj=$home"com.ri.se.commonentities"
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
home="/home/ag/Desktop/RISE/development/formas/farmms/"
df="/home/ag/Desktop/RISE/formasdevops/devops/formasms/"
====================== CP FILES Path =======================
# Copy jar/config file com.ri.se.temporarymovement/target/temporarymovement-1.0.0.jar
##################################################
dest=$df"temporarymovement/"
echo -e "\e[1;31m##################################################\e[0m"
proj=$home"com.ri.se.temporarymovement"
echo " Copy $proj/target/temporarymovement-1.0.0.jar"
cp $proj/target/temporarymovement-1.0.0.jar $dest
echo " Copy $proj/config.yml "
cp /home/ag/Desktop/RISE/development/formas/farmms/com.ri.se.temporarymovement/config.yml $dest
# Copy jar/config file com.ri.se.animalexamination/target/animalexamination-1.0.0.jar
##################################################
dest=$df"animalexamination/"
echo -e "\e[1;31m##################################################\e[0m"
proj=$home"com.ri.se.animalexamination"
echo " Copy $proj/target/animalexamination-1.0.0.jar"
cp $proj/target/animalexamination-1.0.0.jar $dest
echo " Copy $proj/config.yml "
cp /home/ag/Desktop/RISE/development/formas/farmms/com.ri.se.animalexamination/config.yml $dest
# Copy jar/config file com.ri.se.movebullforherd/target/movebullforherd-1.0.0.jar
##################################################
dest=$df"movebullforherd/"
echo -e "\e[1;31m##################################################\e[0m"
proj=$home"com.ri.se.movebullforherd"
echo " Copy $proj/target/movebullforherd-1.0.0.jar"
cp $proj/target/movebullforherd-1.0.0.jar $dest
echo " Copy $proj/config.yml "
cp /home/ag/Desktop/RISE/development/formas/farmms/com.ri.se.movebullforherd/config.yml $dest
# Copy jar/config file com.ri.se.drugstreatments/target/drugstreatments-1.0.0.jar
##################################################
dest=$df"drugstreatments/"
echo -e "\e[1;31m##################################################\e[0m"
proj=$home"com.ri.se.drugstreatments"
echo " Copy $proj/target/drugstreatments-1.0.0.jar"
cp $proj/target/drugstreatments-1.0.0.jar $dest
echo " Copy $proj/config.yml "
cp /home/ag/Desktop/RISE/development/formas/farmms/com.ri.se.drugstreatments/config.yml $dest
# Copy jar/config file com.ri.se.aggregation/target/aggregation-1.0.0.jar
##################################################
dest=$df"aggregation/"
echo -e "\e[1;31m##################################################\e[0m"
proj=$home"com.ri.se.aggregation"
echo " Copy $proj/target/aggregation-1.0.0.jar"
cp $proj/target/aggregation-1.0.0.jar $dest
echo " Copy $proj/config.yml "
cp /home/ag/Desktop/RISE/development/formas/farmms/com.ri.se.aggregation/config.yml $dest
# Copy jar/config file com.ri.se.feedpattern/target/feedpattern-1.0.0.jar
##################################################
dest=$df"feedpattern/"
echo -e "\e[1;31m##################################################\e[0m"
proj=$home"com.ri.se.feedpattern"
echo " Copy $proj/target/feedpattern-1.0.0.jar"
cp $proj/target/feedpattern-1.0.0.jar $dest
echo " Copy $proj/config.yml "
cp /home/ag/Desktop/RISE/development/formas/farmms/com.ri.se.feedpattern/config.yml $dest
# Copy jar/config file com.ri.se.animalderegister/target/animalderegister-1.0.0.jar
##################################################
dest=$df"animalderegister/"
echo -e "\e[1;31m##################################################\e[0m"
proj=$home"com.ri.se.animalderegister"
echo " Copy $proj/target/animalderegister-1.0.0.jar"
cp $proj/target/animalderegister-1.0.0.jar $dest
echo " Copy $proj/config.yml "
cp /home/ag/Desktop/RISE/development/formas/farmms/com.ri.se.animalderegister/config.yml $dest
# Copy jar/config file com.ri.se.generaldata/target/generaldata-1.0.0.jar
##################################################
dest=$df"generaldata/"
echo -e "\e[1;31m##################################################\e[0m"
proj=$home"com.ri.se.generaldata"
echo " Copy $proj/target/generaldata-1.0.0.jar"
cp $proj/target/generaldata-1.0.0.jar $dest
echo " Copy $proj/config.yml "
cp /home/ag/Desktop/RISE/development/formas/farmms/com.ri.se.generaldata/config.yml $dest
# Copy jar/config file com.ri.se.generalhealthexamination/target/generalhealthexamination-1.0.0.jar
##################################################
dest=$df"generalhealthexamination/"
echo -e "\e[1;31m##################################################\e[0m"
proj=$home"com.ri.se.generalhealthexamination"
echo " Copy $proj/target/generalhealthexamination-1.0.0.jar"
cp $proj/target/generalhealthexamination-1.0.0.jar $dest
echo " Copy $proj/config.yml "
cp /home/ag/Desktop/RISE/development/formas/farmms/com.ri.se.generalhealthexamination/config.yml $dest
# Copy jar/config file com.ri.se.ordersemen/target/ordersemen-1.0.0.jar
##################################################
dest=$df"ordersemen/"
echo -e "\e[1;31m##################################################\e[0m"
proj=$home"com.ri.se.ordersemen"
echo " Copy $proj/target/ordersemen-1.0.0.jar"
cp $proj/target/ordersemen-1.0.0.jar $dest
echo " Copy $proj/config.yml "
cp /home/ag/Desktop/RISE/development/formas/farmms/com.ri.se.ordersemen/config.yml $dest
# Copy jar/config file com.ri.se.semenutilization/target/semenutilization-1.0.0.jar
##################################################
dest=$df"semenutilization/"
echo -e "\e[1;31m##################################################\e[0m"
proj=$home"com.ri.se.semenutilization"
echo " Copy $proj/target/semenutilization-1.0.0.jar"
cp $proj/target/semenutilization-1.0.0.jar $dest
echo " Copy $proj/config.yml "
cp /home/ag/Desktop/RISE/development/formas/farmms/com.ri.se.semenutilization/config.yml $dest
# Copy jar/config file com.ri.se.animalreg/target/animalreg-1.0.0.jar
##################################################
dest=$df"animalreg/"
echo -e "\e[1;31m##################################################\e[0m"
proj=$home"com.ri.se.animalreg"
echo " Copy $proj/target/animalreg-1.0.0.jar"
cp $proj/target/animalreg-1.0.0.jar $dest
echo " Copy $proj/config.yml "
cp /home/ag/Desktop/RISE/development/formas/farmms/com.ri.se.animalreg/config.yml $dest
# Copy jar/config file com.ri.se.commonentities/target/commonentities-0.0.1-SNAPSHOT.jar
##################################################
dest=$df"commonentities/"
echo -e "\e[1;31m##################################################\e[0m"
proj=$home"com.ri.se.commonentities"
echo " Copy $proj/target/commonentities-0.0.1-SNAPSHOT.jar"
cp $proj/target/commonentities-0.0.1-SNAPSHOT.jar $dest
echo " Copy $proj/config.yml "
cp /home/ag/Desktop/RISE/development/formas/farmms/com.ri.se.commonentities/config.yml $dest
# Copy jar/config file com.ri.se.reportslaughterhouse/target/reportslaughterhouse-1.0.0.jar
##################################################
dest=$df"reportslaughterhouse/"
echo -e "\e[1;31m##################################################\e[0m"
proj=$home"com.ri.se.reportslaughterhouse"
echo " Copy $proj/target/reportslaughterhouse-1.0.0.jar"
cp $proj/target/reportslaughterhouse-1.0.0.jar $dest
echo " Copy $proj/config.yml "
cp /home/ag/Desktop/RISE/development/formas/farmms/com.ri.se.reportslaughterhouse/config.yml $dest

