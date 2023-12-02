FROM maven:3.8.5-openjdk-17 AS build 
WORKDIR /usr/src/app
COPY /src /home/app/src 
COPY //pom.xml /home/app 
COPY users.db .
RUN mvn -f /home/app/pom.xml clean package 

FROM openjdk:17 
COPY --from=build /home/app/target/UserServer-0.0.1-SNAPSHOT.jar /usr/src/myapp/UserServer.jar 
COPY users.db .
EXPOSE 8080 
ENTRYPOINT ["java","-jar","/usr/src/myapp/UserServer.jar"]