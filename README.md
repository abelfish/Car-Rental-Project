# Car-Rental Project

This project is a car rental system that allows users to rent cars and admin to manage the system. The system is built using Java, Springboot, MongoDB, HSQLDB, ActiveMQ. The system has also been configured to be monitored using Prometheus and Grafana.

## Getting Started

### Prerequisites

- Java 17
- Maven
- Docker
- Springboot
- MongoDB
- HSQLDB
- ActiveMQ
- Prometheus
- Grafana

## Functional requirements:

### Car functionality:

We can add, remove, update and find cars in the car rental application. You can search cars on car type, brand, price. We can have multiple cars of the same brand and type. For example we have 15 Ford F-150 Pickup trucks. Every individual car has a unique license plate. The system keeps track of how many cars of a certain brand and type are available in the car fleet.

### Customer functionality:

We can add, remove, update and find customers in the car rental application. You can search
customers on customernumber, name and email address. Every customers has an unique
customernumber.

### Reservation functionality:

A customer can reserve a certain car type for a certain period.

### Borrowing functionality.

A customer can pickup the car at the car renting location. A customer can return the car to the car renting location. The customer pays for the car when the car is returned. Rentals can only be paid by creditcard.

### Admin functionality:

We can retrieve the following information:

- Get all data from a customer(including reserved cars, rental history, payments made)
- Get all data from a particular car including the rental history.
