package = 'micronaut-poc'

default: build

build:
	cd micronaut-publisher && gradle clean test build
	cd micronaut-receiver && gradle clean test build
