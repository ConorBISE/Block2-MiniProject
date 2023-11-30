# Build stage
FROM maven:3.9.5-eclipse-temurin-21 AS build
COPY . /app/
RUN mvn -f /app/pom.xml clean package

# Run stage
FROM openjdk:19-jdk-alpine
COPY --from=build /app/server/target/server*.jar /app/server.jar
ENTRYPOINT ["java", "-jar","/app/server.jar"]