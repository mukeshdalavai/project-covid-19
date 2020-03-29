FROM openjdk:11

ADD ./target/data-service-0.0.1-RELEASE.jar /usr/src/data-service-0.0.1-RELEASE.jar

WORKDIR usr/src

ENTRYPOINT ["java","-jar", "data-service-0.0.1-RELEASE.jar"]

