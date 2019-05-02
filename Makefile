package = 'micronaut-poc'

PUBLISHER := micronaut-publisher
RECEIVER := micronaut-receiver

default: test

clean:
	cd ${PUBLISHER} && gradle clean
	cd ${RECEIVER} && gradle clean

test:
	cd ${PUBLISHER} && gradle test
	cd ${RECEIVER} && gradle test

build:
	cd ${PUBLISHER} && gradle build
	cd ${RECEIVER} && gradle build

docker-build:
	cd ${PUBLISHER} && PORT=8080 docker build -t ${PUBLISHER} .
	cd ${RECEIVER} && PORT=8082 docker build -t ${RECEIVER} .
