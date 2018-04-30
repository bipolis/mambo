FROM openjdk:9.0.1-11-jre-slim

LABEL maintainer="bipolis.org"

ADD target/application.jar /usr/share/server/application.jar
ADD load /usr/share/server/load/

ENTRYPOINT [ "/usr/bin/java", "--add-opens=java.base/java.net=ALL-UNNAMED", "--add-opens=java.base/java.lang=ALL-UNNAMED", "--add-opens=java.base/jdk.internal.loader=ALL-UNNAMED", "-jar", "/usr/share/server/application.jar" ]