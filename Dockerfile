FROM openjdk:8
MAINTAINER uanid@outlook.com

WORKDIR /usr/local/notibot
COPY *.jar notibot.jar
COPY src/main/resources/application.properties application.properties
RUN chmod 777 application.properties

EXPOSE 8080

ENTRYPOINT ["java -Dfile.encoding=utf-8 -jar notibot.jar"]
