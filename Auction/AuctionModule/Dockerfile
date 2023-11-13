FROM openjdk:19-jdk-alpine

WORKDIR /app

# Copy the Spring Boot project into the image
COPY target/CatalogueServer-0.0.1-SNAPSHOT.jar /app/CatalogueServer.jar

# Install SQLite
RUN apk add --no-cache sqlite

# Copy the database file into the image
COPY target/auction.db /app/auction.db

EXPOSE 1337

# Set the command to run the Spring Boot project
CMD ["java", "-jar", "/app/CatalogueServer.jar"]
