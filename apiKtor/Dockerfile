FROM gradle:8.11-jdk21 AS build
WORKDIR /app
COPY . .
RUN gradle buildFatJar --no-daemon --no-configuration-cache \
    -Dorg.gradle.jvmargs="-Xmx1g -Dfile.encoding=UTF-8"

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /app/build/libs/api-all.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-Xmx220m", "-jar", "app.jar"]
