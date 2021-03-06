#!/bin/bash
cd .. && cd PetStore && mvn clean install && docker build -t petstore-monolith-java .

docker run --rm --add-host=host.docker.internal:host-gateway --name petstore-monolith-java -p 8080:8080 petstore-monolith-java &