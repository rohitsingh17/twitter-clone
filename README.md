# Twitter Clone Backend

A simplified backend application that replicates key functionalities of Twitter, allowing users to create tweets, follow other users, and view a personalized timeline of tweets from those they follow.

## Table of Contents
- [Features](#features)
- [API Endpoints](#api-endpoints)
- [Tech Stack](#tech-stack)
- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Project Structure](#project-structure)
- [Assumptions](#assumptions)
- [Enhancements & Improvements](#enhancements--improvements)
- [Error Handling](#error-handling)
- [Database](#database)

---

## Features

1. **Create a Tweet**: Users can post text-based tweets, up to 160 characters in length.
2. **Follow Other Users**: Follow functionality to view posts from other users in a personalized feed.
3. **Timeline**: See a list of recent tweets from users they follow.

## API Endpoints

1. **Create a Tweet**
    - **Endpoint**: `POST /api/v1/tweets/{username}`
    - **Request Body**:
      ```json
      {
        "content": "This is my first tweet!"
      }
      ```
    - **Response**:
        - **Success (200)**: Returns the newly created tweet details.
        - **Errors (400, 503)**: Validates tweet content or handles server issues.

2. **Follow a User**
    - **Endpoint**: `POST /api/v1/users/{follower}/follow/{user}`
    - **Response**:
        - **Success (200)**: Confirmation of the follow action.
        - **Errors (400, 404)**: Duplicate follows or user not found.

3. **Timeline**
    - **Endpoint**: `GET /api/v1/timeline/{username}`
    - **Response**:
        - **Success (200)**: Returns a list of tweets from followed users.
        - **No Data (204)**: No tweets available.
        - **User Not Found (404)**: User does not exist.

## Tech Stack

- **Java 8**
- **Spring Boot**
- **PostgreSQL**
- **Maven**

## Prerequisites

1. **PostgreSQL**: Ensure PostgreSQL is installed and running.
2. **Java 8**: The application is built on Java 8.
3. **Maven**: For managing dependencies and building the project.

## Installation

1. **Set Up the Database**
    - Execute the SQL scripts in the `/db_scripts` folder to create tables and add sample data.
    - Update the `application.yaml` configuration with your database connection details:
      ```yaml
      spring:
        datasource:
          url: jdbc:postgresql://{host}:{port}/twitter_clone
          username: {username}
          password: {password}
      ```

2. **Run the Application**
   ```bash
   mvn spring-boot:run
   ```

## Project Structure

```
src/main
├── java/com/example/twitter
│   ├── controller
│   ├── model
│   ├── dto
│   ├── repository
│   ├── service
│   └── exception
└── resources
    ├── application.yaml
└── db_scripts
    └── create_tables.sql
    └── insert_sample_data.sql
└── postman_collection
    └── twitter_clone.postman_collection.json
```

- **controller**: Contains the API controllers.
- **model**: Entity models for database tables.
- **dto**: DTOs for request response.
- **repository**: Data access interfaces.
- **service**: Business logic services.
- **exception**: Custom exception handling.

## Assumptions

1. **Authentication and Authorization**: Assumed to be handled externally.
2. **Rate Limiting**: Assumed to be handled externally.
3. **Tweet Length**: Maximum of 160 characters.

## Enhancements & Improvements

1. **Optimize Timeline Retrieval**
    - **Caching**: Cache timelines to improve response time for repeat requests.
    - **Hybrid Push-Pull Model**: Push tweets to subset of active followers' timelines, while allowing others to pull updates as needed.

2. **Microservices Architecture**
    - Separate into services like **Timeline Service**, **User Service**, and **Tweet Service** to improve scalability and maintainability.

3. **Other Suggestions**:
    - Add rate limiting, robust authentication, and additional analytics.

## Error Handling

| Exception                  | Status Code | Description                                       |
|----------------------------|-------------|---------------------------------------------------|
| `UserNotFoundException`    | 404         | User does not exist.                              |
| `DuplicateFollowException` | 400         | User is already following the target user.        |
| `InvalidInputException`    | 400         | Invalid input, such as empty tweet content.       |
| `DataAccessException`      | 503         | Database issue, service temporarily unavailable.  |
| `NoTimelineDataException`  | 204         | No tweets available for the following users.      |
| `MethodArgumentNotValidException` | 400  | Validation failed for input request.              |
| `Exception`                | 500         | General error, unexpected server error.           |

## Database

**Database Setup**: Use the `db_scripts` folder to set up tables and add sample data. Ensure you update database connection details in `application.yaml`.

--- 

### Optional: Attachments

- **Database Scripts**: Include in `/db_scripts`.
- **Postman Collection**: Add to `/postman_collection/twitter_clone.postman_collection.json`.
