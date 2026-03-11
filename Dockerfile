# ================== BUILD STAGE ==================
FROM maven:3.9.9-amazoncorretto-21 AS build
WORKDIR /app

# Copia apenas o pom.xml primeiro (cache de dependências)
COPY backend/pom.xml .
RUN mvn dependency:go-offline -B

# Copia o resto do código e faz o build
COPY backend/. .
RUN mvn clean package -DskipTests

# ================== RUNTIME STAGE ==================
FROM amazoncorretto:21
WORKDIR /app

# Copia o JAR gerado
COPY --from=build /app/target/*.jar app.jar

# Porta do Spring Boot
EXPOSE 8080

# Variáveis de ambiente (pode sobrescrever no docker-compose)
ENV SPRING_PROFILES_ACTIVE=prod

ENTRYPOINT ["java", "-jar", "app.jar"]