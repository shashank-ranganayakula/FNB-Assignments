# 🎓 Student Management API

A clean, production-ready **Spring Boot REST API** for managing students with full CRUD and bonus search endpoints.

---

## 🚀 Quick Start

```bash
# 1. Clone / unzip the project
cd student-management-api

# 2. Build & run
mvn spring-boot:run

# 3. API is live at
http://localhost:8080/api/v1/students

# 4. H2 Database console (optional)
http://localhost:8080/h2-console
# JDBC URL: jdbc:h2:mem:studentdb  |  User: sa  |  Password: (blank)
```

> **Requirements:** Java 21, Maven 3.8+

---

## 🗂️ Project Structure

```
src/main/java/com/example/studentapi/
├── StudentManagementApiApplication.java   ← Entry point
├── controller/
│   └── StudentController.java             ← REST endpoints
├── service/
│   └── StudentService.java                ← Business logic
├── repository/
│   └── StudentRepository.java             ← JPA + custom queries
├── model/
│   └── Student.java                       ← JPA entity
├── dto/
│   └── StudentDTO.java                    ← Request / Response / ApiResponse
└── exception/
    ├── StudentNotFoundException.java
    ├── DuplicateEmailException.java
    └── GlobalExceptionHandler.java
```

---

## 📡 API Endpoints

Base URL: `http://localhost:8080/api/v1/students`

| Method   | Endpoint              | Description                     |
|----------|-----------------------|---------------------------------|
| `POST`   | `/`                   | Create a new student            |
| `GET`    | `/`                   | Get all students                |
| `GET`    | `/{id}`               | Get student by ID               |
| `PUT`    | `/{id}`               | Full update a student           |
| `PATCH`  | `/{id}`               | Partial update a student        |
| `DELETE` | `/{id}`               | Delete a student                |
| `GET`    | `/search?keyword=`    | Search by name or email         |
| `GET`    | `/status/{status}`    | Filter by enrollment status     |
| `GET`    | `/major/{major}`      | Filter by major                 |
| `GET`    | `/gpa?min=&max=`      | Filter by GPA range             |

---

## 📦 Request & Response Examples

### ✅ Create Student — `POST /api/v1/students`

**Request body:**
```json
{
  "firstName": "Jane",
  "lastName": "Doe",
  "email": "jane.doe@university.edu",
  "dateOfBirth": "2002-04-20",
  "major": "Computer Science",
  "gpa": 3.75,
  "status": "ACTIVE"
}
```

**Response `201 Created`:**
```json
{
  "success": true,
  "message": "Student created successfully",
  "data": {
    "id": 7,
    "firstName": "Jane",
    "lastName": "Doe",
    "fullName": "Jane Doe",
    "email": "jane.doe@university.edu",
    "dateOfBirth": "2002-04-20",
    "major": "Computer Science",
    "gpa": 3.75,
    "status": "ACTIVE",
    "createdAt": "2024-03-06T10:00:00",
    "updatedAt": "2024-03-06T10:00:00"
  }
}
```

---

### 📋 Get All Students — `GET /api/v1/students`

```json
{
  "success": true,
  "message": "Retrieved 6 students",
  "data": [ { ... }, { ... } ]
}
```

---

### 🔍 Search — `GET /api/v1/students/search?keyword=alice`

---

### 🏷️ Filter by Status — `GET /api/v1/students/status/ACTIVE`

Valid statuses: `ACTIVE`, `INACTIVE`, `GRADUATED`, `SUSPENDED`

---

### 📊 Filter by GPA — `GET /api/v1/students/gpa?min=3.0&max=4.0`

---

### ❌ Error Response (404)

```json
{
  "success": false,
  "message": "Student not found with ID: 99",
  "data": null
}
```

### ⚠️ Validation Error (400)

```json
{
  "success": false,
  "message": "Validation failed",
  "data": {
    "email": "Email must be valid",
    "gpa": "GPA must be at most 4.0"
  }
}
```

---

## 🛠️ Tech Stack

| Layer       | Technology                      |
|-------------|---------------------------------|
| Framework   | Spring Boot 3.2                 |
| Data        | Spring Data JPA + Hibernate     |
| Database    | H2 (in-memory, swap for MySQL)  |
| Validation  | Jakarta Bean Validation         |
| Boilerplate | Lombok                          |
| Java        | Java 21                         |

---

## 🔄 Swap to MySQL/PostgreSQL

Replace the H2 dependency and `application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/studentdb
spring.datasource.username=root
spring.datasource.password=yourpassword
spring.jpa.hibernate.ddl-auto=update
spring.jpa.database-platform=org.hibernate.dialect.MySQLDialect
```

Add to `pom.xml`:
```xml
<dependency>
    <groupId>com.mysql</groupId>
    <artifactId>mysql-connector-j</artifactId>
    <scope>runtime</scope>
</dependency>
```
