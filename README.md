![CartCrafters Logo](https://github.com/Martin-Brejniak/CartCrafters/assets/77299294/2a516df4-c0f5-4fc3-8743-a26c7f5396c2)
CartCrafters is a RESTFUL auction eCommerce site, akin to auctioning websites like eBay. An account is required to access the service, and items can be put up for sale under a Foward or Dutch auction. Developed in Java, and built on Apache Tomcat. 

# Installation
1. Ensure Docker Desktop is installed on your computer. If you don't have it, you can get it [here](https://www.docker.com/products/docker-desktop/).
2. Within a Command Prompt window, type in the following commands:
```
docker login
docker pull martanisiv/user-module
docker pull martanisiv/item-module
docker pull martanisiv/auction-module 
docker pull martanisiv/cartcrafter-gateway
docker pull martanisiv/cartcrafter-frontend
```
3. Download the ***docker-compose.yml*** file from the CartCrafters-Compose folder.
4. Within another Command Prompt window, navigate to the folder the ***docker-compose.yml*** is in, and run the following command:
```
docker compose up -d
```
5. Open a web browser, and input the following URL:
```
https://localhost:3000
```

# Requirements Document
Can be found [here](https://docs.google.com/document/d/1ZWlmvLkMUWDauz06uMcv-5dzCzevhOUv9Qs2rIAS454/edit?usp=sharing).

# Authors
- Martin Brejniak (martin.brejniak@bell.net)
- Abeed Arefin (abeed09@my.yorku.ca)
- Anika Prova (anika98@my.yorku.ca)
- Madison Hartley (maddi15@my.yorku.ca)
