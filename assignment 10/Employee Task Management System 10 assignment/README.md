# Employee Task Management System

A full-stack web application built with Spring Boot for managing employee tasks.

## Features

- **Admin Features:**
  - Add, update, delete employees
  - Assign tasks to employees
  - View all tasks

- **Employee Features:**
  - View assigned tasks
  - Update task status (Pending, In Progress, Completed)

## Tech Stack

- **Backend:** Spring Boot 3.2.0
- **Database:** MySQL
- **ORM:** Spring Data JPA (Hibernate)
- **Frontend:** Thymeleaf (HTML, CSS)
- **Build Tool:** Maven
- **Language:** Java 17

## Prerequisites

- Java 17 or higher
- MySQL Server
- Maven 3.6+

## Setup Instructions

1. **Clone or download the project**

2. **Database Setup:**
   - Install MySQL Server
   - Create a database named `employee_task_db` (or update `application.properties` with your database name)
   - Update the database credentials in `src/main/resources/application.properties`:
     ```
     spring.datasource.username=your_username
     spring.datasource.password=your_password
     ```

3. **Build the Project:**
   ```bash
   mvn clean install
   ```

4. **Run the Application:**
   ```bash
   mvn spring-boot:run
   ```

5. **Access the Application:**
   - Open your browser and go to `http://localhost:8080`

## Project Structure

```
src/
├── main/
│   ├── java/
│   │   └── com/example/employeetaskmanagement/
│   │       ├── controller/          # Spring MVC Controllers
│   │       ├── model/               # JPA Entities
│   │       ├── repository/          # JPA Repositories
│   │       ├── service/             # Business Logic Services
│   │       └── EmployeeTaskManagementApplication.java
│   └── resources/
│       ├── static/                  # Static resources (CSS, JS)
│       ├── templates/               # Thymeleaf templates
│       └── application.properties   # Configuration
└── test/                            # Unit tests
```

## API Endpoints

- `GET /` - Home page
- `GET /employees` - List all employees
- `GET /employees/add` - Add employee form
- `POST /employees/save` - Save employee
- `GET /employees/edit/{id}` - Edit employee form
- `GET /employees/delete/{id}` - Delete employee
- `GET /tasks` - List all tasks
- `GET /tasks/add` - Add task form
- `POST /tasks/save` - Save task
- `GET /tasks/edit/{id}` - Edit task form
- `GET /tasks/delete/{id}` - Delete task
- `GET /tasks/my-tasks/{employeeId}` - View employee's tasks
- `POST /tasks/update-status/{id}` - Update task status

## Database Schema

### Employee Table
- `id` (Primary Key)
- `name`
- `email` (Unique)
- `department`

### Task Table
- `id` (Primary Key)
- `title`
- `description`
- `deadline` (Date)
- `status` (String: Pending, In Progress, Completed)
- `employee_id` (Foreign Key to Employee)

## Testing

To test the application:

1. Start the application
2. Go to `http://localhost:8080`
3. Add some employees via "Add Employee"
4. Add tasks and assign them to employees
5. Employees can view their tasks by navigating to `/tasks/my-tasks/{employeeId}` (replace {employeeId} with actual ID)
6. Update task statuses as needed

## Notes

- The application uses `spring.jpa.hibernate.ddl-auto=update` to automatically create/update database schema
- SQL queries are logged to the console for debugging
- Basic form validation is implemented using Bean Validation
- Error handling is basic; enhance as needed for production use