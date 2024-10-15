FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

COPY . .

RUN chmod +x ./gradlew

RUN ./gradlew bootJar

COPY build/libs/issues-recovery-service-0.0.1-SNAPSHOT.jar /app/issues-recovery-service.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/issues-recovery-service.jar"]
