FROM maven:3.8.5-openjdk-17 AS build 
WORKDIR /usr/src/app
COPY /src /home/app/src 
COPY //pom.xml /home/app 
COPY items.db .
RUN mvn -f /home/app/pom.xml clean package 

FROM openjdk:17 
COPY --from=build /home/app/target/ItemServer-0.0.1-SNAPSHOT.war /usr/src/myapp/ItemServer.war
COPY items.db .
EXPOSE 9090 
ENTRYPOINT ["java","-jar","/usr/src/myapp/ItemServer.war"]