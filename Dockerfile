# Etapa 1: Build
FROM maven:3.9.6-eclipse-temurin-21-alpine AS build
COPY . .

RUN mvn clean package -DskipTests

# Etapa 2: Run
FROM openjdk:25-slim-bullseye
COPY --from=build /target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]