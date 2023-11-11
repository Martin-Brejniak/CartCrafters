![CartCrafters Logo](https://github.com/Martin-Brejniak/CartCrafters/assets/77299294/2a516df4-c0f5-4fc3-8743-a26c7f5396c2)
CartCrafters is a RESTFUL auction website, akin to auctioning websites like eBay. An account is required to access the service, and items can be put up for sale under a Foward or Dutch auction. Developed in Java, and built on Apache Tomcat. 

# Installation
1. Ensure you have Apache Tomcat version 10.1 installed on your computer. If you don’t, you can get it from [here](https://tomcat.apache.org/download-90.cgi).
2. Download the project zip file, and extract it.
3. In your IDE, import an existing Maven project, and select the project.
4. Right click the main project folder in your IDE, click ***Maven***, and then ***Update Project***.
5. Search through ***src*** folder, and find ***context.xml*** file within the **META-INF** folder.
6. Open the context.xml file with a text editor.
7. Change the ***url*** variables to match where you are storing the project. Ensure you use the following format:
```
jdbc:sqlite:<project-path>\CartCrafters\src\main\resources\<database-name>.db
```
9. Run the project.

# Requirements Document
Can be found [here](https://docs.google.com/document/d/1ZWlmvLkMUWDauz06uMcv-5dzCzevhOUv9Qs2rIAS454/edit?usp=sharing).

# Debugging
Should you run into any issues installing the project, do not hesistant to reach out to us. We'd be happy to help!

# Authors
- Martin Brejniak (martin.brejniak@bell.net)
- Abeed Arefin
- Anika Prova
- Madison Hartley
