FROM openjdk:21-jdk

ENV SPRING_PROFILES_ACTIVE=production

ADD target/RestServer-0.0.1-SNAPSHOT.jar server.jar


ENTRYPOINT ["java", "-jar", "server.jar"]