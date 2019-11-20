FROM openjdk:8
MAINTAINER uanid@outlook.com

WORKDIR /usr/local/notibot
COPY *.jar notibot.jar
COPY src/main/resources/application.properties application.properties
RUN chmod 777 application.properties

COPY entrypoint.sh entrypoint.sh
RUN chmod 777 entrypoint.sh

EXPOSE 8080

ENTRYPOINT ["entrypoint.sh"]
