
---Files:

•	keyconfig.json: This configuration file is used to read ganache default key pairs and sends to the specified URL. The recipient URL points the central host registration. Recommended to only change the url and keep rest attribute same.   
{
   "url":"http://192.168.10.115:9090/application/v1/centralhostregistration/keys",
   "required":"keyfile",
   "key":"store.txt",
   "value":null
}

•	hostconfig.json: This configuration file is used to register IP address in the central registration authority. you may change the name of your host but keep in mind the name of host specified here must be same which you will specify dockar execution command to launch container. In the url specify the URL of the central host registration.     
{
   "url":"http://192.168.10.115:9090/application/v1/centralhostregistration",
   "required":"hostname",
   "key":"ganachectrl",
   "value":null
}
•	rmc.sh: Stops and removes docker container specified in the container variable.
container="cganache"
 
•	clear.sh: If you update container name or image name then you need to update this file and change first the value of first two variables. 

image="iganache"
container="cganache"
  
•	deploy.sh: This file will build docker image, launch container with specific hosts. you may change the value of following variables:

image="iganache"
container="cganache"
host="ganachectrl" 

•	Dockerfile: This is the main docker file and does not require any modification so please do not change this file.
•	hotrunner.sh: This script will execute java file (jar) which will use hostconfig.json to register IP address of this container to the central host registration. It will also use keyconfig.json for uploading EC Account credentials on the central locations to exchange VTT.   

============================================ READ HERE CARE FULLY ====================================================
•	instalganashe.sh: This will be used to execute ganache. Please CHECK that the IP address in the command and it should be the same as container will be.
============================================ READ HERE CARE FULLY ====================================================

  
•	org.ri.se.icc-***.jar File: This is a java application which will register host and uploads EC Account credentials on central host registration application.


Run: 
Prereq. Execute CentralHostRegistration microservice.

I set values of following variables in the configuration files and scripts 
image="iganache"
container="cganache"
host="ganachectrl" 

Current Directory: Location where all above files reside. 
Move to the current directory and then execute 
/ganache$ ./deply.sh 
command. It will build image and executes container. if successfully executed then it will open a terminal. Execute ls command so it will show jar fie, keyconfig.json, hostconfig.json, hotrunner.sh and instalganashe.sh. You need to execute 
root@ganachectrl:/home/ganashe#./hotrunner.sh 
in the container shell. 
After that open a new terminal and execute following command. 
/ganache$ sudo docker exec -it cganache bash
It will open a terminal of container.
Now you can lunch ganache with the following command 
root@ganachectrl:/home/ganashe#./instalganashe.sh

