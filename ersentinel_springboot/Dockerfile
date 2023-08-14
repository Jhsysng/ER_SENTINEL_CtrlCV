FROM openjdk:17-alpine

COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .
COPY src src
RUN chmod +X ./gradlew
RUN ./gradlew bootJar

COPY --from=builder build/libs/*.jar a.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=dev", "/a.jar"]