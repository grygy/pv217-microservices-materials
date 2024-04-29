# Exercise materials for PV217 Service Oriented Architecture

These exercises are part of the thesis designing microservices exercises for the PV217 Service Oriented Architecture course at the Faculty of Informatics, Masaryk University.

## Airport management system description

The goal of this project is to create a simple airport management system. The system will be used by airport staff to manage flights, passengers, and their luggage.

## Exercise design

Each exercise will be iteration over previous ones. The main goal is to incrementally create the whole application over the semester.

## Technologies

- Java
- Quarkus framework
- RestEasy Reactive
- REST Client Reactive
- OpenAPI (Swagger UI)
- Quarkus gRPC
- Panache
- PostgreSQL
- JUnit 5
- RestAssured
- Mockito
- Quarkus Vert.x and Mutiny
- Docker
- Apache Kafka
- SmallRye Health
- Micrometer Metrics
- Prometheus
- Grafana
- SmallRye Fault Tolerance
- OpenTelemetry
- Jaeger


## Exercises

1.  Introduction to the Quarkus -- Introduction to the Quarkus environment and Quarkus features.
2.  REST API -- Explanation and implementation of REST API communication.
3.  OpenAPI and gRPC -- Description and implementation of OpenAPI specification and gRPC communication between services.
4.  Panache persistence -- Introduction and implementation of persistence using ORM Panache and description of reactive programming.
5.  Testing -- Writing unit and integration tests.
6.  RestClient, Containerization -- Use REST API communication between services and packaging services into containers.
7.  Messaging using Apache Kafka -- Implement messaging using Apache Kafka between services.
8.  Health checks, metrics, and monitoring -- Add health checks, metrics, and monitoring tools to services and connect them to Prometheus and Grafana.
9.  Fault tolerance, tracing -- Adding fault tolerance techniques and tracing to services.
10. Authentication between services -- Explanation of different authentication techniques, and implementing basic authentication between services.

## Structure of the repository

The repository contains assignments and solutions. Assignments have `-assignment` suffix, and solutions do not have any suffix.

In each assignment, you will find a README.md file with the explanation of the topic and tasks. The solution contains example implementation of the tasks.