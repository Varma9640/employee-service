# Employee Service

Employee Management Microservice built using Spring Boot.

## Technologies Used

* Java 17
* Spring Boot 3
* Spring Data JPA
* H2 Database
* OpenFeign
* Apache Kafka
* Docker
* JUnit 5
* Mockito

## Features

* Create Employee
* Validate Employee Data
* Call Location Service using OpenFeign Client
* Publish Employee Events to Kafka
* Consume Employee Events using Kafka Consumer
* Save Employee Audit Records
* Dockerized Kafka Setup
* REST APIs
* Unit Testing

## Architecture

Employee Service

↓

OpenFeign Client

↓

Location Service

↓

Kafka Producer

↓

employee-created-topic

↓

Kafka Consumer

↓

Audit Service

↓

EMPLOYEE_AUDIT

## Running the Project

### Start Kafka

docker run -d --name kafka -p 9092:9092 apache/kafka:latest

### Run Application

mvn spring-boot:run

## Swagger URL

http://localhost:8080/swagger-ui.html

## Sample API

POST /employees

Request:

{
"employeeName": "Varma",
"employeeEmail": "[varma@test.com](mailto:varma@test.com)",
"locationName": "Bangalore",
"city": "Bangalore",
"state": "Karnataka",
"country": "India"
}
