
#INSTALLATION OF THE OPERATING SYSTEM
# FROM openjdk:17-jdk
FROM ubuntu:24.04 AS base

# Install OpenJDK 17
RUN apt-get update && apt-get install -y openjdk-17-jdk \
    && rm -rf /var/lib/apt/lists/*

LABEL authors="mkb"

#PLACEMENT OF THE EXECUTABLE [MICROSERVICE] ON THE IMAGE
COPY target/notification-service-dev.jar notification-service.jar

#EXPOSE PORTS FOR INCOMING TRAFFIC - HOST_PORT:CONTAINER_PORT
EXPOSE 8086:8086

#INSTALLING UTILITIES
# RUN apt-get update
# RUN apt-get install -y gcc
# RUN apt-get install -y curl

#ENTRYPOINT OF THE CONTAINER THROUGH THE MICROSERVICE
ENTRYPOINT ["java","-jar","notification-service.jar"]