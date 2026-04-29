# Etapa 1: Construcción (usando Maven y Java 25)
FROM maven:3.9.6-eclipse-temurin-25-alpine AS build
COPY . .
RUN mvn clean package -DskipTests

# Etapa 2: Ejecución (solo el JRE de Java 25 para que pese menos)
FROM eclipse-temurin:25-jre-alpine
COPY --from=build /target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]