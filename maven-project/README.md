# Employee Task Management System - Maven Version

This folder is the `Assignment_10` Employee Task Management System restructured for Maven build automation.

## Build and Run

```bash
mvn clean install
mvn spring-boot:run
```

## Tech Stack

- Java 17
- Spring Boot 3.3.5
- Spring Data JPA / Hibernate
- MySQL
- Maven
- Lombok
- Swagger OpenAPI

## Preserved Functionality

- Employee CRUD
- Task CRUD
- Pagination and sorting
- Validation and exception handling
- Swagger UI at `http://localhost:8080/swagger-ui.html`
- Browser UI at `http://localhost:8080/`

## API Base Paths

- `/api/employees`
- `/api/tasks`

## Database Configuration

Main runtime properties remain unchanged in `src/main/resources/application.properties` and use MySQL on port `3307`.
