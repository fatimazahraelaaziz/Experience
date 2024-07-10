# Microservice Consumer Repository

## Overview

This repository contains a set of microservices designed for different scenarios, including a single consumer microservice and a DAG of multiple consumer microservices. The project utilizes Kafka for communication between the producer and consumer microservices.

## Repository Structure

### Single Microservice Consumer

1. **Integration Controller**
   - **Path**: `integrationcontroller`
   - **Description**: This repository contains the controller responsible for implementing the bin packing algorithm.

2. **Variable Consumer**
   - **Path**: `variableconsumer`
   - **Description**: This repository contains the consumer microservice implemented using Kafka.

3. **Producer**
   - **Path**: `produceri3s`
   - **Description**: This repository models the producer microservice using Apache Kafka.

### DAG of Multiple Microservice Consumers

1. **Graph Controller**
   - **Path**: `graphcontroller2`
   - **Description**: This repository contains the controller responsible for modeling the graph for the scenario involving multiple consumer microservices.

2. **Multiple Consumers**
   - **Path**: `multipleconsumers`
   - **Description**: This repository contains the implementation of the consumer microservices for the DAG scenario.

3. **Producer**
   - **Path**: `produceri3s`
   - **Description**: The producer implementation remains unchanged and continues to use Apache Kafka.

## Getting Started

### Prerequisites

- [Java JDK](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html) (version 11 or later)
- [Kafka](https://kafka.apache.org/)
- [Maven](https://maven.apache.org/) (for building the project)

### Installation

1. Clone the repository:
    ```sh
    git clone https://github.com/USERNAME/REPO](https://github.com/fatimazahraelaaziz/Experience.git
    cd Experience
    ```

2. Build each microservice using Maven:
    ```sh
    cd integrationcontroller
    mvn clean install

    cd ../variableconsumer
    mvn clean install

    cd ../produceri3s
    mvn clean install

    cd ../graphcontroller2
    mvn clean install

    cd ../multipleconsumers
    mvn clean install
    ```


## Usage

1. **Single Consumer Scenario**:
    - The producer sends messages to the Kafka topic.
    - The variable consumer listens to the Kafka topic and processes the messages.
    - The integration controller handles the bin packing algorithm logic.

2. **DAG of Multiple Consumers Scenario**:
    - The producer sends messages to the Kafka topic.
    - The graph controller models the graph of multiple consumer microservices.
    - The multiple consumers listen to the Kafka topic and process the messages according to the graph model.

## Contributing

Feel free to submit issues and enhancement requests.

