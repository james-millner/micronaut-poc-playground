package = 'micronaut-poc'

PUBLISHER := micronaut-publisher
RECEIVER := micronaut-receiver
S_PUBLISHER := spring-publisher
S_RECEIVER := spring-receiver

default: clean

all: clean build docker-build

clean:
	cd ${PUBLISHER} && MICRONAUT_ENVIRONMENTS=default gradle clean
	cd ${RECEIVER} && MICRONAUT_ENVIRONMENTS=default gradle clean
	cd ${S_PUBLISHER} && MICRONAUT_ENVIRONMENTS=default gradle clean
	cd ${S_RECEIVER} && MICRONAUT_ENVIRONMENTS=default gradle clean

compile:
	cd ${PUBLISHER} && MICRONAUT_ENVIRONMENTS=default gradle compileKotlin
	cd ${RECEIVER} && MICRONAUT_ENVIRONMENTS=default gradle compileKotlin
	cd ${S_PUBLISHER} && MICRONAUT_ENVIRONMENTS=default gradle compileKotlin
	cd ${S_RECEIVER} && MICRONAUT_ENVIRONMENTS=default gradle compileKotlin

build:
	cd ${PUBLISHER} && MICRONAUT_ENVIRONMENTS=default gradle build
	cd ${RECEIVER} && MICRONAUT_ENVIRONMENTS=default gradle build
	cd ${S_PUBLISHER} && KAFKA_HOST=localhost gradle build
	cd ${S_RECEIVER} && KAFKA_HOST=localhost gradle build

docker-build:
	cd ${PUBLISHER} && docker build -t ${PUBLISHER} --build-arg APPNAME=${PUBLISHER} .
	cd ${RECEIVER} && docker build -t ${RECEIVER} --build-arg APPNAME=${RECEIVER} .
	cd ${S_PUBLISHER} && docker build -t ${S_PUBLISHER} --build-arg APPNAME=${S_PUBLISHER} .
	cd ${S_RECEIVER} && docker build -t ${S_RECEIVER} --build-arg APPNAME=${S_RECEIVER} .

demo:
	KAFKA_HOST=kafka docker-compose up