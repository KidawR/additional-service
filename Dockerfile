FROM openjdk:17-jdk-slim
COPY build/libs/additional-service.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
