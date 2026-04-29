# --- ETAPA 1: Construcción ---
# Usamos una imagen de Maven que tenga Java 25. 
# Si esta falla, prueba con 'maven:3.9-eclipse-temurin-25'
FROM maven:3.9-eclipse-temurin-25 AS build
WORKDIR /app

# Copiamos dependencias
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copiamos el código fuente
COPY src ./src

# Compilamos (saltando tests para ir más rápido y evitar errores de entorno)
RUN mvn clean package -DskipTests

# --- ETAPA 2: Ejecución ---
# Usamos la imagen oficial de Java 25 para correr la app
FROM eclipse-temurin:25-jdk
WORKDIR /app

# Copiamos el .jar generado en la etapa anterior
# El asterisco *.jar busca cualquier nombre, luego lo renombramos a app.jar
COPY --from=build /app/target/*.jar app.jar

# Exponemos el puerto estándar
EXPOSE 8080

# Arrancamos
ENTRYPOINT ["java", "-jar", "app.jar"]