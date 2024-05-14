# CQRS Example based on Microservices Architecture and Event Sourcing

## Overview

This project demonstrates a very basic bank ledger system using the CQRS (Command Query Responsibility Segregation) pattern, microservices architecture, and event sourcing. Instead of using a messaging system with a JMS provider, Spring Application Events are utilized, and persistence is managed in a HashMap.

The project is a work in progress and will continue to evolve over time. All changes and updates will be logged in the [ChangeLog.md](ChnageLog.md)
file.

### TODOs

- Implement logging using @slf4j for better monitoring and troubleshooting.
- Integrate Swagger for API documentation, or alternatively, use Spring Boot Actuator to expose endpoints.
- Implement projections on the read/view side to optimize query performance and support complex read operations.
- Resolve potential duplicate declarations in the POM(s).
- Explore containerization options once a messaging bus is introduced.

## How to Run

To run the project:

1. Checkout the repository.
2. Navigate to the `cqrs-account-service` module.
3. Run the `CqrsServiceApplication` from IntelliJ IDEA.

## Endpoints (Write Side / Command Pillar)

### Deposit

```bash
curl --location 'localhost:8080/transactions/deposit/971a09db-5d99-4c90-9064-23ff972efb3b' \
--header 'Content-Type: application/json' \
--data '{
"amount": "200.00"
}'
```
### Withdraw

```bash
curl --location 'localhost:8080/transactions/withdraw/971a09db-5d99-4c90-9064-23ff972efb3b' \
--header 'Content-Type: application/json' \
--data '{
"amount": "100.00"
}'
```
## Endpoints (Read Side / View Store)

### Balance

```bash
curl --location 'localhost:8080/accounts/balance/971a09db-5d99-4c90-9064-23ff972efb3b'
```
### Transactions

```bash
curl --location 'localhost:8080/accounts/transactions/971a09db-5d99-4c90-9064-23ff972efb3b?noOfTransactions=20'
```

Enjoy exploring the functionalities!
