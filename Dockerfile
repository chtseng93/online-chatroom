# Stage 1: Build
FROM maven:3.8.6-openjdk-8-slim AS builder
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline -B
COPY src ./src
RUN mvn package -DskipTests -B

# Stage 2: Run
FROM eclipse-temurin:8-jdk-alpine
COPY --from=builder /app/target/demo-0.0.1-SNAPSHOT.jar chatapp.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/chatapp.jar"]
