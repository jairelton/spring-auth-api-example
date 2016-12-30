#FROM openjdk:jre-alpine
FROM frolvlad/alpine-oraclejdk8:slim
WORKDIR /usr/src/app
ADD spring-auth-api-example-1.0.0.jar app.jar
ADD application.properties application.properties
EXPOSE 80
ENV JAVA_OPTS=""
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -jar app.jar" ]