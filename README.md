# EduFlow Academy

A Learning Management System built with Spring Boot for course management, student enrollment, and role-based authentication.

## ✨ Features

- User authentication with role-based access (Student, Instructor, Admin)
- Course management (CRUD operations)
- Student enrollment system
- User management and admin tools
- Modern animated UI with dark theme

## 🛠 Tech Stack

- **Backend:** Spring Boot, Spring Security, JPA/Hibernate
- **Database:** MySQL 8.0
- **Frontend:** Thymeleaf, CSS, GSAP animations
- **Build:** Maven

## 🚀 Quick Start

1. **Prerequisites:** Java 17+, MySQL 8.0+, Maven

2. **Setup Database**
   ```sql
   CREATE DATABASE eduflow;
   INSERT INTO roles (name) VALUES ('ROLE_STUDENT'), ('ROLE_INSTRUCTOR'), ('ROLE_ADMIN');
   ```

3. **Configure** `application.properties`
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/eduflow
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   ```

4. **Run**
   ```bash
   mvn spring-boot:run
   ```

5. **Access:** http://localhost:8080

## 🔌 Key API Endpoints

**Authentication**
- `POST /register` - Register user
- `POST /api/auth/new-login` - Login

**Users**
- `GET /users` - Get all users
- `GET /users/{role}` - Get users by role
- `PUT /users/admin/make-user-admin/{id}` - Promote to admin

**Courses**
- `POST /courses` - Create course
- `GET /courses` - List all courses
- `GET /courses/{id}` - Get course details
- `PUT /courses` - Update course
- `DELETE /courses/{id}` - Delete course

**Enrollment**
- `POST /enroll` - Enroll student

## 👥 User Roles

- **STUDENT** - Enroll in courses, view content
- **INSTRUCTOR** - Create and manage courses
- **ADMIN** - Full system access, user management

## 🔒 Security

- BCrypt password encryption
- Spring Security with role-based access
- ⚠️ **Note:** Security is currently open for development. Enable role restrictions in `SecurityConfig.java` before production.

## 📝 Notes

- Uses UUID for user IDs
- All endpoints temporarily set to `.permitAll()` for testing
- Database schema auto-created via `spring.jpa.hibernate.ddl-auto=update`

---

**Built with Spring Boot | MIT License**
