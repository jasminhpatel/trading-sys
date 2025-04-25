# Trading System

## Overview

This project is a Spring Boot application built with **Gradle** and **JDK 21**. It uses **PostgreSQL** as the database
and **RabbitMQ** as the messaging broker. The application consumes order messages from RabbitMQ and stores them in the
PostgreSQL database.

## Prerequisites

Before running the application, ensure the following services are running locally using Docker Compose.

### Docker Compose Services

```yaml
version: '3.9'

services:
  db:
    image: postgres:latest
    container_name: postgres
    restart: always
    environment:
      POSTGRES_USER: myuser
      POSTGRES_PASSWORD: mypassword
      POSTGRES_DB: mydatabase
    ports:
      - "5432:5432"
    volumes:
      - postgres_data:/var/lib/postgresql/data

  rabbitmq:
    image: rabbitmq:3-management
    container_name: rabbitmq
    restart: always
    environment:
      RABBITMQ_DEFAULT_USER: myuser
      RABBITMQ_DEFAULT_PASS: mypassword
    ports:
      - "5672:5672"    # AMQP protocol
      - "15672:15672"  # Management UI
    volumes:
      - rabbitmq_data:/var/lib/rabbitmq

volumes:
  postgres_data:
  rabbitmq_data:
```

Start these services from the root directory with:

```bash
docker-compose up -d
```

## Build and Run

- The project uses **Gradle** as the build tool.
- Requires **JDK 21**.
- Built with **Spring Boot** framework.

To build and run the application:

```bash
./gradlew bootRun
```

## Database Migrations

- **Flyway** is used for database migrations.
- Migration scripts are located in `src/main/resources/db/migration`.
- Seed scripts are included to populate the database with dummy data.

## Application Behavior

- Once the application starts, it periodically publishes dummy order messages to RabbitMQ.
- The `OrderPublisher` component sends a dummy `FixOrderPayload` every 5 seconds to the configured RabbitMQ exchange.
- The consumer listens to the RabbitMQ queue, consumes the messages, and stores the order information in the PostgreSQL
  database.

## Additional Information

- **RabbitMQ Management UI** is accessible at [http://localhost:15672](http://localhost:15672) with the credentials
  specified in the Docker Compose file.
- **PostgreSQL** is accessible at `localhost:5432` with the credentials specified in the Docker Compose file.

