FROM openjdk:8-jdk-alpine
MAINTAINER Sujeet

COPY target/ner-application-0.0.1-SNAPSHOT.jar chatbot.jar

ENTRYPOINT ["java","-jar","chatbot.jar"]