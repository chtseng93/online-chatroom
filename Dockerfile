FROM openjdk:8-jdk-alpine
COPY target/demo-0.0.1-SNAPSHOT.jar chatapp.jar
ENTRYPOINT ["java","-jar","/chatapp.jar"]