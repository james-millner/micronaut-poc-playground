package = 'micronaut-poc'

PUBLISHER := micronaut-publisher
RECEIVER := micronaut-receiver
S_PUBLISHER := spring-publisher
S_RECEIVER := spring-receiver

default: test

all: test clean build docker-build

clean:
	cd ${PUBLISHER} && gradle clean
	cd ${RECEIVER} && gradle clean
	cd ${S_PUBLISHER} && gradle clean
	cd ${S_RECEIVER} && gradle clean

test:
	cd ${PUBLISHER} && gradle test
	cd ${RECEIVER} && gradle test
	cd ${S_PUBLISHER} && gradle test
	cd ${S_RECEIVER} && gradle test

build:
	cd ${PUBLISHER} && gradle build
	cd ${RECEIVER} && gradle build
	cd ${S_PUBLISHER} && gradle build
	cd ${S_RECEIVER} && gradle build

docker-build:
	cd ${PUBLISHER} && PORT=8080 docker build -t ${PUBLISHER} --build-arg APPNAME=${PUBLISHER} .
	cd ${RECEIVER} && PORT=8082 docker build -t ${RECEIVER} --build-arg APPNAME=${RECEIVER} .
	cd ${S_PUBLISHER} && PORT=8084 docker build -t ${S_PUBLISHER} --build-arg APPNAME=${S_PUBLISHER} .
	cd ${S_RECEIVER} && PORT=8085 docker build -t ${S_RECEIVER} --build-arg APPNAME=${S_RECEIVER} .
