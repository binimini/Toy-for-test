FROM openjdk:11-jre-slim

ARG JAR_FILE=*.jar

EXPOSE 8080

COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java","-jar","/app.jar"]