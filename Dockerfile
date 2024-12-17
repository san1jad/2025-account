# base image containing java runtime
FROM openjdk:21-jdk-slim
# who maintains the image
LABEL "org.opencontainers.image.authors"="sbc.com"
# adds the applications jar to the image
COPY target/accounts-0.0.1-SNAPSHOT.jar accounts-0.0.1-SNAPSHOT.jar
# execute the application
ENTRYPOINT ["java", "-jar","accounts-0.0.1-SNAPSHOT.jar"]