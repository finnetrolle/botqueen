FROM java:8-jre
MAINTAINER Max Syachin <maxsyachin@gmail.com>

ADD ./target/botqueen.jar /app/

CMD ["java", "-jar", "/app/botqueen.jar"]

EXPOSE 8080