FROM maven:3.8.5-openjdk-17 AS build 
WORKDIR /usr/src/app
COPY /src /home/app/src 
COPY //pom.xml /home/app 
COPY auction.db .
RUN mvn -f /home/app/pom.xml clean package 

FROM tomcat:9.0-jdk17-openjdk-slim
COPY --from=build /home/app/target/AuctionServer-0.0.1-SNAPSHOT.jar /usr/src/myapp/AuctionServer.jar 
COPY auction.db .
EXPOSE 7070 
ENTRYPOINT ["java","-jar","/usr/src/myapp/AuctionServer.jar"]