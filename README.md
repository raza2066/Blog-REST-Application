# REST-API-Blog-App

This project is a **Blog Application RESTful API** designed using Java and Spring Boot. It offers a complete backend solution for managing a blogging platform with support for authentication, user roles, categories, blog posts, and comments.

The application is secured using **JWT (JSON Web Token)** for authentication and authorization. It supports **role-based access control** with `ADMIN` and `USER` roles. The API design follows RESTful principles, making it easy to integrate with frontend applications or API clients.

---


## 🔧 Features

- 🛡️ JWT Authentication & Spring Security Integration
- 🧑‍💼 Role-based Authorization (`USER` and `ADMIN`)
- 🗂️ Category Management
- 📝 Post Creation and Search
- 💬 Commenting System
- 📃 OpenAPI Documentation via Swagger (`/v3/api-docs`)
- 🔄 Full CRUD Support for Posts, Comments, and Categories
- ⚠️ Centralized error handling using global exception handler, along with custom exceptions for more specific error messages and HTTP status codes.

---

## 🔐 Authentication APIs

Endpoints for user registration and login:

| Method | Endpoint              | Description          |
|--------|-----------------------|----------------------|
| POST   | `/api/auth/signup`    | Register a new user  |
| POST   | `/api/auth/signin`    | Authenticate a user  |
| POST   | `/api/auth/register`  | (Alias for signup)   |
| POST   | `/api/auth/login`     | (Alias for signin)   |

📌 **Response:** On successful login, a JWT token is returned.

---

## 📂 Category APIs

Endpoints to manage blog categories.

| Method | Endpoint                   | Description               |
|--------|----------------------------|---------------------------|
| POST   | `/api/categories`          | Create a category         |
| GET    | `/api/categories`          | Get all categories        |
| GET    | `/api/categories/{id}`     | Get a specific category   |
| PUT    | `/api/categories/{id}`     | Update a category         |
| DELETE | `/api/categories/{id}`     | Delete a category         |

---

## 📝 Post APIs

Endpoints to manage blog posts.

| Method | Endpoint                      | Description                      |
|--------|-------------------------------|----------------------------------|
| POST   | `/api/posts`                  | Create a new post                |
| GET    | `/api/posts`                  | Get all posts (paginated)        |
| GET    | `/api/posts/{id}`             | Get a post by ID                 |
| PUT    | `/api/posts/{id}`             | Update a post by ID              |
| DELETE | `/api/posts/{id}`             | Delete a post by ID              |
| GET    | `/api/posts/category/{id}`    | Get posts by category ID         |

---

## 💬 Comment APIs

Endpoints for comment operations on posts.

| Method | Endpoint                                     | Description                        |
|--------|----------------------------------------------|------------------------------------|
| POST   | `/api/posts/{postId}/comments`               | Add a comment to a post            |
| GET    | `/api/posts/{postId}/comments`               | Get all comments on a post         |
| GET    | `/api/posts/{postId}/comments/{commentId}`   | Get a specific comment by ID       |
| PUT    | `/api/posts/{postId}/comments/{commentId}`   | Update a comment                   |
| DELETE | `/api/posts/{postId}/comments/{commentId}`   | Delete a comment                   |

---


## 📦 Data Schemas (DTOs)

### 🔹 `CommentDTO`

| Field   | Type    | Description                           |
|---------|---------|---------------------------------------|
| `id`    | `Long`  | Unique comment ID                     |
| `name`  | `String`| Name of the commenter (min 1 char)    |
| `email` | `String`| Email of the commenter (min 1 char)   |
| `body`  | `String`| Body of the comment (min 10 chars)    |

---

### 🔹 `PostDTO`

| Field         | Type             | Description                                      |
|---------------|------------------|--------------------------------------------------|
| `id`          | `Long`           | Unique post ID                                   |
| `title`       | `String`         | Post title (min 2 characters)                    |
| `description` | `String`         | Short description (min 10 characters)            |
| `content`     | `String`         | Full content (min 1 character)                   |
| `comments`    | `List<Comment>`  | List of comments on the post                     |
| `categoryId`  | `Long`           | Category ID to which this post belongs           |

---

### 🔹 `CategoryDTO`

| Field         | Type    | Description                     |
|---------------|---------|---------------------------------|
| `id`          | `Long`  | Unique category ID              |
| `name`        | `String`| Name of the category            |
| `description` | `String`| Description of the category     |

---

### 🔹 `LoginDTO`

| Field              | Type    | Description                      |
|--------------------|---------|----------------------------------|
| `usernameOrEmail`  | `String`| Username or email for login      |
| `password`         | `String`| Password                         |

---

### 🔹 `JwtAuthResponse`

| Field         | Type    | Description                          |
|---------------|---------|--------------------------------------|
| `accessToken` | `String`| JWT access token                     |
| `tokenType`   | `String`| Usually `"Bearer"`                   |

---

### 🔹 `RegisterDTO`

| Field      | Type    | Description             |
|------------|---------|-------------------------|
| `name`     | `String`| Full name               |
| `username` | `String`| Desired username        |
| `email`    | `String`| Email address           |
| `password` | `String`| Account password        |

---

### 🔹 `PostResponse`

Paginated post list with metadata.

| Field           | Type              | Description                          |
|-----------------|-------------------|--------------------------------------|
| `content`       | `List<PostDTO>`   | List of posts                        |
| `pageNo`        | `Integer`         | Current page number                  |
| `pageSize`      | `Integer`         | Number of posts per page             |
| `totalElements` | `Long`            | Total number of posts                |
| `totalPages`    | `Integer`         | Total number of pages                |
| `last`          | `Boolean`         | If this is the last page             |

---

## 🔧 Technologies Used

* Java
* Spring Boot, Spring Data JPA
* Spring Security
* JWT (JSON Web Token)
* Maven
* IntelliJ Idea
* Tomcat Embedded Server
* MySQL
* Postman
* 
---
