FROM openjdk:8
MAINTAINER uanid@outlook.com

WORKDIR /usr/local/notibot
COPY notibot.jar notibot.jar
COPY application.properties application.properties

ENTRYPOINT ["java -jar notibot.jar -Dfile.encoding=utf-8"]
