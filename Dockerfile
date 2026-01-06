# Etapa 1: Construcción
FROM maven:3.9.4-eclipse-temurin-17 AS build
COPY . .
RUN mvn clean package -DskipTests

# Etapa 2: Ejecución
FROM eclipse-temurin:17-jre
COPY --from=build /target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]