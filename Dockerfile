# Сборка JAR через образ с предустановленным Gradle (без зависимости от gradlew)
FROM gradle:8.5-jdk17-alpine AS builder
WORKDIR /app

COPY build.gradle settings.gradle ./
COPY src src

RUN gradle bootJar --no-daemon

# Финальный образ
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
EXPOSE 8080
COPY --from=builder /app/build/libs/*.jar /app/service.jar
CMD ["java", "-jar", "/app/service.jar"]
