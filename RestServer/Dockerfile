FROM jelastic/maven:3.9.5-openjdk-21 as artifact

WORKDIR ArtifactBuilder

ADD src src
ADD pom.xml pom.xml

RUN mvn clean
RUN mvn package

FROM openjdk:21-jdk as server

ENV SPRING_PROFILES_ACTIVE=production

ENTRYPOINT ["java", "-jar", "target/RestServer-0.0.1-SNAPSHOT.jar"]