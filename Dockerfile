FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
EXPOSE 8080
COPY /build/libs/*.jar /app/service.jar
CMD ["java", "-jar", "/app/service.jar"]