# ----------- Stage 1: Build the app ----------- #
FROM gradle:8.4-jdk17 AS builder

WORKDIR /app

COPY --chown=gradle:gradle . .

RUN gradle bootJar --no-daemon

# ----------- Stage 2: Run the app ----------- #
FROM eclipse-temurin:17-jre-jammy

WORKDIR /app

COPY --from=builder /app/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
