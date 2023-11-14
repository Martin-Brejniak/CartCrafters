![CartCrafters Logo](https://github.com/Martin-Brejniak/CartCrafters/assets/77299294/2a516df4-c0f5-4fc3-8743-a26c7f5396c2)
CartCrafters is a RESTFUL auction website, akin to auctioning websites like eBay. An account is required to access the service, and items can be put up for sale under a Foward or Dutch auction. Developed in Java, and built on Apache Tomcat. 

# Installation
1. Ensure you have Apache Tomcat version 10.1 installed on your computer. If you don’t, you can get it from [here](https://tomcat.apache.org/download-90.cgi).
2. Download the project zip file, and extract it.
3. In your IDE, import an existing Maven project, and select the project.
4. Search through ***src*** folder, and find ***context.xml*** file within the **META-INF** folder.
5. Open the context.xml file with a text editor.
6. Change the ***url*** variables to match the path to the project. Ensure you use the following format:
```
jdbc:sqlite:<project-path>\CartCrafters\src\main\resources\<database-name>.db
```
7. Run the project.

# Auction Module Installation

The Auction server is a separate Maven project with its own pom.xml file. It uses springboot framework. To run the application:
1. Extract the zip file. Go to eclipse -> Import -> Import Existing Maven project. 
2. Root Directory: [your-installation-directory]\CartCrafters\Auction\AuctionModule
3.	Go to the package com.example.auctionserver.controller -> CraftCraftersApplication.java.
4.	Right Click –> Run as -> Java Application. The spring application should start in port 8080.
   
# SQL Database for Auction Module
1. The path to the database is specified in the application.properties file in:
 [your-installation-directory]\CartCrafters\Auction\AuctionModule\src\main\resources
2. We have already put the auction.db database in the root directory of the Auction server (in the same path that Auction servers pom.xml is located)

# Troubleshooting
Should you run into any issues, try these methods:
- Right click the main project folder in your IDE, click ***Maven***, and then ***Update Project***.
- Right click the main project folder in your IDE, click ***Run As***, and then ***Maven Install***.

If these methods fail, do not hesistant to reach out to us. We'd be happy to help!

# Troubleshooting (Auction Module)
If for some reason, the auction.db cannot be located, then we have provided the SQL scripts to create a auction table in the auction.db and populate with about 20 rows. Once you have created the db, you can put it in the same directory as the pom.xml of the auction server and double check with the specified path in the application.properties file mentioned above. 

# Requirements Document
Can be found [here](https://docs.google.com/document/d/1ZWlmvLkMUWDauz06uMcv-5dzCzevhOUv9Qs2rIAS454/edit?usp=sharing).

# Authors
- Martin Brejniak (martin.brejniak@bell.net)
- Abeed Arefin (abeed09@my.yorku.ca)
- Anika Prova (anika98@my.yorku.ca)
- Madison Hartley (maddi15@my.yorku.ca)
