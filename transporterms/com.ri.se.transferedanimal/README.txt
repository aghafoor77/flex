

		RISE REST BUILDER v1.0:
Please make sure that following software are already installed:
	- Java Development Kit 1.8 or higher verion 
	- Maven
	- Database (tested with mysql)
1. Open command prompt/terminal/console.
2. Move to folloiwng path.
	/home/ag/Desktop/RISE/restout/com.ri.se.transferedanimal/
3. Execute following command. 
	mvn package 
It will compile the source code and you will see folloiwng meesage on console 
			 BUILD SUCCESS 
4. Update configuration file.
	Open /home/ag/Desktop/RISE/restout/com.ri.se.transferedanimal/config.yml file and update following database parameters.
			driverClass: <com.mysql.jdbc.Driver>
			url: <jdbc:mysql://localhost:3306/riserestbuilder?createDatabaseIfNotExist=true&enabledTLSProtocols=TLSv1.2>
			user: <USER>
			password: <PASSWORD>
5. Deploy REST API.
	Execute following command to deploy REST API Services.
	java -cp target/transferedanimal-1.0.0-jar-with-dependencies.jar com.ri.se.transferedanimal.TransferedAnimalApplication server config.yml
6. Open API Documentation.
	Open following URL in the browser and you can see the swagger documentation !
			http://localhost:8080/swagger:
