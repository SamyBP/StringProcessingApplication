FROM jelastic/maven:3.9.5-openjdk-21

WORKDIR ArtefactBuilder

ADD src src
ADD pom.xml pom.xml
ADD build.sh /build.sh

RUN chmod +x /build.sh

CMD ["/build.sh"]