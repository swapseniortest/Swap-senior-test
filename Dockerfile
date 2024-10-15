# Estágio de build
FROM ubuntu:latest AS build
RUN apt-get update
RUN apt-get install openjdk-17-jdk -y
COPY . .
RUN chmod +x ./gradlew
RUN ./gradlew bootJar --no-daemon

# Estágio final (execução)
FROM openjdk:17-jdk-slim
EXPOSE 8080

# Copia o JAR do estágio de build
COPY --from=build /build/libs/issues-recovery-service-1.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
