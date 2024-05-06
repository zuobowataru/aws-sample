FROM maven:3.8.6-jdk-17 AS builder
WORKDIR /tmp
COPY ./src ./src
COPY ./pom.xml .
RUN mvn package
FROM adoptopenjdk/openjdk17:debianslim-jre
COPY --from=builder /tmp/target/aws-sample.jar /app/aws-sample.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]