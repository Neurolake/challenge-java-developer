FROM maven:3.8.4-jdk-11 AS builder

WORKDIR /challeng-java-developer

COPY pom.xml .
COPY src ./src

RUN mvn -B -DskipTests clean package

FROM alpine/java:21-jdk

ARG JAR_FILE=target/challenge-java-developer-0.0.1-SNAPSHOT.jar

ADD ${JAR_FILE} /app.jar

EXPOSE 5000

ENTRYPOINT ["java", "-jar", "/app.jar"]