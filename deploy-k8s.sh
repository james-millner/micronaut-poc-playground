#!/usr/bin/env bash

kubernetes=k8s

kubectl apply -f $kubernetes/kafka-claim0-persistentvolumeclaim.yaml
kubectl apply -f $kubernetes/kafka-deployment.yaml
kubectl apply -f $kubernetes/kafka-service.yaml

kubectl apply -f $kubernetes/micronaut-publisher-deployment.yaml
kubectl apply -f $kubernetes/micronaut-publisher-service.yaml
kubectl apply -f $kubernetes/micronaut-receiver-deployment.yaml
kubectl apply -f $kubernetes/micronaut-receiver-service.yaml

kubectl apply -f $kubernetes/zookeeper-deployment.yaml
kubectl apply -f $kubernetes/zookeeper-service.yaml
