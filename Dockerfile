FROM openjdk:11-jre-slim

COPY build/libs/app-0.0.1.jar /app.jar

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/app.jar"]