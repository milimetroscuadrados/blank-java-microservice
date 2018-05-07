FROM java:8-jre-alpine

RUN apk add --update curl && rm -rf /var/cache/apk/*

ARG JAVA_OPTS

ENV JAVA_OPTS ${JAVA_OPTS}

ADD ./build/libs/ms-0.0.1-SNAPSHOT.jar /app/ms.jar

ENTRYPOINT java $JAVA_OPTS -Xmx200m -Dspring.cloud.consul.host=$(curl 169.254.169.254/latest/meta-data/local-ipv4 2> /dev/null) -jar /app/ms.jar

EXPOSE 9999

