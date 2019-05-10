FROM adoptopenjdk/openjdk11-openj9:jdk-11.0.1.13-alpine-slim

RUN apk add --no-cache bash

ARG APPNAME
ARG VERSION=0.1
ARG PORT=${PORT}
ARG KAFKA

RUN mkdir -p /app
WORKDIR /app

COPY build/libs/${APPNAME}-${VERSION}.jar ${APPNAME}.jar
COPY start-up.sh .

ENV APPNAME=$APPNAME
ENV BUILDVER=$BUILDVER
ENV KAFKA_HOST=$KAFKA

ENTRYPOINT ["/app/start-up.sh"]

EXPOSE ${PORT}
