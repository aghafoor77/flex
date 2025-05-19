To deploy smart contract:
1. Open the project org.ri.se.eth
2. Find the ManageEther file 
3. Configure following properties in file EtherProps.java
	// Folder where key pair of all users will be stored  
	private String credentialsPath = "/home/blockchain/Desktop/tvsp/traceability/org.ri.se.trace/src/main/resources";
	// Password to protect the key pair
	private String password = "1122334455";
	// Username of the user correspond to the password. It will be used to create a new folder for storing keypair 
	private String username = "abdul";
	// IP address of the Ehterum node
	private String etherURL = "http://localhost:8545";
	// This is only used for Ganache. It is the output of the Ganacge command prompt. 
	private String storePath = "/home/blockchain/eth/store.txt";
4. Execute ManageEther
5. Smart contract address will be shown on the command prompt. 
6. Copy smart contract address  

Test [NOT RECOMMNEDED FOR PRODUCTION VERSION]:
In order to test, you can perform a transaction by executing TraceabilityTest from project org.ri.se.trace.test.
1. Provide smart contract address as a command prompt argument
2. Check the main methods for various tests 
3. Check the attributes and change them accordingly 
2. Execute TraceabilityTest

Execute API (TVSP)
1- Required Software:
- java SDK > 1.8 v 
- Install mysql server 
Configure TVSP
2. Open config.yml and set values for 
	tapi:
	   urlEther: <http://localhost:8545?
	   urlIPFS: </ip4/127.0.0.1/tcp/5001?
	   walletDir: </home/blockchain/eth/dapp/dev/eth.java.lib/src/main/resources/?
	   traceContract: <0x4558e77686c23f925344b72426bf53667fcf4f30>
	 
	 Also check database properties 
	 database:
	  driverClass: com.mysql.jdbc.Driver
	  url: jdbc:mysql://localhost:3306/<evidence>?createDatabaseIfNotExist=true
	  user: <root?
	  password: <12345678>  
3. Carefully recheck EtherProps.java. this is required by the deposit function
4. 
  