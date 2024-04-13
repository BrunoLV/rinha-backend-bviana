FROM gradle:8.7.0-jdk17 AS builder
WORKDIR /home/gradle/rinha
COPY build.gradle.kts .
COPY settings.gradle.kts .
COPY gradlew .
COPY src src
RUN gradle build --no-daemon

FROM ghcr.io/graalvm/jdk-community:17
WORKDIR /app
COPY --from=builder /home/gradle/rinha/build/libs/rinha-backend-bviana-1.0-SNAPSHOT-all.jar app.jar
CMD ["java", "-jar", "app.jar"]
