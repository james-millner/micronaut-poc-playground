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
