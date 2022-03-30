#!/bin/bash
cd PetStore && mvn clean install && docker build -t petstore-monolith-java .

docker run --rm --net=host -p 8080:8080 petstore-monolith-java &
wait
